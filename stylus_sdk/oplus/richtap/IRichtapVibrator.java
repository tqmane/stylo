package vendor.aac.hardware.richtap.vibrator;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import vendor.aac.hardware.richtap.vibrator.IRichtapCallback;

/* loaded from: classes2.dex */
public interface IRichtapVibrator extends IInterface {
    public static final String DESCRIPTOR = "vendor$aac$hardware$richtap$vibrator$IRichtapVibrator".replace('$', '.');
    public static final String HASH = "298dd3bb711fa1f23baaf23ba7ba03997fef4459";
    public static final int VERSION = 2;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void init(IRichtapCallback iRichtapCallback) throws RemoteException;

    void off(IRichtapCallback iRichtapCallback) throws RemoteException;

    void on(int i, IRichtapCallback iRichtapCallback) throws RemoteException;

    int perform(int i, byte b, IRichtapCallback iRichtapCallback) throws RemoteException;

    void performEnvelope(int[] iArr, boolean z, IRichtapCallback iRichtapCallback) throws RemoteException;

    void performHe(int i, int i2, int i3, int i4, int[] iArr, IRichtapCallback iRichtapCallback) throws RemoteException;

    void performHeParam(int i, int i2, int i3, IRichtapCallback iRichtapCallback) throws RemoteException;

    void performRtp(ParcelFileDescriptor parcelFileDescriptor, IRichtapCallback iRichtapCallback) throws RemoteException;

    void setAmplitude(int i, IRichtapCallback iRichtapCallback) throws RemoteException;

    void setDynamicScale(int i, IRichtapCallback iRichtapCallback) throws RemoteException;

    void setF0(int i, IRichtapCallback iRichtapCallback) throws RemoteException;

    void setHapticParam(int[] iArr, int i, IRichtapCallback iRichtapCallback) throws RemoteException;

    void stop(IRichtapCallback iRichtapCallback) throws RemoteException;

    public static class Default implements IRichtapVibrator {
        @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
        public void init(IRichtapCallback callback) throws RemoteException {
        }

        @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
        public void setDynamicScale(int scale, IRichtapCallback callback) throws RemoteException {
        }

        @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
        public void setF0(int f0, IRichtapCallback callback) throws RemoteException {
        }

        @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
        public void stop(IRichtapCallback callback) throws RemoteException {
        }

        @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
        public void setAmplitude(int amplitude, IRichtapCallback callback) throws RemoteException {
        }

        @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
        public void performHeParam(int interval, int amplitude, int freq, IRichtapCallback callback) throws RemoteException {
        }

        @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
        public void off(IRichtapCallback callback) throws RemoteException {
        }

        @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
        public void on(int timeoutMs, IRichtapCallback callback) throws RemoteException {
        }

        @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
        public int perform(int effect_id, byte strength, IRichtapCallback callback) throws RemoteException {
            return 0;
        }

        @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
        public void performEnvelope(int[] envInfo, boolean fastFlag, IRichtapCallback callback) throws RemoteException {
        }

        @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
        public void performRtp(ParcelFileDescriptor hdl, IRichtapCallback callback) throws RemoteException {
        }

        @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
        public void performHe(int looper, int interval, int amplitude, int freq, int[] he, IRichtapCallback callback) throws RemoteException {
        }

        @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
        public void setHapticParam(int[] data, int length, IRichtapCallback callback) throws RemoteException {
        }

