package com.open.app.model.rx.transformer

import com.open.app.model.rx.error.ErrorTransformer
import com.open.app.model.rx.base.BaseHttpResult
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

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


class CommonTransformer<T> : Observable.Transformer<BaseHttpResult<T>, T> {

    override fun call(tansFormerObservable: Observable<BaseHttpResult<T>>): Observable<T> {
        return tansFormerObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ErrorTransformer())
    }
}

