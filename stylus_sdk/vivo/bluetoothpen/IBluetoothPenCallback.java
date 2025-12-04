package com.vivo.bluetoothpen;

import android.bluetooth.BluetoothDevice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.vivo.bluetoothpen.OnPenBatteryListener;
import com.vivo.bluetoothpen.OnPenConnectListener;
import com.vivo.bluetoothpen.OnPenStateListener;
import com.vivo.bluetoothpen.OnScanListener;

/* loaded from: classes4.dex */
public interface IBluetoothPenCallback extends IInterface {

    public static abstract class Stub extends Binder implements IBluetoothPenCallback {
        public static final String DESCRIPTOR = "com.vivo.bluetoothpen.IBluetoothPenCallback";
        public static final int TRANSACTION_connectPen = 10;
        public static final int TRANSACTION_disconnectPen = 11;
        public static final int TRANSACTION_forceDisconnectPen = 12;
        public static final int TRANSACTION_getBatteryLevel = 22;
        public static final int TRANSACTION_getChargeState = 19;
        public static final int TRANSACTION_getConnectedPen = 32;
        public static final int TRANSACTION_getFirmwareVersion = 15;
        public static final int TRANSACTION_getMotorMode = 21;
        public static final int TRANSACTION_getPenName = 16;
        public static final int TRANSACTION_getPressThreshold = 23;
        public static final int TRANSACTION_getPressureLevel = 20;
        public static final int TRANSACTION_getReleaseThreshold = 24;
        public static final int TRANSACTION_getSnCode = 17;
        public static final int TRANSACTION_getSoftWareVersion = 14;
        public static final int TRANSACTION_getWorkMode = 18;
        public static final int TRANSACTION_hasPenBonded = 31;
        public static final int TRANSACTION_isCurrentPen = 13;
        public static final int TRANSACTION_isPenConnect = 30;
        public static final int TRANSACTION_registerConnectListener = 3;
        public static final int TRANSACTION_registerPenBatteryListener = 7;
        public static final int TRANSACTION_registerPenStateListener = 5;
        public static final int TRANSACTION_registerScanListener = 1;
        public static final int TRANSACTION_scanPen = 9;
        public static final int TRANSACTION_setFirmwareInstallState = 28;
        public static final int TRANSACTION_setIMUMode = 33;
        public static final int TRANSACTION_setMotorMode = 26;
        public static final int TRANSACTION_setPressureLevel = 25;
        public static final int TRANSACTION_setWorkMode = 27;
        public static final int TRANSACTION_setWritingVibrationIntensity = 34;
        public static final int TRANSACTION_unregisterConnectListener = 4;
        public static final int TRANSACTION_unregisterPenBatteryListener = 8;
        public static final int TRANSACTION_unregisterPenStateListener = 6;
        public static final int TRANSACTION_unregisterScanListener = 2;
        public static final int TRANSACTION_vibrate = 29;

