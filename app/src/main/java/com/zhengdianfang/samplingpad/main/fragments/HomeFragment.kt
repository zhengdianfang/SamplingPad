package com.zhengdianfang.samplingpad.main.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.zhengdianfang.samplingpad.R
import me.yokeyword.fragmentation.SupportFragment

class HomeFragment : SupportFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

}
