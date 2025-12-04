package com.huawei.stylus.penengine.estimate;

import com.huawei.penkit.impl.estimate.HwRecycleQueueImpl;
import java.util.List;

/* loaded from: classes9.dex */
public final class HwMotionEventQueue {
    public static final int RET_ERROR = -1;
    public static final String TAG = "PenKit-" + HwMotionEventQueue.class.getSimpleName();
    public IHwRecycleQueue mRecycleQueue;

    public HwMotionEventQueue() {
        if (this.mRecycleQueue == null) {
            this.mRecycleQueue = new HwRecycleQueueImpl();
        }
    }

    public void clear() {
        IHwRecycleQueue iHwRecycleQueue = this.mRecycleQueue;
        if (iHwRecycleQueue != null) {
            iHwRecycleQueue.clear();
        }
    }

    public int fill(List<HwMotionEventInfo> list) {
        IHwRecycleQueue iHwRecycleQueue = this.mRecycleQueue;
        if (iHwRecycleQueue != null) {
            return iHwRecycleQueue.fill(list);
        }
        return -1;
    }

    public IHwRecycleQueue getQueue() {
        return this.mRecycleQueue;
    }
}
