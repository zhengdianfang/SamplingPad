package com.zhengdianfang.samplingpad.common.components

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.LabelView

open class BaseComponent: LinearLayout {
    private lateinit var labelTextView: LabelView

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, style: Int): super(context, attributeSet, style)


    fun isRequired(): Boolean {
        return labelTextView.isRequired()
    }

    protected fun initLabelTextView(context: Context, attrs: TypedArray) {
        labelTextView = LabelView(context, null, R.attr.titleTextStyle, R.style.AppTheme_Table_Title).apply {
            text = attrs.getString(R.styleable.AppTheme_RadioGroupComponent_radio_label)
            gravity = Gravity.RIGHT
            layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply{
                rightMargin = (8 * resources.displayMetrics.density).toInt()
            }
            minWidth = attrs.getDimension(R.styleable.AppTheme_RadioGroupComponent_radio_label_min_width, 0F).toInt()
        }

        addView(labelTextView)
    }
}