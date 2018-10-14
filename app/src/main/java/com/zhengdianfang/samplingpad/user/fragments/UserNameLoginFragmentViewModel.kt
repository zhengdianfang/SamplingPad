package com.zhengdianfang.samplingpad.user.fragments

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.zhengdianfang.samplingpad.App
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.api.ApiClient
import com.zhengdianfang.samplingpad.api.UserApi
import com.zhengdianfang.samplingpad.common.md5
import com.zhengdianfang.samplingpad.user.entities.User
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import timber.log.Timber

class UserNameLoginFragmentViewModel(application: Application): AndroidViewModel(application) {

    val errorLiveData = MutableLiveData<String>()
    val userLiveData = MutableLiveData<User>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    fun login(username: String, password: String, code: String, rememberMe: Boolean) {
        val context = getApplication<App>()
        when {
            username.isNullOrEmpty() -> errorLiveData.postValue(context.resources.getString(R.string.please_input_username_hint))
            password.isNullOrEmpty() -> errorLiveData.postValue(context.resources.getString(R.string.please_input_password_hint))
            code.isNullOrEmpty() -> errorLiveData.postValue(context.resources.getString(R.string.please_input_verify_code_hint))
            else -> doAsync {
                isLoadingLiveData.postValue(true)
                val response  = ApiClient.INSTANCE
                    .create(UserApi::class.java)
                    .login(username, password.md5(), code, rememberMe)
                    .execute()

                uiThread {
                    isLoadingLiveData.postValue(false)
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            userLiveData.postValue(response.body())
                        }
                    } else {
                        errorLiveData.postValue(response.message())
                    }
                }
            }
        }
    }
}