package com.zhengdianfang.samplingpad

import android.app.Application
import com.badoo.mvicore.consumer.middleware.LoggingMiddleware
import com.badoo.mvicore.consumer.middleware.PlaybackMiddleware
import com.badoo.mvicore.consumer.middlewareconfig.MiddlewareConfiguration
import com.badoo.mvicore.consumer.middlewareconfig.Middlewares
import com.badoo.mvicore.consumer.middlewareconfig.WrappingCondition
import com.badoo.mvicore.consumer.playback.MemoryRecordStore
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber

class App: Application() {

    val recordStore by lazy { MemoryRecordStore(logger = { Timber.d(it) }, playbackScheduler = AndroidSchedulers.mainThread()) }

    override fun onCreate() {
        super.onCreate()
        middleware()
    }

    private fun middleware() {
        Middlewares.configurations.add(
            MiddlewareConfiguration(
                condition = WrappingCondition.Always,
                factories = listOf({ consumer -> LoggingMiddleware(consumer, { Timber.d(it) })})
            )
        )

        Middlewares.configurations.add(
            MiddlewareConfiguration(
                condition = WrappingCondition.AllOf(
                    WrappingCondition.Conditional { BuildConfig.DEBUG },
                    WrappingCondition.AnyOf(
                        WrappingCondition.IsNamed,
                        WrappingCondition.IsStandalone
                    )
                ),
                factories = listOf(
                    { consumer -> PlaybackMiddleware(consumer, recordStore) { Timber.d(it) } }
                )

            )

        )
    }

}