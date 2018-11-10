package com.zhengdianfang.samplingpad.task.normal_product.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.common.TableFragment
import com.zhengdianfang.samplingpad.common.components.BaseComponent
import com.zhengdianfang.samplingpad.common.entities.Region
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
        updateFrameVisibleStatus()
        //标称信息
        producerActiveRadioGroup.radioButtonCheckCallback = { _, option ->
            taskItem.producerActive = option.id
            if (option.id == 1) {
               taskItem.entrustActive = null
            }
            updateFrameVisibleStatus()
        }
        producerActiveRadioGroup.setDefaultCheckedRadioButton(taskItem.producerActive)
        produceCsNoEditText.setEditTextContent(taskItem.producerCsNo)
        produceNameEditText.setEditTextContent(taskItem.producerName)
        produceAddressEditText.setEditTextContent(taskItem.producerAddress)
        produceContactsEditText.setEditTextContent(taskItem.producerContacts)
        producePhoneEditText.setEditTextContent(taskItem.producerPhone)
        addressSpinner.labelTextView.text = "*生产所在地："
        addressSpinner.fetchData()
        addressSpinner.setDefault(
            Region(taskItem.producerProvincialId, taskItem.producerProvincialName),
            Region(taskItem.producerTownId, taskItem.producerTownName),
            Region(taskItem.producerCountyId, taskItem.producerCountyName))

        entrustActiveRadioGroup.radioButtonCheckCallback = { _, option ->
            taskItem.entrustActive = option.id
            updateFrameVisibleStatus()
        }
        entrustActiveRadioGroup.setDefaultCheckedRadioButton(taskItem.entrustActive)

        //委托单位信息
        entrustCsNoEditText.setEditTextContent(taskItem.entrustCsNo)
        entrustNameEditText.setEditTextContent(taskItem.entrustName)
        entrustAddressEditText.setEditTextContent(taskItem.entrustAddress)
        entrustContactsEditText.setEditTextContent(taskItem.entrustContacts)
        entrustPhoneEditText.setEditTextContent(taskItem.entrustPhone)
        addressSpinnerGroupView.labelTextView.text = "*生产所在地："
        addressSpinnerGroupView.fetchData()
        addressSpinnerGroupView.setDefault(
            Region(taskItem.entrustProvincialId, taskItem.entrustProvincialName),
            Region(taskItem.entrustTownId, taskItem.entrustTownName),
            Region(taskItem.entrustCountyId, taskItem.entrustCountyName))

        //进口代理商信息
        agencyNameEditText.setEditTextContent(taskItem.agencyName)
        agencyAddressEditText.setEditTextContent(taskItem.agencyAddress)
        agencyContactsEditText.setEditTextContent(taskItem.agencyContacts)
        agencyPhoneEditText.setEditTextContent(taskItem.agencyPhone)
        resourceSpinnerGroupView.fetchData("${ApiClient.HOST}app/areas/origin")

    }

    private fun updateFrameVisibleStatus() {
        showHideProduceFrame(isShowProducerViews())
        showHideEntrustFrame(isShowEntrustViews())
        showHideAgencyFrame(isShowAgencyViews())
    }

    private fun showHideProduceFrame(visible: Boolean) {
        for (index in 0 until produceFrame.childCount) {
            if (index > 1) {
                val childrenView = produceFrame.getChildAt(index)
                if (childrenView is BaseComponent) {
                    if (visible) {
                        childrenView.visibility = View.VISIBLE
                    } else {
                        childrenView.visibility = View.GONE
                        childrenView.clear()
                    }
                }
            }
        }
    }

    private fun showHideEntrustFrame(visible: Boolean) {
        if (visible) {
            entrustFrame.visibility = View.VISIBLE
        } else {
            entrustFrame.visibility = View.GONE
            for (index in 0 until entrustFrame.childCount) {
                val childrenView = entrustFrame.getChildAt(index)
                if (childrenView is BaseComponent) {
                    childrenView.clear()
                }
            }
        }
    }

    private fun showHideAgencyFrame(visible: Boolean) {
        if (visible) {
            agencyFrame.visibility = View.VISIBLE
        } else {
            agencyFrame.visibility = View.GONE
            for (index in 0 until agencyFrame.childCount) {
                val childrenView = agencyFrame.getChildAt(index)
                if (childrenView is BaseComponent) {
                    childrenView.clear()
                }
            }
        }
    }


    private fun isShowProducerViews() =
        taskItem.producerActive == null || taskItem.producerActive  == 0

    private fun isShowEntrustViews() =
        (taskItem.producerActive == null || taskItem.producerActive  == 0) && taskItem.entrustActive == 1

    private fun isShowAgencyViews() = taskItem.producerActive == 1

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
        taskItem.setProducerAddressInfo(addressSpinner.selectedProvince, addressSpinner.selectedTown, addressSpinner.selectedCounty)

        //委托单位信息
        taskItem.entrustCsNo = entrustCsNoEditText.getContent()
        taskItem.entrustName = entrustNameEditText.getContent()
        taskItem.entrustAddress = entrustAddressEditText.getContent()
        taskItem.entrustContacts = entrustContactsEditText.getContent()
        taskItem.entrustPhone = entrustPhoneEditText.getContent()
        taskItem.entrustActive = entrustActiveRadioGroup.getCheckedOption()?.id
        taskItem.setEnstrustAddressInfo(addressSpinnerGroupView.selectedProvince, addressSpinnerGroupView.selectedTown, addressSpinnerGroupView.selectedCounty)

        //进口代理商信息
        taskItem.agencyName = agencyNameEditText.getContent()
        taskItem.agencyAddress = agencyAddressEditText.getContent()
        taskItem.agencyContacts = agencyContactsEditText.getContent()
        taskItem.agencyPhone =  agencyPhoneEditText.getContent()
    }

}