package com.oplusos.vfxsdk.doodleengine;

import kotlin.jvm.internal.e;

/* compiled from: VibrationType.kt */
/* loaded from: classes5.dex */
public enum VibrationType {
    PENCIL,
    ERASE,
    BALL,
    PEN,
    NONE;

    public static final Companion Companion = new Companion(null);

    /* compiled from: VibrationType.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(e eVar) {
            this();
        }

        public final VibrationType getValue(int i) {
            int iOrdinal = VibrationType.NONE.ordinal();
            if (i < 0) {
                i = 0;
            }
            if (iOrdinal > i) {
                iOrdinal = i;
            }
            return VibrationType.values()[iOrdinal];
        }
    }
}
