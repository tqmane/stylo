package com.oplusos.vfxsdk.doodleengine.stylusapi;

import com.oplusos.vfxsdk.doodleengine.CanvasExtendType;
import com.oplusos.vfxsdk.doodleengine.LassoOperation;
import com.oplusos.vfxsdk.doodleengine.MenuType;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PaintViewListener.kt */
/* loaded from: classes5.dex */
public interface PaintViewListener {
    void initialized();

    void onAddedNode();

    void onAdsorption();

    void onCanvasExtended(CanvasExtendType canvasExtendType, float f);

    void onCreateBackGroundError(int i);

    void onGenerateImage();

    void onGenerateRelativeImage();

    void onLassoOperationBegin(LassoOperation lassoOperation);

    void onLassoOperationEnd(LassoOperation lassoOperation, int i);

    void onLoaded();

    void onMenuChanged(MenuType menuType, int i, Integer num, int i2, String[] strArr, String[] strArr2, int[] iArr, int[] iArr2);

    void onRedone();

    void onResetEnd();

    void onUndid();

    /* compiled from: PaintViewListener.kt */
    public static final class DefaultImpls {
        public static void onCanvasExtended(PaintViewListener paintViewListener, CanvasExtendType code, float f) {
            Intrinsics.checkNotNullParameter(code, "code");
        }

        public static void onLassoOperationBegin(PaintViewListener paintViewListener, LassoOperation op) {
            Intrinsics.checkNotNullParameter(op, "op");
        }

        public static void onLassoOperationEnd(PaintViewListener paintViewListener, LassoOperation op, int i) {
            Intrinsics.checkNotNullParameter(op, "op");
        }

        public static void onMenuChanged(PaintViewListener paintViewListener, MenuType type, int i, Integer num, int i2, String[] entityIdArray, String[] entityExtraArray, int[] entityTypeArray, int[] entityColorArray) {
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(entityIdArray, "entityIdArray");
            Intrinsics.checkNotNullParameter(entityExtraArray, "entityExtraArray");
            Intrinsics.checkNotNullParameter(entityTypeArray, "entityTypeArray");
            Intrinsics.checkNotNullParameter(entityColorArray, "entityColorArray");
        }

        public static void initialized(PaintViewListener paintViewListener) {
        }

        public static void onAddedNode(PaintViewListener paintViewListener) {
        }

        public static void onAdsorption(PaintViewListener paintViewListener) {
        }

        public static void onGenerateImage(PaintViewListener paintViewListener) {
        }

        public static void onGenerateRelativeImage(PaintViewListener paintViewListener) {
        }

        public static void onLoaded(PaintViewListener paintViewListener) {
        }

        public static void onRedone(PaintViewListener paintViewListener) {
        }

        public static void onResetEnd(PaintViewListener paintViewListener) {
        }

        public static void onUndid(PaintViewListener paintViewListener) {
        }

        public static void onCreateBackGroundError(PaintViewListener paintViewListener, int i) {
        }
    }
}
