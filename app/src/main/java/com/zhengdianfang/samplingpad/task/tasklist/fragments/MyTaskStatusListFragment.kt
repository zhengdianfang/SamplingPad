package com.zhengdianfang.samplingpad.task.tasklist.fragments


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.BaseFragment
import com.zhengdianfang.samplingpad.common.tintDrawable
import com.zhengdianfang.samplingpad.food_product.FoodProductSamplingTableActivity
import com.zhengdianfang.samplingpad.network_product.NetworkProductSamplingTableActivity
import com.zhengdianfang.samplingpad.normal_product.NormalProductSamplingTableActivity
import kotlinx.android.synthetic.main.fragment_my_task_status_list.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import me.yokeyword.fragmentation.SupportFragment
import timber.log.Timber

class MyTaskStatusListFragment : BaseFragment() {

    private val taskStatusListFragmentViewModel by lazy { ViewModelProviders.of(this).get(MyTaskStatusListFragmentViewModel::class.java) }

    private val itemIds = arrayOf(
        R.id.qualifiedItem,
        R.id.disqualifiedItem,
        R.id.toBeCompletedItem,
        R.id.commitItem,
        R.id.verifyItem,
        R.id.refusesItem,
        R.id.cannotVerifyItem
    )

    private val itemLeftDrawableColors = arrayOf(
        R.color.green,
        R.color.red,
        R.color.yellow,
        R.color.blue,
        R.color.blue,
        R.color.red,
        R.color.colorLightGray
    )

    private val sectionIds = arrayOf(
        R.id.completedStatus,
        R.id.unCompleteStatus
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

    private fun bindViewModel() {
        taskStatusListFragmentViewModel.fetchStatusCount()
        taskStatusListFragmentViewModel.isLoadingLiveData.observe(this, Observer { isLoading ->
            if (isLoading == true) {
                startLoading()
            } else {
                stopLoading()
            }
        })

        taskStatusListFragmentViewModel.countLiveData.observe(this, Observer { statusCount ->
            Timber.d("statusCount : %s", statusCount.toString())
            commitItem.findViewById<TextView>(R.id.taskCountTextView).text = "${statusCount?.countSubmit ?: ""}"
            refusesItem.findViewById<TextView>(R.id.taskCountTextView).text = "${statusCount?.countRefuse?: ""}"
            cannotVerifyItem.findViewById<TextView>(R.id.taskCountTextView).text = "${statusCount?.countAbnormal?: ""}"
        })
    }

    private fun setupViews() {
        toolBarTitleView.setText(R.string.my_task_text)
        this.renderSections()
        this.renderItems()
    }

    private fun renderSections() {
        val statusSectionNames = resources.getStringArray(R.array.my_task_status_sections)
        sectionIds.forEachIndexed { index, viewId ->
            val statusSectionView = view?.findViewById<TextView>(viewId)!!
            statusSectionView.text = statusSectionNames[index]
        }
    }

    private fun renderItems() {
        val statusNames = resources.getStringArray(R.array.my_task_status_array)
        itemIds.forEachIndexed { index, frameId ->
            val itemView = view?.findViewById<View>(frameId)!!
            val statusNameTextView = itemView.findViewById<TextView>(R.id.statusNameTextView)
            statusNameTextView.text = statusNames[index]
            val color = ContextCompat.getColor(context!!, itemLeftDrawableColors[index])
            val leftDrawable =
                ContextCompat.getDrawable(context!!, R.drawable.my_task_item_left_drawable)!!.tintDrawable(color)
            statusNameTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(leftDrawable, null, null, null)

            val taskCountTextView = itemView.findViewById<TextView>(R.id.taskCountTextView)
            when(index) {
                0 -> {
                    taskCountTextView.text = "普通食品抽样单"
                    itemView.setOnClickListener {
                        startActivity(Intent(context, NormalProductSamplingTableActivity::class.java))
                    }
                }
                1 -> {
                    taskCountTextView.text = "网购食品抽样单"
                    itemView.setOnClickListener {
                        startActivity(Intent(context, NetworkProductSamplingTableActivity::class.java))
                    }
                }
                2 -> {
                    taskCountTextView.text = "食用农产品抽样单"
                    itemView.setOnClickListener {
                        startActivity(Intent(context, FoodProductSamplingTableActivity::class.java))
                    }
                }
            }

//            itemView.setOnClickListener {
//               start(TaskListWithStatusFragment.newInstance(it.context, statusNames[index]))
//            }
        }
    }

}
