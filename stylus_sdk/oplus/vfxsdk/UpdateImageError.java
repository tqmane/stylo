package com.oplusos.vfxsdk.doodleengine.stylusapi;

import com.nearme.note.db.FolderUtil;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ImageOperationCallbacks.kt */
/* loaded from: classes5.dex */
public enum UpdateImageError {
    IMAGE_NOT_FOUND(200, "The target image to update was not found"),
    OVER_SIZE(201, "The maximum size of the new image allowed is 6M"),
    DECODE_ERROR(FolderUtil.CODE_UPDATE_FOLDER_NEW_NAME_EMPTY, "Decode new image failed"),
    FILE_NOT_FOUND(FolderUtil.CODE_UPDATE_FOLDER_GUID_NOT_EXISTED, "The new image file for update was not found"),
    NATIVE_ERROR(FolderUtil.CODE_UPDATE_FOLDER_EXCEPTION, "Native error during update"),
    UNKNOWN_ERROR(299, "Unknown error during update"),
    NO_SELECTED_IMAGE(205, "No select");

    private final int code;
    private String msg;

    UpdateImageError(int i, String str) {
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
