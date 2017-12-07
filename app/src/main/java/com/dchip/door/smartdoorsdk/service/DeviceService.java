package com.dchip.door.smartdoorsdk.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.dchip.door.smartdoorsdk.deviceControl.CardHandler;
import com.dchip.door.smartdoorsdk.deviceControl.HumanCheckHandler;
import com.dchip.door.smartdoorsdk.utils.LogUtil;


public class DeviceService extends Service {
    public static final String TAG = "LockService";
    public DeviceService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.e(TAG,"###DeviceService.onCreate()");
        //初始化读卡模块
        CardHandler.getInstance();
        //初始化人体检测设备
        HumanCheckHandler.getInstance();
    }


    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
