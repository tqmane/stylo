package com.vivo.trackpredictor.utils;

/* loaded from: classes4.dex */
public class HwPoint {
    public float pointX = 0.0f;
    public float pointY = 0.0f;
    public int actionCode = 0;
    public int pointTime = (int) System.currentTimeMillis();
    public float pressure = 0.0f;

    public int getActionCode() {
        return this.actionCode;
    }

    public float getPointPressure() {
        return this.pressure;
    }

    public int getPointTime() {
        return this.pointTime;
    }

    public float getPointX() {
        return this.pointX;
    }

    public float getPointY() {
        return this.pointY;
    }

    public void setActionCode(int i) {
        this.actionCode = i;
    }

    public void setPointTime(int i) {
        this.pointTime = i;
    }

    public void setPointX(float f) {
        this.pointX = f;
    }

    public void setPointY(float f) {
        this.pointY = f;
    }

    public void setPressure(float f) {
        this.pressure = f;
    }
}
