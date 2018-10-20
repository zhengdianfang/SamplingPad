package com.zhengdianfang.samplingpad.common.components

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.Gravity
import android.widget.*
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayout
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.LabelView

class RadioGroupComponent: BaseComponent {

    private lateinit var radioGroup: FlexboxLayout

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet) {
        this.setupViews(context, attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, style: Int): super(context, attributeSet, style) {
        this.setupViews(context, attributeSet)
    }

    fun setDefaultCheckedRadioButton(text: String?) {
        for(index in 0 until radioGroup.childCount) {
            val radioButton = radioGroup.getChildAt(index) as RadioButton
            if (text == radioButton.text) {
                radioButton.isChecked = true
            }
        }
    }

    fun clear() {
        for(index in 0 until radioGroup.childCount) {
            val radioButton = radioGroup.getChildAt(index) as RadioButton
            radioButton.isChecked = false
        }
    }

    fun getCheckedText(): String{
        for(index in 0 until radioGroup.childCount) {
            val radioButton = radioGroup.getChildAt(index) as RadioButton
            if (radioButton.isChecked) {
                return radioButton.text.toString()
            }
        }
        return ""
    }

    private fun setupViews(context: Context, attributeSet: AttributeSet) {
        val attrs = context.obtainStyledAttributes(attributeSet, R.styleable.AppTheme_RadioGroupComponent)
        initLabelTextView(context, attrs)
        initRadioGroupView(context, attrs)
    }

    private fun initRadioGroupView(context: Context, attrs: TypedArray) {
        radioGroup = FlexboxLayout(context).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            val buttons = attrs.getTextArray(R.styleable.AppTheme_RadioGroupComponent_radio_buttons)
            buttons.forEachIndexed { index, text ->
                val radioButton = RadioButton(context).apply {
                    setText(text)
                    layoutParams = RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT).apply {
                        rightMargin = if (index == buttons.count() - 1) 0 else resources.getDimension(R.dimen.radio_button_margin_right).toInt()
                    }
                    setPadding(16, 0, 16, 0)
                }
                radioButton.setOnCheckedChangeListener(RadioButtonCheckListener())
                addView(radioButton)
            }
            layoutParams =  LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply{
                weight = 1F
            }
        }
        addView(radioGroup)
    }



    private inner class RadioButtonCheckListener: CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(checkedButton: CompoundButton, checked: Boolean) {
            if (checked) {
                for(index in 0 until radioGroup.childCount) {
                    val radioButton = radioGroup.getChildAt(index) as RadioButton
                    if (checkedButton.text !== radioButton.text) {
                        radioButton.isChecked = false
                    }
                }
            }
        }

    }
}