package com.zhengdianfang.samplingpad.user

import android.os.Bundle
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.user.fragments.FirstLoginFragment
import me.yokeyword.fragmentation.SupportActivity

class LoginActivity : SupportActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadRootFragment(android.R.id.content, FirstLoginFragment())
    }

}
