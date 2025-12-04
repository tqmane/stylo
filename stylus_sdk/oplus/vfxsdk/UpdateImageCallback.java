package com.oplusos.vfxsdk.doodleengine.stylusapi;

/* compiled from: ImageOperationCallbacks.kt */
/* loaded from: classes5.dex */
public interface UpdateImageCallback {

    /* compiled from: ImageOperationCallbacks.kt */
    public static final class DefaultImpls {
        public static /* synthetic */ void onSuccess$default(UpdateImageCallback updateImageCallback, String str, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: onSuccess");
            }
            if ((i & 1) != 0) {
                str = null;
            }
            updateImageCallback.onSuccess(str);
        }
    }

    void onFailure(UpdateImageError updateImageError);

    void onSuccess(String str);
}
