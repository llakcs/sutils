package com.dchip.door.smartdoorsdk.video;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Toast;

import com.dchip.door.smartdoorsdk.s;
import com.dchip.door.smartdoorsdk.utils.LogUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMCallStateChangeListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMVideoCallHelper;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.media.EMCallSurfaceView;
import com.hyphenate.util.EMLog;

/**
 * Created by llakcs on 2017/11/30.
 */

public class VideoImpl implements VideoManager {

    private VideoImpl(){

    }
    private static final Object lock = new Object();
    private static volatile VideoImpl instance;
    public static void registerInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new VideoImpl();
                }
            }
        }
        s.Ext.setVideoManager(instance);
    }
    private String TAG="VideoImpl";
    @Override
    public void cmLogin(String user,String pass) {
        EMClient.getInstance().login(user, pass, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                LogUtil.e(TAG,"###VideoImpl.cmlogin =onSuccess");
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, final String message) {
                LogUtil.e(TAG,"###VideoImpl.cmlogin =onError"+message + "--------");

            }
        });
    }



}
