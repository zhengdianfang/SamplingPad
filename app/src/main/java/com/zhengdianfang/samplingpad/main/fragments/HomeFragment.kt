package com.zhengdianfang.samplingpad.main.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.BaseFragment
import com.zhengdianfang.samplingpad.common.active
import kotlinx.android.synthetic.main.fragment_home.*
import me.yokeyword.fragmentation.SupportFragment

class HomeFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        val verifyFragment = findChildFragment(VerifyFragment::class.java)
        val taskListFragment = findChildFragment(TaskListFragment::class.java)
        homeTabBar.setOnCheckedChangeListener { _, buttonId ->
            when (buttonId) {
                R.id.verifyTab -> {
                    verifyTab.active(R.drawable.home_verify_tab_active_background, android.R.color.white)
                    taskTab.active(R.drawable.transparent, R.color.colorPrimary)
                    showHideFragment(verifyFragment, taskListFragment)
                }
                R.id.taskTab -> {
                    taskTab.active(R.drawable.home_task_tab_active_background, android.R.color.white)
                    verifyTab.active(R.drawable.transparent, R.color.colorPrimary)
                    showHideFragment(taskListFragment, verifyFragment)
                }
            }
        }
        homeTabBar.check(R.id.verifyTab)

        userSettingButton.setOnClickListener {
            if (parentFragment is MainFragment) {
                (parentFragment as? MainFragment)?.openDrawer()
            }
        }
    }
}
