package com.open.app.model.rx.error

import com.open.app.model.rx.base.BaseHttpResult
import rx.Observable
import rx.functions.Func1

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

class ErrorTransformer<T> : Observable.Transformer<BaseHttpResult<T>, T> {

    private var errorTransformer: ErrorTransformer<T>? = null
    private val TAG = "ErrorTransformer"

    /**
     * @return 线程安全, 双层校验
     */
    fun <T> getInstance(): ErrorTransformer<T> {

        if (errorTransformer == null) {
            synchronized(ErrorTransformer::class.java) {
                if (errorTransformer == null) {
                    errorTransformer = ErrorTransformer()
                }
            }
        }
        return errorTransformer as ErrorTransformer<T>

    }

    override fun call(responseObservable: Observable<BaseHttpResult<T>>): Observable<T> {

        return responseObservable.map(Func1<BaseHttpResult<T>, T> { httpResult ->
            if (httpResult == null)
                throw ServerException(ErrorType.EMPTY_BEAN, "解析对象为空")

            //                LogUtils.e(TAG, httpResult.toString());

            if (httpResult.status !== ErrorType.SUCCESS)
                throw ServerException(httpResult.status, httpResult.message)
            httpResult.data
        }).onErrorResumeNext { throwable ->
            //ExceptionEngine为处理异常的驱动器throwable
            throwable.printStackTrace()
            Observable.error(ExceptionEngine.handleException(throwable))
        }

    }


}
