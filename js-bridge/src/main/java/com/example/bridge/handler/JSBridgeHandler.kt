package com.example.bridge.handler

import android.content.Context
import android.text.TextUtils
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.alibaba.fastjson.JSONObject
import com.example.bridge.BridgeSDK
import com.example.bridge.log.Logger


class JSBridgeHandler(
    private val mContext: Context,
    private val mWebView: WebView,
    private val mHandlerMap: Map<String, IBridgeHandler>
) {
    companion object {
        private const val TAG = "JSBridgeHandler"
        private const val JS_HANDLER = "__js_handler__"
        private const val NATIVE_TO_JS_PREFIX = "javascript:$JS_HANDLER & $JS_HANDLER"
    }

    @JavascriptInterface
    fun handleJSInvocation(
        callBackId: Int,
        moduleName: String,
        methodName: String,
        params: String
    ) {
        if (TextUtils.isEmpty(moduleName) || TextUtils.isEmpty(methodName)) {
            Logger.e(TAG, "module name or method name is empty")
            return
        }

        var paramsObject: JSONObject? = null
        try {
            paramsObject = JSONObject.parseObject(params)
        } catch (e: Exception) {
            Logger.e(TAG, "parse params error")
            return
        }

        if (paramsObject == null) {
            paramsObject = JSONObject()
        }

        val bridgeHandler: IBridgeHandler? = mHandlerMap[moduleName]

        if (bridgeHandler == null) {
            Logger.e(TAG, "can not find the handler for this module: $moduleName")
            return
        }

        bridgeHandler.handleJSInvocation(mContext, methodName, paramsObject)

        val callBackJs = "$NATIVE_TO_JS_PREFIX($callBackId)"
        BridgeSDK.runOnUI {
            mWebView.evaluateJavascript(callBackJs, null)
        }
    }
}