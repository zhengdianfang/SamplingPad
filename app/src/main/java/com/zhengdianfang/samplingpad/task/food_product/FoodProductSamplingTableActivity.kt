package com.zhengdianfang.samplingpad.task.food_product

import android.os.Bundle
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.task.food_product.components.FirstTableFragment
import kotlinx.android.synthetic.main.toolbar_layout.*
import me.yokeyword.fragmentation.SupportActivity

class FoodProductSamplingTableActivity: SupportActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_product_sampling_table_layout)
        loadRootFragment(R.id.contentFrame, FirstTableFragment.newInstance())

        toolBarTitleView.setText(R.string.food_product_title)

        backButton.setOnClickListener {
            onBackPressed()
        }
    }
}