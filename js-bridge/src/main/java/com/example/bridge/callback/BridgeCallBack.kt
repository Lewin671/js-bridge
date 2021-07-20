package com.example.bridge.callback

import com.example.bridge.BridgeSDK

object BridgeCallBack : IHandlerCallBack {
    private const val TAG = "BridgeCallBack"

    override fun onSuccess(msg: String) {
        BridgeSDK.handlerCallBack?.onSuccess(msg)
    }

    override fun onFail(msg: String) {
        BridgeSDK.handlerCallBack?.onFail(msg)
    }

}