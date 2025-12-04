package com.android.server.vibrator;

import android.content.Context;
import android.content.pm.ServiceInfo;
import android.os.SystemProperties;
import android.os.VibrationEffect;
import android.os.vibrator.OplusPrebakedSegment;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Slog;
import android.util.Xml;
import android.view.inputmethod.InputMethodInfo;
import com.android.server.display.feature.marvels.utils.MarvelsLog;
import com.android.server.inputmethod.InputMethodManagerInternal;
import com.android.server.wm.squaredisplay.SquareDisplayOrientationRUSHelper;
import com.oplus.content.OplusFeatureConfigManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public class VibrationEffectConvertor {
    private static final int AMPLITUDE_SETTINGS_MAX = 2400;
    private static final int AMPLITUDE_SETTINGS_MIN = 800;
    private static final String ATTR_DEFAULT = "default";
    private static final String ATTR_PACKAGE_NAME = "packagename";
    private static final String IME_ADAPTATION_CONFIG_PATH = "/my_product/etc/vibrator_service_config/inputmethod_duration_map.xml";
    private static final int IME_WAVEFORM_ID_LIGHT = 16;
    private static final int IME_WAVEFORM_ID_MEDIUM = 17;
    private static final int IME_WAVEFORM_ID_STRONG = 18;
    private static final int LEGACY_IME_WAVEFORM_STRENGTH_0 = 100;
    private static final int LEGACY_IME_WAVEFORM_STRENGTH_1 = 103;
    private static final int LEGACY_IME_WAVEFORM_STRENGTH_2 = 106;
    private static final int LEGACY_IME_WAVEFORM_STRENGTH_3 = 109;
    private static final String NOTIFICATION_REASON = "Notification (";
    private static final int RTP_INDEX_EMULATION_KEYBOARD_DOWN = 302;
    private static final int RTP_INDEX_EMULATION_KEYBOARD_UP = 303;
    private static final int RTP_INDEX_KEYBOARD_MEDIUM = 111;
    private static final int RTP_INDEX_KEYBOARD_STRONG = 112;
    private static final int RTP_INDEX_KEYBOARD_WEAK = 110;
    private static final int RTP_INDEX_WEAK_EMULATION_KEYBOARD_DOWN = 304;
    private static final int RTP_INDEX_WEAK_EMULATION_KEYBOARD_UP = 305;
    private static final String TAG = "VibrationEffectConvertor";
    private static final String TAG_ENHANCE_DEFAULT = "enhancedefault";
    private static final int TIMING_THRESH_1 = 0;
    private static final int TIMING_THRESH_2 = 12;
    private static final int TIMING_THRESH_3 = 52;
    private static final int TIMING_THRESH_4 = 102;
    private static final int WAVEFORM_INDEX_STRONG_GRANULAR = 6;
    private static final int WAVEFORM_INDEX_WEAKEST_SHORT = 1;
    private static final int WAVEFORM_INDEX_WEAK_GRANULAR = 7;
    private static final int WAVEFORM_INDEX_WEAK_SHORT = 2;
    private final Context mContext;
    private String mCurrentImePackageName;
    private boolean mIsSupportVibrationIntensityIme;
    private boolean mIsSupportVibrationKeyboardConvert;
    private final WaveformEffectParser mWaveformEffectParser;
    private final Map<String, Integer> mEnhanceDefaultMap = new HashMap();
    private final boolean mLogEnable = SystemProperties.getBoolean(MarvelsLog.LOG_TOOL_RUNNING, false);
    private final Object mInputMethodLock = new Object();
    private final List<String> mInputMethodList = new ArrayList();
    private final InputMethodManagerInternal.InputMethodListListener mInputMethodListListener = new InputMethodManagerInternal.InputMethodListListener() { // from class: com.android.server.vibrator.VibrationEffectConvertor.1
        public void onInputMethodListUpdated(List<InputMethodInfo> info, int userId) {
            ServiceInfo serviceInfo;
            synchronized (VibrationEffectConvertor.this.mInputMethodLock) {
                Slog.i(VibrationEffectConvertor.TAG, "onInputMethodListUpdated received, info.size = " + info.size());
                VibrationEffectConvertor.this.mInputMethodList.clear();
                for (InputMethodInfo inputMethodInfo : info) {
                    if (inputMethodInfo != null && (serviceInfo = inputMethodInfo.getServiceInfo()) != null) {
                        String packageName = serviceInfo.packageName;
                        Slog.i(VibrationEffectConvertor.TAG, "userid = " + userId + ", packageName = " + packageName);
                        if (!TextUtils.isEmpty(packageName)) {
                            VibrationEffectConvertor.this.mInputMethodList.add(packageName);
                        }
                    }
                }
            }
        }
    };

    public VibrationEffectConvertor(Context context, WaveformEffectParser parser) {
        this.mIsSupportVibrationIntensityIme = false;
        this.mIsSupportVibrationKeyboardConvert = false;
        this.mContext = context;
        this.mWaveformEffectParser = parser;
        InputMethodManagerInternal inputMethodManagerInternal = InputMethodManagerInternal.get();
        inputMethodManagerInternal.registerInputMethodListListener(this.mInputMethodListListener);
        this.mIsSupportVibrationIntensityIme = OplusFeatureConfigManager.getInstance().hasFeature("oplus.software.vibration_intensity_ime");
        this.mIsSupportVibrationKeyboardConvert = OplusFeatureConfigManager.getInstacne().hasFeature("oplus.software.vibration_keyboard_convert");
        if (this.mIsSupportVibrationIntensityIme || this.mIsSupportVibrationKeyboardConvert) {
            getIMEAdaptionRule(IME_ADAPTATION_CONFIG_PATH);
        }
    }

    public void updateInputMethodSetting() {
        if (!this.mIsSupportVibrationIntensityIme && !this.mIsSupportVibrationKeyboardConvert) {
            return;
        }
        String imCmt = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "default_input_method", -2);
        synchronized (this.mInputMethodLock) {
            if (imCmt != null) {
                String[] tmp = imCmt.split(SquareDisplayOrientationRUSHelper.SLASH);
                this.mCurrentImePackageName = tmp[0];
                Slog.d(TAG, "updateVibrator: mCurrentImePackageName=" + this.mCurrentImePackageName);
            } else {
                Slog.d(TAG, "updateVibrator: mCurrentImePackageName=" + this.mCurrentImePackageName);
            }
        }
    }

    public boolean isWaveformForCustomizedIme(int waveformId) {
        switch (waveformId) {
            case 110:
            case 111:
            case 112:
            case RTP_INDEX_EMULATION_KEYBOARD_DOWN /* 302 */:
            case RTP_INDEX_EMULATION_KEYBOARD_UP /* 303 */:
            case RTP_INDEX_WEAK_EMULATION_KEYBOARD_DOWN /* 304 */:
            case RTP_INDEX_WEAK_EMULATION_KEYBOARD_UP /* 305 */:
                return true;
            default:
                return false;
        }
    }

    private boolean isInputMethodPackage(String packageName) {
        boolean z;
        synchronized (this.mInputMethodLock) {
            z = !TextUtils.isEmpty(packageName) && (this.mInputMethodList.contains(packageName) || packageName.equals(this.mCurrentImePackageName));
        }
        return z;
    }

    private void getIMEAdaptionRule(String path) {
        if (path == null) {
            return;
        }
        File configFile = new File(path);
        try {
            try {
                FileReader xmlReader = new FileReader(configFile);
                try {
                    try {
                        XmlPullParser parser = Xml.newPullParser();
                        parser.setInput(xmlReader);
                        for (int eventType = parser.getEventType(); eventType != 1; eventType = parser.next()) {
                            switch (eventType) {
                                case 2:
                                    String tmp = parser.getName();
                                    if (TAG_ENHANCE_DEFAULT.equals(tmp)) {
                                        this.mEnhanceDefaultMap.put(parser.getAttributeValue(null, ATTR_PACKAGE_NAME), Integer.valueOf(Integer.parseInt(parser.getAttributeValue(null, "default"))));
                                        break;
                                    } else {
                                        break;
                                    }
                            }
                        }
                        if (this.mLogEnable) {
                            Slog.d(TAG, "getIMEAdaptionRule: the result is enhanceDefaultMap=" + this.mEnhanceDefaultMap);
                        }
                        xmlReader.close();
                    } catch (Throwable th) {
                        try {
                            xmlReader.close();
                        } catch (IOException e) {
                            Slog.d(TAG, "Got exception close permReader.", e);
                        }
                        throw th;
                    }
                } catch (IOException e2) {
                    Slog.d(TAG, "Got exception parsing permissions.", e2);
                    xmlReader.close();
                } catch (XmlPullParserException e3) {
                    Slog.d(TAG, "Got exception parsing permissions.", e3);
                    xmlReader.close();
                }
            } catch (IOException e4) {
                Slog.d(TAG, "Got exception close permReader.", e4);
            }
        } catch (FileNotFoundException e5) {
            Slog.d(TAG, "create reader failed.", e5);
        }
    }

    private int getImeWaveformIdFromTiming(long timing) {
        if (timing > 0 && timing <= 12) {
            return 110;
        }
        if (timing > 12 && timing <= 52) {
            return 111;
        }
        if (timing > 52 && timing <= 102) {
            return 112;
        }
        return -1;
    }

    private int getLegacyImeWaveformIdFromTiming(long timing) {
        if (timing > 0 && timing <= 12) {
            return 16;
        }
        if (timing > 12 && timing <= 52) {
            return 17;
        }
        if (timing > 52 && timing <= 102) {
            return 18;
        }
        return -1;
    }

    private int getLegacyImeStrengthFromTiming(long timing) {
        if (timing > 0 && timing <= 12) {
            return 100;
        }
        if (timing <= 12 || timing > 52) {
            return (timing <= 52 || timing > 102) ? 100 : 109;
        }
        return 106;
    }

    private int getWaveformIdFromTiming(long timing) {
        if (timing > 0 && timing <= 12) {
            return 7;
        }
        if (timing > 12 && timing <= 52) {
            return 2;
        }
        if (timing > 52 && timing <= 102) {
            return 6;
        }
        return -1;
    }

    private int transferImeWaveformId(int waveformId) {
        switch (waveformId) {
            case 1:
            case 7:
                return 110;
            case 2:
                return 111;
            case 3:
            case 4:
            case 5:
            default:
                return -1;
            case 6:
                return 112;
        }
    }

    private int transferLegacyImeWaveformId(int waveformId) {
        switch (waveformId) {
            case 1:
            case 2:
                return 17;
            case 3:
            case 4:
            case 5:
            default:
                return -1;
            case 6:
                return 18;
            case 7:
                return 16;
        }
    }

    private int getLegacyImeStrengthByWaveform(int waveformId) {
        switch (waveformId) {
            case 1:
                return 103;
            case 2:
                return 106;
            case 3:
            case 4:
            case 5:
            default:
                return -1;
            case 6:
                return 109;
            case 7:
                return 100;
        }
    }

    private VibrationEffect createOplusPredefined(int effectId, long duration, int strength, boolean isRTPType) {
        OplusPrebakedSegment oplusPrebakedSegment = new OplusPrebakedSegment(effectId, duration, strength, isRTPType);
        oplusPrebakedSegment.setDefaultUsage(18);
        return new VibrationEffect.Composed(Collections.singletonList(oplusPrebakedSegment), -1);
    }

    private VibrationEffect createOplusWaveform(int[] effectIds, long[] timings, int[] strengths, boolean[] isRTPType, int repeat, boolean isImeUsage) {
        if (effectIds.length != strengths.length) {
            throw new IllegalArgumentException("effectIds and strengths arrays must be of equal length (effectIds.length=" + effectIds.length + ", strengths.length=" + strengths.length + ")");
        }
        List<OplusPrebakedSegment> segments = new ArrayList<>();
        for (int i = 0; i < effectIds.length; i++) {
            OplusPrebakedSegment oplusPrebakedSegment = new OplusPrebakedSegment(effectIds[i], timings[i], strengths[i], isRTPType[i]);
            if (isImeUsage) {
                oplusPrebakedSegment.setDefaultUsage(18);
            }
            segments.add(oplusPrebakedSegment);
        }
        return new VibrationEffect.Composed(segments, repeat);
    }

    private int convertEffectStrength(int strength) {
        switch (strength) {
            case 0:
            case 1:
            case 2:
                return Math.min((strength + 1) * 800, AMPLITUDE_SETTINGS_MAX);
            default:
                return strength;
        }
    }

    private int convertEffectStrength(float amplitude) {
        if (Float.compare(amplitude, -1.0f) == 0) {
            return -1;
        }
        if (Float.compare(amplitude, 0.0f) == 0) {
            return 0;
        }
        return Math.min(Math.max(800, (int) (2400.0f * amplitude)), AMPLITUDE_SETTINGS_MAX);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x003d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public android.os.VibrationEffect convertVibrationEffect(java.lang.String r28, java.lang.String r29, android.os.VibrationEffect r30) {
        /*
            Method dump skipped, instructions count: 545
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.vibrator.VibrationEffectConvertor.convertVibrationEffect(java.lang.String, java.lang.String, android.os.VibrationEffect):android.os.VibrationEffect");
    }
}
