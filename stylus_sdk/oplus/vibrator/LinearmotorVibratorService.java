package com.android.server.oplus;

import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.UserHandle;
import android.util.Slog;
import com.android.server.vibrator.VibratorManagerServiceExtImpl;
import com.oplus.os.ILinearmotorVibratorService;
import com.oplus.os.WaveformEffect;

/* loaded from: classes.dex */
public class LinearmotorVibratorService extends ILinearmotorVibratorService.Stub {
    private static final float DEFAULT_AMPLITUDE_RATIO = 1.0f;
    private static final String TAG = "LinearmotorVibratorService";
    private final Context mContext;
    private VibratorManagerServiceExtImpl mVibratorServiceExt;

    public LinearmotorVibratorService(Context context) {
        this.mContext = context;
    }

    public void systemReady() {
        this.mVibratorServiceExt = VibratorManagerServiceExtImpl.getInstance(null);
    }

    public void vibrate(int uid, String opPkg, WaveformEffect we, IBinder token) {
    }

    public void cancelVibrate(WaveformEffect we, IBinder token) {
    }

    public int getVibratorStatus() {
        return this.mVibratorServiceExt.getVibratorStatus();
    }

    public void setVibratorStrength(int strength) {
        if (UserHandle.getAppId(Binder.getCallingUid()) != 1000) {
            Slog.d(TAG, "Permission Denial : can't access from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid());
        } else {
            this.mVibratorServiceExt.setVibratorStrength(strength);
        }
    }

    public int getVibratorTouchStyle() {
        return this.mVibratorServiceExt.getVibratorTouchStyle();
    }

    public void setVibratorTouchStyle(int style) {
        if (UserHandle.getAppId(Binder.getCallingUid()) != 1000) {
            Slog.d(TAG, "Permission Denial : can't access from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid());
        } else {
            this.mVibratorServiceExt.setVibratorTouchStyle(style);
        }
    }

    public int getSettingsTouchEffectStrength() {
        return this.mVibratorServiceExt.getCurrentVibrationIntensity(18);
    }

    public int getSettingsRingtoneEffectStrength() {
        return this.mVibratorServiceExt.getCurrentVibrationIntensity(33);
    }

    public int getSettingsNotificationEffectStrength() {
        return this.mVibratorServiceExt.getCurrentVibrationIntensity(49);
    }

    public void updateVibrationAmplitude(int uid, String opPkg, float amplitudeRatio) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.VIBRATE") != 0) {
            throw new SecurityException("Permission Denial: can't access updateVibrationAmplitude from uid = " + uid + ", opPkg = " + opPkg);
        }
        Slog.i(TAG, "updateVibrationAmplitude uid = " + uid + ", opPkg = " + opPkg + ", amplitudeRatio = " + amplitudeRatio);
        if (amplitudeRatio < 0.0f || amplitudeRatio > 1.0f) {
            Slog.e(TAG, "invalid amplitudeRatio");
        } else {
            this.mVibratorServiceExt.updateVibrationAmplitude(uid, opPkg, amplitudeRatio);
        }
    }

    public boolean checkRichtapBlackList(String packageName) {
        return this.mVibratorServiceExt.checkRichtapBlackList(packageName);
    }
}
