package com.zhengdianfang.samplingpad.common.components

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.LabelView

class TextComponent : LinearLayout {

    private lateinit var labelTextView: LabelView
    private lateinit var contentTextView : TextView

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet) {
        this.setupViews(context, attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, style: Int): super(context, attributeSet, style) {
        this.setupViews(context, attributeSet)
    }

    fun setContentText(text: String?) {
        this.contentTextView.text = text
    }

    private fun setupViews(context: Context, attributeSet: AttributeSet) {
        val attrs = context.obtainStyledAttributes(attributeSet, R.styleable.AppTheme_TextComponent)
        initLabelTextView(context, attrs)
        initContentTextView(context, attrs)
    }

    private fun initContentTextView(context: Context, attrs: TypedArray) {
        contentTextView = TextView(context, null, R.attr.titleTextStyle, R.style.AppTheme_Table_Title).apply {
            text = attrs.getString(R.styleable.AppTheme_TextComponent_text_content)
            layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply{
                weight = 1f
            }
        }
        addView(contentTextView)
    }

    private fun initLabelTextView(context: Context, attrs: TypedArray) {
        labelTextView = LabelView(context, null, R.attr.titleTextStyle, R.style.AppTheme_Table_Title).apply {
            text = attrs.getString(R.styleable.AppTheme_TextComponent_text_label)
            gravity = Gravity.RIGHT
            layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply{
                rightMargin = (8 * resources.displayMetrics.density).toInt()
            }
            minWidth = attrs.getDimension(R.styleable.AppTheme_TextComponent_text_label_min_width, 0F).toInt()
        }

        addView(labelTextView)
    }
}