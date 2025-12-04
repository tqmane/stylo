package com.oplusos.vfxsdk.doodleengine.stylusapi;

import com.oplusos.vfxsdk.doodleengine.FileCode;
import com.oplusos.vfxsdk.doodleengine.base.ActionResult;
import java.util.ArrayList;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SaveListener.kt */
/* loaded from: classes5.dex */
public interface SaveListener {
    void onDataTruncation();

    void onDrawingsSaved(FileCode fileCode, ArrayList<String> arrayList);

    void onPreviewSaved(FileCode fileCode, String str);

    void onSaved(ActionResult actionResult);

    /* compiled from: SaveListener.kt */
    public static final class DefaultImpls {
        public static void onSaved(SaveListener saveListener, ActionResult result) {
            Intrinsics.checkNotNullParameter(result, "result");
        }

        public static void onDataTruncation(SaveListener saveListener) {
        }

        public static void onDrawingsSaved(SaveListener saveListener, FileCode fileCode, ArrayList<String> arrayList) {
        }

        public static void onPreviewSaved(SaveListener saveListener, FileCode fileCode, String str) {
        }
    }
}
