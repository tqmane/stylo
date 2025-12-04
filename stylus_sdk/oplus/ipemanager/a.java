package com.oplus.ipemanager.sdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import androidx.appsearch.app.e;
import com.oplus.ipemanager.sdk.ISDKAidlCallback;
import com.oplus.ipemanager.sdk.ISdkAidlInterface;
import java.util.ArrayList;
import java.util.List;

/* compiled from: PencilManager.java */
/* loaded from: classes3.dex */
public final class a {
    public static final a h = new a();

    /* renamed from: a, reason: collision with root package name */
    public d f3914a;
    public ISdkAidlInterface b;
    public Context d;
    public final C0262a c = new C0262a();
    public boolean e = false;
    public final b f = new b();
    public final c g = new c();

    /* compiled from: PencilManager.java */
    /* renamed from: com.oplus.ipemanager.sdk.a$a, reason: collision with other inner class name */
    public class C0262a implements IBinder.DeathRecipient {
        public C0262a() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            Log.e("PencilManager", "ISdkAidlInterface dead!!!");
            a.this.b = null;
        }
    }

    /* compiled from: PencilManager.java */
    public class b implements ServiceConnection {
        public b() {
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) throws RemoteException {
            Log.d("PencilManager", "onServiceConnected");
            a aVar = a.this;
            aVar.e = true;
            ISdkAidlInterface iSdkAidlInterfaceAsInterface = ISdkAidlInterface.Stub.asInterface(iBinder);
            aVar.b = iSdkAidlInterfaceAsInterface;
            try {
                iSdkAidlInterfaceAsInterface.asBinder().linkToDeath(aVar.c, 0);
                aVar.b.setISdkAidlCallback(aVar.g);
            } catch (RemoteException e) {
                Log.e("PencilManager", "onServiceConnected, setISdkAidlCallback error = " + e);
            }
            d dVar = aVar.f3914a;
            if (dVar != null) {
                dVar.onInitSuccess();
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            Log.e("PencilManager", "onServiceDisconnected");
            a aVar = a.this;
            aVar.e = false;
            aVar.b = null;
            d dVar = aVar.f3914a;
            if (dVar != null) {
                dVar.onReleased(-1);
            }
        }
    }

    /* compiled from: PencilManager.java */
    public class c extends ISDKAidlCallback.Stub {
        public c() {
        }

        @Override // com.oplus.ipemanager.sdk.ISDKAidlCallback
        public final void onConnectionChanged(int i) {
            e.v("onConnectionChanged  state = ", i, "PencilManager");
            d dVar = a.this.f3914a;
            if (dVar != null) {
                dVar.onConnectionChanged(i);
            }
        }

        @Override // com.oplus.ipemanager.sdk.ISDKAidlCallback
        public final void onDemoModeEnableChange(boolean z) {
            com.coui.appcompat.card.c.A("onDemoModeEnableChange, enable = ", z, "PencilManager");
            d dVar = a.this.f3914a;
            if (dVar != null) {
                dVar.onDemoModeEnableChange(z);
            }
        }

        @Override // com.oplus.ipemanager.sdk.ISDKAidlCallback
        public final void onFunctionFeedbackStateChange(boolean z) {
            com.coui.appcompat.card.c.A("onFunctionFeedbackStateChange, enable = ", z, "PencilManager");
            d dVar = a.this.f3914a;
            if (dVar != null) {
                dVar.onFunctionFeedbackStateChange(z);
            }
        }

        @Override // com.oplus.ipemanager.sdk.ISDKAidlCallback
        public final void onVibrationSwitchStateChange(boolean z) {
            com.coui.appcompat.card.c.A("onVibrationSwitchStateChange  isOpen = ", z, "PencilManager");
            d dVar = a.this.f3914a;
            if (dVar != null) {
                dVar.onVibrationSwitchStateChange(z);
            }
        }
    }

    /* compiled from: PencilManager.java */
    public interface d {
        void onConnectionChanged(int i);

        void onDemoModeEnableChange(boolean z);

        void onFunctionFeedbackStateChange(boolean z);

        void onInitSuccess();

        void onReleased(int i);

        void onVibrationSwitchStateChange(boolean z);
    }

    public final List<IpeFeature> a() {
        ISdkAidlInterface iSdkAidlInterface = this.b;
        if (iSdkAidlInterface == null) {
            Log.e("PencilManager", "getSupportFeatures error, interface is null");
            return new ArrayList();
        }
        try {
            return iSdkAidlInterface.getSupportFeatures();
        } catch (RemoteException e) {
            Log.e("PencilManager", "getSupportFeatures error = " + e);
            return null;
        }
    }

    public final void b(Vibration vibration) {
        Log.d("PencilManager", "setVibrationType " + vibration);
        if (this.b == null) {
            return;
        }
        try {
            int iOrdinal = vibration.ordinal();
            if (iOrdinal == 0) {
                this.b.setVibrationType(0);
            } else if (iOrdinal == 1) {
                this.b.setVibrationType(1);
            } else if (iOrdinal == 2) {
                this.b.setVibrationType(2);
            } else if (iOrdinal == 3) {
                this.b.setVibrationType(3);
            }
        } catch (RemoteException e) {
            Log.e("PencilManager", "setVibrationType, error = " + e);
        }
    }
}
