package com.zhengdianfang.samplingpad.common.pdf

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.print.PrintManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener
import com.github.barteksc.pdfviewer.util.FileUtils
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.BaseFragment
import com.zhengdianfang.samplingpad.http.ApiClient
import kotlinx.android.synthetic.main.fragment_pdf_preview.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import okhttp3.Request
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import timber.log.Timber
import java.io.File

class PdfPreviewFragment: BaseFragment() {

    companion object {
        fun newInstance(pdfUrl: String): PdfPreviewFragment  {
            val bundle = Bundle()
            bundle.putString("url", pdfUrl)
            val fragment = PdfPreviewFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val pdfUrl by lazy { arguments?.getString("url") ?: "" }
    private var localFile: File? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  inflater.inflate(R.layout.fragment_pdf_preview, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolBarTitleView.setOnClickListener {
            activity?.finish()
        }
        this.setupViews()
        this.downloadPdf()
    }

    private fun setupViews() {
        rightButton.text = "打印PDF"
        rightButton.setOnClickListener {
            val printManager = context?.getSystemService(Context.PRINT_SERVICE) as PrintManager
            Timber.d("print pdf file: ${localFile?.absolutePath}")
            val printAdapter = PrintAdapter(localFile?.absolutePath ?: "")
            printManager.print("print $pdfUrl", printAdapter, null)
        }
    }

    private fun downloadPdf() {
        val fileName = this.pdfUrl.split("/").last()
        val localPdfDir = File("${Environment.getExternalStorageDirectory().absolutePath}/sampling/pdf/")
        if (localPdfDir.exists().not()) {
            localPdfDir.mkdirs()
        }
        localFile = File(localPdfDir, fileName)
        if (localFile!!.exists().not()) {
            doAsync {
                val request = Request.Builder().url(pdfUrl).build()
                val response = ApiClient.okHttpClient.newCall(request).execute()
                val byteStream = response.body()?.byteStream()
                FileUtils.copy(byteStream, localFile)
                if (localFile!!.exists()) {
                    uiThread {
                        pdfView.fromFile(localFile)
                            .defaultPage(0)
                            .onLoad {
                                rightButton.visibility = View.VISIBLE
                            }
                            .load()
                    }
                }
            }
        } else {
            pdfView.fromFile(localFile)
                .defaultPage(0)
                .onLoad {
                    rightButton.visibility = View.VISIBLE
                }
                .load()
        }
    }

}