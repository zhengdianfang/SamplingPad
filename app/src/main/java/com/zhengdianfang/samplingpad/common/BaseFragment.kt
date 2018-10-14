package com.zhengdianfang.samplingpad.common

import com.afollestad.materialdialogs.MaterialDialog
import me.yokeyword.fragmentation.SupportFragment

open class BaseFragment: SupportFragment() {

    private val progressDialog by lazy {
        MaterialDialog.Builder(context!!).progress(true, 10).build()
    }

    protected fun startLoading() {
        progressDialog.show()
    }

    protected fun stopLoading() {
        progressDialog.dismiss()
    }
}