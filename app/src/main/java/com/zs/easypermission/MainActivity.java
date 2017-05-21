package com.zs.easypermission;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button requestAll;
    private Button requestFile;
    private Button requestRecorder;
    private Button toRefuse;
    private Button toRecord1;
    private Button toRecord2;
    private PermissionUtil mPermissionUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        requestAll = (Button) findViewById(R.id.requestAll);
        requestFile = (Button) findViewById(R.id.requestFile);
        requestRecorder = (Button) findViewById(R.id.requestRecorder);
        toRefuse = (Button) findViewById(R.id.toRefuse);
        toRecord1 = (Button) findViewById(R.id.toRecord1);
        toRecord2 = (Button) findViewById(R.id.toRecord2);

    }

    private void initData() {
        mPermissionUtil = new PermissionUtil(this);
    }

    private void initEvent() {
        requestAll.setOnClickListener(this);
        requestFile.setOnClickListener(this);
        requestRecorder.setOnClickListener(this);
        toRefuse.setOnClickListener(this);
        toRecord1.setOnClickListener(this);
        toRecord2.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.requestAll:
                requestAll();
                break;
            case R.id.requestFile:
                requestFile();
                break;
            case R.id.requestRecorder:
                requestRecorder();
                break;
            case R.id.toRefuse:
                toRefuse();
                break;
            case R.id.toRecord1:
                toRecord1();
                break;
            case R.id.toRecord2:
                toRecord2();
                break;
        }
    }

    private void requestAll() {
        mPermissionUtil.requestAllPermissions(new GenericPermissionCallBack() {

            @Override
            public void onPermissionsSuccess(Activity activity) {
                Toast.makeText(MainActivity.this, "申请所有权限成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void requestFile() {
        mPermissionUtil.requestPermissions(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, new GenericPermissionCallBack() {

            @Override
            public void onPermissionsSuccess(Activity activity) {
                Toast.makeText(MainActivity.this, "申请文件权限成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void requestRecorder() {
        mPermissionUtil.requestPermissions(new String[]{
                Manifest.permission.RECORD_AUDIO}, new GenericPermissionCallBack() {

            @Override
            public void onPermissionsSuccess(Activity activity) {
                Toast.makeText(MainActivity.this, "申请录音权限成功", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void toRefuse() {
        mPermissionUtil.toPermissionPage(this);
    }

    private void toRecord1() {
        startActivity(new Intent(getApplicationContext(),RecordActivity1.class));
    }

    private void toRecord2() {
        startActivity(new Intent(getApplicationContext(),RecordActivity2.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPermissionUtil.onActivityResult(requestCode);
    }
}
