package com.oplusos.vfxsdk.doodleengine;

import kotlin.jvm.internal.e;

/* compiled from: FileCode.kt */
/* loaded from: classes5.dex */
public enum FileCode {
    Success,
    InvalidInstance,
    Empty,
    InvalidImagePath,
    InvalidDataPath,
    ImageWriteFailed,
    DataWriteFailed,
    LowMemory,
    NoChange,
    InvalidFile,
    DataDecodeFailed,
    ImageDecodeFailed,
    IncompatibleVersion,
    BlockFormatError,
    ForceQuit,
    Busy,
    Unknown;

    public static final Companion Companion = new Companion(null);

    /* compiled from: FileCode.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(e eVar) {
            this();
        }

        public final FileCode getValue(int i) {
            int iOrdinal = FileCode.Unknown.ordinal();
            if (i < 0) {
                i = 0;
            }
            if (iOrdinal > i) {
                iOrdinal = i;
            }
            return FileCode.values()[iOrdinal];
        }
    }
}
