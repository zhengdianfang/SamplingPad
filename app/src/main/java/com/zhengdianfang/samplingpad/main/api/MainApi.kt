package com.zhengdianfang.samplingpad.main.api

import com.zhengdianfang.samplingpad.common.entities.OptionItem
import com.zhengdianfang.samplingpad.http.Response
import com.zhengdianfang.samplingpad.main.entites.VerifyParams
import retrofit2.Call
import retrofit2.http.*

interface MainApi {

    @POST("app/totalvalidates")
    fun postVerifySample(@Body verifyParams: VerifyParams): Call<Response<String>>

    @GET("app/{path}")
    fun fetchOptionData(@Path("path") path: String): Call<Response<Array<OptionItem>>>

    @GET("app/appversions")
    fun fetchAppVersion(): Call<Response<Map<String, String>>>
}

