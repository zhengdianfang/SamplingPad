package com.zhengdianfang.samplingpad.task.canot_verify

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.BaseActivity
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import kotlinx.android.synthetic.main.activity_cannot_reason_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import me.yokeyword.fragmentation.SupportActivity

class CanotVerifyReasonActivity: BaseActivity() {

    private val taskItem by lazy { intent.getParcelableExtra("taskItem") as TaskItem }
    private val canotVerifyViewModel by lazy { ViewModelProviders.of(this).get(CanotVerifyViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cannot_reason_layout)

        this.setupViews()
        this.bindViewModel()
    }

    private fun bindViewModel() {

    }

    private fun setupViews() {
        toolBarTitleView.text = "无法检验原因"
        backButton.setOnClickListener {
            finish()
        }
        taskNoTextView.text = "单号：${taskItem.code}"
        saveAndSubmitButton.setOnClickListener {
            val abnormalTypeName = abnormalTypeNameSpinner.getContent()
        }

        saveOnlyButton.setOnClickListener {
            val abnormalTypeName = abnormalTypeNameSpinner.getContent()
        }

        abnormalTypeNameSpinner.fetchData("${ApiClient.HOST}/app/abnormaltypelis")

    }
}