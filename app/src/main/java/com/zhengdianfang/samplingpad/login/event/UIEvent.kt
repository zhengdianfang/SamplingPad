package com.zhengdianfang.samplingpad.login.event

import com.zhengdianfang.samplingpad.store.UserState

sealed class LoginUIEvent {
    data class LoginButtonClicked(val username: String, val password: String): LoginUIEvent()
}

class LoginUIEventTransformer: (LoginUIEvent) -> UserState.Wish {
    override fun invoke(event: LoginUIEvent): UserState.Wish = when(event) {
        is LoginUIEvent.LoginButtonClicked -> UserState.Wish.Login(event.username, event.password)
    }
}