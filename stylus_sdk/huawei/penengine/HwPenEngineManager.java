package com.huawei.stylus.penengine;

import android.annotation.SuppressLint;
import android.content.Context;
import com.huawei.penkit.impl.KitManager;
import com.huawei.stylus.penengine.version.IVersionInfo;
import java.util.NoSuchElementException;
import java.util.Optional;

/* loaded from: classes9.dex */
public class HwPenEngineManager {
    public static final String KIT_MANAGER = "com.huawei.penkit.impl.KitManager";
    public static final String TAG = "HwPenEngineManager";
    public static int sApiLevel = -1;

    public static int getApiLevel() {
        return sApiLevel;
    }

    @SuppressLint({"NewApi"})
    public static Optional<VersionInfo> getVersionInfo(KitManager kitManager) {
        if (kitManager == null) {
            return Optional.empty();
        }
        IVersionInfo versionInfo = kitManager.getVersionInfo();
        return versionInfo != null ? Optional.ofNullable(new VersionInfo(versionInfo)) : Optional.empty();
    }

    public static boolean isApiLevelCompatible(int i) {
        return i >= 1 && getApiLevel() >= i;
    }

    @SuppressLint({"NewApi"})
    public static boolean isEngineRuntimeAvailable(Context context) {
        if (context == null || !VersionInfo.isKitRuntimeAvailable(context)) {
            return false;
        }
        try {
            if (Class.forName(KIT_MANAGER) == null) {
                return false;
            }
            VersionInfo versionInfo = getVersionInfo(new KitManager(context)).get();
            sApiLevel = versionInfo.getApiLevel();
            return versionInfo.isAllCompatible();
        } catch (ClassNotFoundException | NoSuchElementException unused) {
            return false;
        }
    }

    public static boolean isSupportEink(Context context) {
        if (isEngineRuntimeAvailable(context) && isApiLevelCompatible(3)) {
            try {
                if (Class.forName(KIT_MANAGER) == null) {
                    return false;
                }
                return new KitManager(context).isSupportEink();
            } catch (ClassNotFoundException unused) {
            }
        }
        return false;
    }

    public static boolean isSupportFeature(Context context, int i) {
        if (isEngineRuntimeAvailable(context)) {
            return isApiLevelCompatible(i);
        }
        return false;
    }
}
