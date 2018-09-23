package com.zhengdianfang.samplingpad.login

import android.os.Bundle
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.active
import kotlinx.android.synthetic.main.activity_login.*
import me.yokeyword.fragmentation.SupportActivity
import me.yokeyword.fragmentation.SupportFragment

class LoginActivity : SupportActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupViews()
    }

    private fun setupViews() {
        switchLoginRadioGroup.setOnCheckedChangeListener { _, buttonId ->
            when(buttonId) {
                R.id.userNameRadioButton -> {
                    userNameRadioButton.active(R.drawable.login_tab_active_background, R.color.colorPrimary)
                    phoneNumberRadioButton.active(R.drawable.transparent, android.R.color.black)
                    showHideFragment(userNameLoginFragment as SupportFragment, phoneNumberLoginFragment as SupportFragment)
                }
                R.id.phoneNumberRadioButton-> {
                    phoneNumberRadioButton.active(R.drawable.login_tab_active_background, R.color.colorPrimary)
                    userNameRadioButton.active(R.drawable.transparent, android.R.color.black)
                    showHideFragment(phoneNumberLoginFragment as SupportFragment, userNameLoginFragment as SupportFragment)
                }
            }
        }
        switchLoginRadioGroup.check(R.id.userNameRadioButton)
    }
}
