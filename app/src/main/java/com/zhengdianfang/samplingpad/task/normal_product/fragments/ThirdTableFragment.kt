package com.zhengdianfang.samplingpad.task.normal_product.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.common.TableFragment
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import kotlinx.android.synthetic.main.fragment_third_normal_table_layout.*

class ThirdTableFragment: TableFragment() {

    companion object {
        fun newInstance(taskItem: TaskItem?): ThirdTableFragment {
            val fragment = ThirdTableFragment()
            val bundle = Bundle()
            bundle.putParcelable("task", taskItem)
            fragment.arguments = bundle
            return fragment
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_third_normal_table_layout, container, false)
    }

    override fun setupViews() {
        super.setupViews()

        val yesOrNo = resources.getStringArray(R.array.yes_or_no)
        if (taskItem.producerActive != null && taskItem.producerActive!! > 0) {
            producerActiveRadioGroup.setDefaultCheckedRadioButton(yesOrNo[taskItem.producerActive!!])
        }
        //委托单位信息
        entrustCsNoEditText.setEditTextContent(taskItem.entrustCsNo)
        entrustNameEditText.setEditTextContent(taskItem.entrustName)
        entrustAddressEditText.setEditTextContent(taskItem.entrustAddress)
        entrustContactsEditText.setEditTextContent(taskItem.entrustContacts)
        entrustPhoneEditText.setEditTextContent(taskItem.entrustPhone)

        areaSpinnerGroupView.fetchData()
        //进口代理商信息
        agencyNameEditText.setEditTextContent(taskItem.agencyName)
        agencyAddressEditText.setEditTextContent(taskItem.agencyAddress)
        agencyContactsEditText.setEditTextContent(taskItem.agencyContacts)
        agencyPhoneEditText.setEditTextContent(taskItem.agencyPhone)
        resourceSpinnerGroupView.fetchData("${ApiClient.HOST}app/areas/origin")

    }

    override fun submitSuccessful() {
        start(FourthTableFragment.newInstance(taskItem))
    }

    override fun assembleSubmitTaskData() {
        val yesOrNo = resources.getStringArray(R.array.yes_or_no)
        taskItem.producerActive = yesOrNo.indexOf(producerActiveRadioGroup.getCheckedText())
        //委托单位信息
        taskItem.entrustCsNo = entrustCsNoEditText.getContent()
        taskItem.entrustName = entrustNameEditText.getContent()
        taskItem.entrustAddress = entrustAddressEditText.getContent()
        taskItem.entrustContacts = entrustContactsEditText.getContent()
        taskItem.entrustPhone = entrustPhoneEditText.getContent()

        areaSpinnerGroupView.fetchData()
        //进口代理商信息
        taskItem.agencyName = agencyNameEditText.getContent()
        taskItem.agencyAddress = agencyAddressEditText.getContent()
        taskItem.agencyContacts = agencyContactsEditText.getContent()
        taskItem.agencyPhone =  agencyPhoneEditText.getContent()
    }

    override fun clearAllFilledData() {
        producerActiveRadioGroup.clear()
        entrustCsNoEditText.clear()
        entrustNameEditText.clear()
        entrustAddressEditText.clear()
        entrustContactsEditText.clear()
        entrustPhoneEditText.clear()

        agencyNameEditText.clear()
        agencyAddressEditText.clear()
        agencyContactsEditText.clear()
        agencyPhoneEditText.clear()
    }
}