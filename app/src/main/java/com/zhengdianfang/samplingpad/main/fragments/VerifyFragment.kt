package com.zhengdianfang.samplingpad.main.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.BaseFragment
import com.zhengdianfang.samplingpad.main.api.VerifySampleParam
import kotlinx.android.synthetic.main.fragment_verfiy_layout.*

class VerifyFragment : BaseFragment() {

    private val verifyFragmentViewModel by lazy { ViewModelProviders.of(this).get(VerifyFragmentViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_verfiy_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        verifyFragmentViewModel.isLoadingLiveData.observe(this, Observer { isLoading ->
            if (isLoading!!) {
                startLoading()
            } else {
                stopLoading()
            }
        })

        verifyFragmentViewModel.messageLiveData.observe(this, Observer { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        })

        verifyButton.setOnClickListener {
            val implPlanCode = implPlanCodeEditText.getContent()
            val level1Name = level1NameEditText.getContent()
            val enterpriseLicenseNumber = enterpriseLicenseNumberEditText.getContent()
            val sampleName = sampleNameEditText.getContent()
            val sampleProductDate = sampleProductDateView.getDate()
            val chainBrand = chainBrandEditText.getContent()
            val sampleDate = sampleDateView.getDate()
            val producerCsNo = producerCsNoEditText.getContent()

            val verifySampleParam = VerifySampleParam(
                implPlanCode,
                producerCsNo,
                sampleName,
                sampleProductDate,
                enterpriseLicenseNumber,
                level1Name,
                chainBrand,
                sampleDate
            )
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
    }
}
