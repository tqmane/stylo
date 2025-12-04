package com.oplusos.vfxsdk.doodleengine;

import kotlin.jvm.internal.e;

/* compiled from: Operation.kt */
/* loaded from: classes5.dex */
public enum Operation {
    PENCIL,
    MARK,
    BALLPEN,
    PEN,
    CRAYON,
    BRUSH,
    GEOMETRY,
    TEXTBOX,
    PointErase,
    LineErase,
    PixelErase,
    Selection;

    public static final Companion Companion = new Companion(null);

    /* compiled from: Operation.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(e eVar) {
            this();
        }

        public final Operation getValue(int i) {
            int iOrdinal = Operation.Selection.ordinal();
            if (i < 0) {
                i = 0;
            }
            if (iOrdinal > i) {
                iOrdinal = i;
            }
            return Operation.values()[iOrdinal];
        }
    }
}
