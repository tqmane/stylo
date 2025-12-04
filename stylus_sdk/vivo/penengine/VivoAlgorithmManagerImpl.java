package com.vivo.penengine.impl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.PointF;
import android.os.Build;
import android.os.IBinder;
import android.view.MotionEvent;
import com.vivo.penalgoengine.IAlgoService;
import com.vivo.penalgoengine.entity.ShapeData;
import com.vivo.penalgoengine.entity.TouchPoint;
import com.vivo.trackpredictor.core.TrackPredCore;
import com.vivo.trackpredictor.utils.HwPoint;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public final class VivoAlgorithmManagerImpl {
    public static final String ACTION_ALGO_SERVICE = "com.vivo.penalgoengine.intent.ACTION_ALGO_SERVICE";
    public static final int RECOGNIZE_MODE_SHAPE = 1;
    public static final String STYLUS_ALGO_PKG_NAME = "com.vivo.penalgoengine";
    public static final String TAG = "VivoAlgorithmManagerImpl";
    public IAlgoService mAlgoService;
    public final Context mContext;
    public boolean mHasBond;
    public boolean mInitSuccess;
    public boolean mNeedRebind;
    public final ServiceConnection mServiceConnection = new ServiceConnection() { // from class: com.vivo.penengine.impl.VivoAlgorithmManagerImpl.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            VivoAlgorithmManagerImpl.this.mHasBond = true;
            try {
                VivoAlgorithmManagerImpl.this.mAlgoService = IAlgoService.a.a(iBinder);
                EngineLog.d(VivoAlgorithmManagerImpl.TAG, "onServiceConnected");
            } catch (Exception e) {
                EngineLog.e(VivoAlgorithmManagerImpl.TAG, "onServiceConnected error", e);
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            VivoAlgorithmManagerImpl.this.mAlgoService = null;
            VivoAlgorithmManagerImpl.this.mHasBond = false;
            EngineLog.d(VivoAlgorithmManagerImpl.TAG, "onServiceDisconnected");
            VivoAlgorithmManagerImpl.this.bindServiceIfNeed();
        }
    };
    public TrackPredCore mTrackPredCore;

    public VivoAlgorithmManagerImpl(Context context) throws Throwable {
        this.mContext = context;
        if ("vivo".equals(Build.BRAND) && isTablet()) {
            this.mTrackPredCore = TrackPredCore.getInstance();
            this.mTrackPredCore.init(context);
            this.mNeedRebind = true;
            bindServiceIfNeed();
            this.mInitSuccess = true;
        }
        EngineLog.d(TAG, "invoke init, result=" + this.mInitSuccess);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindServiceIfNeed() {
        if (!this.mNeedRebind || this.mHasBond) {
            return;
        }
        try {
            Intent intent = new Intent();
            intent.setAction(ACTION_ALGO_SERVICE);
            intent.setPackage(STYLUS_ALGO_PKG_NAME);
            this.mContext.bindService(intent, this.mServiceConnection, 1);
            EngineLog.d(TAG, "bindService");
        } catch (Exception e) {
            EngineLog.e(TAG, "bindService error", e);
        }
    }

    public PointF computeEstimatePoint(MotionEvent motionEvent) {
        if (motionEvent == null) {
            return null;
        }
        HwPoint hwPoint = new HwPoint();
        hwPoint.setActionCode(motionEvent.getAction());
        hwPoint.setPointX(motionEvent.getX());
        hwPoint.setPointY(motionEvent.getY());
        hwPoint.setPressure(motionEvent.getPressure());
        hwPoint.setPointTime((int) motionEvent.getEventTime());
        if (!isEstimateEnable()) {
            EngineLog.e(TAG, "failed to invoke computeEstimatePoint: estimate disable");
            return new PointF(hwPoint.getPointX(), hwPoint.getPointY());
        }
        EngineLog.d(TAG, "invoke computeEstimatePoint");
        ArrayList arrayList = new ArrayList();
        int historySize = motionEvent.getToolType(motionEvent.getActionIndex()) == 2 ? motionEvent.getHistorySize() : motionEvent.getHistorySize() - 1;
        for (int i = 0; i < historySize && motionEvent.getAction() != 1; i++) {
            float historicalX = motionEvent.getHistoricalX(i);
            float historicalY = motionEvent.getHistoricalY(i);
            float historicalPressure = motionEvent.getHistoricalPressure(i);
            long historicalEventTime = motionEvent.getHistoricalEventTime(i);
            HwPoint hwPoint2 = new HwPoint();
            hwPoint2.setActionCode(motionEvent.getAction());
            hwPoint2.setPointX(historicalX);
            hwPoint2.setPointY(historicalY);
            hwPoint2.setPressure(historicalPressure);
            hwPoint2.setPointTime((int) historicalEventTime);
            arrayList.add(hwPoint2);
        }
        arrayList.add(hwPoint);
        HwPoint hwPointDoPredict = this.mTrackPredCore.doPredict(arrayList, motionEvent.getToolType(motionEvent.getActionIndex()));
        return new PointF(hwPointDoPredict.getPointX(), hwPointDoPredict.getPointY());
    }

    public ShapeData computeShapeData(List<TouchPoint> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        if (!isComputeShapeEnable()) {
            EngineLog.e(TAG, "failed to invoke computeEstimatePoint: compute disable");
            return null;
        }
        EngineLog.d(TAG, "invoke computeShapeData");
        try {
            return this.mAlgoService.computeShapeData(list, 1);
        } catch (Exception e) {
            EngineLog.e(TAG, "computeShapeData error", e);
            return null;
        }
    }

    public boolean isComputeShapeEnable() {
        return this.mInitSuccess && this.mAlgoService != null;
    }

    public boolean isEstimateEnable() {
        return this.mInitSuccess && this.mTrackPredCore != null;
    }

    public boolean isTablet() throws NoSuchMethodException, SecurityException {
        String str;
        try {
            Method declaredMethod = Class.forName("android.util.FtDeviceInfo").getDeclaredMethod("getDeviceType", new Class[0]);
            declaredMethod.setAccessible(true);
            str = (String) declaredMethod.invoke(null, new Object[0]);
        } catch (Exception unused) {
            EngineLog.e(TAG, "get device type error.");
            str = "phone";
        }
        EngineLog.d(TAG, "deviceType=" + str);
        return str.equals("tablet");
    }

    public void release() {
        this.mNeedRebind = false;
        try {
            if (this.mHasBond) {
                this.mContext.unbindService(this.mServiceConnection);
            }
        } catch (Exception e) {
            EngineLog.e(TAG, "unbind service error", e);
        }
        this.mHasBond = false;
        TrackPredCore trackPredCore = this.mTrackPredCore;
        if (trackPredCore != null) {
            trackPredCore.release();
        }
        this.mTrackPredCore = null;
        this.mInitSuccess = false;
        EngineLog.d(TAG, "invoke release");
    }
}
