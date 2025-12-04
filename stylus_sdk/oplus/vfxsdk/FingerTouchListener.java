package com.oplusos.vfxsdk.doodleengine.stylusapi;

import android.graphics.PointF;
import java.util.List;

/* compiled from: FingerTouchListener.kt */
/* loaded from: classes5.dex */
public interface FingerTouchListener {
    void onMultiFingerTap(int i, List<? extends PointF> list);

    void onSingleFingerLongPress(float f, float f2);
}
