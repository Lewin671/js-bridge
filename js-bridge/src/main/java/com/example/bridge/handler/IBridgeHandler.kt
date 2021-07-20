package com.example.bridge.handler

import android.content.Context
import com.alibaba.fastjson.JSONObject

/**
 * the interface of handle invocation from js
 */
interface IBridgeHandler {
    fun handleJSInvocation(
        context: Context,
        methodName: String,
        params: JSONObject
    )
}