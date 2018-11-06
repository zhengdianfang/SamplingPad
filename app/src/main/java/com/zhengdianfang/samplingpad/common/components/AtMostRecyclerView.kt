package com.zhengdianfang.samplingpad.common.components

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

class AtMostRecyclerView(context: Context?, attrs: AttributeSet?) : RecyclerView(context, attrs) {

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        super.onMeasure(widthSpec, MeasureSpec.AT_MOST)
    }
}