package com.zhengdianfang.samplingpad.task.normal_product.fragments

import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.task.api.TaskApi
import com.zhengdianfang.samplingpad.task.entities.AttachmentItem

class AttachmentAdapter(data: List<MultiItemEntity>, private val type: Int) : BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(data) {

    init {
        addItemType(0, R.layout.image_item_layout)
        addItemType(1, R.layout.upload_item_layout)
    }

    override fun convert(helper: BaseViewHolder, item: MultiItemEntity) {
        if (item.itemType == 0 && item is AttachmentItem) {
            if (item.documentType == ".pdf") {
                Glide.with(helper.itemView.context).load(R.drawable.ic_pdf).into(helper.itemView.findViewById(R.id.imageView) as ImageView)
            } else {
                Glide.with(helper.itemView.context)
                    .load("${ApiClient.HOST}${TaskApi.ATTACHMENT_URL}${item.id}")
                    .apply(RequestOptions().placeholder(R.drawable.verify_code_default_pic))
                    .into(helper.itemView.findViewById(R.id.imageView) as ImageView)
            }
        } else if(item.itemType == 1) {
            (helper.itemView as TextView).text = if (type == 0) "上传图片" else "上传视频"
        }
    }

}