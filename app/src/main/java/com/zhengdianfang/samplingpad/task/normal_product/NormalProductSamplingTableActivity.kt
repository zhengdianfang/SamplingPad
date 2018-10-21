package com.zhengdianfang.samplingpad.task.normal_product

import android.os.Bundle
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.BaseActivity
import com.zhengdianfang.samplingpad.task.normal_product.fragments.FirstTableFragment
import com.zhengdianfang.samplingpad.task.entities.TaskItem
import com.zhengdianfang.samplingpad.task.normal_product.fragments.SecondTableFragment
import kotlinx.android.synthetic.main.toolbar_layout.*
import me.yokeyword.fragmentation.SupportActivity

class NormalProductSamplingTableActivity: BaseActivity() {

    val task by lazy { intent.getParcelableExtra("task") as TaskItem }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_normal_product_sampling_table_layout)
        loadRootFragment(R.id.contentFrame, FirstTableFragment.newInstance(task))

        toolBarTitleView.setText(R.string.normal_product_title)

        backButton.setOnClickListener {
            onBackPressed()
        }
    }
}