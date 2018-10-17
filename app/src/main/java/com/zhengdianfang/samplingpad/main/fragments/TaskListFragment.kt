package com.zhengdianfang.samplingpad.main.fragments


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.BaseFragment
import com.zhengdianfang.samplingpad.common.ItemDecoration
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import kotlinx.android.synthetic.main.fragment_task_list.*

class TaskListFragment : BaseFragment() {

    private val taskListFragmentViewModel by lazy { ViewModelProviders.of(this).get(TaskListFragmentViewModel::class.java) }
    private val taskData = mutableListOf<TaskItem>()
    private val refreshFrame by lazy { view as SwipeRefreshLayout }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
        bindViewModel()
        autoRefresh()
    }

    private fun autoRefresh() {
        refreshFrame.isRefreshing = true
        taskListFragmentViewModel.loadTaskData()
    }

    private fun bindViewModel() {
        taskListFragmentViewModel.taskListLiveData.observe(this, Observer { data ->
            taskRecyclerView.adapter.notifyDataSetChanged()
            taskData.clear()
            taskData.addAll(data!!)
            refreshFrame.isRefreshing = false
        })
    }

    private fun setupViews() {
        refreshFrame.setOnRefreshListener {
            taskListFragmentViewModel.loadTaskData()
        }
        taskRecyclerView.addItemDecoration(ItemDecoration())
        val allTaskItemAdapter = AllTaskItemAdapter(taskData)
        taskRecyclerView.adapter = allTaskItemAdapter
    }
}
