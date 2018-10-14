package com.zhengdianfang.samplingpad.api

import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody

class AppResponeInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var response = chain.proceed(chain.request())
        if (response.isSuccessful) {
            val responseText = response.body()?.string()
            if (responseText.isNullOrEmpty().not()) {
                val gson = Gson()
                val responseBean = gson.fromJson<com.zhengdianfang.samplingpad.api.Response>(
                    responseText, com.zhengdianfang.samplingpad.api.Response::class.java)
                response = response
                    .newBuilder()
                    .code(responseBean.code)
                    .message(responseBean.msg)
                    .body(ResponseBody.create(MediaType.parse("application/json"), responseText))
                    .build()
            }
        }
        return response
    }
}