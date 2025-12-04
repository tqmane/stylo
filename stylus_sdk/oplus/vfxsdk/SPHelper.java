package com.oplusos.vfxsdk.doodleengine;

import android.content.Context;
import android.content.SharedPreferences;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.e;

/* compiled from: SPHelper.kt */
/* loaded from: classes5.dex */
public final class SPHelper {
    public static final Companion Companion = new Companion(null);
    public static final String SP_SHAPE_REGULAR = "sp_share_regular";
    public static final String SP_TYPE_FONT = "sp_type_font";
    private static SPHelper instance;
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;

    /* compiled from: SPHelper.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(e eVar) {
            this();
        }

        public final SPHelper getInstance() {
            SPHelper sPHelper = SPHelper.instance;
            if (sPHelper == null) {
                synchronized (this) {
                    sPHelper = SPHelper.instance;
                    if (sPHelper == null) {
                        sPHelper = new SPHelper(null);
                        SPHelper.instance = sPHelper;
                    }
                }
            }
            return sPHelper;
        }
    }

    private SPHelper() {
    }

    public /* synthetic */ SPHelper(e eVar) {
        this();
    }

    public final void checkEmpty(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (this.editor == null) {
            init(context);
        }
    }

    public final boolean getBoolean(String key, boolean z) {
        Intrinsics.checkNotNullParameter(key, "key");
        SharedPreferences sharedPreferences = this.preferences;
        return sharedPreferences != null ? sharedPreferences.getBoolean(key, z) : z;
    }

    public final float getFloat(String key, float f) {
        Intrinsics.checkNotNullParameter(key, "key");
        SharedPreferences sharedPreferences = this.preferences;
        return sharedPreferences != null ? sharedPreferences.getFloat(key, f) : f;
    }

    public final int getInt(String key, int i) {
        Intrinsics.checkNotNullParameter(key, "key");
        SharedPreferences sharedPreferences = this.preferences;
        return sharedPreferences != null ? sharedPreferences.getInt(key, i) : i;
    }

    public final String getString(String str, String str2) {
        SharedPreferences sharedPreferences = this.preferences;
        if (sharedPreferences != null) {
            return sharedPreferences.getString(str, str2);
        }
        return null;
    }

    public final void init(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), 0);
        this.preferences = sharedPreferences;
        this.editor = sharedPreferences != null ? sharedPreferences.edit() : null;
    }

    public final void putBoolean(String key, boolean z) {
        Intrinsics.checkNotNullParameter(key, "key");
        SharedPreferences.Editor editor = this.editor;
        if (editor != null) {
            editor.putBoolean(key, z);
        }
        SharedPreferences.Editor editor2 = this.editor;
        if (editor2 != null) {
            editor2.commit();
        }
    }

    public final void putFloat(String key, float f) {
        Intrinsics.checkNotNullParameter(key, "key");
        SharedPreferences.Editor editor = this.editor;
        if (editor != null) {
            editor.putFloat(key, f);
        }
        SharedPreferences.Editor editor2 = this.editor;
        if (editor2 != null) {
            editor2.commit();
        }
    }

    public final void putInt(String key, int i) {
        Intrinsics.checkNotNullParameter(key, "key");
        SharedPreferences.Editor editor = this.editor;
        if (editor != null) {
            editor.putInt(key, i);
        }
        SharedPreferences.Editor editor2 = this.editor;
        if (editor2 != null) {
            editor2.commit();
        }
    }

    public final void putString(String str, String str2) {
        SharedPreferences.Editor editor = this.editor;
        if (editor != null) {
            editor.putString(str, str2);
        }
        SharedPreferences.Editor editor2 = this.editor;
        if (editor2 != null) {
            editor2.commit();
        }
    }
}
