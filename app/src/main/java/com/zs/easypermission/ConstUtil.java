package com.zs.easypermission;

import java.util.HashMap;

/**
 * 常量相关工具类
 *
 * @version v1.0
 * @author: hsu
 * @date: 2016-11-25
 */
public class ConstUtil {

    /**
     * 危险权限对应中文MAP
     */
    public static final HashMap<String, String> PERMISSION_INFLACT_MAP = new HashMap<String, String>();

    /**
     * 所有危险权限中文的数组
     */
    public static final String[] ALL_PERMISSION_ARRAY = new String[]{"通讯录","电话","日程","相机","身体传感器","位置信息","存储","麦克风","短信"};

    static {
        PERMISSION_INFLACT_MAP.put("android.permission.WRITE_CONTACTS", "通讯录");
        PERMISSION_INFLACT_MAP.put("android.permission.GET_ACCOUNTS", "通讯录");
        PERMISSION_INFLACT_MAP.put("android.permission.READ_CONTACTS", "通讯录");

        PERMISSION_INFLACT_MAP.put("android.permission.READ_CALL_LOG", "电话");
        PERMISSION_INFLACT_MAP.put("android.permission.READ_PHONE_STATE", "电话");
        PERMISSION_INFLACT_MAP.put("android.permission.CALL_PHONE", "电话");
        PERMISSION_INFLACT_MAP.put("android.permission.WRITE_CALL_LOG", "电话");
        PERMISSION_INFLACT_MAP.put("android.permission.USE_SIP", "电话");
        PERMISSION_INFLACT_MAP.put("android.permission.PROCESS_OUTGOING_CALLS", "电话");
        PERMISSION_INFLACT_MAP.put("com.android.voicemail.permission.ADD_VOICEMAIL", "电话");

        PERMISSION_INFLACT_MAP.put("android.permission.READ_CALENDAR", "日程");
        PERMISSION_INFLACT_MAP.put("android.permission.WRITE_CALENDAR", "日程");

        PERMISSION_INFLACT_MAP.put("android.permission.CAMERA", "相机");

        PERMISSION_INFLACT_MAP.put("android.permission.BODY_SENSORS", "身体传感器");

        PERMISSION_INFLACT_MAP.put("android.permission.ACCESS_FINE_LOCATION", "位置信息");
        PERMISSION_INFLACT_MAP.put("android.permission.ACCESS_COARSE_LOCATION", "位置信息");

        PERMISSION_INFLACT_MAP.put("android.permission.READ_EXTERNAL_STORAGE", "存储");
        PERMISSION_INFLACT_MAP.put("android.permission.WRITE_EXTERNAL_STORAGE", "存储");

        PERMISSION_INFLACT_MAP.put("android.permission.RECORD_AUDIO", "麦克风");

        PERMISSION_INFLACT_MAP.put("android.permission.READ_SMS", "短信");
        PERMISSION_INFLACT_MAP.put("android.permission.RECEIVE_WAP_PUSH", "短信");
        PERMISSION_INFLACT_MAP.put("android.permission.RECEIVE_MMS", "短信");
        PERMISSION_INFLACT_MAP.put("android.permission.RECEIVE_SMS", "短信");
        PERMISSION_INFLACT_MAP.put("android.permission.SEND_SMS", "短信");
        PERMISSION_INFLACT_MAP.put("android.permission.READ_CELL_BROADCASTS", "短信");

    }

   /* static {
        PERMISSION_INFLACT_MAP.put("android.permission-group.CONTACTS", "通讯录");
        PERMISSION_INFLACT_MAP.put("android.permission.WRITE_CONTACTS", "写入联系人");
        PERMISSION_INFLACT_MAP.put("android.permission.GET_ACCOUNTS", "读取邮件账户");
        PERMISSION_INFLACT_MAP.put("android.permission.READ_CONTACTS", "读取联系人");

        PERMISSION_INFLACT_MAP.put("android.permission-group.PHONE", "电话");
        PERMISSION_INFLACT_MAP.put("android.permission.READ_CALL_LOG", "读取通话记录");
        PERMISSION_INFLACT_MAP.put("android.permission.READ_PHONE_STATE", "获取电话状态");
        PERMISSION_INFLACT_MAP.put("android.permission.CALL_PHONE", "直接拨打电话");
        PERMISSION_INFLACT_MAP.put("android.permission.WRITE_CALL_LOG", "写入通话记录");
        PERMISSION_INFLACT_MAP.put("android.permission.USE_SIP", "使用SIP视频");
        PERMISSION_INFLACT_MAP.put("android.permission.PROCESS_OUTGOING_CALLS", "处理拨出电话");
        PERMISSION_INFLACT_MAP.put("com.android.voicemail.permission.ADD_VOICEMAIL", "添加语音邮件");

        PERMISSION_INFLACT_MAP.put("android.permission-group.CALENDAR", "日程");
        PERMISSION_INFLACT_MAP.put("android.permission.READ_CALENDAR", "读取日程");
        PERMISSION_INFLACT_MAP.put("android.permission.WRITE_CALENDAR", "写入日程");

        PERMISSION_INFLACT_MAP.put("android.permission-group.CAMERA", "相机");
        PERMISSION_INFLACT_MAP.put("android.permission.CAMERA", "相机");

        PERMISSION_INFLACT_MAP.put("android.permission-group.SENSORS", "身体传感器");
        PERMISSION_INFLACT_MAP.put("android.permission.BODY_SENSORS", "身体传感器");

        PERMISSION_INFLACT_MAP.put("android.permission-group.LOCATION", "位置信息");
        PERMISSION_INFLACT_MAP.put("android.permission.ACCESS_FINE_LOCATION", "基于GPS的精确位置");
        PERMISSION_INFLACT_MAP.put("android.permission.ACCESS_COARSE_LOCATION", "基于网络的粗略位置");

        PERMISSION_INFLACT_MAP.put("android.permission-group.STORAGE", "存储");
        PERMISSION_INFLACT_MAP.put("android.permission.READ_EXTERNAL_STORAGE", "读取外部存储");
        PERMISSION_INFLACT_MAP.put("android.permission.WRITE_EXTERNAL_STORAGE", "写入外部存储");

        PERMISSION_INFLACT_MAP.put("android.permission-group.MICROPHONE", "麦克风");
        PERMISSION_INFLACT_MAP.put("android.permission.RECORD_AUDIO", "录音");

        PERMISSION_INFLACT_MAP.put("android.permission-group.SMS", "短信");
        PERMISSION_INFLACT_MAP.put("android.permission.READ_SMS", "读取短信");
        PERMISSION_INFLACT_MAP.put("android.permission.RECEIVE_WAP_PUSH", "接收WAP PUSH信息");
        PERMISSION_INFLACT_MAP.put("android.permission.RECEIVE_MMS", "接收彩信");
        PERMISSION_INFLACT_MAP.put("android.permission.RECEIVE_SMS", "接收彩信");
        PERMISSION_INFLACT_MAP.put("android.permission.SEND_SMS", "发送短信");
        PERMISSION_INFLACT_MAP.put("android.permission.READ_CELL_BROADCASTS", "接收短信变动通知");

    }*/

}