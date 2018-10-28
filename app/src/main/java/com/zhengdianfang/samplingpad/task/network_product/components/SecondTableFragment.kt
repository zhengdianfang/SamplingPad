package com.zhengdianfang.samplingpad.task.network_product.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.TableFragment
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import kotlinx.android.synthetic.main.fragment_first_normal_table_layout.*
import kotlinx.android.synthetic.main.fragment_second_network_table_layout.*
import me.yokeyword.fragmentation.SupportFragment

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
        return inflater.inflate(R.layout.fragment_second_network_table_layout, container, false)
    }

    override fun setupViews() {
        super.setupViews()

        thirdLicenseNumberEditText.setEditTextContent(taskItem.thirdLicenseNumber)
        thirdNameEditText.setEditTextContent(taskItem.thirdName)
        thirdPlatformNameEditText.setEditTextContent(taskItem.thirdPlatformName)
        thirdQsNoEditText.setEditTextContent(taskItem.thirdQsNo)
        thirdUrlEditText.setEditTextContent(taskItem.thirdUrl)
    }

    override fun submitSuccessful() {
        start(ThirdTableFragment.newInstance(taskItem))
    }

    override fun assembleSubmitTaskData() {
        taskItem.thirdLicenseNumber = thirdLicenseNumberEditText.getContent()
        taskItem.thirdName = thirdNameEditText.getContent()
        taskItem.thirdPlatformName = thirdPlatformNameEditText.getContent()
        taskItem.thirdQsNo = thirdQsNoEditText.getContent()
        taskItem.thirdUrl = thirdUrlEditText.getContent()
    }

}