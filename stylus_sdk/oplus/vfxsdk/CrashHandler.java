package com.oplusos.vfxsdk.doodleengine.stylus;

import com.oplus.touchnode.OplusTouchNodeManager;
import java.lang.Thread;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.e;

/* compiled from: CrashHandler.kt */
/* loaded from: classes5.dex */
public final class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String COMMAND_STOP_VIBRATION = "2";
    public static final Companion Companion = new Companion(null);
    private static final int PENCIL_CONTROL_NODE = 38;
    private static final String TAG = "PAINT:CrashHandler";
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private TaskType mTaskType;

    /* compiled from: CrashHandler.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(e eVar) {
            this();
        }
    }

    /* compiled from: CrashHandler.kt */
    public enum TaskType {
        VIBRATION,
        NONE
    }

    /* compiled from: CrashHandler.kt */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TaskType.values().length];
            try {
                iArr[TaskType.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TaskType.VIBRATION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public CrashHandler() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    private final void stopVibration() {
        OplusTouchNodeManager.getInstance().writeNodeFile(38, "2");
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread t, Throwable e) {
        Intrinsics.checkNotNullParameter(t, "t");
        Intrinsics.checkNotNullParameter(e, "e");
        int i = WhenMappings.$EnumSwitchMapping$0[this.mTaskType.ordinal()];
        if (i != 1) {
            if (i != 2) {
                return;
            }
            stopVibration();
        } else {
            Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.mDefaultHandler;
            if (uncaughtExceptionHandler != null) {
                uncaughtExceptionHandler.uncaughtException(t, e);
            }
        }
    }

    public CrashHandler(TaskType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        TaskType taskType = TaskType.VIBRATION;
        this.mTaskType = type;
        Thread.setDefaultUncaughtExceptionHandler(this);
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    public /* synthetic */ CrashHandler(TaskType taskType, int i, e eVar) {
        this((i & 1) != 0 ? TaskType.NONE : taskType);
    }
}
