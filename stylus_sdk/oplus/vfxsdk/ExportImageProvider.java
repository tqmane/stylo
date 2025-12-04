package com.oplusos.vfxsdk.doodleengine.stylusapi;

import android.content.Context;
import android.graphics.Bitmap;
import kotlin.Unit;
import kotlin.jvm.functions.l;

/* compiled from: ExportImageProvider.kt */
/* loaded from: classes5.dex */
public interface ExportImageProvider {
    void exportBitmapFromPaint(Context context, String str, String str2, int i, int i2, l<? super Bitmap, Unit> lVar);

    void initAuth(Context context, l<? super Boolean, Unit> lVar);
}
