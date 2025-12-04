package com.vivo.penengine.impl;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.provider.Settings;
import com.vivo.bluetoothpen.IBluetoothPenCallback;
import com.vivo.bluetoothpen.OnPenStateListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public final class VivoStylusGestureManagerImpl {
    public static final int FILM_STATE_DOUBLE_CLICK = 3;
    public static final String STYLUS_HAPTIC_FEEDBACK_GESTURE_SWITCH = "vivo_stylus_haptic_feedback_gesture_switch";
    public static final int STYLUS_HAPTIC_FEEDBACK_GESTURE_SWITCH_OFF = 0;
    public static final int STYLUS_HAPTIC_FEEDBACK_GESTURE_SWITCH_ON = 1;
    public static final int STYLUS_PRIMARY_BUTTON_CLICK = 33;
    public static final int STYLUS_SECONDLY_BUTTON_CLICK = 34;
    public static final int STYLUS_WRITING_VIBRATE_INTENSITY_NORMAL = 128;
    public static final int STYLUS_WRITING_VIBRATE_INTENSITY_STRONG = 160;
    public static final int STYLUS_WRITING_VIBRATE_INTENSITY_WEAK = 96;
    public static final String STYLUS_WRITING_VIBRATE_STRENGTH_LEVEL = "vivo_stylus_haptic_feedback_write_select_level";
    public static final int STYLUS_WRITING_VIBRATE_STRENGTH_LEVEL_NORMAL = 2;
    public static final int STYLUS_WRITING_VIBRATE_STRENGTH_LEVEL_OFF = 0;
    public static final int STYLUS_WRITING_VIBRATE_STRENGTH_LEVEL_STRONG = 3;
    public static final int STYLUS_WRITING_VIBRATE_STRENGTH_LEVEL_WEAK = 1;
    public static final String STYLUS_WRITING_VIBRATE_SWITCH = "vivo_stylus_haptic_feedback_write_switch";
    public static final int STYLUS_WRITING_VIBRATE_SWITCH_OFF = 0;
    public static final int STYLUS_WRITING_VIBRATE_SWITCH_ON = 1;
    public static final String TAG = "VivoStylusGestureManagerImpl";
    public static volatile VivoStylusGestureManagerImpl instance;
    public final Context mContext;
    public boolean mHasBond;
    public IBluetoothPenCallback mIBluetoothPenCallback;
    public final List<OnGestureCallback> mGestureCallbackList = new ArrayList();
    public final Handler mMainHandler = new Handler(Looper.getMainLooper());
    public final ServiceConnection mServiceConnection = new ServiceConnection() { // from class: com.vivo.penengine.impl.VivoStylusGestureManagerImpl.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            VivoStylusGestureManagerImpl.this.mHasBond = true;
            try {
                VivoStylusGestureManagerImpl.this.mIBluetoothPenCallback = IBluetoothPenCallback.Stub.asInterface(iBinder);
                VivoStylusGestureManagerImpl.this.mIBluetoothPenCallback.registerPenStateListener(VivoStylusGestureManagerImpl.this.mPenStateListener);
                EngineLog.d(VivoStylusGestureManagerImpl.TAG, "onServiceConnected success");
            } catch (Exception e) {
                EngineLog.e(VivoStylusGestureManagerImpl.TAG, "onServiceConnected error", e);
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            VivoStylusGestureManagerImpl.this.mIBluetoothPenCallback = null;
            VivoStylusGestureManagerImpl.this.mHasBond = false;
            EngineLog.d(VivoStylusGestureManagerImpl.TAG, "onServiceDisconnected");
            VivoStylusGestureManagerImpl.this.bindServiceIfNeed();
        }
    };
    public final OnPenStateListener mPenStateListener = new AnonymousClass2();
    public final Application.ActivityLifecycleCallbacks mActivityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() { // from class: com.vivo.penengine.impl.VivoStylusGestureManagerImpl.3
        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
            EngineLog.d(VivoStylusGestureManagerImpl.TAG, "pause activity->" + activity);
            try {
                if (VivoStylusGestureManagerImpl.this.mIBluetoothPenCallback != null) {
                    VivoStylusGestureManagerImpl.this.mIBluetoothPenCallback.unregisterPenStateListener(VivoStylusGestureManagerImpl.this.mPenStateListener);
                }
            } catch (Exception e) {
                EngineLog.e(VivoStylusGestureManagerImpl.TAG, "unregisterPenStateListener error", e);
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
            EngineLog.d(VivoStylusGestureManagerImpl.TAG, "resume activity->" + activity);
            try {
                if (VivoStylusGestureManagerImpl.this.mIBluetoothPenCallback != null) {
                    VivoStylusGestureManagerImpl.this.mIBluetoothPenCallback.registerPenStateListener(VivoStylusGestureManagerImpl.this.mPenStateListener);
                }
            } catch (Exception e) {
                EngineLog.e(VivoStylusGestureManagerImpl.TAG, "registerPenStateListener error", e);
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
        }
    };
    public boolean mNeedRebind = true;

    /* renamed from: com.vivo.penengine.impl.VivoStylusGestureManagerImpl$2, reason: invalid class name */
    public class AnonymousClass2 extends OnPenStateListener.Stub {
        public AnonymousClass2() {
        }

        public /* synthetic */ void a() {
            VivoStylusGestureManagerImpl.this.processDoubleClick();
        }

        public /* synthetic */ void b() {
            VivoStylusGestureManagerImpl.this.processClick(true);
        }

        public /* synthetic */ void c() {
            VivoStylusGestureManagerImpl.this.processClick(false);
        }

        @Override // com.vivo.bluetoothpen.OnPenStateListener
        public void onFilmStateChange(int i) {
            EngineLog.d(VivoStylusGestureManagerImpl.TAG, "onFilmStateChange: state=" + i + ", listener size=" + VivoStylusGestureManagerImpl.this.mGestureCallbackList.size());
            if (VivoStylusGestureManagerImpl.this.mGestureCallbackList.size() == 0) {
                return;
            }
            if (i == 3) {
                VivoStylusGestureManagerImpl.this.mMainHandler.post(new Runnable() { // from class: com.vivo.penengine.impl.b
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.n.a();
                    }
                });
            } else if (i == 33) {
                VivoStylusGestureManagerImpl.this.mMainHandler.post(new Runnable() { // from class: com.vivo.penengine.impl.c
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.n.b();
                    }
                });
            } else if (i == 34) {
                VivoStylusGestureManagerImpl.this.mMainHandler.post(new Runnable() { // from class: com.vivo.penengine.impl.a
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.n.c();
                    }
                });
            }
        }

        @Override // com.vivo.bluetoothpen.OnPenStateListener
        public void onIMUCoordinateChange(int i, int i2) {
        }

        @Override // com.vivo.bluetoothpen.OnPenStateListener
        public void onIMUModeChange(int i) {
        }

        @Override // com.vivo.bluetoothpen.OnPenStateListener
        public void onMotorModeChange(int i) {
        }

        @Override // com.vivo.bluetoothpen.OnPenStateListener
        public void onPressureLevelChange(int i) {
        }

        @Override // com.vivo.bluetoothpen.OnPenStateListener
        public void onPressureStateChange(int i) {
        }

        @Override // com.vivo.bluetoothpen.OnPenStateListener
        public void onShakeStateChange(int i) {
        }

        @Override // com.vivo.bluetoothpen.OnPenStateListener
        public void onSoftwareVersionChange(String str) {
        }

        @Override // com.vivo.bluetoothpen.OnPenStateListener
        public void onWorkModeChange(int i) {
        }
    }

    public interface OnGestureCallback {
        boolean onGesture(int i);
    }

    public VivoStylusGestureManagerImpl(Context context) {
        this.mContext = context.getApplicationContext();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindServiceIfNeed() {
        EngineLog.d(TAG, "bindServiceIfNeed: mNeedRebind=" + this.mNeedRebind + ", mHasBond=" + this.mHasBond);
        if (!this.mNeedRebind || this.mHasBond) {
            return;
        }
        EngineLog.d(TAG, "bindServiceIfNeed");
        try {
            Intent intent = new Intent();
            intent.setPackage("com.vivo.penwrite");
            intent.setAction("com.vivo.bluetoothpen.service.BluetoothPenService");
            this.mContext.bindService(intent, this.mServiceConnection, 1);
        } catch (Exception e) {
            EngineLog.e(TAG, "bindServiceIfNeed error", e);
        }
    }

    public static VivoStylusGestureManagerImpl getInstance(Context context) {
        if (instance == null) {
            synchronized (VivoAlgorithmManagerImpl.class) {
                if (instance == null) {
                    instance = new VivoStylusGestureManagerImpl(context);
                }
            }
        }
        return instance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processClick(boolean z) {
        if (this.mIBluetoothPenCallback == null) {
            return;
        }
        Iterator<OnGestureCallback> it = this.mGestureCallbackList.iterator();
        while (it.hasNext()) {
            EngineLog.d(TAG, "processClick: " + it.next().onGesture(z ? 3 : 4));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processDoubleClick() {
        if (this.mIBluetoothPenCallback == null) {
            return;
        }
        boolean z = false;
        Iterator<OnGestureCallback> it = this.mGestureCallbackList.iterator();
        while (it.hasNext()) {
            if (it.next().onGesture(2) && !z) {
                z = true;
            }
        }
        EngineLog.d(TAG, "processDoubleClick: need vibrate=" + z);
        if (z) {
            vibrate();
        }
    }

    private void unbindServiceIfNeed() {
        if (this.mGestureCallbackList.size() == 0) {
            EngineLog.d(TAG, "unbindServiceIfNeed");
            try {
                if (this.mIBluetoothPenCallback != null) {
                    this.mIBluetoothPenCallback.unregisterPenStateListener(this.mPenStateListener);
                }
            } catch (Exception e) {
                EngineLog.e(TAG, "unregisterPenStateListener error", e);
            }
            try {
                if (this.mHasBond) {
                    this.mContext.unbindService(this.mServiceConnection);
                }
                this.mIBluetoothPenCallback = null;
            } catch (Exception e2) {
                EngineLog.e(TAG, "unbindServiceIfNeed error", e2);
            }
            this.mHasBond = false;
            this.mNeedRebind = false;
        }
    }

    private void vibrate() {
        if (this.mIBluetoothPenCallback == null) {
            EngineLog.d(TAG, "vibrate: callback is null");
            return;
        }
        if (Settings.Global.getInt(this.mContext.getContentResolver(), STYLUS_HAPTIC_FEEDBACK_GESTURE_SWITCH, 1) == 0) {
            EngineLog.d(TAG, "vibrate: vibrate disable");
            return;
        }
        try {
            this.mIBluetoothPenCallback.vibrate(1, 50, 4);
        } catch (Exception e) {
            EngineLog.e(TAG, "vibrate error", e);
        }
    }

    public void registerGestureCallback(OnGestureCallback onGestureCallback) {
        if (onGestureCallback == null || this.mGestureCallbackList.contains(onGestureCallback)) {
            return;
        }
        this.mGestureCallbackList.add(onGestureCallback);
        EngineLog.d(TAG, "registerGestureCallback: list size=" + this.mGestureCallbackList.size());
        if (this.mGestureCallbackList.size() == 1) {
            this.mNeedRebind = true;
        }
        bindServiceIfNeed();
    }

    public void registerLifecycle(Activity activity) {
        if (Build.VERSION.SDK_INT >= 29) {
            activity.registerActivityLifecycleCallbacks(this.mActivityLifecycleCallbacks);
        }
    }

    public void unregisterGestureCallback(OnGestureCallback onGestureCallback) {
        EngineLog.d(TAG, "unregisterGestureCallback: list size=" + this.mGestureCallbackList.size());
        if (onGestureCallback != null) {
            this.mGestureCallbackList.remove(onGestureCallback);
        }
        unbindServiceIfNeed();
    }

    public void unregisterLifecycle(Activity activity) {
        if (Build.VERSION.SDK_INT >= 29) {
            activity.unregisterActivityLifecycleCallbacks(this.mActivityLifecycleCallbacks);
        }
    }

    public void writingVibrate(boolean z, int i) {
        if (this.mIBluetoothPenCallback == null) {
            EngineLog.d(TAG, "writingVibrate: callback is null");
            return;
        }
        Context context = this.mContext;
        if (context == null) {
            EngineLog.d(TAG, "writingVibrate: mContext is null");
            return;
        }
        int i2 = 0;
        int i3 = Settings.Global.getInt(context.getContentResolver(), STYLUS_WRITING_VIBRATE_SWITCH, 0);
        EngineLog.i(TAG, "writingVibrate: switch: " + i3);
        if (i3 == 1) {
            i3 = Settings.Global.getInt(this.mContext.getContentResolver(), STYLUS_WRITING_VIBRATE_STRENGTH_LEVEL, 2);
            EngineLog.i(TAG, "writingVibrate: writingVibrateStrengthLevel: " + i3);
        } else {
            EngineLog.d(TAG, "writingVibrate: writing vibrate disable");
        }
        if (z) {
            if (i3 == 1) {
                i2 = 96;
            } else if (i3 == 2) {
                i2 = 128;
            } else if (i3 == 3) {
                i2 = 160;
            }
        }
        int i4 = i2 | i;
        try {
            this.mIBluetoothPenCallback.setWritingVibrationIntensity(i4);
            EngineLog.i(TAG, "writingVibrate intensity: " + i4);
        } catch (Exception e) {
            EngineLog.e(TAG, "writingVibrate error", e);
        }
    }
}
