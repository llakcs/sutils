package com.dchip.door.smartdoorsdk.deviceControl.Listener;

/**
 * Created by llakcs on 2017/12/14.
 */

public interface ServerstatusListner {
    void DISCONNECTED();
    void CONNECTED();
    void UPDATE_APK();
    void UPDATE_CARD_LIST();
}
