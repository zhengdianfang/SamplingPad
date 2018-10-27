package com.zhengdianfang.samplingpad.main.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.amap.api.services.cloud.CloudSearch
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.core.PoiItem
import com.amap.api.services.poisearch.PoiResult
import com.amap.api.services.poisearch.PoiSearch
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zhengdianfang.samplingpad.App

import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.BaseFragment
import com.zhengdianfang.samplingpad.common.searchPoiByText
import kotlinx.android.synthetic.main.fragment_verify_result.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class VerifyResultFragment : BaseFragment() {

    private val isSuccessFul by lazy { arguments?.getBoolean("isSuccessFul") ?: false }
    private val message by lazy { arguments?.getString("message") ?: "" }

    companion object {
        fun newInstance(isSuccessFul: Boolean, message: String): VerifyResultFragment {
            val fragment = VerifyResultFragment()
            val bundle = Bundle()
            bundle.putString("message", message)
            bundle.putBoolean("isSuccessFul", isSuccessFul)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verify_result, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
        this.poiSearch("超市")
    }

    private fun setupViews() {
        toolBarTitleView.text = "检验结果"
        backButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
        if (isSuccessFul) {
            resultTitleTextView.text = "校验通过"
            resultMarkImageView.setImageResource(R.drawable.ic_ok)
        } else {
            resultTitleTextView.text = "校验不通过"
            resultMarkImageView.setImageResource(R.drawable.ic_error)
        }

        resultMessageTextView.text = message
    }

    private fun poiSearch(keyWord: String) {
        if (keyWord.isNotEmpty()) {
            keyWord.searchPoiByText(context!!) {
                shopListView.adapter = ShopNameAdapter(it?.asSequence()?.map { it.title }?.take(20)?.toMutableList())
            }
        }
    }

    private inner class ShopNameAdapter(data: MutableList<String>?)
        : BaseQuickAdapter<String, BaseViewHolder>(R.layout.shop_item_layout, data) {
        override fun convert(helper: BaseViewHolder, item: String) {
            (helper.itemView as TextView).text = item
        }
    }

}
