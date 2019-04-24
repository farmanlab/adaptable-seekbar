package com.farmanlab.adaptableseekbar

import android.util.Log

internal object Logger {
    val isDebug: Boolean = BuildConfig.DEBUG

    fun v(msg: String) {
        if (isDebug) {
            Log.v(TAG, msg)
        }
    }

    fun d(msg: String) {
        if (isDebug) {
            Log.d(TAG, msg)
        }
    }

    fun i(msg: String) {
        if (isDebug) {
            Log.i(TAG, msg)
        }
    }

    fun w(msg: String, throwable: Throwable? = null) {
        if (isDebug) {
            throwable?.let { Log.w(TAG, msg, it) } ?: Log.w(TAG, msg)
        }
    }

    fun e(msg: String, throwable: Throwable? = null) {
        if (isDebug) {
            throwable?.let { Log.e(TAG, msg, it) } ?: Log.e(TAG, msg)
        }
    }

    @JvmStatic
    private val TAG: String = this::class.java.simpleName
}
