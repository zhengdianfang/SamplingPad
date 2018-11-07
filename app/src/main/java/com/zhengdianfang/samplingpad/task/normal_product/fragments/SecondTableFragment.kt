package com.zhengdianfang.samplingpad.task.normal_product.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.common.TableFragment
import com.zhengdianfang.samplingpad.common.components.BaseComponent
import com.zhengdianfang.samplingpad.common.entities.OptionItem
import com.zhengdianfang.samplingpad.common.searchPoiByText
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import kotlinx.android.synthetic.main.fragment_second_normal_table_layout.*
import timber.log.Timber

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
        enterpriseLicenseNumberEditText.search = {text ->
            if (text.isNotEmpty()) {
                tableFragmentViewModel.fetchEnterpriseByLincenseCode(text)
            }
        }
        enterpriseNameEditText.setEditTextContent(taskItem.enterpriseName)
        enterpriseNameEditText.search = {text ->
            text.searchPoiByText(context!!) {data ->
                if (data != null) {
                    Timber.d("search nearby market list ${data.size}")
                    enterpriseNameEditText.notifySelectItems(data.asSequence().map { it.title }.toMutableList())
                }
            }
        }
        enterpriseAreaTypeRadioGroup.setDefaultCheckedRadioButton(taskItem.enterpriseAreaType)
        regionSpinnerGroup.fetchData()

        fetchEnterprisePlaceName(taskItem.enterpriseLinkId)

        enterpriseLinkNameRadioGroup.setDefaultCheckedRadioButton(taskItem.enterpriseLinkName)
        enterpriseLinkNameRadioGroup.radioButtonCheckCallback  = { _, option ->
            fetchEnterprisePlaceName(option.id)
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
        enterpriseQsNoEditText.search = {text ->
            if (text.isNotEmpty()) {
               tableFragmentViewModel.fetchEntrustByCsNo(text)
            }
        }
        enterpriseLegalRepEditText.setEditTextContent(taskItem.enterpriseLegalRep)
        enterpriseContactsEditText.setEditTextContent(taskItem.enterpriseContacts)
        enterprisePhoneEditText.setEditTextContent(taskItem.enterprisePhone)
        enterpriseFaxEditText.setEditTextContent(taskItem.enterpriseFax)
        enterpriseZipCodeEditText.setEditTextContent(taskItem.enterpriseZipCode)
        enterpriseAnnualSalesEditText.setEditTextContent(taskItem.enterpriseAnnualSales)
        specialAreaNameRadioGroup.setDefaultCheckedRadioButton(taskItem.specialAreaName)

        val chainTypes = resources.getStringArray(R.array.verify_unit_array)
        if (taskItem.enterpriseChain != null) {
            enterpriseChainRadioGroup.setDefaultCheckedRadioButton(chainTypes[taskItem.enterpriseChain!!])
            if (taskItem.enterpriseChain == TaskItem.UN_CHAIN_ENTERPRISE) {
                chainBrandEditText.visibility = View.GONE
            } else if (taskItem.enterpriseChain == TaskItem.CHAIN_ENTERPRISE) {
                chainBrandEditText.visibility = View.VISIBLE
            }
        }
        enterpriseChainRadioGroup.radioButtonCheckCallback = { position, _ ->
           if (position == TaskItem.UN_CHAIN_ENTERPRISE) {
               chainBrandEditText.visibility = View.GONE
           } else if (position == TaskItem.CHAIN_ENTERPRISE) {
               chainBrandEditText.visibility = View.VISIBLE
           }
        }

        chainBrandEditText.setEditTextContent(taskItem.chainBrand)
        regionSpinnerGroup.setDefaultStreet(taskItem?.enterpriseAreaId)

    }

    private fun fetchEnterprisePlaceName(id: Int?) {
        if (id != null) {
            val url = getString(R.string.sample_link_name_data_api, id)
            enterprisePlaceNameSpinner.fetchData("${ApiClient.HOST}$url")
        }
    }

    override fun submitSuccessful() {
        start(ThirdTableFragment.newInstance(taskItem))
    }

    override fun assembleSubmitTaskData() {
        taskItem.enterpriseLicenseNumber = enterpriseLicenseNumberEditText.getContent()
        taskItem.enterpriseName = enterpriseNameEditText.getContent()
        taskItem.enterpriseAreaType = enterpriseAreaTypeRadioGroup.getCheckedText()
        taskItem.setEnterpriseAreaInfo(regionSpinnerGroup.getStreet())
        taskItem.setEnterpriseLink(enterpriseLinkNameRadioGroup.getCheckedOption())
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
        taskItem.setSpecialAreaName(specialAreaNameRadioGroup.getCheckedOption())

        val chainTypes = resources.getStringArray(R.array.verify_unit_array)
        when {
            enterpriseChainRadioGroup.getCheckedText() == chainTypes[0] -> taskItem.enterpriseChain = TaskItem.UN_CHAIN_ENTERPRISE
            enterpriseChainRadioGroup.getCheckedText() == chainTypes[1] -> taskItem.enterpriseChain = TaskItem.CHAIN_ENTERPRISE
        }
        taskItem.chainBrand = chainBrandEditText.getContent()
    }

    override fun bindViewModel() {
        super.bindViewModel()
        tableFragmentViewModel.enterpriseLiveData.observe(this, Observer { enterprise ->
            this.taskItem.mergeEnterprise(enterprise!!)
            this.setupViews()
        })
    }
}