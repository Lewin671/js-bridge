package com.example.bridge.log

import android.util.Log

class Logger {
    companion object {
        fun v(tag: String, msg: String) {
            Log.v(tag, msg)
        }

        fun d(tag: String, msg: String) {
            Log.e(tag, msg)
        }

        fun i(tag: String, msg: String) {
            Log.i(tag, msg)
        }

        fun w(tag: String, msg: String) {
            Log.w(tag, msg)
        }

        fun e(tag: String, msg: String) {
            Log.e(tag, msg)
        }
    }
}