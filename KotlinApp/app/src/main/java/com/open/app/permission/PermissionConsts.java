package com.open.app.permission;

import android.Manifest;

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
public interface PermissionConsts {
    String[] CONTACTS = {
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.READ_CONTACTS
    };

    String[] PHONE = {
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.WRITE_CALL_LOG,
            Manifest.permission.USE_SIP,
            Manifest.permission.PROCESS_OUTGOING_CALLS,
            Manifest.permission.ADD_VOICEMAIL
    };

    String[] CALENDAR = {
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR
    };

    String[] CAMERA = {
            Manifest.permission.CAMERA
    };

    String[] SENSORS = {
            Manifest.permission.BODY_SENSORS
    };

    String[] LOCATION = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };


    String[] STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    String[] MICROPHONE = {
            Manifest.permission.RECORD_AUDIO
    };

    String[] SMS = {
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_WAP_PUSH,
            Manifest.permission.RECEIVE_MMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.SEND_SMS
    };
}
