package com.zhengdianfang.samplingpad.main.fragments


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.zhengdianfang.samplingpad.App

import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.BaseFragment
import com.zhengdianfang.samplingpad.main.MainActivity
import com.zhengdianfang.samplingpad.task.canot_verify.CanotVerifyReasonActivity
import com.zhengdianfang.samplingpad.task.food_product.FoodProductSamplingTableActivity
import com.zhengdianfang.samplingpad.task.tasklist.ExceptionTaskListActivity
import com.zhengdianfang.samplingpad.task.tasklist.MyTaskListActivity
import com.zhengdianfang.samplingpad.task.tasklist.fragments.TaskListWithExceptionFragment
import kotlinx.android.synthetic.main.fragment_navigation.*
import me.yokeyword.fragmentation.SupportFragment

class NavigationFragment : BaseFragment() {

    private val navigationFragmentViewModel by lazy { ViewModelProviders.of(this).get(NavigationFragmentViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.setupViews()
        this.bindViewModel()
    }

    private fun setupViews() {

        firstUserNameTextView.text = "抽样人员1：${App.INSTANCE.user?.userName1}"
        secondUserNameTextView.text = "抽样人员2：${App.INSTANCE.user?.userName2}"
        appVersionTextView.text = context?.packageManager?.getPackageInfo(context?.packageName, 0)?.versionName
        myTaskListButton.setOnClickListener {
            startActivity(Intent(context, MyTaskListActivity::class.java))
        }

        exceptionTaskListButton.setOnClickListener {
            startActivity(Intent(context, CanotVerifyReasonActivity::class.java))
        }

        upgradeAppButton.setOnClickListener {
            navigationFragmentViewModel.fetchAppVersion()
        }

        logoutAppButton.setOnClickListener {
            App.INSTANCE.logout()
        }
    }

    private fun bindViewModel() {
        navigationFragmentViewModel.isLoadingLiveData.observe(this, Observer { isLoading ->
            if (isLoading == true) {
                startLoading()
            } else {
                stopLoading()
            }
        })

        navigationFragmentViewModel.upgradeInfoLiveData.observe(this, Observer { upgradeInfo ->
            if (upgradeInfo == null) {
                Toast.makeText(context, "已是最新版本", Toast.LENGTH_SHORT).show()
            } else {
                MaterialDialog.Builder(context!!)
                    .title("更新")
                    .content(upgradeInfo?.get("description") ?: "")
                    .positiveText("确定")
                    .onPositive { _, _ ->
                       startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(upgradeInfo?.get("url"))))
                    }
                    .negativeText("取消")
                    .show()
            }
        })
        navigationFragmentViewModel.fetchAppVersion()
    }
}
