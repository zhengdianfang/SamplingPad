package com.zhengdianfang.samplingpad.main.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.normal_product.NormalProductSamplingTableActivity
import kotlinx.android.synthetic.main.fragment_verfiy_layout.*
import me.yokeyword.fragmentation.SupportFragment

class VerfiyFragment : SupportFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_verfiy_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        verifyButton.setOnClickListener {
            startActivity(Intent(context, NormalProductSamplingTableActivity::class.java))
        }
    }
}
