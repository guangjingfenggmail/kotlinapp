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
class ErrorType {

    companion object {
        /**
         * 请求成功
         */
        val SUCCESS: Int = 1
        /**
         * 未知错误
         */
        val UNKONW: Int = 1000

        /**
         * 解析错误
         */
        val PARSE_ERROR: Int = 1001
        /**
         * 网络错误
         */
        val NETWORK_ERROR: Int = 1002

        /**
         * 解析对象为空
         */
        val EMPTY_BEAN: Int = 1004


        /**
         *
         */
        val HTTP_ERROR: Int = 1003
    }


}