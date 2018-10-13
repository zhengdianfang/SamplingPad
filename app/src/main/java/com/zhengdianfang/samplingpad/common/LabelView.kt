package com.zhengdianfang.samplingpad.common

import android.content.Context
import android.support.v4.content.ContextCompat
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.widget.TextView
import com.zhengdianfang.samplingpad.R

class LabelView : TextView {
    constructor(context: Context?): super(context)
    constructor(context: Context?, attributes: AttributeSet?): super(context, attributes)
    constructor(context: Context?, attributes: AttributeSet?, style: Int): super(context, attributes, style)
    constructor(context: Context?, attributes: AttributeSet?, attr: Int, style: Int): super(context, attributes, attr, style)

    override fun setText(text: CharSequence?, type: BufferType?) {
        if (TextUtils.isEmpty(text).not()) {
            val spannableString = SpannableString(text)
            if (text!![0].toString()== "*") {
                spannableString.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.red)), 0 , 1, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            super.setText(spannableString, type)
        }
    }

}