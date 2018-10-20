package com.zhengdianfang.samplingpad.task.network_product.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhengdianfang.samplingpad.R
import me.yokeyword.fragmentation.SupportFragment

class SeventhTableFragment: SupportFragment() {

    companion object {
        fun newInstance() = SeventhTableFragment()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_seventh_normal_table_layout, container, false)
    }
}