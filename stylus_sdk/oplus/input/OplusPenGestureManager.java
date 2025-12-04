package com.android.server.oplus.input.exinputservice.globalgesture;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.OplusWindowManager;
import android.view.ViewConfiguration;
import com.android.server.oplus.osense.feature.uaf.common.Constants;
import com.oplus.content.AppfeatureHelper;
import com.oplus.debug.InputLog;
import com.oplus.screenshot.OplusLongshotUtils;
import com.oplus.screenshot.OplusScreenshotManager;

/* loaded from: classes.dex */
public class OplusPenGestureManager {
    private static final String BUNDLE_KEY_COMPLETE = "complete";
    private static final String BUNDLE_KEY_X = "x";
    private static final String BUNDLE_KEY_Y = "y";
    private static final String BUNDLE_MODE = "mode";
    private static final String FEATURE_SCREENSHOT_LIGHT = "com.oplus.screenshot.light";
    private static final int GESTURE_MOVE_AREA_HEIGHT_HORIZONTAL = 70;
    private static final int GESTURE_MOVE_AREA_HEIGHT_VECTICAL = 65;
    private static final int GESTURE_MOVE_AREA_WIDTH_HORIZONTAL = 75;
    private static final int GESTURE_MOVE_AREA_WIDTH_VECTICAL = 75;
    private static final int GESTURE_SOW_VIEW_SLOP = 30;
    private static final int GESTURE_START_AREA_HEIGHT = 50;
    private static final int GESTURE_START_AREA_WIDTH = 50;
    private static final int HIDE_VIEW = 2;
    private static final int HORIZONTAL = 0;
    private static final int PEN_SCREENSHOT_SWITCH_CLOSE = 2;
    private static final int PEN_SCREENSHOT_SWITCH_OPEN = 1;
    private static final int PEN_SCREEN_SHOT = 0;
    private static final String SCREENSHOT_TIME_STAMP = "screenshot_time_stamp";
    private static final int SHOW_VIEW = 0;
    private static final String SOURCE_PEN_SWIPE_QUICK_EDITOR = "QuickEditor_pencil";
    private static final String TAG = "GestureLog_OplusPenGestureManager";
    private static final int UPDATE_VIEW = 1;
    private static final int VERTICAL = 1;
    private static volatile OplusPenGestureManager sInstance;
    private Handler mAsyncHandler;
    private Context mContext;
    private DisplayMetrics mDm;
    private float mDownX;
    private float mDownY;
    private OplusWindowManager mIOplusWindowManagerImpl;
    private float mLastMotionX;
    private float mLastMotionY;
    private int mPenShowViewSlop;
    private int mScreenOrientation;
    private boolean mScreenShotLightFeature;
    private int mSwipeMajorMoveHeightHorizontal;
    private int mSwipeMajorMoveHeightVertical;
    private int mSwipeMajorMoveWidthHorizontal;
    private int mSwipeMajorMoveWidthVertical;
    private int mSwipeStartAreaHeight;
    private int mSwipeStartAreaWidth;
    private ThreeFingerViewManager mThreeFingerViewManager;
    private int mTouchSlop;
    private float mUpX;
    private float mUpY;
    private static final boolean WRITING_PEN_ONLY = SystemProperties.getBoolean("persist.sys.force_writting_pen", true);
    private static final String IPE_PENCIL_SWIP_FUNCTION = "ipe_pencil_swipe_function";
    private static final Uri IPE_PENCIL_SWIP_FUNCTION_URI = Settings.Global.getUriFor(IPE_PENCIL_SWIP_FUNCTION);
    private final Rect mSwipeStartArea = new Rect();
    private int mFirstShowViewCount = 0;
    private boolean mPenSwipeSatrt = false;
    private int mPencilSwipFunction = 0;

    private OplusPenGestureManager() {
    }

    public static OplusPenGestureManager getInstance() {
        if (sInstance == null) {
            synchronized (OplusPenGestureManager.class) {
                if (sInstance == null) {
                    sInstance = new OplusPenGestureManager();
                }
            }
        }
        return sInstance;
    }

