package com.zhengdianfang.samplingpad.main.api

import com.zhengdianfang.samplingpad.http.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MainApi {

    @FormUrlEncoded
    @POST("app/totalvalidates")
    fun postVerifySample(@FieldMap params: Map<String, String>) : Call<Response<String>>
}

data class VerifySampleParam(
   val implPlanCode: String,
   val producerCsNo: String,
   val sampleName: String,
   val sampleProductDate: Long,
   val enterpriseLicenseNumber: String,
   val level1Name: String,
   val chainBrand: String,
   val sampleDate: Long
)