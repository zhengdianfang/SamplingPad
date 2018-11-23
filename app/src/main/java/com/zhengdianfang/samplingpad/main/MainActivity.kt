package com.zhengdianfang.samplingpad.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import com.baidu.trace.LBSTraceClient
import com.baidu.trace.Trace
import com.baidu.trace.model.OnTraceListener
import com.baidu.trace.model.PushMessage
import com.google.gson.Gson
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.RequestCallback
import com.netease.nimlib.sdk.SDKOptions
import com.netease.nimlib.sdk.auth.AuthService
import com.netease.nimlib.sdk.auth.LoginInfo
import com.zhengdianfang.samplingpad.App
import com.zhengdianfang.samplingpad.common.BaseActivity
import com.zhengdianfang.samplingpad.common.entities.OptionItem
import com.zhengdianfang.samplingpad.common.md5
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.main.api.MainApi
import com.zhengdianfang.samplingpad.main.fragments.MainFragment
import org.jetbrains.anko.defaultSharedPreferences
import org.jetbrains.anko.doAsync
import timber.log.Timber


class MainActivity : BaseActivity(), OnTraceListener {

    companion object {
        const val REQUEST_LOCATION_PERMISSION = 0x00001
    }

    private var traceClient: LBSTraceClient? = null
    private var trace: Trace? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadRootFragment(android.R.id.content, MainFragment.newInstance(), false, false)
        applyLocationPermission()
        loadDataOnBackground()
        fetchThirdSdkId()
    }

    override fun onDestroy() {
        super.onDestroy()
        traceClient?.stopTrace(trace, this)
        traceClient?.stopGather(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            var granted = true
            grantResults.forEach {
                if (it != PackageManager.PERMISSION_GRANTED) {
                   granted = false
                }
            }
            if (granted) {
                App.INSTANCE.initLocationClient()
            }
        }
    }

    override fun onStartGatherCallback(status: Int, message: String?) {
        Timber.d("Baidu gather start : $status======$message")
    }

    override fun onStopGatherCallback(status: Int, message: String?) {
        Timber.d("Baidu gather stop: $status======$message")
    }

    override fun onBindServiceCallback(p0: Int, p1: String?) {
    }

    override fun onInitBOSCallback(p0: Int, p1: String?) {
    }


    override fun onPushCallback(p0: Byte, message: PushMessage?) {
        Timber.d("Baidu push  : ${message.toString()}")
    }

    override fun onStartTraceCallback(status: Int, message: String?) {
        if (status == 0) {
            traceClient!!.startGather(this)
        }
        Timber.d("Baidu trace start : $status======$message")
    }

    override fun onStopTraceCallback(status: Int, message: String?) {
        Timber.d("Baidu trace stop: $status======$message")
    }

    private fun applyLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CAMERA
            ), REQUEST_LOCATION_PERMISSION)
        } else {
            App.INSTANCE.initLocationClient()
        }
    }

    private fun saveOptionData2Perference(key: String, value: Array<OptionItem>?) {
        if (value != null) {
            defaultSharedPreferences.edit().putString(key, Gson().toJson(value)).commit()
        }
    }

    private fun loadDataOnBackground() {
        doAsync {
            val inspectionKindResponse = ApiClient.getRetrofit().create(MainApi::class.java).fetchOptionData("inspectionkindlis").execute()
            saveOptionData2Perference("inspectionKindOptions", inspectionKindResponse.body()?.data)

            val linklisResponse = ApiClient.getRetrofit().create(MainApi::class.java).fetchOptionData("linklis").execute()
            saveOptionData2Perference("linklisOptions", linklisResponse.body()?.data)

            val specialarealisResponse = ApiClient.getRetrofit().create(MainApi::class.java).fetchOptionData("specialarealis").execute()
            saveOptionData2Perference("specialarealisOptions", specialarealisResponse.body()?.data)

            val sampleTypeResponse = ApiClient.getRetrofit().create(MainApi::class.java).fetchOptionData("sampletypelis").execute()
            saveOptionData2Perference("sampleTypeOptions", sampleTypeResponse.body()?.data)

            val sampleattributelisResponse = ApiClient.getRetrofit().create(MainApi::class.java).fetchOptionData("sampleattributelis").execute()
            saveOptionData2Perference("sampleattributelisOptions", sampleattributelisResponse.body()?.data)

            val sampleSourceResponse = ApiClient.getRetrofit().create(MainApi::class.java).fetchOptionData("samplesourcelis").execute()
            saveOptionData2Perference("sampleSourceOptions", sampleSourceResponse.body()?.data)

            val packagetypelisResponse = ApiClient.getRetrofit().create(MainApi::class.java).fetchOptionData("packagetypelis").execute()
            saveOptionData2Perference("packagetypelisOptions", packagetypelisResponse.body()?.data)

            val samplepackagelisResponse = ApiClient.getRetrofit().create(MainApi::class.java).fetchOptionData("samplepackagelis").execute()
            saveOptionData2Perference("samplepackagelisOptions", samplepackagelisResponse.body()?.data)

            val samplemethodlisResponse = ApiClient.getRetrofit().create(MainApi::class.java).fetchOptionData("samplemethodlis").execute()
            saveOptionData2Perference("samplemethodlisOptions", samplemethodlisResponse.body()?.data)

            val sampleformlisResponse = ApiClient.getRetrofit().create(MainApi::class.java).fetchOptionData("sampleformlis").execute()
            saveOptionData2Perference("sampleformlisOptions", sampleformlisResponse.body()?.data)

            val storageenvironmentlisResponse = ApiClient.getRetrofit().create(MainApi::class.java).fetchOptionData("storageenvironmentlis").execute()
            saveOptionData2Perference("storageenvironmentlisOptions", storageenvironmentlisResponse.body()?.data)

            val samplereservelisResponse = ApiClient.getRetrofit().create(MainApi::class.java).fetchOptionData("samplereservelis").execute()
            saveOptionData2Perference("samplereservelisOptions", samplereservelisResponse.body()?.data)

            saveOptionData2Perference("yesOrNo", arrayOf(OptionItem(0, "否", 0), OptionItem(1, "是", 0)))

        }
    }

    private fun initBaiduTrace(serviceId: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val packageName = this.packageName
            val isIgnoring = (getSystemService(Context.POWER_SERVICE) as PowerManager).isIgnoringBatteryOptimizations(packageName)
            if (!isIgnoring) {
                val intent = Intent(
                    Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
                intent.data = Uri.parse("package:$packageName")
                try {
                    startActivity(intent)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }

            }
        }
        val serviceId: Long = serviceId.toLong()
        val entityName = "${App.INSTANCE.user?.userName1}"
        val isNeedObjectStorage = false
        trace = Trace(serviceId, entityName, isNeedObjectStorage)
        traceClient = LBSTraceClient(applicationContext)
        // 定位周期(单位:秒)
        val gatherInterval = 5
        val packInterval = 10
        traceClient!!.setInterval(gatherInterval, packInterval)
        traceClient!!.startTrace(trace, this)
    }

    private fun initVideoSdk() {
        runOnUiThread {
            NIMClient.init(this, getLoginInfo(), initSDKOptions())
//            NIMClient.getService(AuthService::class.java).login(getLoginInfo()).setCallback(object: RequestCallback<LoginInfo> {
//                override fun onSuccess(p0: LoginInfo) {
//
//                    Timber.d("im $p0")
//                }
//                override fun onFailed(p0: Int) {
//                    Timber.d("im $p0")
//                }
//
//                override fun onException(p0: Throwable?) {
//                    Timber.d("im $p0")
//
//                }
//
//            })
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


    private fun fetchThirdSdkId() {
        doAsync {
            val response = ApiClient.getRetrofit()
                .create(MainApi::class.java)
                .fetchAcountId(App.INSTANCE.user?.id ?: "")
                .execute()
            val body = response.body()
            if (body != null) {
//                initVideoSdk()
                initBaiduTrace(body.data?.get("serviceId") ?: "")
            }
        }
    }


}