    public void init(Context context, Handler handler) {
        this.mContext = context;
        this.mAsyncHandler = handler;
        registerSwitchChangeObserver();
        this.mThreeFingerViewManager = ThreeFingerViewManager.getInstance(context);
        this.mScreenShotLightFeature = AppfeatureHelper.isFeatureSupport(context.getContentResolver(), FEATURE_SCREENSHOT_LIGHT);
        initSwipeConstant();
        this.mPencilSwipFunction = isPenScreenshotEnable();
        this.mIOplusWindowManagerImpl = new OplusWindowManager();
        Log.d(TAG, "mPencilSwipFunction = " + this.mPencilSwipFunction + " mThreeFingerViewManager = " + this.mThreeFingerViewManager);
    }

    private static int dpToPx(float dpValue, Resources res) {
        return (int) TypedValue.applyDimension(1, dpValue, res.getDisplayMetrics());
    }

    private void initSwipeConstant() {
        this.mSwipeStartAreaWidth = dpToPx(50.0f, this.mContext.getResources());
        this.mSwipeStartAreaHeight = dpToPx(50.0f, this.mContext.getResources());
        this.mSwipeMajorMoveWidthHorizontal = dpToPx(75.0f, this.mContext.getResources());
        this.mSwipeMajorMoveHeightHorizontal = dpToPx(70.0f, this.mContext.getResources());
        this.mSwipeMajorMoveWidthVertical = dpToPx(75.0f, this.mContext.getResources());
        this.mSwipeMajorMoveHeightVertical = dpToPx(65.0f, this.mContext.getResources());
        this.mPenShowViewSlop = dpToPx(30.0f, this.mContext.getResources());
        this.mTouchSlop = ViewConfiguration.get(this.mContext).getScaledTouchSlop();
        Log.d(TAG, " mSwipeStartAreaWidth:" + this.mSwipeStartAreaWidth + " mSwipeStartAreaHeight:" + this.mSwipeStartAreaHeight + " mSwipeMajorMoveWidthHorizontal:" + this.mSwipeMajorMoveWidthHorizontal + " mSwipeMajorMoveWidthVertical:" + this.mSwipeMajorMoveWidthVertical + " mSwipeMajorMoveHeightVertical:" + this.mSwipeMajorMoveHeightVertical + " mTouchSlop:" + this.mTouchSlop + " mPenShowViewSlop:" + this.mPenShowViewSlop);
    }

    public void onTouchEvent(MotionEvent event) {
        if ((event.getSource() & 2) != 0) {
            if ((isStylusEvent(event) && this.mPencilSwipFunction == 1) || !WRITING_PEN_ONLY) {
                onPointerEvent(event);
            }
        }
    }

    private boolean isStylusEvent(MotionEvent event) {
        if (event == null || !event.isFromSource(16386)) {
            return false;
        }
        int tool = event.getToolType(0);
        return tool == 2 || tool == 4;
    }

