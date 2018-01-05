package com.dchip.door.smartdoorsdk.deviceControl.Listener;

/**
 * Created by llakcs on 2017/12/14.
 */

public interface ServerstatusListner {
    void getHeartBeats();
    void disconn();
    void connected();
    void updateAPK();
    void updatecardlist();
}
