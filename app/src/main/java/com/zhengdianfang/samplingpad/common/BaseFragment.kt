package com.zhengdianfang.samplingpad.common

import android.os.Bundle
import com.afollestad.materialdialogs.MaterialDialog
import kotlinx.android.synthetic.main.toolbar_layout.*
import me.yokeyword.fragmentation.SupportFragment

open class BaseFragment: SupportFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        backButton?.setOnClickListener {
            pop()
        }
    }

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