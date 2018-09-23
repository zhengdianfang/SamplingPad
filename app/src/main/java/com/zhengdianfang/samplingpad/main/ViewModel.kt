package com.zhengdianfang.samplingpad.main

import com.zhengdianfang.samplingpad.store.UserState

data class ViewModel (
    val isLoading: Boolean,
    val username: String
)

object ViewModelTransformer: (UserState.State) -> ViewModel {
    override fun invoke(state: UserState.State): ViewModel =
        ViewModel(state.isLoading, state.user?.name ?: "")
}