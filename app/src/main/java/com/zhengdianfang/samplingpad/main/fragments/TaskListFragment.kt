package com.zhengdianfang.samplingpad.main.fragments


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.BaseFragment
import com.zhengdianfang.samplingpad.common.ItemDecoration
import com.zhengdianfang.samplingpad.task.canot_verify.CanotVerifyReasonActivity
import com.zhengdianfang.samplingpad.task.food_product.FoodProductSamplingTableActivity
import com.zhengdianfang.samplingpad.task.network_product.NetworkProductSamplingTableActivity
import com.zhengdianfang.samplingpad.task.normal_product.NormalProductSamplingTableActivity
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import com.zhengdianfang.samplingpad.task.entities.Task_Type
import kotlinx.android.synthetic.main.empty_view_layout.*
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
    }

    override fun onResume() {
        super.onResume()
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
            emptyView.visibility = if (taskData.size == 0) View.VISIBLE else View.GONE
            refreshFrame.isRefreshing = false
        })
    }

    private fun setupViews() {
        refreshFrame.setOnRefreshListener {
            taskListFragmentViewModel.loadTaskData()
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
