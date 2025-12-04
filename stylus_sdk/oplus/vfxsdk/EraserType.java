package com.oplusos.vfxsdk.doodleengine;

import kotlin.jvm.internal.e;

/* compiled from: EraserType.kt */
/* loaded from: classes5.dex */
public enum EraserType {
    POINT,
    LINE;

    public static final Companion Companion = new Companion(null);

    /* compiled from: EraserType.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(e eVar) {
            this();
        }

        public final EraserType getType(int i) {
            return EraserType.values()[i];
        }
    }
}
