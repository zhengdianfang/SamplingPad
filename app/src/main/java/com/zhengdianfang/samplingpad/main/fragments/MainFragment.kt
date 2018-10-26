package com.zhengdianfang.samplingpad.main.fragments


import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_main.*
import me.yokeyword.fragmentation.SupportFragment

class MainFragment : BaseFragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    fun openDrawer() {
        drawerFrame.openDrawer(Gravity.START)
    }
}
