package com.dchip.door.smartdoorsdk.deviceControl;

import android.app.Activity;

import com.dchip.door.smartdoorsdk.deviceControl.Listener.HumanCheckListner;
import com.dchip.door.smartdoorsdk.deviceControl.Listener.ServerstatusListner;
import com.dchip.door.smartdoorsdk.deviceControl.Listener.ServiceOpenLockListner;
import com.dchip.door.smartdoorsdk.deviceControl.Listener.UpdateOwenerListner;
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
    void setHumanCheckListner(HumanCheckListner humanCheckListner);
    void unRegHumanCheckListner();
    void setUpdateOwenerListner(UpdateOwenerListner updateOwenerListner);
    void unRegUpdateOwnerListner();
    void setServiceOpenLockListner(ServiceOpenLockListner serviceOpenLockListner);
    void unRegServiceOpenLockListner();
    void setServerstatusListner(ServerstatusListner serverstatusListner);
    void unRegServerstatusListner();
    void uploadLock();
    void checkCrashLogAndUpload();
    void init(Activity activity);
    void release();
    void updateOnwerStatus();

}