        public static class Proxy implements IBluetoothPenCallback {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.vivo.bluetoothpen.IBluetoothPenCallback
            public void connectPen(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.vivo.bluetoothpen.IBluetoothPenCallback
            public void disconnectPen(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.vivo.bluetoothpen.IBluetoothPenCallback
            public void forceDisconnectPen(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.vivo.bluetoothpen.IBluetoothPenCallback
            public int getBatteryLevel() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.vivo.bluetoothpen.IBluetoothPenCallback
            public int getChargeState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.vivo.bluetoothpen.IBluetoothPenCallback
            public BluetoothDevice getConnectedPen() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? (BluetoothDevice) BluetoothDevice.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.vivo.bluetoothpen.IBluetoothPenCallback
            public String getFirmwareVersion() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.vivo.bluetoothpen.IBluetoothPenCallback
            public int getMotorMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.vivo.bluetoothpen.IBluetoothPenCallback
            public String getPenName() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.vivo.bluetoothpen.IBluetoothPenCallback
            public int getPressThreshold() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.vivo.bluetoothpen.IBluetoothPenCallback
            public int getPressureLevel() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.vivo.bluetoothpen.IBluetoothPenCallback
            public int getReleaseThreshold() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.vivo.bluetoothpen.IBluetoothPenCallback
            public long getSnCode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.vivo.bluetoothpen.IBluetoothPenCallback
            public String getSoftWareVersion() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.vivo.bluetoothpen.IBluetoothPenCallback
            public int getWorkMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.vivo.bluetoothpen.IBluetoothPenCallback
            public boolean hasPenBonded() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.vivo.bluetoothpen.IBluetoothPenCallback
            public boolean isCurrentPen(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.vivo.bluetoothpen.IBluetoothPenCallback
            public boolean isPenConnect(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.vivo.bluetoothpen.IBluetoothPenCallback
            public void registerConnectListener(OnPenConnectListener onPenConnectListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(onPenConnectListener != null ? onPenConnectListener.asBinder() : null);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.vivo.bluetoothpen.IBluetoothPenCallback
            public void registerPenBatteryListener(OnPenBatteryListener onPenBatteryListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(onPenBatteryListener != null ? onPenBatteryListener.asBinder() : null);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.vivo.bluetoothpen.IBluetoothPenCallback
            public void registerPenStateListener(OnPenStateListener onPenStateListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(onPenStateListener != null ? onPenStateListener.asBinder() : null);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.vivo.bluetoothpen.IBluetoothPenCallback
            public void registerScanListener(OnScanListener onScanListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(onScanListener != null ? onScanListener.asBinder() : null);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.vivo.bluetoothpen.IBluetoothPenCallback
            public void scanPen() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.vivo.bluetoothpen.IBluetoothPenCallback
            public void setFirmwareInstallState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.vivo.bluetoothpen.IBluetoothPenCallback
            public void setIMUMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.vivo.bluetoothpen.IBluetoothPenCallback
            public void setMotorMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.vivo.bluetoothpen.IBluetoothPenCallback
            public void setPressureLevel(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.vivo.bluetoothpen.IBluetoothPenCallback
            public void setWorkMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.vivo.bluetoothpen.IBluetoothPenCallback
            public void setWritingVibrationIntensity(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(34, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.vivo.bluetoothpen.IBluetoothPenCallback
            public void unregisterConnectListener(OnPenConnectListener onPenConnectListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(onPenConnectListener != null ? onPenConnectListener.asBinder() : null);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.vivo.bluetoothpen.IBluetoothPenCallback
            public void unregisterPenBatteryListener(OnPenBatteryListener onPenBatteryListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(onPenBatteryListener != null ? onPenBatteryListener.asBinder() : null);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.vivo.bluetoothpen.IBluetoothPenCallback
            public void unregisterPenStateListener(OnPenStateListener onPenStateListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(onPenStateListener != null ? onPenStateListener.asBinder() : null);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.vivo.bluetoothpen.IBluetoothPenCallback
            public void unregisterScanListener(OnScanListener onScanListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(onScanListener != null ? onScanListener.asBinder() : null);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.vivo.bluetoothpen.IBluetoothPenCallback
            public void vibrate(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBluetoothPenCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IBluetoothPenCallback)) ? new Proxy(iBinder) : (IBluetoothPenCallback) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    parcel.enforceInterface(DESCRIPTOR);
                    registerScanListener(OnScanListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    unregisterScanListener(OnScanListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    registerConnectListener(OnPenConnectListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 4:
                    parcel.enforceInterface(DESCRIPTOR);
                    unregisterConnectListener(OnPenConnectListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 5:
                    parcel.enforceInterface(DESCRIPTOR);
                    registerPenStateListener(OnPenStateListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 6:
                    parcel.enforceInterface(DESCRIPTOR);
                    unregisterPenStateListener(OnPenStateListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 7:
                    parcel.enforceInterface(DESCRIPTOR);
                    registerPenBatteryListener(OnPenBatteryListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 8:
                    parcel.enforceInterface(DESCRIPTOR);
                    unregisterPenBatteryListener(OnPenBatteryListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 9:
                    parcel.enforceInterface(DESCRIPTOR);
                    scanPen();
                    parcel2.writeNoException();
                    return true;
                case 10:
                    parcel.enforceInterface(DESCRIPTOR);
                    connectPen(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 11:
                    parcel.enforceInterface(DESCRIPTOR);
                    disconnectPen(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 12:
                    parcel.enforceInterface(DESCRIPTOR);
                    forceDisconnectPen(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 13:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean zIsCurrentPen = isCurrentPen(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsCurrentPen ? 1 : 0);
                    return true;
                case 14:
                    parcel.enforceInterface(DESCRIPTOR);
                    String softWareVersion = getSoftWareVersion();
                    parcel2.writeNoException();
                    parcel2.writeString(softWareVersion);
                    return true;
                case 15:
                    parcel.enforceInterface(DESCRIPTOR);
                    String firmwareVersion = getFirmwareVersion();
                    parcel2.writeNoException();
                    parcel2.writeString(firmwareVersion);
                    return true;
                case 16:
                    parcel.enforceInterface(DESCRIPTOR);
                    String penName = getPenName();
                    parcel2.writeNoException();
                    parcel2.writeString(penName);
                    return true;
                case 17:
                    parcel.enforceInterface(DESCRIPTOR);
                    long snCode = getSnCode();
                    parcel2.writeNoException();
                    parcel2.writeLong(snCode);
                    return true;
                case 18:
                    parcel.enforceInterface(DESCRIPTOR);
                    int workMode = getWorkMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(workMode);
                    return true;
                case 19:
                    parcel.enforceInterface(DESCRIPTOR);
                    int chargeState = getChargeState();
                    parcel2.writeNoException();
                    parcel2.writeInt(chargeState);
                    return true;
                case 20:
                    parcel.enforceInterface(DESCRIPTOR);
                    int pressureLevel = getPressureLevel();
                    parcel2.writeNoException();
                    parcel2.writeInt(pressureLevel);
                    return true;
                case 21:
                    parcel.enforceInterface(DESCRIPTOR);
                    int motorMode = getMotorMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(motorMode);
                    return true;
                case 22:
                    parcel.enforceInterface(DESCRIPTOR);
                    int batteryLevel = getBatteryLevel();
                    parcel2.writeNoException();
                    parcel2.writeInt(batteryLevel);
                    return true;
                case 23:
                    parcel.enforceInterface(DESCRIPTOR);
                    int pressThreshold = getPressThreshold();
                    parcel2.writeNoException();
                    parcel2.writeInt(pressThreshold);
                    return true;
                case 24:
                    parcel.enforceInterface(DESCRIPTOR);
                    int releaseThreshold = getReleaseThreshold();
                    parcel2.writeNoException();
                    parcel2.writeInt(releaseThreshold);
                    return true;
                case 25:
                    parcel.enforceInterface(DESCRIPTOR);
                    setPressureLevel(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 26:
                    parcel.enforceInterface(DESCRIPTOR);
                    setMotorMode(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 27:
                    parcel.enforceInterface(DESCRIPTOR);
                    setWorkMode(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 28:
                    parcel.enforceInterface(DESCRIPTOR);
                    setFirmwareInstallState(parcel.readInt() != 0);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    parcel.enforceInterface(DESCRIPTOR);
                    vibrate(parcel.readInt(), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 30:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean zIsPenConnect = isPenConnect(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsPenConnect ? 1 : 0);
                    return true;
                case 31:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean zHasPenBonded = hasPenBonded();
                    parcel2.writeNoException();
                    parcel2.writeInt(zHasPenBonded ? 1 : 0);
                    return true;
                case 32:
                    parcel.enforceInterface(DESCRIPTOR);
                    BluetoothDevice connectedPen = getConnectedPen();
                    parcel2.writeNoException();
                    if (connectedPen != null) {
                        parcel2.writeInt(1);
                        connectedPen.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 33:
                    parcel.enforceInterface(DESCRIPTOR);
                    setIMUMode(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 34:
                    parcel.enforceInterface(DESCRIPTOR);
                    setWritingVibrationIntensity(parcel.readInt());
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void connectPen(String str) throws RemoteException;

    void disconnectPen(String str) throws RemoteException;

    void forceDisconnectPen(String str) throws RemoteException;

    int getBatteryLevel() throws RemoteException;

    int getChargeState() throws RemoteException;

    BluetoothDevice getConnectedPen() throws RemoteException;

    String getFirmwareVersion() throws RemoteException;

    int getMotorMode() throws RemoteException;

    String getPenName() throws RemoteException;

    int getPressThreshold() throws RemoteException;

    int getPressureLevel() throws RemoteException;

    int getReleaseThreshold() throws RemoteException;

    long getSnCode() throws RemoteException;

    String getSoftWareVersion() throws RemoteException;

    int getWorkMode() throws RemoteException;

    boolean hasPenBonded() throws RemoteException;

    boolean isCurrentPen(String str) throws RemoteException;

    boolean isPenConnect(String str) throws RemoteException;

    void registerConnectListener(OnPenConnectListener onPenConnectListener) throws RemoteException;

    void registerPenBatteryListener(OnPenBatteryListener onPenBatteryListener) throws RemoteException;

    void registerPenStateListener(OnPenStateListener onPenStateListener) throws RemoteException;

    void registerScanListener(OnScanListener onScanListener) throws RemoteException;

    void scanPen() throws RemoteException;

    void setFirmwareInstallState(boolean z) throws RemoteException;

    void setIMUMode(int i) throws RemoteException;

    void setMotorMode(int i) throws RemoteException;

    void setPressureLevel(int i) throws RemoteException;

    void setWorkMode(int i) throws RemoteException;

    void setWritingVibrationIntensity(int i) throws RemoteException;

    void unregisterConnectListener(OnPenConnectListener onPenConnectListener) throws RemoteException;

    void unregisterPenBatteryListener(OnPenBatteryListener onPenBatteryListener) throws RemoteException;

    void unregisterPenStateListener(OnPenStateListener onPenStateListener) throws RemoteException;

    void unregisterScanListener(OnScanListener onScanListener) throws RemoteException;

    void vibrate(int i, int i2, int i3) throws RemoteException;
}
