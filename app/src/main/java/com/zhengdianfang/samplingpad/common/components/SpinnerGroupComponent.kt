package com.zhengdianfang.samplingpad.common.components

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.Gravity
import android.widget.*
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.LabelView

class SpinnerGroupComponent: LinearLayout {

    private lateinit var labelTextView: LabelView
    private val spinnerViews = mutableListOf<TextView>()

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet) {
        this.setupViews(context, attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, style: Int): super(context, attributeSet, style) {
        this.setupViews(context, attributeSet)
    }

    private fun setupViews(context: Context, attributeSet: AttributeSet) {
        val attrs = context.obtainStyledAttributes(attributeSet, R.styleable.AppTheme_SpinnerGroupComponent)
        initLabelTextView(context, attrs)
        initSpinnerGroupView(context, attrs)
    }

    private fun initSpinnerGroupView(context: Context, attrs: TypedArray) {
        val hints = attrs.getTextArray(R.styleable.AppTheme_SpinnerGroupComponent_spinner_hints)
        hints.forEachIndexed { index, text ->
            val textView = TextView(context).apply {
                setBackgroundResource(R.drawable.edit_text_background)
                gravity = Gravity.CENTER_VERTICAL
                setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0)
                setPadding(16, 8, 16, 8)
                setText(text)
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                    rightMargin = if (index == hints.count() - 1) 0 else resources.getDimension(R.dimen.radio_button_margin_right).toInt()
                    weight = 1F
                }
            }
            spinnerViews.add(textView)
            addView(textView)
        }
    }

    private fun initLabelTextView(context: Context, attrs: TypedArray) {
        labelTextView = LabelView(context, null, R.attr.titleTextStyle, R.style.AppTheme_Table_Title).apply {
            text = attrs.getString(R.styleable.AppTheme_SpinnerGroupComponent_spinner_label)
            gravity = Gravity.RIGHT
            layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply{
                gravity = Gravity.CENTER_VERTICAL
                rightMargin = (8 * resources.displayMetrics.density).toInt()
            }
            minWidth = attrs.getDimension(R.styleable.AppTheme_EditComponent_edit_label_min_width, 0F).toInt()
        }

        addView(labelTextView)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }
}