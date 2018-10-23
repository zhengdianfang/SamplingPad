package com.zhengdianfang.samplingpad.main.fragments

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.http.Response
import com.zhengdianfang.samplingpad.main.api.MainApi
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class VerifyFragmentViewModel(application: Application) : AndroidViewModel(application) {

    val isLoadingLiveData = MutableLiveData<Boolean>()
    val responseLiveData = MutableLiveData<Response<String>>()

    fun postVerifySample(params: Map<String, String>) {
        isLoadingLiveData.postValue(true)
        doAsync {
            val response = ApiClient.INSTANCE.create(MainApi::class.java)
                .postVerifySample(params)
                .execute()

            val body = response.body()
            if (body != null) {
                uiThread {
                    responseLiveData.postValue(body)
                    isLoadingLiveData.postValue(false)
                }
            }
        }
    }
}