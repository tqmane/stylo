package com.android.server.vibrator;

import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Slog;
import com.android.server.display.feature.marvels.utils.MarvelsLog;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes2.dex */
public class WaveformEffectParser {
    private static final String ATTRIBUTE_DESCRIPTION = "desc";
    private static final String ATTRIBUTE_DURATION = "duration";
    private static final String ATTRIBUTE_EFFECT_ID = "effect_id";
    private static final String ATTRIBUTE_RINGTONE_NAME = "ringtone_name";
    private static final String ATTRIBUTE_TYPE = "type";
    private static final String ATTRIBUTE_USAGE = "usage";
    private static final String ATTRIBUTE_WAVEFORM_ID = "waveform_id";
    private static final String COMMON_WAVEFORM_MAP_PATH = "/my_product/etc/vibrator/effect2waveform.xml";
    private static final boolean DEBUG = SystemProperties.getBoolean(MarvelsLog.LOG_TOOL_RUNNING, false);
    private static final String EFFECT_WAVEFORM_MAP_DATA_OVERRIDE_PATH = "/data/oplus/os/vibrator_effect_waveform_override.xml";
    private static final String EFFECT_WAVEFORM_MAP_PATH = "/my_product/etc/vibrator/effect_waveform.xml";
    private static final String EFFECT_WAVEFORM_OVERRIDE_MAP_PATH = "/my_product/etc/vibrator/effect_waveform_override.xml";
    private static final String TAG = "WaveformEffectParser";
    private static final String TAG_EFFECT_ITEM = "effect";
    private static final String TAG_RINGTONE_EFFECT_ITEM = "ringtone_effect";
    private final ArrayMap<String, Integer> mRingtoneEffectMap = new ArrayMap<>();
    private final ArrayMap<Integer, WaveformItem> mEffectWaveformMap = new ArrayMap<>();
    private final ArrayMap<String, Integer> mOverrideRingtoneEffectMap = new ArrayMap<>();
    private final ArrayMap<Integer, WaveformItem> mOverrideEffectWaveformMap = new ArrayMap<>();
    private final Object mLock = new Object();

    public WaveformEffectParser() {
        synchronized (this.mLock) {
            this.mOverrideEffectWaveformMap.putAll((ArrayMap<? extends Integer, ? extends WaveformItem>) parseEffectWaveformMap(EFFECT_WAVEFORM_MAP_DATA_OVERRIDE_PATH));
            this.mOverrideRingtoneEffectMap.putAll((ArrayMap<? extends String, ? extends Integer>) parseRingtoneEffectMap(EFFECT_WAVEFORM_MAP_DATA_OVERRIDE_PATH));
            this.mEffectWaveformMap.putAll((ArrayMap<? extends Integer, ? extends WaveformItem>) parseEffectWaveformMap(getWaveformMapPath()));
            this.mRingtoneEffectMap.putAll((ArrayMap<? extends String, ? extends Integer>) parseRingtoneEffectMap(getWaveformMapPath()));
            if (new File(EFFECT_WAVEFORM_OVERRIDE_MAP_PATH).canRead()) {
                ArrayMap<Integer, WaveformItem> effectWaveformMap = parseEffectWaveformMap(EFFECT_WAVEFORM_OVERRIDE_MAP_PATH);
                ArrayMap<String, Integer> ringtoneEffectMap = parseRingtoneEffectMap(EFFECT_WAVEFORM_OVERRIDE_MAP_PATH);
                if (!effectWaveformMap.isEmpty()) {
                    Set<Map.Entry<Integer, WaveformItem>> entries = effectWaveformMap.entrySet();
                    for (Map.Entry<Integer, WaveformItem> entry : entries) {
                        int effectId = entry.getKey().intValue();
                        WaveformItem waveformItem = entry.getValue();
                        if (this.mEffectWaveformMap.containsKey(Integer.valueOf(effectId))) {
                            this.mEffectWaveformMap.put(Integer.valueOf(effectId), waveformItem);
                        }
                    }
                }
                if (!ringtoneEffectMap.isEmpty()) {
                    Set<Map.Entry<String, Integer>> entries2 = ringtoneEffectMap.entrySet();
                    for (Map.Entry<String, Integer> entry2 : entries2) {
                        String ringtone = entry2.getKey();
                        int effectId2 = entry2.getValue().intValue();
                        if (this.mRingtoneEffectMap.containsKey(ringtone)) {
                            this.mRingtoneEffectMap.put(ringtone, Integer.valueOf(effectId2));
                        }
                    }
                }
            }
        }
    }

