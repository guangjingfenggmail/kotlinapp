package com.open.app.permission;

import android.app.Activity;

/**
 * ****************************************************************************************************************************************************************************
 *
 * @author : guangjing.feng
 * @createTime: 2018/11/15
 * @version: 1.0.0
 * @modifyTime: 2018/11/15
 * @modifyAuthor: guangjing.feng
 * @description: ****************************************************************************************************************************************************************************
 */
public interface IPermissionResult {
    void getPermissionFailed(Activity activity, int requestCode,String[] deniedPermissions);

    void getPermissionSuccess(Activity activity, int requestCode);
}
