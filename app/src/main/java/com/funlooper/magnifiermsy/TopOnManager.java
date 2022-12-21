package com.funlooper.magnifiermsy;

import android.content.Context;
import android.util.Log;

import com.anythink.core.api.ATSDK;
import com.anythink.core.api.DeviceInfoCallback;

public class TopOnManager {

    public void init(Context context) {
        ATSDK.testModeDeviceInfo(context, new DeviceInfoCallback() {
            @Override
            public void deviceInfo(String s) {
                Log.e("TopOnManager", s);
            }
        });

        ATSDK.init(context, "a6364fb7396f65", "c13e4357e57d78e76e472899eb182e98"); //初始化SDK
    }
}