    private String getWaveformMapPath() {
        if (new File(EFFECT_WAVEFORM_MAP_PATH).canRead()) {
            return EFFECT_WAVEFORM_MAP_PATH;
        }
        if (new File(COMMON_WAVEFORM_MAP_PATH).canRead()) {
            return COMMON_WAVEFORM_MAP_PATH;
        }
        Slog.e(TAG, "no waveform effect config found");
        return null;
    }

    private ArrayMap<Integer, WaveformItem> parseEffectWaveformMap(String effectWaveformFilePath) {
        FileReader fileReader = null;
        ArrayMap<Integer, WaveformItem> effectWaveformMap = new ArrayMap<>();
        if (!TextUtils.isEmpty(effectWaveformFilePath)) {
            try {
                try {
                } finally {
                }
            } catch (IOException e) {
            }
            if (new File(effectWaveformFilePath).exists()) {
                try {
                    XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
                    xmlPullParserFactory.setNamespaceAware(false);
                    XmlPullParser parser = xmlPullParserFactory.newPullParser();
                    fileReader = new FileReader(effectWaveformFilePath);
                    parser.setInput(fileReader);
                    while (parser.getEventType() != 1) {
                        if (parser.getEventType() == 2 && TAG_EFFECT_ITEM.equals(parser.getName())) {
                            String attrEffectId = parser.getAttributeValue(null, ATTRIBUTE_EFFECT_ID);
                            int effectId = TextUtils.isEmpty(attrEffectId) ? -1 : Integer.parseInt(attrEffectId);
                            String attrWaveformId = parser.getAttributeValue(null, ATTRIBUTE_WAVEFORM_ID);
                            String attrDuration = parser.getAttributeValue(null, "duration");
                            String attrType = parser.getAttributeValue(null, "type");
                            String attrUsage = parser.getAttributeValue(null, ATTRIBUTE_USAGE);
                            String attrDesc = parser.getAttributeValue(null, ATTRIBUTE_DESCRIPTION);
                            int waveformId = Integer.parseInt(attrWaveformId);
                            int duration = Integer.parseInt(attrDuration);
                            if (effectId != -1 && waveformId >= 0 && duration >= 0) {
                                if (WaveformItem.WAVEFORM_TYPE_RAM.equals(attrType) || WaveformItem.WAVEFORM_TYPE_RTP.equals(attrType)) {
                                    effectWaveformMap.put(Integer.valueOf(effectId), new WaveformItem(waveformId, duration, attrType, attrUsage, attrDesc));
                                }
                            }
                        }
                        parser.next();
                    }
                    Slog.i(TAG, "amount=" + effectWaveformMap.size() + " from " + effectWaveformFilePath);
                    fileReader.close();
                } catch (Exception e2) {
                    Slog.i(TAG, "parse exception caught : " + e2.getMessage());
                    if (fileReader != null) {
                        fileReader.close();
                    }
                }
                return effectWaveformMap;
            }
        }
        return effectWaveformMap;
    }

