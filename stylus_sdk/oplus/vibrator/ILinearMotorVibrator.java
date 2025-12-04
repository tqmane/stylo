package vendor.oplus.hardware.vibrator;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ILinearMotorVibrator extends IInterface {
    public static final int CAP_ALWAYS_ON_CONTROL = 64;
    public static final int CAP_AMPLITUDE_CONTROL = 4;
    public static final int CAP_COMPOSE_EFFECTS = 32;
    public static final int CAP_EXTERNAL_AMPLITUDE_CONTROL = 16;
    public static final int CAP_EXTERNAL_CONTROL = 8;
    public static final int CAP_ON_CALLBACK = 1;
    public static final int CAP_PERFORM_CALLBACK = 2;
    public static final String DESCRIPTOR = "vendor$oplus$hardware$vibrator$ILinearMotorVibrator".replace('$', '.');
    public static final String HASH = "1f5cfc391948481e22443680e6d15507c7cfe3e0";
    public static final int STATUS_DEINIT = 4096;
    public static final int STATUS_INIT = 1;
    public static final int STATUS_RUNNING = 16;
    public static final int STATUS_STOPPED = 0;
    public static final int VERSION = 1;

    int[] getCalibrateResults() throws RemoteException;

    int getCapabilities() throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    int getStatus() throws RemoteException;

    void linearmotorVibratorOff() throws RemoteException;

    void linearmotorVibratorOn(int i, int i2, boolean z) throws RemoteException;

    void setMotorOldTest(int i) throws RemoteException;

    void setVmax(int i) throws RemoteException;

    void startCalibrate() throws RemoteException;

    public static class Default implements ILinearMotorVibrator {
        @Override // vendor.oplus.hardware.vibrator.ILinearMotorVibrator
        public int getCapabilities() throws RemoteException {
            return 0;
        }

        @Override // vendor.oplus.hardware.vibrator.ILinearMotorVibrator
        public void linearmotorVibratorOff() throws RemoteException {
        }

        @Override // vendor.oplus.hardware.vibrator.ILinearMotorVibrator
        public void linearmotorVibratorOn(int waveform_id, int amplitude, boolean is_rtp_mode) throws RemoteException {
        }

        @Override // vendor.oplus.hardware.vibrator.ILinearMotorVibrator
        public void startCalibrate() throws RemoteException {
        }

        @Override // vendor.oplus.hardware.vibrator.ILinearMotorVibrator
        public int[] getCalibrateResults() throws RemoteException {
            return null;
        }

        @Override // vendor.oplus.hardware.vibrator.ILinearMotorVibrator
        public int getStatus() throws RemoteException {
            return 0;
        }

        @Override // vendor.oplus.hardware.vibrator.ILinearMotorVibrator
        public void setVmax(int vmax) throws RemoteException {
        }

        @Override // vendor.oplus.hardware.vibrator.ILinearMotorVibrator
        public void setMotorOldTest(int type) throws RemoteException {
        }

        @Override // vendor.oplus.hardware.vibrator.ILinearMotorVibrator
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.oplus.hardware.vibrator.ILinearMotorVibrator
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ILinearMotorVibrator {
        static final int TRANSACTION_getCalibrateResults = 5;
        static final int TRANSACTION_getCapabilities = 1;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getStatus = 6;
        static final int TRANSACTION_linearmotorVibratorOff = 2;
        static final int TRANSACTION_linearmotorVibratorOn = 3;
        static final int TRANSACTION_setMotorOldTest = 8;
        static final int TRANSACTION_setVmax = 7;
        static final int TRANSACTION_startCalibrate = 4;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ILinearMotorVibrator asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ILinearMotorVibrator)) {
                return (ILinearMotorVibrator) iin;
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
                    return "getCapabilities";
                case 2:
                    return "linearmotorVibratorOff";
                case 3:
                    return "linearmotorVibratorOn";
                case 4:
                    return "startCalibrate";
                case 5:
                    return "getCalibrateResults";
                case 6:
                    return "getStatus";
                case 7:
                    return "setVmax";
                case 8:
                    return "setMotorOldTest";
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
                    int _result = getCapabilities();
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 2:
                    linearmotorVibratorOff();
                    reply.writeNoException();
                    return true;
                case 3:
                    int _arg0 = data.readInt();
                    int _arg1 = data.readInt();
                    boolean _arg2 = data.readBoolean();
                    data.enforceNoDataAvail();
                    linearmotorVibratorOn(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                case 4:
                    startCalibrate();
                    reply.writeNoException();
                    return true;
                case 5:
                    int[] _result2 = getCalibrateResults();
                    reply.writeNoException();
                    reply.writeIntArray(_result2);
                    return true;
                case 6:
                    int _result3 = getStatus();
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 7:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    setVmax(_arg02);
                    reply.writeNoException();
                    return true;
                case 8:
                    int _arg03 = data.readInt();
                    data.enforceNoDataAvail();
                    setMotorOldTest(_arg03);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ILinearMotorVibrator {
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

            @Override // vendor.oplus.hardware.vibrator.ILinearMotorVibrator
            public int getCapabilities() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method getCapabilities is unimplemented.");
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.oplus.hardware.vibrator.ILinearMotorVibrator
            public void linearmotorVibratorOff() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method linearmotorVibratorOff is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.oplus.hardware.vibrator.ILinearMotorVibrator
            public void linearmotorVibratorOn(int waveform_id, int amplitude, boolean is_rtp_mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(waveform_id);
                    _data.writeInt(amplitude);
                    _data.writeBoolean(is_rtp_mode);
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method linearmotorVibratorOn is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.oplus.hardware.vibrator.ILinearMotorVibrator
            public void startCalibrate() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(4, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method startCalibrate is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.oplus.hardware.vibrator.ILinearMotorVibrator
            public int[] getCalibrateResults() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(5, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method getCalibrateResults is unimplemented.");
                    }
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.oplus.hardware.vibrator.ILinearMotorVibrator
            public int getStatus() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(6, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method getStatus is unimplemented.");
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.oplus.hardware.vibrator.ILinearMotorVibrator
            public void setVmax(int vmax) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(vmax);
                    boolean _status = this.mRemote.transact(7, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method setVmax is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.oplus.hardware.vibrator.ILinearMotorVibrator
            public void setMotorOldTest(int type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    boolean _status = this.mRemote.transact(8, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method setMotorOldTest is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.oplus.hardware.vibrator.ILinearMotorVibrator
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

            @Override // vendor.oplus.hardware.vibrator.ILinearMotorVibrator
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
