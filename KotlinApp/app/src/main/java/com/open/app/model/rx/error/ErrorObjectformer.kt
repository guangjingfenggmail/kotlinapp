package com.open.app.model.rx.error

import rx.Observable;

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

class ErrorObjectformer<Object> : Observable.Transformer<Object, Object> {
    companion object {
        private var errorTransformer: ErrorObjectformer<Object>? = null
        private val TAG = "ErrorTransformer"

        /**
         * @return 线程安全, 双层校验
         */
        fun <Object> getInstance(): ErrorObjectformer<Object>? {
            if (errorTransformer == null) {
                synchronized(ErrorObjectformer::class.java) {
                    if (errorTransformer == null) {
                        errorTransformer = ErrorObjectformer()
                    }
                }
            }
            return  errorTransformer as ErrorObjectformer<Object>

        }
    }

    override fun call(responseObservable: Observable<Object>): Observable<Object> {
        return responseObservable.map { httpResult ->
            if (httpResult == null)
                throw ServerException(11, "解析对象为空")
            httpResult
        }.onErrorResumeNext { throwable ->
            //ExceptionEngine为处理异常的驱动器throwable
            throwable.printStackTrace()
            Observable.error(ExceptionEngine.handleException(throwable))
        }
    }


//    override fun call(t: Observable<Object>): Observable<Object> {
//        return t.map(FuncResponse())
//                .onErrorResumeNext(FuncError())
//    }
//
//
//    inner class FuncResponse : Func1<Object, Object> {
//        override fun call(t: Object): Object {
//            if (t == null)
//                throw ServerException(ErrorType.EMPTY_BEAN.toString())
//            return t
//        }
//    }
//
//
//    inner class FuncError : Func1<Throwable, Observable<Object>> {
//        override fun call(t: Throwable): Observable<Object> {
//            t.printStackTrace()
//            return Observable.error(ExceptionEngine.handleException(t))
//        }
//    }
}