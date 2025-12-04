package com.huawei.stylus.penengine.estimate;

import java.util.List;

/* loaded from: classes9.dex */
public interface IHwStrokeEstimate {
    int getEstimateEvent(IHwRecycleQueue iHwRecycleQueue, List<HwMotionEventInfo> list);

    boolean isFeatureEnable();

    void setRefreshRate(float f);
}
