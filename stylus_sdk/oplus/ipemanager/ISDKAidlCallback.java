package com.oplus.ipemanager.sdk;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes3.dex */
public interface ISDKAidlCallback extends IInterface {
    public static final String DESCRIPTOR = "com.oplus.ipemanager.sdk.ISDKAidlCallback";

    void onConnectionChanged(int i);

    void onDemoModeEnableChange(boolean z);

    void onFunctionFeedbackStateChange(boolean z);

    void onVibrationSwitchStateChange(boolean z);

    public static abstract class Stub extends Binder implements ISDKAidlCallback {
        static final int TRANSACTION_onConnectionChanged = 1;
        static final int TRANSACTION_onDemoModeEnableChange = 4;
        static final int TRANSACTION_onFunctionFeedbackStateChange = 3;
        static final int TRANSACTION_onVibrationSwitchStateChange = 2;

        public static class a implements ISDKAidlCallback {

            /* renamed from: a, reason: collision with root package name */
            public IBinder f3911a;

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.f3911a;
            }

            @Override // com.oplus.ipemanager.sdk.ISDKAidlCallback
            public final void onConnectionChanged(int i) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISDKAidlCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.f3911a.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.oplus.ipemanager.sdk.ISDKAidlCallback
            public final void onDemoModeEnableChange(boolean z) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISDKAidlCallback.DESCRIPTOR);
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.f3911a.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.oplus.ipemanager.sdk.ISDKAidlCallback
            public final void onFunctionFeedbackStateChange(boolean z) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISDKAidlCallback.DESCRIPTOR);
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.f3911a.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.oplus.ipemanager.sdk.ISDKAidlCallback
            public final void onVibrationSwitchStateChange(boolean z) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISDKAidlCallback.DESCRIPTOR);
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.f3911a.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, ISDKAidlCallback.DESCRIPTOR);
        }

        public static ISDKAidlCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISDKAidlCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISDKAidlCallback)) {
                return (ISDKAidlCallback) iInterfaceQueryLocalInterface;
            }
            a aVar = new a();
            aVar.f3911a = iBinder;
            return aVar;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISDKAidlCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISDKAidlCallback.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                if (i == 2) {
                    onVibrationSwitchStateChange(parcel.readInt() != 0);
                } else if (i == 3) {
                    onFunctionFeedbackStateChange(parcel.readInt() != 0);
                } else {
                    if (i != 4) {
                        return super.onTransact(i, parcel, parcel2, i2);
                    }
                    onDemoModeEnableChange(parcel.readInt() != 0);
                }
            } else {
                onConnectionChanged(parcel.readInt());
            }
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
