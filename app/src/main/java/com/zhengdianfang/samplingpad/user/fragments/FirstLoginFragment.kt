package com.zhengdianfang.samplingpad.user.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.zhengdianfang.samplingpad.App
import com.zhengdianfang.samplingpad.R
import com.zhengdianfang.samplingpad.user.api.UserApi
import com.zhengdianfang.samplingpad.common.BaseFragment
import com.zhengdianfang.samplingpad.http.ApiClient
import com.zhengdianfang.samplingpad.main.MainActivity
import com.zhengdianfang.samplingpad.user.LoginActivity
import kotlinx.android.synthetic.main.fragment_first_login.*
import timber.log.Timber
import kotlin.math.log


class FirstLoginFragment : BaseFragment() {

    private val userNameLoginFragmentViewModel by lazy { ViewModelProviders.of(this).get(UserNameLoginFragmentViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_first_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.d("start FirstLoginFragment")
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
                val fragment = SecondLoginFragment()
                val bundle = Bundle()
                bundle.putString("token", loginToken)
                App.INSTANCE.firstUsername = userNameEditText.text.toString()
                fragment.arguments = bundle
                start(fragment)
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
        setupCodeImageView()
        loginButton.setOnClickListener {
            val username = userNameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val code = imageCodeEditText.text.toString()
            val rememberMe =  rememberMeRadioButton.isChecked
            Timber.d("now codeKey : ${(context as LoginActivity).cookieKey}")
            userNameLoginFragmentViewModel.login((context as LoginActivity).cookieKey, username, password, code, rememberMe)
        }
    }

    private fun setupCodeImageView() {
        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)

        (context as LoginActivity).generateCookieKey()
        Glide.with(this).load(UserApi.VERIFY_CODE_URL)
            .apply(requestOptions)
            .into(codeImageView)

        codeImageView.setOnClickListener {
            (context as LoginActivity).generateCookieKey()
            Glide.with(this).clear(codeImageView)
            Glide.with(this).load(UserApi.VERIFY_CODE_URL)
                .apply(requestOptions)
                .into(codeImageView)

        }
    }
}
