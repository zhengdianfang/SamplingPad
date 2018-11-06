package com.zhengdianfang.samplingpad.common


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide

import com.zhengdianfang.samplingpad.R
import kotlinx.android.synthetic.main.fragment_photo_preview.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class PhotoPreviewFragment : BaseFragment() {
    companion object {
        fun newInstance(url: String): PhotoPreviewFragment {
            val fragment = PhotoPreviewFragment()
            val bundle = Bundle()
            bundle.putString("url", url)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val url by lazy { arguments?.getString("url") ?: "" }
    private val configDialog by lazy {
        MaterialDialog.Builder(context!!)
            .content("确定删除此图片吗？")
            .positiveText("确定")
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
        Glide.with(context!!).load(url).into(photoView)
    }

}
