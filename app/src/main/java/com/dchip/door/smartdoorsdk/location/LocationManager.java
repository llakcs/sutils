package com.dchip.door.smartdoorsdk.location;

import android.app.Activity;
import android.content.Context;

/**
 * Created by llakcs on 2017/12/1.
 */

public interface LocationManager {
    void startLocation(Activity activity);
    void onStop();
    void setLocationRecvListner(BDlocationRecvListner recvListner);
}
