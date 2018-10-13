package com.zhengdianfang.samplingpad.network_product.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhengdianfang.samplingpad.R
import kotlinx.android.synthetic.main.fragment_first_normal_table_layout.*
import me.yokeyword.fragmentation.SupportFragment

class FifthTableFragment: SupportFragment() {
    companion object {
        fun newInstance() = FifthTableFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_fifth_network_table_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        nextButtonButton.setOnClickListener {
            start(SixthTableFragment.newInstance())
        }
    }
}