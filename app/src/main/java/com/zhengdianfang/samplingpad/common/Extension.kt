package com.zhengdianfang.samplingpad.common

import android.support.v4.content.ContextCompat
import android.widget.RadioButton

fun RadioButton.active(backgroundResId: Int, textColorId: Int) {
    if (backgroundResId > 0) {
        setBackgroundResource(backgroundResId)
    }
    if (textColorId > 0) {
        setTextColor(ContextCompat.getColor(context, textColorId))
    }
}

fun RadioButton.unActive(backgroundResId: Int, textColorId: Int) {
    if (backgroundResId > 0) {
        setBackgroundResource(backgroundResId)
    }
    if (textColorId > 0) {
        setTextColor(ContextCompat.getColor(context, textColorId))
    }
}
