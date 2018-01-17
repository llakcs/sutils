package com.dchip.door.smartdoorsdk.opencv;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.dchip.door.smartdoorsdk.Bean.FacePojo;
import com.dchip.door.smartdoorsdk.R;
import com.dchip.door.smartdoorsdk.s;
import com.dchip.door.smartdoorsdk.utils.Constant;
import com.dchip.door.smartdoorsdk.utils.LogUtil;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_face;
import org.bytedeco.javacpp.opencv_imgproc;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.bytedeco.javacpp.opencv_core.CV_32SC1;
import static org.bytedeco.javacpp.opencv_core.CV_HIST_ARRAY;
import static org.bytedeco.javacpp.opencv_face.createFisherFaceRecognizer;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
import static org.bytedeco.javacpp.opencv_imgproc.cvCalcHist;
import static org.bytedeco.javacpp.opencv_imgproc.cvCompareHist;
import static org.bytedeco.javacpp.opencv_imgproc.cvNormalizeHist;
import static org.opencv.imgcodecs.Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;
import static org.opencv.imgproc.Imgproc.CV_COMP_CORREL;

/**
 * Created by llakcs on 2017/11/30.
 */

public class OpencvImpl implements OpencvManager,CameraBridgeViewBase.CvCameraViewListener2{

    private OpencvImpl(){

    }

