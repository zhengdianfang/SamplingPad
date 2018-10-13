package com.zhengdianfang.samplingpad.normal_product

import android.os.Bundle
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.normal_product.fragments.FirstTableFragment
import kotlinx.android.synthetic.main.toolbar_layout.*
import me.yokeyword.fragmentation.SupportActivity

class NormalProductSamplingTableActivity: SupportActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_normal_product_sampling_table_layout)
        loadRootFragment(R.id.contentFrame, FirstTableFragment.newInstance())

        toolBarTitleView.setText(R.string.normal_product_title)

        backButton.setOnClickListener {
            onBackPressed()
        }
    }
}