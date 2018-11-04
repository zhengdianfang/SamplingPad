package com.zhengdianfang.samplingpad.http

import okhttp3.Interceptor
import okhttp3.Response

class UploadInterceptor(
    private val onRequestProgress: ((btyesWritten: Long, contentLength: Long) -> Unit)?
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        if (originalRequest.body() == null) {
            return chain.proceed(originalRequest)
        }

        val uploadRequest = originalRequest.newBuilder()
            .method(originalRequest.method(), UploadRequestBody(originalRequest.body(), onRequestProgress))
            .build()
        return chain.proceed(uploadRequest)
    }
}