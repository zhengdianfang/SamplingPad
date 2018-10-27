package com.zhengdianfang.samplingpad.main.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.BaseFragment
import com.zhengdianfang.samplingpad.common.convertForegroundColorSpannableString
import com.zhengdianfang.samplingpad.common.searchPoiByText
import kotlinx.android.synthetic.main.fragment_verfiy_layout.*
import timber.log.Timber

class VerifyFragment : BaseFragment() {

    private val verifyFragmentViewModel by lazy { ViewModelProviders.of(this).get(VerifyFragmentViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_verfiy_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
        bindViewModels()
    }

    private fun autoCompleteNearbyShopName() {
        enterpriseNameEditText.search = {text ->
            text.searchPoiByText(context!!) {data ->
                if (data != null) {
                    Timber.d("search nearby market list ${data.size}")
                    enterpriseNameEditText.notifySelectItems(data.asSequence().map { it.title }.toMutableList())
                }
            }
        }
    }

    private fun setupViews() {
        verifyButton.setOnClickListener {
            val implPlanCode = implPlanCodeEditText.getContent()
            val level1Name = level1NameEditText.getContent()
            val enterpriseLicenseNumber = enterpriseLicenseNumberEditText.getContent()
            val sampleName = sampleNameEditText.getContent()
            val sampleProductDate = sampleProductDateView.getDate()
            val chainBrand = chainBrandEditText.getContent()
            val sampleDate = sampleDateView.getDate()
            val producerCsNo = producerCsNoEditText.getContent()

            verifyFragmentViewModel.postVerifySample(
                mapOf(
                    Pair("implPlanCode", implPlanCode),
                    Pair("level1Name", level1Name),
                    Pair("enterpriseLicenseNumber", enterpriseLicenseNumber),
                    Pair("sampleName", sampleName),
                    Pair("sampleProductDate", "$sampleProductDate"),
                    Pair("chainBrand", chainBrand),
                    Pair("sampleDate", "$sampleDate"),
                    Pair("producerCsNo", producerCsNo)
                )
            )
        }

        introduceTextView.text = getString(R.string.verify_detail_text)
            .convertForegroundColorSpannableString(regex = "(\\([^\\)]+\\))")
        autoCompleteNearbyShopName()
    }

    private fun bindViewModels() {
        verifyFragmentViewModel.isLoadingLiveData.observe(this, Observer { isLoading ->
            if (isLoading!!) {
                startLoading()
            } else {
                stopLoading()
            }
        })

        verifyFragmentViewModel.responseLiveData.observe(this, Observer { response ->
            activity?.supportFragmentManager?.beginTransaction()
                ?.add(android.R.id.content, VerifyResultFragment.newInstance(response!!.code == 200, response!!.msg))
                ?.addToBackStack("")
                ?.commit()
        })
    }
}
