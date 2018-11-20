package com.zhengdianfang.samplingpad

import android.app.Application
import android.content.Intent
import android.text.TextUtils
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.google.gson.Gson
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.user.LoginActivity
import com.zhengdianfang.samplingpad.user.api.UserApi
import com.zhengdianfang.samplingpad.user.entities.User
import org.jetbrains.anko.defaultSharedPreferences
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.runOnUiThread
import timber.log.Timber
import java.io.InputStream

class App: Application() {

    companion object {
        lateinit var INSTANCE: App
    }

    private var aMapLocationClient: AMapLocationClient? = null
    var latitude = 0.0
    var longitude = 0.0

    var user: User? = null
        get() {
            if (field == null) {
                val json = defaultSharedPreferences.getString("firstUsername", "")
                if (TextUtils.isEmpty(json).not()) {
                    Timber.d("get login users: %s", field)
                    field = Gson().fromJson(json, User::class.java)
                }
            }
            return field
        }
        set(value) {
            Timber.d("set login users: %s", value)
            if (value != null) {
                defaultSharedPreferences.edit().putString("firstUsername", Gson().toJson(value)).apply()
            } else {
                defaultSharedPreferences.edit().putString("firstUsername", "").apply()
            }
            field = value
        }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        timber()
        initGlide()
    }

    override fun onTerminate() {
        super.onTerminate()
        aMapLocationClient?.onDestroy()
    }

    private fun initGlide() {
        val factory = OkHttpUrlLoader.Factory(ApiClient.getHttpClient())
        Glide.get(this).registry.replace(GlideUrl::class.java, InputStream::class.java, factory)
    }

    fun logout() {
        synchronized(this) {
            if (App.INSTANCE.user != null) {
                doAsync {
                    ApiClient.getRetrofit().create(UserApi::class.java)
                        .logout()
                        .execute()
                }
                App.INSTANCE.user = null
                ApiClient.reset()
                startActivity(
                    Intent(this, LoginActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                )
            }
        }
    }

    private fun timber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }

    fun initLocationClient() {
        doAsync {
            aMapLocationClient = AMapLocationClient(this.weakRef.get())
            val aMapLocationClientOption = AMapLocationClientOption()
            aMapLocationClientOption.locationMode = AMapLocationClientOption.AMapLocationMode.Battery_Saving
            aMapLocationClient?.setLocationOption(aMapLocationClientOption)
            aMapLocationClient?.setLocationListener {
                if (it.errorCode == 0) {
                    App.INSTANCE.longitude = it.longitude
                    App.INSTANCE.latitude = it.latitude

                }
            }
            aMapLocationClient?.startLocation()
        }
    }

    inner class ReleaseTree : Timber.DebugTree()
}