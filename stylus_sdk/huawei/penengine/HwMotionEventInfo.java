package com.huawei.stylus.penengine.estimate;

/* loaded from: classes9.dex */
public class HwMotionEventInfo {
    public float mAxisX;
    public float mAxisY;
    public long mEventTime;
    public float mPress;
    public float mTilt;

    public HwMotionEventInfo(float f, float f2, float f3, float f4, long j) {
        this.mAxisX = f;
        this.mAxisY = f2;
        this.mPress = f3;
        this.mTilt = f4;
        this.mEventTime = j;
    }

    public long getEventTime() {
        return this.mEventTime;
    }

    public float getPress() {
        return this.mPress;
    }

    public float getTilt() {
        return this.mTilt;
    }

    public float getX() {
        return this.mAxisX;
    }

    public float getY() {
        return this.mAxisY;
    }

    public void setEventTime(long j) {
        this.mEventTime = j;
    }

    public void setPress(float f) {
        this.mPress = f;
    }

    public void setTilt(float f) {
        this.mTilt = f;
    }

    public void setX(float f) {
        this.mAxisX = f;
    }

    public void setY(float f) {
        this.mAxisY = f;
    }

    public String toString() {
        return "time: " + this.mEventTime + "point: " + this.mAxisX + ", " + this.mAxisY;
    }
}
