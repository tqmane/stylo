package com.vivo.penalgoengine.entity;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/* loaded from: classes4.dex */
public class ShapeData implements Parcelable {
    public static final int A = 17;
    public static final int B = 18;
    public static final int C = 19;
    public static final Parcelable.Creator<ShapeData> CREATOR = new a();
    public static final int D = 20;
    public static final int E = 21;
    public static final int F = 22;
    public static final int G = 23;
    public static final int H = 24;
    public static final int I = 100;
    public static final int p = 6;
    public static final int q = 7;
    public static final int r = 8;
    public static final int s = 9;
    public static final int t = 10;
    public static final int u = 11;
    public static final int v = 12;
    public static final int w = 13;
    public static final int x = 14;
    public static final int y = 15;
    public static final int z = 16;
    public int n;
    public List<TouchPoint> o;

    public class a implements Parcelable.Creator<ShapeData> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ShapeData createFromParcel(Parcel parcel) {
            return new ShapeData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ShapeData[] newArray(int i) {
            return new ShapeData[i];
        }
    }

    public ShapeData() {
    }

    public void a(int i) {
        this.n = i;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getType() {
        return this.n;
    }

    public String toString() {
        return "ShapeData{type=" + this.n + ", touchPoints=" + this.o + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.n);
        parcel.writeTypedList(this.o);
    }

    public ShapeData(Parcel parcel) {
        this.n = parcel.readInt();
        this.o = parcel.createTypedArrayList(TouchPoint.CREATOR);
    }

    public List<TouchPoint> a() {
        return this.o;
    }

    public void a(List<TouchPoint> list) {
        this.o = list;
    }

    public ShapeData(int i, List<TouchPoint> list) {
        this.n = i;
        this.o = list;
    }
}
