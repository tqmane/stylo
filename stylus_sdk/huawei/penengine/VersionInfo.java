package com.huawei.stylus.penengine;

import android.content.Context;
import com.huawei.stylus.penengine.version.IVersionInfo;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes9.dex */
public final class VersionInfo {
    public static final int API_LEVEL_1 = 1;
    public static final int API_LEVEL_2 = 2;
    public static final int API_LEVEL_3 = 3;
    public static final int CURRENT_SDK_VERSIONCODE = 110101300;
    public static final String CURRENT_SDK_VERSIONNAME = "11.1.1.300";
    public static final String HUAWEI_PENKIT_RUNTIME_PACKAGE_NAME = "com.huawei.featurelayer.sharedfeature.stylus";
    public static final String TAG = "VersionInfo";
    public static final Pattern VERSION_STRING_PATTERN = Pattern.compile("(\\d+)(?:\\.(\\d+))(?:\\.(\\d+))(?:\\.(\\d+))");
    public IVersionInfo versionInfo;

    public VersionInfo(IVersionInfo iVersionInfo) {
        this.versionInfo = iVersionInfo;
    }

    private IVersionInfo getObject() {
        return this.versionInfo;
    }

    public static boolean isKitRuntimeAvailable(Context context) {
        if (context == null) {
            return false;
        }
        return context.getPackageManager().getPackageInfo(HUAWEI_PENKIT_RUNTIME_PACKAGE_NAME, 0) != null;
    }

    private boolean isVersionCompatible(String str, int i) {
        String versionName;
        if (getObject() == null || (versionName = getObject().getVersionName()) == null || versionName.length() == 0) {
            return false;
        }
        Matcher matcher = VERSION_STRING_PATTERN.matcher(versionName);
        Matcher matcher2 = VERSION_STRING_PATTERN.matcher(str);
        if (!matcher.matches() || !matcher2.matches()) {
            return false;
        }
        try {
            return Integer.parseInt(matcher.group(1)) == Integer.parseInt(matcher2.group(1));
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    public int getApiLevel() {
        if (getObject() != null) {
            return getObject().getApiLevel();
        }
        return -1;
    }

    public boolean isAllCompatible() {
        return isVersionCompatible(CURRENT_SDK_VERSIONNAME, CURRENT_SDK_VERSIONCODE);
    }
}
