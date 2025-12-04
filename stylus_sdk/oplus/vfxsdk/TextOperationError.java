package com.oplusos.vfxsdk.doodleengine.stylusapi;

import com.nearme.note.db.FolderUtil;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: TextOperationCallback.kt */
/* loaded from: classes5.dex */
public enum TextOperationError {
    GENERATE_BITMAP_FAILED(100, "Failed to generate bitmap."),
    NATIVE_ERROR(FolderUtil.CODE_UPDATE_FOLDER_EXCEPTION, "Native error during update"),
    CONTENT_EMPTY(101, "Content is empty.");

    private final int code;
    private String msg;

    TextOperationError(int i, String str) {
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
