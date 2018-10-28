package com.zhengdianfang.samplingpad.task.network_product.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.TableFragment
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import kotlinx.android.synthetic.main.fragment_fifth_network_table_layout.*
import kotlinx.android.synthetic.main.fragment_first_normal_table_layout.*
import me.yokeyword.fragmentation.SupportFragment

class FifthTableFragment: TableFragment() {

    companion object {
        fun newInstance(taskItem: TaskItem): FifthTableFragment {
            val fragment = FifthTableFragment()
            val bundle = Bundle()
            bundle.putParcelable("task", taskItem)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_fifth_network_table_layout, container, false)
    }

    override fun setupViews() {
        super.setupViews()
        entrustLicenseNumberEditText.setEditTextContent(taskItem.entrustLicenseNumber)
        entrustNameEditText.setEditTextContent(taskItem.entrustName)
        entrustAddressEditText.setEditTextContent(taskItem.entrustAddress)
        entrustContactsEditText.setEditTextContent(taskItem.entrustContacts)
        entrustPhoneEditText.setEditTextContent(taskItem.entrustPhone)
    }

    override fun assembleSubmitTaskData() {
        taskItem.entrustLicenseNumber = entrustLicenseNumberEditText.getContent()
        taskItem.entrustName = entrustNameEditText.getContent()
        taskItem.entrustAddress = entrustAddressEditText.getContent()
        taskItem.entrustContacts = entrustContactsEditText.getContent()
        taskItem.entrustPhone = entrustPhoneEditText.getContent()
    }

    override fun submitSuccessful() {
        start(SixthTableFragment.newInstance(taskItem))
    }

}