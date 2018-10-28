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
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import kotlinx.android.synthetic.main.activity_cannot_reason_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class CanotVerifyReasonActivity: BaseActivity() {

    private val taskItem by lazy { intent.getParcelableExtra("taskItem") as TaskItem }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadRootFragment(android.R.id.content, CanotVerifyReasonFragment.newInstance(taskItem))

    }

}

public class CanotVerifyReasonFragment : BaseFragment() {

    companion object {
        fun newInstance(taskItem: TaskItem):  CanotVerifyReasonFragment {
            val fragment = CanotVerifyReasonFragment()
            val bundle = Bundle()
            bundle.putParcelable("taskItem", taskItem)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val taskItem by lazy { arguments?.getParcelable("taskItem") as TaskItem }
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
        taskNoTextView.text = "单号：${taskItem.code}"

        abnormalTypeNameSpinner.fetchData("${ApiClient.HOST}/app/abnormaltypelis")
        saveAndSubmitButton.setOnClickListener {
            val wantSample = wantSampleEditText.getContent()
            val enterpriseName = enterpriseNameEditText.getContent()
            val enterpriseAddress = enterpriseAddressEditText.getContent()
            val abnormalTypeName = abnormalTypeNameSpinner.getContent()
            val creatorName= creatorNameEditText.getContent()
            val taskNo = taskItem.code

            canotVerifyViewModel.submitCanotVerifyTask(
                mapOf(
                    Pair("wantSample", wantSample),
                    Pair("enterpriseName", enterpriseName),
                    Pair("enterpriseAddress", enterpriseAddress),
                    Pair("abnormalTypeName", abnormalTypeName),
                    Pair("creatorName", creatorName),
                    Pair("taskNo", taskNo)
                )
            )
        }
    }
}