package com.example.bridge

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.webkit.WebView
import com.example.bridge.callback.IHandlerCallBack
import com.example.bridge.handler.IBridgeHandler
import com.example.bridge.handler.JSBridgeHandler
import com.example.bridge.log.Logger

object BridgeSDK {

    var handlerCallBack: IHandlerCallBack? = null
    private val mHandler = Handler(Looper.getMainLooper())

    /**
     * @param webView the given WebView
     * @param context activity context
     * @param handlerMap a map containing bridge method names and their handler of js invocation
     * register a javascript interface on the given WebView, which will inject a javascript object named bridgeName with methods in handlerMap
     */
    @SuppressLint("SetJavaScriptEnabled")
    @JvmStatic
    fun registerBridge(
        context: Context,
        webView: WebView,
        handlerMap: Map<String, IBridgeHandler>,
    ) {
        webView.apply {
            settings.javaScriptEnabled = true
            addJavascriptInterface(JSBridgeHandler(context, webView, handlerMap), "__js_bridge__")
        }
    }

    fun runOnUI(task: () -> Unit) {
        mHandler.post(task)
    }


}