    private static final Object lock = new Object();
    private static volatile OpencvImpl instance;
    public static void registerInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new OpencvImpl();
                }
            }
        }
        s.Ext.setOpencvManager(instance);
    }
    private JavaCameraView mOpenCvCameraView;
    private String TAG="OpencvImpl";
    private File mCascadeFile;
    private CascadeClassifier mJavaDetector;
    private DetectionBasedTracker mNativeDetector;
    private Mat mRgba;
    private Mat mGray;
    private int faceSerialCount = 0;
    private float mRelativeFaceSize = 0.2f;
    private int mAbsoluteFaceSize = 0;
    private static final Scalar FACE_RECT_COLOR = new Scalar(0, 255, 0, 255);
    public static final int JAVA_DETECTOR = 0;
    public static final int NATIVE_DETECTOR = 1;
    private int mDetectorType = JAVA_DETECTOR;
    private int FACECOUNT = 3;
    private int RECTCOUNT = 0;
    private Context mContext;
    private DetectionListner mDetection;
    private FacePojo fp;
    private List<FacePojo> faceList = new ArrayList<FacePojo>();
    @Override
    public void onResume() {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                if (!OpenCVLoader.initDebug()) {
//                    LogUtil.e(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
////                    OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, mContext, mLoaderCallback);
//                } else {
//                    LogUtil.e(TAG, "OpenCV library found inside package. Using it!");
                    mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
//                }
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        if (mOpenCvCameraView != null) {
            mOpenCvCameraView.disableView();
        }
        if(mContext != null){
            mContext = null;
        }

    }

    @Override
    public void onPause() {
        if (mOpenCvCameraView != null) {
            mOpenCvCameraView.disableView();
        }
    }

    @Override
    public void InitOpencv(Context context,JavaCameraView OpenCvCameraView) {
        this.mOpenCvCameraView = OpenCvCameraView;
        this.mContext =context;
        mOpenCvCameraView.setVisibility(CameraBridgeViewBase.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);

    }


    @Override
    public double CmpPic(String facepath, String localpath) {
        int l_bins = 20;
        int hist_size[] = {l_bins};

        float v_ranges[] = {0, 100};
        float ranges[][] = {v_ranges};

        opencv_core.IplImage Image1 = cvLoadImage(Constant.VISTPATH + facepath, CV_LOAD_IMAGE_GRAYSCALE);
        opencv_core.IplImage Image2 = cvLoadImage(Constant.FACEDETEPATH + localpath, CV_LOAD_IMAGE_GRAYSCALE);

        opencv_core.IplImage imageArr1[] = {Image1};
        opencv_core.IplImage imageArr2[] = {Image2};

        opencv_core.CvHistogram Histogram1 = opencv_core.CvHistogram.create(1, hist_size, CV_HIST_ARRAY, ranges, 1);
        opencv_core.CvHistogram Histogram2 = opencv_core.CvHistogram.create(1, hist_size, CV_HIST_ARRAY, ranges, 1);

        cvCalcHist(imageArr1, Histogram1, 0, null);
        cvCalcHist(imageArr2, Histogram2, 0, null);

        cvNormalizeHist(Histogram1, 100.0);
        cvNormalizeHist(Histogram2, 100.0);

        return cvCompareHist(Histogram1, Histogram2, CV_COMP_CORREL);
    }

    /**
     * 设置人脸识别几次之后拍照
     * @param count
     */
    @Override
    public void setFaceCount(int count) {
        this.FACECOUNT = count;
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        mGray = new Mat();
        mRgba = new Mat();
    }

    @Override
    public void onCameraViewStopped() {
        mGray.release();
        mRgba.release();
    }

    @Override
    public void setDetectionListner(DetectionListner detectionListner) {
        this.mDetection = detectionListner;
    }

    @Override
    public void unRegDetectionListner() {
        if(mDetection != null){
            mDetection = null;
        }
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mRgba = inputFrame.rgba();
        mGray = inputFrame.gray();

        if (mAbsoluteFaceSize == 0) {
            LogUtil.e(TAG,"###mAbsoluteFaceSize == 0");
            int height = mGray.rows();
            if (Math.round(height * mRelativeFaceSize) > 0) {
                mAbsoluteFaceSize = Math.round(height * mRelativeFaceSize);
            }
            mNativeDetector.setMinFaceSize(mAbsoluteFaceSize);
        }

        MatOfRect faces = new MatOfRect();

        if (mDetectorType == JAVA_DETECTOR) {
            if (mJavaDetector != null)
                mJavaDetector.detectMultiScale(mGray, faces, 1.1, 2, 2, // TODO: objdetect.CV_HAAR_SCALE_IMAGE
                        new Size(mAbsoluteFaceSize, mAbsoluteFaceSize), new Size());

        } else if (mDetectorType == NATIVE_DETECTOR) {
            if (mNativeDetector != null)
                mNativeDetector.detect(mGray, faces);

        } else {
            LogUtil.e(TAG, "Detection method is not selected!");
        }

        Rect[] facesArray = faces.toArray();
        /**
         * add by lee
         */
        int faceCount = facesArray.length;
        if (faceCount > 0) {
            faceSerialCount++;
        } else {
            faceSerialCount = 0;
        }
        if (faceSerialCount > FACECOUNT || RECTCOUNT >FACECOUNT) {
            LogUtil.e(TAG, "#####识别中");
            String facepName = "vist" + System.currentTimeMillis() + ".jpg";
            mOpenCvCameraView.takephoto(Constant.VISTPATH+ facepName);
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            mDetection.complete(Constant.VISTPATH+ facepName);
            RECTCOUNT = 0;
            faceSerialCount = -5000;
        }

        for (int i = 0; i < facesArray.length; i++){
            RECTCOUNT++;
            Imgproc.rectangle(mRgba, facesArray[i].tl(), facesArray[i].br(), FACE_RECT_COLOR, 3);
        }

        return mRgba;
    }




    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(mContext) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    LogUtil.e(TAG, "OpenCV loaded successfully");
                    // Load native library after(!) OpenCV initialization
                    System.loadLibrary("opencv_java3");
                    System.loadLibrary("detection_based_tracker");
                    try {
                        // load cascade file from application resources
                        InputStream is = mContext.getResources().openRawResource(R.raw.lbpcascade_frontalface);
                        File cascadeDir = mContext.getDir("cascade", Context.MODE_PRIVATE);
                        mCascadeFile = new File(cascadeDir, "lbpcascade_frontalface.xml");
                        FileOutputStream os = new FileOutputStream(mCascadeFile);

                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = is.read(buffer)) != -1) {
                            os.write(buffer, 0, bytesRead);
                        }
                        is.close();
                        os.close();

                        mJavaDetector = new CascadeClassifier(mCascadeFile.getAbsolutePath());
                        if (mJavaDetector.empty()) {
                            LogUtil.e(TAG, "Failed to load cascade classifier");
                            mJavaDetector = null;
                        } else {
                            LogUtil.i(TAG, "Loaded cascade classifier from " + mCascadeFile.getAbsolutePath());

                            mNativeDetector = new DetectionBasedTracker(mCascadeFile.getAbsolutePath(), 0);
                        }

                        cascadeDir.delete();

                    } catch (IOException e) {
                        e.printStackTrace();
                        LogUtil.e(TAG, "Failed to load cascade. Exception thrown: " + e);
                    }

                    mOpenCvCameraView.enableView();

                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };
}
