package vendor.oplus.hardware.oplusvibrator;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import vendor.aac.hardware.richtap.vibrator.IRichtapCallback;

/* loaded from: classes2.dex */
public interface IOplusVibrator extends IInterface {
    public static final String DESCRIPTOR = "vendor$oplus$hardware$oplusvibrator$IOplusVibrator".replace('$', '.');
    public static final String HASH = "b103e6d812aa472b9c5b7deefd7986fa729ce1a8";
    public static final int VERSION = 2;

    int[] getCalibrateResults() throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    int getStatus() throws RemoteException;

    byte[] getSupportFeatures() throws RemoteException;

    int getVibratorTouchStyle() throws RemoteException;

    void init(IRichtapCallback iRichtapCallback) throws RemoteException;

    void linearMotorVibratorOff() throws RemoteException;

    void linearMotorVibratorOn(int i, int i2, boolean z) throws RemoteException;

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

    void setMotorOldTest(int i) throws RemoteException;

    void setPowerOnVibratorSwitch(String str) throws RemoteException;

    void setVibratorTouchStyle(int i) throws RemoteException;

    void setVmax(int i) throws RemoteException;

    void startCalibrate() throws RemoteException;

    void stop(IRichtapCallback iRichtapCallback) throws RemoteException;

    void writeBulletNum(int i) throws RemoteException;

    void writeGunMode(int i) throws RemoteException;

    void writeGunType(int i) throws RemoteException;

    void writeHapticAudio(String str) throws RemoteException;

    public static class Default implements IOplusVibrator {
        @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
        public void init(IRichtapCallback callback) throws RemoteException {
        }

        @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
        public void setDynamicScale(int scale, IRichtapCallback callback) throws RemoteException {
        }

        @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
        public void setF0(int f0, IRichtapCallback callback) throws RemoteException {
        }

        @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
        public void stop(IRichtapCallback callback) throws RemoteException {
        }

        @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
        public void setAmplitude(int amplitude, IRichtapCallback callback) throws RemoteException {
        }

        @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
        public void performHeParam(int interval, int amplitude, int freq, IRichtapCallback callback) throws RemoteException {
        }

        @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
        public void off(IRichtapCallback callback) throws RemoteException {
        }

        @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
        public void on(int timeoutMs, IRichtapCallback callback) throws RemoteException {
        }

        @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
        public int perform(int effect_id, byte strength, IRichtapCallback callback) throws RemoteException {
            return 0;
        }

        @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
        public void performEnvelope(int[] envInfo, boolean fastFlag, IRichtapCallback callback) throws RemoteException {
        }

        @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
        public void performRtp(ParcelFileDescriptor hdl, IRichtapCallback callback) throws RemoteException {
        }

        @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
        public void performHe(int looper, int interval, int amplitude, int freq, int[] he, IRichtapCallback callback) throws RemoteException {
        }

        @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
        public void setHapticParam(int[] data, int length, IRichtapCallback callback) throws RemoteException {
        }

        @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
        public void linearMotorVibratorOff() throws RemoteException {
        }

        @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
        public void linearMotorVibratorOn(int waveform_id, int amplitude, boolean is_rtp_mode) throws RemoteException {
        }

        @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
        public void startCalibrate() throws RemoteException {
        }

        @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
        public int[] getCalibrateResults() throws RemoteException {
            return null;
        }

        @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
        public int getStatus() throws RemoteException {
            return 0;
        }

        @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
        public void setVmax(int vmax) throws RemoteException {
        }

        @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
        public void setMotorOldTest(int type) throws RemoteException {
        }

        @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
        public byte[] getSupportFeatures() throws RemoteException {
            return null;
        }

        @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
        public void writeGunType(int gunType) throws RemoteException {
        }

        @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
        public void writeGunMode(int gunMode) throws RemoteException {
        }

        @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
        public void writeBulletNum(int bulletNum) throws RemoteException {
        }

        @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
        public void writeHapticAudio(String buffer) throws RemoteException {
        }

        @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
        public void setVibratorTouchStyle(int touchStyle) throws RemoteException {
        }

        @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
        public int getVibratorTouchStyle() throws RemoteException {
            return 0;
        }

        @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
        public void setPowerOnVibratorSwitch(String data) throws RemoteException {
        }

