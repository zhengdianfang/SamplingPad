package com.zhengdianfang.samplingpad.task.tasklist.fragments


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.BaseFragment
import com.zhengdianfang.samplingpad.common.tintDrawable
import com.zhengdianfang.samplingpad.main.fragments.TaskListFragment
import com.zhengdianfang.samplingpad.task.entities.Task_Status
import kotlinx.android.synthetic.main.fragment_my_task_status_list.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import timber.log.Timber

class MyTaskStatusListFragment : BaseFragment() {

    private val taskStatusListFragmentViewModel by lazy { ViewModelProviders.of(this).get(MyTaskStatusListFragmentViewModel::class.java) }

    private val itemIds = arrayOf(
        R.id.waitVerifyItem,
        R.id.cancelItem,
        R.id.completeItem,
        R.id.refusesItem,
        R.id.cannotVerifyItem
    )

    private val itemLeftDrawableColors = arrayOf(
        R.color.yellow,
        R.color.red,
        R.color.green,
        R.color.blue,
        R.color.colorDarkGray
    )

    companion object {

        fun newInstance() = MyTaskStatusListFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my_task_status_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.setupViews()
        this.bindViewModel()
    }

    override fun onResume() {
        super.onResume()
        taskStatusListFragmentViewModel.fetchStatusCount()
    }

    private fun bindViewModel() {
        taskStatusListFragmentViewModel.isLoadingLiveData.observe(this, Observer { isLoading ->
            if (isLoading == true) {
                startLoading()
            } else {
                stopLoading()
            }
        })

        taskStatusListFragmentViewModel.countLiveData.observe(this, Observer { statusCount ->
            Timber.d("statusCount : %s", statusCount.toString())
            waitVerifyItem.findViewById<TextView>(R.id.taskCountTextView).text = "${statusCount?.countAll ?: ""}"
            refusesItem.findViewById<TextView>(R.id.taskCountTextView).text = "${statusCount?.countRefuse ?: ""}"
            completeItem.findViewById<TextView>(R.id.taskCountTextView).text = "${statusCount?.countSubmit ?: ""}"
            cancelItem.findViewById<TextView>(R.id.taskCountTextView).text = "${statusCount?.retireCount?: ""}"
            cannotVerifyItem.findViewById<TextView>(R.id.taskCountTextView).text = "${statusCount?.countAbnormal ?: ""}"
        })
    }

    private fun setupViews() {
        toolBarTitleView.setText(R.string.my_task_text)
        this.renderItems()
        backButton.setOnClickListener {
            activity?.finish()
        }
    }

    private fun renderItems() {
        val statusNames = resources.getStringArray(R.array.my_task_status_array)
        Task_Status.values().forEachIndexed { index, task_Status ->
            val itemView = view?.findViewById<View>(itemIds[index])!!
            val statusNameTextView = itemView.findViewById<TextView>(R.id.statusNameTextView)
            statusNameTextView.text = statusNames[index]
            val color = ContextCompat.getColor(context!!, itemLeftDrawableColors[index])
            val leftDrawable =
                ContextCompat.getDrawable(context!!, R.drawable.my_task_item_left_drawable)!!.tintDrawable(color)
            statusNameTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(leftDrawable, null, null, null)
            itemView.setOnClickListener {
                if (task_Status.equals(Task_Status.CAN_NOT_VERIFY)) {
                   start(TaskListWithExceptionFragment.newInstance())
                } else {
                    start(TaskListFragment.newInstance(statusNames[index], task_Status))
                }
            }
        }
    }

}
