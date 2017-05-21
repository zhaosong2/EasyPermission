package com.zs.easypermission;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class RecordActivity2 extends AppCompatActivity implements View.OnClickListener{
    private static final String LOG_TAG = "AudioRecordTest";
    //语音文件保存路径
    private String FileName = null;

    //界面控件
    private Button toRefuse;
    private Button startRecord;
    private Button startPlay;
    private Button stopRecord;
    private Button stopPlay;

    //语音操作对象
    private MediaPlayer mPlayer = null;
    private MediaRecorder mRecorder = null;
    private PermissionUtil mPermissionUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorder2);
        initView();
        initData();
        initEvent();

    }

    private void initView() {
        toRefuse = (Button) findViewById(R.id.toRefuse);
        startRecord = (Button)findViewById(R.id.startRecord);
        stopRecord = (Button)findViewById(R.id.stopRecord);
        startPlay = (Button)findViewById(R.id.startPlay);
        stopPlay = (Button)findViewById(R.id.stopPlay);

    }

    private void initData() {
        startRecord.setText("开始录音");
        stopRecord.setText("结束录音");
        startPlay.setText("开始播放");
        stopPlay.setText("结束播放");
        //设置sdcard的路径
        FileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        FileName += "/audiorecordtest.3gp";
        mPermissionUtil = new PermissionUtil(this);
    }

    private void initEvent() {
        toRefuse.setOnClickListener(this);
        startRecord.setOnClickListener(this);
        stopRecord.setOnClickListener(this);
        startPlay.setOnClickListener(this);
        stopPlay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.toRefuse:
                toRefuse();
                break;
            case R.id.startRecord:
                mPermissionUtil.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, new GenericPermissionCallBack() {
                    @Override
                    public void onPermissionsSuccess(Activity activity) {
                        startRecord();
                    }
                });

                break;
            case R.id.stopRecord:
                mPermissionUtil.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, new GenericPermissionCallBack() {
                    @Override
                    public void onPermissionsSuccess(Activity activity) {
                        stopRecord();
                    }
                });
                break;
            case R.id.startPlay:
                mPermissionUtil.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, new GenericPermissionCallBack() {
                    @Override
                    public void onPermissionsSuccess(Activity activity) {
                        startPlay();
                    }
                });
                break;
            case R.id.stopPlay:
                mPermissionUtil.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, new GenericPermissionCallBack() {
                    @Override
                    public void onPermissionsSuccess(Activity activity) {
                        stopPlay();
                    }
                });
                break;
        }
    }

    private void toRefuse() {
        mPermissionUtil.toPermissionPage(this);
    }

    //开始录音
    private void startRecord() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(FileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
        mRecorder.start();
    }

    //停止录音
    private void stopRecord() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    //播放录音
    private void startPlay() {
        mPlayer = new MediaPlayer();
        try{
            mPlayer.setDataSource(FileName);
            mPlayer.prepare();
            mPlayer.start();
        }catch(IOException e){
            Log.e(LOG_TAG,"播放失败");
        }
    }

    //停止播放录音
    private void stopPlay() {
        mPlayer.release();
        mPlayer = null;
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
