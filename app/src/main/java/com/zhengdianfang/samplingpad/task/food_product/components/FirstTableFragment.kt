package com.zhengdianfang.samplingpad.task.food_product.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import kotlinx.android.synthetic.main.fragment_first_food_table_layout.*
import me.yokeyword.fragmentation.SupportFragment

class FirstTableFragment: com.zhengdianfang.samplingpad.task.normal_product.fragments.FirstTableFragment() {
    companion object {
        fun newInstance(taskItem: TaskItem): FirstTableFragment {
            val fragment = FirstTableFragment()
            val bundle = Bundle()
            bundle.putParcelable("task", taskItem)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_first_normal_table_layout, container, false)
    }

    override fun submitSuccessful() {
        start(SecondTableFragment.newInstance(taskItem))
    }
}