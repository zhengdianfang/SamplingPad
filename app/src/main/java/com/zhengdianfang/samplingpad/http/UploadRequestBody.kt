package com.zhengdianfang.samplingpad.http

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.*

class UploadRequestBody(
    private val delegate: RequestBody?,
    private val onRequestProgress: ((btyesWritten: Long, contentLength: Long) -> Unit)?
): RequestBody() {

    override fun contentType(): MediaType? {
        return delegate?.contentType()
    }

    override fun contentLength(): Long {
        return delegate?.contentLength() ?: -1
    }

    override fun writeTo(sink: BufferedSink) {
        val uploadSink = UploadSink(sink)
        val bufferedSink = Okio.buffer(uploadSink)
        delegate?.writeTo(bufferedSink)
        bufferedSink.flush()
    }

    private inner class UploadSink(delegate: Sink) : ForwardingSink(delegate) {

        private var bytesWritten = 0L

        override fun write(source: Buffer, byteCount: Long) {
            super.write(source, byteCount)
            bytesWritten += byteCount
            onRequestProgress?.invoke(bytesWritten, contentLength())
        }
    }
}