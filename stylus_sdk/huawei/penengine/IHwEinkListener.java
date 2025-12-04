package com.huawei.stylus.penengine.eink.listener;

import android.view.MotionEvent;
import android.view.SurfaceHolder;

/* loaded from: classes9.dex */
public interface IHwEinkListener {
    void onAddPoint(MotionEvent motionEvent);

    void onSelectCancel();

    void onSelectComplete();

    void onStepChanged(int i);

    void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3);

    void surfaceCreated(SurfaceHolder surfaceHolder);

    void surfaceDestroyed(SurfaceHolder surfaceHolder);
}
