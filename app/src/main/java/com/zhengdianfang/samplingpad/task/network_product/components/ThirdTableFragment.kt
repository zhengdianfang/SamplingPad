package com.zhengdianfang.samplingpad.task.network_product.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.TableFragment
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import kotlinx.android.synthetic.main.fragment_first_normal_table_layout.*
import kotlinx.android.synthetic.main.fragment_third_network_table_layout.*
import me.yokeyword.fragmentation.SupportFragment

class ThirdTableFragment: TableFragment() {

    companion object {
        fun newInstance(taskItem: TaskItem): ThirdTableFragment {
            val fragment = ThirdTableFragment()
            val bundle = Bundle()
            bundle.putParcelable("task", taskItem)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_third_network_table_layout, container, false)
    }

    override fun setupViews() {
        super.setupViews()
        producerLicenseNumberEditText.setEditTextContent(taskItem.producerLicenseNumber)
        producerCsNoEditText.setEditTextContent(taskItem.producerCsNo)
        producerAreaNameEditText.setEditTextContent(taskItem.producerAreaName)
        enterpriseUrlEditText.setEditTextContent(taskItem.enterpriseUrl)
        producerAddressEditText.setEditTextContent(taskItem.producerAddress)
        producerContactsEditText.setEditTextContent(taskItem.producerContacts)
        producerPhoneEditText.setEditTextContent(taskItem.producerPhone)
    }

    override fun submitSuccessful() {
    }

    override fun assembleSubmitTaskData() {
        taskItem.producerLicenseNumber = producerLicenseNumberEditText.getContent()
        taskItem.producerCsNo = producerCsNoEditText.getContent()
        taskItem.producerAreaName = producerAreaNameEditText.getContent()
        taskItem.enterpriseUrl = enterpriseUrlEditText.getContent()
        taskItem.producerAddress = producerAddressEditText.getContent()
        taskItem.producerContacts = producerContactsEditText.getContent()
        taskItem.producerPhone = producerPhoneEditText.getContent()
    }

    override fun clearAllFilledData() {
        producerLicenseNumberEditText.clear()
        producerCsNoEditText.clear()
        producerAreaNameEditText.clear()
        enterpriseUrlEditText.clear()
        producerAddressEditText.clear()
        producerContactsEditText.clear()
        producerPhoneEditText.clear()
    }

}