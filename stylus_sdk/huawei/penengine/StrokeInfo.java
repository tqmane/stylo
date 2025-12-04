package com.huawei.stylus.penengine.eink.model;

import android.graphics.RectF;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes9.dex */
public class StrokeInfo {
    public static final String TAG = "StrokeInfo";
    public RectF mBound;
    public int mBrushColor;
    public int mBrushType;
    public float mBrushWidth;
    public List<StrokePoint> mPoints;

    public StrokeInfo(int i, float f, int i2, List<StrokePoint> list, RectF rectF) {
        this.mPoints = new ArrayList();
        this.mBrushType = i;
        this.mBrushWidth = f;
        this.mBrushColor = i2;
        this.mPoints = list;
        this.mBound = rectF;
    }

    public RectF getBound() {
        return this.mBound;
    }

    public int getBrushColor() {
        return this.mBrushColor;
    }

    public int getBrushType() {
        return this.mBrushType;
    }

    public float getBrushWidth() {
        return this.mBrushWidth;
    }

    public List<StrokePoint> getStrokePoints() {
        return this.mPoints;
    }
}
