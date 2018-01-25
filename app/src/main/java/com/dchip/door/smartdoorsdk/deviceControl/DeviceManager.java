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
import com.dchip.door.smartdoorsdk.deviceControl.devicehandler.SteerHandler;
import com.dchip.door.smartdoorsdk.deviceControl.interfaces.LockHandler;

/**
 * Created by llakcs on 2017/12/5.
 */
public interface DeviceManager {

    /**
     * 通过传入对象的方式，设置锁。
     *
     * @param lock the lock
     */
    void setLock(LockHandler lock);

    /**
     * 通过读取配置文件的方式，设置锁。
     *
     * @param config the config
     */
    void setLock(String config);

    /**
     * 获取锁对象
     *
     * @return the lock
     */
    LockHandler getLock();

    /**
     * 获取led对象
     *
     * @return the led
     */
    LedHandler getLed();

    /**
     * 获取关节控制(点头，摇头),舵机对象
     *
     * @return the steer
     */
    SteerHandler getSteer();

    /**
     * 获取广告类型，(图片，视频)
     *
     * @return the adv type
     */
    int getAdvType();

    /**
     * 上传本机mac至服务器
     */
    void upLoadMac();

    /**
     * 上传本机运行app的版本至服务器
     *
     * @param ver the ver
     */
    void uploadAppVer(String ver);

    /**
     * 检查服务器上的最新的版本信息
     */
    void checkVer();

    /**
     * 设置人体检测的监听器
     *
     * @param humanCheckListner the human check listner
     * @return the human check listner
     */
    DeviceManager setHumanCheckListner(HumanCheckListner humanCheckListner);

    /**
     * 注销人体检测监听器
     */
    void unRegHumanCheckListner();

    /**
     * Sets update owener listner.
     *
     * @param updateOwenerListner the update owener listner
     */
    void setUpdateOwenerListner(UpdateOwenerListner updateOwenerListner);

    /**
     * Un reg update owner listner.
     */
    void unRegUpdateOwnerListner();

    /**
     * Sets service open lock listner.
     *
     * @param serviceOpenLockListner the service open lock listner
     */
    void setServiceOpenLockListner(ServiceOpenLockListner serviceOpenLockListner);

    /**
     * Un reg service open lock listner.
     */
    void unRegServiceOpenLockListner();

    /**
     * Sets serverstatus listner.
     *
     * @param serverstatusListner the serverstatus listner
     */
    void setServerstatusListner(ServerstatusListner serverstatusListner);

    /**
     * Un reg serverstatus listner.
     */
    void unRegServerstatusListner();

    /**
     * Sets lock push listener.
     *
     * @param lockPushListener the lock push listener
     * @return the lock push listener
     */
    DeviceManager setLockPushListener(LockPushListener lockPushListener);

    /**
     * Unreg lock push listener listner.
     */
    void unRegLockPushListenerListner();

    /**
     * Sets lock break listener.
     *
     * @param lockBreakListener the lock break listener
     * @return the lock break listener
     */
    DeviceManager setLockBreakListener(LockBreakListener lockBreakListener);

    /**
     * Un reg lock break listener.
     */
    void unRegLockBreakListener();

    /**
     * Upload lock.
     */
    void uploadLock();

    /**
     * Check crash log and upload.
     */
    void checkCrashLogAndUpload();

    /**
     * Init device.
     *
     * @param activity   the activity
     * @param appTypeNum the app type num
     * @return the device
     */
    DeviceManager init(Activity activity,int appTypeNum);

    /**
     * Release.
     */
    void release();

    /**
     * Update onwer status.
     */
    void updateOnwerStatus();

    /**
     * Sets ease acount listner.
     *
     * @param acountListner the acount listner
     */
    void setEaseAcountListner(EaseAccountListner acountListner);

    /**
     * Un reg ease acount listner.
     */
    void unRegEaseAcountListner();

    /**
     * Sets get ad time.
     *
     * @param GET_AD_TIME the get ad time
     * @return the get ad time
     */
    DeviceManager setGET_AD_TIME(int GET_AD_TIME);

    /**
     * Enable card reader device.
     *
     * @return the device
     */
    DeviceManager EnableCardReader();

    /**
     * Enable lock device.
     *
     * @return the device
     */
    DeviceManager EnableLock();

    /**
     * Enable led device.
     *
     * @return the device
     */
    DeviceManager EnableLed();

    /**
     * Enable dtimer device.
     *
     * @return the device
     */
    DeviceManager EnableDtimer();

    /**
     * Enable steer device.
     *
     * @return the device
     */
    DeviceManager EnableSteer();

}
