package com.zhengdianfang.samplingpad.api

import com.zhengdianfang.samplingpad.store.UserState
import io.reactivex.Observable
import retrofit2.http.POST

interface UserApi {

    @POST("/login")
    fun login(username: String, password: String): Observable<UserState.User>

}