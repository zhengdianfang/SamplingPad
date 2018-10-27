package com.zhengdianfang.samplingpad.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.MotionEvent
import com.zhengdianfang.samplingpad.App
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.BaseActivity
import com.zhengdianfang.samplingpad.common.active
import com.zhengdianfang.samplingpad.main.fragments.MainFragment
import kotlinx.android.synthetic.main.activity_main.*
import me.yokeyword.fragmentation.SupportActivity

class MainActivity : BaseActivity() {

    companion object {
        const val REQUEST_LOCATION_PERMISSION = 0x00001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadRootFragment(android.R.id.content, MainFragment.newInstance(), false, false)
        applyLocationPermission()
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

    private fun applyLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE
            ), REQUEST_LOCATION_PERMISSION)
        } else {
            App.INSTANCE.initLocationClient()
        }
    }
}
