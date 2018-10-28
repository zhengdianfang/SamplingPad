package com.zhengdianfang.samplingpad.main.api

import com.zhengdianfang.samplingpad.http.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MainApi {

    @POST("app/totalvalidates")
    fun postVerifySample(@Body params: Map<String, String?>) : Call<Response<String>>
}

