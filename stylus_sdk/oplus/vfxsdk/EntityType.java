package com.oplusos.vfxsdk.doodleengine.stylusapi;

import kotlin.collections.k;
import kotlin.jvm.internal.e;

/* compiled from: EntityType.kt */
/* loaded from: classes5.dex */
public enum EntityType {
    UNKNOWN,
    IMAGE,
    TEXT;

    public static final Companion Companion = new Companion(null);

    /* compiled from: EntityType.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(e eVar) {
            this();
        }

        public final EntityType getValue(int i) {
            EntityType[] entityTypeArrValues = EntityType.values();
            return (i < 0 || i > k.R1(entityTypeArrValues)) ? EntityType.UNKNOWN : entityTypeArrValues[i];
        }
    }
}
