package com.vivo.penalgoengine.entity;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class TouchPoint implements Parcelable {
    public static final Parcelable.Creator<TouchPoint> CREATOR = new a();
    public int n;
    public int o;
    public float p;
    public float q;
    public float r;
    public long s;

    public class a implements Parcelable.Creator<TouchPoint> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TouchPoint createFromParcel(Parcel parcel) {
            return new TouchPoint(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TouchPoint[] newArray(int i) {
            return new TouchPoint[i];
        }
    }

    public TouchPoint() {
        this.o = 1;
    }

    public int a() {
        return this.n;
    }

    public void b(int i) {
        this.o = i;
    }

    public void d(float f) {
        this.r = f;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void e(float f) {
        this.p = f;
    }

    public void f(float f) {
        this.q = f;
    }

    public int getType() {
        return this.o;
    }

    public float h() {
        return this.p;
    }

    public float i() {
        return this.q;
    }

    public String toString() {
        return "TouchPoint{action=" + this.n + ", type=" + this.o + ", x=" + this.p + ", y=" + this.q + ", pressure=" + this.r + ", time=" + this.s + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.n);
        parcel.writeInt(this.o);
        parcel.writeFloat(this.p);
        parcel.writeFloat(this.q);
        parcel.writeFloat(this.r);
        parcel.writeLong(this.s);
    }

    public void a(int i) {
        this.n = i;
    }

    public float b() {
        return this.r;
    }

    public long e() {
        return this.s;
    }

    public TouchPoint(Parcel parcel) {
        this.o = 1;
        this.n = parcel.readInt();
        this.o = parcel.readInt();
        this.p = parcel.readFloat();
        this.q = parcel.readFloat();
        this.r = parcel.readFloat();
        this.s = parcel.readLong();
    }

    public void a(long j) {
        this.s = j;
    }

    public TouchPoint(int i, int i2, float f, float f2, float f3, long j) {
        this.o = 1;
        this.n = i;
        this.o = i2;
        this.p = f;
        this.q = f2;
        this.r = f3;
        this.s = j;
    }
}
