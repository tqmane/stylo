package com.oplus.ipemanager.sdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.Log;
import com.oplus.ipemanager.sdk.ISystemAidlInterface;

/* loaded from: classes2.dex */
public class PencilManager {
    private static final int MAX_COUNT = 3;
    private static final int MAX_SET_MODE_TRY_COUNT = 1;
    private static final long MODE_SET_WAIT_TIME = 2000;
    private static final String TAG = "PencilManager";
    private static final int VIBRATION = 5;
    private static final long WAIT_TIME = 20000;
    private static PencilManager sInstance;
    private Context mContext;
    private ISystemAidlInterface mSystemAidlInterface;
    private static final ArrayMap<Integer, PencilManager> PENCIL_MANAGER_INSTANCES_CACHE_FOR_DIFF_USER = new ArrayMap<>();
    private static final ArrayMap<Integer, Context> CONTEXTS_CACHE_FOR_DIFF_USER = new ArrayMap<>();
    private static int sTryCount = 0;
    private Handler mHandler = new Handler();
    private ConnectionRunnable mConnectionRunnable = new ConnectionRunnable(false);
    private ConnectionRunnable mInitConnectionRunnable = new ConnectionRunnable(true);
    private int mSetModeTryCount = 0;
    private PencilConnection mPencilConnection = new PencilConnection();

    private PencilManager(Context context) {
        this.mContext = context;
        connectPencilService(true);
    }

    private static Context getUserContext(Context context, int userId) {
        Context userContext = CONTEXTS_CACHE_FOR_DIFF_USER.get(Integer.valueOf(userId));
        if (userContext == null) {
            UserHandle user = new UserHandle(userId);
            Context userContext2 = context.createContextAsUser(user, 0);
            CONTEXTS_CACHE_FOR_DIFF_USER.put(Integer.valueOf(userId), userContext2);
            return userContext2;
        }
        return userContext;
    }

    public static PencilManager getInstance(Context context, int userId) {
        PencilManager pencilManager;
        synchronized (PencilManager.class) {
            sInstance = PENCIL_MANAGER_INSTANCES_CACHE_FOR_DIFF_USER.get(Integer.valueOf(userId));
            if (sInstance == null) {
                Log.d(TAG, "getInstance for user " + userId);
                Context currentContext = getUserContext(context, userId);
                sInstance = new PencilManager(currentContext);
                PENCIL_MANAGER_INSTANCES_CACHE_FOR_DIFF_USER.put(Integer.valueOf(userId), sInstance);
            }
            pencilManager = sInstance;
        }
        return pencilManager;
    }

    private class ConnectionRunnable implements Runnable {
        boolean mInit;

        public ConnectionRunnable(boolean fromInit) {
            this.mInit = fromInit;
        }

        @Override // java.lang.Runnable
        public void run() {
            PencilManager.this.connectPencilService(this.mInit);
        }
    }

    private class PencilConnection implements ServiceConnection {
        private PencilConnection() {
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName component, IBinder binder) {
            Log.w(PencilManager.TAG, "onServiceConnected");
            PencilManager.this.mSystemAidlInterface = ISystemAidlInterface.Stub.asInterface(binder);
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName component) {
            Log.w(PencilManager.TAG, "onServiceDisconnected: " + component);
            PencilManager.this.mSystemAidlInterface = null;
        }

        @Override // android.content.ServiceConnection
        public final void onBindingDied(ComponentName component) {
            Log.w(PencilManager.TAG, "onBindingDied: " + component);
            PencilManager.this.mSystemAidlInterface = null;
        }

        @Override // android.content.ServiceConnection
        public final void onNullBinding(ComponentName component) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectPencilService(boolean isInit) {
        if (this.mSystemAidlInterface != null && this.mSystemAidlInterface.asBinder() != null && this.mSystemAidlInterface.asBinder().isBinderAlive()) {
            return;
        }
        Intent intent = new Intent("com.oplus.ipemanager.ACTION.PENCIL_SYSTEM");
        intent.setPackage("com.oplus.ipemanager");
        boolean isBinded = this.mContext.bindService(intent, this.mPencilConnection, 1);
        if (!isBinded) {
            if (isInit && sTryCount < 3) {
                if (this.mInitConnectionRunnable == null) {
                    this.mInitConnectionRunnable = new ConnectionRunnable(true);
                }
                if (!this.mHandler.hasCallbacks(this.mInitConnectionRunnable)) {
                    this.mHandler.postDelayed(this.mInitConnectionRunnable, WAIT_TIME);
                    sTryCount++;
                    return;
                }
                return;
            }
            return;
        }
        Log.w(TAG, "connectPencilService success!");
    }

    public void startVibration() {
        if (this.mSystemAidlInterface != null) {
            try {
                Log.d(TAG, "startVibration");
                this.mSystemAidlInterface.startVibration(5);
                return;
            } catch (RemoteException e) {
                Log.e(TAG, "startVibration failed as RemoteException");
                return;
            }
        }
        Log.e(TAG, "SystemAidlInterface is null");
        if (this.mConnectionRunnable == null) {
            this.mConnectionRunnable = new ConnectionRunnable(false);
        }
        if (!this.mHandler.hasCallbacks(this.mConnectionRunnable)) {
            this.mHandler.postAtFrontOfQueue(this.mConnectionRunnable);
        }
    }

    /* renamed from: setLaserMode, reason: merged with bridge method [inline-methods] */
    public void lambda$setLaserMode$0(final int mode) {
        if (this.mSystemAidlInterface != null) {
            try {
                Log.d(TAG, "setLaserMode mode = " + mode);
                this.mSystemAidlInterface.setLaserMode(mode);
                this.mSetModeTryCount = 0;
                return;
            } catch (RemoteException e) {
                Log.e(TAG, "setLaserMode failed as RemoteException");
                return;
            }
        }
        Log.e(TAG, "[setLaserMode]SystemAidlInterface is null");
        if (this.mConnectionRunnable == null) {
            this.mConnectionRunnable = new ConnectionRunnable(false);
        }
        if (!this.mHandler.hasCallbacks(this.mConnectionRunnable)) {
            this.mHandler.postAtFrontOfQueue(this.mConnectionRunnable);
        }
        if (this.mSetModeTryCount < 1) {
            this.mSetModeTryCount++;
            this.mHandler.postDelayed(new Runnable() { // from class: com.oplus.ipemanager.sdk.PencilManager$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setLaserMode$0(mode);
                }
            }, 2000L);
        }
    }

    public void onUserRemoved(int userId) {
        synchronized (PencilManager.class) {
            PENCIL_MANAGER_INSTANCES_CACHE_FOR_DIFF_USER.remove(Integer.valueOf(userId));
            CONTEXTS_CACHE_FOR_DIFF_USER.remove(Integer.valueOf(userId));
        }
    }
}
