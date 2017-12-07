package com.dchip.door.smartdoorsdk.deviceControl;

import android.app.Activity;

import com.dchip.door.smartdoorsdk.deviceControl.interfaces.LockHandler;

/**
 * Created by llakcs on 2017/12/5.
 */

public interface DeviceManager {

    void setLock(LockHandler lock);
    void setLock(String config);
    LockHandler getLock();
    void upLoadMac();
    void uploadAppVer(String ver);
    void checkVer();
    void uploadLock();
    void checkCrashLogAndUpload();
    void init(Activity activity);
    void release();

}
