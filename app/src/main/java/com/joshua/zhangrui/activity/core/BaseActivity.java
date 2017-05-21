package com.joshua.zhangrui.activity.core;

import android.app.Activity;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.joshua.zhangrui.receiver.ExitReceiver;
import com.joshua.zhangrui.utils.PrefUtils;

import org.xutils.x;


public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected Activity mBaseActivity;
    private ExitReceiver exitReceiver;
    public static String EXIT_APP_ACTION = "com.joshua.exit";
    public static final String TAG = "ZR";


    public BaseActivity() {
        mBaseActivity = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initScreen();
        initXUtils();
        initReceiver();
    }


    public String getCurrentUser() {
        return PrefUtils.getString(mBaseActivity, "username", "");
    }

    /**
     * 初始化屏幕方向
     */
    private void initScreen() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置竖屏
    }

    /**
     * 初始化xUtils
     */
    private void initXUtils() {
        x.view().inject(this);
    }

    /**
     * 初始化退出广播监听
     */
    private void initReceiver() {
        exitReceiver = new ExitReceiver();
        registerExitReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterExitReceiver();
    }

    /**
     * 注册退出广播
     */
    private void registerExitReceiver() {
        IntentFilter exitFilter = new IntentFilter();
        exitFilter.addAction(EXIT_APP_ACTION);
        registerReceiver(exitReceiver, exitFilter);
    }

    /**
     * 注销退出广播
     */
    private void unRegisterExitReceiver() {
        unregisterReceiver(exitReceiver);
    }


}
