package com.oplusos.vfxsdk.doodleengine.stylus;

import android.graphics.PointF;
import android.os.Handler;
import android.view.MotionEvent;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.q;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.e;

/* compiled from: FingerTouchDetector.kt */
/* loaded from: classes5.dex */
public final class FingerTouchDetector {
    public static final Companion Companion = new Companion(null);
    private static final long FINGER_LONG_PRESS_TIMEOUT = 500;
    private static final float FINGER_MOVE_THRESHOLD = 10.0f;
    private static final long FINGER_TAP_TIMEOUT = 150;
    private static final String TAG = "FingerTouchDetector";
    private static final long TOW_FINGER_TAP_TIMEOUT = 50;
    private int fingerDownCount;
    private List<PointF> fingerDownPositions;
    private long fingerDownTime;
    private Runnable fingerLongPressRunnable;
    private final FingerTouchListener fingerTouchListener;
    private final Handler handler;
    private boolean isFingerLongPressDetected;
    private boolean isFingerMove;
    private boolean isTowFingerTapOverTime;
    private boolean isTriggerMoveEvent;

    /* compiled from: FingerTouchDetector.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(e eVar) {
            this();
        }
    }

    /* compiled from: FingerTouchDetector.kt */
    public interface FingerTouchListener {
        void onMultiFingerTap(int i, List<? extends PointF> list);

        void onSingleFingerLongPress(float f, float f2);
    }

    public FingerTouchDetector(Handler handler, FingerTouchListener fingerTouchListener) {
        Intrinsics.checkNotNullParameter(handler, "handler");
        this.handler = handler;
        this.fingerTouchListener = fingerTouchListener;
        this.fingerDownPositions = new ArrayList();
    }

    private final boolean handleFingerDown(MotionEvent motionEvent) {
        this.fingerDownTime = System.currentTimeMillis();
        this.fingerDownCount = 1;
        this.fingerDownPositions.clear();
        this.fingerDownPositions.add(new PointF(motionEvent.getX(), motionEvent.getY()));
        this.isFingerLongPressDetected = false;
        Runnable runnable = this.fingerLongPressRunnable;
        if (runnable != null) {
            this.handler.removeCallbacks(runnable);
        }
        com.oplus.richtext.editor.view.toolbar.content.e eVar = new com.oplus.richtext.editor.view.toolbar.content.e(this, 12);
        this.fingerLongPressRunnable = eVar;
        this.handler.postDelayed(eVar, 500L);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleFingerDown$lambda$1(FingerTouchDetector this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.fingerDownPositions.size() != 1 || this$0.isFingerLongPressDetected) {
            return;
        }
        this$0.isFingerLongPressDetected = true;
        FingerTouchListener fingerTouchListener = this$0.fingerTouchListener;
        if (fingerTouchListener != null) {
            fingerTouchListener.onSingleFingerLongPress(this$0.fingerDownPositions.get(0).x, this$0.fingerDownPositions.get(0).y);
        }
    }

    private final boolean handleFingerMove(MotionEvent motionEvent) {
        Runnable runnable;
        this.isTriggerMoveEvent = true;
        if (this.fingerDownPositions.isEmpty()) {
            return false;
        }
        if (this.isFingerLongPressDetected) {
            return true;
        }
        if (motionEvent.getPointerCount() != this.fingerDownPositions.size()) {
            return false;
        }
        int pointerCount = motionEvent.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            float x = motionEvent.getX(i);
            float y = motionEvent.getY(i);
            PointF pointF = (PointF) q.c2(i, this.fingerDownPositions);
            float f = pointF != null ? pointF.x : 0.0f;
            PointF pointF2 = (PointF) q.c2(i, this.fingerDownPositions);
            float f2 = x - f;
            float f3 = y - (pointF2 != null ? pointF2.y : 0.0f);
            if (((float) Math.sqrt((f3 * f3) + (f2 * f2))) > 10.0f) {
                this.isFingerMove = true;
            }
        }
        if (this.isFingerMove && (runnable = this.fingerLongPressRunnable) != null) {
            this.handler.removeCallbacks(runnable);
        }
        return false;
    }

    private final boolean handleFingerPointerDown(MotionEvent motionEvent) {
        if (System.currentTimeMillis() - this.fingerDownTime > 50) {
            this.isTowFingerTapOverTime = true;
        }
        this.fingerDownCount = motionEvent.getPointerCount();
        Runnable runnable = this.fingerLongPressRunnable;
        if (runnable != null) {
            this.handler.removeCallbacks(runnable);
        }
        this.fingerDownPositions.clear();
        int pointerCount = motionEvent.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            this.fingerDownPositions.add(new PointF(motionEvent.getX(i), motionEvent.getY(i)));
        }
        return false;
    }

    private final boolean handleFingerPointerUp(MotionEvent motionEvent) {
        int pointerCount = motionEvent.getPointerCount() - 1;
        this.fingerDownCount = pointerCount;
        if (this.isFingerLongPressDetected) {
            return true;
        }
        if (pointerCount > 0) {
            return false;
        }
        return handleFingerUp(motionEvent);
    }

    private final boolean handleFingerUp(MotionEvent motionEvent) {
        Runnable runnable = this.fingerLongPressRunnable;
        if (runnable != null) {
            this.handler.removeCallbacks(runnable);
        }
        long jCurrentTimeMillis = System.currentTimeMillis() - this.fingerDownTime;
        if (this.isFingerLongPressDetected) {
            resetFingerTouchState();
            return true;
        }
        if (jCurrentTimeMillis < 150) {
            if ((!this.isTowFingerTapOverTime && !this.isFingerMove && this.fingerDownPositions.size() == 2) || (!this.isTriggerMoveEvent && this.fingerDownPositions.size() >= 3)) {
                q.v2(this.fingerDownPositions);
                this.fingerDownPositions.size();
                resetFingerTouchState();
                return true;
            }
            if (!this.isFingerMove && this.fingerDownPositions.size() == 1) {
                FingerTouchListener fingerTouchListener = this.fingerTouchListener;
                if (fingerTouchListener != null) {
                    fingerTouchListener.onMultiFingerTap(1, com.oplus.note.scenecard.utils.b.p0(this.fingerDownPositions.get(0)));
                }
                return true;
            }
        }
        resetFingerTouchState();
        return false;
    }

    private final void resetFingerTouchState() {
        this.fingerDownTime = 0L;
        this.fingerDownCount = 0;
        this.fingerDownPositions.clear();
        this.isFingerLongPressDetected = false;
        this.isTowFingerTapOverTime = false;
        this.isFingerMove = false;
        this.isTriggerMoveEvent = false;
        Runnable runnable = this.fingerLongPressRunnable;
        if (runnable != null) {
            this.handler.removeCallbacks(runnable);
        }
        this.fingerLongPressRunnable = null;
    }

    public final void destroy() {
        resetFingerTouchState();
    }

    public final boolean handleFingerTouchEvent(MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getToolType(event.getActionIndex()) != 1) {
            return false;
        }
        int actionMasked = event.getActionMasked();
        if (actionMasked == 0) {
            return handleFingerDown(event);
        }
        if (actionMasked != 1) {
            if (actionMasked == 2) {
                return handleFingerMove(event);
            }
            if (actionMasked != 3) {
                if (actionMasked == 5) {
                    return handleFingerPointerDown(event);
                }
                if (actionMasked != 6) {
                    return false;
                }
                return handleFingerPointerUp(event);
            }
        }
        return handleFingerUp(event);
    }
}
