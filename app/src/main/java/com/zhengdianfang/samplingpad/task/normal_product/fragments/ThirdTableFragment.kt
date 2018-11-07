package com.zhengdianfang.samplingpad.task.normal_product.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.common.TableFragment
import com.zhengdianfang.samplingpad.task.entities.Goods
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

        //标称信息
        producerActiveRadioGroup.radioButtonCheckCallback = { _, option ->
            showHideAgencyFrame(option.id)
        }
        producerActiveRadioGroup.setDefaultCheckedRadioButton(taskItem.producerActive)
        produceCsNoEditText.setEditTextContent(taskItem.producerCsNo)
        produceNameEditText.setEditTextContent(taskItem.producerName)
        produceAddressEditText.setEditTextContent(taskItem.producerAddress)
        produceContactsEditText.setEditTextContent(taskItem.producerContacts)
        producePhoneEditText.setEditTextContent(taskItem.producerPhone)
        addressSpinner.labelTextView.text = "*生产所在地："
        addressSpinner.fetchData()
        addressSpinner.setDefault(taskItem.producerProvincialId, taskItem.producerTownId, taskItem.producerCountyId)

        entrustActiveRadioGroup.radioButtonCheckCallback = { position, _ ->
            showHideEntrustFrame(position)
        }
        entrustActiveRadioGroup.setDefaultCheckedRadioButton(taskItem.entrustActive)

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

    private fun showHideAgencyFrame(active: Int) {
        if (active == 0) {
            agencyFrame.visibility = View.GONE
            showHideEntrustFrame(entrustActiveRadioGroup.getCheckedOption()?.id ?: 0)
            showHideProduceFrame(View.VISIBLE)
        } else if (active == 1){
            agencyFrame.visibility = View.VISIBLE
            entrustFrame.visibility = View.GONE
            showHideProduceFrame(View.GONE)
        }
    }

    private fun showHideEntrustFrame(active: Int) {
        if (active == 0) {
            entrustFrame.visibility = View.GONE
        } else if (active == 1){
            entrustFrame.visibility = View.VISIBLE
        }
    }

    private fun showHideProduceFrame(visiable: Int) {
        for (index in 0 until produceFrame.childCount) {
            if (index > 1) {
                produceFrame.getChildAt(index).visibility = visiable
            }
        }
        entrustActiveRadioGroup.clear()
    }

    override fun submitSuccessful() {
        start(FourthTableFragment.newInstance(taskItem))
    }

    override fun assembleSubmitTaskData() {
        //标称信息
        taskItem.producerActive = producerActiveRadioGroup.getCheckedOption()?.id

        taskItem.producerCsNo = produceCsNoEditText.getContent()
        taskItem.producerName = produceNameEditText.getContent()
        taskItem.producerAddress = produceAddressEditText.getContent()
        taskItem.producerContacts = produceContactsEditText.getContent()
        taskItem.producerPhone = producePhoneEditText.getContent()
        taskItem.producerAddress = addressSpinner.getContent()
        taskItem.setProducerAddressInfo(addressSpinner.getProvince(), addressSpinner.getTown(), addressSpinner.getCounty())

        //委托单位信息
        taskItem.entrustCsNo = entrustCsNoEditText.getContent()
        taskItem.entrustName = entrustNameEditText.getContent()
        taskItem.entrustAddress = entrustAddressEditText.getContent()
        taskItem.entrustContacts = entrustContactsEditText.getContent()
        taskItem.entrustPhone = entrustPhoneEditText.getContent()
        taskItem.entrustActive = entrustActiveRadioGroup.getCheckedOption()?.id

        //进口代理商信息
        taskItem.agencyName = agencyNameEditText.getContent()
        taskItem.agencyAddress = agencyAddressEditText.getContent()
        taskItem.agencyContacts = agencyContactsEditText.getContent()
        taskItem.agencyPhone =  agencyPhoneEditText.getContent()
    }

}