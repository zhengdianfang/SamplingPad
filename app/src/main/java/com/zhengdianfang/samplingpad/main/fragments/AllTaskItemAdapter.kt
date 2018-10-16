package com.zhengdianfang.samplingpad.main.fragments

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.task.entities.TaskItem

class AllTaskItemAdapter(data: MutableList<TaskItem>)
    : BaseQuickAdapter<TaskItem, BaseViewHolder>(R.layout.task_item_layout, data) {

    override fun convert(helper: BaseViewHolder?, item: TaskItem?) {
    }
}