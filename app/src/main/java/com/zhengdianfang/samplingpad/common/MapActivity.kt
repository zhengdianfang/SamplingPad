package com.zhengdianfang.samplingpad.common

import android.app.Activity
import android.content.res.Resources
import android.os.Bundle
import com.amap.api.navi.AMapNavi
import com.amap.api.navi.AMapNaviListener
import com.amap.api.navi.AMapNaviViewListener
import com.amap.api.navi.enums.NaviType
import com.amap.api.navi.model.*
import com.autonavi.tbt.TrafficFacilityInfo
import com.zhengdianfang.samplingpad.R
import kotlinx.android.synthetic.main.activity_map.*
import com.amap.api.navi.model.NaviLatLng
import com.amap.api.services.core.LatLonPoint
import com.zhengdianfang.samplingpad.App
import com.zhengdianfang.samplingpad.BuildConfig
import kotlinx.android.synthetic.main.toolbar_layout.*


class MapActivity : BaseActivity(),  AMapNaviListener, AMapNaviViewListener {

    private val latLonPoint by lazy { intent.getParcelableExtra<LatLonPoint>("latLonPoint")!! }

    private val aMapNavi by lazy {
        AMapNavi.getInstance(this.applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        mapView.setAMapNaviViewListener(this)
        mapView.onCreate(savedInstanceState)
        aMapNavi.addAMapNaviListener(this)

        backButton.setOnClickListener {
            finish()
        }
    }

    override fun getResources(): Resources {
        return baseContext.resources
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
        aMapNavi.stopNavi()
        aMapNavi.destroy()
    }

    override fun onNaviInfoUpdate(p0: NaviInfo?) {
    }

    override fun onCalculateRouteSuccess(p0: IntArray?) {
    }

    override fun onCalculateRouteSuccess(result: AMapCalcRouteResult?) {
        aMapNavi.startNavi(NaviType.GPS)
    }

    override fun onCalculateRouteFailure(p0: Int) {
    }

    override fun onCalculateRouteFailure(p0: AMapCalcRouteResult?) {
    }

    override fun onServiceAreaUpdate(p0: Array<out AMapServiceAreaInfo>?) {
    }

    override fun onEndEmulatorNavi() {
    }

    override fun onArrivedWayPoint(p0: Int) {
    }

    override fun onArriveDestination() {
    }

    override fun onPlayRing(p0: Int) {
    }

    override fun onTrafficStatusUpdate() {
    }

    override fun onGpsOpenStatus(p0: Boolean) {
    }

    override fun updateAimlessModeCongestionInfo(p0: AimLessModeCongestionInfo?) {
    }

    override fun showCross(p0: AMapNaviCross?) {
    }

    override fun onGetNavigationText(p0: Int, p1: String?) {
    }

    override fun onGetNavigationText(p0: String?) {
    }

    override fun updateAimlessModeStatistics(p0: AimLessModeStat?) {
    }

    override fun hideCross() {
    }

    override fun onInitNaviFailure() {
    }

    override fun onInitNaviSuccess() {
        if (BuildConfig.DEBUG) {
            aMapNavi.calculateWalkRoute(
                NaviLatLng(39.926911, 116.617201), NaviLatLng(latLonPoint.latitude, latLonPoint.longitude))
        } else {

            aMapNavi.calculateWalkRoute(
                NaviLatLng(App.INSTANCE.longitude, App.INSTANCE.latitude), NaviLatLng(latLonPoint.latitude, latLonPoint.longitude))
        }

    }

    override fun onReCalculateRouteForTrafficJam() {
    }

    override fun updateIntervalCameraInfo(p0: AMapNaviCameraInfo?, p1: AMapNaviCameraInfo?, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLaneInfo() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNaviInfoUpdated(p0: AMapNaviInfo?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showModeCross(p0: AMapModelCross?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateCameraInfo(p0: Array<out AMapNaviCameraInfo>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideModeCross() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLocationChange(p0: AMapNaviLocation?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onReCalculateRouteForYaw() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartNavi(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun notifyParallelRoad(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun OnUpdateTrafficFacility(p0: AMapNaviTrafficFacilityInfo?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun OnUpdateTrafficFacility(p0: Array<out AMapNaviTrafficFacilityInfo>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun OnUpdateTrafficFacility(p0: TrafficFacilityInfo?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNaviRouteNotify(p0: AMapNaviRouteNotifyData?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLaneInfo(p0: Array<out AMapLaneInfo>?, p1: ByteArray?, p2: ByteArray?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLaneInfo(p0: AMapLaneInfo?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNaviTurnClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onScanViewButtonClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLockMap(p0: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onMapTypeChanged(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNaviCancel() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNaviViewLoaded() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNaviBackClick(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNaviMapMode(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNextRoadClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNaviViewShowMode(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNaviSetting() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