    private void onPointerEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case 0:
                notifyPenSwipeStart(event);
                break;
            case 1:
                if (this.mPenSwipeSatrt) {
                    boolean inEndArea = captureUpInfo(event);
                    if (inEndArea) {
                        Log.d(TAG, "[notifyPenSwipeEnd] inEndArea startScreenShot");
                        this.mAsyncHandler.sendEmptyMessage(29);
                    }
                    notifyPenSwipeEnd(true);
                    break;
                }
                break;
            case 2:
                if (this.mPenSwipeSatrt) {
                    notifyPenSwipeMove(event);
                    break;
                }
                break;
            case 3:
                if (this.mPenSwipeSatrt) {
                    notifyPenSwipeEnd(true);
                    break;
                }
                break;
        }
    }

    private void notifyPenSwipeStart(MotionEvent event) {
        updateSwipeStartArea();
        boolean inStartArea = captureDownInfo(event);
        if (inStartArea) {
            this.mPenSwipeSatrt = true;
            saveToLastMotion(event);
        } else {
            this.mPenSwipeSatrt = false;
        }
        Log.d(TAG, "[notifyPenSwipeStart] mPenSwipeSatrt = " + this.mPenSwipeSatrt);
    }

    private void notifyPenSwipeMove(MotionEvent event) {
        deliverPenSwipeAndStayIfNeed(event);
    }

    private void notifyPenSwipeEnd(boolean complete) {
        InputLog.v(TAG, "[notifyPenSwipeEnd]");
        this.mPenSwipeSatrt = false;
        Message msg = Message.obtain(this.mAsyncHandler, 32);
        Bundle bundle = new Bundle();
        bundle.putInt("mode", 2);
        bundle.putBoolean("complete", complete);
        msg.setData(bundle);
        this.mAsyncHandler.sendMessage(msg);
    }

    private boolean captureDownInfo(MotionEvent event) {
        Log.d(TAG, "captureDownInfo mDownX = " + this.mDownX + " mDownY = " + this.mDownY);
        this.mFirstShowViewCount = 0;
        this.mDownX = event.getX();
        this.mDownY = event.getY();
        initSwipeConstant();
        if (!this.mSwipeStartArea.contains((int) this.mDownX, (int) this.mDownY)) {
            return false;
        }
        return true;
    }

    private boolean captureUpInfo(MotionEvent event) {
        this.mUpX = event.getX();
        this.mUpY = event.getY();
        float deltaDownX = this.mUpX - this.mDownX;
        float deltaDownY = this.mUpY - this.mDownY;
        Log.d(TAG, "captureUpInfo mDownX:" + this.mDownX + " mDownY:" + this.mDownY + " mUpX:" + this.mUpX + " mUpY:" + this.mUpY + " deltaDownX= " + deltaDownX + " deltaDownY= " + deltaDownY);
        if (this.mScreenOrientation == 0 && ((Math.abs(deltaDownX) >= this.mSwipeMajorMoveWidthHorizontal && deltaDownX < 0.0f) || (deltaDownY >= this.mSwipeMajorMoveHeightHorizontal && deltaDownY > 0.0f))) {
            return true;
        }
        if (this.mScreenOrientation == 1) {
            return (Math.abs(deltaDownX) >= ((float) this.mSwipeMajorMoveWidthVertical) && deltaDownX < 0.0f) || (deltaDownY >= ((float) this.mSwipeMajorMoveHeightVertical) && deltaDownY > 0.0f);
        }
        return false;
    }

    private void saveToLastMotion(MotionEvent event) {
        this.mLastMotionX = event.getX();
        this.mLastMotionY = event.getY();
    }

    private void handlePenSwipeMove(Bundle bundle) {
        if (bundle == null) {
            Log.d(TAG, "handlePenSwipeMove invalid arg return!");
            return;
        }
        int x = bundle.getInt("x");
        int y = bundle.getInt("y");
        int mode = bundle.getInt("mode");
        penSwipNotifyThreeFingerView(x, y, mode);
    }

    private void handlePenSwipeEnd(Bundle bundle) {
        if (bundle == null) {
            Log.d(TAG, "handlePenSwipeEnd invalid arg return!");
            return;
        }
        boolean complete = bundle.getBoolean("complete");
        int mode = bundle.getInt("mode");
        Log.d(TAG, "handlePenSwipeEnd : " + complete);
        penSwipNotifyThreeFingerView(0, 0, mode);
    }

    private void penSwipNotifyThreeFingerView(int x, int y, int mode) {
        InputLog.d(TAG, "penSwipNotifyThreeFingerView mode = " + mode + ", x = " + x + ", y = " + y);
        if (mode == 0) {
            this.mThreeFingerViewManager.setPencilView(true);
            if (!this.mScreenShotLightFeature) {
                this.mThreeFingerViewManager.showThreeFingerView(x, y);
                return;
            }
            return;
        }
        if (mode == 1) {
            if (!this.mScreenShotLightFeature) {
                this.mThreeFingerViewManager.updateThreeFingerView(x, y);
            }
        } else if (mode == 2) {
            this.mThreeFingerViewManager.setPencilView(false);
            if (!this.mScreenShotLightFeature) {
                this.mThreeFingerViewManager.hideThreeFingerView();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSwipeStartArea() {
        this.mDm = this.mContext.getResources().getDisplayMetrics();
        Log.d(TAG, "updateSwipeStartArea mDm.widthPixels = " + this.mDm.widthPixels + " mDm.heightPixels = " + this.mDm.heightPixels);
        this.mSwipeStartArea.set(this.mDm.widthPixels - this.mSwipeStartAreaWidth, 0, this.mDm.widthPixels, this.mSwipeStartAreaHeight);
        if (this.mDm.widthPixels > this.mDm.heightPixels) {
            this.mScreenOrientation = 0;
        } else {
            this.mScreenOrientation = 1;
        }
    }

    private void deliverPenSwipeAndStayIfNeed(MotionEvent event) {
        float curX = event.getX();
        float curY = event.getY();
        float deltaDownX = curX - this.mDownX;
        float deltaDownY = curY - this.mDownY;
        int deltaX = (int) Math.abs(curX - this.mLastMotionX);
        int deltaY = (int) Math.abs(curY - this.mLastMotionY);
        InputLog.d(TAG, "[deliverPenSwipeAndStayIfNeed] deltaDownX=" + deltaDownX + " deltaDownY=" + deltaDownY + " deltaX=" + deltaX + " deltaY=" + deltaY);
        if ((deltaX * deltaX) + (deltaY * deltaY) < this.mTouchSlop * this.mTouchSlop) {
            InputLog.d(TAG, "not trigger swiped!");
            return;
        }
        saveToLastMotion(event);
        if ((Math.abs(deltaDownX) >= this.mPenShowViewSlop && deltaDownX < 0.0f) || (Math.abs(deltaDownY) >= this.mPenShowViewSlop && deltaDownY > 0.0f)) {
            needUpdateView((int) curX, (int) curY);
        }
    }

    private void needUpdateView(int curX, int curY) {
        if (this.mFirstShowViewCount == 0) {
            sendPenMessage(31, 0, this.mDm.widthPixels - curX, curY);
            this.mFirstShowViewCount++;
        } else {
            sendPenMessage(31, 1, this.mDm.widthPixels - curX, curY);
        }
    }

    private void sendPenMessage(int type, int mode, int x, int y) {
        Message msg = Message.obtain(this.mAsyncHandler, type);
        Bundle bundle = new Bundle();
        bundle.putInt("x", x);
        bundle.putInt("y", y);
        bundle.putInt("mode", mode);
        msg.setData(bundle);
        if (this.mAsyncHandler.hasMessages(type)) {
            this.mAsyncHandler.removeMessages(type);
        }
        this.mAsyncHandler.sendMessage(msg);
    }

    private void registerSwitchChangeObserver() {
        this.mContext.getContentResolver().registerContentObserver(IPE_PENCIL_SWIP_FUNCTION_URI, false, new ContentObserver(this.mAsyncHandler) { // from class: com.android.server.oplus.input.exinputservice.globalgesture.OplusPenGestureManager.1
            @Override // android.database.ContentObserver
            public void onChange(boolean b) {
                super.onChange(b);
                OplusPenGestureManager.this.mPencilSwipFunction = OplusPenGestureManager.this.isPenScreenshotEnable();
                Log.d(OplusPenGestureManager.TAG, "[onChange]mPencilSwipFunction:" + OplusPenGestureManager.this.mPencilSwipFunction);
                OplusPenGestureManager.this.updateSwipeStartArea();
            }
        });
    }

    private void startScreenShot() {
        if (this.mPencilSwipFunction == 1) {
            OplusScreenshotManager screenshotManager = OplusLongshotUtils.getScreenshotManager(this.mContext);
            Bundle bundle = new Bundle();
            bundle.putString(Constants.BUNDLE_KEY_SCREENSHOT_SOURCE, SOURCE_PEN_SWIPE_QUICK_EDITOR);
            screenshotManager.takeScreenshot(bundle);
            Intent intent = new Intent("oplus.intent.action.SCREEN_SHOT");
            long currentTime = System.currentTimeMillis();
            intent.putExtra(SCREENSHOT_TIME_STAMP, currentTime);
            this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
            Log.i(TAG, "startSource: " + SOURCE_PEN_SWIPE_QUICK_EDITOR + ", currentTime = " + currentTime);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int isPenScreenshotEnable() {
        int value = Settings.Global.getInt(this.mContext.getContentResolver(), IPE_PENCIL_SWIP_FUNCTION, 2);
        Log.d(TAG, "isPenScreenshotEnable = " + value);
        return value;
    }

    public void onGestureMessage(Message msg) {
        switch (msg.what) {
            case 29:
                startScreenShot();
                break;
            case 31:
                handlePenSwipeMove(msg.getData());
                break;
            case 32:
                handlePenSwipeEnd(msg.getData());
                break;
        }
    }
}
