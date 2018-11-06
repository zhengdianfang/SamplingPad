package com.zhengdianfang.samplingpad.common.pdf

import android.os.Bundle
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import java.io.FileInputStream
import java.io.FileOutputStream

class PrintAdapter(private val filePath: String): PrintDocumentAdapter() {
    override fun onLayout(oldAttributes: PrintAttributes, newAttributes: PrintAttributes,
                          cancellationSignal: CancellationSignal, callback: LayoutResultCallback, extras: Bundle) {
        if (cancellationSignal.isCanceled) {
            callback.onLayoutCancelled()
            return
        }
        val info = PrintDocumentInfo.Builder("name")
            .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
            .build()
        callback.onLayoutFinished(info, true)
    }

    @Throws
    override fun onWrite(pages: Array<out PageRange>, destination: ParcelFileDescriptor,
                         cancellationSignal: CancellationSignal?, callback: WriteResultCallback) {

        val fileInputStream = FileInputStream(filePath)
        val fileOutputStream = FileOutputStream(destination.fileDescriptor)
        var bytesRead: Int
        val buf = ByteArray(1024)
        while (true) {
            bytesRead = fileInputStream.read(buf)
            if (bytesRead <= 0) {
               break
            }
            fileOutputStream.write(buf, 0 , bytesRead)
        }
        callback.onWriteFinished(arrayOf(PageRange.ALL_PAGES))
        fileInputStream.close()
        fileOutputStream.close()
    }

}
