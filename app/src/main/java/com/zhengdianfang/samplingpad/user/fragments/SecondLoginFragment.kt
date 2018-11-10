package com.zhengdianfang.samplingpad.user.fragments


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.zhengdianfang.samplingpad.App

import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.common.BaseFragment
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.main.MainActivity
import kotlinx.android.synthetic.main.fragment_second_login.*
import timber.log.Timber

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

        userNameLoginFragmentViewModel.tokenLiveData.observe(this, Observer { loginToken ->
            Timber.d("login token: %s", loginToken)
            if (loginToken.isNullOrEmpty().not()) {
                startActivity(Intent(context, MainActivity::class.java))
                App.INSTANCE.secondUsername = userNameEditText.text.toString()
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
            userNameLoginFragmentViewModel.loginSecond(token ,username, password, rememberMe)
        }
    }

}
