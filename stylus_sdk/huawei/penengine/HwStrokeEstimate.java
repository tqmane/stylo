package com.huawei.stylus.penengine.estimate;

import com.huawei.penkit.impl.estimate.HwStrokeEstimateImpl;
import java.util.List;

/* loaded from: classes9.dex */
public final class HwStrokeEstimate {
    public static final String HW_STROKE_ESTIMATE_IMPL = "com.huawei.penkit.impl.estimate.HwStrokeEstimateImpl";
    public static final int RET_ERROR = -1;
    public static final String TAG = "PenKit-" + HwStrokeEstimate.class.getSimpleName();
    public static IHwStrokeEstimate sEstimate;

    static {
        try {
            if (Class.forName(HW_STROKE_ESTIMATE_IMPL) == null || sEstimate != null) {
                return;
            }
            sEstimate = new HwStrokeEstimateImpl();
        } catch (ClassNotFoundException unused) {
        }
    }

    public static synchronized int getEstimateEvent(IHwRecycleQueue iHwRecycleQueue, List<HwMotionEventInfo> list) {
        if (sEstimate == null) {
            return -1;
        }
        return sEstimate.getEstimateEvent(iHwRecycleQueue, list);
    }

    public static synchronized boolean isFeatureEnable() {
        if (sEstimate == null) {
            return false;
        }
        return sEstimate.isFeatureEnable();
    }

    public static synchronized void setRefreshRate(float f) {
        if (sEstimate != null) {
            sEstimate.setRefreshRate(f);
        }
    }
}
