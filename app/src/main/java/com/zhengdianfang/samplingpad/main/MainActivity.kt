package com.zhengdianfang.samplingpad.main

import android.os.Bundle
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.active
import kotlinx.android.synthetic.main.activity_main.*
import me.yokeyword.fragmentation.SupportActivity

class MainActivity : SupportActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.setupViews()
    }

    private fun setupViews() {
        homeTabBar.setOnCheckedChangeListener { _, buttonId ->
            when(buttonId) {
                R.id.verifyTab -> {
                    verifyTab.active(R.drawable.home_verify_tab_active_background, android.R.color.white)
                    taskTab.active(R.drawable.transparent, R.color.colorPrimary)
                }
                R.id.taskTab -> {
                    taskTab.active(R.drawable.home_task_tab_active_background, android.R.color.white)
                    verifyTab.active(R.drawable.transparent, R.color.colorPrimary)
                }
            }
        }
        homeTabBar.check(R.id.verifyTab)
    }
}
