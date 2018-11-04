package com.zhengdianfang.samplingpad.common.pdf

import android.os.Bundle
import com.zhengdianfang.samplingpad.common.BaseActivity

class PdfPreviewActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadRootFragment(android.R.id.content, PdfPreviewFragment.newInstance("http://192.168.1.100:8081/test.pdf"))
    }
}
