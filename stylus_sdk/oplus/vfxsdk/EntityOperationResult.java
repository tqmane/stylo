package com.oplusos.vfxsdk.doodleengine;

import kotlin.jvm.internal.e;

/* compiled from: EntityOperationResult.kt */
/* loaded from: classes5.dex */
public enum EntityOperationResult {
    Success,
    IdDuplication,
    IdNotExist,
    BitmapError,
    Unknown;

    public static final Companion Companion = new Companion(null);

    /* compiled from: EntityOperationResult.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(e eVar) {
            this();
        }

        public final EntityOperationResult getValue(int i) {
            int iOrdinal = EntityOperationResult.Unknown.ordinal();
            if (i < 0) {
                i = 0;
            }
            if (iOrdinal > i) {
                iOrdinal = i;
            }
            return EntityOperationResult.values()[iOrdinal];
        }
    }
}
