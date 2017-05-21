package com.zs.easypermission;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限请求工具类
 *
 * @version v1.0
 * @author: zs
 * @date: 2016-12-01
 */
public class PermissionUtil {

    private static final int REQUEST_ALL_PERMISSIONS = 2001;
    private static final int START_ACTIVITY_FOR_PERMISSION_RESULT = 2002;
    private PermissionCallBack callBack = new DefaultGenericPermissionCallBack();
    private final Activity activity;
    private String[] mPermissions;

    public PermissionUtil(Activity activity) {
        this.activity = activity;
    }

    /**
     * 检查一个权限
     *
     * @param permission 权限
     * @return {@code true}: 已经授权<br>{@code false}: 尚未授权
     */
    public static boolean hasPermission(String permission) {
        if (ContextCompat.checkSelfPermission(MyApplication.getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
            if (!Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission) && !Manifest.permission.READ_EXTERNAL_STORAGE.equals(permission)){
                //如果需要记录权限申请相关日志到文件中,请在此处记录,避免出现申请文件权限-记录权限失败日志到文件-申请文件权限...的死循环
                Log.v("PermissionUtil", permission +" hasn't been granted");
            }
            return false;//尚未授权
        }
        return true;//已经授权
    }

    /**
     * 批量检查权限
     *
     * @param permissions 授权列表
     * @return {@code true}: 已经授权<br>{@code false}: 尚未授权
     */
    public static boolean hasPermissions(String[] permissions) {
        if (permissions == null || permissions.length == 0) {
            return true;
        }
        for (int i = 0; i < permissions.length; i++) {
            if (!hasPermission(permissions[i])) {
                return false;
            }
        }
        return true;
    }

    private String[] getManifestPermissions(@NonNull final Activity activity) {
        PackageInfo packageInfo = null;
        List<String> list = new ArrayList<>(1);
        try {
            packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), PackageManager.GET_PERMISSIONS);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("PermissionUtil", "A problem occurred when retrieving permissions", e);
        }
        if (packageInfo != null) {
            String[] permissions = packageInfo.requestedPermissions;
            if (permissions != null) {
                for (String perm : permissions) {
                    Log.i("PermissionUtil", "Manifest contained permission: " + perm);
                    list.add(perm);
                }
            }
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * 请求所有权限，用于初始化时申请所有权限，但不建议使用;建议在初始化时只申请必要权限，其他权限在使用时再申请
     * @return
     */
    public boolean requestAllPermissions(PermissionCallBack callBack) {
        return requestPermissions(getManifestPermissions(activity),callBack);
    }

    /**
     * 请求权限,自定义回调
     * @param permissions
     * @param callBack
     * @return
     */
    public boolean requestPermissions(String[] permissions, PermissionCallBack callBack) {
        if (callBack !=null){
            this.callBack = callBack;
        }
        return requestPermissions(permissions);
    }

    /**
     * 请求权限,使用默认回调
     * @param permissions
     * @return
     */
    public boolean requestPermissions(String[] permissions) {
        if (permissions == null || permissions.length == 0) {
            callBack.onPermissionsSuccess(activity);
            return true;
        }
        this.mPermissions = permissions;
        List<String> deniedList = getDeniedList(permissions);
        if (deniedList.size() !=0){
            final String[] deniedPermissions = deniedList.toArray(new String[deniedList.size()]);
            String dialogMsg = getDialogMsg(deniedPermissions);
            /*for (String permission : deniedPermissions) {
                if (isNeverShowPermission(permission)) {
                    PermissionUtil.showEnteringDialog(activity, dialogMsg);
                    return false;
                }
            }*/
            showRequestDialog(deniedPermissions, dialogMsg);
            return false;
        }
        callBack.onPermissionsSuccess(activity);
        return true;
    }

    private void showRequestDialog(final String[] deniedPermissions, String dialogMsg) {
        new AlertDialog.Builder(activity)
                .setTitle("为完成后续操作,请逐个允许如下权限:")
                .setMessage(dialogMsg)
                .setCancelable(false)
                .setPositiveButton("确认", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(activity, deniedPermissions, REQUEST_ALL_PERMISSIONS);
                    }
                }).show();

    }

    @NonNull
    private List<String> getDeniedList(String[] permissions) {
        List<String> deniedList = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (!hasPermission(permissions[i])) {
                deniedList.add(permissions[i]);
            }
        }
        return deniedList;
    }

    /**
     * 勾选了不再提示的拒绝权限
     *
     * @param deniedPermission 权限
     * @return {@code true}: 不再提示<br>{@code false}: 可以提示
     */
    public boolean isNeverShowPermission(String deniedPermission) {
        if (!hasPermission(deniedPermission)) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, deniedPermission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 所有授权是否被同意<br>
     * {@code deniedPermissions.size()>0  => callBack.onPermissionsFailed}: 全部同意<br>
     * {@code deniedPermissions.size() ==0  => callBack.onPermissionsSuccess}: 存在不同意的授权
     */

    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode != REQUEST_ALL_PERMISSIONS) {
            return;
        }
        List<String> deniedPermissions = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permissions[i]);
            }
        }
        if (deniedPermissions.size()>0){
            String[] mPermissions = deniedPermissions.toArray(new String[deniedPermissions.size()]);
            callBack.onPermissionsFailed(activity, mPermissions, this);
        }else{
            callBack.onPermissionsSuccess(activity);
        }
    }

    /**
     * 拒绝授权后跳转到授权页面后未开启权限则需要对其进行权限判断
     * @param requestCode 返回的请求码 {@code GenericCode.START_ACTIVITY_FOR_PERMISSION_RESULT}
     */
    public void onActivityResult(int requestCode){
        if(requestCode != START_ACTIVITY_FOR_PERMISSION_RESULT){
            return;
        }
        boolean hasAllPermissions = hasPermissions(mPermissions);
        if(!hasAllPermissions){
            List<String> deniedList = getDeniedList(mPermissions);
            String[] deniedPermissions = deniedList.toArray(new String[deniedList.size()]);
            String dialogMsg = getDialogMsg(deniedPermissions);
            showEnteringDialog(activity,dialogMsg);
        }

    }

    public String getDialogMsg(@NonNull String[] deniedPermissions) {
        StringBuilder sb = new StringBuilder();
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i<deniedPermissions.length; i ++ ){
            String permission = ConstUtil.PERMISSION_INFLACT_MAP.get(deniedPermissions[i]);
            if (permission == null || list.contains(permission)){
                continue;
            }
            list.add(permission);
            if (list.size() > 1){
                sb.append("\n");
            }
            sb.append(list.size()).append(".").append(permission);
        }
        return sb.toString();
    }

    public void showEnteringDialog(final Activity activity, String msg) {
        new AlertDialog.Builder(activity)
                .setTitle("为完成后续操作,请开启如下权限:")
                .setMessage(msg)
                .setCancelable(false)
                .setNegativeButton("退出",new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        activity.finish();
                    }
                })
                .setPositiveButton("确认", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        toPermissionPage(activity);
                    }
                }).show();
    }

    public void toPermissionPage(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, START_ACTIVITY_FOR_PERMISSION_RESULT);
    }

    private class DefaultGenericPermissionCallBack extends GenericPermissionCallBack{

        @Override
        public void onPermissionsSuccess(Activity activity) {

        }
    }
}
