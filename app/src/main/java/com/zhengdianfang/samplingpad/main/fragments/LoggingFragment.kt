package com.zhengdianfang.samplingpad.main.fragments

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_logging.*
import com.baidu.trace.api.track.HistoryTrackResponse
import com.baidu.trace.api.track.OnTrackListener
import com.baidu.trace.api.track.HistoryTrackRequest
import com.zhengdianfang.samplingpad.main.MainActivity
import timber.log.Timber


class LoggingFragment: BaseFragment() {

    private val timerHandler = Handler()
    private var entityName = ""
    private val callback = {
            fetchBaiduTrace(entityName)
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_logging, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timerHandler.removeCallbacks(callback)
    }

    fun updateBaiduStaticLogging(entityName: String) {
        val baiduStaticLogging = "appKey: 0y4bU2GKCGwPihekeA2ThAtrwsN6ioSt\n" +
            "serviceId: 205477\n" +
            "entityName: $entityName\n" +
            "开启定位: ${isLocationEnabled()}\n"
        staticLoggingView.text = baiduStaticLogging


    }

    fun updateDyamicLogging(message: String) {
       dynamicLogginView?.append("$message\n=====================\n")
    }

    fun startTimer(entityName: String) {
        this.entityName = entityName
        timerHandler.postDelayed(callback, 5000)
    }

    private fun fetchBaiduTrace(entityName: String) {
        val tag = 1
        val serviceId: Long = 205477
        val historyTrackRequest = HistoryTrackRequest(tag, serviceId, entityName)
        val startTime = System.currentTimeMillis() / 1000 - 5000
        val endTime = System.currentTimeMillis() / 1000
        historyTrackRequest.startTime = startTime
        historyTrackRequest.endTime = endTime
        val mTrackListener = object : OnTrackListener() {
            override fun onHistoryTrackCallback(response: HistoryTrackResponse?) {
                Timber.d("response: $response")
                updateDyamicLogging("${response?.entityName}\n" +
                    "start latitude: ${response?.startPoint?.location?.latitude}\n" +
                    "start longitude: ${response?.startPoint?.location?.longitude}\n" +
                    "end latitude: ${response?.endPoint?.location?.latitude}\n" +
                    "end longitude: ${response?.endPoint?.location?.longitude}\n" +
                    "tollDistance: ${response?.tollDistance}\n"
                )
                timerHandler.postDelayed({
                    fetchBaiduTrace(entityName)
                }, 5000)
            }
        }
        (activity as? MainActivity)?.traceClient?.queryHistoryTrack(historyTrackRequest, mTrackListener)

    }

    private fun isLocationEnabled(): Boolean {
        var locationMode: Int
        val locationProviders: String
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context?.contentResolver, Settings.Secure.LOCATION_MODE)
            } catch (e: Settings.SettingNotFoundException) {
                e.printStackTrace()
                return false
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF
        } else {
            locationProviders = Settings.Secure.getString(context?.contentResolver, Settings.Secure.LOCATION_PROVIDERS_ALLOWED)
            return !TextUtils.isEmpty(locationProviders)
        }
    }
}