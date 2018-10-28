package com.zhengdianfang.samplingpad.task.tasklist.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.BaseFragment
import com.zhengdianfang.samplingpad.common.ItemDecoration
import com.zhengdianfang.samplingpad.main.fragments.AllTaskItemAdapter
import com.zhengdianfang.samplingpad.task.canot_verify.CanotVerifyReasonActivity
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import com.zhengdianfang.samplingpad.task.entities.Task_Status
import com.zhengdianfang.samplingpad.task.entities.Task_Type
import com.zhengdianfang.samplingpad.task.food_product.FoodProductSamplingTableActivity
import com.zhengdianfang.samplingpad.task.network_product.NetworkProductSamplingTableActivity
import com.zhengdianfang.samplingpad.task.normal_product.NormalProductSamplingTableActivity
import kotlinx.android.synthetic.main.empty_view_layout.*
import kotlinx.android.synthetic.main.fragment_task_list_with_status_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class TaskListWithStatusFragment: BaseFragment() {

    private val taskListWithStatusFragmentViewModel by lazy { ViewModelProviders.of(this).get(TaskListWithStatusFragmentViewModel::class.java) }
    private val taskData = mutableListOf<TaskItem>()
    var statusName = ""
    var status = Task_Status.WAIT_VERIFY

    companion object {

        fun newInstance(statusName: String, status: Task_Status): TaskListWithStatusFragment {
            val fragment = TaskListWithStatusFragment()
            fragment.statusName = statusName
            fragment.status = status
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_task_list_with_status_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.setupViews()
        this.bindViewModel()
        this.autoRefresh()
    }

    private fun requestData() {
        if (status == Task_Status.CAN_NOT_VERIFY) {
            taskListWithStatusFragmentViewModel.loadErrorTaskDataByStatus()
        } else {
            taskListWithStatusFragmentViewModel.loadTaskDataByStatus(this.status)
        }
    }

    private fun autoRefresh() {
        refreshFrame.isRefreshing = true
        this.requestData()
    }

    private fun bindViewModel() {
        taskListWithStatusFragmentViewModel.taskListLiveData.observe(this, Observer { data ->
            taskData.clear()
            taskData.addAll(data!!)
            emptyView.visibility = if (taskData.size == 0) View.VISIBLE else View.GONE
            refreshFrame.isRefreshing = false
            taskRecyclerView.adapter.notifyDataSetChanged()
        })
    }

    private fun setupViews() {
        backButton.setOnClickListener {
            pop()
        }
        toolBarTitleView.text = this.statusName
        refreshFrame.setOnRefreshListener {
            this.requestData()
        }
        taskRecyclerView.addItemDecoration(ItemDecoration())
        val allTaskItemAdapter = AllTaskItemAdapter(taskData)
        allTaskItemAdapter.setOnItemChildClickListener { _, view, position ->
            if (view.id == R.id.operationButton2) {
                startActivity(Intent(context, CanotVerifyReasonActivity::class.java).putExtra("taskItem", taskData[position]))
            }
            if (view.id == R.id.operationButton1) {
                val task = taskData[position]
                when(task.foodTypeId) {
                    Task_Type.NORMAL_TASK.value -> startActivity(Intent(context, NormalProductSamplingTableActivity::class.java).putExtra("task", task))
                    Task_Type.FOOD_TASK.value -> startActivity(Intent(context, FoodProductSamplingTableActivity::class.java).putExtra("task", task))
                    Task_Type.NETWORK_TASK.value -> startActivity(Intent(context, NetworkProductSamplingTableActivity::class.java).putExtra("task", task))
                }
            }
        }
        taskRecyclerView.adapter = allTaskItemAdapter
    }
}