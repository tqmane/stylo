package com.oplusos.vfxsdk.doodleengine;

import kotlin.jvm.internal.e;

/* compiled from: ShapeType.kt */
/* loaded from: classes5.dex */
public enum ShapeType {
    NONE_SHAPE,
    LINE_SHAPE,
    CURVE_SHAPE,
    CIRCLE_SHAPE,
    TRIANGLE_SHAPE,
    RECTANGLE_SHAPE,
    HEART_SHAPE,
    PENTAGON_SHAPE,
    STAR_SHAPE,
    CLOUD_SHAPE,
    SHAPE_NUMBER;

    public static final Companion Companion = new Companion(null);

    /* compiled from: ShapeType.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(e eVar) {
            this();
        }

        public final ShapeType getValue(int i) {
            int iOrdinal = ShapeType.SHAPE_NUMBER.ordinal();
            if (i < 0) {
                i = 0;
            }
            if (iOrdinal > i) {
                iOrdinal = i;
            }
            return ShapeType.values()[iOrdinal];
        }
    }
}
