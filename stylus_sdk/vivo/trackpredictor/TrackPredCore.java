package com.vivo.trackpredictor.core;

import android.content.Context;
import com.huawei.hms.framework.common.grs.GrsUtils;
import com.vivo.trackpredictor.utils.CommonUtils;
import com.vivo.trackpredictor.utils.HwPoint;
import java.io.IOException;
import java.util.List;

/* loaded from: classes4.dex */
public class TrackPredCore {
    public String TAG;
    public HwPoint pred;
    public float[] res;

    public static class SingleHolder {
        public static final TrackPredCore instance = new TrackPredCore();
    }

    static {
        try {
            System.loadLibrary("track_prediction");
        } catch (UnsatisfiedLinkError e) {
            String str = "failed to load native library: " + e.getMessage();
        }
    }

    public static TrackPredCore getInstance() {
        return SingleHolder.instance;
    }

    private native int initial(String str, String str2);

    private native float[] predict(int i, float f, float f2, int i2, float f3, int i3);

    public HwPoint doPredict(List<HwPoint> list, int i) {
        if (i != 1 && i != 2) {
            HwPoint hwPoint = list.get(list.size() - 1);
            this.pred.setPointX(hwPoint.getPointX());
            this.pred.setPointY(hwPoint.getPointY());
            return this.pred;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            HwPoint hwPoint2 = list.get(i2);
            this.res = predict(hwPoint2.getPointTime(), hwPoint2.getPointX(), hwPoint2.getPointY(), hwPoint2.getActionCode(), hwPoint2.getPointPressure(), i);
        }
        this.pred.setPointX(this.res[0]);
        this.pred.setPointY(this.res[1]);
        return this.pred;
    }

    public boolean init(Context context) throws Throwable {
        String str = context.getCacheDir() + GrsUtils.SEPARATOR + "optparam1130_best.cfg";
        String str2 = context.getCacheDir() + GrsUtils.SEPARATOR + "optparam240_12.cfg";
        try {
            CommonUtils.copyAssetResource2File(context, "optparam1130_best.cfg", str);
            CommonUtils.copyAssetResource2File(context, "optparam240_12.cfg", str2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return initial(str, str2) == 0;
    }

    public native void release();

    public TrackPredCore() {
        this.TAG = "trackPredictionSDK";
        this.res = new float[2];
        this.pred = new HwPoint();
    }

    public HwPoint doPredict(HwPoint hwPoint, int i) {
        if (i != 1 && i != 2) {
            this.pred.setPointX(hwPoint.getPointX());
            this.pred.setPointY(hwPoint.getPointY());
            return this.pred;
        }
        this.res = predict(hwPoint.getPointTime(), hwPoint.getPointX(), hwPoint.getPointY(), hwPoint.getActionCode(), hwPoint.getPointPressure(), i);
        this.pred.setPointX(this.res[0]);
        this.pred.setPointY(this.res[1]);
        return this.pred;
    }
}
