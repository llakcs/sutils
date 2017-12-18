package com.dchip.door.smartdoorsdk.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by llakcs on 2017/11/29.
 */

public class Constant {
    //sdcardpath
//    public static final String SDCARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String SDCARD_PATH = "/sdcard";
    public static final String BASE_PATh = SDCARD_PATH + File.separator + "smdsdk" + File.separator;
    //opencv
    public static final String VISTPATH = BASE_PATh + "vistdata";
    public static final String DOWNLOAD_PATH = BASE_PATh + "downloadAPK" + File.separator;
    //crash
    public static final String CRASH_LOG_PATH = BASE_PATh + "crashLog" + File.separator;
    public static final String CRASH_LOG_UPLOAD_FAIL_PATH = BASE_PATh + "crashLog" + File.separator + "uploadFail" + File.separator;
    public static final String CARDS_FILE_PATH = BASE_PATh + "cards.txt";

    public static final String WS_URI = "ws://%s";
    public static final String LOCK_CONFIG_FILE_PATH = BASE_PATh + "lockConfig.txt";
}
