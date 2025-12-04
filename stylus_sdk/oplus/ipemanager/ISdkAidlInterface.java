package com.oplus.ipemanager.sdk;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.oplus.ipemanager.sdk.ISDKAidlCallback;
import java.util.List;

/* loaded from: classes3.dex */
public interface ISdkAidlInterface extends IInterface {
    public static final String DESCRIPTOR = "com.oplus.ipemanager.sdk.ISdkAidlInterface";

    int getPencilConnectState();

    int getSuportSdkVersion();

    List<IpeFeature> getSupportFeatures();

    boolean getVibrationSwitchState();

    boolean isDemoModeEnable();

    boolean isFunctionVibrationEnable();

    void setISdkAidlCallback(ISDKAidlCallback iSDKAidlCallback);

    void setVibrationType(int i);

    void startVibration(int i);

    void unsetISdkAidlCallback(ISDKAidlCallback iSDKAidlCallback);

    public static abstract class Stub extends Binder implements ISdkAidlInterface {
        static final int TRANSACTION_getPencilConnectState = 3;
        static final int TRANSACTION_getSuportSdkVersion = 6;
        static final int TRANSACTION_getSupportFeatures = 7;
        static final int TRANSACTION_getVibrationSwitchState = 2;
        static final int TRANSACTION_isDemoModeEnable = 8;
        static final int TRANSACTION_isFunctionVibrationEnable = 9;
        static final int TRANSACTION_setISdkAidlCallback = 1;
        static final int TRANSACTION_setVibrationType = 4;
        static final int TRANSACTION_startVibration = 5;
        static final int TRANSACTION_unsetISdkAidlCallback = 10;

        public static class a implements ISdkAidlInterface {

            /* renamed from: a, reason: collision with root package name */
            public IBinder f3912a;

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.f3912a;
            }

            @Override // com.oplus.ipemanager.sdk.ISdkAidlInterface
            public final int getPencilConnectState() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISdkAidlInterface.DESCRIPTOR);
                    this.f3912a.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.oplus.ipemanager.sdk.ISdkAidlInterface
            public final int getSuportSdkVersion() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISdkAidlInterface.DESCRIPTOR);
                    this.f3912a.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.oplus.ipemanager.sdk.ISdkAidlInterface
            public final List<IpeFeature> getSupportFeatures() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISdkAidlInterface.DESCRIPTOR);
                    this.f3912a.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(IpeFeature.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.oplus.ipemanager.sdk.ISdkAidlInterface
            public final boolean getVibrationSwitchState() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISdkAidlInterface.DESCRIPTOR);
                    this.f3912a.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.oplus.ipemanager.sdk.ISdkAidlInterface
            public final boolean isDemoModeEnable() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISdkAidlInterface.DESCRIPTOR);
                    this.f3912a.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.oplus.ipemanager.sdk.ISdkAidlInterface
            public final boolean isFunctionVibrationEnable() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISdkAidlInterface.DESCRIPTOR);
                    this.f3912a.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.oplus.ipemanager.sdk.ISdkAidlInterface
            public final void setISdkAidlCallback(ISDKAidlCallback iSDKAidlCallback) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISdkAidlInterface.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSDKAidlCallback);
                    this.f3912a.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.oplus.ipemanager.sdk.ISdkAidlInterface
            public final void setVibrationType(int i) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISdkAidlInterface.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.f3912a.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.oplus.ipemanager.sdk.ISdkAidlInterface
            public final void startVibration(int i) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISdkAidlInterface.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.f3912a.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.oplus.ipemanager.sdk.ISdkAidlInterface
            public final void unsetISdkAidlCallback(ISDKAidlCallback iSDKAidlCallback) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISdkAidlInterface.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSDKAidlCallback);
                    this.f3912a.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, ISdkAidlInterface.DESCRIPTOR);
        }

        public static ISdkAidlInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISdkAidlInterface.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISdkAidlInterface)) {
                return (ISdkAidlInterface) iInterfaceQueryLocalInterface;
            }
            a aVar = new a();
            aVar.f3912a = iBinder;
            return aVar;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISdkAidlInterface.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISdkAidlInterface.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    setISdkAidlCallback(ISDKAidlCallback.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                case 2:
                    boolean vibrationSwitchState = getVibrationSwitchState();
                    parcel2.writeNoException();
                    parcel2.writeInt(vibrationSwitchState ? 1 : 0);
                    return true;
                case 3:
                    int pencilConnectState = getPencilConnectState();
                    parcel2.writeNoException();
                    parcel2.writeInt(pencilConnectState);
                    return true;
                case 4:
                    setVibrationType(parcel.readInt());
                    return true;
                case 5:
                    startVibration(parcel.readInt());
                    return true;
                case 6:
                    int suportSdkVersion = getSuportSdkVersion();
                    parcel2.writeNoException();
                    parcel2.writeInt(suportSdkVersion);
                    return true;
                case 7:
                    List<IpeFeature> supportFeatures = getSupportFeatures();
                    parcel2.writeNoException();
                    if (supportFeatures == null) {
                        parcel2.writeInt(-1);
                    } else {
                        int size = supportFeatures.size();
                        parcel2.writeInt(size);
                        for (int i3 = 0; i3 < size; i3++) {
                            parcel2.writeTypedObject(supportFeatures.get(i3), 1);
                        }
                    }
                    return true;
                case 8:
                    boolean zIsDemoModeEnable = isDemoModeEnable();
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsDemoModeEnable ? 1 : 0);
                    return true;
                case 9:
                    boolean zIsFunctionVibrationEnable = isFunctionVibrationEnable();
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsFunctionVibrationEnable ? 1 : 0);
                    return true;
                case 10:
                    unsetISdkAidlCallback(ISDKAidlCallback.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
