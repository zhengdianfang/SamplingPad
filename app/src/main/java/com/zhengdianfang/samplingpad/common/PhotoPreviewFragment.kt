package com.zhengdianfang.samplingpad.common


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide

import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.task.api.TaskApi
import com.zhengdianfang.samplingpad.task.normal_product.fragments.TableFragmentViewModel
import kotlinx.android.synthetic.main.fragment_photo_preview.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class PhotoPreviewFragment : BaseFragment() {
    companion object {
        fun newInstance(id: Int): PhotoPreviewFragment {
            val fragment = PhotoPreviewFragment()
            val bundle = Bundle()
            bundle.putInt("id", id)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val attachmentId  by lazy { arguments?.getInt("id")!! }
    private val tableFragmentViewModel by lazy { ViewModelProviders.of(this).get(TableFragmentViewModel::class.java) }
    private val configDialog by lazy {
        MaterialDialog.Builder(context!!)
            .content("确定删除此图片吗？")
            .positiveText("确定")
            .onPositive { _, _ ->
                tableFragmentViewModel.deleteAttachments("$attachmentId")
            }
            .negativeText("取消")
            .build()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo_preview, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        deleteButton.setOnClickListener {
            configDialog.show()
        }
        photoView.enable()
        photoView.scaleType = ImageView.ScaleType.CENTER_INSIDE;
        Glide.with(context!!).load("${ApiClient.getHost()}${TaskApi.ATTACHMENT_URL}$attachmentId").into(photoView)
        tableFragmentViewModel.deleteAttachmentLiveData.observe(this, Observer {
            if (it == true) {
                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show()
                pop()
            }
        })
    }

}
