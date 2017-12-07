package com.dchip.door.smartdoorsdk.deviceControl;

import android.util.Log;

import com.dchip.door.smartdoorsdk.deviceControl.nativeLev.Pn512Card;
import com.dchip.door.smartdoorsdk.s;
import com.dchip.door.smartdoorsdk.service.DeviceService;
import com.dchip.door.smartdoorsdk.utils.LogUtil;


/**
 * Created by jelly on 2017/11/11.
 */

public class CardHandler {
    private static final String TAG = "CardHandler";
    private static CardHandler instance;
    private static Pn512Card mcard;
    private boolean stop = false;

    public static CardHandler getInstance() {
        if (instance == null) {
            instance = new CardHandler();
        }
        return instance;
    }

    public CardHandler() {
        mcard = new Pn512Card();
        new Thread(runnable).start();
    }

    public void finish() {
        stop = true;
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mcard.open();
            while (!stop) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                boolean b = mcard.cardDetect();
                Log.i(TAG, "CardDetect=" + b);
                if (b) {
                    mcard.cardChecked();
                    //// TODO: 2017/11/11 验证卡
                    if (mcard.operation("FF82000006DCDCDCDCDCDC").replace(" ","").equals("9000") && mcard.operation("FF8800076000").replace(" ","").equals("9000")) {
                        String id = mcard.operation("FFCA000000");
                        LogUtil.d(TAG,"读卡成功：" + id);
                        s.device().getLock().openLock();
                    }
//                    Log.w(TAG,"验证B密码:"+mcard.operation("FF82000006CDCDCDCDCDCD"));
//                    Log.w(TAG,"验证:"+mcard.operation("FF8800076000"));

                }
            }
            LogUtil.d(TAG,"读卡设备关闭 stop:"+stop);
            mcard.close();
        }
    };
}
