package com.open.app.viewmodel

import android.databinding.*
import com.open.app.model.data.VersionBean

class WelcomeViewModel : BaseObservable {
    var versions: ObservableField<VersionBean> = ObservableField<VersionBean>()

    constructor()

    @Bindable
    fun getVersionCode(): String {
        var bean: VersionBean? = versions.get();
        return bean?.versionCode.toString() ?: "0"
    }

    @Bindable
    fun getVersionName(): String? {
        var bean: VersionBean? = versions.get()
        return bean?.versionName ?: null
    }

    fun setVersionObObservable(bean: VersionBean) {
        versions.set(bean)
    }
}