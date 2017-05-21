package com.zs.easypermission;

import android.app.Activity;

/**
 * 通用的权限回调函数
 *
 * @version v1.0
 * @author: zs
 * @date: 2016-12-05
 */
public abstract class GenericPermissionCallBack implements PermissionCallBack {

    @Override
    public abstract void onPermissionsSuccess(final Activity activity);

    @Override
    public void onPermissionsFailed(final Activity activity, final String[] permissions, final PermissionUtil permissionUtil) {
        for (String permission : permissions) {
            if (permissionUtil.isNeverShowPermission(permission)) {
                String dialogMsg = permissionUtil.getDialogMsg(permissions);
                permissionUtil.showEnteringDialog(activity, dialogMsg);
                return;
            }
        }
        permissionUtil.requestPermissions(permissions);
    }


}
