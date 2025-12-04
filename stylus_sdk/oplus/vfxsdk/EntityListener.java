package com.oplusos.vfxsdk.doodleengine.stylusapi;

import com.oplusos.vfxsdk.doodleengine.EntityOperationResult;

/* compiled from: EntityListener.kt */
/* loaded from: classes5.dex */
public interface EntityListener {
    void onAfterAddEntity(String str, String str2, EntityOperationResult entityOperationResult);

    void onAfterUpdateEntity(String str, String str2, EntityOperationResult entityOperationResult);

    void requestUpdateBitmap(int i, String[] strArr, String[] strArr2, int[] iArr, int[] iArr2);
}
