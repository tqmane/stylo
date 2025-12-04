package com.oplusos.vfxsdk.doodleengine;

import android.content.Context;
import com.oplusos.vfxsdk.doodleengine.stylusapi.ExportImageProvider;
import com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface;
import com.oplusos.vfxsdk.doodleengine.stylusapi.TrackEngineCallback;

/* compiled from: StylusImplProvider.kt */
/* loaded from: classes5.dex */
public interface StylusImplProvider {
    ExportImageProvider getExportImageInterface();

    StylusInterface getStylusInterface(Context context);

    TrackEngineCallback getTrackEngineCallback(String str);

    int isThereMore(int i, int i2, String str);
}
