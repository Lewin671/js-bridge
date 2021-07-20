package com.example.jsbridge

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.fastjson.JSONObject
import com.example.bridge.BridgeSDK
import com.example.bridge.handler.IBridgeHandler
import com.example.bridge.log.Logger

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WebView.setWebContentsDebuggingEnabled(true)

        val handlerMap = HashMap<String, IBridgeHandler>()

        handlerMap["pha"] = object : IBridgeHandler {
            override fun handleJSInvocation(
                context: Context,
                methodName: String,
                params: JSONObject
            ) {
                Toast.makeText(context, "handleJsInvocation pha.$methodName", Toast.LENGTH_LONG)
                    .show()
            }
        }

        handlerMap["pha1"] = handlerMap["pha"]!!

        findViewById<WebView>(R.id.webView).apply {
            loadUrl("file:///android_asset/index.html")
            BridgeSDK.registerBridge(context, this, handlerMap)

            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    Logger.d(TAG, "onPageStarted")
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    Logger.d(TAG, "onPageFinished")
                }
            }

            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    if (newProgress > 0) {
                        val injectJs = "javascript:window.__api_list__ = ${
                            handlerMap.keys.map {
                                "'$it'"
                            }
                        };console.log('inject api list success with $newProgress');"
                        view?.evaluateJavascript(injectJs, null)
                    }

                    Logger.d(TAG, "onProgressChanged with $newProgress")
                }

                override fun onReceivedTitle(view: WebView?, title: String?) {
                    Logger.d(TAG, "onReceivedTitle")
                }
            }
        }
    }
}