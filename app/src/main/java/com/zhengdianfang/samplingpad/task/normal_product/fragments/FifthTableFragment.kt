package com.zhengdianfang.samplingpad.task.normal_product.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhengdianfang.samplingpad.App
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.BaseFragment
import com.zhengdianfang.samplingpad.common.TableFragment
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import kotlinx.android.synthetic.main.fragment_fifth_normal_table_layout.*
import me.yokeyword.fragmentation.SupportFragment

open class FifthTableFragment: TableFragment() {

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
        return inflater.inflate(R.layout.fragment_fifth_normal_table_layout, container, false)
    }

    override fun setupViews() {
        saveOnlyButton.setOnClickListener {
            tableFragmentViewModel.saveSample(taskItem)
        }

        submitButton.setOnClickListener {
            tableFragmentViewModel.submitSample(taskItem)
        }
    }
    override fun submitSuccessful() {
        activity?.finish()
    }

    override fun assembleSubmitTaskData() {
    }

}