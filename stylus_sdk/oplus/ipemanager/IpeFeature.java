package com.oplus.ipemanager.sdk;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: IpeFeature.kt */
/* loaded from: classes3.dex */
public enum IpeFeature implements Parcelable {
    DEMO_MODE,
    FUNCTION_VIBRATION,
    FEEDBACK_VIBRATION;

    public static final a CREATOR = new a();

    /* compiled from: IpeFeature.kt */
    public static final class a implements Parcelable.Creator<IpeFeature> {
        @Override // android.os.Parcelable.Creator
        public final IpeFeature createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return IpeFeature.values()[parcel.readInt()];
        }

        @Override // android.os.Parcelable.Creator
        public final IpeFeature[] newArray(int i) {
            return new IpeFeature[i];
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        Intrinsics.checkNotNullParameter(parcel, "parcel");
        parcel.writeInt(ordinal());
    }

    IpeFeature(Parcel parcel) {
        this();
    }
}
