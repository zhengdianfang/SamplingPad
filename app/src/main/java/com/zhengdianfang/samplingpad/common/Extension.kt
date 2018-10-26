package com.zhengdianfang.samplingpad.common

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.RadioButton
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.regex.Pattern

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

fun String.md5(): String {
    try {
        val instance: MessageDigest = MessageDigest.getInstance("MD5")
        val digest:ByteArray = instance.digest(this.toByteArray())
        var sb = StringBuffer()
        for (b in digest) {
            var i :Int = b.toInt() and 0xff
            var hexString = Integer.toHexString(i)
            if (hexString.length < 2) {
                hexString = "0$hexString"
            }
            sb.append(hexString)
        }
        return sb.toString().toUpperCase()

    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }

    return ""
}

fun String.convertForegroundColorSpannableString(regex: String): SpannableString {
    val spannableString = SpannableString(this)
    val pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(this)
    while (matcher.find()) {
        spannableString.setSpan(ForegroundColorSpan(Color.RED), matcher.start(), matcher.end(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    return spannableString
}

