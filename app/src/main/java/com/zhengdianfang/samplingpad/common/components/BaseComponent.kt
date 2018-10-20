package com.zhengdianfang.samplingpad.common.components

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.LabelView

abstract class BaseComponent: LinearLayout {
    protected lateinit var labelTextView: LabelView

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, style: Int): super(context, attributeSet, style)


    fun isRequired(): Boolean {
        return labelTextView.isRequired()
    }

    abstract fun clear()

}