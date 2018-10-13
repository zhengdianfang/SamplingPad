package com.zhengdianfang.samplingpad.common.components

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.LabelView

class DateComponent : LinearLayout {

    private lateinit var labelTextView: LabelView
    private lateinit var dateTextView: TextView

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet) {
        this.setupViews(context, attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, style: Int): super(context, attributeSet, style) {
        this.setupViews(context, attributeSet)
    }

    private fun setupViews(context: Context, attributeSet: AttributeSet) {
        val attrs = context.obtainStyledAttributes(attributeSet, R.styleable.AppTheme_DateComponent)
        initLabelTextView(context, attrs)
        initDateTextView(context, attrs)
    }

    private fun initDateTextView(context: Context, attrs: TypedArray) {
        dateTextView = TextView(context).apply {
            setBackgroundResource(R.drawable.edit_text_background)
            text = attrs.getString(R.styleable.AppTheme_DateComponent_date_hint)
            setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_calendar, 0)
            setPadding(16, 24, 16, 24)
            layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply{
                weight = 1f
                gravity = Gravity.CENTER_VERTICAL
            }
        }
        addView(dateTextView)
    }

    private fun initLabelTextView(context: Context, attrs: TypedArray) {
        labelTextView = LabelView(context, null, R.attr.titleTextStyle, R.style.AppTheme_Table_Title).apply {
            text = attrs.getString(R.styleable.AppTheme_DateComponent_date_label)
            gravity = Gravity.RIGHT
            layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply{
                gravity = Gravity.CENTER_VERTICAL
                rightMargin = (8 * resources.displayMetrics.density).toInt()
            }
            minWidth = attrs.getDimension(R.styleable.AppTheme_DateComponent_date_label_min_width, 0F).toInt()
        }

        addView(labelTextView)
    }
}