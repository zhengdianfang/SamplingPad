package com.zhengdianfang.samplingpad.common.components

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import com.zhengdianfang.samplingpad.PerviewTaskFormActivity
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.LabelView

abstract class BaseComponent: LinearLayout {
    lateinit var labelTextView: LabelView
    private var disable = false

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, style: Int): super(context, attributeSet, style)
    init {

    }

    open fun isRequired(): Boolean {
        return labelTextView.isRequired()
    }

    open fun setDisable(disable: Boolean) {
        this.disable = disable
    }

    fun getDisable() = disable

    abstract fun clear()

    abstract fun checkFieldHasValue(): Boolean

    open fun noValueTip() = ""

}