package com.zhengdianfang.samplingpad.user.api

import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.http.Response
import com.zhengdianfang.samplingpad.user.entities.User
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded

interface UserApi {

    companion object {
        const val VERIFY_CODE_URL = "${ApiClient.HOST}gifCode"
    }

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("username") username: String,
        @Field("password")password: String,
        @Field("code")code: String,
        @Field("rememberMe")rememberMe: Boolean): Call<Response<User>>

    @FormUrlEncoded
    @POST("login/second")
    fun loginSecond(
        @Field("username") username: String,
        @Field("password")password: String,
        @Field("rememberMe")rememberMe: Boolean): Call<Response<String>>
}