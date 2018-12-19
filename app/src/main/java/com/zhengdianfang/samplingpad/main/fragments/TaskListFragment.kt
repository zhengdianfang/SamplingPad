package com.zhengdianfang.samplingpad.main.fragments


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.BaseFragment
import com.zhengdianfang.samplingpad.common.ItemDecoration
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import com.zhengdianfang.samplingpad.task.entities.Task_Status
import com.zhengdianfang.samplingpad.task.entities.Task_Type
import com.zhengdianfang.samplingpad.task.food_product.FoodProductSamplingTableActivity
import com.zhengdianfang.samplingpad.task.network_product.NetworkProductSamplingTableActivity
import com.zhengdianfang.samplingpad.task.normal_product.NormalProductSamplingTableActivity
import kotlinx.android.synthetic.main.empty_view_layout.*
import kotlinx.android.synthetic.main.fragment_task_list.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class TaskListFragment : BaseFragment() {
    private val taskListFragmentViewModel by lazy { ViewModelProviders.of(this).get(TaskListFragmentViewModel::class.java) }
    private val taskData = mutableListOf<TaskItem>()

    var status = Task_Status.WAIT_VERIFY
    var statusName = ""

    companion object {
        fun newInstance(statusName: String, status: Task_Status): TaskListFragment {
            val fragment = TaskListFragment()
            fragment.statusName = statusName
            fragment.status = status
            return fragment
        }
    }

    private val giveUpDialog by lazy {
        MaterialDialog.Builder(context!!)
            .positiveText("确定")
            .negativeText("取消")
            .title("放弃任务")
            .onPositive { dialog, _ ->
                if (this.clickPosition >= 0) {
                    stopLoading()
                    if (taskData[this.clickPosition].code != null) {
                        taskListFragmentViewModel.giveUpTask(taskData[this.clickPosition].code ?: "")
                    }
                }
                dialog.dismiss()
            }.build()
    }

    private val seizeDialog by lazy {
        MaterialDialog.Builder(context!!)
            .positiveText("确定")
            .negativeText("取消")
            .title("抽样单被认领")
            .content("该抽样单已被领用，请刷新后更换其他抽样单进行填报")
    }

    private var clickPosition = -1

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
        taskListFragmentViewModel.loadTaskData(status)
    }

    private fun bindViewModel() {
        taskListFragmentViewModel.taskListLiveData.observe(this, Observer { data ->
            if (data != null) {
                searchBar.updateTaskList(data)
                emptyView.visibility = if (data.size == 0) View.VISIBLE else View.GONE
            }
            refreshFrame.isRefreshing = false
        })

        taskListFragmentViewModel.giveUpLiveData.observe(this, Observer { success ->
            stopLoading()
            if (success == true) {
                autoRefresh()
                Toast.makeText(context, "操作成功", Toast.LENGTH_SHORT).show()
            }
            this.clickPosition = -1
        })

        taskListFragmentViewModel.sampleSeizeLiveData.observe(this, Observer { isNotSeized ->
            stopLoading()
            if (isNotSeized == true) {
                val task = taskData[this.clickPosition]
                when(task.foodTypeId) {
                    Task_Type.NORMAL_TASK.value -> startActivity(Intent(context, NormalProductSamplingTableActivity::class.java).putExtra("task", task))
                    Task_Type.FOOD_TASK.value -> startActivity(Intent(context, FoodProductSamplingTableActivity::class.java).putExtra("task", task))
                    Task_Type.NETWORK_TASK.value -> startActivity(Intent(context, NetworkProductSamplingTableActivity::class.java).putExtra("task", task))
                }
            } else {
                seizeDialog.show()
            }
            this.clickPosition = -1
        })
    }

    private fun openSampleDetailPage(task: TaskItem) {
        when(task.foodTypeId) {
            Task_Type.NORMAL_TASK.value -> startActivity(Intent(context, NormalProductSamplingTableActivity::class.java).putExtra("task", task))
            Task_Type.FOOD_TASK.value -> startActivity(Intent(context, FoodProductSamplingTableActivity::class.java).putExtra("task", task))
            Task_Type.NETWORK_TASK.value -> startActivity(Intent(context, NetworkProductSamplingTableActivity::class.java).putExtra("task", task))
        }
    }


    private fun startVerify(task: TaskItem) {
       if (task.isMyTask()) {
          this.openSampleDetailPage(task)
       } else {
           MaterialDialog.Builder(context!!)
                       .title("领取任务")
                       .content("领取任务后，其他人不可领取")
                       .positiveText("确定")
                       .negativeText("取消")
                       .onPositive { dialog, _ ->
                           if (task.code != null) {
                               startLoading()
                               taskListFragmentViewModel.fetchSampleSeize(task.code!!)
                           }
                           dialog.cancel()
                       }
                       .show()
       }
    }

    private fun setupViews() {
        refreshFrame.setOnRefreshListener {
            taskListFragmentViewModel.loadTaskData(this.status)
        }
        if (TextUtils.isEmpty(this.statusName)) {
            toolbar.visibility = View.GONE
            toolBarTitleView.text = this.statusName
            backButton?.setOnClickListener {
                pop()
            }

        } else {
            toolbar.visibility = View.VISIBLE
        }
        taskRecyclerView.addItemDecoration(ItemDecoration())
        val allTaskItemAdapter = AllTaskItemAdapter(taskData)
        allTaskItemAdapter.setOnItemChildClickListener { _, view, position ->
            this.clickPosition = position
            if (view.id == R.id.operationButton1) {
                val task = taskData[position]
                this.startVerify(task)
            } else if (view.id == R.id.operationButton2) {
                giveUpDialog.show()
            }
        }
        taskRecyclerView.adapter = allTaskItemAdapter

        searchBar.filterTaskCallback = {
            taskData.clear()
            taskData.addAll(it)
            allTaskItemAdapter.notifyDataSetChanged()
        }
    }
}
