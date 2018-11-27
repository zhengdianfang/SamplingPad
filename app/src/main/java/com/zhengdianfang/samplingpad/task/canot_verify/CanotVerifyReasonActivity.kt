package com.zhengdianfang.samplingpad.task.canot_verify

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.BaseActivity
import com.zhengdianfang.samplingpad.common.BaseFragment
import com.zhengdianfang.samplingpad.http.ApiClient
import kotlinx.android.synthetic.main.activity_cannot_reason_layout.*
import kotlinx.android.synthetic.main.notification_template_lines_media.view.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class CanotVerifyReasonActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadRootFragment(android.R.id.content, CanotVerifyReasonFragment.newInstance())

    }

}

class CanotVerifyReasonFragment : BaseFragment() {

    companion object {
        fun newInstance():  CanotVerifyReasonFragment {
            return CanotVerifyReasonFragment()
        }
    }

    private val canotVerifyViewModel by lazy { ViewModelProviders.of(this).get(CanotVerifyViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_cannot_reason_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.setupViews()
        this.bindViewModel()
    }

    private fun bindViewModel() {
        canotVerifyViewModel.isLoadingLiveData.observe(this, Observer { isLoading ->
            if (isLoading!!) {
                startLoading()
            } else {
                stopLoading()
            }
        })

        canotVerifyViewModel.responseLiveData.observe(this, Observer { response ->
            Toast.makeText(context, response?.msg, Toast.LENGTH_SHORT).show()
            if (response?.code == 200) {
                activity?.finish()
            }
        })

    }

    private fun setupViews() {
        toolBarTitleView.text = "无法检验原因"
        backButton.setOnClickListener {
            activity?.finish()
        }

        regionSpinnerGroup.fetchData()
        abnormalTypeNameSpinner.fetchData("${ApiClient.getHost()}/app/abnormaltypelis")
        saveAndSubmitButton.setOnClickListener {
            val wantSample = wantSampleEditText.getContent()
            val enterpriseName = enterpriseNameEditText.getContent()
            val enterpriseAddress = enterpriseAddressEditText.getContent()
            val abnormalTypeName = abnormalTypeNameSpinner.getSelectedOption()?.name
            val creatorName= creatorNameEditText.getContent()
            val enterpriseAreaName = regionSpinnerGroup.getContent()
            val createOrgName = detectionCompanyEditText.getContent()
            val callback = if (producerActiveRadioGroup.getCheckedText() == "是")  1 else  0
            val taskNo = taskNoEditText.getContent()

            canotVerifyViewModel.submitCanotVerifyTask(
                mapOf(
                    Pair("wantSample", wantSample),
                    Pair("enterpriseName", enterpriseName),
                    Pair("enterpriseAddress", enterpriseAddress),
                    Pair("abnormalTypeName", abnormalTypeName),
                    Pair("creatorName", creatorName),
                    Pair("enterpriseAreaName", enterpriseAreaName),
                    Pair("createOrgName", createOrgName),
                    Pair("callback", callback.toString()),
                    Pair("taskNo", taskNo)
                )
            )
        }
    }
}