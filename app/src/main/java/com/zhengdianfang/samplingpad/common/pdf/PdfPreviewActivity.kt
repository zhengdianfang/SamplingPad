package com.zhengdianfang.samplingpad.common.pdf

import android.os.Bundle
import com.zhengdianfang.samplingpad.common.BaseActivity

class PdfPreviewActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadRootFragment(android.R.id.content, PdfPreviewFragment.newInstance("http://103.78.124.135/2Q2W51E01502D1C9E8853E17E111400CB8C3B9A77C7A_unknown_295002AB78373D687433EDDED5FCC82448BE6DE0_7/www.gov.cn/zhengce/pdfFile/2018_PDF.pdf"))
    }
}
