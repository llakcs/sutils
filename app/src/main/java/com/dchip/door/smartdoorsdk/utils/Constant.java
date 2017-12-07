package com.dchip.door.smartdoorsdk.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by llakcs on 2017/11/29.
 */

public class Constant {
    //sdcardpath
    public static final String SDCARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String BASE_PATh = SDCARD_PATH + File.separator + "smdsdk" + File.separator;
    //opencv
    public static final String VISTPATH = BASE_PATh + File.separator + "vistdata";
    public static final String DOWNLOAD_PATH = BASE_PATh + "downloadAPK" + File.separator;
    //crash
    public static final String CRASH_LOG_PATH = BASE_PATh + "crashLog" + File.separator;
    public static final String CRASH_LOG_UPLOAD_FAIL_PATH = BASE_PATh + "crashLog" + File.separator + "uploadFail" + File.separator;
    public static final String CARDS_FILE_PATH = BASE_PATh + "cards.txt";
    public static final String WS_URI = "ws://%s";


    //阿里云服务器
//    public static final String wsUrl = "119.23.149.160/door/websocket/";
//    public static final String serverUrl = "http://119.23.149.160/door/api/";
    //阿里云测试服务器
     public static final String wsUrl="119.23.149.160/doortest/websocket/";
     public static final String serverUrl="http://119.23.149.160/doortest/api/";

     //上传mac
     public static final String API_UPLOADMAC=serverUrl+"maincontrol/upload";


}
