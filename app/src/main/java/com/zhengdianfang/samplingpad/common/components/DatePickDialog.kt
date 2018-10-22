package com.zhengdianfang.samplingpad.common.components

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.DatePicker
import com.afollestad.materialdialogs.MaterialDialog

class DatePickDialog: DialogFragment() {

    var onConfirmCallback: ((date: String) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val datePicker = DatePicker(context)
        return MaterialDialog.Builder(context!!)
            .customView(datePicker, false)
            .positiveText("确定")
            .negativeText("取消")
            .onPositive { _, _ ->
                var month = datePicker.month + 1
                val monthStr = if ((month) / 10 < 1) "0${month}" else "$month"
                val day = if (datePicker.dayOfMonth / 10 < 1) "0${datePicker.dayOfMonth}" else "${datePicker.dayOfMonth}"
                onConfirmCallback?.invoke("${datePicker.year}年${monthStr}月${day}日")
            }
            .build()
    }
}