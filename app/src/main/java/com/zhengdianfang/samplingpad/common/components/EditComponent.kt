package com.zhengdianfang.samplingpad.common.components

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.Gravity
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.LabelView

class EditComponent: BaseComponent {

    private lateinit var editTextView: EditText

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet) {
        this.setupViews(context, attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, style: Int): super(context, attributeSet, style) {
        this.setupViews(context, attributeSet)
    }

    fun setEditTextContent(text: String?) {
        editTextView.setText(text)
    }

    fun getContent(): String {
        return editTextView.text.toString()
    }

    override fun clear() {
        editTextView.setText("")
    }

    private fun setupViews(context: Context, attributeSet: AttributeSet) {
        val attrs = context.obtainStyledAttributes(attributeSet, R.styleable.AppTheme_EditComponent)
        initLabelTextView(context, attrs)
        initEditTextView(context, attrs)
    }

    private fun initEditTextView(context: Context, attrs: TypedArray) {
        editTextView = EditText(context, null, R.attr.editTextStyle, R.style.AppTheme_EditText).apply {
            hint = attrs.getString(R.styleable.AppTheme_EditComponent_edit_hint)
            maxLines = 1
            setLines(1)
            setSingleLine(true)
            layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply{
                weight = 1f
                gravity = Gravity.CENTER_VERTICAL
            }
        }
        addView(editTextView)
    }

    private fun initLabelTextView(context: Context, attrs: TypedArray) {
        labelTextView = LabelView(context, null, R.attr.titleTextStyle, R.style.AppTheme_Table_Title).apply {
            text = attrs.getString(R.styleable.AppTheme_EditComponent_edit_label)
            gravity = Gravity.RIGHT
            layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply{
                gravity = Gravity.CENTER_VERTICAL
                rightMargin = (8 * resources.displayMetrics.density).toInt()
            }
            minWidth = attrs.getDimension(R.styleable.AppTheme_EditComponent_edit_label_min_width, 0F).toInt()
        }

        addView(labelTextView)
    }
}