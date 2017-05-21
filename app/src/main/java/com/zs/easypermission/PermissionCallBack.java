package com.zs.easypermission;

import android.app.Activity;

/**
 * 权限回调接口
 *
 * @version v1.0
 * @author: hsu
 * @date: 2016-12-05
 */
public interface PermissionCallBack {
    /**
     * 所有授权成功
     * @param activity
     */
    void onPermissionsSuccess(final Activity activity);

    /**
     * 存在未授权的权限处理
     * @param activity
     * @param permissions
     * @param permissionUtil
     */
    void onPermissionsFailed(final Activity activity, final String[] permissions, PermissionUtil permissionUtil);
}
