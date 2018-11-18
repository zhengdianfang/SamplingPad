package com.zhengdianfang.samplingpad.main.fragments

import android.support.v4.content.ContextCompat
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.tintDrawable
import com.zhengdianfang.samplingpad.task.entities.TaskException
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import com.zhengdianfang.samplingpad.task.entities.Task_Status

class ExceptionTaskItemAdapter(data: MutableList<TaskException>)
    : BaseQuickAdapter<TaskException, BaseViewHolder>(R.layout.exception_task_item_layout, data) {


    override fun convert(helper: BaseViewHolder, item: TaskException) {
        this.renderBasicInfo(helper, item)
        this.renderMarkImageView(helper)

    }

    private fun renderBasicInfo(helper: BaseViewHolder, item: TaskException) {
        helper.setText(R.id.taskNoTextView, "任务号：${item.task_no}")
            .setText(R.id.enterpriseNameView, "单位名称：${item.enterprise_name}")
            .setText(R.id.enterprisePlaceView, "行政区域：${item.enterprise_area_name}")
            .setText(R.id.timeView, item.create_date)
            .setText(R.id.sampleNameView, "样品名称：${item.want_sample}")
            .setText(R.id.reasonTextView, item.abnormal_type_name)
    }

    private fun renderMarkImageView(helper: BaseViewHolder) {
        val imageView = helper.getView<ImageView>(R.id.markImageView)
        val imageColor = ContextCompat.getColor(imageView.context, R.color.red)
        imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, R.drawable.ic_task)!!.tintDrawable(imageColor))
    }


}