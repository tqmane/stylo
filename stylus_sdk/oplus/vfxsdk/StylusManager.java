package com.oplusos.vfxsdk.doodleengine.stylus;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import androidx.recyclerview.widget.h;
import com.oplus.coreapp.appfeature.AppFeatureProviderUtils;
import com.oplus.ipemanager.sdk.ISdkAidlInterface;
import com.oplus.ipemanager.sdk.IpeFeature;
import com.oplus.ipemanager.sdk.Vibration;
import com.oplus.ipemanager.sdk.a;
import com.oplus.touchnode.OplusTouchNodeManager;
import com.oplusos.vfxsdk.doodleengine.VibrationType;
import com.oplusos.vfxsdk.doodleengine.stylus.CrashHandler;
import com.oplusos.vfxsdk.doodleengine.toolkit.internal.IPESettingManager;
import defpackage.d;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.e;

/* compiled from: StylusManager.kt */
/* loaded from: classes5.dex */
public final class StylusManager extends Thread {
    public static final String ALLOW_PLAY_SOUND = "allow_play_sound";
    private static final String AUDIO_FILE_HEIGHT = "high.ogg";
    private static final String AUDIO_FILE_LOW = "low.ogg";
    private static final String COMMAND_START_VIBRATION = "3";
    private static final String COMMAND_STOP_VIBRATION = "2";
    public static final String EXTRA_VOLUME_STREAM_TYPE = "android.media.EXTRA_VOLUME_STREAM_TYPE";
    private static final int PENCIL_CONTROL_NODE = 38;
    private static final int PENCIL_STATE_OFF = 0;
    private static final int PENCIL_STATE_ON = 1;
    private static final String SOUND_DIR_PREFIX = "audio";
    public static final String SP_KEY_UNIC_FUNCTION_FEEDBACK_STATE = "ipe_function_feedback_state";
    private static final String TAG = "PAINT:StylusManager";
    public static final String VOLUME_CHANGE_ACTION = "android.media.VOLUME_CHANGED_ACTION";
    private static int mConnectState;
    private static int mDeviceId;
    private static boolean mFunctionVibrationSwitchState;
    private static boolean mIsInitSuccess;
    private static boolean mIsSupportFeedBackVibration;
    private static boolean mIsSupportFunctionVibration;
    private static boolean mVibrationSwitchState;
    private boolean isCurrentPenTypeSupportVibration;
    private boolean mAllowPlay;
    private AudioFocusRequest mAudioFocus;
    private float mAudioFocusVolume;
    private AudioManager mAudioManager;
    private boolean mCancel;
    private Context mContext;
    private CrashHandler mCrashHandler;
    private float mCurrentSystemVolume;
    private boolean mFocus;
    private final AudioManager.OnAudioFocusChangeListener mFocusChangeListener;
    private Handler mHandler;
    private boolean mIsFirst;
    private boolean mIsInitVibrationType;
    private boolean mIsNeedVibrate;
    private boolean mIsPencilPlay;
    private boolean mIsVibrationOn;
    private float mLastVolume;
    private boolean mLoadedReady;
    private float mMaxVolume;
    private float mMoveVolume;
    private boolean mPauseState;
    private boolean mPlayingMove;
    private int mReady;
    private boolean mSetPencilEventDirty;
    private int[] mSoundIds;
    private SoundPool mSoundPool;
    private boolean mSoundSwitchEventDirty;
    private float mStandardVolume;
    private int mStreamId;
    private boolean mSystemVolumeReg;
    private VelocityTracker mVelocityTracker;
    private VibrationType mVibrationType;
    private BroadcastReceiver mVolumeBroadCastReceiver;
    private float[] mVolumes;
    public static final Companion Companion = new Companion(null);
    private static StylusManager$Companion$mCallback$1 mCallback = new a.d() { // from class: com.oplusos.vfxsdk.doodleengine.stylus.StylusManager$Companion$mCallback$1
        @Override // com.oplus.ipemanager.sdk.a.d
        public void onConnectionChanged(int i) {
            boolean vibrationSwitchState = false;
            if (i == 0) {
                StylusManager.mConnectState = 0;
            } else if (i != 2) {
                StylusManager.mConnectState = 0;
                Log.e("PAINT:StylusManager", "Parameter error");
            } else {
                StylusManager.mConnectState = 2;
                com.oplus.ipemanager.sdk.a aVar = com.oplus.ipemanager.sdk.a.h;
                aVar.getClass();
                Log.d("PencilManager", "getVibrationSwitchState");
                ISdkAidlInterface iSdkAidlInterface = aVar.b;
                if (iSdkAidlInterface != null) {
                    try {
                        vibrationSwitchState = iSdkAidlInterface.getVibrationSwitchState();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                StylusManager.mVibrationSwitchState = vibrationSwitchState;
                if (aVar.a().contains(IpeFeature.FEEDBACK_VIBRATION)) {
                    StylusManager.mIsSupportFeedBackVibration = true;
                    Log.d("PAINT:StylusManager", "this device support feedback vibration");
                }
                if (aVar.a().contains(IpeFeature.FUNCTION_VIBRATION)) {
                    StylusManager.mIsSupportFunctionVibration = true;
                    Log.d("PAINT:StylusManager", "this device support function vibration");
                }
            }
            Log.d("PAINT:StylusManager", "Connection state change to:" + StylusManager.mConnectState + ", " + i + " SwitchState: " + StylusManager.mVibrationSwitchState + "  functionSwitch: " + StylusManager.mFunctionVibrationSwitchState + "mIsSupportFeedBackVibration: " + StylusManager.mIsSupportFeedBackVibration + "mIsSupportFunctionVibration: " + StylusManager.mIsSupportFunctionVibration);
        }

        @Override // com.oplus.ipemanager.sdk.a.d
        public void onFunctionFeedbackStateChange(boolean z) {
            StylusManager.mFunctionVibrationSwitchState = z;
            Log.d("PAINT:StylusManager", "Is Function Vibration Switch Open: " + z);
        }

        @Override // com.oplus.ipemanager.sdk.a.d
        public void onInitSuccess() {
            boolean vibrationSwitchState;
            int pencilConnectState;
            StylusManager.mIsInitSuccess = true;
            com.oplus.ipemanager.sdk.a aVar = com.oplus.ipemanager.sdk.a.h;
            aVar.getClass();
            Log.d("PencilManager", "getVibrationSwitchState");
            ISdkAidlInterface iSdkAidlInterface = aVar.b;
            if (iSdkAidlInterface == null) {
                vibrationSwitchState = false;
            } else {
                try {
                    vibrationSwitchState = iSdkAidlInterface.getVibrationSwitchState();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            StylusManager.mVibrationSwitchState = vibrationSwitchState;
            ISdkAidlInterface iSdkAidlInterface2 = aVar.b;
            if (iSdkAidlInterface2 == null) {
                pencilConnectState = 0;
            } else {
                try {
                    pencilConnectState = iSdkAidlInterface2.getPencilConnectState();
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                }
            }
            StylusManager.mConnectState = pencilConnectState;
            List<IpeFeature> listA = com.oplus.ipemanager.sdk.a.h.a();
            if (listA != null) {
                if (listA.contains(IpeFeature.FEEDBACK_VIBRATION)) {
                    StylusManager.mIsSupportFeedBackVibration = true;
                    Log.d("PAINT:StylusManager", "this device support feedback vibration");
                }
                if (listA.contains(IpeFeature.FUNCTION_VIBRATION)) {
                    StylusManager.mIsSupportFunctionVibration = true;
                    Log.d("PAINT:StylusManager", "this device support function vibration");
                }
            } else {
                Log.e("PAINT:StylusManager", "supportFeatures is null");
            }
            StringBuilder sb = new StringBuilder("pencil Manager init success SwitchState: ");
            sb.append(StylusManager.mVibrationSwitchState);
            sb.append("  ConnectState:");
            sb.append(StylusManager.mConnectState == 2);
            sb.append("  SUPPORT FEEDBACK_VIBRATION: ");
            sb.append(StylusManager.mIsSupportFeedBackVibration);
            sb.append("  SUPPORT FUNCTION_VIBRATION: ");
            sb.append(StylusManager.mIsSupportFunctionVibration);
            Log.d("PAINT:StylusManager", sb.toString());
        }

        @Override // com.oplus.ipemanager.sdk.a.d
        public void onReleased(int i) {
            StylusManager.mIsInitSuccess = false;
            Log.d("PAINT:StylusManager", "onReleased");
        }

        @Override // com.oplus.ipemanager.sdk.a.d
        public void onVibrationSwitchStateChange(boolean z) {
            StylusManager.mVibrationSwitchState = z;
            Log.d("PAINT:StylusManager", "Is Vibration Switch Open: " + z);
        }

        @Override // com.oplus.ipemanager.sdk.a.d
        public void onDemoModeEnableChange(boolean z) {
        }
    };

    /* compiled from: StylusManager.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(e eVar) {
            this();
        }
    }

    /* compiled from: StylusManager.kt */
    public enum EventType {
        SetSwitch,
        SetPencil,
        TouchEvent,
        SetVibrationType,
        VibrationControl,
        ResumeVibration,
        FunctionVibration,
        Exit
    }

    /* compiled from: StylusManager.kt */
    public static final class TouchData {
        private int count;
        private boolean isStylus;
        private int type;
        private float velocity;
        private float x;
        private float y;

        public TouchData(int i, int i2, float f, float f2, boolean z, float f3) {
            this.type = i;
            this.count = i2;
            this.x = f;
            this.y = f2;
            this.isStylus = z;
            this.velocity = f3;
        }

        public final int getCount() {
            return this.count;
        }

        public final int getType() {
            return this.type;
        }

        public final float getVelocity() {
            return this.velocity;
        }

        public final float getX() {
            return this.x;
        }

        public final float getY() {
            return this.y;
        }

        public final boolean isStylus() {
            return this.isStylus;
        }

        public final void setCount(int i) {
            this.count = i;
        }

        public final void setStylus(boolean z) {
            this.isStylus = z;
        }

        public final void setType(int i) {
            this.type = i;
        }

        public final void setVelocity(float f) {
            this.velocity = f;
        }

        public final void setX(float f) {
            this.x = f;
        }

        public final void setY(float f) {
            this.y = f;
        }
    }

    /* compiled from: StylusManager.kt */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[VibrationType.values().length];
            try {
                iArr[VibrationType.PENCIL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[VibrationType.ERASE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[VibrationType.PEN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[VibrationType.BALL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public StylusManager(Context context) {
        Context applicationContext = context != null ? context.getApplicationContext() : null;
        this.mContext = applicationContext;
        this.mAllowPlay = true;
        this.mSoundIds = new int[12];
        this.mStreamId = -1;
        this.mIsFirst = true;
        this.mStandardVolume = 0.4f;
        this.mMaxVolume = 16.0f;
        this.mVolumes = new float[17];
        AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() { // from class: com.oplusos.vfxsdk.doodleengine.stylus.b
            @Override // android.media.AudioManager.OnAudioFocusChangeListener
            public final void onAudioFocusChange(int i) {
                StylusManager.mFocusChangeListener$lambda$0(this.f5782a, i);
            }
        };
        this.mFocusChangeListener = onAudioFocusChangeListener;
        Intrinsics.checkNotNull(applicationContext);
        Object systemService = applicationContext.getApplicationContext().getSystemService("audio");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.media.AudioManager");
        AudioManager audioManager = (AudioManager) systemService;
        this.mAudioManager = audioManager;
        Intrinsics.checkNotNull(audioManager != null ? Integer.valueOf(audioManager.getStreamMaxVolume(3)) : null);
        this.mMaxVolume = r0.intValue();
        this.mVelocityTracker = VelocityTracker.obtain();
        IPESettingManager iPESettingManager = IPESettingManager.INSTANCE;
        Context context2 = this.mContext;
        Intrinsics.checkNotNull(context2);
        mDeviceId = iPESettingManager.getCurrentFoldingMode(context2);
        this.mAudioFocus = new AudioFocusRequest.Builder(1).setAudioAttributes(new AudioAttributes.Builder().setUsage(1).setContentType(2).build()).setAcceptsDelayedFocusGain(true).setOnAudioFocusChangeListener(onAudioFocusChangeListener).build();
        requestFocus();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void abandonFocus() {
        AudioManager audioManager;
        d.B(new StringBuilder("abandonFocus:  mFocus:"), this.mFocus, TAG);
        if (this.mFocus) {
            this.mFocus = false;
            AudioFocusRequest audioFocusRequest = this.mAudioFocus;
            if (audioFocusRequest != null && (audioManager = this.mAudioManager) != null) {
                Intrinsics.checkNotNull(audioFocusRequest);
                audioManager.abandonAudioFocusRequest(audioFocusRequest);
            }
            this.mAudioFocusVolume = 0.0f;
        }
    }

    private final void addTouchEvent(TouchData touchData) {
        if ((this.mAllowPlay && this.mIsPencilPlay && touchData.isStylus()) || this.mPlayingMove) {
            int type = touchData.getType();
            if (type == 0) {
                checkSoundAndPencilEventStatus();
                startPlaySound();
                Log.i(TAG, "sound play start");
            } else if (type != 2) {
                pauseSound();
                this.mPlayingMove = false;
                this.mCancel = false;
            } else {
                if (!this.mPlayingMove || this.mCancel) {
                    return;
                }
                this.mMoveVolume = touchData.getVelocity();
                setMoveVolume();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void adjustVolume() {
        AudioManager audioManager = this.mAudioManager;
        Intrinsics.checkNotNull(audioManager != null ? Integer.valueOf(audioManager.getStreamVolume(3)) : null);
        int iIntValue = (int) ((r0.intValue() / this.mMaxVolume) * 16.0f);
        if (iIntValue > 16) {
            iIntValue = 16;
        }
        if (iIntValue < 0) {
            iIntValue = 0;
        }
        this.mCurrentSystemVolume = iIntValue <= 8 ? 1.0f : iIntValue == 9 ? 0.85f : iIntValue == 10 ? 0.7f : iIntValue == 11 ? 0.567f : iIntValue == 12 ? 0.45f : iIntValue == 13 ? 0.356f : iIntValue == 14 ? 0.277f : (iIntValue == 15 || iIntValue == 16) ? 0.28f : 0.15f;
        Log.i(TAG, "mCurrentSystemVolume: " + this.mCurrentSystemVolume);
        resetVolume();
    }

    private final void checkSoundAndPencilEventStatus() {
        Log.d(TAG, "checkSoundAndPencilEventStatus mSetPencilEventDirty: " + this.mSetPencilEventDirty + ", mSoundSwitchEventDirty: " + this.mSoundSwitchEventDirty);
        kotlin.jvm.functions.a<Unit> aVar = new kotlin.jvm.functions.a<Unit>() { // from class: com.oplusos.vfxsdk.doodleengine.stylus.StylusManager$checkSoundAndPencilEventStatus$fp$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.a
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                if (this.this$0.mIsPencilPlay && this.this$0.mAllowPlay) {
                    this.this$0.requestFocus();
                    this.this$0.initSoundSource();
                } else {
                    this.this$0.abandonFocus();
                    this.this$0.stopPlaySound();
                }
                this.this$0.soundSwitchStatue();
                this.this$0.adjustVolume();
            }
        };
        if (this.mSetPencilEventDirty) {
            aVar.invoke();
            this.mSetPencilEventDirty = false;
        }
        if (this.mSoundSwitchEventDirty) {
            aVar.invoke();
            this.mSoundSwitchEventDirty = false;
        }
    }

    private final void destroyPlaySound() {
        stopPlaySound();
        SoundPool soundPool = this.mSoundPool;
        if (soundPool == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mSoundPool");
            soundPool = null;
        }
        soundPool.release();
        Log.d(TAG, "SoundPool release");
    }

    private final float getVolumeWithVelocity(float f, float f2, float f3, float f4, float f5) {
        return (((com.heytap.common.util.c.i(f, f2, f3) - f2) / (f3 - f2)) * (f5 - f4)) + f4;
    }

    private final void initPencilManager(Context context) {
        if (mIsInitSuccess) {
            return;
        }
        try {
            com.oplus.ipemanager.sdk.a aVar = com.oplus.ipemanager.sdk.a.h;
            StylusManager$Companion$mCallback$1 stylusManager$Companion$mCallback$1 = mCallback;
            aVar.getClass();
            if (context != null && stylusManager$Companion$mCallback$1 != null) {
                Log.d("PencilManager", "init");
                aVar.d = context;
                aVar.f3914a = stylusManager$Companion$mCallback$1;
                Intent intent = new Intent("com.oplus.ipemanager.ACTION.PENCIL_SDK");
                intent.setPackage("com.oplus.ipemanager");
                context.bindService(intent, aVar.f, 1);
            }
        } catch (SecurityException e) {
            Log.e(TAG, "init pencilManager failed:" + e.getMessage());
        }
        String strA = AppFeatureProviderUtils.a(context.getContentResolver(), "com.oplus.ipemanager.support_devices", "String");
        if (strA == null) {
            strA = "";
        }
        Log.d(TAG, "initPencilManager: result = ".concat(strA));
        if (strA.equals("OPPO Pen")) {
            mIsSupportFeedBackVibration = true;
            Log.d(TAG, "support feedback vibration on fold device");
        }
    }

    @SuppressLint({"SuspiciousIndentation"})
    private final void initRes() throws IOException {
        initSourceSrc();
        for (int i = 0; i < 17; i++) {
            AudioManager audioManager = this.mAudioManager;
            Float fValueOf = audioManager != null ? Float.valueOf(audioManager.getStreamVolumeDb(3, i, 2)) : null;
            if (fValueOf != null) {
                fValueOf = Float.valueOf((float) Math.pow(10.0f, Float.valueOf(fValueOf.floatValue() / 20.0f).floatValue()));
            }
            float[] fArr = this.mVolumes;
            Intrinsics.checkNotNull(fValueOf);
            fArr[i] = fValueOf.floatValue();
        }
        this.mStandardVolume = this.mVolumes[6];
        adjustVolume();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initSoundSource() {
        StringBuilder sb = new StringBuilder("initSoundSource: ");
        sb.append(this.mStreamId);
        sb.append(", ");
        sb.append(this.mPlayingMove);
        sb.append(", ");
        d.B(sb, this.mLoadedReady, TAG);
        if (this.mLoadedReady) {
            if (this.mStreamId == -1) {
                SoundPool soundPool = this.mSoundPool;
                if (soundPool == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mSoundPool");
                    soundPool = null;
                }
                this.mStreamId = soundPool.play(this.mSoundIds[0], 0.0f, 0.0f, 1, -1, 1.0f);
            }
            if (this.mPlayingMove) {
                return;
            }
            pauseSound();
        }
    }

    private final void initSourceSrc() throws IOException {
        final String[] strArr;
        SoundPool soundPoolBuild = new SoundPool.Builder().setMaxStreams(2).setAudioAttributes(new AudioAttributes.Builder().setUsage(1).setContentType(2).build()).build();
        Intrinsics.checkNotNullExpressionValue(soundPoolBuild, "Builder()\n            .s…Set)\n            .build()");
        this.mSoundPool = soundPoolBuild;
        String str = Build.MODEL;
        if (Intrinsics.areEqual(str, "PHN110") || Intrinsics.areEqual(str, "CPH2499") || Intrinsics.areEqual(str, "CPH2551")) {
            Log.d(TAG, "use low sound file");
            strArr = new String[]{AUDIO_FILE_LOW};
        } else {
            Log.d(TAG, "use height sound file");
            strArr = new String[]{AUDIO_FILE_HEIGHT};
        }
        try {
            SoundPool soundPool = this.mSoundPool;
            if (soundPool == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mSoundPool");
                soundPool = null;
            }
            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() { // from class: com.oplusos.vfxsdk.doodleengine.stylus.c
                @Override // android.media.SoundPool.OnLoadCompleteListener
                public final void onLoadComplete(SoundPool soundPool2, int i, int i2) {
                    StylusManager.initSourceSrc$lambda$2(this.f5783a, strArr, soundPool2, i, i2);
                }
            });
            if (this.mContext != null) {
                int length = strArr.length;
                for (int i = 0; i < length; i++) {
                    String str2 = strArr[i];
                    Context context = this.mContext;
                    Intrinsics.checkNotNull(context);
                    AssetFileDescriptor assetFileDescriptorOpenFd = context.getAssets().openFd("audio" + File.separatorChar + str2);
                    Intrinsics.checkNotNullExpressionValue(assetFileDescriptorOpenFd, "mContext!!.assets.openFd…paratorChar}${fileName}\")");
                    int[] iArr = this.mSoundIds;
                    SoundPool soundPool2 = this.mSoundPool;
                    if (soundPool2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mSoundPool");
                        soundPool2 = null;
                    }
                    iArr[i] = soundPool2.load(assetFileDescriptorOpenFd, 1);
                }
            }
        } catch (FileNotFoundException unused) {
            Log.e(TAG, "load sound file failed");
        } catch (IOException unused2) {
            Log.e(TAG, "IOException");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initSourceSrc$lambda$2(StylusManager this$0, String[] sources, SoundPool soundPool, int i, int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(sources, "$sources");
        this$0.mReady++;
        androidx.appsearch.app.e.y(new StringBuilder("Load Completed: "), this$0.mReady, TAG);
        if (this$0.mReady == sources.length) {
            this$0.mLoadedReady = true;
            if (this$0.mIsPencilPlay && this$0.mAllowPlay) {
                this$0.initSoundSource();
            }
        }
    }

    private final void initVibrationType(VibrationType vibrationType) {
        if (!mIsSupportFeedBackVibration) {
            Log.d(TAG, "not support feedBack vibration");
            return;
        }
        Context context = this.mContext;
        if (context != null) {
            IPESettingManager iPESettingManager = IPESettingManager.INSTANCE;
            Intrinsics.checkNotNull(context);
            mDeviceId = iPESettingManager.getCurrentFoldingMode(context);
        }
        this.isCurrentPenTypeSupportVibration = true;
        int i = WhenMappings.$EnumSwitchMapping$0[vibrationType.ordinal()];
        if (i == 1) {
            com.oplus.ipemanager.sdk.a.h.b(Vibration.f3913a);
            Log.d(TAG, "set Vibration Type: PENCIL");
            return;
        }
        if (i == 2) {
            com.oplus.ipemanager.sdk.a.h.b(Vibration.b);
            Log.d(TAG, "set Vibration Type: ERASER");
            return;
        }
        if (i == 3) {
            com.oplus.ipemanager.sdk.a.h.b(Vibration.d);
            Log.d(TAG, "set Vibration Type: PEN");
        } else if (i == 4) {
            com.oplus.ipemanager.sdk.a.h.b(Vibration.c);
            Log.d(TAG, "set Vibration Type: BALL PEN");
        } else {
            this.isCurrentPenTypeSupportVibration = false;
            stopVibration();
            Log.d(TAG, "set Vibration Type: NONE");
        }
    }

    private final boolean isEnableVibrate() {
        return mIsSupportFeedBackVibration && mVibrationSwitchState && mConnectState == 2;
    }

    private final boolean isNeedVibration() {
        if (this.mIsNeedVibrate) {
            return true;
        }
        if (this.mIsInitVibrationType || !mIsSupportFeedBackVibration) {
            return false;
        }
        Message message = new Message();
        message.what = EventType.SetVibrationType.ordinal();
        message.obj = this.mVibrationType;
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.sendMessage(message);
        }
        this.mIsInitVibrationType = true;
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mFocusChangeListener$lambda$0(StylusManager this$0, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Log.d(TAG, "focusChange: " + i);
        float f = 1.0f;
        if (i != 1 && (!this$0.mAllowPlay || !this$0.mIsPencilPlay)) {
            f = 0.0f;
        }
        this$0.mAudioFocusVolume = f;
    }

    private final void pauseSound() {
        if (this.mStreamId < 0) {
            Log.e(TAG, "mStreamId: " + this.mStreamId);
        } else {
            SoundPool soundPool = this.mSoundPool;
            if (soundPool == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mSoundPool");
                soundPool = null;
            }
            soundPool.pause(this.mStreamId);
            Log.d(TAG, "pause play Sound");
        }
    }

    private final void registerVolumeChangeListener() {
        Context context;
        if (this.mVolumeBroadCastReceiver == null) {
            this.mVolumeBroadCastReceiver = new BroadcastReceiver() { // from class: com.oplusos.vfxsdk.doodleengine.stylus.StylusManager.registerVolumeChangeListener.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context2, Intent intent) {
                    if (Intrinsics.areEqual(intent != null ? intent.getAction() : null, StylusManager.VOLUME_CHANGE_ACTION) && intent.getIntExtra(StylusManager.EXTRA_VOLUME_STREAM_TYPE, -1) == 3) {
                        StylusManager.this.adjustVolume();
                    }
                }
            };
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(VOLUME_CHANGE_ACTION);
        if (this.mVolumeBroadCastReceiver != null && (context = this.mContext) != null) {
            if (Build.VERSION.SDK_INT > 33) {
                Intrinsics.checkNotNull(context);
                BroadcastReceiver broadcastReceiver = this.mVolumeBroadCastReceiver;
                Intrinsics.checkNotNull(broadcastReceiver);
                context.registerReceiver(broadcastReceiver, intentFilter, 4);
            } else {
                Intrinsics.checkNotNull(context);
                BroadcastReceiver broadcastReceiver2 = this.mVolumeBroadCastReceiver;
                Intrinsics.checkNotNull(broadcastReceiver2);
                context.registerReceiver(broadcastReceiver2, intentFilter);
            }
            this.mSystemVolumeReg = true;
        }
        Log.d(TAG, "registerVolumeChangeListener");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void requestFocus() {
        Integer numValueOf;
        if (this.mAllowPlay && this.mIsPencilPlay) {
            float f = 0.0f;
            if (!this.mFocus || this.mAudioFocusVolume <= 0.0f) {
                this.mFocus = true;
                AudioManager audioManager = this.mAudioManager;
                if (audioManager != null) {
                    AudioFocusRequest audioFocusRequest = this.mAudioFocus;
                    Intrinsics.checkNotNull(audioFocusRequest);
                    numValueOf = Integer.valueOf(audioManager.requestAudioFocus(audioFocusRequest));
                } else {
                    numValueOf = null;
                }
                if (numValueOf == null || 1 != numValueOf.intValue()) {
                    Log.d(TAG, "audio request failed");
                    if (this.mAllowPlay && this.mIsPencilPlay) {
                    }
                    this.mAudioFocusVolume = f;
                }
                Log.d(TAG, "audio request granted");
                f = 1.0f;
                this.mAudioFocusVolume = f;
            }
        }
    }

    private final void resetVolume() {
        if (this.mStreamId > 0) {
            SoundPool soundPool = this.mSoundPool;
            if (soundPool == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mSoundPool");
                soundPool = null;
            }
            soundPool.setVolume(this.mStreamId, 0.0f, 0.0f);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean run$lambda$7(StylusManager this$0, Message it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        int i = it.what;
        if (i == EventType.SetSwitch.ordinal()) {
            this$0.soundSwitch(it.arg1 == 1);
        } else if (i == EventType.SetPencil.ordinal()) {
            this$0.setPencil(it.arg1 == 1);
        } else if (i == EventType.TouchEvent.ordinal()) {
            Object obj = it.obj;
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type com.oplusos.vfxsdk.doodleengine.stylus.StylusManager.TouchData");
            this$0.addTouchEvent((TouchData) obj);
        } else if (i == EventType.SetVibrationType.ordinal()) {
            Object obj2 = it.obj;
            if (obj2 != null) {
                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type com.oplusos.vfxsdk.doodleengine.VibrationType");
                this$0.initVibrationType((VibrationType) obj2);
            }
        } else if (i == EventType.VibrationControl.ordinal()) {
            Object obj3 = it.obj;
            Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type android.view.MotionEvent");
            this$0.vibrationControl((MotionEvent) obj3);
        } else if (i == EventType.ResumeVibration.ordinal()) {
            this$0.startVibration();
        } else {
            if (i != EventType.FunctionVibration.ordinal()) {
                this$0.destroyPlaySound();
                Looper looperMyLooper = Looper.myLooper();
                Intrinsics.checkNotNull(looperMyLooper);
                looperMyLooper.quitSafely();
                return true;
            }
            this$0.vibrationOnce();
        }
        return false;
    }

    private final void setMoveVolume() {
        float f = this.mCurrentSystemVolume * this.mAudioFocusVolume * this.mMoveVolume;
        SoundPool soundPool = this.mSoundPool;
        if (soundPool == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mSoundPool");
            soundPool = null;
        }
        soundPool.setVolume(this.mStreamId, f, f);
        this.mLastVolume = f;
    }

    private final void setPencil(boolean z) {
        this.mIsPencilPlay = z;
        if (z && this.mAllowPlay) {
            requestFocus();
            initSoundSource();
        } else {
            abandonFocus();
            stopPlaySound();
        }
        soundSwitchStatue();
        adjustVolume();
        StringBuilder sb = new StringBuilder("setPencil mIsPencilPlay: ");
        sb.append(this.mIsPencilPlay);
        sb.append(", mAllowPlay: ");
        d.B(sb, this.mAllowPlay, TAG);
    }

    private final void soundSwitch(boolean z) {
        this.mAllowPlay = z;
        if (z && this.mIsPencilPlay) {
            requestFocus();
            initSoundSource();
        } else {
            abandonFocus();
            stopPlaySound();
        }
        soundSwitchStatue();
        adjustVolume();
        d.B(new StringBuilder("soundSwitch mAllowPlay: "), this.mAllowPlay, TAG);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void soundSwitchStatue() {
        synchronized (this) {
            try {
                if (this.mAllowPlay && this.mIsPencilPlay) {
                    registerVolumeChangeListener();
                } else {
                    unregisterVolumeChangeListener();
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void stopPlaySound() {
        if (this.mStreamId > 0) {
            SoundPool soundPool = this.mSoundPool;
            if (soundPool == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mSoundPool");
                soundPool = null;
            }
            soundPool.stop(this.mStreamId);
            this.mStreamId = -1;
            Log.d(TAG, "stop play Sound");
        }
    }

    private final void unregisterVolumeChangeListener() {
        Context context;
        if (this.mVolumeBroadCastReceiver != null && this.mSystemVolumeReg && (context = this.mContext) != null) {
            Intrinsics.checkNotNull(context);
            BroadcastReceiver broadcastReceiver = this.mVolumeBroadCastReceiver;
            Intrinsics.checkNotNull(broadcastReceiver);
            context.unregisterReceiver(broadcastReceiver);
            this.mSystemVolumeReg = false;
        }
        Log.d(TAG, "unregisterVolumeChangeListener");
    }

    private final void updateVibrationStatus() {
        if (mIsSupportFeedBackVibration) {
            Message message = new Message();
            message.what = EventType.SetVibrationType.ordinal();
            message.obj = this.mVibrationType;
            Handler handler = this.mHandler;
            if (handler != null) {
                handler.sendMessage(message);
            }
            this.mIsInitVibrationType = true;
        }
    }

    private final void vibrationControl(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            startVibration();
            return;
        }
        if (action == 1) {
            stopVibration();
        } else if (action != 2) {
            stopVibration();
        } else {
            if (this.mIsVibrationOn) {
                return;
            }
            startVibration();
        }
    }

    private final void vibrationOnce() {
        Context context = this.mContext;
        boolean z = Settings.Global.getInt(context != null ? context.getContentResolver() : null, SP_KEY_UNIC_FUNCTION_FEEDBACK_STATE, 0) == 1;
        mFunctionVibrationSwitchState = z;
        if (z) {
            com.oplus.ipemanager.sdk.a aVar = com.oplus.ipemanager.sdk.a.h;
            Vibration vibration = Vibration.e;
            if (aVar.b == null) {
                Log.e("PencilManager", "mInterface is null!");
            } else {
                Log.d("PencilManager", "startVibration " + vibration);
                try {
                    aVar.b.startVibration(4);
                } catch (RemoteException e) {
                    Log.e("PencilManager", "startVibration, error = " + e);
                }
            }
        }
        Log.d(TAG, "vibration once: ".concat(mFunctionVibrationSwitchState ? "success" : "failed"));
    }

    public final void addTouchEventEvent(MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getAction() != 2 && event.getToolType(0) == 2) {
            if (isEnableVibrate() && isNeedVibration() && !this.mPauseState) {
                Message message = new Message();
                message.what = EventType.VibrationControl.ordinal();
                message.obj = event;
                Handler handler = this.mHandler;
                if (handler != null) {
                    handler.sendMessage(message);
                }
            } else if (!isEnableVibrate()) {
                StringBuilder sb = new StringBuilder("vibration not enable, case: support:");
                sb.append(mIsSupportFeedBackVibration);
                sb.append("  switch:");
                sb.append(mVibrationSwitchState);
                sb.append("  connect state:");
                androidx.appsearch.app.e.y(sb, mConnectState, TAG);
            } else if (isNeedVibration()) {
                d.B(new StringBuilder("pause state:"), this.mPauseState, TAG);
            } else {
                StringBuilder sb2 = new StringBuilder("not need vibration, case: need:");
                sb2.append(this.mIsNeedVibrate);
                sb2.append("  initType:");
                d.B(sb2, this.mIsInitVibrationType, TAG);
            }
        }
        if ((this.mAllowPlay && this.mIsPencilPlay && event.getToolType(0) == 2) || this.mPlayingMove) {
            if (event.getAction() == 0) {
                VelocityTracker velocityTracker = this.mVelocityTracker;
                if (velocityTracker != null) {
                    velocityTracker.clear();
                }
                this.mIsFirst = true;
            }
            VelocityTracker velocityTracker2 = this.mVelocityTracker;
            if (velocityTracker2 != null) {
                velocityTracker2.addMovement(event);
            }
            VelocityTracker velocityTracker3 = this.mVelocityTracker;
            if (velocityTracker3 != null) {
                velocityTracker3.computeCurrentVelocity(10);
            }
            VelocityTracker velocityTracker4 = this.mVelocityTracker;
            Intrinsics.checkNotNull(velocityTracker4);
            float xVelocity = velocityTracker4.getXVelocity();
            VelocityTracker velocityTracker5 = this.mVelocityTracker;
            Intrinsics.checkNotNull(velocityTracker5);
            float yVelocity = velocityTracker5.getYVelocity();
            float volumeWithVelocity = getVolumeWithVelocity((float) Math.pow((yVelocity * yVelocity) + (xVelocity * xVelocity), 0.5d), 0.0f, 50.0f, 0.0f, 1.0f);
            int action = event.getAction();
            if (action == 0) {
                checkSoundAndPencilEventStatus();
            } else if (action != 2) {
                pauseSound();
                this.mPlayingMove = false;
                this.mCancel = false;
            }
            TouchData touchData = new TouchData(event.getAction(), event.getPointerCount(), event.getRawX(), event.getRawY(), event.getToolType(0) == 2, volumeWithVelocity);
            Message message2 = new Message();
            message2.what = EventType.TouchEvent.ordinal();
            message2.obj = touchData;
            Handler handler2 = this.mHandler;
            if (handler2 != null) {
                handler2.sendMessage(message2);
            }
        }
    }

    public final void cancel() {
        if (this.mIsPencilPlay) {
            this.mCancel = true;
        }
    }

    public final void destroyEvent() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.clear();
        }
        VelocityTracker velocityTracker2 = this.mVelocityTracker;
        if (velocityTracker2 != null) {
            velocityTracker2.recycle();
        }
        this.mVelocityTracker = null;
        abandonFocus();
        Message message = new Message();
        message.what = EventType.Exit.ordinal();
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.sendMessage(message);
        }
        this.mContext = null;
        Log.d(TAG, "stylusManager destroyed");
    }

    public final void enableVibration(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Log.d(TAG, "enableVibration");
        Context applicationContext = ctx.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "ctx.applicationContext");
        initPencilManager(applicationContext);
        this.mCrashHandler = new CrashHandler(CrashHandler.TaskType.VIBRATION);
    }

    public final boolean isCurrentPenTypeSupportVibration() {
        return this.isCurrentPenTypeSupportVibration;
    }

    public final boolean isInPause() {
        return this.mPauseState;
    }

    public final void pauseVibration(boolean z) {
        this.mPauseState = z;
        if (!z && isNeedVibration() && isEnableVibrate()) {
            Log.d(TAG, "Resume Vibration");
            Message message = new Message();
            message.what = EventType.ResumeVibration.ordinal();
            Handler handler = this.mHandler;
            if (handler != null) {
                handler.sendMessage(message);
            }
        }
    }

    public final void releasePencilManager() {
        if (!mIsInitSuccess) {
            Log.e(TAG, "releasePencilManager() can not reach before init");
            return;
        }
        com.oplus.ipemanager.sdk.a aVar = com.oplus.ipemanager.sdk.a.h;
        aVar.getClass();
        Log.d("PencilManager", "release ");
        if (aVar.d != null) {
            ISdkAidlInterface iSdkAidlInterface = aVar.b;
            if (iSdkAidlInterface != null) {
                try {
                    iSdkAidlInterface.unsetISdkAidlCallback(aVar.g);
                    aVar.b.asBinder().unlinkToDeath(aVar.c, 0);
                } catch (RemoteException e) {
                    Log.e("PencilManager", "release, error = " + e);
                }
            }
            if (aVar.e) {
                aVar.d.unbindService(aVar.f);
                aVar.e = false;
                aVar.b = null;
            }
            a.d dVar = aVar.f3914a;
            if (dVar != null) {
                dVar.onReleased(0);
            }
            aVar.d = null;
        }
        Log.d(TAG, "releasePencilManager Success");
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() throws IOException {
        Looper.prepare();
        initRes();
        Looper looperMyLooper = Looper.myLooper();
        Intrinsics.checkNotNull(looperMyLooper);
        this.mHandler = new Handler(looperMyLooper, new Handler.Callback() { // from class: com.oplusos.vfxsdk.doodleengine.stylus.a
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                return StylusManager.run$lambda$7(this.b, message);
            }
        });
        Looper.loop();
    }

    public final void setPencilEvent(boolean z, float f) {
        if (this.mPauseState == z) {
            this.mPauseState = !z;
        }
        Message message = new Message();
        message.what = EventType.SetPencil.ordinal();
        message.arg1 = z ? 1 : 0;
        message.obj = Float.valueOf(f);
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.sendMessage(message);
        }
        if (this.mHandler == null) {
            synchronized (this) {
                this.mIsPencilPlay = z;
                this.mSetPencilEventDirty = true;
                Unit unit = Unit.INSTANCE;
            }
        }
        Log.i(TAG, "setPencilEvent: " + z + ' ' + f);
    }

    public final void setVibrationType(VibrationType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        Log.d(TAG, "setVibrationType: " + type);
        this.mVibrationType = type;
        this.mIsNeedVibrate = type != VibrationType.NONE;
        if (!mIsSupportFeedBackVibration) {
            Log.e(TAG, "vibration is init failed");
            this.mIsInitVibrationType = false;
            return;
        }
        Message message = new Message();
        message.what = EventType.SetVibrationType.ordinal();
        message.obj = type;
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.sendMessage(message);
        }
        this.mIsInitVibrationType = true;
    }

    public final void soundSwitchEvent$paint_intermediate_release(boolean z) {
        Message message = new Message();
        message.what = EventType.SetSwitch.ordinal();
        message.arg1 = z ? 1 : 0;
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.sendMessage(message);
        }
        if (this.mHandler == null) {
            synchronized (this) {
                this.mAllowPlay = z;
                this.mSoundSwitchEventDirty = true;
                Unit unit = Unit.INSTANCE;
            }
        }
        if (!z) {
            this.mPlayingMove = false;
        }
        StringBuilder sbY = h.y("soundSwitchEvent: ", z, ", mHandler: ");
        sbY.append(this.mHandler);
        Log.i(TAG, sbY.toString());
    }

    public final void startFunctionVibration() {
        if (mIsSupportFunctionVibration) {
            Message message = new Message();
            message.what = EventType.FunctionVibration.ordinal();
            Handler handler = this.mHandler;
            if (handler != null) {
                handler.sendMessage(message);
            }
            Log.d(TAG, "startFunctionVibration");
        }
    }

    public final void startPlaySound() {
        resetVolume();
        SoundPool soundPool = this.mSoundPool;
        if (soundPool == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mSoundPool");
            soundPool = null;
        }
        soundPool.resume(this.mStreamId);
        this.mMoveVolume = 0.0f;
        this.mPlayingMove = true;
    }

    public final void startVibration() {
        if (this.mIsVibrationOn) {
            Log.d(TAG, "vibration is already start");
            return;
        }
        Log.d(TAG, "startVibration");
        OplusTouchNodeManager.getInstance().writeNodeFileByDevice(mDeviceId, 38, "3");
        this.mIsVibrationOn = true;
    }

    public final void stopVibration() {
        if (!this.mIsVibrationOn) {
            Log.d(TAG, "vibration is never start");
            return;
        }
        Log.d(TAG, "stopVibration");
        OplusTouchNodeManager.getInstance().writeNodeFileByDevice(mDeviceId, 38, "2");
        this.mIsVibrationOn = false;
    }

    public final void windowFocusChange(boolean z) {
        com.coui.appcompat.card.c.A("windowFocusChange: ", z, TAG);
        if (!z) {
            abandonFocus();
            return;
        }
        requestFocus();
        adjustVolume();
        updateVibrationStatus();
    }
}
