package com.zhengdianfang.samplingpad.user.fragments

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.zhengdianfang.samplingpad.App
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.user.api.UserApi
import com.zhengdianfang.samplingpad.common.md5
import com.zhengdianfang.samplingpad.user.entities.User
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class UserNameLoginFragmentViewModel(application: Application): AndroidViewModel(application) {

    val errorLiveData = MutableLiveData<String>()
    val userLiveData = MutableLiveData<User>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    fun login(codeKey: String, username: String, password: String, code: String, rememberMe: Boolean) {
        val context = getApplication<App>()
        when {
            username.isNullOrEmpty() -> errorLiveData.postValue(context.resources.getString(R.string.please_input_username_hint))
            password.isNullOrEmpty() -> errorLiveData.postValue(context.resources.getString(R.string.please_input_password_hint))
            code.isNullOrEmpty() -> errorLiveData.postValue(context.resources.getString(R.string.please_input_verify_code_hint))
            else ->
                doAsync {
                    try {
                        isLoadingLiveData.postValue(true)
                        val response  = ApiClient.getRetrofit()
                            .create(UserApi::class.java)
                            .login(codeKey, username, password.md5(), code,  rememberMe)
                            .execute()

                        uiThread {
                            val body = response.body()
                            if (body?.code == 200) {
                                val user = body.userInfo
                                user?.token = body.token
                                userLiveData.postValue(user)
                            }else{
                                errorLiveData.postValue(body?.msg)
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    } finally {
                        isLoadingLiveData.postValue(false)
                    }

                }
        }
    }

    fun loginSecond(codeKey: String, token: String, username: String, password: String, rememberMe: Boolean) {
        val context = getApplication<App>()
        when {
            username.isNullOrEmpty() -> errorLiveData.postValue(context.resources.getString(R.string.please_input_username_hint))
            password.isNullOrEmpty() -> errorLiveData.postValue(context.resources.getString(R.string.please_input_password_hint))
            else -> doAsync {
                try {
                    isLoadingLiveData.postValue(true)
                    val response  = ApiClient.getRetrofit()
                        .create(UserApi::class.java)
                        .loginSecond(codeKey, token ,username, password.md5(), rememberMe)
                        .execute()

                    uiThread {
                        val body = response.body()
                        if (body?.get("code")?.toIntOrNull() == 200) {
                            val user = App.INSTANCE.user
                            user?.userName1 = body["userName1"] ?: ""
                            user?.userName2 = body["userName2"] ?: ""
                            user?.id2 = body["id"]?.toIntOrNull() ?: 0
                            App.INSTANCE.user = user
                            userLiveData.postValue(App.INSTANCE.user)
                        } else {
                            errorLiveData.postValue(body?.get("msg"))
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    isLoadingLiveData.postValue(false)
                }

            }
        }
    }
}