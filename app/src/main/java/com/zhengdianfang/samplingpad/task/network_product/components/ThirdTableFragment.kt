package com.zhengdianfang.samplingpad.task.network_product.components

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.TableFragment
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import kotlinx.android.synthetic.main.fragment_third_network_table_layout.*

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
        enterpriseLicenseNumberNumberEditText.setEditTextContent(taskItem.enterpriseLicenseNumber)
        enterpriseLicenseNumberNumberEditText.search = {text ->
            if (text.isNotEmpty()) {
                tableFragmentViewModel.fetchEnterpriseByLincenseCode(text)
            }
        }
        enterpriseQsNoEditText.setEditTextContent(taskItem.enterpriseQsNo)
        enterpriseAreaNameEditText.setEditTextContent(taskItem.enterpriseAreaName)
        enterpriseUrlEditText.setEditTextContent(taskItem.enterpriseUrl)
        enterpriseAddressEditText.setEditTextContent(taskItem.enterpriseAddress)
        enterpriseContactsEditText.setEditTextContent(taskItem.enterpriseContacts)
        enterprisePhoneEditText.setEditTextContent(taskItem.enterprisePhone)

        val sourceArray = resources.getStringArray(R.array.enterprise_address_sources_array)
        if (taskItem.enterpriseAddressSources != null) {
            enterpriseAddressSourcesRadioGroup.setDefaultCheckedRadioButton(sourceArray[taskItem.enterpriseAddressSources!! + 1])
        }

        enterpriseQsNoEditText.search = {text ->
            if (text.isNotEmpty()) {
                tableFragmentViewModel.fetchEntrustByCsNo(text)
            }
        }
    }

    override fun bindViewModel() {
        super.bindViewModel()
        tableFragmentViewModel.enterpriseLiveData.observe(this, Observer {enterprise ->
            taskItem.mergeEnterprise(enterprise!!)
            this.setupViews()
        })
    }

    override fun submitSuccessful() {
        start(FourthTableFragment.newInstance(taskItem))
    }

    override fun assembleSubmitTaskData() {
        taskItem.enterpriseLicenseNumber = enterpriseLicenseNumberNumberEditText.getContent()
        taskItem.enterpriseQsNo = enterpriseQsNoEditText.getContent()
        taskItem.enterpriseAreaName = enterpriseAreaNameEditText.getContent()
        taskItem.enterpriseUrl = enterpriseUrlEditText.getContent()
        taskItem.enterpriseAddress = enterpriseAddressEditText.getContent()
        taskItem.enterpriseContacts = enterpriseContactsEditText.getContent()
        taskItem.enterprisePhone = enterprisePhoneEditText.getContent()

        val sourceArray = resources.getStringArray(R.array.enterprise_address_sources_array)
        taskItem.enterpriseAddressSources = sourceArray.indexOf(enterpriseAddressSourcesRadioGroup.getCheckedText()) + 1
    }

}