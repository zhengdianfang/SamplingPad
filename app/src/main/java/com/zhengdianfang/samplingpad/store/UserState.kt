package com.zhengdianfang.samplingpad.store

import android.os.Parcelable
import com.badoo.mvicore.element.*
import com.badoo.mvicore.feature.ActorReducerFeature
import com.zhengdianfang.samplingpad.api.ApiClient
import com.zhengdianfang.samplingpad.api.UserApi
import com.zhengdianfang.samplingpad.store.UserState.State
import com.zhengdianfang.samplingpad.store.UserState.Wish
import com.zhengdianfang.samplingpad.store.UserState.Effect
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.parcel.Parcelize

class UserState(
    timeCapsule: TimeCapsule<Parcelable>? = null
) : ActorReducerFeature<Wish, Effect, State, Nothing>(
    initialState = timeCapsule?.get(State::class.java) ?: State(),
    actor = ActorImpl(),
    reducer = ReducerImpl()
) {


    @Parcelize
    data class State(
        val isLoading: Boolean = false,
        val user: User? = null
    ) : Parcelable

    sealed class Wish {
        data class Login(val username: String, val password: String): Wish()
    }

    @Parcelize
    data class User(val id: Int, val name: String): Parcelable

    sealed class Effect {
        object StartedLoading: Effect()
        data class LoginSuccess(var user: User?): Effect()
        data class LoginFail(val throwable: Throwable): Effect()
    }

    class ActorImpl : Actor<State, Wish, Effect> {
        override fun invoke(state: State, wish: Wish): Observable<out Effect> = when(wish) {
            is Wish.Login ->
                if (state.isLoading.not()) {
                    ApiClient.INSTANCE
                        .create(UserApi::class.java)
                        .login(wish.username, wish.password)
                        .map { Effect.LoginSuccess(it) as Effect }
                        .startWith { Observable.just(Effect.StartedLoading) }
                        .onErrorReturn { Effect.LoginFail(it) }
                        .observeOn(AndroidSchedulers.mainThread())
                } else {
                    Observable.empty()
                }

        }

    }

    class ReducerImpl : Reducer<State, Effect> {
        override fun invoke(state: State, effect: Effect): State = when(effect) {
            is Effect.StartedLoading -> state.copy(isLoading = true)
            is Effect.LoginSuccess -> state.copy(isLoading = false, user = effect.user)
            is Effect.LoginFail -> state.copy(isLoading = false, user = null)
        }
    }
}