        @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IRichtapVibrator {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_init = 1;
        static final int TRANSACTION_off = 7;
        static final int TRANSACTION_on = 8;
        static final int TRANSACTION_perform = 9;
        static final int TRANSACTION_performEnvelope = 10;
        static final int TRANSACTION_performHe = 12;
        static final int TRANSACTION_performHeParam = 6;
        static final int TRANSACTION_performRtp = 11;
        static final int TRANSACTION_setAmplitude = 5;
        static final int TRANSACTION_setDynamicScale = 2;
        static final int TRANSACTION_setF0 = 3;
        static final int TRANSACTION_setHapticParam = 13;
        static final int TRANSACTION_stop = 4;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IRichtapVibrator asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IRichtapVibrator)) {
                return (IRichtapVibrator) iin;
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
                    return "init";
                case 2:
                    return "setDynamicScale";
                case 3:
                    return "setF0";
                case 4:
                    return "stop";
                case 5:
                    return "setAmplitude";
                case 6:
                    return "performHeParam";
                case 7:
                    return "off";
                case 8:
                    return "on";
                case 9:
                    return "perform";
                case 10:
                    return "performEnvelope";
                case 11:
                    return "performRtp";
                case 12:
                    return "performHe";
                case 13:
                    return "setHapticParam";
                case TRANSACTION_getInterfaceHash /* 16777214 */:
                    return "getInterfaceHash";
                case 16777215:
                    return "getInterfaceVersion";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String descriptor = DESCRIPTOR;
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(descriptor);
            }
            if (code == 1598968902) {
                reply.writeString(descriptor);
                return true;
            }
            if (code == 16777215) {
                reply.writeNoException();
                reply.writeInt(getInterfaceVersion());
                return true;
            }
            if (code == TRANSACTION_getInterfaceHash) {
                reply.writeNoException();
                reply.writeString(getInterfaceHash());
                return true;
            }
            switch (code) {
                case 1:
                    IRichtapCallback _arg0 = IRichtapCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    init(_arg0);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    IRichtapCallback _arg1 = IRichtapCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setDynamicScale(_arg02, _arg1);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    IRichtapCallback _arg12 = IRichtapCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setF0(_arg03, _arg12);
                    return true;
                case 4:
                    IRichtapCallback _arg04 = IRichtapCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    stop(_arg04);
                    reply.writeNoException();
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    IRichtapCallback _arg13 = IRichtapCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setAmplitude(_arg05, _arg13);
                    return true;
                case 6:
                    int _arg06 = data.readInt();
                    int _arg14 = data.readInt();
                    int _arg2 = data.readInt();
                    IRichtapCallback _arg3 = IRichtapCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    performHeParam(_arg06, _arg14, _arg2, _arg3);
                    reply.writeNoException();
                    return true;
                case 7:
                    IRichtapCallback _arg07 = IRichtapCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    off(_arg07);
                    return true;
                case 8:
                    int _arg08 = data.readInt();
                    IRichtapCallback _arg15 = IRichtapCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    on(_arg08, _arg15);
                    return true;
                case 9:
                    int _arg09 = data.readInt();
                    byte _arg16 = data.readByte();
                    IRichtapCallback _arg22 = IRichtapCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    int _result = perform(_arg09, _arg16, _arg22);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 10:
                    int[] _arg010 = data.createIntArray();
                    boolean _arg17 = data.readBoolean();
                    IRichtapCallback _arg23 = IRichtapCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    performEnvelope(_arg010, _arg17, _arg23);
                    return true;
                case 11:
                    ParcelFileDescriptor _arg011 = (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    IRichtapCallback _arg18 = IRichtapCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    performRtp(_arg011, _arg18);
                    return true;
                case 12:
                    int _arg012 = data.readInt();
                    int _arg19 = data.readInt();
                    int _arg24 = data.readInt();
                    int _arg32 = data.readInt();
                    int[] _arg4 = data.createIntArray();
                    IRichtapCallback _arg5 = IRichtapCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    performHe(_arg012, _arg19, _arg24, _arg32, _arg4, _arg5);
                    return true;
                case 13:
                    int[] _arg013 = data.createIntArray();
                    int _arg110 = data.readInt();
                    IRichtapCallback _arg25 = IRichtapCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setHapticParam(_arg013, _arg110, _arg25);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRichtapVibrator {
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
            public void init(IRichtapCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method init is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
            public void setDynamicScale(int scale, IRichtapCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(scale);
                    _data.writeStrongInterface(callback);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setDynamicScale is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
            public void setF0(int f0, IRichtapCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(f0);
                    _data.writeStrongInterface(callback);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setF0 is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
            public void stop(IRichtapCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    boolean _status = this.mRemote.transact(4, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method stop is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
            public void setAmplitude(int amplitude, IRichtapCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(amplitude);
                    _data.writeStrongInterface(callback);
                    boolean _status = this.mRemote.transact(5, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setAmplitude is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
            public void performHeParam(int interval, int amplitude, int freq, IRichtapCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(interval);
                    _data.writeInt(amplitude);
                    _data.writeInt(freq);
                    _data.writeStrongInterface(callback);
                    boolean _status = this.mRemote.transact(6, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method performHeParam is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
            public void off(IRichtapCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    boolean _status = this.mRemote.transact(7, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method off is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
            public void on(int timeoutMs, IRichtapCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(timeoutMs);
                    _data.writeStrongInterface(callback);
                    boolean _status = this.mRemote.transact(8, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method on is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
            public int perform(int effect_id, byte strength, IRichtapCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(effect_id);
                    _data.writeByte(strength);
                    _data.writeStrongInterface(callback);
                    boolean _status = this.mRemote.transact(9, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method perform is unimplemented.");
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
            public void performEnvelope(int[] envInfo, boolean fastFlag, IRichtapCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeIntArray(envInfo);
                    _data.writeBoolean(fastFlag);
                    _data.writeStrongInterface(callback);
                    boolean _status = this.mRemote.transact(10, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method performEnvelope is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
            public void performRtp(ParcelFileDescriptor hdl, IRichtapCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(hdl, 0);
                    _data.writeStrongInterface(callback);
                    boolean _status = this.mRemote.transact(11, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method performRtp is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
            public void performHe(int looper, int interval, int amplitude, int freq, int[] he, IRichtapCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(looper);
                    _data.writeInt(interval);
                    _data.writeInt(amplitude);
                    _data.writeInt(freq);
                    _data.writeIntArray(he);
                    _data.writeStrongInterface(callback);
                    boolean _status = this.mRemote.transact(12, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method performHe is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
            public void setHapticParam(int[] data, int length, IRichtapCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeIntArray(data);
                    _data.writeInt(length);
                    _data.writeStrongInterface(callback);
                    boolean _status = this.mRemote.transact(13, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setHapticParam is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel data = Parcel.obtain(asBinder());
                    Parcel reply = Parcel.obtain();
                    try {
                        data.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, data, reply, 0);
                        reply.readException();
                        this.mCachedVersion = reply.readInt();
                    } finally {
                        reply.recycle();
                        data.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel data = Parcel.obtain(asBinder());
                    Parcel reply = Parcel.obtain();
                    try {
                        data.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(Stub.TRANSACTION_getInterfaceHash, data, reply, 0);
                        reply.readException();
                        this.mCachedHash = reply.readString();
                        reply.recycle();
                        data.recycle();
                    } catch (Throwable th) {
                        reply.recycle();
                        data.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }

        public int getMaxTransactionId() {
            return TRANSACTION_getInterfaceHash;
        }
    }
}
