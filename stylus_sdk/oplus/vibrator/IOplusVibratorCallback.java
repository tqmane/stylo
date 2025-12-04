package com.android.server;

/* loaded from: classes.dex */
public interface IOplusVibratorCallback {
    void informVibrationFinished();

    boolean isInputDeviceVibratorsEmpty();

    void onAcquireVibratorWakeLock(int i);

    void onDebugFlagSwitch(boolean z);

    void onNoteVibratorOnLocked(int i, long j);

    void onReleaseVibratorWakeLock();

    void onVibrationEndLocked(long j);

    void originVibratorOff();

    long originVibratorPerformEffect(long j, long j2, boolean z);

    void originVibratorSetAmplitude(float f);

    void originVibratorUpdateAmplitude(int i, String str, float f);

    void removeVibrationEndRunnable();

    void setVibratorTouchStyle(int i);
}
