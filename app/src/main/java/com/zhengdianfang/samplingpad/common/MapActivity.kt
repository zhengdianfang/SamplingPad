package com.zhengdianfang.samplingpad.common

import android.os.Bundle
import com.amap.api.navi.AMapNaviView
import com.amap.api.navi.enums.NaviType
import com.amap.api.navi.model.NaviLatLng
import com.amap.api.services.core.LatLonPoint
import com.zhengdianfang.samplingpad.App
import com.zhengdianfang.samplingpad.BuildConfig
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.map.BaseMapActivity


class MapActivity : BaseMapActivity() {
    private val latLonPoint by lazy { intent.getParcelableExtra<LatLonPoint>("latLonPoint")!! }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        mAMapNaviView = findViewById(R.id.mapView)
        mAMapNaviView.onCreate(savedInstanceState)
        mAMapNaviView.setAMapNaviViewListener(this)
    }

    override fun onInitNaviSuccess() {
        super.onInitNaviSuccess()
        if (BuildConfig.DEBUG) {
            mAMapNavi.calculateWalkRoute(
                NaviLatLng(39.926911, 116.617201), NaviLatLng(latLonPoint.latitude, latLonPoint.longitude))
        } else {
            mAMapNavi.calculateWalkRoute(
                NaviLatLng(App.INSTANCE.longitude, App.INSTANCE.latitude), NaviLatLng(latLonPoint.latitude, latLonPoint.longitude))
        }
    }

    override fun onCalculateRouteSuccess(ints: IntArray?) {
        super.onCalculateRouteSuccess(ints)
        mAMapNavi.startNavi(NaviType.GPS)
    }

}
