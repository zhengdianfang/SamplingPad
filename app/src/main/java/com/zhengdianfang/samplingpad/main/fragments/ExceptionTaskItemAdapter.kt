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
    : BaseQuickAdapter<TaskException, BaseViewHolder>(R.layout.task_item_layout, data) {


    override fun convert(helper: BaseViewHolder, item: TaskException) {
        this.renderBasicInfo(helper, item)
        this.renderMarkImageView(helper)

    }

    private fun renderBasicInfo(helper: BaseViewHolder, item: TaskException) {

    }

    private fun renderMarkImageView(helper: BaseViewHolder) {
        val imageView = helper.getView<ImageView>(R.id.markImageView)
        val imageColor = ContextCompat.getColor(imageView.context, R.color.red)
        imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, R.drawable.ic_task)!!.tintDrawable(imageColor))
    }


}