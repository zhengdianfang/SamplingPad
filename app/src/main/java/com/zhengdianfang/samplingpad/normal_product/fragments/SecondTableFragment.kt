package com.zhengdianfang.samplingpad.normal_product.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhengdianfang.samplingpad.R
import kotlinx.android.synthetic.main.fragment_second_normal_table_layout.*
import me.yokeyword.fragmentation.SupportFragment

class SecondTableFragment: SupportFragment() {

    companion object {
        fun newInstance() = SecondTableFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_second_normal_table_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        nextButtonButton.setOnClickListener {
            start(ThirdTableFragment.newInstance())
        }
    }
}