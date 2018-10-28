package com.zhengdianfang.samplingpad.main.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.BaseFragment
import com.zhengdianfang.samplingpad.common.components.BaseComponent
import com.zhengdianfang.samplingpad.common.convertForegroundColorSpannableString
import com.zhengdianfang.samplingpad.common.searchPoiByText
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.task.normal_product.fragments.TableFragmentViewModel
import kotlinx.android.synthetic.main.fragment_verfiy_layout.*
import timber.log.Timber

class VerifyFragment : BaseFragment() {

    private val verifyFragmentViewModel by lazy { ViewModelProviders.of(this).get(VerifyFragmentViewModel::class.java) }
    private val tableFragmentViewModel by lazy { ViewModelProviders.of(this).get(TableFragmentViewModel::class.java) }

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
        enterpriseNameEditText.search = { text ->
            text.searchPoiByText(context!!) { data ->
                if (data != null) {
                    Timber.d("search nearby market list ${data.size}")
                    enterpriseNameEditText.notifySelectItems(data.asSequence().map { it.title }.toMutableList())
                }
            }
        }
    }

    private fun setupViews() {
        level1NameSpinner.fetchData("${ApiClient.HOST}app/foodCataloglis") { item -> item.level == 1 }
        verifyButton.setOnClickListener {
            val implPlanCode = implPlanCodeEditText.getContent()
            val level1Name = level1NameSpinner.getContent()
            val enterpriseLicenseNumber = enterpriseLicenseNumberEditText.getContent()
            val sampleName = sampleNameEditText.getContent()
            val sampleProductDate = sampleProductDateView.getDate()
            val chainBrand = chainBrandEditText.getContent()
            val sampleDate = sampleDateView.getDate()
            val producerCsNo = producerCsNoEditText.getContent()
            val enterpriseName = enterpriseNameEditText.getContent()

            verifyFragmentViewModel.postVerifySample(
                implPlanCode,
                level1Name,
                enterpriseLicenseNumber,
                sampleName,
                "$sampleProductDate",
                chainBrand,
                "$sampleDate",
                producerCsNo,
                enterpriseName
            )
        }
        resetButton.setOnClickListener {
            for (index in 0 until componentGroupFrame.childCount) {
                val childrenView = componentGroupFrame.getChildAt(index)
                if (childrenView is BaseComponent) {
                    childrenView.clear()
                }
            }
        }

        introduceTextView.text = getString(R.string.verify_detail_text)
            .convertForegroundColorSpannableString(regex = "(\\([^\\)]+\\))")
        autoCompleteNearbyShopName()
        enterpriseLicenseNumberEditText.search = { text ->
            if (text.isNotEmpty()) {
                tableFragmentViewModel.fetchEnterpriseByLincenseCode(text)
            }
        }
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
                ?.add(android.R.id.content, VerifyResultFragment.newInstance(response!!.code == 200, response.msg))
                ?.addToBackStack("")
                ?.commit()
        })

        tableFragmentViewModel.enterpriseLiveData.observe(this, Observer { enterprise ->
            enterpriseNameEditText.setEditTextContent(enterprise!!.name)
        })
    }
}
