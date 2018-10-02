package com.zhengdianfang.samplingpad.common

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
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

fun Drawable.tintDrawable(tintColor: Int): Drawable? {
    var drawable = DrawableCompat.wrap(this)
    DrawableCompat.setTint(drawable, tintColor)
    DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_ATOP)
    return drawable
}