package com.zhengdianfang.samplingpad.main.fragments

import android.support.v4.content.ContextCompat
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.tintDrawable
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import com.zhengdianfang.samplingpad.task.entities.Task_Status

class AllTaskItemAdapter(data: MutableList<TaskItem>)
    : BaseQuickAdapter<TaskItem, BaseViewHolder>(R.layout.task_item_layout, data) {

    private val drawableColorMap = mapOf(
        Pair(Task_Status.WAIT_VERIFY.value, R.color.yellow),
        Pair(Task_Status.CANCEL.value, R.color.colorDarkGray),
        Pair(Task_Status.COMPLETE.value, R.color.green),
        Pair(Task_Status.REFUSE.value, R.color.blue),
        Pair(Task_Status.CAN_NOT_VERIFY.value, R.color.red)
    )

    override fun convert(helper: BaseViewHolder, item: TaskItem) {
        this.renderBasicInfo(helper, item)
        this.renderOperationButtons(helper, item)
        this.renderMarkImageView(helper, item)

    }

    private fun renderBasicInfo(helper: BaseViewHolder, item: TaskItem) {
        helper.setText(R.id.taskCodetextView, "单号：${item.code}")
        helper.setText(R.id.dateTextView, "更新时间：${item.updateDate ?: ""}")
        helper.setText(R.id.nameTextView, "样品名称：${item.sampleName ?: ""}")
        helper.setText(R.id.level1CategoryTextView, "食品大类：${item.level1Name ?: ""}")
        helper.setText(R.id.level2CategroyTextView, "食品亚类：${item.level2Name ?: ""}")
        helper.setText(R.id.level3CategoryTextView, "食品比亚类：${item.level3Name ?: ""}")
        helper.setText(R.id.level4CategoryTextView, "食品细类：${item.level4Name ?: ""}")
        helper.setText(R.id.addressTextView, "抽样地点：${item.sampleLinkName ?: ""}")
        helper.setText(R.id.spaceTextView, "场所名称：${item.enterpriseName ?: ""}")
        helper.addOnClickListener(R.id.operationButton1)
        helper.addOnClickListener(R.id.operationButton2)
    }

    private fun renderMarkImageView(helper: BaseViewHolder, item: TaskItem) {
        val imageView = helper.getView<ImageView>(R.id.markImageView)
        val imageColor = ContextCompat.getColor(imageView.context, drawableColorMap[item.state] ?: R.color.green)
        imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, R.drawable.ic_task)!!.tintDrawable(imageColor))
    }


    private fun renderOperationButtons(helper: BaseViewHolder, item: TaskItem) {
        when (item.state) {
            Task_Status.WAIT_VERIFY.value -> {
                helper.setVisible(R.id.operationButton1, true)
                helper.setVisible(R.id.operationButton2, true)
            }
            Task_Status.CANCEL.value -> {
                helper.setVisible(R.id.operationButton1, true)
                helper.setVisible(R.id.operationButton2, true)
            }
            else -> {
                helper.setVisible(R.id.operationButton1, false)
                helper.setVisible(R.id.operationButton2, false)
            }
        }
    }
}