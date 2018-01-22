package com.dchip.door.smartdoorsdk.deviceControl.devicehandler;

import com.dchip.door.smartdoorsdk.deviceControl.nativeLev.Led;
import com.dchip.door.smartdoorsdk.deviceControl.nativeLev.Steer;

/**
 * Created by jelly on 2018/1/22.
 */

public class SteerHandler {
    private static final String TAG = "SteerHandler";
    private static SteerHandler instance;
    private static Steer mSteer;

    private static final int SHAKE = 0;
    private static final int STOP = 1;

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

    public void start(){
        mSteer.control(SHAKE);
    }
    public void stop(){
        mSteer.control(STOP);
    }
    public void close(){
        mSteer.closeDevice();
    }



}
