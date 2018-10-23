package com.zhengdianfang.samplingpad.task.normal_product.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.common.TableFragment
import com.zhengdianfang.samplingpad.common.components.BaseComponent
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import kotlinx.android.synthetic.main.fragment_second_normal_table_layout.*

open class SecondTableFragment: TableFragment() {

    companion object {
        fun newInstance(taskItem: TaskItem?): SecondTableFragment {
            val fragment = SecondTableFragment()
            val bundle = Bundle()
            bundle.putParcelable("task", taskItem)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_second_normal_table_layout, container, false)
    }

    override fun setupViews() {
        super.setupViews()
        enterpriseLicenseNumberEditText.setEditTextContent(taskItem.enterpriseLicenseNumber)
        enterpriseNameEditText.setEditTextContent(taskItem.enterpriseName)
        enterpriseAreaTypeRadioGroup.setDefaultCheckedRadioButton(taskItem.enterpriseAreaType)
        regionSpinnerGroup.fetchData()
        enterpriseLinkNameRadioGroup.setDefaultCheckedRadioButton(taskItem.enterpriseLinkName)
        enterpriseLinkNameRadioGroup.radioButtonCheckCallback  = { _, text ->
            val enterpriseLinkNames = resources.getStringArray(R.array.sample_link_names)
            val url = getString(R.string.sample_link_name_data_api, enterpriseLinkNames.indexOf(text) + 1)
            enterprisePlaceNameSpinner.fetchData("${ApiClient.HOST}$url")
        }
        enterprisePlaceNameSpinner.setDefaultText(taskItem.enterprisePlaceName)
        enterpriseAddressEditText.setEditTextContent(taskItem.enterpriseAddress)
        val certificates = resources.getStringArray(R.array.licence_type_array)
        when(taskItem.enterpriseMOrP) {
           TaskItem.BUSINESS_CERTIFICATE ->
               enterpriseMOrPRadioGroup.setDefaultCheckedRadioButton(certificates[0])

            TaskItem.PRODUCE_CERTIFICATE ->
                enterpriseMOrPRadioGroup.setDefaultCheckedRadioButton(certificates[1])
        }
        enterpriseQsNoEditText.setEditTextContent(taskItem.enterpriseQsNo)
        enterpriseLegalRepEditText.setEditTextContent(taskItem.enterpriseLegalRep)
        enterpriseContactsEditText.setEditTextContent(taskItem.enterpriseContacts)
        enterprisePhoneEditText.setEditTextContent(taskItem.enterprisePhone)
        enterpriseFaxEditText.setEditTextContent(taskItem.enterpriseFax)
        enterpriseZipCodeEditText.setEditTextContent(taskItem.enterpriseZipCode)
        enterpriseAnnualSalesEditText.setEditTextContent(taskItem.enterpriseAnnualSales)
        specialAreaNameRadioGroup.setDefaultCheckedRadioButton(taskItem.specialAreaName)

        val chainTypes = resources.getStringArray(R.array.verify_unit_array)
        when(taskItem.enterpriseChain) {
            TaskItem.UN_CHAIN_ENTERPRISE ->
                enterpriseChainRadioGroup.setDefaultCheckedRadioButton(chainTypes[1])

            TaskItem.CHAIN_ENTERPRISE ->
                enterpriseChainRadioGroup.setDefaultCheckedRadioButton(chainTypes[0])

            TaskItem.CHAIN_BRAND ->
                enterpriseChainRadioGroup.setDefaultCheckedRadioButton(chainTypes[2])
        }

    }

    override fun submitSuccessful() {
        start(ThirdTableFragment.newInstance(taskItem))
    }

    override fun assembleSubmitTaskData() {
        taskItem.enterpriseLicenseNumber = enterpriseLicenseNumberEditText.getContent()
        taskItem.enterpriseName = enterpriseNameEditText.getContent()
        taskItem.enterpriseAreaType = enterpriseAreaTypeRadioGroup.getCheckedText()
        taskItem.enterpriseAreaName = regionSpinnerGroup.getContent()
        taskItem.enterpriseLinkName =  enterpriseLinkNameRadioGroup.getCheckedText()
        taskItem.enterprisePlaceName =  enterprisePlaceNameSpinner.getContent()
        taskItem.enterpriseAddress = enterpriseAddressEditText.getContent()

        val certificates = resources.getStringArray(R.array.licence_type_array)
        taskItem.enterpriseMOrP = certificates.indexOf(enterpriseMOrPRadioGroup.getCheckedText()) + 1
        taskItem.enterpriseQsNo = enterpriseQsNoEditText.getContent()
        taskItem.enterpriseLegalRep = enterpriseLegalRepEditText.getContent()
        taskItem.enterpriseContacts = enterpriseContactsEditText.getContent()
        taskItem.enterprisePhone = enterprisePhoneEditText.getContent()
        taskItem.enterpriseFax = enterpriseFaxEditText.getContent()
        taskItem.enterpriseZipCode = enterpriseZipCodeEditText.getContent()
        taskItem.enterpriseAnnualSales = enterpriseAnnualSalesEditText.getContent()
        taskItem.specialAreaName = specialAreaNameRadioGroup.getCheckedText()

        val chainTypes = resources.getStringArray(R.array.verify_unit_array)
        when {
            enterpriseChainRadioGroup.getCheckedText() == chainTypes[0] -> taskItem.enterpriseChain = TaskItem.CHAIN_ENTERPRISE
            enterpriseChainRadioGroup.getCheckedText() == chainTypes[1] -> taskItem.enterpriseChain = TaskItem.UN_CHAIN_ENTERPRISE
            enterpriseChainRadioGroup.getCheckedText() == chainTypes[2] -> taskItem.enterpriseChain = TaskItem.CHAIN_BRAND
        }
    }

    override fun clearAllFilledData() {
        for(index in 0 until tableFrame.childCount) {
            val childView = tableFrame.getChildAt(index)
            if (childView is BaseComponent) {
                childView.clear()
            }
        }
        enterpriseChainRadioGroup.clear()
    }
}