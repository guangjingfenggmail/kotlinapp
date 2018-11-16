package com.open.app.model.rx.error

/**
 * ****************************************************************************************************************************************************************************
 *
 * @author :guangjing.feng
 * @createTime: 2018/11/16.
 * @version:1.1.0
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *
 * *****************************************************************************************************************************************************************************
 **/

class ApiException:RuntimeException{
    var code:Int = -100
    var msg:String = ""

    constructor(throwable:Throwable,code:Int):super(throwable){
        this.code = code
    }
}