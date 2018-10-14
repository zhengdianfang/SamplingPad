package com.zhengdianfang.samplingpad.api

import com.zhengdianfang.samplingpad.user.entities.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    companion object {
        const val VERIFY_CODE_URL = "${ApiClient.HOST}gifCode"
    }

    @GET("login")
    fun login(
        @Query("username") username: String,
        @Query("password")password: String,
        @Query("code")code: String,
        @Query("rememberMe")rememberMe: Boolean): Call<User>

}