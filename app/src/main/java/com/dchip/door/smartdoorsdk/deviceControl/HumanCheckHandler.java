package com.dchip.door.smartdoorsdk.deviceControl;


import com.dchip.door.smartdoorsdk.deviceControl.nativeLev.HumanCheck;
import com.dchip.door.smartdoorsdk.event.DeviceCheckEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by jelly on 2017/11/23.
 */

public class HumanCheckHandler {
    protected static final String TAG = "HumanCheckHandler";
    public static HumanCheckHandler instance;
    private static HumanCheck hc;
    private boolean stop = false;


    public static HumanCheckHandler getInstance() {
        if (instance == null) {
            instance = new HumanCheckHandler();
        }
        return instance;
    }

    public HumanCheckHandler() {
        stop = false;
        hc = new HumanCheck();
        new Thread(runnable).start();
    }

    public void finish() {
        stop = true;
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(hc.openDvice()) {
                int statuslod = 0;
                while (!stop) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int statusnew = hc.checkHuman();
                    if (statuslod != statusnew){
                        if (statusnew == 1)
                            EventBus.getDefault().post(new DeviceCheckEvent("human"));
                    }
                   // LogUtil.e(TAG,"人体检测:" + statusnew);
                    statuslod = statusnew;
                }
            }

            hc.closeDvice();
        }
    };


}
