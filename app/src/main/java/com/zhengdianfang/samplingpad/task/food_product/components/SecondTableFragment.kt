package com.zhengdianfang.samplingpad.task.food_product.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.task.entities.TaskItem

class SecondTableFragment: com.zhengdianfang.samplingpad.task.normal_product.fragments.SecondTableFragment() {
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

    override fun submitSuccessful() {
        start(ThirdTableFragment.newInstance(taskItem))
    }
}