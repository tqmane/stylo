package com.vivo.penalgoengine;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.vivo.penalgoengine.entity.ShapeData;
import com.vivo.penalgoengine.entity.TouchPoint;
import java.util.List;

/* loaded from: classes4.dex */
public interface IAlgoService extends IInterface {

    public static abstract class a extends Binder implements IAlgoService {
        public static final String n = "com.vivo.penalgoengine.IAlgoService";
        public static final int o = 1;

        /* renamed from: com.vivo.penalgoengine.IAlgoService$a$a, reason: collision with other inner class name */
        public static class C0557a implements IAlgoService {
            public IBinder n;

            public C0557a(IBinder iBinder) {
                this.n = iBinder;
            }

            public String a() {
                return a.n;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.n;
            }

            @Override // com.vivo.penalgoengine.IAlgoService
            public ShapeData computeShapeData(List<TouchPoint> list, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(a.n);
                    parcelObtain.writeTypedList(list);
                    parcelObtain.writeInt(i);
                    this.n.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? ShapeData.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public a() {
            attachInterface(this, n);
        }

        public static IAlgoService a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(n);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IAlgoService)) ? new C0557a(iBinder) : (IAlgoService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1) {
                if (i != 1598968902) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                parcel2.writeString(n);
                return true;
            }
            parcel.enforceInterface(n);
            ShapeData shapeDataComputeShapeData = computeShapeData(parcel.createTypedArrayList(TouchPoint.CREATOR), parcel.readInt());
            parcel2.writeNoException();
            if (shapeDataComputeShapeData != null) {
                parcel2.writeInt(1);
                shapeDataComputeShapeData.writeToParcel(parcel2, 1);
            } else {
                parcel2.writeInt(0);
            }
            return true;
        }
    }

    ShapeData computeShapeData(List<TouchPoint> list, int i) throws RemoteException;
}
