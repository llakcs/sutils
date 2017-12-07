package com.dchip.door.smartdoorsdk.opencv;

import android.content.Context;

import org.opencv.android.JavaCameraView;

/**
 * Created by llakcs on 2017/11/30.
 */

public interface OpencvManager{

     void onResume();

     void onDestroy();

     void onPause();

     void InitOpencv(Context context,JavaCameraView mOpenCvCameraView);

     void setFaceCount(int count);

     void setDetectionListner(DetectionListner detectionListner);
}
