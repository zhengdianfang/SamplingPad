package com.zhengdianfang.samplingpad.common.components

import android.content.Context
import android.content.res.TypedArray
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.LabelView
import com.zhengdianfang.samplingpad.common.entities.OptionItem
import org.jetbrains.anko.defaultSharedPreferences

class NewRadioGroupComponent: BaseComponent {

    private lateinit var radioGroup: FlexboxLayout
    var radioButtonCheckCallback: ((position: Int, option: OptionItem) -> Unit)? = null
    private var options: Array<OptionItem>? = null

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

    fun setDefaultCheckedRadioButton(id: Int?) {
        if (id != null) {
            val index = options?.indexOfFirst { it.id == id }
            if (index != null && index >=0) {
                (radioGroup.getChildAt(index) as RadioButton).isChecked = true
            }
        }
    }

    override fun clear() {
        for(index in 0 until radioGroup.childCount) {
            val radioButton = radioGroup.getChildAt(index) as RadioButton
            radioButton.isChecked = false
        }
    }

    fun getCheckedOption(): OptionItem? {
        for(index in 0 until radioGroup.childCount) {
            val radioButton = radioGroup.getChildAt(index) as RadioButton
            if (radioButton.isChecked) {
                return options!![index]
            }
        }
        return null
    }

    override fun checkFieldHasValue(): Boolean {
        for(index in 0 until radioGroup.childCount) {
            val radioButton = radioGroup.getChildAt(index) as RadioButton
            if (radioButton.isChecked) {
                return true
            }
        }
        return false;
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
            val key = attrs.getString(R.styleable.AppTheme_RadioGroupComponent_radio_data_key)
            val dataString = context.defaultSharedPreferences.getString(key, "")
            if (TextUtils.isEmpty(dataString).not()) {
                options = Gson().fromJson<Array<OptionItem>>(dataString, object : TypeToken<Array<OptionItem>>(){}.type)
                if (options != null) {
                    renderOptionView(context, this)
                }
                layoutParams =  LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply{
                    weight = 1F
                }
            }

        }
        addView(radioGroup)
    }

    private fun renderOptionView(context: Context, viewGroup: ViewGroup) {
        options!!.forEachIndexed { index, option ->
            val radioButton = RadioButton(context).apply {
                text = option.name
                layoutParams = RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT).apply {
                    rightMargin = if (index == options!!.count() - 1) 0 else resources.getDimension(R.dimen.radio_button_margin_right).toInt()
                }
                setPadding(16, 0, 16, 0)
            }
            radioButton.setOnCheckedChangeListener(RadioButtonCheckListener())
            viewGroup.addView(radioButton)
        }
    }

    private fun initLabelTextView(context: Context, attrs: TypedArray) {
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


    private inner class RadioButtonCheckListener: CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(checkedButton: CompoundButton, checked: Boolean) {
            if (checked) {
                for(index in 0 until radioGroup.childCount) {
                    val radioButton = radioGroup.getChildAt(index) as RadioButton
                    if (checkedButton.text !== radioButton.text) {
                        radioButton.isChecked = false
                    } else {
                        radioButtonCheckCallback?.invoke(index, options!!.get(index))
                    }
                }
            }
        }

    }
}