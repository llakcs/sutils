package com.dchip.door.smartdoorsdk;

import android.app.Application;
import android.content.Context;
import com.dchip.door.smartdoorsdk.deviceControl.DeviceImpl;
import com.dchip.door.smartdoorsdk.deviceControl.DeviceManager;
import com.dchip.door.smartdoorsdk.location.LocationManager;
import com.dchip.door.smartdoorsdk.location.locationImpl;
import com.dchip.door.smartdoorsdk.opencv.OpencvImpl;
import com.dchip.door.smartdoorsdk.opencv.OpencvManager;
import com.dchip.door.smartdoorsdk.player.IMPlayer;
import com.dchip.door.smartdoorsdk.player.MPlayer;
import com.dchip.door.smartdoorsdk.utils.LogUtil;
import com.dchip.door.smartdoorsdk.voice.BDVoiceImpl;
import com.dchip.door.smartdoorsdk.voice.BDVoiceManager;
import java.lang.reflect.Method;


/**
 * Created by llakcs on 2017/11/29.
 */

public class s {


    private s(){
    }

    public static Application app() {
        if (s.Ext.app == null) {
            try {
                // 在IDE进行布局预览时使用
                Class<?> renderActionClass = Class.forName("com.android.layoutlib.bridge.impl.RenderAction");
                Method method = renderActionClass.getDeclaredMethod("getCurrentContext");
                Context context = (Context) method.invoke(null);
                s.Ext.app = new s.MockApplication(context);
            } catch (Throwable ignored) {
                throw new RuntimeException("please invoke x.Ext.init(app) on Application#onCreate()"
                        + " and register your Application in manifest.");
            }
        }
        return s.Ext.app;
    }
    private static class MockApplication extends Application {
        public MockApplication(Context baseContext) {
            this.attachBaseContext(baseContext);
        }
    }

    public static OpencvManager openv(){
        if(Ext.opencvManager == null){
            OpencvImpl.registerInstance();
        }
        return Ext.opencvManager;
    }
    public static BDVoiceManager voice(){
        if(Ext.bdVoiceManager == null){
            BDVoiceImpl.registerInstance();
        }
        return Ext.bdVoiceManager;
    }

    public static LocationManager location(){
        if(Ext.locationManager == null){
            locationImpl.registerInstance();
        }
        return Ext.locationManager;
    }


    public static DeviceManager device(){
        if(Ext.deviceManager == null){
            DeviceImpl.registerInstance();
        }
        return Ext.deviceManager;
    }

    public static IMPlayer player(){
        if(Ext.imPlayer == null){
            MPlayer.registerInstance();
        }
        return Ext.imPlayer;
    }


    public static class Ext{

        private static Application app;
        private static OpencvManager opencvManager;
        private static BDVoiceManager bdVoiceManager;
        private static LocationManager locationManager;
        private static DeviceManager deviceManager;
        private static IMPlayer imPlayer;
        public static boolean debug;
        private Ext(){
        }

        /**
         * DEBUG模式下打印日志
         * @param debug
         */
        public static void setDebug(boolean debug) {
            Ext.debug = debug;
            if(debug){
                LogUtil.setLevel(1);
            }else{
                LogUtil.setLevel(5);
            }

        }


        /**
         * 初始化
         * @param app
         */
        public static void init(Application app){
            if (Ext.app == null) {
                Ext.app = app;
            }
            SdkInit.onCreate(Ext.app);
        }

        public static void setOpencvManager(OpencvManager opencvManager){
            Ext.opencvManager = opencvManager;
        }


        public static void setBDVoiceManager(BDVoiceManager bdVoiceManager){
            Ext.bdVoiceManager = bdVoiceManager;
        }

        public static void setLocationManager(LocationManager locationManager){
            Ext.locationManager = locationManager;
        }

        public static void setDeviceManager(DeviceManager deviceManager){
            Ext.deviceManager = deviceManager;
        }

        public static void setImPlayerManager(IMPlayer player){
            Ext.imPlayer = player;
        }


    }



}
