package com.android.server.input;

import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.ContextImpl;
import android.app.UserSwitchObserver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayViewport;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IRemoteCallback;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import com.android.server.LocalServices;
import com.android.server.pm.UserManagerInternal;
import com.oplus.debug.InputLog;
import com.oplus.ipemanager.sdk.PencilManager;
import java.util.List;

/* loaded from: classes.dex */
public class OplusLaserPointerController implements OplusPointerControllerInterface {
    private static final int DEFAULT_DISPLAY = 0;
    private static final String EVENT_ID = "LaserPenUseTime";
    private static final String IPE_LASER_MODE = "ipe_laser_mode";
    private static final long KEY_DOWN_TIMEOUT = 240000;
    private static final String LASER_MODE = "MODE";
    private static final float MAX_PATH_LENGTH = 20000.0f;
    private static final int MODE_LASER_CURSORLINE = 1;
    private static final int MODE_LASER_POINT = 0;
    private static final int MSG_KEY_DOWN_TIMEOUT = 8;
    private static final int MSG_LASER_INIT = 7;
    private static final int MSG_LASER_REMOVE_WINDOW = 2;
    private static final int MSG_LASER_STATE_UPDATE = 1;
    private static final int MSG_LASER_VIBRATION_START = 4;
    private static final int MSG_NOTIFY_LASER_MODE_SWITCH = 5;
    private static final int MSG_PENCIL_MANAGER_INIT = 6;
    private static final int MSG_SET_LASER_STATE_BY_TOUCH = 3;
    private static final int PEN_CONNECT = 2;
    private static final int PEN_DISCONNECT = 0;
    private static final long POKE_USER_ACTIVITY_TIMEOUT = 2000;
    private static final int SCAN_CODE_DOUBLE_CLICK = 190;
    private static final int SCAN_CODE_LONG_PRESS = 189;
    private static final int SOURCE_LASER_PEN = 257;
    private static final String TAG = "OplusLaserPointerController";
    private static final long TIMEOUT_REMOVE_WINDOW_TIME = 120000;
    private static volatile OplusLaserPointerController sInstance;
    private final Context mContext;
    private int mCurrentUserId;
    private DisplayManager mDisplayManager;
    private DisplayViewport mDisplayViewport;
    private final Handler mHandler;
    private final HandlerThread mHandlerThread;
    private InputStatistic mInputStatistic;
    private ContentObserver mLaserStateObserver;
    private OplusLaserView mLaserView;
    private WindowManager.LayoutParams mLayoutParams;
    private PencilManager mPencilManager;
    private final PowerManager mPowerManager;
    private InputManagerService mService;
    private List<DisplayViewport> mTempViewports;
    private final WindowManager mWindowManger;
    private static final String DARK_MODE = "dark_mode_state";
    private static final Uri DARK_MODE_ENABLED_URI = Settings.Global.getUriFor(DARK_MODE);
    private static final String LASER_PEN_ENABLE = "ipe_setting_only_laser_enable";
    private static final Uri LASER_PEN_ENABLE_URI = Settings.Global.getUriFor(LASER_PEN_ENABLE);
    private static final String LASER_PEN_CONNECT_STATE = "ipe_pencil_connect_state";
    private static final Uri LASER_PEN_CONNECT_URI = Settings.Global.getUriFor(LASER_PEN_CONNECT_STATE);
    private final Object mLock = new Object();
    private final LaserState mLaserState = new LaserState();
    private final LaserState mViewLaserState = new LaserState();
    private final float mHalf = 2.0f;
    private final float mOne = 1.0f;
    private final DisplayManager.DisplayListener mDisplayListener = new DisplayManager.DisplayListener() { // from class: com.android.server.input.OplusLaserPointerController.1
        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int displayId) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int displayId) {
            synchronized (OplusLaserPointerController.this.mLock) {
                if (OplusLaserPointerController.this.mDisplayId != displayId) {
                    return;
                }
                int displayState = OplusLaserPointerController.this.mDisplayManager.getDisplay(displayId).getState();
                InputLog.v(OplusLaserPointerController.TAG, "onDisplayChanged displayState = " + displayState);
                OplusLaserPointerController.this.mDisplayState = displayState;
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int displayId) {
        }
    };
    private final UserManagerInternal.UserLifecycleListener mUserLifecycleListener = new UserManagerInternal.UserLifecycleListener() { // from class: com.android.server.input.OplusLaserPointerController.2
        public void onUserRemoved(UserInfo user) {
            Log.d(OplusLaserPointerController.TAG, "Removing user " + user.id);
            if (OplusLaserPointerController.this.mPencilManager != null) {
                OplusLaserPointerController.this.mPencilManager.onUserRemoved(user.id);
            }
        }
    };
    private final UserSwitchObserver mUserSwitchObserver = new UserSwitchObserver() { // from class: com.android.server.input.OplusLaserPointerController.3
        public void onUserSwitching(int newUserId, IRemoteCallback reply) throws RemoteException {
            try {
                Log.d(OplusLaserPointerController.TAG, "onUserSwitching, send MSG_LASER_REMOVE_WINDOW, newUserId=: " + newUserId + " mCurrentUserId=" + OplusLaserPointerController.this.mCurrentUserId);
                OplusLaserPointerController.this.mHandler.sendMessageAtFrontOfQueue(OplusLaserPointerController.this.mHandler.obtainMessage(2));
            } finally {
                if (reply != null) {
                    reply.sendResult((Bundle) null);
                }
            }
        }

        public void onUserSwitchComplete(int newUserId) {
            Log.d(OplusLaserPointerController.TAG, "onUserSwitchComplete newUserId= " + newUserId + " mCurrentUserId=" + OplusLaserPointerController.this.mCurrentUserId);
            OplusLaserPointerController.this.mCurrentUserId = newUserId;
            OplusLaserPointerController.this.switchPenConnectState();
        }
    };
    private final BroadcastReceiver mDataChangeReceiver = new AnonymousClass4();
    private PointF mPointF = new PointF(0.0f, 0.0f);
    private PointF mDetLtaPointF = new PointF(0.0f, 0.0f);
    private int mDisplayId = 0;
    private float mPointerX = 0.0f;
    private float mPointerY = 0.0f;
    private float mLastPointerX = 0.0f;
    private float mLastPointerY = 0.0f;
    private long mLastPokeUserActivityTime = -2000;
    private boolean mLaserSettingsState = false;
    private int mDisplayState = 2;
    private boolean mIsSwitchInit = false;
    private boolean mChange = false;
    private Long mStartTime = 0L;
    private Long mProcTime = 0L;

    /* renamed from: com.android.server.input.OplusLaserPointerController$4, reason: invalid class name */
    class AnonymousClass4 extends BroadcastReceiver {
        AnonymousClass4() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.intent.action.DATE_CHANGED".equals(action)) {
                Log.d(OplusLaserPointerController.TAG, "ACTION_DATE_CHANGED mProcTime= " + OplusLaserPointerController.this.mProcTime);
                if (OplusLaserPointerController.this.mProcTime.longValue() > 0) {
                    OplusLaserPointerController.this.mHandler.post(new Runnable() { // from class: com.android.server.input.OplusLaserPointerController$4$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onReceive$0();
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$0() {
            OplusLaserPointerController.this.uploadCollectData(OplusLaserPointerController.EVENT_ID, String.valueOf(OplusLaserPointerController.this.mProcTime));
        }
    }

    public OplusLaserPointerController() {
        this.mCurrentUserId = 0;
        Log.d(TAG, "new OplusLaserPointerController");
        ContextImpl systemContext = ActivityThread.currentActivityThread().getSystemContext();
        this.mContext = systemContext;
        this.mCurrentUserId = ActivityManager.getCurrentUser();
        this.mPowerManager = (PowerManager) systemContext.getSystemService(PowerManager.class);
        this.mWindowManger = (WindowManager) systemContext.getSystemService(WindowManager.class);
        this.mDisplayManager = (DisplayManager) systemContext.getSystemService(DisplayManager.class);
        HandlerThread handlerThread = new HandlerThread(TAG);
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandler = new LaserHandler(handlerThread.getLooper());
        this.mLaserStateObserver = new ContentObserver(this.mHandler) { // from class: com.android.server.input.OplusLaserPointerController.5
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange, Uri uri) {
                if (OplusLaserPointerController.LASER_PEN_ENABLE_URI.equals(uri)) {
                    OplusLaserPointerController.this.switchToLaser();
                } else if (OplusLaserPointerController.LASER_PEN_CONNECT_URI.equals(uri)) {
                    OplusLaserPointerController.this.switchPenConnectState();
                } else if (OplusLaserPointerController.DARK_MODE_ENABLED_URI.equals(uri)) {
                    OplusLaserPointerController.this.switchToDarkMode();
                }
            }
        };
        this.mContext.getContentResolver().registerContentObserver(LASER_PEN_ENABLE_URI, false, this.mLaserStateObserver, -1);
        this.mContext.getContentResolver().registerContentObserver(LASER_PEN_CONNECT_URI, false, this.mLaserStateObserver, -1);
        registerUserSwitchObserver();
        UserManagerInternal umi = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        umi.addUserLifecycleListener(this.mUserLifecycleListener);
        this.mInputStatistic = InputStatistic.getInstance(this.mContext);
        this.mContext.registerReceiver(this.mDataChangeReceiver, new IntentFilter("android.intent.action.DATE_CHANGED"), null, this.mHandler);
    }

    public static OplusLaserPointerController getInstance() {
        if (sInstance == null) {
            synchronized (OplusLaserPointerController.class) {
                if (sInstance == null) {
                    sInstance = new OplusLaserPointerController();
                }
            }
        }
        return sInstance;
    }

    void initService(InputManagerService base, List<DisplayViewport> viewports) {
        Log.d(TAG, "initService");
        this.mService = base;
        this.mTempViewports = viewports;
        Settings.Global.putInt(this.mContext.getContentResolver(), IPE_LASER_MODE, 0);
        if (!messagesExist(7)) {
            this.mHandler.sendEmptyMessage(7);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void switchPenConnectState() {
        int penConnectState = Settings.Global.getInt(this.mContext.getContentResolver(), LASER_PEN_CONNECT_STATE, 0);
        Log.d(TAG, "[switchPenConnectState] penConnectState = " + penConnectState + " mPencilManager = " + this.mPencilManager);
        if (penConnectState == 2 && !messagesExist(6)) {
            this.mHandler.sendEmptyMessage(6);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void switchToLaser() {
        boolean enableLaserSwitch = Settings.Global.getInt(this.mContext.getContentResolver(), LASER_PEN_ENABLE, 0) != 0;
        Log.d(TAG, "[switchToLaser] enableLaserSwitch = " + enableLaserSwitch);
        if (this.mService != null) {
            this.mService.setLaserEnabled(enableLaserSwitch);
        }
        changeLaserSwitch(enableLaserSwitch);
        if (enableLaserSwitch) {
            setDisplayViewPorts(this.mTempViewports);
            this.mDisplayManager.registerDisplayListener(this.mDisplayListener, this.mHandler);
            this.mContext.getContentResolver().registerContentObserver(DARK_MODE_ENABLED_URI, false, this.mLaserStateObserver, -1);
            return;
        }
        this.mDisplayManager.unregisterDisplayListener(this.mDisplayListener);
    }

    private void registerUserSwitchObserver() {
        try {
            ActivityManager.getService().registerUserSwitchObserver(this.mUserSwitchObserver, TAG);
        } catch (RemoteException e) {
            Log.e(TAG, "registerUserSwitchObserver failed. msg = " + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void switchToDarkMode() {
        boolean enableDarkModeSwitch = Settings.Global.getInt(this.mContext.getContentResolver(), DARK_MODE, 0) != 0;
        Log.d(TAG, "[switchToDarkMode] enableDarkModeSwitch = " + enableDarkModeSwitch);
        synchronized (this.mLock) {
            this.mLaserState.mDarkMode = enableDarkModeSwitch;
        }
        if (messagesExist(1)) {
            this.mHandler.removeMessages(1);
        }
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // com.android.server.input.OplusPointerControllerInterface
    public void setDisplayViewPorts(List<DisplayViewport> viewports) {
        synchronized (this.mLock) {
            DisplayViewport displayViewport = findDisplayViewportByIdLocked(viewports, this.mDisplayId);
            if (displayViewport == null) {
                Log.w(TAG, "Can't find the designated viewport with ID " + this.mDisplayId + " to update laser input mapper. Fall back to default display");
                displayViewport = findDisplayViewportByIdLocked(viewports, 0);
            }
            if (displayViewport == null) {
                Log.e(TAG, "Still can't find a viable viewport to update cursor input mapper. Skip setting it to OplusLaserPointerController.");
                return;
            }
            InputLog.v(TAG, "Display viewport updated, new display viewport = " + displayViewport);
            this.mDisplayViewport = displayViewport;
            if (this.mLaserView != null && this.mDisplayState == 2) {
                initLaserState(true, false);
            }
        }
    }

    @Override // com.android.server.input.OplusPointerControllerInterface
    public void move(float deltaX, float deltaY) {
        InputLog.d(TAG, "sendLaserDelta deltaX=" + deltaX + " deltaY=" + deltaY);
        if (this.mLaserSettingsState && this.mDisplayState == 2) {
            synchronized (this.mLock) {
                if (this.mLaserState.mMode == 0 && !this.mLaserState.mLongPress) {
                    InputLog.v(TAG, "[move]MODE_LASER_POINT and not mLongPress");
                    return;
                }
                this.mDetLtaPointF.set(deltaX, deltaY);
                transformLocked(this.mDetLtaPointF);
                setMovePosition(this.mPointerX + this.mDetLtaPointF.x, this.mPointerY + this.mDetLtaPointF.y);
            }
        }
    }

    void interceptNotifyMotion(int action, String toolType) {
        InputLog.d(TAG, "interceptNotifyMotion action: " + action + ", toolType: " + toolType);
        if (!messagesExist(3)) {
            this.mHandler.sendEmptyMessage(3);
        }
    }

    void fadePointer(int displayId) {
        if (this.mService != null) {
            InputLog.d(TAG, "fadePointer displayId=" + displayId);
            this.mService.fadePointer(displayId);
        }
    }

    void setNotifyEnable(boolean enabled) {
        if (this.mService != null) {
            this.mService.setNotifyEnable(enabled);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startVibration() {
        if (this.mPencilManager == null) {
            this.mPencilManager = PencilManager.getInstance(this.mContext, this.mCurrentUserId);
        }
        this.mPencilManager.startVibration();
    }

    private void notifyPencilLaserModeChange(int mode) {
        Message msg = Message.obtain(this.mHandler, 5);
        Bundle bundle = new Bundle();
        bundle.putInt(LASER_MODE, mode);
        msg.setData(bundle);
        if (messagesExist(5)) {
            this.mHandler.removeMessages(5);
        }
        this.mHandler.sendMessage(msg);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendModeToPencilManager(int mode) {
        InputLog.v(TAG, "[sendModeToPencilManager] mode = " + mode);
        if (this.mPencilManager == null) {
            this.mPencilManager = PencilManager.getInstance(this.mContext, this.mCurrentUserId);
        }
        this.mPencilManager.lambda$setLaserMode$0(mode);
    }

    private void transformLocked(PointF pointF) {
        switch (this.mDisplayViewport.orientation) {
            case 0:
                this.mDetLtaPointF.set(pointF.x, pointF.y);
                break;
            case 1:
                this.mDetLtaPointF.set(pointF.y, -pointF.x);
                break;
            case 2:
                this.mDetLtaPointF.set(-pointF.x, -pointF.y);
                break;
            case 3:
                this.mDetLtaPointF.set(-pointF.y, pointF.x);
                break;
        }
    }

    private void setMovePosition(float x, float y) {
        synchronized (this.mLock) {
            DisplayViewport displayViewport = this.mDisplayViewport;
            Rect logicalFrame = displayViewport.logicalFrame;
            this.mPointerX = Math.max(logicalFrame.left, Math.min(logicalFrame.right - 1.0f, x));
            this.mPointerY = Math.max(logicalFrame.top, Math.min(logicalFrame.bottom - 1.0f, y));
            updatePathListLocked(this.mPointerX, this.mPointerY);
            this.mLaserState.mLastPosition.set(this.mLastPointerX, this.mLastPointerY);
            this.mLastPointerX = this.mPointerX;
            this.mLastPointerY = this.mPointerY;
            this.mLaserState.mPosition.set(this.mPointerX, this.mPointerY);
        }
        if (messagesExist(1)) {
            this.mHandler.removeMessages(1);
        }
        this.mHandler.sendEmptyMessage(1);
    }

    private void updatePathListLocked(float x, float y) {
        InputLog.d(TAG, "updatePathListLocked mLongPress = " + this.mLaserState.mLongPress);
        if (this.mLaserState.mMode == 1) {
            if (this.mLaserState.mLongPress) {
                if (this.mLaserState.mPath.isEmpty()) {
                    if (this.mChange) {
                        InputLog.d(TAG, "moveto, mLastPointerX:" + this.mLastPointerX + ", mLastPointerY:" + this.mLastPointerY);
                        this.mLaserState.mPath.moveTo(this.mLastPointerX, this.mLastPointerY);
                        this.mLaserState.mPath.quadTo(this.mLastPointerX, this.mLastPointerY, (this.mLastPointerX + x) / 2.0f, (this.mLastPointerY + y) / 2.0f);
                        this.mLaserState.mDirty = true;
                        return;
                    }
                    this.mLaserState.mPath.moveTo(x, y);
                    return;
                }
                this.mLaserState.mPath.quadTo(this.mLastPointerX, this.mLastPointerY, (this.mLastPointerX + x) / 2.0f, (this.mLastPointerY + y) / 2.0f);
                InputLog.d(TAG, "endX:" + ((this.mLastPointerX + x) / 2.0f) + ", endY:" + ((this.mLastPointerY + y) / 2.0f));
                this.mLaserState.mDirty = true;
                return;
            }
            this.mLaserState.mPath.moveTo(x, y);
        }
    }

    private void resetPositionLocked() {
        if (this.mDisplayViewport != null) {
            DisplayViewport displayViewport = this.mDisplayViewport;
            Rect logicalFrame = displayViewport.logicalFrame;
            this.mPointerX = logicalFrame.width() / 2.0f;
            this.mPointerY = logicalFrame.height() / 2.0f;
            Log.d(TAG, "resetPositionLocked x=" + this.mPointerX + " y=" + this.mPointerY);
            this.mLaserState.mPosition.set(this.mPointerX, this.mPointerY);
        }
    }

    private DisplayViewport findDisplayViewportByIdLocked(List<DisplayViewport> displayViewports, int displayId) {
        for (DisplayViewport displayViewport : displayViewports) {
            if (displayViewport.displayId == displayId) {
                return displayViewport;
            }
        }
        return null;
    }

    private void pokeUserActivity() {
        long now = SystemClock.uptimeMillis();
        if (now - this.mLastPokeUserActivityTime < 2000) {
            return;
        }
        this.mLastPokeUserActivityTime = now;
        this.mPowerManager.userActivity(now, 2, 0);
    }

    private void switchModeByDoubleClick() {
        int currentMode;
        Log.d(TAG, "switchModeByDoubleClick currentMode = " + this.mLaserState.mMode);
        this.mHandler.sendEmptyMessage(4);
        synchronized (this.mLock) {
            if (this.mLaserState.mMode == 0) {
                this.mLaserState.mMode = 1;
                setVisibleLocked(true);
            } else {
                resetLaserStatePathLocked();
                this.mLaserState.mMode = 0;
                setVisibleLocked(false);
            }
            currentMode = this.mLaserState.mMode;
            resetPositionLocked();
        }
        notifyPencilLaserModeChange(currentMode);
        if (messagesExist(1)) {
            this.mHandler.removeMessages(1);
        }
        this.mHandler.sendEmptyMessage(1);
    }

    private void resetLaserStatePathLocked() {
        Log.d(TAG, "resetLaserStatePathLocked");
        this.mLaserState.mPath.reset();
        this.mLaserState.mDirty = false;
    }

    public long interceptKeyBeforeDispatching(KeyEvent event, int policyFlags, int keyCode, boolean down, boolean interactive) {
        InputLog.d(TAG, "[interceptKeyBeforeDispatching] event = " + event + ", kecode = " + event.getKeyCode() + ", interactive=" + interactive + " mLaserSettingsState = " + this.mLaserSettingsState + " event.getScanCode = " + event.getScanCode() + " event.getSource=" + event.getSource());
        if (event.getSource() == 257 && this.mLaserSettingsState && this.mDisplayState == 2) {
            if (event.getScanCode() == SCAN_CODE_DOUBLE_CLICK && event.getAction() == 0) {
                Log.d(TAG, "[interceptKeyBeforeDispatching]double click, switchModeByDoubleClick");
                switchModeByDoubleClick();
                return -1L;
            }
            if (event.getScanCode() == SCAN_CODE_LONG_PRESS && event.getAction() == 0 && event.getRepeatCount() == 0) {
                setLongClick(true);
                if (messagesExist(8)) {
                    this.mHandler.removeMessages(8);
                }
                this.mHandler.sendEmptyMessageDelayed(8, KEY_DOWN_TIMEOUT);
                Log.d(TAG, "[interceptKeyBeforeDispatching]Long click laser, down");
                return -1L;
            }
            if (event.getScanCode() == SCAN_CODE_LONG_PRESS && event.getAction() == 1) {
                setLongClick(false);
                this.mHandler.removeMessages(8);
                Log.d(TAG, "[interceptKeyBeforeDispatching]Long click laser, up");
                return -1L;
            }
            return 0L;
        }
        return 0L;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onKeyDownTimeout() {
        Log.w(TAG, "May be the hardware occurred an exception, laser key not up");
        setLongClick(false);
    }

    private void setLongClick(boolean isLongClick) {
        boolean needUpdate = false;
        synchronized (this.mLock) {
            Log.d(TAG, "setLongClick isLongClick = " + isLongClick + " ,mLongPress = " + this.mLaserState.mLongPress + " ,mMode = " + this.mLaserState.mMode);
            if (this.mLaserState.mLongPress != isLongClick) {
                needUpdate = true;
                this.mLaserState.mLongPress = isLongClick;
                if (this.mLaserState.mMode == 0) {
                    setVisibleLocked(isLongClick);
                } else if (isLongClick && this.mLaserState.mMode == 1) {
                    Log.d(TAG, "setLongClick for timeout hide, isVisible = " + isLongClick);
                    setVisibleLocked(isLongClick);
                }
            }
        }
        if (needUpdate && messagesExist(1)) {
            this.mHandler.removeMessages(1);
        }
        this.mHandler.sendEmptyMessage(1);
    }

    private void setVisibleLocked(boolean isVisible) {
        setNotifyEnable(isVisible);
        if (this.mLaserState.mVisible != isVisible) {
            this.mLaserState.mVisible = isVisible;
            if (isVisible) {
                fadePointer(this.mDisplayId);
            }
        }
    }

    public void changeLaserSwitch(boolean settingsState) {
        this.mLaserSettingsState = settingsState;
        Log.d(TAG, "[changeLaserSwitch] mLaserSettingsState = " + this.mLaserSettingsState);
        if (this.mLaserSettingsState) {
            this.mIsSwitchInit = true;
            initLaserState(true, false);
        } else {
            this.mIsSwitchInit = false;
            if (messagesExist(2)) {
                this.mHandler.removeMessages(2);
            }
            this.mHandler.sendEmptyMessage(2);
        }
    }

    private void initLaserState(boolean initPosition, boolean removeWmFlag) {
        Log.d(TAG, "initLaserState initPosition = " + initPosition + " ,removeWmFlag = " + removeWmFlag);
        setLaserPointMode();
        synchronized (this.mLock) {
            this.mLaserState.mLongPress = false;
            this.mLaserState.mPath.reset();
            this.mLaserState.mDirty = false;
            setVisibleLocked(false);
            if (initPosition) {
                resetPositionLocked();
            }
        }
        if (messagesExist(1)) {
            this.mHandler.removeMessages(1);
        }
        if (!removeWmFlag) {
            this.mHandler.sendEmptyMessage(1);
        }
    }

    private void setLaserPointMode() {
        synchronized (this.mLock) {
            if (this.mLaserState.mMode == 0 && !this.mIsSwitchInit) {
                return;
            }
            this.mIsSwitchInit = false;
            this.mLaserState.mMode = 0;
            notifyPencilLaserModeChange(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setLaserStateByTouchScreen() {
        synchronized (this.mLock) {
            if (this.mLaserView != null && this.mLaserState.mVisible) {
                initLaserState(false, false);
            }
        }
    }

    private void addLaserWindow() {
        if (this.mLaserView != null) {
            return;
        }
        Log.d(TAG, "addLaserWindow");
        OplusLaserView laserView = new OplusLaserView(this.mContext);
        this.mLaserView = laserView;
        WindowManager.LayoutParams layoutParams = getLayoutParams();
        this.mLayoutParams = layoutParams;
        this.mWindowManger.addView(this.mLaserView, layoutParams);
        this.mStartTime = Long.valueOf(SystemClock.uptimeMillis());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeLaserWindow() {
        if (this.mLaserView == null) {
            Log.d(TAG, "[removeLaserWindow] mLaserView == null");
            return;
        }
        Log.d(TAG, "removeLaserWindow");
        initLaserState(true, true);
        this.mWindowManger.removeViewImmediate(this.mLaserView);
        this.mLaserView = null;
        this.mProcTime = Long.valueOf(this.mProcTime.longValue() + (SystemClock.uptimeMillis() - this.mStartTime.longValue()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadCollectData(String type, String reason) {
        if (this.mInputStatistic != null) {
            this.mInputStatistic.startInputStatistic(type, reason);
            this.mProcTime = 0L;
        }
    }

    private WindowManager.LayoutParams getLayoutParams() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.flags = 24;
        params.layoutInDisplayCutoutMode = 1;
        params.type = 2018;
        params.format = -3;
        params.gravity = 8388659;
        params.x = 0;
        params.y = 0;
        params.width = -1;
        params.height = -1;
        params.setTrustedOverlay();
        params.setTitle("laser");
        return params;
    }

    public void refreshLaserState() {
        InputLog.d(TAG, "refreshLaserState " + this.mLaserState);
        synchronized (this.mLock) {
            this.mViewLaserState.copy(this.mLaserState);
        }
        OplusLaserView laserView = this.mLaserView;
        if (laserView == null && this.mLaserSettingsState && this.mDisplayState == 2) {
            addLaserWindow();
        }
        if (this.mLaserView == null) {
            return;
        }
        this.mLaserView.setMode(this.mViewLaserState.mMode);
        this.mLaserView.setLongClick(this.mViewLaserState.mLongPress);
        this.mLaserView.setDirty(this.mViewLaserState.mLongPress);
        this.mLaserView.setViewXY(this.mViewLaserState.mPosition);
        this.mLaserView.setVisible(this.mViewLaserState.mVisible);
        float length = 0.0f;
        if (!this.mViewLaserState.mPath.isEmpty()) {
            length = this.mLaserView.getPathLength(this.mViewLaserState.mPath);
        }
        if (length > MAX_PATH_LENGTH) {
            this.mLaserView.setPath(this.mViewLaserState.mPath, true);
            this.mChange = true;
            this.mLaserState.mPath.reset();
            this.mLastPointerX = (this.mViewLaserState.mLastPosition.x + this.mViewLaserState.mPosition.x) / 2.0f;
            this.mLastPointerY = (this.mViewLaserState.mLastPosition.y + this.mViewLaserState.mPosition.y) / 2.0f;
            Log.d(TAG, "The path is too long to draw, record mLastPointerX = " + this.mLastPointerX + " mLastPointerY = " + this.mLastPointerY);
        } else {
            this.mLaserView.setPath(this.mViewLaserState.mPath, false);
            this.mChange = false;
        }
        this.mLaserView.setDarkMode(this.mViewLaserState.mDarkMode);
        this.mLaserView.updateLaserView();
        pokeUserActivity();
        resetWindowRemoveTimeout();
    }

    private void resetWindowRemoveTimeout() {
        if (this.mHandler.hasMessages(2) && !this.mLaserSettingsState) {
            Log.d(TAG, "[resetWindowRemoveTimeout] mLaserSettingsState is false");
            return;
        }
        this.mHandler.removeMessages(2);
        if (!needExist()) {
            this.mHandler.sendEmptyMessageDelayed(2, 120000L);
        }
    }

    private boolean needExist() {
        synchronized (this.mLock) {
            if (this.mLaserState.mVisible) {
                return !this.mLaserState.mPath.isEmpty();
            }
            return false;
        }
    }

    public static class LaserState {
        int mMode = 0;
        boolean mLongPress = false;
        boolean mDirty = false;
        boolean mVisible = false;
        PointF mPosition = new PointF();
        PointF mLastPosition = new PointF();
        Path mPath = new Path();
        boolean mDarkMode = false;

        LaserState() {
        }

        void copy(LaserState laserState) {
            this.mMode = laserState.mMode;
            this.mLongPress = laserState.mLongPress;
            this.mDirty = laserState.mDirty;
            this.mVisible = laserState.mVisible;
            this.mPosition.set(laserState.mPosition.x, laserState.mPosition.y);
            this.mLastPosition.set(laserState.mLastPosition.x, laserState.mLastPosition.y);
            this.mPath = new Path(laserState.mPath);
            this.mDarkMode = laserState.mDarkMode;
        }
    }

    private boolean messagesExist(int msg) {
        if (this.mHandler.hasMessages(msg)) {
            return true;
        }
        return false;
    }

    private class LaserHandler extends Handler {
        public LaserHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    OplusLaserPointerController.this.refreshLaserState();
                    break;
                case 2:
                    OplusLaserPointerController.this.removeLaserWindow();
                    break;
                case 3:
                    OplusLaserPointerController.this.setLaserStateByTouchScreen();
                    break;
                case 4:
                    OplusLaserPointerController.this.startVibration();
                    break;
                case 5:
                    Bundle bundle = msg.getData();
                    int mode = bundle.getInt(OplusLaserPointerController.LASER_MODE);
                    OplusLaserPointerController.this.sendModeToPencilManager(mode);
                    break;
                case 6:
                    OplusLaserPointerController.this.mPencilManager = PencilManager.getInstance(OplusLaserPointerController.this.mContext, OplusLaserPointerController.this.mCurrentUserId);
                    break;
                case 7:
                    OplusLaserPointerController.this.mLaserStateObserver.onChange(false, OplusLaserPointerController.LASER_PEN_ENABLE_URI);
                    OplusLaserPointerController.this.mLaserStateObserver.onChange(false, OplusLaserPointerController.LASER_PEN_CONNECT_URI);
                    OplusLaserPointerController.this.mLaserStateObserver.onChange(false, OplusLaserPointerController.DARK_MODE_ENABLED_URI);
                    break;
                case 8:
                    OplusLaserPointerController.this.onKeyDownTimeout();
                    break;
            }
        }
    }
}
