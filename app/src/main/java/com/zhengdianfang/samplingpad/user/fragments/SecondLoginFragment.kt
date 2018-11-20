package com.zhengdianfang.samplingpad.user.fragments


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.BaseFragment
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.main.MainActivity
import com.zhengdianfang.samplingpad.user.LoginActivity
import kotlinx.android.synthetic.main.fragment_second_login.*

class SecondLoginFragment : BaseFragment() {

    private val token by lazy { arguments?.getString("token")!! }

    private val userNameLoginFragmentViewModel by lazy { ViewModelProviders.of(this).get(UserNameLoginFragmentViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_second_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
        setupViewModel()
    }

    private fun setupViewModel() {
        userNameLoginFragmentViewModel.errorLiveData.observe(this, Observer { message ->
           if (message.isNullOrEmpty().not()) {
               Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
           }
        })

        userNameLoginFragmentViewModel.userLiveData.observe(this, Observer { loginToken ->
            if (loginToken != null) {
                startActivity(Intent(context, MainActivity::class.java))
                Toast.makeText(context, getString(R.string.login_successful), Toast.LENGTH_SHORT).show()
                activity?.finish()
            } else {
                (ApiClient.getHttpClient().cookieJar() as ApiClient.AppCookieJar).clearCookies()
                Toast.makeText(context, getString(R.string.login_failure), Toast.LENGTH_SHORT).show()
            }
        })

        userNameLoginFragmentViewModel.isLoadingLiveData.observe(this, Observer { isLoading ->
            if (isLoading == true) {
                startLoading()
            } else {
                stopLoading()
            }
        })
    }

    private fun setupViews() {
        loginButton.setOnClickListener {
            val username = userNameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val rememberMe =  rememberMeRadioButton.isChecked
            userNameLoginFragmentViewModel.loginSecond((context as LoginActivity).cookieKey, token ,username, password, rememberMe)
        }
    }

}
