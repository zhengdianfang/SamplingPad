package com.zhengdianfang.samplingpad.task.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhengdianfang.samplingpad.R
import kotlinx.android.synthetic.main.toolbar_layout.*
import me.yokeyword.fragmentation.SupportFragment

class TaskListWithStatusFragment: SupportFragment() {

    companion object {

        const val BUNDLE_ARGUMENT_STATUS_NAME = "statusName"

        fun newInstance(context: Context, statusName: String): TaskListWithStatusFragment {
            val bundle = Bundle()
            bundle.putString(BUNDLE_ARGUMENT_STATUS_NAME, statusName)
            return Fragment.instantiate(context, TaskListWithStatusFragment::class.java.name, bundle)
                as TaskListWithStatusFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_task_list_with_status_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.setupViews()
    }

    private fun setupViews() {
        backButton.setOnClickListener {
            pop()
        }
        toolBarTitleView.text = arguments?.getString(BUNDLE_ARGUMENT_STATUS_NAME)
    }
}