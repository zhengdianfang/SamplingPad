package com.zhengdianfang.samplingpad.user.api

import com.zhengdianfang.samplingpad.http.Response
import com.zhengdianfang.samplingpad.user.entities.User
import retrofit2.Call
import retrofit2.http.*

interface UserApi {
    @POST("login")
    fun login(
        @Header("codeKey") codeKey: String,
        @Field("username") username: String,
        @Field("password")password: String,
        @Field("code")code: String,
        @Field("rememberMe")rememberMe: Boolean): Call<Response<User>>

    @FormUrlEncoded
    @POST("login/second")
    fun loginSecond(
        @Header("codeKey") codeKey: String,
        @Header("Authorization") token: String,
        @Field("username") username: String,
        @Field("password")password: String,
        @Field("remeqmberMe")rememberMe: Boolean): Call<Map<String, String>>

    @GET("logout")
    fun logout(): Call<Response<String>>
}