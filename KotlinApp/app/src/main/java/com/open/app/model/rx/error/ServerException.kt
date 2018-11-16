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

class ServerException : RuntimeException {
    var code: Int = 0
    var msg: String = ""

    constructor(code: Int, msg: String) {
        this.code = code
        this.msg = msg
    }
}