package com.zhengdianfang.samplingpad.login.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.main.MainActivity
import kotlinx.android.synthetic.main.fragment_user_name_login.*
import me.yokeyword.fragmentation.SupportFragment

class UserNameLoginFragment : SupportFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_name_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loginButton.setOnClickListener {
            startActivity(Intent(context, MainActivity::class.java))
        }
    }
}
