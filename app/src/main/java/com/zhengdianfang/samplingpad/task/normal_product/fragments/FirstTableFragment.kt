package com.zhengdianfang.samplingpad.task.normal_product.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.BaseFragment
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import kotlinx.android.synthetic.main.fragment_first_normal_table_layout.*

class FirstTableFragment: BaseFragment() {

    private val taskItem by lazy { arguments?.getParcelable("task") as TaskItem }
    private val tableFragmentViewModel by lazy { ViewModelProviders.of(this).get(TableFragmentViewModel::class.java) }

    companion object {
        fun newInstance(taskItem: TaskItem): FirstTableFragment {
            val fragment = FirstTableFragment()
            val bundle = Bundle()
            bundle.putParcelable("task", taskItem)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_first_normal_table_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.setupViews()
        this.bindViewModel()
    }

    private fun setupViews() {
        if (taskItem != null) {
            taskSourceTextView.setContentText(taskItem.taskSource)
            planNameTextView.setContentText(taskItem.planName)
            sampleCodeTextView.setContentText(taskItem.code)
            detectionCompanyTextView.setContentText(taskItem.detectionCompanyName)
            implPlanCodeTextView.setContentText(taskItem.implPlanCode)
            leve1TypeTextView.setContentText(taskItem.level1Name)
            level2TypeTextView.setContentText(taskItem.level2Name)
            level3TypeTextView.setContentText(taskItem.level3Name)
            level4TypeTextView.setContentText(taskItem.level4Name)

            createOrgNameTextView.setContentText(taskItem.createOrgName)
            createOrgAddressTextView.setContentText(taskItem.createOrgAddress)
            createOrgAreaNameTextView.setContentText(taskItem.createOrgAreaName)
            createOrgContactsTextView.setContentText(taskItem.createOrgContacts)
            creatorEmailTextView.setContentText(taskItem.creatorEmail)
            creatorPhoneTextView.setContentText(taskItem.creatorPhone)
            inspectionKindNameRadioGroup.setDefaultCheckedRadioButton(taskItem.inspectionKindName)
        }

        nextButtonButton.setOnClickListener {
            taskItem.inspectionKindName = inspectionKindNameRadioGroup.getCheckedText()
            tableFragmentViewModel.saveSample(taskItem)
        }
        resetButton.setOnClickListener {
           inspectionKindNameRadioGroup.clear()
        }
    }

    private fun bindViewModel() {
        tableFragmentViewModel.isLoadingLiveData.observe(this, Observer { isLoading ->
           if (isLoading!!) {
               startLoading()
           } else {
               stopLoading()
           }
        })
        tableFragmentViewModel.responseLiveData.observe(this, Observer { response ->
            Toast.makeText(context, response!!.msg, Toast.LENGTH_SHORT).show()
            if (response!!.code == 200) {
                start(SecondTableFragment.newInstance())
            }
        })

    }
}