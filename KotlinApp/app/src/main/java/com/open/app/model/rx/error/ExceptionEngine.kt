package com.open.app.model.rx.error

import android.net.ParseException
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.adapter.rxjava.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException

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

class ExceptionEngine {
    companion object {


        //对应HTTP的状态码
        @JvmStatic
        private val FAIL = 0

        @JvmStatic
        private val UNAUTHORIZED = 401

        @JvmStatic
        private val FORBIDDEN = 403

        @JvmStatic
        private val NOT_FOUND = 404

        @JvmStatic
        private val REQUEST_TIMEOUT = 408

        @JvmStatic
        private val INTERNAL_SERVER_ERROR = 500

        @JvmStatic
        private val BAD_GATEWAY = 502

        @JvmStatic
        private val SERVICE_UNAVAILABLE = 503

        @JvmStatic
        private val GATEWAY_TIMEOUT = 504

        @JvmStatic
        fun handleException(e: Throwable): ApiException {
            val ex: ApiException
            if (e is ServerException) {             //HTTP错误
                val httpException = e as ServerException
                ex = ApiException(e, ErrorType.HTTP_ERROR)
                when (httpException.code) {
                    FAIL -> ex.msg = "userName or passWord is error!"
                    UNAUTHORIZED -> ex.msg = "当前请求需要用户验证"
                    FORBIDDEN -> ex.msg = "服务器已经理解请求，但是拒绝执行它"
                    NOT_FOUND -> ex.msg = "服务器异常，请稍后再试"
                    REQUEST_TIMEOUT -> ex.msg = "请求超时"
                    GATEWAY_TIMEOUT -> ex.msg = "作为网关或者代理工作的服务器尝试执行请求时，未能及时从上游服务器（URI标识出的服务器，例如HTTP、FTP、LDAP" + "）或者辅助服务器（例如DNS）收到响应"
                    INTERNAL_SERVER_ERROR -> ex.msg = "服务器遇到了一个未曾预料的状况，导致了它无法完成对请求的处理"
                    BAD_GATEWAY -> ex.msg = "作为网关或者代理工作的服务器尝试执行请求时，从上游服务器接收到无效的响应"
                    SERVICE_UNAVAILABLE -> ex.msg = "由于临时的服务器维护或者过载，服务器当前无法处理请求"
                    else -> ex.msg = "网络错误"  //其它均视为网络错误
                }
                return ex
            } else if (e is ServerException) {    //服务器返回的错误
                val resultException = e as ServerException
                ex = ApiException(resultException, resultException.code)
                ex.msg = resultException.msg
                return ex
            } else if (e is JSONException || e is ParseException) {
                ex = ApiException(e, ErrorType.PARSE_ERROR)
                ex.msg = "解析错误"            //均视为解析错误
                return ex
            } else if (e is ConnectException || e is SocketTimeoutException || e is ConnectTimeoutException) {
                ex = ApiException(e, ErrorType.NETWORK_ERROR)
                ex.msg = "连接失败"  //均视为网络错误
                return ex
            } else if (e is HttpException) {
                if ("HTTP 404 Not Found" == e.message) {
                    ex = ApiException(e, ErrorType.NETWORK_ERROR)
                    ex.msg = "没有连接服务器"
                } else {
                    ex = ApiException(e, ErrorType.NETWORK_ERROR)
                    ex.msg = "其他连接服务器错误"
                }
                return ex

            } else {
                ex = ApiException(e, ErrorType.UNKONW)
                ex.msg = "未知错误"          //未知错误
                return ex
            }
        }

    }
}