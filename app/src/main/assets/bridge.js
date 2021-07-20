(function(){
    if(typeof __js_bridge__ === "undefined"){
        console.error("__js_bridge__ is undefined");
        return;
    }

    class CallBack{
        constructor(id,onSuccess,onFail){
            this.id = id;
            this.onSuccess = onSuccess;
            this.onFail = onFail;
        }
    }

    var id = 0;
    let callbackPools = new Map()


    function saveCallBack(callback){
        callbackPools.set(callback.id,callback);
    }

    function removeCallBack(callback){
        callbackPools.delete(callback.id)
    }


    function jsCallNative(name,params,successCallBack,onFail){
        const [moduleName,methodName] = name.split('.');
        const callback = new CallBack(id,successCallBack,onFail);
        saveCallBack(callback);
        __js_bridge__.handleJSInvocation(id,moduleName,methodName,"");
        id = id + 1;
    }

    function __js_handler__(id,params){
        const callback = callbackPools.get(id);
        // todo
        callback.onSuccess()
        callbackPools.delete(id);
    }

    // function nativeCallJs(id,){

    // }

    window.jsCallNative = jsCallNative;
    window.__js_handler__ = __js_handler__;

    console.log("js-bridge.js finished");
})()