package com.zhengdianfang.samplingpad.normal_product.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhengdianfang.samplingpad.R
import me.yokeyword.fragmentation.SupportFragment

class FifthTableFragment: SupportFragment() {

    companion object {
        fun newInstance() = FifthTableFragment()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_fifth_normal_table_layout, container, false)
    }
}