    private ArrayMap<String, Integer> parseRingtoneEffectMap(String effectWaveformFilePath) {
        FileReader fileReader = null;
        ArrayMap<String, Integer> ringtoneEffectMap = new ArrayMap<>();
        if (!TextUtils.isEmpty(effectWaveformFilePath)) {
            if (new File(effectWaveformFilePath).exists()) {
                try {
                    try {
                        XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
                        xmlPullParserFactory.setNamespaceAware(false);
                        XmlPullParser parser = xmlPullParserFactory.newPullParser();
                        fileReader = new FileReader(effectWaveformFilePath);
                        parser.setInput(fileReader);
                        while (parser.getEventType() != 1) {
                            if (parser.getEventType() == 2 && TAG_RINGTONE_EFFECT_ITEM.equals(parser.getName())) {
                                String attrEffectId = parser.getAttributeValue(null, ATTRIBUTE_EFFECT_ID);
                                int effectId = TextUtils.isEmpty(attrEffectId) ? -1 : Integer.parseInt(attrEffectId);
                                String attrRingtoneName = parser.getAttributeValue(null, ATTRIBUTE_RINGTONE_NAME);
                                if (TextUtils.isEmpty(attrRingtoneName)) {
                                    Slog.e(TAG, "invalid ringtone name or effect id");
                                } else {
                                    ringtoneEffectMap.put(attrRingtoneName, Integer.valueOf(effectId));
                                }
                            }
                            parser.next();
                        }
                        Slog.i(TAG, "ringtone amount=" + ringtoneEffectMap.size() + " from " + effectWaveformFilePath);
                        fileReader.close();
                    } catch (Exception e) {
                        Slog.i(TAG, "parse exception caught : " + e.getMessage());
                        if (fileReader != null) {
                            fileReader.close();
                        }
                    }
                    return ringtoneEffectMap;
                } catch (Throwable th) {
                    if (fileReader != null) {
                        try {
                            fileReader.close();
                        } catch (IOException e2) {
                        }
                    }
                    throw th;
                }
            }
        }
        return ringtoneEffectMap;
    }

    public int getRingtoneEffectInternal(String ringtoneTitle) {
        synchronized (this.mLock) {
            if (TextUtils.isEmpty(ringtoneTitle)) {
                return -1;
            }
            int effectId = this.mOverrideRingtoneEffectMap.getOrDefault(ringtoneTitle, -1).intValue();
            if (effectId == -1) {
                return this.mRingtoneEffectMap.getOrDefault(ringtoneTitle, -1).intValue();
            }
            if (DEBUG) {
                Slog.i(TAG, "getRingtoneEffectInternal from override effect map, ringtoneTitle = " + ringtoneTitle + ", effectId = " + effectId);
            }
            return effectId;
        }
    }

    public WaveformItem getWaveformItem(int effectId) {
        synchronized (this.mLock) {
            WaveformItem item = this.mOverrideEffectWaveformMap.getOrDefault(Integer.valueOf(effectId), null);
            if (item == null) {
                return this.mEffectWaveformMap.getOrDefault(Integer.valueOf(effectId), null);
            }
            if (DEBUG) {
                Slog.i(TAG, "getWaveformItem from override waveform map, effectId = " + effectId + ", item = " + item);
            }
            return item;
        }
    }

    public long getWaveformDuration(int waveformId, boolean isRtp, int usage) {
        synchronized (this.mLock) {
            if (waveformId == -1) {
                return -1L;
            }
            int duration = getDurationFromWaveformMap(this.mOverrideEffectWaveformMap, waveformId, isRtp, usage);
            if (duration == -1) {
                return getDurationFromWaveformMap(this.mEffectWaveformMap, waveformId, isRtp, usage);
            }
            if (DEBUG) {
                Slog.i(TAG, "getWaveformDuration from override waveform map, waveformId = " + waveformId + ", isRtp = " + isRtp + ", duration = " + duration);
            }
            return duration;
        }
    }

    private int getDurationFromWaveformMap(Map<Integer, WaveformItem> waveformItemMap, int waveformId, boolean isRtp, int usage) {
        int duration = -1;
        if (waveformItemMap != null && !waveformItemMap.isEmpty()) {
            Set<Map.Entry<Integer, WaveformItem>> entries = waveformItemMap.entrySet();
            for (Map.Entry<Integer, WaveformItem> entry : entries) {
                WaveformItem waveformItem = entry.getValue();
                if (waveformItem != null && waveformItem.getWaveformId() == waveformId && waveformItem.isTypeRTP() == isRtp) {
                    if (usage == waveformItem.getDefaultUsage()) {
                        return waveformItem.getDuration();
                    }
                    if (waveformItem.getDuration() >= duration) {
                        duration = waveformItem.getDuration();
                    }
                }
            }
        }
        return duration;
    }
}
