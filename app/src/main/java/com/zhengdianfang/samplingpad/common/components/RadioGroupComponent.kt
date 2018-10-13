package com.zhengdianfang.samplingpad.common.components

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.Gravity
import android.widget.*
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.LabelView

class RadioGroupComponent: LinearLayout {

    private lateinit var labelTextView: LabelView
    private lateinit var radioGroup: RadioGroup

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet) {
        this.setupViews(context, attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, style: Int): super(context, attributeSet, style) {
        this.setupViews(context, attributeSet)
    }

    private fun setupViews(context: Context, attributeSet: AttributeSet) {
        val attrs = context.obtainStyledAttributes(attributeSet, R.styleable.AppTheme_RadioGroupComponent)
        initLabelTextView(context, attrs)
        initRadioGroupView(context, attrs)
    }

    private fun initRadioGroupView(context: Context, attrs: TypedArray) {
        radioGroup = RadioGroup(context).apply {
            orientation = RadioGroup.HORIZONTAL
            val buttons = attrs.getTextArray(R.styleable.AppTheme_RadioGroupComponent_radio_buttons)
            buttons.forEachIndexed { index, text ->
                val radioButton = RadioButton(context).apply {
                    setText(text)
                    layoutParams = RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT).apply {
                        rightMargin = if (index == buttons.count() - 1) 0 else resources.getDimension(R.dimen.radio_button_margin_right).toInt()
                    }
                }
                addView(radioButton)
            }
            layoutParams =  LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply{
                weight = 1F
            }
        }
        addView(radioGroup)
    }

    private fun initLabelTextView(context: Context, attrs: TypedArray) {
        labelTextView = LabelView(context, null, R.attr.titleTextStyle, R.style.AppTheme_Table_Title).apply {
            text = attrs.getString(R.styleable.AppTheme_RadioGroupComponent_radio_label)
            gravity = Gravity.RIGHT
            layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply{
                gravity = Gravity.CENTER_VERTICAL
                rightMargin = (8 * resources.displayMetrics.density).toInt()
            }
            minWidth = attrs.getDimension(R.styleable.AppTheme_RadioGroupComponent_radio_label_min_width, 0F).toInt()
        }

        addView(labelTextView)
    }
}