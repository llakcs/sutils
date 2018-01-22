package com.dchip.door.smartdoorsdk.deviceControl.devicehandler;

import com.dchip.door.smartdoorsdk.deviceControl.nativeLev.Led;
import com.dchip.door.smartdoorsdk.deviceControl.nativeLev.Steer;

import java.util.logging.Handler;

/**
 * Created by jelly on 2018/1/22.
 */

public class SteerHandler {
    private static final String TAG = "SteerHandler";
    private static SteerHandler instance;
    private static Steer mSteer;

    private static final int SHAKE = 0;
    private static final int NOD = 0;
    private static final int SHAKE_STOP = 1;
    private static final int NOD_STOP = 1;

    public static SteerHandler getInstance(){
        if (instance == null){
            instance = new SteerHandler();
        }
        return instance;
    }

    SteerHandler(){
        mSteer = new Steer();
        mSteer.openDevice();
    }

    public void shake(){
        mSteer.control(SHAKE,0);
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSteer.control(SHAKE_STOP,0);
            }
        },500);
    }
    public void nod(){
        mSteer.control(NOD,1);
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSteer.control(NOD_STOP,0);
            }
        },500);
    }
    public void close(){
        mSteer.closeDevice();
    }



}
