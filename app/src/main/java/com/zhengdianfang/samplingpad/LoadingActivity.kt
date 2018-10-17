package com.zhengdianfang.samplingpad

import android.content.Intent
import android.os.Bundle
import com.zhengdianfang.samplingpad.common.BaseActivity
import com.zhengdianfang.samplingpad.main.MainActivity
import com.zhengdianfang.samplingpad.user.LoginActivity
import org.jetbrains.anko.defaultSharedPreferences

class LoadingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        if (defaultSharedPreferences.getString("token", "").isNullOrEmpty()) {
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }
        finish()
    }
}
