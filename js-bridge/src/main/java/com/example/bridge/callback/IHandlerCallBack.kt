package com.example.bridge.callback

interface IHandlerCallBack {
    fun onSuccess(msg: String)
    fun onFail(msg: String)
}