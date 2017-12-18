package com.dchip.door.smartdoorsdk.deviceControl;

import com.dchip.door.smartdoorsdk.deviceControl.nativeLev.Led;
import com.dchip.door.smartdoorsdk.deviceControl.nativeLev.Pn512Card;

/**
 * Created by jelly on 2017/12/18.
 */

public class LedHandler {
    private static final String TAG = "LedHandler";
    private static LedHandler instance;
    private static Led mLed;
    private static int defOpen = 0;

    public static LedHandler getInstance(){
        if (instance == null){
            instance = new LedHandler();
        }
        return instance;
    }

    LedHandler(){
        mLed = new Led();
        mLed.openDevice();
    }

    //i = 1,2,3
    public int openLed(int i){
        if (i==1 || i == 2 || i == 3) {
            return mLed.ioDevice(defOpen, i - 1);
        } else return 0;
    }

    //i = 1,2,3
    public int closeLed(int i){
        if (i==1 || i == 2 || i == 3) {
            return mLed.ioDevice(inv(defOpen), i - 1);
        }else return 0;

    }

    void finish(){
        mLed.closeDevice();
    }


    protected int inv(int i){
        if (i>0) return 0;
        else return 1;
    }

}
