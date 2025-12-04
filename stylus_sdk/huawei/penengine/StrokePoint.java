package com.huawei.stylus.penengine.eink.model;

/* loaded from: classes9.dex */
public class StrokePoint {
    public int action;
    public float pressure;
    public long timestamp;
    public float x;
    public float y;

    public StrokePoint(int i, float f, float f2, long j, float f3) {
        this(f, f2, j, f3);
        this.action = i;
    }

    public int getAction() {
        return this.action;
    }

    public float getPressure() {
        return this.pressure;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public void setAction(int i) {
        this.action = i;
    }

    public void setPressure(float f) {
        this.pressure = f;
    }

    public void setTimestamp(long j) {
        this.timestamp = j;
    }

    public void setX(float f) {
        this.x = f;
    }

    public void setY(float f) {
        this.y = f;
    }

    public StrokePoint(float f, float f2, long j, float f3) {
        this(f, f2);
        this.timestamp = j;
        this.pressure = f3;
    }

    public StrokePoint(float f, float f2) {
        this.x = f;
        this.y = f2;
    }
}
