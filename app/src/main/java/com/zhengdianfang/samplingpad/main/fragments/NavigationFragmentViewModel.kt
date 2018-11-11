package com.zhengdianfang.samplingpad.main.fragments

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.zhengdianfang.samplingpad.App
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.main.api.MainApi
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class NavigationFragmentViewModel(application: Application) : AndroidViewModel(application) {

    val isLoadingLiveData = MutableLiveData<Boolean>()
    val upgradeInfoLiveData = MutableLiveData<Map<String, String>>()

    fun fetchAppVersion() {
        isLoadingLiveData.postValue(true)
        doAsync {
            val response = ApiClient.getRetrofit().create(MainApi::class.java)
                .fetchAppVersion()
                .execute()
            val body = response.body()
            if (body != null) {
                val newVersion = body.data?.get("version")
                val nowVersion =
                    getApplication<App>().packageManager.getPackageInfo(getApplication<App>().packageName, 0).versionName
                uiThread {
                    if (newVersion.equals(nowVersion)) {
                        upgradeInfoLiveData.postValue(null)
                    } else {
                        upgradeInfoLiveData.postValue(body.data)
                    }
                }
            }
            uiThread {
                isLoadingLiveData.postValue(false)
            }
        }
    }
}