package com.vivo.penengine.impl;

import android.content.Context;
import com.vivo.penengine.impl.VivoStylusGestureManagerImpl;

/* loaded from: classes4.dex */
public final class VivoStylusManagerImpl implements VivoStylusGestureManagerImpl.OnGestureCallback {
    public static final String TAG = "VivoStylusManagerImpl";
    public static volatile VivoStylusManagerImpl instance;
    public final Context mContext;
    public boolean mIsInitialized;

    public enum STYLUS_WRITING_VIBRATE_TYPE {
        OFF,
        TYPE_1,
        TYPE_2;

        @Override // java.lang.Comparable
        public /* bridge */ /* synthetic */ int compareTo(Object obj) {
            return super.compareTo((STYLUS_WRITING_VIBRATE_TYPE) obj);
        }
    }

    public VivoStylusManagerImpl(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static VivoStylusManagerImpl getInstance(Context context) {
        if (instance == null) {
            synchronized (VivoStylusManagerImpl.class) {
                if (instance == null) {
                    instance = new VivoStylusManagerImpl(context);
                }
            }
        }
        return instance;
    }

    public void destroy() {
        EngineLog.d(TAG, "destroy: init=" + this.mIsInitialized);
        if (this.mIsInitialized) {
            VivoStylusGestureManagerImpl.getInstance(this.mContext).unregisterGestureCallback(this);
            this.mIsInitialized = false;
        }
    }

    public void enableWritingVibrate(boolean z) {
        if (this.mIsInitialized) {
            VivoStylusGestureManagerImpl.getInstance(this.mContext).writingVibrate(z, STYLUS_WRITING_VIBRATE_TYPE.TYPE_2.ordinal());
        }
    }

    public void init() {
        VivoStylusGestureManagerImpl.getInstance(this.mContext).registerGestureCallback(this);
        this.mIsInitialized = true;
        EngineLog.d(TAG, "init");
    }

    @Override // com.vivo.penengine.impl.VivoStylusGestureManagerImpl.OnGestureCallback
    public boolean onGesture(int i) {
        return false;
    }
}
