package com.zhengdianfang.samplingpad

import android.app.Application
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.google.gson.Gson
import com.netease.nim.avchatkit.AVChatKit
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.RequestCallback
import com.netease.nimlib.sdk.SDKOptions
import com.netease.nimlib.sdk.auth.AuthService
import com.netease.nimlib.sdk.auth.LoginInfo
import com.netease.nimlib.sdk.util.NIMUtil
import com.zhengdianfang.samplingpad.common.md5
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.user.LoginActivity
import com.zhengdianfang.samplingpad.user.api.UserApi
import com.zhengdianfang.samplingpad.user.entities.User
import org.jetbrains.anko.defaultSharedPreferences
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.runOnUiThread
import timber.log.Timber
import java.io.InputStream
import com.zhengdianfang.samplingpad.main.MainActivity
import com.netease.nim.avchatkit.config.AVChatOptions
import com.netease.nim.avchatkit.AVChatKit.getUserInfoProvider
import com.netease.nim.avchatkit.model.IUserInfoProvider
import com.netease.nimlib.sdk.uinfo.model.UserInfo


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
                val json = defaultSharedPreferences.getString("user", "")
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
                defaultSharedPreferences.edit().putString("user", Gson().toJson(value)).apply()
            } else {
                defaultSharedPreferences.edit().putString("user", "").apply()
            }
            field = value
        }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        timber()
        initGlide()
        initVideoSdk()
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
    private fun initVideoSdk() {
        NIMClient.init(this, getLoginInfo(), initSDKOptions())
        if (NIMUtil.isMainProcess(this)) {
            val avChatOptions = object : AVChatOptions() {
                override fun logout(context: Context) {
                }
            }
            AVChatKit.init(avChatOptions)
            AVChatKit.setContext(this)
            AVChatKit.setUserInfoProvider(object : IUserInfoProvider() {
                override fun getUserInfo(account: String): UserInfo {
                    return object: UserInfo {
                        override fun getAvatar() = ""
                        override fun getName() = ""
                        override fun getAccount() = "zdf345"

                    }
                }

                override fun getUserDisplayName(account: String): String {
                    return ""
                }
            })
        }
    }

    private fun initSDKOptions(): SDKOptions {
        val options = SDKOptions()
        options.preloadAttach = true
        return options
    }

    private fun getLoginInfo(): LoginInfo? {
        val account = App.INSTANCE.user?.videoAccount
        val token = App.INSTANCE.user?.videoToken

        return if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
            LoginInfo(account, token)
        } else {
            LoginInfo("zdf345", "111111".md5())
        }
    }

    inner class ReleaseTree : Timber.DebugTree()
}