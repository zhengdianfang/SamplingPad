package com.zhengdianfang.samplingpad.main.api

import com.zhengdianfang.samplingpad.common.entities.OptionItem
import com.zhengdianfang.samplingpad.http.Response
import retrofit2.Call
import retrofit2.http.*

interface MainApi {

    @FormUrlEncoded
    @POST("app/totalvalidates")
    fun postVerifySample(

        @Field("implPlanCode") implPlanCode: String?,
        @Field("level1Name") level1Name: String?,
        @Field("enterpriseLicenseNumber") enterpriseLicenseNumber: String?,
        @Field("sampleName") sampleName: String?,
        @Field("sampleProductDate") sampleProductDate: String?,
        @Field("chainBrand") chainBrand: String?,
        @Field("sampleDate") sampleDate: String?,
        @Field("producerCsNo") producerCsNo: String?,
        @Field("enterpriseName") enterpriseName: String?
    ): Call<Response<String>>

    @GET("app/{path}")
    fun fetchOptionData(@Path("path") path: String): Call<Response<Array<OptionItem>>>
}

