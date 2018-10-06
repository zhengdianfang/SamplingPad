package com.zhengdianfang.samplingpad.main.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.task.MyTaskListActivity
import kotlinx.android.synthetic.main.fragment_navigation.*
import me.yokeyword.fragmentation.SupportFragment

class NavigationFragment : SupportFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.setupViews()
    }

    private fun setupViews() {
        appVersionTextView.text = context?.packageManager?.getPackageInfo(context?.packageName, 0)?.versionName
        myTaskListButton.setOnClickListener {
            startActivity(Intent(context, MyTaskListActivity::class.java))
        }
    }
}