        @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IOplusVibrator {
        static final int TRANSACTION_getCalibrateResults = 17;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getStatus = 18;
        static final int TRANSACTION_getSupportFeatures = 21;
        static final int TRANSACTION_getVibratorTouchStyle = 27;
        static final int TRANSACTION_init = 1;
        static final int TRANSACTION_linearMotorVibratorOff = 14;
        static final int TRANSACTION_linearMotorVibratorOn = 15;
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
        static final int TRANSACTION_setMotorOldTest = 20;
        static final int TRANSACTION_setPowerOnVibratorSwitch = 28;
        static final int TRANSACTION_setVibratorTouchStyle = 26;
        static final int TRANSACTION_setVmax = 19;
        static final int TRANSACTION_startCalibrate = 16;
        static final int TRANSACTION_stop = 4;
        static final int TRANSACTION_writeBulletNum = 24;
        static final int TRANSACTION_writeGunMode = 23;
        static final int TRANSACTION_writeGunType = 22;
        static final int TRANSACTION_writeHapticAudio = 25;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IOplusVibrator asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IOplusVibrator)) {
                return (IOplusVibrator) iin;
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
                case 14:
                    return "linearMotorVibratorOff";
                case 15:
                    return "linearMotorVibratorOn";
                case 16:
                    return "startCalibrate";
                case 17:
                    return "getCalibrateResults";
                case 18:
                    return "getStatus";
                case 19:
                    return "setVmax";
                case 20:
                    return "setMotorOldTest";
                case 21:
                    return "getSupportFeatures";
                case 22:
                    return "writeGunType";
                case 23:
                    return "writeGunMode";
                case 24:
                    return "writeBulletNum";
                case 25:
                    return "writeHapticAudio";
                case 26:
                    return "setVibratorTouchStyle";
                case 27:
                    return "getVibratorTouchStyle";
                case 28:
                    return "setPowerOnVibratorSwitch";
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
                case 14:
                    linearMotorVibratorOff();
                    reply.writeNoException();
                    return true;
                case 15:
                    int _arg014 = data.readInt();
                    int _arg111 = data.readInt();
                    boolean _arg26 = data.readBoolean();
                    data.enforceNoDataAvail();
                    linearMotorVibratorOn(_arg014, _arg111, _arg26);
                    reply.writeNoException();
                    return true;
                case 16:
                    startCalibrate();
                    reply.writeNoException();
                    return true;
                case 17:
                    int[] _result2 = getCalibrateResults();
                    reply.writeNoException();
                    reply.writeIntArray(_result2);
                    return true;
                case 18:
                    int _result3 = getStatus();
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 19:
                    int _arg015 = data.readInt();
                    data.enforceNoDataAvail();
                    setVmax(_arg015);
                    reply.writeNoException();
                    return true;
                case 20:
                    int _arg016 = data.readInt();
                    data.enforceNoDataAvail();
                    setMotorOldTest(_arg016);
                    reply.writeNoException();
                    return true;
                case 21:
                    byte[] _result4 = getSupportFeatures();
                    reply.writeNoException();
                    reply.writeByteArray(_result4);
                    return true;
                case 22:
                    int _arg017 = data.readInt();
                    data.enforceNoDataAvail();
                    writeGunType(_arg017);
                    reply.writeNoException();
                    return true;
                case 23:
                    int _arg018 = data.readInt();
                    data.enforceNoDataAvail();
                    writeGunMode(_arg018);
                    reply.writeNoException();
                    return true;
                case 24:
                    int _arg019 = data.readInt();
                    data.enforceNoDataAvail();
                    writeBulletNum(_arg019);
                    reply.writeNoException();
                    return true;
                case 25:
                    String _arg020 = data.readString();
                    data.enforceNoDataAvail();
                    writeHapticAudio(_arg020);
                    reply.writeNoException();
                    return true;
                case 26:
                    int _arg021 = data.readInt();
                    data.enforceNoDataAvail();
                    setVibratorTouchStyle(_arg021);
                    reply.writeNoException();
                    return true;
                case 27:
                    int _result5 = getVibratorTouchStyle();
                    reply.writeNoException();
                    reply.writeInt(_result5);
                    return true;
                case 28:
                    String _arg022 = data.readString();
                    data.enforceNoDataAvail();
                    setPowerOnVibratorSwitch(_arg022);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IOplusVibrator {
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

            @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
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

            @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
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

            @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
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

            @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
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

            @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
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

            @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
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

            @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
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

            @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
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

            @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
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

            @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
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

            @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
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

            @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
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

            @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
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

            @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
            public void linearMotorVibratorOff() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(14, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method linearMotorVibratorOff is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
            public void linearMotorVibratorOn(int waveform_id, int amplitude, boolean is_rtp_mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(waveform_id);
                    _data.writeInt(amplitude);
                    _data.writeBoolean(is_rtp_mode);
                    boolean _status = this.mRemote.transact(15, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method linearMotorVibratorOn is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
            public void startCalibrate() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(16, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method startCalibrate is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
            public int[] getCalibrateResults() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(17, _data, _reply, 0);
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

            @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
            public int getStatus() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(18, _data, _reply, 0);
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

            @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
            public void setVmax(int vmax) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(vmax);
                    boolean _status = this.mRemote.transact(19, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method setVmax is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
            public void setMotorOldTest(int type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    boolean _status = this.mRemote.transact(20, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method setMotorOldTest is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
            public byte[] getSupportFeatures() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(21, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method getSupportFeatures is unimplemented.");
                    }
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
            public void writeGunType(int gunType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(gunType);
                    boolean _status = this.mRemote.transact(22, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method writeGunType is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
            public void writeGunMode(int gunMode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(gunMode);
                    boolean _status = this.mRemote.transact(23, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method writeGunMode is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
            public void writeBulletNum(int bulletNum) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(bulletNum);
                    boolean _status = this.mRemote.transact(24, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method writeBulletNum is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
            public void writeHapticAudio(String buffer) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(buffer);
                    boolean _status = this.mRemote.transact(25, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method writeHapticAudio is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
            public void setVibratorTouchStyle(int touchStyle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(touchStyle);
                    boolean _status = this.mRemote.transact(26, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method setVibratorTouchStyle is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
            public int getVibratorTouchStyle() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(27, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method getVibratorTouchStyle is unimplemented.");
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
            public void setPowerOnVibratorSwitch(String data) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(data);
                    boolean _status = this.mRemote.transact(28, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method setPowerOnVibratorSwitch is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
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

            @Override // vendor.oplus.hardware.oplusvibrator.IOplusVibrator
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
