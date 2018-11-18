package com.zhengdianfang.samplingpad.user

import android.os.Bundle
import com.zhengdianfang.samplingpad.App
import com.zhengdianfang.samplingpad.user.fragments.FirstLoginFragment
import me.yokeyword.fragmentation.SupportActivity
import timber.log.Timber
import java.util.*

class LoginActivity : SupportActivity(){

    var cookieKey = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadRootFragment(android.R.id.content, FirstLoginFragment())
        Timber.d("get token ${App.INSTANCE.token}")
    }


    fun generateCookieKey() {
        this.cookieKey = UUID.randomUUID().toString()
    }
}
