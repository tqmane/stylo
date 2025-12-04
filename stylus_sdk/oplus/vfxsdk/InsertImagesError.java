package com.oplusos.vfxsdk.doodleengine.stylusapi;

import com.oplus.dmp.sdk.index.IndexProtocol;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ImageOperationCallbacks.kt */
/* loaded from: classes5.dex */
public enum InsertImagesError {
    REACH_LIMIT(100, "The maximum number of images allowed is 30"),
    OVER_SIZE(101, "The maximum size of each image allowed is 6M"),
    DECODE_ERROR(102, "Decode image failed"),
    UNKNOWN_ERROR(103, IndexProtocol.ERROR_MSG_UNKNOWN),
    FILE_NOT_FOUND(104, "File not found"),
    NATIVE_ERROR(105, "Native error"),
    OUT_OF_BOUNDS(106, "Out of bounds");

    private final int code;
    private String msg;

    InsertImagesError(int i, String str) {
        this.code = i;
        this.msg = str;
    }

    public final int getCode() {
        return this.code;
    }

    public final String getMsg() {
        return this.msg;
    }

    public final void setMsg(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.msg = str;
    }
}
