package com.oplus.ipemanager.sdk;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ISystemAidlInterface extends IInterface {
    public static final String DESCRIPTOR = "com.oplus.ipemanager.sdk.ISystemAidlInterface";

    void setLaserMode(int i) throws RemoteException;

    void startVibration(int i) throws RemoteException;

    public static class Default implements ISystemAidlInterface {
        @Override // com.oplus.ipemanager.sdk.ISystemAidlInterface
        public void startVibration(int type) throws RemoteException {
        }

        @Override // com.oplus.ipemanager.sdk.ISystemAidlInterface
        public void setLaserMode(int mode) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISystemAidlInterface {
        static final int TRANSACTION_setLaserMode = 2;
        static final int TRANSACTION_startVibration = 1;

        public Stub() {
            attachInterface(this, ISystemAidlInterface.DESCRIPTOR);
        }

        public static ISystemAidlInterface asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISystemAidlInterface.DESCRIPTOR);
            if (iin != null && (iin instanceof ISystemAidlInterface)) {
                return (ISystemAidlInterface) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "startVibration";
                case 2:
                    return "setLaserMode";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISystemAidlInterface.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISystemAidlInterface.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    startVibration(_arg0);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    setLaserMode(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISystemAidlInterface {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISystemAidlInterface.DESCRIPTOR;
            }

            @Override // com.oplus.ipemanager.sdk.ISystemAidlInterface
            public void startVibration(int type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISystemAidlInterface.DESCRIPTOR);
                    _data.writeInt(type);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.oplus.ipemanager.sdk.ISystemAidlInterface
            public void setLaserMode(int mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISystemAidlInterface.DESCRIPTOR);
                    _data.writeInt(mode);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        public int getMaxTransactionId() {
            return 1;
        }
    }
}
