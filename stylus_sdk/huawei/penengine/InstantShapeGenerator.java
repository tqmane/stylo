package com.huawei.stylus.penengine.instantshape;

import android.content.Context;
import android.graphics.Path;
import android.view.MotionEvent;
import com.huawei.penkit.impl.manager.forming.OpenFormingManager;

/* loaded from: classes9.dex */
public class InstantShapeGenerator {
    public OpenFormingManager mFormingManager;

    public InstantShapeGenerator(Context context) {
        this.mFormingManager = new OpenFormingManager(context);
    }

    public void destroy() {
        OpenFormingManager openFormingManager = this.mFormingManager;
        if (openFormingManager != null) {
            openFormingManager.destroy();
        }
        this.mFormingManager = null;
    }

    public int getCurveType() {
        OpenFormingManager openFormingManager = this.mFormingManager;
        if (openFormingManager == null) {
            return 0;
        }
        return openFormingManager.getCurveType();
    }

    public Path getPath() {
        OpenFormingManager openFormingManager = this.mFormingManager;
        return openFormingManager == null ? new Path() : openFormingManager.getPath();
    }

    public Path getPathFromString(String str, float f) {
        if (str == null || str.length() == 0) {
            return new Path();
        }
        OpenFormingManager openFormingManager = this.mFormingManager;
        return openFormingManager == null ? new Path() : openFormingManager.getFormedShapePathFromString(str, f);
    }

    public String getStringResult() {
        OpenFormingManager openFormingManager = this.mFormingManager;
        return openFormingManager == null ? "" : openFormingManager.getFormedShapeStringResult();
    }

    public void notifyViewSizeChange(int i, int i2) {
        OpenFormingManager openFormingManager = this.mFormingManager;
        if (openFormingManager == null) {
            return;
        }
        openFormingManager.notifyViewSizeChange(i, i2);
    }

    public void processTouchEvent(MotionEvent motionEvent) {
        OpenFormingManager openFormingManager = this.mFormingManager;
        if (openFormingManager == null) {
            return;
        }
        openFormingManager.processFormingEvent(motionEvent);
    }

    public void setStopTime(int i) {
        OpenFormingManager openFormingManager = this.mFormingManager;
        if (openFormingManager == null) {
            return;
        }
        openFormingManager.setStopTime(i);
    }
}
