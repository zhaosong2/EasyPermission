# EasyPermission
运行时权限处理工具类，使用简单而功能完备：能智能弹出对话框提示，处理用户选择不再询问的情况。

## 一、简单使用  

### 1）创建权限管理对象,一个Activity一个mPermissionUtil
```
private PermissionUtil mPermissionUtil;   
//建议在onCreate里初始化   
 mPermissionUtil = new PermissionUtil(this);
 ```

### 2）在需要用到权限的所在Activity申请所需权限,会先弹出对话框告诉用户需要开启哪些权限，让用户确认,然后调用系统权限管理逐个询问

```
 mPermissionUtil.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO},  
 new GenericPermissionCallBack() {
                    @Override
                    public void onPermissionsSuccess(Activity activity) {
                        //do something
                    }
                });
```
                
### 3) 系统回调处理 

 ```
 @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //处理用户权限处理的结果,如果全部权限通过,会走onSuccess;如果部分权限没给,会走到步骤2)；如果有权限没给并且勾选了不再提示，将弹出一个对话框，要么直接退出，要么跳转到系统权限管理界面去手动开启权限
        mPermissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      //拒绝授权后跳转到授权页面后未开启权限的处理 
      mPermissionUtil.onActivityResult(requestCode);
    }
 ```
    
## 二、项目说明

### 1）没有用单例或静态的模式。因为每次申请权限都需要调用三个方法，如果全用静态方法的话，每个方法方法的参数都会增加当前Activity，用实例的话只用在构造方法里传一次当前Activity就行；权限管理工具类只在当前Activity里会用到，所以需要在Activity里创建对象，切换Activity后并不能复用，所以不能用单例。
### 2）使用简单。权限校验操作完全封装在权限校验申请的操作里，如果已经给了全部权限或者申请权限后用户同意了全部权限就走回调的onPermissionsSuccess方法；回调做了统一的一般封装，如果用户没有同意全部权限，或者勾选了不再提示，后续逻辑已通过对话框全部处理；甚至还可以不用管具体申请哪些权限，直接申请全部，不过不建议这么用。
### 3）智能提示。将所有危险权限操作对应汉语放在Map中,可根据申请的权限自动给出汉语提示；申请权限前会弹出对话框告诉用户需要申请哪些权限，让用户确认；如果用户拒绝权限会再次提示并申请：如果用户勾选不再提示，也会对话框提示跳转到系统设置或强制退出；总之申请权限全程都有对话框提示。
### 4）代码健壮。考虑到了权限处理中的全部问题，用户随意开关权限均不会崩溃。
### 5）只处理了Activity，fragment的情况未处理。
### 6）还有一个待优化点，那两个系统回调方法是可以不用手动调用的，可以设置ActivityLifecycleCallbacks后，在这个里面处理，这样代码会更简单，每次申请权限只用一行代码。
### 7）代码并不复杂，没有做成类库，只写了一个Demo,我建议你把相关代码拷过去使用,这样也不会增加依赖，你还可以根据自己的情况进行一些定制修改。
### 8）第一次发代码,欢迎大家批评指正。邮箱zhaosongxuexi@163.com。
