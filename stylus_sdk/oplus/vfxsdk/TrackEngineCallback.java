package com.oplusos.vfxsdk.doodleengine.stylusapi;

import android.content.Context;
import com.oplusos.vfxsdk.doodleengine.LassoOperation;
import com.oplusos.vfxsdk.doodleengine.Paint;

/* compiled from: TrackEngineCallback.kt */
/* loaded from: classes5.dex */
public interface TrackEngineCallback {
    void commit();

    void initEnd(Context context, long j);

    void loadBegin();

    void loadEnd();

    void saveBegin();

    void saveEnd();

    void soundSwitch(boolean z);

    void trackColorPickerInfo(int i);

    void trackEraserInfo(int i);

    void trackLassoInfo(LassoOperation lassoOperation);

    void trackLongTouchInfo();

    void trackOverlayInfo();

    void trackPaintInfo(Paint.Type type);

    void trackShapeInfo(int i);

    void uploadDataInRealTime();
}
