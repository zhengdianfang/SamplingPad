package com.zhengdianfang.samplingpad.user.fragments

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.zhengdianfang.samplingpad.App
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.user.api.UserApi
import com.zhengdianfang.samplingpad.common.md5
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class UserNameLoginFragmentViewModel(application: Application): AndroidViewModel(application) {

    val errorLiveData = MutableLiveData<String>()
    val tokenLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    fun login(codeKey: String, username: String, password: String, code: String, rememberMe: Boolean) {
        val context = getApplication<App>()
        when {
            username.isNullOrEmpty() -> errorLiveData.postValue(context.resources.getString(R.string.please_input_username_hint))
            password.isNullOrEmpty() -> errorLiveData.postValue(context.resources.getString(R.string.please_input_password_hint))
            code.isNullOrEmpty() -> errorLiveData.postValue(context.resources.getString(R.string.please_input_verify_code_hint))
            else -> doAsync {
                try {
                    isLoadingLiveData.postValue(true)
                    val response  = ApiClient.getRetrofit()
                        .create(UserApi::class.java)
                        .login(codeKey, username, password.md5(), code,  rememberMe)
                        .execute()

                    uiThread {
                        val body = response.body()
                        if (body?.code == 200) {
                            tokenLiveData.postValue(body?.token)
                        }else{
                            errorLiveData.postValue(body?.msg)
                        }
                        if (response.isSuccessful) {

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
                        if (body?.code == 200) {
                            App.INSTANCE.token = token
                            tokenLiveData.postValue(App.INSTANCE.token)
                        } else {
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
}