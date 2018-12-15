package com.zhengdianfang.samplingpad.task.normal_product.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.TableFragment
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import kotlinx.android.synthetic.main.fragment_first_normal_table_layout.*

open class FirstTableFragment: TableFragment() {

    companion object {
        fun newInstance(taskItem: TaskItem): FirstTableFragment {
            val fragment = FirstTableFragment()
            val bundle = Bundle()
            bundle.putParcelable("task", taskItem)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun assembleSubmitTaskData() {
        taskItem.enforcePeople = enforcePeopleEditText.getContent()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_first_normal_table_layout, container, false)
    }

    override fun setupViews() {
        super.setupViews()
        taskSourceTextView.setContentText(taskItem.taskSource)
        planNameTextView.setContentText(taskItem.planName)
        sampleCodeTextView.setContentText(taskItem.code)
        detectionCompanyTextView.setContentText(taskItem.detectionCompanyName)
        implPlanCodeTextView.setContentText(taskItem.implPlanCode)
        createOrgNameTextView.setContentText(taskItem.createOrgName)
        createOrgAddressTextView.setContentText(taskItem.createOrgAddress)
        createOrgAreaNameTextView.setContentText(taskItem.createOrgAreaName)
        createOrgContactsTextView.setContentText(taskItem.createOrgContacts)
        creatorEmailTextView.setContentText(taskItem.creatorEmail)
        creatorPhoneTextView.setContentText(taskItem.creatorPhone)
        enforcePeopleEditText.setEditTextContent(taskItem.enforcePeople)


    }

    override fun submitSuccessful() {
        start(SecondTableFragment.newInstance(taskItem))
    }
}