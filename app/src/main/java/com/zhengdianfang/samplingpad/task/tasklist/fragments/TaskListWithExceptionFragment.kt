package com.zhengdianfang.samplingpad.task.tasklist.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.BaseFragment
import com.zhengdianfang.samplingpad.common.ItemDecoration
import com.zhengdianfang.samplingpad.main.fragments.ExceptionTaskItemAdapter
import com.zhengdianfang.samplingpad.task.entities.TaskException
import kotlinx.android.synthetic.main.empty_view_layout.*
import kotlinx.android.synthetic.main.fragment_task_list_with_status_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class TaskListWithExceptionFragment: BaseFragment() {

    private val taskListWithStatusFragmentViewModel by lazy { ViewModelProviders.of(this).get(TaskListWithStatusFragmentViewModel::class.java) }
    private val taskData = mutableListOf<TaskException>()

    companion object {

        fun newInstance(): TaskListWithExceptionFragment {
            return TaskListWithExceptionFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_task_list_with_status_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.setupViews()
        this.bindViewModel()
    }

    override fun onResume() {
        super.onResume()
        this.autoRefresh()
    }

    private fun requestData() {
        taskListWithStatusFragmentViewModel.loadErrorTaskDataByStatus()
    }

    private fun autoRefresh() {
        refreshFrame.isRefreshing = true
        this.requestData()
    }

    private fun bindViewModel() {
        taskListWithStatusFragmentViewModel.exceptionTaskListLiveData.observe(this, Observer { data ->
            taskData.clear()
            if (null != data) {
                taskData.addAll(data)
                emptyView.visibility = if (taskData.size == 0) View.VISIBLE else View.GONE
                taskRecyclerView.adapter.notifyDataSetChanged()
            }
            refreshFrame.isRefreshing = false
        })
    }

    private fun setupViews() {
        backButton.setOnClickListener {
            pop()
        }
        toolBarTitleView.text =  "无法抽样"
        refreshFrame.setOnRefreshListener {
            this.requestData()
        }
        taskRecyclerView.addItemDecoration(ItemDecoration())
        val taskItemAdapter = ExceptionTaskItemAdapter(taskData)
        taskRecyclerView.adapter = taskItemAdapter
    }
}