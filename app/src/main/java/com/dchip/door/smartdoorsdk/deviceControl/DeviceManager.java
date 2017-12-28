package com.dchip.door.smartdoorsdk.deviceControl;

import android.app.Activity;

import com.dchip.door.smartdoorsdk.deviceControl.Listener.EaseAccountListner;
import com.dchip.door.smartdoorsdk.deviceControl.Listener.HumanCheckListner;
import com.dchip.door.smartdoorsdk.deviceControl.Listener.LockBreakListener;
import com.dchip.door.smartdoorsdk.deviceControl.Listener.LockPushListener;
import com.dchip.door.smartdoorsdk.deviceControl.Listener.ServerstatusListner;
import com.dchip.door.smartdoorsdk.deviceControl.Listener.ServiceOpenLockListner;
import com.dchip.door.smartdoorsdk.deviceControl.Listener.UpdateOwenerListner;
import com.dchip.door.smartdoorsdk.deviceControl.devicehandler.LedHandler;
import com.dchip.door.smartdoorsdk.deviceControl.interfaces.LockHandler;

/**
 * Created by llakcs on 2017/12/5.
 */

public interface DeviceManager {

    void setLock(LockHandler lock);
    void setLock(String config);
    LockHandler getLock();
    LedHandler getLed();
    void upLoadMac();
    void uploadAppVer(String ver);
    void checkVer();
    DeviceImpl setHumanCheckListner(HumanCheckListner humanCheckListner);
    void unRegHumanCheckListner();
    void setUpdateOwenerListner(UpdateOwenerListner updateOwenerListner);
    void unRegUpdateOwnerListner();
    void setServiceOpenLockListner(ServiceOpenLockListner serviceOpenLockListner);
    void unRegServiceOpenLockListner();
    void setServerstatusListner(ServerstatusListner serverstatusListner);
    void unRegServerstatusListner();
    DeviceImpl setLockPushListener(LockPushListener lockPushListener);
    void unRegLockPushListenerListner();
    DeviceImpl setLockBreakListener(LockBreakListener lockBreakListener);
    void unRegLockBreakListener();
    void uploadLock();
    void checkCrashLogAndUpload();
    DeviceImpl init(Activity activity,int appTypeNum);
    void release();
    void updateOnwerStatus();
    void setEaseAcountListner(EaseAccountListner acountListner);
    void unRegEaseAcountListner();
    DeviceImpl EnableCardReader();
    DeviceImpl EnableLock();
    DeviceImpl EnableLed();
    DeviceImpl EnableDtimer();

}
