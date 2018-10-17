package com.zhengdianfang.samplingpad.main.fragments

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import com.zhengdianfang.samplingpad.task.entities.Task_Status

class AllTaskItemAdapter(data: MutableList<TaskItem>)
    : BaseQuickAdapter<TaskItem, BaseViewHolder>(R.layout.task_item_layout, data) {

    override fun convert(helper: BaseViewHolder, item: TaskItem) {
        renderBasicInfo(helper, item)
        this.renderOperationButtons(helper, item)

    }

    private fun renderBasicInfo(helper: BaseViewHolder, item: TaskItem) {
        helper.setText(R.id.taskCodetextView, "单号：${item.code}")
        helper.setText(R.id.dateTextView, "更新时间：${item.updateDate}")
        helper.setText(R.id.nameTextView, "样品名称：${item.sampleName}")
        helper.setText(R.id.level1CategoryTextView, "食品大类：${item.level1Name}")
        helper.setText(R.id.level2CategroyTextView, "食品亚类：${item.level2Name}")
        helper.setText(R.id.level3CategoryTextView, "食品比亚类：${item.level3Name}")
        helper.setText(R.id.level4CategoryTextView, "食品细类：${item.level4Name}")
        helper.setText(R.id.addressTextView, "抽样地点：${item.sampleLinkName}")
        helper.setText(R.id.spaceTextView, "场所名称：${item.enterpriseName}")
    }

    private fun renderOperationButtons(helper: BaseViewHolder, item: TaskItem) {
        when (item.state) {
            Task_Status.COMPLETE.status -> {
                helper.setVisible(R.id.startVerifyButton, false)
                helper.setVisible(R.id.cannotVerifyButton, false)
            }
            else -> {
                helper.setVisible(R.id.startVerifyButton, false)
                helper.setVisible(R.id.cannotVerifyButton, false)
            }
        }
    }
}