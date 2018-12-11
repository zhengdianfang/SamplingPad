package com.zhengdianfang.samplingpad.task.network_product.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.TableFragment
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import kotlinx.android.synthetic.main.fragment_first_normal_table_layout.*
import kotlinx.android.synthetic.main.fragment_fourth_network_table_layout.*
import me.yokeyword.fragmentation.SupportFragment

class FourthTableFragment: TableFragment() {

    companion object {
        fun newInstance(taskItem: TaskItem): FourthTableFragment {
            val fragment = FourthTableFragment()
            val bundle = Bundle()
            bundle.putParcelable("task", taskItem)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_fourth_network_table_layout, container, false)
    }

    override fun setupViews() {
        super.setupViews()
        val yesNoArray = resources.getStringArray(R.array.yes_or_no)
        if (taskItem.sampleActive != null) {
            producerActiveRadioGroup.setDefaultCheckedRadioButton(yesNoArray[taskItem.sampleActive!!])
        }
        producerCsNoEditText.setEditTextContent(taskItem.producerCsNo)
        producerNameEditText.setEditTextContent(taskItem.producerName)
        producerAddressEditText.setEditTextContent(taskItem.producerAddress)
        producerContactsEditText.setEditTextContent(taskItem.producerPhone)
        producerPhoneEditText.setEditTextContent(taskItem.producerPhone)
    }

    override fun submitSuccessful() {
        start(FifthTableFragment.newInstance(taskItem))
    }

    override fun assembleSubmitTaskData() {
        val yesNoArray = resources.getStringArray(R.array.yes_or_no)
        val index = yesNoArray.indexOf(producerActiveRadioGroup.getCheckedText())
        taskItem.sampleActive = if(index >= 0) index else null
        taskItem.producerCsNo = producerCsNoEditText.getContent()
        taskItem.producerName = producerNameEditText.getContent()
        taskItem.producerAddress = producerAddressEditText.getContent()
        taskItem.producerPhone = producerContactsEditText.getContent()
        taskItem.producerPhone = producerPhoneEditText.getContent()
    }

}