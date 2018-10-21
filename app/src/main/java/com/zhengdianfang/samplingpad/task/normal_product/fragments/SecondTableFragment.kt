package com.zhengdianfang.samplingpad.task.normal_product.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.api.ApiClient
import com.zhengdianfang.samplingpad.common.TableFragment
import com.zhengdianfang.samplingpad.common.components.BaseComponent
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import kotlinx.android.synthetic.main.fragment_second_normal_table_layout.*

class SecondTableFragment: TableFragment() {

    companion object {
        fun newInstance(taskItem: TaskItem): SecondTableFragment {
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        nextButtonButton.setOnClickListener {
            start(ThirdTableFragment.newInstance())
        }
    }

    override fun setupViews() {
        super.setupViews()
        enterpriseLicenseNumberEditText.setEditTextContent(taskItem.enterpriseLicenseNumber)
        enterpriseNameEditText.setEditTextContent(taskItem.enterpriseName)
        enterpriseAreaTypeRadioGroup.setDefaultCheckedRadioButton(taskItem.enterpriseAreaType)
        // TODO 区 街道选择
        enterpriseLinkNameRadioGroup.setDefaultCheckedRadioButton(taskItem.enterpriseLinkName)
        enterpriseLinkNameRadioGroup.radioButtonCheckCallback  = { _, text ->
            val enterpriseLinkNames = resources.getStringArray(R.array.sample_link_names)
            val url = getString(R.string.sample_link_name_data_api, enterpriseLinkNames.indexOf(text) + 1)
            sampleLinkNameSpinner.fetchSpinnerItems("${ApiClient.HOST}$url")
        }
        sampleLinkNameSpinner.setDefaultText(taskItem.sampleLinkName)
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
        start(ThirdTableFragment.newInstance())
    }

    override fun assembleSubmitTaskData() {
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