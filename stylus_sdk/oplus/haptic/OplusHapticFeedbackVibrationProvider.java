package com.android.server.vibrator;

import android.os.VibrationEffect;
import android.os.vibrator.OplusVibrationEffectSegment;
import com.android.server.notification.datacollection.TrackPolicyConfig;
import com.oplus.content.OplusFeatureConfigManager;
import java.util.Collections;

/* loaded from: classes2.dex */
public class OplusHapticFeedbackVibrationProvider {
    private static final int VIBRATION_EFFECT_DEFAULT_STRENGTH = -1;
    private static final int VIBRATION_EFFECT_NOT_REPEAT = -1;
    private final boolean mIsLinearMotorVibrator = OplusFeatureConfigManager.getInstance().hasFeature("oplus.software.vibrator_lmvibrator");
    private final boolean mIsLuxunLinearMotorVibrator = OplusFeatureConfigManager.getInstance().hasFeature("oplus.software.vibrator_luxunvibrator");

    public VibrationEffect getOverrideVibrationForHapticFeedback(int constant) {
        switch (constant) {
            case 0:
                long[] patternVirtualKey = {0, 65};
                return getVibrationForHapticFeedbackImpl(1, patternVirtualKey);
            case 1:
                long[] patternVirtualKey2 = {0, 65};
                int effctType = 1;
                if (this.mIsLuxunLinearMotorVibrator) {
                    effctType = 368;
                }
                return getVibrationForHapticFeedbackImpl(effctType, patternVirtualKey2);
            case 3:
            case 7:
            case 8:
            case 11:
            case 12:
            case 13:
            case 15:
            case 16:
                return getVibrationForHapticFeedbackImpl(1, null);
            case 4:
            case 6:
            case 9:
                return getVibrationForHapticFeedbackImpl(0, null);
            case 5:
                return getVibrationForHapticFeedbackImpl(2, null);
            case 14:
                return getVibrationForHapticFeedbackImpl(68, null);
            case 17:
                return getVibrationForHapticFeedbackImpl(3, null);
            case 300:
                long[] patternLongVibrate = {0, 65, 50, 65};
                return getVibrationForHapticFeedbackImpl(-1, patternLongVibrate);
            case TrackPolicyConfig.COMPONENT_TYPE_ASSOCIATION_START /* 301 */:
                long[] patternKeyboardTouch = {0, 40};
                return getVibrationForHapticFeedbackImpl(-1, patternKeyboardTouch);
            case 302:
                return getVibrationForHapticFeedbackImpl(0, null);
            case 303:
                return getVibrationForHapticFeedbackImpl(1, null);
            case 304:
                return getVibrationForHapticFeedbackImpl(3, null);
            case 305:
                return getVibrationForHapticFeedbackImpl(152, null);
            case 306:
                return getVibrationForHapticFeedbackImpl(154, null);
            case 307:
                return getVibrationForHapticFeedbackImpl(68, null);
            case 308:
                return getVibrationForHapticFeedbackImpl(0, null);
            case 309:
                long[] patternShutDown = {0, 65};
                return getVibrationForHapticFeedbackImpl(69, patternShutDown);
            case 10001:
                return getVibrationForHapticFeedbackImpl(70, null);
            default:
                return null;
        }
    }

    private VibrationEffect createWaveformEffect(int effectId, int strength, int repeat) {
        if (!this.mIsLinearMotorVibrator) {
            return null;
        }
        OplusVibrationEffectSegment segment = new OplusVibrationEffectSegment(effectId, strength);
        return new VibrationEffect.Composed(Collections.singletonList(segment), repeat);
    }

    private VibrationEffect createWaveformEffect(int effectId) {
        return createWaveformEffect(effectId, -1, -1);
    }

    private VibrationEffect getVibrationForHapticFeedbackImpl(int effectId, long[] fallback) {
        if (this.mIsLinearMotorVibrator && effectId != -1) {
            return createWaveformEffect(effectId);
        }
        if (fallback != null && fallback.length > 0) {
            return VibrationEffect.createWaveform(fallback, -1);
        }
        return null;
    }
}
