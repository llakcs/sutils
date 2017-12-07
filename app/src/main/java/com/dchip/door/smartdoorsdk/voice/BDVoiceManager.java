package com.dchip.door.smartdoorsdk.voice;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;


/**
 * Created by llakcs on 2017/11/30.
 */

public interface BDVoiceManager {

    void start();
    void stop();
    void initRecog();
    void onDestroy();
    void enableOffline(boolean enable);
    void speak(String text);
    void init(Application app, Activity activity, Handler handler);
    void recogn();

}
