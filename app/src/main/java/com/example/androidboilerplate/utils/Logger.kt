package com.example.androidboilerplate.utils

import android.util.Log
import com.example.androidboilerplate.BuildConfig

class Logger {
    companion object {

        private const val TAG: String = "Android Boilerplate"

        fun d(msg: String) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, msg)
            }
        }

        fun e(msg: String) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, msg)
            }
        }

        fun i(msg: String) {
            if (BuildConfig.DEBUG) {
                Log.i(TAG, msg)
            }
        }

        fun w(msg: String) {
            if (BuildConfig.DEBUG) {
                Log.w(TAG, msg)
            }
        }

    }
}