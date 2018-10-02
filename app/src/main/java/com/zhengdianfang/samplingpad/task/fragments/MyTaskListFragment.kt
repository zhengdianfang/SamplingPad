package com.zhengdianfang.samplingpad.task.fragments


import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.widget.DrawableUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.tintDrawable
import me.yokeyword.fragmentation.SupportFragment

class MyTaskListFragment : SupportFragment() {

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

        fun newInstance() = MyTaskListFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my_task_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.setupViews()
    }

    private fun setupViews() {
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


            // TODO setup task count
        }
    }

}
