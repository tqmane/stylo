package com.oplusos.vfxsdk.doodleengine.stylus;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.VelocityTracker;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.s;
import androidx.recyclerview.widget.h;
import com.coui.appcompat.scroll.SpringOverScroller;
import com.nearme.note.logic.ThumbFileConstants;
import com.oplus.backup.sdk.common.utils.ModuleType;
import com.oplus.dmp.sdk.index.IndexProtocol;
import com.oplus.dmp.sdk.search.bean.v2.FileHighlighter;
import com.oplusos.vfxsdk.doodleengine.CanvasExtendType;
import com.oplusos.vfxsdk.doodleengine.EffectType;
import com.oplusos.vfxsdk.doodleengine.EntityOperationResult;
import com.oplusos.vfxsdk.doodleengine.FileCode;
import com.oplusos.vfxsdk.doodleengine.LassoOperation;
import com.oplusos.vfxsdk.doodleengine.MenuItemData;
import com.oplusos.vfxsdk.doodleengine.MenuType;
import com.oplusos.vfxsdk.doodleengine.Operation;
import com.oplusos.vfxsdk.doodleengine.Paint;
import com.oplusos.vfxsdk.doodleengine.base.ActionResult;
import com.oplusos.vfxsdk.doodleengine.base.FontType;
import com.oplusos.vfxsdk.doodleengine.base.ImplPlatform;
import com.oplusos.vfxsdk.doodleengine.base.SaveDrawingResult;
import com.oplusos.vfxsdk.doodleengine.base.SelectType;
import com.oplusos.vfxsdk.doodleengine.data.ScaleData;
import com.oplusos.vfxsdk.doodleengine.font.FontDataList;
import com.oplusos.vfxsdk.doodleengine.font.IDownloadListener;
import com.oplusos.vfxsdk.doodleengine.span.EntityManager;
import com.oplusos.vfxsdk.doodleengine.span.Extra;
import com.oplusos.vfxsdk.doodleengine.span.OplusSpannedLayout;
import com.oplusos.vfxsdk.doodleengine.span.SpanEditDrawView;
import com.oplusos.vfxsdk.doodleengine.stylus.FingerTouchDetector;
import com.oplusos.vfxsdk.doodleengine.stylusapi.EntityListener;
import com.oplusos.vfxsdk.doodleengine.stylusapi.EntityType;
import com.oplusos.vfxsdk.doodleengine.stylusapi.FingerTouchListener;
import com.oplusos.vfxsdk.doodleengine.stylusapi.InsertImageCallback;
import com.oplusos.vfxsdk.doodleengine.stylusapi.LoadListener;
import com.oplusos.vfxsdk.doodleengine.stylusapi.PaintViewListener;
import com.oplusos.vfxsdk.doodleengine.stylusapi.SaveListener;
import com.oplusos.vfxsdk.doodleengine.stylusapi.ScrollListener;
import com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface;
import com.oplusos.vfxsdk.doodleengine.stylusapi.TextOperationCallback;
import com.oplusos.vfxsdk.doodleengine.stylusapi.TextOperationError;
import com.oplusos.vfxsdk.doodleengine.stylusapi.TrackEngineCallback;
import com.oplusos.vfxsdk.doodleengine.stylusapi.UpdateImageCallback;
import com.oplusos.vfxsdk.doodleengine.stylusapi.ZoomingListener;
import com.oplusos.vfxsdk.doodleengine.toolkit.ToolkitModel;
import com.oplusos.vfxsdk.doodleengine.toolkit.internal.EraserType;
import com.oplusos.vfxsdk.doodleengine.util.ActionCallbackManager;
import com.oplusos.vfxsdk.doodleengine.util.CanvasRendRectManager;
import com.oplusos.vfxsdk.doodleengine.util.DisplayUtils;
import com.oplusos.vfxsdk.doodleengine.util.KeyBoardHelper;
import com.oplusos.vfxsdk.doodleengine.util.NativePaint;
import com.oplusos.vfxsdk.doodleengine.util.RendRectTopExtendUtil;
import com.oplusos.vfxsdk.doodleengine.util.RendReplacePointUtil;
import com.oplusos.vfxsdk.doodleengine.view.PaintEditBgView;
import com.oplusos.vfxsdk.doodleengine.view.PaintSurfaceView;
import com.oplusos.vfxsdk.doodleengine.view.PaintTextureView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Timer;
import java.util.TimerTask;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.q;
import kotlin.coroutines.d;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.e;
import kotlin.ranges.j;
import kotlin.sequences.k;
import kotlinx.coroutines.a0;
import kotlinx.coroutines.f1;
import kotlinx.coroutines.h0;
import kotlinx.coroutines.z;
import org.json.JSONException;
import org.json.JSONObject;
import org.spongycastle.asn1.cmc.BodyPartID;

/* compiled from: OplusStylusImplement.kt */
/* loaded from: classes5.dex */
public class OplusStylusImplement implements StylusInterface {
    private static final float MAX_PAINT_SIZE = 50.0f;
    private static final int POINT_COUNT = 120;
    private static final float TOP_CANVAS_Y = 2400.0f;
    private static final String VERSION = "VERSION_6.0.5";
    private boolean addingExternalText;
    private StylusInterface.BitmapMenuCallback bitmapMenuCallback;
    private int canvasExtendMax;
    private CanvasRendRectManager canvasRendRectManager;
    private final Runnable delayGestureEnd;
    private l<? super RectF, Unit> extendCallback;
    private final FingerTouchDetector fingerTouchDetector;
    private FingerTouchListener fingerTouchListener;
    private final boolean g_Debug;
    private final Handler handler;
    private ArrayList<String> imagePath;
    private boolean inCanvasScroll;
    private boolean inWebviewScroll;
    private PaintEditBgView inkBackgroundView;
    private boolean isInputTextMode;
    private KeyBoardHelper keyBoardHelper;
    private boolean keyboardChanging;
    private int keyboardHeight;
    private KeyBoardHelper.KeyboardHeightListener keyboardHeightListener;
    private int keyboardRunnableDelayTime;
    private final PointF lastInvalidPoint;
    private ScaleData lastScaleData;
    private final PointF lastValidPoint;
    private AssetManager mAssetManager;
    private float mAxisValue;
    private Bitmap mCanvasBitmap;
    private int mCanvasType;
    private Bitmap mCaptureBitmap;
    private int mCaptureHeight;
    private int mCaptureWidth;
    private RectF mContentRect;
    private Context mContext;
    private float mCurScale;
    private Operation mCurrentStatus;
    private boolean mDarkTheme;
    private DisplayUtils mDisplayUtils;
    private long mDownTime;
    private long mDrawingBackground;
    private String mDrawingsPath;
    private SpanEditDrawView mEditDrawView;
    private boolean mEnableOverlay;
    private boolean mEnableShapeRegular;
    private EntityListener mEntityListener;
    private EntityManager mEntityManager;
    private float mEraserSize;
    private int mImageHeight;
    private int mImageWidth;
    private boolean mInLassoAddOne;
    private boolean mInitialized;
    private boolean mIsStylus;
    private final List<LoadListener> mLoadListener;
    private float mMaxScale;
    private float mMinScale;
    private long mNativeHandle;
    private boolean mOnlyStylus;
    private final Paint mPaint;
    private int mPaintHeight;
    private final List<PaintViewListener> mPaintViewListener;
    private int mPaintWidth;
    private final Object mPickEntityLock;
    private String[] mPickEntityResult;
    private int mPointIdx;
    private final float[] mPoints;
    private float mPressure;
    private long mPreviewBackground;
    private l<? super Bitmap, Unit> mPreviewHandwritingCall;
    private String mPreviewImagePath;
    private long mPreviousTime;
    private float mRefreshRate;
    private Bitmap mRelativeCanvasBitmap;
    private int mRelativeCanvasHeight;
    private int mRelativeCanvasWidth;
    private RectF mRendRect;
    private SaveListener mSaveListener;
    private ScrollListener mScrollListener;
    private Timer mShapeRegularTimer;
    private OplusSpannedLayout mSpannedLayout;
    private long mStartTime;
    private StylusManager mStylusManager;
    private PaintSurfaceView mSurfaceView;
    private float mTempExtendUpValue;
    private PaintTextureView mTextureView;
    private boolean mTouchPointerDown;
    private boolean mTouching;
    private TrackEngineCallback mTrackEngine;
    private float mTx;
    private float mTy;
    private final kotlin.b mVelocityTracker$delegate;
    private ZoomingListener mZoomListener;
    private z mainScope;
    private f1 previewJob;
    private SaveCacheState saveCacheState;
    private String saveDataPath;
    private String saveImagePath;
    private l<? super Bitmap, Unit> saveSelect2BitmapCall;
    private int scrollByKeyboard;
    private f1 scrollKeyboardJob;
    private Extra selectedImgExtra;
    private final int spaceDistance;
    private p<? super String, ? super Boolean, Unit> splitLongCanvasCall;
    private long stepChangeTime;
    private int visibleHeight;
    private int visibleWidth;
    private float zoomScale;
    public static final Companion Companion = new Companion(null);
    private static final String TAG = "PAINT: OplusStylusImplement";
    private static float SPLIT_LONGCANVAS_PROP = 1.4152777f;

    /* compiled from: OplusStylusImplement.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(e eVar) {
            this();
        }

        public final Class<?> getNestedClass(String className) {
            Intrinsics.checkNotNullParameter(className, "className");
            if (Intrinsics.areEqual(className, "DrawableBoundaryInfo")) {
                return DrawableBoundaryInfo.class;
            }
            return null;
        }

        public final Triple<Integer, Boolean, Integer> getSplitCountDetachMain(String paint_file, float f, int i) {
            Intrinsics.checkNotNullParameter(paint_file, "paint_file");
            SplitCountAndContextHeight splitCountDetachMain = NativePaint.Companion.getSplitCountDetachMain(paint_file, f, i);
            if (splitCountDetachMain != null) {
                return new Triple<>(Integer.valueOf(splitCountDetachMain.getMSplitCount()), Boolean.valueOf(splitCountDetachMain.getSuccess()), Integer.valueOf(splitCountDetachMain.getMContextHeight()));
            }
            return null;
        }

        public final String getTAG() {
            return OplusStylusImplement.TAG;
        }

        public final void prepareNativeTouchData(float[] points, float f, float f2, long j) {
            Intrinsics.checkNotNullParameter(points, "points");
            points[0] = f;
            points[1] = f2;
            points[2] = 1.0f;
            points[3] = 0.0f;
            points[4] = 10.0f;
            points[5] = j;
            points[6] = 0.0f;
            points[7] = 0.0f;
            points[8] = 0.0f;
        }
    }

    /* compiled from: OplusStylusImplement.kt */
    public static final class DrawableBoundaryInfo {
        private final float mContextRangeX;
        private final float mContextRangeY;
        private final RectF mDrawableCanvasRect;
        private final RectF mDrawableSurfaceRect;

        public DrawableBoundaryInfo(RectF mDrawableCanvasRect, RectF mDrawableSurfaceRect, float f, float f2) {
            Intrinsics.checkNotNullParameter(mDrawableCanvasRect, "mDrawableCanvasRect");
            Intrinsics.checkNotNullParameter(mDrawableSurfaceRect, "mDrawableSurfaceRect");
            this.mDrawableCanvasRect = mDrawableCanvasRect;
            this.mDrawableSurfaceRect = mDrawableSurfaceRect;
            this.mContextRangeX = f;
            this.mContextRangeY = f2;
        }

        public static /* synthetic */ DrawableBoundaryInfo copy$default(DrawableBoundaryInfo drawableBoundaryInfo, RectF rectF, RectF rectF2, float f, float f2, int i, Object obj) {
            if ((i & 1) != 0) {
                rectF = drawableBoundaryInfo.mDrawableCanvasRect;
            }
            if ((i & 2) != 0) {
                rectF2 = drawableBoundaryInfo.mDrawableSurfaceRect;
            }
            if ((i & 4) != 0) {
                f = drawableBoundaryInfo.mContextRangeX;
            }
            if ((i & 8) != 0) {
                f2 = drawableBoundaryInfo.mContextRangeY;
            }
            return drawableBoundaryInfo.copy(rectF, rectF2, f, f2);
        }

        public final RectF component1() {
            return this.mDrawableCanvasRect;
        }

        public final RectF component2() {
            return this.mDrawableSurfaceRect;
        }

        public final float component3() {
            return this.mContextRangeX;
        }

        public final float component4() {
            return this.mContextRangeY;
        }

        public final DrawableBoundaryInfo copy(RectF mDrawableCanvasRect, RectF mDrawableSurfaceRect, float f, float f2) {
            Intrinsics.checkNotNullParameter(mDrawableCanvasRect, "mDrawableCanvasRect");
            Intrinsics.checkNotNullParameter(mDrawableSurfaceRect, "mDrawableSurfaceRect");
            return new DrawableBoundaryInfo(mDrawableCanvasRect, mDrawableSurfaceRect, f, f2);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DrawableBoundaryInfo)) {
                return false;
            }
            DrawableBoundaryInfo drawableBoundaryInfo = (DrawableBoundaryInfo) obj;
            return Intrinsics.areEqual(this.mDrawableCanvasRect, drawableBoundaryInfo.mDrawableCanvasRect) && Intrinsics.areEqual(this.mDrawableSurfaceRect, drawableBoundaryInfo.mDrawableSurfaceRect) && Float.compare(this.mContextRangeX, drawableBoundaryInfo.mContextRangeX) == 0 && Float.compare(this.mContextRangeY, drawableBoundaryInfo.mContextRangeY) == 0;
        }

        public final float getMContextRangeX() {
            return this.mContextRangeX;
        }

        public final float getMContextRangeY() {
            return this.mContextRangeY;
        }

        public final RectF getMDrawableCanvasRect() {
            return this.mDrawableCanvasRect;
        }

        public final RectF getMDrawableSurfaceRect() {
            return this.mDrawableSurfaceRect;
        }

        public int hashCode() {
            return Float.hashCode(this.mContextRangeY) + com.coui.appcompat.card.c.a(this.mContextRangeX, (this.mDrawableSurfaceRect.hashCode() + (this.mDrawableCanvasRect.hashCode() * 31)) * 31, 31);
        }

        public String toString() {
            return "DrawableBoundaryInfo(mDrawableCanvasRect=" + this.mDrawableCanvasRect + ", mDrawableSurfaceRect=" + this.mDrawableSurfaceRect + ", mContextRangeX=" + this.mContextRangeX + ", mContextRangeY=" + this.mContextRangeY + ')';
        }
    }

    /* compiled from: OplusStylusImplement.kt */
    @kotlin.coroutines.jvm.internal.c(c = "com.oplusos.vfxsdk.doodleengine.stylus.OplusStylusImplement$OnExtendDrawableRect$1", f = "OplusStylusImplement.kt", l = {}, m = "invokeSuspend")
    /* renamed from: com.oplusos.vfxsdk.doodleengine.stylus.OplusStylusImplement$OnExtendDrawableRect$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements p<z, d<? super Unit>, Object> {
        final /* synthetic */ RectF $rendRect;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(RectF rectF, d<? super AnonymousClass1> dVar) {
            super(2, dVar);
            this.$rendRect = rectF;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final d<Unit> create(Object obj, d<?> dVar) {
            return OplusStylusImplement.this.new AnonymousClass1(this.$rendRect, dVar);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.f6153a;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            OplusStylusImplement oplusStylusImplement = OplusStylusImplement.this;
            oplusStylusImplement.callRendMsg(oplusStylusImplement.lastScaleData);
            PaintEditBgView paintEditBgView = OplusStylusImplement.this.inkBackgroundView;
            if (paintEditBgView != null) {
                paintEditBgView.redraw();
            }
            l lVar = OplusStylusImplement.this.extendCallback;
            if (lVar != null) {
                lVar.invoke(this.$rendRect);
            }
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.p
        public final Object invoke(z zVar, d<? super Unit> dVar) {
            return ((AnonymousClass1) create(zVar, dVar)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* compiled from: OplusStylusImplement.kt */
    public enum PaintLogLeval {
        ANDROID_LOG_ERROR,
        ANDROID_LOG_WARN,
        ANDROID_LOG_INFO,
        ANDROID_LOG_DEBUG
    }

    /* compiled from: OplusStylusImplement.kt */
    public static final class RectTransform {
        private final float mCenterX;
        private final float mCenterY;
        private final float mHeight;
        private final float mRotation;
        private final float mWidth;

        public RectTransform(float f, float f2, float f3, float f4, float f5) {
            this.mCenterX = f;
            this.mCenterY = f2;
            this.mWidth = f3;
            this.mHeight = f4;
            this.mRotation = f5;
        }

        public static /* synthetic */ RectTransform copy$default(RectTransform rectTransform, float f, float f2, float f3, float f4, float f5, int i, Object obj) {
            if ((i & 1) != 0) {
                f = rectTransform.mCenterX;
            }
            if ((i & 2) != 0) {
                f2 = rectTransform.mCenterY;
            }
            float f6 = f2;
            if ((i & 4) != 0) {
                f3 = rectTransform.mWidth;
            }
            float f7 = f3;
            if ((i & 8) != 0) {
                f4 = rectTransform.mHeight;
            }
            float f8 = f4;
            if ((i & 16) != 0) {
                f5 = rectTransform.mRotation;
            }
            return rectTransform.copy(f, f6, f7, f8, f5);
        }

        public final float component1() {
            return this.mCenterX;
        }

        public final float component2() {
            return this.mCenterY;
        }

        public final float component3() {
            return this.mWidth;
        }

        public final float component4() {
            return this.mHeight;
        }

        public final float component5() {
            return this.mRotation;
        }

        public final RectTransform copy(float f, float f2, float f3, float f4, float f5) {
            return new RectTransform(f, f2, f3, f4, f5);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof RectTransform)) {
                return false;
            }
            RectTransform rectTransform = (RectTransform) obj;
            return Float.compare(this.mCenterX, rectTransform.mCenterX) == 0 && Float.compare(this.mCenterY, rectTransform.mCenterY) == 0 && Float.compare(this.mWidth, rectTransform.mWidth) == 0 && Float.compare(this.mHeight, rectTransform.mHeight) == 0 && Float.compare(this.mRotation, rectTransform.mRotation) == 0;
        }

        public final float getMCenterX() {
            return this.mCenterX;
        }

        public final float getMCenterY() {
            return this.mCenterY;
        }

        public final float getMHeight() {
            return this.mHeight;
        }

        public final float getMRotation() {
            return this.mRotation;
        }

        public final float getMWidth() {
            return this.mWidth;
        }

        public int hashCode() {
            return Float.hashCode(this.mRotation) + com.coui.appcompat.card.c.a(this.mHeight, com.coui.appcompat.card.c.a(this.mWidth, com.coui.appcompat.card.c.a(this.mCenterY, Float.hashCode(this.mCenterX) * 31, 31), 31), 31);
        }

        public String toString() {
            return "RectTransform(mCenterX=" + this.mCenterX + ", mCenterY=" + this.mCenterY + ", mWidth=" + this.mWidth + ", mHeight=" + this.mHeight + ", mRotation=" + this.mRotation + ')';
        }
    }

    /* compiled from: OplusStylusImplement.kt */
    public static final class SaveCacheState {
        private boolean cacheForce;
        private boolean cacheKeepTopBlank;
        private long cacheMaxSize;
        private int cacheType;

        public SaveCacheState(int i, long j, boolean z, boolean z2) {
            this.cacheType = i;
            this.cacheMaxSize = j;
            this.cacheForce = z;
            this.cacheKeepTopBlank = z2;
        }

        public static /* synthetic */ SaveCacheState copy$default(SaveCacheState saveCacheState, int i, long j, boolean z, boolean z2, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                i = saveCacheState.cacheType;
            }
            if ((i2 & 2) != 0) {
                j = saveCacheState.cacheMaxSize;
            }
            long j2 = j;
            if ((i2 & 4) != 0) {
                z = saveCacheState.cacheForce;
            }
            boolean z3 = z;
            if ((i2 & 8) != 0) {
                z2 = saveCacheState.cacheKeepTopBlank;
            }
            return saveCacheState.copy(i, j2, z3, z2);
        }

        public final int component1() {
            return this.cacheType;
        }

        public final long component2() {
            return this.cacheMaxSize;
        }

        public final boolean component3() {
            return this.cacheForce;
        }

        public final boolean component4() {
            return this.cacheKeepTopBlank;
        }

        public final SaveCacheState copy(int i, long j, boolean z, boolean z2) {
            return new SaveCacheState(i, j, z, z2);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SaveCacheState)) {
                return false;
            }
            SaveCacheState saveCacheState = (SaveCacheState) obj;
            return this.cacheType == saveCacheState.cacheType && this.cacheMaxSize == saveCacheState.cacheMaxSize && this.cacheForce == saveCacheState.cacheForce && this.cacheKeepTopBlank == saveCacheState.cacheKeepTopBlank;
        }

        public final boolean getCacheForce() {
            return this.cacheForce;
        }

        public final boolean getCacheKeepTopBlank() {
            return this.cacheKeepTopBlank;
        }

        public final long getCacheMaxSize() {
            return this.cacheMaxSize;
        }

        public final int getCacheType() {
            return this.cacheType;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public int hashCode() {
            int iA = com.heytap.cloud.sdk.base.a.a(this.cacheMaxSize, Integer.hashCode(this.cacheType) * 31, 31);
            boolean z = this.cacheForce;
            int i = z;
            if (z != 0) {
                i = 1;
            }
            int i2 = (iA + i) * 31;
            boolean z2 = this.cacheKeepTopBlank;
            return i2 + (z2 ? 1 : z2 ? 1 : 0);
        }

        public final void setCacheForce(boolean z) {
            this.cacheForce = z;
        }

        public final void setCacheKeepTopBlank(boolean z) {
            this.cacheKeepTopBlank = z;
        }

        public final void setCacheMaxSize(long j) {
            this.cacheMaxSize = j;
        }

        public final void setCacheType(int i) {
            this.cacheType = i;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("SaveCacheState(cacheType=");
            sb.append(this.cacheType);
            sb.append(", cacheMaxSize=");
            sb.append(this.cacheMaxSize);
            sb.append(", cacheForce=");
            sb.append(this.cacheForce);
            sb.append(", cacheKeepTopBlank=");
            return h.t(sb, this.cacheKeepTopBlank, ')');
        }
    }

    /* compiled from: OplusStylusImplement.kt */
    public enum SelectionStatus {
        Invalid,
        Selected,
        Cut;

        public static final Companion Companion = new Companion(null);

        /* compiled from: OplusStylusImplement.kt */
        public static final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(e eVar) {
                this();
            }

            public final SelectionStatus getValue(int i) {
                int iOrdinal = SelectionStatus.Cut.ordinal();
                if (i < 0) {
                    i = 0;
                }
                if (iOrdinal > i) {
                    iOrdinal = i;
                }
                return SelectionStatus.values()[iOrdinal];
            }
        }
    }

    /* compiled from: OplusStylusImplement.kt */
    public static final class SplitCountAndContextHeight {
        private final int mContextHeight;
        private final int mSplitCount;
        private final boolean success;

        public SplitCountAndContextHeight(int i, boolean z, int i2) {
            this.mSplitCount = i;
            this.success = z;
            this.mContextHeight = i2;
        }

        public static /* synthetic */ SplitCountAndContextHeight copy$default(SplitCountAndContextHeight splitCountAndContextHeight, int i, boolean z, int i2, int i3, Object obj) {
            if ((i3 & 1) != 0) {
                i = splitCountAndContextHeight.mSplitCount;
            }
            if ((i3 & 2) != 0) {
                z = splitCountAndContextHeight.success;
            }
            if ((i3 & 4) != 0) {
                i2 = splitCountAndContextHeight.mContextHeight;
            }
            return splitCountAndContextHeight.copy(i, z, i2);
        }

        public final int component1() {
            return this.mSplitCount;
        }

        public final boolean component2() {
            return this.success;
        }

        public final int component3() {
            return this.mContextHeight;
        }

        public final SplitCountAndContextHeight copy(int i, boolean z, int i2) {
            return new SplitCountAndContextHeight(i, z, i2);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SplitCountAndContextHeight)) {
                return false;
            }
            SplitCountAndContextHeight splitCountAndContextHeight = (SplitCountAndContextHeight) obj;
            return this.mSplitCount == splitCountAndContextHeight.mSplitCount && this.success == splitCountAndContextHeight.success && this.mContextHeight == splitCountAndContextHeight.mContextHeight;
        }

        public final int getMContextHeight() {
            return this.mContextHeight;
        }

        public final int getMSplitCount() {
            return this.mSplitCount;
        }

        public final boolean getSuccess() {
            return this.success;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public int hashCode() {
            int iHashCode = Integer.hashCode(this.mSplitCount) * 31;
            boolean z = this.success;
            int i = z;
            if (z != 0) {
                i = 1;
            }
            return Integer.hashCode(this.mContextHeight) + ((iHashCode + i) * 31);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("SplitCountAndContextHeight(mSplitCount=");
            sb.append(this.mSplitCount);
            sb.append(", success=");
            sb.append(this.success);
            sb.append(", mContextHeight=");
            return defpackage.d.j(sb, this.mContextHeight, ')');
        }
    }

    /* compiled from: OplusStylusImplement.kt */
    @kotlin.coroutines.jvm.internal.c(c = "com.oplusos.vfxsdk.doodleengine.stylus.OplusStylusImplement$delayedComputeScrollByKeyboard$1", f = "OplusStylusImplement.kt", l = {3318}, m = "invokeSuspend")
    /* renamed from: com.oplusos.vfxsdk.doodleengine.stylus.OplusStylusImplement$delayedComputeScrollByKeyboard$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10331 extends SuspendLambda implements p<z, d<? super Unit>, Object> {
        int label;

        public C10331(d<? super C10331> dVar) {
            super(2, dVar);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final d<Unit> create(Object obj, d<?> dVar) {
            return OplusStylusImplement.this.new C10331(dVar);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.f6153a;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                long j = OplusStylusImplement.this.keyboardRunnableDelayTime;
                this.label = 1;
                if (h0.a(j, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            OplusStylusImplement.this.computeScrollByKeyboard();
            OplusStylusImplement.this.setKeyboardChanging(false);
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.p
        public final Object invoke(z zVar, d<? super Unit> dVar) {
            return ((C10331) create(zVar, dVar)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* compiled from: OplusStylusImplement.kt */
    @kotlin.coroutines.jvm.internal.c(c = "com.oplusos.vfxsdk.doodleengine.stylus.OplusStylusImplement$delayedModifyRect$1", f = "OplusStylusImplement.kt", l = {3402}, m = "invokeSuspend")
    /* renamed from: com.oplusos.vfxsdk.doodleengine.stylus.OplusStylusImplement$delayedModifyRect$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10341 extends SuspendLambda implements p<z, d<? super Unit>, Object> {
        final /* synthetic */ RectF $drawRectF;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10341(RectF rectF, d<? super C10341> dVar) {
            super(2, dVar);
            this.$drawRectF = rectF;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final d<Unit> create(Object obj, d<?> dVar) {
            return OplusStylusImplement.this.new C10341(this.$drawRectF, dVar);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.f6153a;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (h0.a(10L, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            OplusStylusImplement.this.mEntityManager.modifyRectAction(this.$drawRectF);
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.p
        public final Object invoke(z zVar, d<? super Unit> dVar) {
            return ((C10341) create(zVar, dVar)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* compiled from: OplusStylusImplement.kt */
    @kotlin.coroutines.jvm.internal.c(c = "com.oplusos.vfxsdk.doodleengine.stylus.OplusStylusImplement$onPreviewDataReady$1", f = "OplusStylusImplement.kt", l = {}, m = "invokeSuspend")
    /* renamed from: com.oplusos.vfxsdk.doodleengine.stylus.OplusStylusImplement$onPreviewDataReady$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10361 extends SuspendLambda implements p<z, d<? super Unit>, Object> {
        final /* synthetic */ Bitmap $bitmap;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10361(Bitmap bitmap, d<? super C10361> dVar) {
            super(2, dVar);
            this.$bitmap = bitmap;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final d<Unit> create(Object obj, d<?> dVar) {
            return OplusStylusImplement.this.new C10361(this.$bitmap, dVar);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.f6153a;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            l lVar = OplusStylusImplement.this.mPreviewHandwritingCall;
            if (lVar != null) {
                lVar.invoke(this.$bitmap);
            }
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.p
        public final Object invoke(z zVar, d<? super Unit> dVar) {
            return ((C10361) create(zVar, dVar)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* compiled from: OplusStylusImplement.kt */
    @kotlin.coroutines.jvm.internal.c(c = "com.oplusos.vfxsdk.doodleengine.stylus.OplusStylusImplement$onScrollScale$1", f = "OplusStylusImplement.kt", l = {}, m = "invokeSuspend")
    /* renamed from: com.oplusos.vfxsdk.doodleengine.stylus.OplusStylusImplement$onScrollScale$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10371 extends SuspendLambda implements p<z, d<? super Unit>, Object> {
        final /* synthetic */ float $dx;
        final /* synthetic */ float $dy;
        final /* synthetic */ float $scale;
        final /* synthetic */ float $x;
        final /* synthetic */ float $y;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10371(float f, float f2, float f3, float f4, float f5, d<? super C10371> dVar) {
            super(2, dVar);
            this.$scale = f;
            this.$x = f2;
            this.$y = f3;
            this.$dx = f4;
            this.$dy = f5;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final d<Unit> create(Object obj, d<?> dVar) {
            return OplusStylusImplement.this.new C10371(this.$scale, this.$x, this.$y, this.$dx, this.$dy, dVar);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            float f;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.f6153a;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            if (OplusStylusImplement.this.mEnableOverlay) {
                StylusInterface.DefaultImpls.rolling$default(OplusStylusImplement.this, 0.0f, true, false, 4, null);
            }
            OplusStylusImplement.this.lastScaleData.setScale((this.$scale / 2.0f) / OplusStylusImplement.this.mMinScale);
            OplusStylusImplement.this.lastScaleData.setOffsetX(this.$x);
            OplusStylusImplement.this.lastScaleData.setOffsetY(this.$y);
            ScaleData scaleData = OplusStylusImplement.this.lastScaleData;
            float f2 = 0.0f;
            if (Float.isNaN(this.$dx)) {
                f = 0.0f;
            } else {
                f = OplusStylusImplement.this.mMinScale * this.$dx * 2.0f;
            }
            scaleData.setDx(f);
            ScaleData scaleData2 = OplusStylusImplement.this.lastScaleData;
            if (!Float.isNaN(this.$dy)) {
                f2 = OplusStylusImplement.this.mMinScale * this.$dy * 2.0f;
            }
            scaleData2.setDy(f2);
            OplusStylusImplement oplusStylusImplement = OplusStylusImplement.this;
            oplusStylusImplement.callRendMsg(oplusStylusImplement.lastScaleData);
            PaintEditBgView paintEditBgView = OplusStylusImplement.this.inkBackgroundView;
            if (paintEditBgView != null) {
                paintEditBgView.redraw();
            }
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.p
        public final Object invoke(z zVar, d<? super Unit> dVar) {
            return ((C10371) create(zVar, dVar)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* compiled from: OplusStylusImplement.kt */
    @kotlin.coroutines.jvm.internal.c(c = "com.oplusos.vfxsdk.doodleengine.stylus.OplusStylusImplement$previewHandwriting$1", f = "OplusStylusImplement.kt", l = {2431}, m = "invokeSuspend")
    /* renamed from: com.oplusos.vfxsdk.doodleengine.stylus.OplusStylusImplement$previewHandwriting$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10381 extends SuspendLambda implements p<z, d<? super Unit>, Object> {
        final /* synthetic */ int $height;
        final /* synthetic */ int $width;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10381(int i, int i2, d<? super C10381> dVar) {
            super(2, dVar);
            this.$width = i;
            this.$height = i2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final d<Unit> create(Object obj, d<?> dVar) {
            C10381 c10381 = OplusStylusImplement.this.new C10381(this.$width, this.$height, dVar);
            c10381.L$0 = obj;
            return c10381;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            z zVar;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.f6153a;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                z zVar2 = (z) this.L$0;
                this.L$0 = zVar2;
                this.label = 1;
                if (h0.a(200L, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                zVar = zVar2;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                zVar = (z) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            kotlin.jvm.internal.d.i(zVar.getCoroutineContext());
            NativePaint.Companion.drawPreviewImage(OplusStylusImplement.this.mNativeHandle, this.$width, this.$height);
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.p
        public final Object invoke(z zVar, d<? super Unit> dVar) {
            return ((C10381) create(zVar, dVar)).invokeSuspend(Unit.INSTANCE);
        }
    }

    public OplusStylusImplement(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.stepChangeTime = -1L;
        this.mCurScale = 1.0f;
        this.mMinScale = 1.0f;
        this.mMaxScale = 6.0f;
        this.handler = new Handler(Looper.getMainLooper());
        this.mPoints = new float[120];
        this.zoomScale = 1.0f;
        this.mLoadListener = new ArrayList();
        this.mPaintViewListener = new ArrayList();
        this.mPaint = new Paint(Paint.Type.PENCIL, Paint.Stroke.TYPE3.ordinal(), 0.0f, 0.0f, 0.0f, 1.0f);
        this.mCurrentStatus = Operation.PENCIL;
        this.lastValidPoint = new PointF();
        this.lastInvalidPoint = new PointF();
        this.mEntityManager = new EntityManager(context);
        this.imagePath = new ArrayList<>();
        this.mRendRect = new RectF();
        this.mContentRect = new RectF();
        this.lastScaleData = new ScaleData(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 127, null);
        this.canvasExtendMax = 5000;
        this.saveCacheState = new SaveCacheState(0, 0L, false, false);
        this.mVelocityTracker$delegate = kotlin.c.b(new kotlin.jvm.functions.a<VelocityTracker>() { // from class: com.oplusos.vfxsdk.doodleengine.stylus.OplusStylusImplement$mVelocityTracker$2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.a
            public final VelocityTracker invoke() {
                return VelocityTracker.obtain();
            }
        });
        this.fingerTouchDetector = new FingerTouchDetector(new Handler(Looper.getMainLooper()), new FingerTouchDetector.FingerTouchListener() { // from class: com.oplusos.vfxsdk.doodleengine.stylus.OplusStylusImplement$fingerTouchDetector$1
            @Override // com.oplusos.vfxsdk.doodleengine.stylus.FingerTouchDetector.FingerTouchListener
            public void onMultiFingerTap(int i, List<? extends PointF> touchPoints) {
                Intrinsics.checkNotNullParameter(touchPoints, "touchPoints");
                FingerTouchListener fingerTouchListener = this.this$0.fingerTouchListener;
                if (fingerTouchListener != null) {
                    fingerTouchListener.onMultiFingerTap(i, touchPoints);
                }
            }

            @Override // com.oplusos.vfxsdk.doodleengine.stylus.FingerTouchDetector.FingerTouchListener
            public void onSingleFingerLongPress(float f, float f2) {
                FingerTouchListener fingerTouchListener = this.this$0.fingerTouchListener;
                if (fingerTouchListener != null) {
                    fingerTouchListener.onSingleFingerLongPress(f, f2);
                }
            }
        });
        this.keyboardHeightListener = new KeyBoardHelper.KeyboardHeightListener() { // from class: com.oplusos.vfxsdk.doodleengine.stylus.OplusStylusImplement$keyboardHeightListener$1
            @Override // com.oplusos.vfxsdk.doodleengine.util.KeyBoardHelper.KeyboardHeightListener
            public void onKeyboardHeightChanged(int i) {
                if (i == this.this$0.keyboardHeight) {
                    return;
                }
                this.this$0.keyboardHeight = i;
                this.this$0.keyboardRunnableDelayTime = 100;
                this.this$0.setKeyboardChanging(true);
                this.this$0.delayedComputeScrollByKeyboard();
                androidx.appsearch.app.e.v("onKeyboardHeightChanged: ", i, OplusStylusImplement.Companion.getTAG());
            }

            @Override // com.oplusos.vfxsdk.doodleengine.util.KeyBoardHelper.KeyboardHeightListener
            public void onKeyboardVisible(boolean z) {
                com.coui.appcompat.card.c.A("onKeyboardVisible: ", z, OplusStylusImplement.Companion.getTAG());
            }

            @Override // com.oplusos.vfxsdk.doodleengine.util.KeyBoardHelper.KeyboardHeightListener
            public void onNoFullScreenHeighChanged(int i) {
                androidx.appsearch.app.e.v("onNoFullScreenHeighChanged: ", i, OplusStylusImplement.Companion.getTAG());
                if (i < 0) {
                    if (this.this$0.keyboardHeight == 0) {
                        return;
                    }
                    this.this$0.keyboardHeight = 0;
                    this.this$0.keyboardRunnableDelayTime = 100;
                    this.this$0.setKeyboardChanging(true);
                    this.this$0.delayedComputeScrollByKeyboard();
                    return;
                }
                int i2 = i + 120;
                if (i2 == this.this$0.keyboardHeight) {
                    return;
                }
                this.this$0.keyboardHeight = i2;
                this.this$0.keyboardRunnableDelayTime = 100;
                this.this$0.setKeyboardChanging(true);
                this.this$0.delayedComputeScrollByKeyboard();
            }
        };
        Log.d(TAG, "OplusStylusImplement constructor: VERSION_6.0.5");
        this.mContext = context;
        this.mDisplayUtils = new DisplayUtils(this.mContext);
        this.mainScope = a0.b();
        this.g_Debug = true;
        this.mEnableShapeRegular = true;
        this.delayGestureEnd = new com.oplus.richtext.editor.view.toolbar.content.e(this, 13);
        this.mPickEntityLock = new Object();
        this.mPickEntityResult = new String[0];
        this.spaceDistance = 100;
    }

    private final Pair<Float, Float> GetVelocity(MotionEvent motionEvent, boolean z) {
        float xVelocity;
        float yVelocity;
        if (!z) {
            return new Pair<>(Float.valueOf(getVelocityTracker().getXVelocity()), Float.valueOf(getVelocityTracker().getYVelocity()));
        }
        int action = motionEvent.getAction();
        if (action != 0) {
            if (action == 1) {
                xVelocity = getVelocityTracker().getXVelocity();
                yVelocity = getVelocityTracker().getYVelocity();
            } else if (action == 2) {
                getVelocityTracker().addMovement(motionEvent);
                getVelocityTracker().computeCurrentVelocity(1);
                xVelocity = getVelocityTracker().getXVelocity();
                yVelocity = getVelocityTracker().getYVelocity();
            }
            return new Pair<>(Float.valueOf(xVelocity), Float.valueOf(yVelocity));
        }
        getVelocityTracker().clear();
        getVelocityTracker().addMovement(motionEvent);
        xVelocity = 0.0f;
        yVelocity = 0.0f;
        return new Pair<>(Float.valueOf(xVelocity), Float.valueOf(yVelocity));
    }

    public static /* synthetic */ Pair GetVelocity$default(OplusStylusImplement oplusStylusImplement, MotionEvent motionEvent, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: GetVelocity");
        }
        if ((i & 2) != 0) {
            z = true;
        }
        return oplusStylusImplement.GetVelocity(motionEvent, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void callRendMsg(ScaleData scaleData) {
        if (scaleData == null) {
            scaleData = new ScaleData(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 126, null);
        }
        getRendRect();
        configureRendManager();
        if (this.mScrollListener == null || this.inWebviewScroll) {
            Log.d(TAG, "load_save saveRect: callRendMsg return");
            return;
        }
        DLog("callRendMsg: " + this.mRendRect + ", " + scaleData);
        ScrollListener scrollListener = this.mScrollListener;
        if (scrollListener != null) {
            scrollListener.onScrollScale(this.mRendRect, getShowRectf(), scaleData, new p<Integer, Integer, Unit>() { // from class: com.oplusos.vfxsdk.doodleengine.stylus.OplusStylusImplement.callRendMsg.1
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.p
                public /* bridge */ /* synthetic */ Unit invoke(Integer num, Integer num2) {
                    invoke(num.intValue(), num2.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(int i, int i2) {
                    OplusStylusImplement.this.DLog("set web scroll, rend: " + OplusStylusImplement.this.mRendRect + ", max: " + i + ", " + i2 + " ---> " + (((int) OplusStylusImplement.this.mRendRect.top) - i2) + ", " + (((int) OplusStylusImplement.this.mRendRect.bottom) + i));
                    NativePaint.Companion.setMinAndMaxScrollHeight(OplusStylusImplement.this.mNativeHandle, i, i2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void computeScrollByKeyboard() {
        int i = this.scrollByKeyboard;
        this.scrollByKeyboard = 0;
        String str = TAG;
        androidx.appsearch.app.e.y(defpackage.d.p("computeScrollByKeyboard: last:", i, ", height:"), this.keyboardHeight, str);
        if (this.keyboardHeight == 0) {
            if (i > 0) {
                float f = i;
                this.lastScaleData.setDy(f);
                this.mRendRect.offset(0.0f, f);
                setNativeRendRect();
                callRendMsg(this.lastScaleData);
                PaintEditBgView paintEditBgView = this.inkBackgroundView;
                if (paintEditBgView != null) {
                    paintEditBgView.redraw();
                }
                configureRendManager();
                androidx.appsearch.app.e.v("Keyboard hidden, scrolling back by ", i, str);
                RectF currentTextRectF$paint_oplus_release = this.mEntityManager.getCurrentTextRectF$paint_oplus_release();
                if (currentTextRectF$paint_oplus_release == null || currentTextRectF$paint_oplus_release.isEmpty()) {
                    return;
                }
                currentTextRectF$paint_oplus_release.offset(0.0f, f);
                delayedModifyRect(currentTextRectF$paint_oplus_release);
                return;
            }
            return;
        }
        if (!this.mEntityManager.isSpannedEditing()) {
            Log.d(str, "computeScrollByKeyboard: not in span editing");
            return;
        }
        RectF currentTextRectF$paint_oplus_release2 = this.mEntityManager.getCurrentTextRectF$paint_oplus_release();
        if (currentTextRectF$paint_oplus_release2 == null || currentTextRectF$paint_oplus_release2.isEmpty()) {
            com.oplus.cloudkit.util.b.F("computeScrollByKeyboard: spannedContentRectF empty ", currentTextRectF$paint_oplus_release2, str);
            return;
        }
        int focusHeight = this.mEntityManager.getFocusHeight();
        RectF rectF = new RectF(currentTextRectF$paint_oplus_release2);
        float f2 = rectF.bottom;
        if (focusHeight > 0) {
            f2 = rectF.top + focusHeight;
        }
        int i2 = this.visibleHeight;
        StringBuilder sb = new StringBuilder("computeScrollByKeyboard: focusH:");
        sb.append(focusHeight);
        sb.append(" focusBottom:");
        sb.append(f2);
        sb.append(" visibleHeight:");
        androidx.appsearch.app.e.y(sb, i2, str);
        if (f2 > i2) {
            this.scrollByKeyboard = ((int) Math.ceil(this.keyboardHeight)) + this.spaceDistance;
        } else {
            float f3 = 0 + f2;
            int i3 = this.keyboardHeight;
            if (f3 > i2 - i3) {
                this.scrollByKeyboard = ((int) Math.ceil((f3 + i3) - r3)) + this.spaceDistance;
            }
        }
        androidx.appsearch.app.e.y(new StringBuilder("computeScrollByKeyboard: scrollByKeyboard"), this.scrollByKeyboard, str);
        if (this.scrollByKeyboard > 0) {
            this.lastScaleData.setDy(-r0);
            this.mRendRect.offset(0.0f, -this.scrollByKeyboard);
            setNativeRendRect();
            callRendMsg(this.lastScaleData);
            PaintEditBgView paintEditBgView2 = this.inkBackgroundView;
            if (paintEditBgView2 != null) {
                paintEditBgView2.redraw();
            }
            configureRendManager();
            androidx.appsearch.app.e.y(new StringBuilder("Keyboard shown, scrolling up by "), this.scrollByKeyboard, str);
            rectF.offset(0.0f, -this.scrollByKeyboard);
            delayedModifyRect(rectF);
        }
    }

    private final void configureRendManager() {
        CanvasRendRectManager canvasRendRectManager = this.canvasRendRectManager;
        if (canvasRendRectManager == null) {
            Log.w(TAG, "configureRendManager: canvasRendRectManager is null");
        } else if (canvasRendRectManager != null) {
            canvasRendRectManager.configure(this.mRendRect);
        }
    }

    private final boolean containsEvent(MotionEvent motionEvent) {
        if (this.mRendRect.isEmpty()) {
            getRendRect();
            if (this.mRendRect.isEmpty()) {
                return true;
            }
        }
        if (motionEvent.getY() >= this.mRendRect.top || motionEvent.getX() < this.mRendRect.left || motionEvent.getX() > this.mRendRect.right) {
            return motionEvent.getX() >= ((float) Math.floor((double) this.mRendRect.left)) && motionEvent.getX() <= ((float) Math.ceil((double) this.mRendRect.right)) && motionEvent.getY() >= ((float) Math.floor((double) this.mRendRect.top)) && motionEvent.getY() <= ((float) Math.ceil((double) this.mRendRect.bottom));
        }
        Log.d(TAG, "canWriteEvent top: true");
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void delayGestureEnd$lambda$39(OplusStylusImplement this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Log.d(TAG, "scrollFromGestureEnd delayGestureEnd");
        this$0.scrollFromGestureEnd();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void delayedComputeScrollByKeyboard() {
        f1 f1Var = this.scrollKeyboardJob;
        if (f1Var != null) {
            f1Var.a(null);
        }
        z zVar = this.mainScope;
        this.scrollKeyboardJob = zVar != null ? k.n(zVar, null, null, new C10331(null), 3) : null;
    }

    private final void delayedModifyRect(RectF rectF) {
        z zVar = this.mainScope;
        if (zVar != null) {
            k.n(zVar, null, null, new C10341(rectF, null), 3);
        }
    }

    private final float getCanvasTop() {
        RectF rectFSurfaceToCanvasRect = NativePaint.Companion.surfaceToCanvasRect(this.mNativeHandle, new RectF(0.0f, 0.0f, 1.0f, 1.0f));
        if (rectFSurfaceToCanvasRect == null) {
            Log.d(TAG, "getCanvasTop default: 2400.0");
            return TOP_CANVAS_Y;
        }
        androidx.appsearch.app.e.x(new StringBuilder("getCanvasTop: "), rectFSurfaceToCanvasRect.top, TAG);
        return rectFSurfaceToCanvasRect.top;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final RectF getContentRange() {
        RectF doodleContextRange = NativePaint.Companion.getDoodleContextRange(this.mNativeHandle);
        return doodleContextRange == null ? new RectF(0.0f, 0.0f, 0.0f, 0.0f) : doodleContextRange;
    }

    private final void getLassoAddEvent(float f, float f2) {
        float[] fArr = this.mPoints;
        fArr[0] = f;
        fArr[1] = f2;
        if (this.mIsStylus) {
            fArr[2] = this.mPressure;
            fArr[3] = this.mAxisValue;
            fArr[4] = 10.0f;
        } else {
            fArr[2] = 1.0f;
            fArr[3] = 0.0f;
            fArr[4] = 10.0f;
        }
        fArr[5] = (System.nanoTime() / IndexProtocol.CONFIG_VAL_MAX_DOC_CNT) - this.mPreviousTime;
        float[] fArr2 = this.mPoints;
        fArr2[6] = 0.0f;
        fArr2[7] = 0.0f;
        fArr2[8] = 0.0f;
    }

    private final VelocityTracker getMVelocityTracker() {
        Object value = this.mVelocityTracker$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-mVelocityTracker>(...)");
        return (VelocityTracker) value;
    }

    public static final Class<?> getNestedClass(String str) {
        return Companion.getNestedClass(str);
    }

    private final float getRealSize(Paint paint) {
        return paint.getMType() == Paint.Type.ERASER ? paint.getMSize() : paint.getMSize() / 50.0f;
    }

    private final Pair<MotionEvent, Integer> getReplaceEventAndAction(boolean z, MotionEvent motionEvent, int i) {
        if (!z) {
            RendReplacePointUtil.Companion companion = RendReplacePointUtil.Companion;
            companion.getLastInValidPointF().set(motionEvent.getX(), motionEvent.getY());
            if (Float.isNaN(companion.getLastValidPointF().x) || Float.isNaN(companion.getLastValidPointF().y)) {
                return null;
            }
            PointF borderPoint = companion.getBorderPoint(this.mRendRect, motionEvent);
            MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent.getDownTime(), System.currentTimeMillis(), 1, borderPoint.x, borderPoint.y, motionEvent.getMetaState());
            companion.getLastValidPointF().set(Float.NaN, Float.NaN);
            Log.d(TAG, "replace point: call Up ---" + this.mRendRect);
            return new Pair<>(motionEventObtain, 1);
        }
        RendReplacePointUtil.Companion companion2 = RendReplacePointUtil.Companion;
        companion2.getLastValidPointF().set(motionEvent.getX(), motionEvent.getY());
        if (Float.isNaN(companion2.getLastInValidPointF().x) || Float.isNaN(companion2.getLastInValidPointF().y)) {
            return new Pair<>(motionEvent, Integer.valueOf(i));
        }
        PointF borderPoint2 = companion2.getBorderPoint(this.mRendRect, motionEvent);
        Log.d(TAG, "replace point: call Down ---" + this.mRendRect + ", [" + motionEvent.getX() + ", " + motionEvent.getY() + "]/" + borderPoint2);
        MotionEvent motionEventObtain2 = MotionEvent.obtain(motionEvent.getDownTime(), System.currentTimeMillis(), 0, borderPoint2.x, borderPoint2.y, motionEvent.getMetaState());
        companion2.getLastInValidPointF().set(Float.NaN, Float.NaN);
        return new Pair<>(motionEventObtain2, 0);
    }

    public static final Triple<Integer, Boolean, Integer> getSplitCountDetachMain(String str, float f, int i) {
        return Companion.getSplitCountDetachMain(str, f, i);
    }

    private final int getTouchEvent(MotionEvent motionEvent, int i, int i2, boolean z) {
        int i3;
        if (i2 >= 120) {
            return i2;
        }
        Pair<Float, Float> pairGetVelocity = GetVelocity(motionEvent, z);
        float fFloatValue = pairGetVelocity.a().floatValue();
        float fFloatValue2 = pairGetVelocity.b().floatValue();
        this.mPoints[i2] = motionEvent.getX(i);
        int i4 = i2 + 2;
        this.mPoints[i2 + 1] = motionEvent.getY(i);
        if (this.mIsStylus) {
            this.mPoints[i4] = motionEvent.getPressure(i);
            int i5 = i2 + 4;
            this.mPoints[i2 + 3] = motionEvent.getAxisValue(25, i);
            i3 = i2 + 5;
            this.mPoints[i5] = 10.0f;
        } else {
            float[] fArr = this.mPoints;
            fArr[i4] = 1.0f;
            int i6 = i2 + 4;
            fArr[i2 + 3] = 0.0f;
            i3 = i2 + 5;
            fArr[i6] = 10.0f;
        }
        this.mPoints[i3] = motionEvent.getEventTime() - motionEvent.getDownTime();
        float[] fArr2 = this.mPoints;
        fArr2[i3 + 1] = fFloatValue;
        int i7 = i3 + 3;
        fArr2[i3 + 2] = fFloatValue2;
        int i8 = i3 + 4;
        fArr2[i7] = 0.0f;
        androidx.appsearch.app.e.y(com.coui.appcompat.card.c.q("InterpolateSpeedAndTime: vx: ", fFloatValue, ", vy: ", fFloatValue2, ", "), i8, TAG);
        return i8;
    }

    private final int getTouchHistoryEvent(MotionEvent motionEvent, int i, int i2, boolean z) {
        int i3;
        if (i2 >= 120) {
            return i2;
        }
        this.mPoints[i2] = motionEvent.getHistoricalX(i);
        int i4 = i2 + 2;
        this.mPoints[i2 + 1] = motionEvent.getHistoricalY(i);
        if (this.mIsStylus) {
            this.mPoints[i4] = motionEvent.getHistoricalPressure(i);
            int i5 = i2 + 4;
            this.mPoints[i2 + 3] = motionEvent.getHistoricalAxisValue(25, i);
            i3 = i2 + 5;
            this.mPoints[i5] = motionEvent.getHistoricalOrientation(i);
        } else {
            float[] fArr = this.mPoints;
            fArr[i4] = 1.0f;
            int i6 = i2 + 4;
            fArr[i2 + 3] = 0.0f;
            i3 = i2 + 5;
            fArr[i6] = 10.0f;
        }
        this.mPoints[i3] = motionEvent.getHistoricalEventTime(i) - this.mPreviousTime;
        Pair<Float, Float> pairGetVelocity = GetVelocity(motionEvent, z);
        float fFloatValue = pairGetVelocity.a().floatValue();
        float fFloatValue2 = pairGetVelocity.b().floatValue();
        float[] fArr2 = this.mPoints;
        fArr2[i3 + 1] = fFloatValue;
        int i7 = i3 + 3;
        fArr2[i3 + 2] = fFloatValue2;
        int i8 = i3 + 4;
        fArr2[i7] = 0.0f;
        return i8;
    }

    private final VelocityTracker getVelocityTracker() {
        return getMVelocityTracker();
    }

    private final void initImageViewTrans() {
        setMinScale(this.mMinScale);
        setMaxScale(this.mMaxScale);
        NativePaint.Companion companion = NativePaint.Companion;
        companion.setCanvasRect(this.mNativeHandle, this.mImageWidth, this.mImageHeight);
        companion.initImageViewTrans(this.mNativeHandle, this.mTx, this.mTy, this.mCurScale);
    }

    private final void onPickEntity(String[] strArr) {
        synchronized (this.mPickEntityLock) {
            this.mPickEntityResult = strArr;
            this.mPickEntityLock.notify();
            Unit unit = Unit.INSTANCE;
        }
    }

    public static final void prepareNativeTouchData(float[] fArr, float f, float f2, long j) {
        Companion.prepareNativeTouchData(fArr, f, f2, j);
    }

    private final void registerKeyboard(FrameLayout frameLayout) {
        KeyBoardHelper keyBoardHelper = new KeyBoardHelper(getActivity(this.mContext), frameLayout, -1);
        this.keyBoardHelper = keyBoardHelper;
        KeyBoardHelper.KeyboardHeightListener keyboardHeightListener = this.keyboardHeightListener;
        if (keyboardHeightListener != null) {
            keyBoardHelper.registerKeyboardHeightListener(keyboardHeightListener);
        }
    }

    private final void resetTempExtendValue() {
        this.mTempExtendUpValue = 0.0f;
    }

    private final void runDelayGestureEnd() {
        this.handler.removeCallbacks(this.delayGestureEnd);
        this.handler.postDelayed(this.delayGestureEnd, 100L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void runOnUiThread$lambda$38(kotlin.jvm.functions.a block) {
        Intrinsics.checkNotNullParameter(block, "$block");
        block.invoke();
    }

    private final void selectLassoAddPosition() {
        float menuPositionX = getMenuPositionX();
        float menuPositionY = getMenuPositionY();
        float f = 88;
        float f2 = this.zoomScale;
        float f3 = (f * f2) + menuPositionX;
        float f4 = (f * f2) + menuPositionY;
        if ((getMenuWidthValue() / 2) + f3 <= this.mPaintWidth) {
            menuPositionX = f3;
        }
        long jNanoTime = System.nanoTime();
        this.mPreviousTime = jNanoTime / IndexProtocol.CONFIG_VAL_MAX_DOC_CNT;
        this.mInLassoAddOne = true;
        getLassoAddEvent(menuPositionX, f4);
        NativePaint.Companion companion = NativePaint.Companion;
        companion.setInputType(this.mNativeHandle, this.mIsStylus);
        companion.touchEvent(this.mNativeHandle, 0, jNanoTime, 1, 1, this.mPoints);
        getLassoAddEvent(menuPositionX, f4);
        companion.touchEvent(this.mNativeHandle, 2, System.nanoTime(), 1, 1, this.mPoints);
        getLassoAddEvent(menuPositionX, f4);
        companion.touchEvent(this.mNativeHandle, 1, System.nanoTime(), 1, 1, this.mPoints);
    }

    private final void setImageViewTrans(float[] fArr) {
        this.mCurScale = fArr[0];
        this.mTx = fArr[2];
        this.mTy = fArr[5];
    }

    private final void setNativeRendRect() {
        NativePaint.Companion companion = NativePaint.Companion;
        long j = this.mNativeHandle;
        RectF rectF = this.mRendRect;
        companion.initDrawableRect(j, rectF.left, rectF.bottom, rectF.right, rectF.top);
        DLog("setNativeRendRect: [" + this.mRendRect.left + ", " + this.mRendRect.bottom + ", " + this.mRendRect.right + ", " + this.mRendRect.top + ']');
    }

    private final RectF toRectF(RectTransform rectTransform) {
        if (rectTransform.getMRotation() == 0.0f) {
            return new RectF(rectTransform.getMCenterX() - (rectTransform.getMWidth() / 2.0f), rectTransform.getMCenterY() - (rectTransform.getMHeight() / 2.0f), (rectTransform.getMWidth() / 2.0f) + rectTransform.getMCenterX(), (rectTransform.getMHeight() / 2.0f) + rectTransform.getMCenterY());
        }
        float mWidth = rectTransform.getMWidth() / 2.0f;
        float mHeight = rectTransform.getMHeight() / 2.0f;
        PointF[] pointFArr = {new PointF(rectTransform.getMCenterX() - mWidth, rectTransform.getMCenterY() - mHeight), new PointF(rectTransform.getMCenterX() + mWidth, rectTransform.getMCenterY() - mHeight), new PointF(rectTransform.getMCenterX() + mWidth, rectTransform.getMCenterY() + mHeight), new PointF(rectTransform.getMCenterX() - mWidth, rectTransform.getMCenterY() + mHeight)};
        double radians = (float) Math.toRadians(rectTransform.getMRotation());
        float fCos = (float) Math.cos(radians);
        float fSin = (float) Math.sin(radians);
        ArrayList arrayList = new ArrayList(4);
        for (int i = 0; i < 4; i++) {
            PointF pointF = pointFArr[i];
            float mCenterX = pointF.x - rectTransform.getMCenterX();
            float mCenterY = pointF.y - rectTransform.getMCenterY();
            arrayList.add(new PointF(rectTransform.getMCenterX() + ((mCenterX * fCos) - (mCenterY * fSin)), rectTransform.getMCenterY() + (mCenterY * fCos) + (mCenterX * fSin)));
        }
        Iterator it = arrayList.iterator();
        if (!it.hasNext()) {
            throw new NoSuchElementException();
        }
        float fMin = ((PointF) it.next()).x;
        while (it.hasNext()) {
            fMin = Math.min(fMin, ((PointF) it.next()).x);
        }
        Iterator it2 = arrayList.iterator();
        if (!it2.hasNext()) {
            throw new NoSuchElementException();
        }
        float fMin2 = ((PointF) it2.next()).y;
        while (it2.hasNext()) {
            fMin2 = Math.min(fMin2, ((PointF) it2.next()).y);
        }
        Iterator it3 = arrayList.iterator();
        if (!it3.hasNext()) {
            throw new NoSuchElementException();
        }
        float fMax = ((PointF) it3.next()).x;
        while (it3.hasNext()) {
            fMax = Math.max(fMax, ((PointF) it3.next()).x);
        }
        Iterator it4 = arrayList.iterator();
        if (!it4.hasNext()) {
            throw new NoSuchElementException();
        }
        float fMax2 = ((PointF) it4.next()).y;
        while (it4.hasNext()) {
            fMax2 = Math.max(fMax2, ((PointF) it4.next()).y);
        }
        return new RectF(fMin, fMin2, fMax, fMax2);
    }

    public final void DLog(String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (this.mNativeHandle == 0) {
            defpackage.d.z("writeLog error, mNativeHandle == 0L, ", msg, TAG);
            return;
        }
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
        NativePaint.Companion.writeLog(this.mNativeHandle, "[" + stackTraceElement.getFileName() + ':' + stackTraceElement.getMethodName() + '@' + stackTraceElement.getLineNumber() + "] " + msg, PaintLogLeval.ANDROID_LOG_DEBUG.ordinal());
    }

    public final void ELog(String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (this.mNativeHandle == 0) {
            com.heytap.cloud.sdk.base.a.u("writeLog error, mNativeHandle == 0L, ", msg, TAG);
            return;
        }
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
        NativePaint.Companion.writeLog(this.mNativeHandle, "[" + stackTraceElement.getFileName() + ':' + stackTraceElement.getMethodName() + '@' + stackTraceElement.getLineNumber() + "] " + msg, PaintLogLeval.ANDROID_LOG_ERROR.ordinal());
    }

    public final void ILog(String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (this.mNativeHandle == 0) {
            Log.i(TAG, "writeLog error, mNativeHandle == 0L, " + msg);
            return;
        }
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
        NativePaint.Companion.writeLog(this.mNativeHandle, "[" + stackTraceElement.getFileName() + ':' + stackTraceElement.getMethodName() + '@' + stackTraceElement.getLineNumber() + "] " + msg, PaintLogLeval.ANDROID_LOG_INFO.ordinal());
    }

    public final void OnExtendDrawableRect(float f, boolean z, float f2) {
        RectF rendRect = getRendRect();
        DLog("OnExtendDrawableRect dy: " + f + " , extendUp: " + z + ", extendHeight: " + f2 + ", " + rendRect);
        z zVar = this.mainScope;
        if (zVar != null) {
            k.n(zVar, null, null, new AnonymousClass1(rendRect, null), 3);
        }
    }

    public final void OnExtendReachedBoundary(DrawableBoundaryInfo info) {
        Intrinsics.checkNotNullParameter(info, "info");
        DLog("OnExtendReachedBoundary: " + info.getMDrawableCanvasRect());
    }

    public final void WLog(String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (this.mNativeHandle == 0) {
            Log.w(TAG, "writeLog error, mNativeHandle == 0L, " + msg);
            return;
        }
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
        NativePaint.Companion.writeLog(this.mNativeHandle, "[" + stackTraceElement.getFileName() + ':' + stackTraceElement.getMethodName() + '@' + stackTraceElement.getLineNumber() + "] " + msg, PaintLogLeval.ANDROID_LOG_WARN.ordinal());
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void addAdapterFontDownLoadListener(IDownloadListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void addCustomBitmapMenu(List<MenuItemData> menuList, StylusInterface.BitmapMenuCallback bitmapMenuCallback) {
        Intrinsics.checkNotNullParameter(menuList, "menuList");
        Intrinsics.checkNotNullParameter(bitmapMenuCallback, "bitmapMenuCallback");
        ToolkitModel.Companion.setExtraMenuList(q.x2(menuList));
        this.bitmapMenuCallback = bitmapMenuCallback;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void addDownloadListener(IDownloadListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void addEntity(String id, String extra, float[] position, float f, float[] scaling, EntityType type, Color color, Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(extra, "extra");
        Intrinsics.checkNotNullParameter(position, "position");
        Intrinsics.checkNotNullParameter(scaling, "scaling");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(color, "color");
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        if (!(!Intrinsics.areEqual(id, ""))) {
            throw new IllegalArgumentException("Must: id != \"\"".toString());
        }
        if (position.length != 2) {
            throw new IllegalArgumentException("Must: position.size == 2".toString());
        }
        if (scaling.length != 2) {
            throw new IllegalArgumentException("Must: scaling.size == 2".toString());
        }
        for (float f2 : scaling) {
            if (f2 <= 0.0f) {
                throw new IllegalArgumentException("Must: scaling > 0".toString());
            }
        }
        NativePaint.Companion.addEntity(this.mNativeHandle, id, extra, position, f, scaling, type.ordinal(), color.toArgb(), bitmap);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void addListener(PaintViewListener paintViewListener) {
        if (paintViewListener != null) {
            synchronized (this) {
                this.mPaintViewListener.add(paintViewListener);
            }
        }
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void addLoadListener(LoadListener loadListener) {
        if (loadListener != null) {
            synchronized (this) {
                this.mLoadListener.add(loadListener);
            }
        }
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void bindRecognizeFile(String entDir) {
        Intrinsics.checkNotNullParameter(entDir, "entDir");
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public boolean calculateCompressBottom(int i) {
        return true;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public boolean calculateCompressTop(int i) {
        return true;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public boolean canAutoSave() {
        return true;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public boolean canShowPastePopup() {
        return !NativePaint.Companion.clipboardEmpty(this.mNativeHandle);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void cancelLastDrawnContent(float f, float f2) {
        if (!this.mTouching) {
            Log.w(TAG, "Can't send CANCEL because no touching now.");
            return;
        }
        long jNanoTime = System.nanoTime() / SpringOverScroller.ReboundOverScroller.NANOS_PER_MS;
        Companion.prepareNativeTouchData(this.mPoints, f, f2, jNanoTime - this.mDownTime);
        NativePaint.Companion.touchEvent(this.mNativeHandle, 3, jNanoTime, 1, 1, this.mPoints);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void changeBeautifyFont(FontType font) {
        Intrinsics.checkNotNullParameter(font, "font");
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void changeSelectedColor(float f, float f2, float f3, float f4) {
        float fA1 = j.A1(1.0f, j.z1(f, 0.0f));
        float fA12 = j.A1(1.0f, j.z1(f2, 0.0f));
        float fA13 = j.A1(1.0f, j.z1(f3, 0.0f));
        float fA14 = j.A1(1.0f, j.z1(f4, 0.0f));
        int iOrdinal = this.mCurrentStatus.ordinal();
        if (iOrdinal == Operation.Selection.ordinal()) {
            NativePaint.Companion.changeSelectedColor(this.mNativeHandle, fA1, fA12, fA13, fA14);
        }
        String str = TAG;
        StringBuilder sbQ = com.coui.appcompat.card.c.q("changeSelectedColor, red: ", fA1, ", green: ", fA12, ", blue: ");
        sbQ.append(fA13);
        sbQ.append(", index: ");
        sbQ.append(iOrdinal);
        Log.d(str, sbQ.toString());
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void clear() {
        if (!this.mTouching) {
            NativePaint.Companion.reset$default(NativePaint.Companion, this.mNativeHandle, false, 2, null);
            TrackEngineCallback trackEngineCallback = this.mTrackEngine;
            if (trackEngineCallback != null) {
                trackEngineCallback.trackEraserInfo(EraserType.ClearEraser.ordinal());
            }
        }
        DLog("Clear mTouching: " + this.mTouching);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void destroySurface() {
        NativePaint.Companion.surfaceDestroyed(this.mNativeHandle);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void destroySurfaceWithSurface(Surface surface) {
        Intrinsics.checkNotNullParameter(surface, "surface");
        NativePaint.Companion.surfaceDestroyedWithSurface(this.mNativeHandle, surface);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void doPointSelect(float f, float f2) {
        if (isFingerTouchValidElement(f, f2)) {
            String[] strArr = this.mPickEntityResult;
            if (strArr.length == 0) {
                Log.w(TAG, "pickEntity success but result is empty.");
            } else {
                NativePaint.Companion.directSelectEntity(this.mNativeHandle, (String) kotlin.collections.k.Y1(strArr));
            }
        }
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void drawSizePoint(float f) {
        NativePaint.Companion.drawSizePoint(this.mNativeHandle, f);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void enableInputTextMode(boolean z) {
        if (this.isInputTextMode == z) {
            return;
        }
        com.coui.appcompat.card.c.A("setInputTextMode ", z, TAG);
        EntityManager.cancelSpanEdit$default(this.mEntityManager, false, 1, null);
        this.isInputTextMode = z;
        this.mEntityManager.setInputTextMode(z);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void enableOverlay() {
        NativePaint.Companion.enableOverlay(this.mNativeHandle);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void enablePreviewMode(boolean z) {
        com.coui.appcompat.card.c.A("enablePreviewMode: ", z, TAG);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void enableRulerMode(boolean z) {
        com.coui.appcompat.card.c.A("enableRulerMode: ", z, TAG);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void enableShapeRegular(boolean z) {
        this.mEnableShapeRegular = z;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void exit() {
        DLog("exit");
        NativePaint.Companion companion = NativePaint.Companion;
        companion.releaseBackGroundGlobalRef(this.mNativeHandle);
        companion.destroyRenderThread(this.mNativeHandle);
        Log.d(TAG, "destroy instance mNativeHandle: " + this.mNativeHandle);
        StylusManager stylusManager = this.mStylusManager;
        if (stylusManager != null) {
            stylusManager.destroyEvent();
        }
        TrackEngineCallback trackEngineCallback = this.mTrackEngine;
        if (trackEngineCallback != null) {
            trackEngineCallback.commit();
        }
        this.mTrackEngine = null;
        this.mStylusManager = null;
        this.mPreviewHandwritingCall = null;
        f1 f1Var = this.previewJob;
        if (f1Var != null) {
            f1Var.a(null);
        }
        this.previewJob = null;
        z zVar = this.mainScope;
        if (zVar != null) {
            a0.c(zVar, null);
        }
        this.mainScope = null;
        this.handler.removeCallbacksAndMessages(null);
        this.fingerTouchDetector.destroy();
        companion.exit(this.mNativeHandle);
        this.mNativeHandle = 0L;
        this.mContext = null;
        this.mAssetManager = null;
        this.mEntityManager.release();
        this.bitmapMenuCallback = null;
        this.extendCallback = null;
        KeyBoardHelper keyBoardHelper = this.keyBoardHelper;
        if (keyBoardHelper != null) {
            keyBoardHelper.unregisterKeyboardHeightListener();
        }
        this.keyboardHeightListener = null;
        f1 f1Var2 = this.scrollKeyboardJob;
        if (f1Var2 != null) {
            f1Var2.a(null);
        }
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void exportBitmapFromPaint(String paintPath, String str, int i, int i2, l<? super Bitmap, Unit> lVar) {
        Intrinsics.checkNotNullParameter(paintPath, "paintPath");
        if (lVar != null) {
            lVar.invoke(null);
        }
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void extendCanvas(float f) {
        NativePaint.Companion.extendCanvas$default(NativePaint.Companion, this.mNativeHandle, f, false, 4, null);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void finishSelect() {
        NativePaint.Companion.directSelectEntity(this.mNativeHandle, "finish_select_none");
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void forceScroll(float f) {
        this.inWebviewScroll = true;
        rolling(-f, false, true);
        Log.d(TAG, "forceScroll end");
        this.inWebviewScroll = false;
    }

    public final Activity getActivity(Context context) {
        if (context == null) {
            return null;
        }
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            return getActivity(((ContextWrapper) context).getBaseContext());
        }
        if (context instanceof ContextThemeWrapper) {
            return getActivity(((ContextThemeWrapper) context).getBaseContext());
        }
        Log.e("Utils", "Didn't find Activity!");
        return null;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public int getCanvaHeight() {
        return NativePaint.Companion.getCanvasHeight(this.mNativeHandle);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public Bitmap getCanvasBitmap() {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(this.mImageWidth, this.mImageHeight, Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(width, height, config)");
        this.mCanvasBitmap = bitmapCreateBitmap;
        NativePaint.Companion companion = NativePaint.Companion;
        long j = this.mNativeHandle;
        Intrinsics.checkNotNull(bitmapCreateBitmap);
        companion.getCanvasBitmap(j, bitmapCreateBitmap);
        if (this.mCanvasBitmap == null) {
            Log.e(TAG, "mCanvasBitmap is Empty");
        }
        return this.mCanvasBitmap;
    }

    public final CanvasRendRectManager getCanvasRendRectManager() {
        return this.canvasRendRectManager;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public float getContentBottomInScreen() {
        NativePaint.Companion companion = NativePaint.Companion;
        RectF doodleContextRange = companion.getDoodleContextRange(this.mNativeHandle);
        if (doodleContextRange == null) {
            Log.d(TAG, "getContentBottomInScreen return, contentRange == null");
            return 0.0f;
        }
        RectF rectFCanvasToSurfaceRect = companion.canvasToSurfaceRect(this.mNativeHandle, doodleContextRange);
        if (rectFCanvasToSurfaceRect != null) {
            return rectFCanvasToSurfaceRect.bottom;
        }
        return 0.0f;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public float getContentOffsetX() {
        return NativePaint.Companion.getContentOffsetX(this.mNativeHandle);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public float getContentOffsetY() {
        return NativePaint.Companion.getContentOffsetY(this.mNativeHandle);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public float getContentScale() {
        float contentScale = NativePaint.Companion.getContentScale(this.mNativeHandle);
        Log.d(TAG, "getContentScale: " + contentScale);
        return contentScale;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public Paint getCurrentPaint() {
        Paint paint = new Paint();
        paint.setValue(this.mPaint);
        return paint;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public int getDisplayHeight() {
        return NativePaint.Companion.getDisplayHeight(this.mNativeHandle);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public float[][][] getDisplayStrokes() {
        long j = this.mNativeHandle;
        return j != 0 ? NativePaint.Companion.getDisplayStrokes(j) : new float[0][][];
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public IDownloadListener getFontDownLoadListener() {
        return null;
    }

    public final boolean getG_Debug() {
        return this.g_Debug;
    }

    public final boolean getKeyboardChanging() {
        return this.keyboardChanging;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public RectF getLassoRect() {
        RectTransform selectedTrans = NativePaint.Companion.getSelectedTrans(this.mNativeHandle);
        if (selectedTrans != null) {
            return toRectF(selectedTrans);
        }
        return null;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public FileCode getLoadedStatus() {
        return FileCode.Companion.getValue(NativePaint.Companion.getLoadedStatus(this.mNativeHandle));
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public int getMaxPaintHeight() {
        return NativePaint.Companion.getMaxPaintHeight(this.mNativeHandle);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public float getMaxScale() {
        return NativePaint.Companion.getMaxScaleFactor(this.mNativeHandle);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public int getMenuHeightValue() {
        return (int) NativePaint.Companion.menuHeightValue(this.mNativeHandle);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public float getMenuPositionX() {
        return NativePaint.Companion.menuPositionX(this.mNativeHandle);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public float getMenuPositionY() {
        return NativePaint.Companion.menuPositionY(this.mNativeHandle);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public int getMenuRotateValue() {
        return (int) NativePaint.Companion.menuRotateValue(this.mNativeHandle);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public int getMenuType() {
        return NativePaint.Companion.menuType(this.mNativeHandle);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public int getMenuWidthValue() {
        return (int) NativePaint.Companion.menuWidthValue(this.mNativeHandle);
    }

    public final long getNativeHandle() {
        return this.mNativeHandle;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public boolean getOnlyStylus() {
        return this.mOnlyStylus;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public long getPaintFileSize() {
        return NativePaint.Companion.getPaintFileSize(this.mNativeHandle);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public int getPaintHeight() {
        return NativePaint.Companion.getPaintHeight(this.mNativeHandle);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public ImplPlatform getPlatform() {
        return ImplPlatform.PlatForm_O;
    }

    public final float getRealScale(float f) {
        return f / 2.0f;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public boolean getRedoStatus() {
        if (this.mEntityManager.isSpannedEditing() && this.mEntityManager.canRedo()) {
            return true;
        }
        return NativePaint.Companion.getRedoStatus(this.mNativeHandle);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public Bitmap getRelativeCanvasBitmap() {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(this.mRelativeCanvasWidth, this.mRelativeCanvasHeight, Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(width, height, config)");
        this.mRelativeCanvasBitmap = bitmapCreateBitmap;
        NativePaint.Companion companion = NativePaint.Companion;
        long j = this.mNativeHandle;
        Intrinsics.checkNotNull(bitmapCreateBitmap);
        companion.getRelativeCanvasBitmap(j, bitmapCreateBitmap);
        if (this.mRelativeCanvasBitmap == null) {
            Log.e(TAG, "mRelativeCanvasBitmap is Empty");
        }
        return this.mRelativeCanvasBitmap;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public RectF getRendRect() {
        if (this.inWebviewScroll && !this.mRendRect.isEmpty()) {
            return this.mRendRect;
        }
        RectF drawableSurfaceRect = NativePaint.Companion.getDrawableSurfaceRect(this.mNativeHandle);
        if (drawableSurfaceRect == null) {
            Log.d(TAG, "getRendRect: rect is null");
            return new RectF(0.0f, 0.0f, 1.0f, 1.0f);
        }
        this.mRendRect.set(drawableSurfaceRect);
        com.oplus.cloudkit.util.b.F("getRendRect: ", drawableSurfaceRect, TAG);
        return drawableSurfaceRect;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public float getRotateIconPositionX() {
        return NativePaint.Companion.rotateIconPositionX(this.mNativeHandle);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public float getRotateIconPositionY() {
        return NativePaint.Companion.rotateIconPositionY(this.mNativeHandle);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public FileCode getSavedStatus() {
        return FileCode.Companion.getValue(NativePaint.Companion.getSavedStatus(this.mNativeHandle));
    }

    public final float getScaleInSDK(float f) {
        return f * 2.0f;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public RectF getSelectedRecF() {
        RectTransform selectedTrans = NativePaint.Companion.getSelectedTrans(this.mNativeHandle);
        if (selectedTrans != null) {
            return toRectF(selectedTrans);
        }
        return null;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public RectF getShowRectf() {
        return new RectF(0.0f, 0.0f, 1.0f, 1.0f);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public Pair<Integer, Boolean> getSplitCount(String paintPath) {
        Intrinsics.checkNotNullParameter(paintPath, "paintPath");
        return NativePaint.Companion.getSplitCount(this.mNativeHandle, paintPath);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public long getStepChangeTime() {
        return this.stepChangeTime;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public float getTopBlank() {
        return NativePaint.Companion.getTopBlank(this.mNativeHandle);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public boolean getUndoStatus() {
        if (this.mEntityManager.isSpannedEditing() && this.mEntityManager.canUndo()) {
            return true;
        }
        return NativePaint.Companion.getUndoStatus(this.mNativeHandle);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void initAuth(kotlin.jvm.functions.a<Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        block.invoke();
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void initCanvas(float[] values) {
        Intrinsics.checkNotNullParameter(values, "values");
        setImageViewTrans(values);
        initImageViewTrans();
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void initContent(float f, float f2, float f3) {
        NativePaint.Companion.initContentTrans(this.mNativeHandle, f, f2, f3);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void initFragmentManager(FragmentManager fragmentManager, boolean z) {
        if (fragmentManager != null) {
            ToolkitModel.Companion.setInLockScreen(z);
        }
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void initRender(boolean z) {
        Context context = this.mContext;
        if (context == null) {
            Log.e(TAG, "initRenderThread, context is null, do nothing");
            return;
        }
        Intrinsics.checkNotNull(context);
        AssetManager assets = context.getAssets();
        this.mAssetManager = assets;
        if (assets == null) {
            Log.e(TAG, "initRenderThread, AssetsManager is null, do nothing");
            return;
        }
        DisplayUtils displayUtils = this.mDisplayUtils;
        Intrinsics.checkNotNull(displayUtils);
        displayUtils.getDisplaySize();
        Context context2 = this.mContext;
        Intrinsics.checkNotNull(context2);
        float f = context2.getResources().getDisplayMetrics().density;
        Context context3 = this.mContext;
        Intrinsics.checkNotNull(context3);
        float f2 = context3.getResources().getDisplayMetrics().xdpi;
        Context context4 = this.mContext;
        Intrinsics.checkNotNull(context4);
        float f3 = context4.getResources().getDisplayMetrics().ydpi;
        NativePaint.Companion companion = NativePaint.Companion;
        long j = this.mNativeHandle;
        AssetManager assetManager = this.mAssetManager;
        Intrinsics.checkNotNull(assetManager);
        Context context5 = this.mContext;
        Intrinsics.checkNotNull(context5);
        String absolutePath = context5.getFilesDir().getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "mContext!!.filesDir.absolutePath");
        DisplayUtils displayUtils2 = this.mDisplayUtils;
        Intrinsics.checkNotNull(displayUtils2);
        int mDeviceWidth = displayUtils2.getMDeviceWidth();
        DisplayUtils displayUtils3 = this.mDisplayUtils;
        Intrinsics.checkNotNull(displayUtils3);
        int mDeviceHeight = displayUtils3.getMDeviceHeight();
        DisplayUtils displayUtils4 = this.mDisplayUtils;
        Intrinsics.checkNotNull(displayUtils4);
        long jInitRenderThread = companion.initRenderThread(j, this, assetManager, absolutePath, mDeviceWidth, mDeviceHeight, f, f2, f3, displayUtils4.getMRefreshRate(), this.mImageWidth, this.mImageHeight, this.mCanvasType, z);
        this.mNativeHandle = jInitRenderThread;
        companion.setDarkTheme(jInitRenderThread, this.mDarkTheme);
        if (this.mNativeHandle != 0) {
            this.mInitialized = true;
            TrackEngineCallback trackEngineCallback = this.mTrackEngine;
            if (trackEngineCallback != null) {
                Context context6 = this.mContext;
                Intrinsics.checkNotNull(context6);
                trackEngineCallback.initEnd(context6.getApplicationContext(), this.mNativeHandle);
            }
        }
        companion.setOnlyStylusDrawing(this.mNativeHandle, this.mOnlyStylus);
        Log.d(TAG, "initRenderThread, instance: " + hashCode() + ", native: " + this.mNativeHandle);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void initStylusManager(StylusManager stylusManager) {
        this.mStylusManager = stylusManager;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void initSurface(int i, FrameLayout container, boolean z, boolean z2, kotlin.jvm.functions.q<? super PaintSurfaceView, ? super PaintTextureView, ? super PaintEditBgView, Unit> onSurfaceAdded) {
        PaintTextureView paintTextureView;
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(onSurfaceAdded, "onSurfaceAdded");
        this.mEnableOverlay = z2;
        Context context = this.mContext;
        if (context != null) {
            PaintEditBgView paintEditBgView = new PaintEditBgView(context, this.canvasRendRectManager);
            this.inkBackgroundView = paintEditBgView;
            container.addView(paintEditBgView);
            ViewGroup.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
            PaintTextureView paintTextureView2 = null;
            paintSurfaceView = null;
            paintSurfaceView = null;
            PaintSurfaceView paintSurfaceView = null;
            if (i != 0) {
                if (i != 1) {
                    paintTextureView = null;
                } else {
                    paintTextureView = new PaintTextureView(context);
                    paintTextureView.setPaintInstance(this);
                    container.addView(paintTextureView, layoutParams);
                    paintTextureView.enableTextureSurface(true);
                    paintTextureView.setTransparent();
                }
            } else if (z && z2) {
                paintTextureView = new PaintTextureView(context);
                paintTextureView.setPaintInstance(this);
                paintTextureView.enableTextureSurface(true);
                container.addView(paintTextureView, layoutParams);
                paintTextureView.setTransparent();
            } else {
                PaintSurfaceView paintSurfaceView2 = new PaintSurfaceView(context);
                paintSurfaceView2.enableSurface(true);
                paintSurfaceView2.setPaintInstance(this);
                container.addView(paintSurfaceView2, layoutParams);
                if (z2) {
                    PaintSurfaceView.setTransparent$default(paintSurfaceView2, false, 1, null);
                    paintTextureView2 = new PaintTextureView(context);
                    paintTextureView2.setPaintInstance(this);
                    container.addView(paintTextureView2, layoutParams);
                    paintTextureView2.setTransparent();
                } else {
                    paintSurfaceView2.setBackgroundColor(-1);
                    paintSurfaceView2.setZOrderOnTop(true);
                }
                PaintTextureView paintTextureView3 = paintTextureView2;
                paintSurfaceView = paintSurfaceView2;
                paintTextureView = paintTextureView3;
            }
            this.mSurfaceView = paintSurfaceView;
            this.mTextureView = paintTextureView;
            OplusSpannedLayout oplusSpannedLayout = new OplusSpannedLayout(context);
            this.mSpannedLayout = oplusSpannedLayout;
            container.addView(oplusSpannedLayout);
            SpanEditDrawView spanEditDrawView = new SpanEditDrawView(context);
            this.mEditDrawView = spanEditDrawView;
            container.addView(spanEditDrawView);
            EntityManager entityManager = this.mEntityManager;
            OplusSpannedLayout oplusSpannedLayout2 = this.mSpannedLayout;
            Intrinsics.checkNotNull(oplusSpannedLayout2);
            SpanEditDrawView spanEditDrawView2 = this.mEditDrawView;
            Intrinsics.checkNotNull(spanEditDrawView2);
            entityManager.init(oplusSpannedLayout2, spanEditDrawView2, this);
            onSurfaceAdded.invoke(paintSurfaceView, paintTextureView, this.inkBackgroundView);
            registerKeyboard(container);
        }
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void initTrack(TrackEngineCallback trackEngineCallback) {
        this.mTrackEngine = trackEngineCallback;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void insertImage(String id, String path, InsertImageCallback callback) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.mEntityManager.insertImage(new PointF(this.mPaintWidth / 2.0f, this.mPaintHeight / 2.0f), id, path, callback);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void insertTextToPaintView(String content, RectF rectF, final TextOperationCallback callback) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(content, "content");
        Intrinsics.checkNotNullParameter(rectF, "rectF");
        Intrinsics.checkNotNullParameter(callback, "callback");
        if (content.length() == 0) {
            callback.onFailure(TextOperationError.CONTENT_EMPTY);
        } else {
            this.addingExternalText = true;
            this.mEntityManager.insertText(content, rectF, new TextOperationCallback() { // from class: com.oplusos.vfxsdk.doodleengine.stylus.OplusStylusImplement.insertTextToPaintView.1
                @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.TextOperationCallback
                public void onFailure(TextOperationError error) {
                    Intrinsics.checkNotNullParameter(error, "error");
                    callback.onFailure(error);
                    this.addingExternalText = false;
                }

                @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.TextOperationCallback
                public void onSuccess() {
                    callback.onSuccess();
                    this.addingExternalText = false;
                }
            });
        }
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void invalidate() {
        NativePaint.Companion.invalidate(this.mNativeHandle);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public boolean isChanged() {
        boolean zIsChanged = NativePaint.Companion.isChanged(this.mNativeHandle);
        boolean zHasTextEntered = this.mEntityManager.hasTextEntered();
        boolean zIsSpanEditingEmpty = isSpanEditingEmpty();
        StringBuilder sbT = defpackage.d.t("isChanged: hasChanged:", zIsChanged, " hasTextEntered:", zHasTextEntered, ", isSpanEditingEmpty = ");
        sbT.append(zIsSpanEditingEmpty);
        DLog(sbT.toString());
        if (!zIsChanged && zIsSpanEditingEmpty) {
            this.mEntityManager.removeEmptyEditView();
        }
        return zIsChanged || zHasTextEntered;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public boolean isContentEmpty() {
        boolean zIsContentEmpty = NativePaint.Companion.isContentEmpty(this.mNativeHandle);
        boolean zHasTextEntered = this.mEntityManager.hasTextEntered();
        Log.d(TAG, "isContentEmpty: " + zIsContentEmpty + ", hasTextEntered: " + zHasTextEntered);
        return zIsContentEmpty && !zHasTextEntered;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public boolean isFingerTouchValidElement(float f, float f2) {
        boolean z;
        if (this.mNativeHandle == 0) {
            Log.w(TAG, "SDK instance not init!");
            return false;
        }
        synchronized (this.mPickEntityLock) {
            this.mPickEntityResult = new String[0];
            long jCurrentTimeMillis = System.currentTimeMillis();
            NativePaint.Companion.pickEntity(this.mNativeHandle, new PointF(f, f2));
            this.mPickEntityLock.wait(100L);
            Log.d(TAG, "pickEntity blocking time: " + (System.currentTimeMillis() - jCurrentTimeMillis) + "ms");
            z = !(this.mPickEntityResult.length == 0);
        }
        return z;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public boolean isSpanEditing() {
        return this.mEntityManager.isSpannedEditing();
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public boolean isSpanEditingEmpty() {
        boolean zIsSpannedEditingEmpty = this.mEntityManager.isSpannedEditingEmpty();
        com.coui.appcompat.card.c.A("isSpanEditingEmpty: ", zIsSpannedEditingEmpty, TAG);
        return zIsSpannedEditingEmpty;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public boolean isTouching() {
        return this.mTouching;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public FileCode load(String str, String str2, String str3, LoadListener loadListener) {
        StringBuilder sbX = h.x(str2, "dataPath", str3, "mixturePath", "load: ");
        sbX.append(str);
        sbX.append(", / ");
        sbX.append(str2);
        DLog(sbX.toString());
        FileCode value = FileCode.Companion.getValue(NativePaint.Companion.load(this.mNativeHandle, str, str2));
        ActionCallbackManager.INSTANCE.registerLoad(str, str2, value, loadListener);
        return value;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void lockScale(boolean z) {
        if (!z) {
            setMinScale(this.mMinScale);
            setMaxScale(this.mMaxScale);
        } else {
            float contentScale = getContentScale();
            NativePaint.Companion companion = NativePaint.Companion;
            companion.setMinScaleFactor(this.mNativeHandle, contentScale);
            companion.setMaxScaleFactor(this.mNativeHandle, contentScale);
        }
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void moveBy(float f, float f2) {
        String str = TAG;
        StringBuilder sb = new StringBuilder("call moveBy: ");
        sb.append(f2);
        sb.append(", ");
        defpackage.d.B(sb, this.mTouching, str);
        if (this.mTouching) {
            return;
        }
        NativePaint.Companion.moveBy(this.mNativeHandle, f, f2);
    }

    public final void onAddedNode() {
        Log.d(TAG, "onAddedNode, mPaintViewListener.size(): " + this.mPaintViewListener.size());
        synchronized (this) {
            try {
                int size = this.mPaintViewListener.size();
                for (int i = 0; i < size; i++) {
                    this.mPaintViewListener.get(i).onAddedNode();
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
        PaintEditBgView paintEditBgView = this.inkBackgroundView;
        if (paintEditBgView != null) {
            paintEditBgView.postRedraw();
        }
        this.stepChangeTime = System.currentTimeMillis();
    }

    public final void onAdsorption() {
        Log.d(TAG, "onAdsorption, mPaintViewListener.size(): " + this.mPaintViewListener.size());
        synchronized (this) {
            try {
                int size = this.mPaintViewListener.size();
                for (int i = 0; i < size; i++) {
                    this.mPaintViewListener.get(i).onAdsorption();
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onAfterAddEntity(String id, String extra, int i) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(extra, "extra");
        EntityOperationResult value = EntityOperationResult.Companion.getValue(i);
        ILog("call onAfterAddEntity id=" + id + " result=" + value);
        EntityListener entityListener = this.mEntityListener;
        if (entityListener != null) {
            entityListener.onAfterAddEntity(id, extra, value);
        }
    }

    public final void onAfterUpdateEntity(String id, String extra, int i) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(extra, "extra");
        EntityOperationResult value = EntityOperationResult.Companion.getValue(i);
        ILog("call onAfterUpdateEntity id=" + id + " result=" + value);
        EntityListener entityListener = this.mEntityListener;
        if (entityListener != null) {
            entityListener.onAfterUpdateEntity(id, extra, value);
        }
    }

    public final void onCanvasExtended(int i, float f, boolean z) {
        DLog("onCanvasExtended, mPaintViewListener.size(): " + this.mPaintViewListener.size() + ", code: " + CanvasExtendType.values()[i] + ", + dy: " + f);
        synchronized (this) {
            try {
                int size = this.mPaintViewListener.size();
                for (int i2 = 0; i2 < size; i2++) {
                    this.mPaintViewListener.get(i2).onCanvasExtended(CanvasExtendType.values()[i], f);
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void onConfigurationChanged(Configuration configuration) {
        KeyBoardHelper keyBoardHelper;
        KeyBoardHelper keyBoardHelper2 = this.keyBoardHelper;
        if (keyBoardHelper2 != null) {
            keyBoardHelper2.unregisterKeyboardHeightListener();
        }
        KeyBoardHelper.KeyboardHeightListener keyboardHeightListener = this.keyboardHeightListener;
        if (keyboardHeightListener == null || (keyBoardHelper = this.keyBoardHelper) == null) {
            return;
        }
        keyBoardHelper.registerKeyboardHeightListener(keyboardHeightListener);
    }

    public final void onCreateBackGroundError(int i) {
        DLog("onCreateBackGroundError, code: " + i);
        synchronized (this) {
            try {
                int size = this.mPaintViewListener.size();
                for (int i2 = 0; i2 < size; i2++) {
                    this.mPaintViewListener.get(i2).onCreateBackGroundError(i);
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onDataTruncation() {
        DLog("onDataTruncation");
        SaveListener saveListener = this.mSaveListener;
        if (saveListener != null) {
            saveListener.onDataTruncation();
        }
    }

    public void onDrawingsSaved(int i, int i2) {
        FileCode value = FileCode.Companion.getValue(i);
        DLog("onDrawingsSaved, code: " + i + ", page count: " + i2);
        ArrayList<String> arrayList = new ArrayList<>(i2);
        for (int i3 = 0; i3 < i2; i3++) {
            arrayList.add(this.mDrawingsPath + '_' + i3 + ThumbFileConstants.IMAGE_EXT_BEFORE);
        }
        ActionCallbackManager.INSTANCE.triggerNext(new SaveDrawingResult(arrayList, value));
        SaveListener saveListener = this.mSaveListener;
        if (saveListener != null) {
            saveListener.onDrawingsSaved(value, arrayList);
        }
    }

    public final void onGenerateRelativeImage() {
        Log.d(TAG, "onGenerateRelativeImage");
        synchronized (this) {
            try {
                int size = this.mPaintViewListener.size();
                for (int i = 0; i < size; i++) {
                    this.mPaintViewListener.get(i).onGenerateRelativeImage();
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onGeometryStrokeChecked() {
        StylusManager stylusManager;
        Log.d(TAG, "onGeometryStrokeChecked");
        Operation operation = this.mCurrentStatus;
        if (operation != Operation.PENCIL) {
            if ((operation == Operation.BALLPEN || operation == Operation.PEN) && (stylusManager = this.mStylusManager) != null) {
                stylusManager.pauseVibration(true);
                return;
            }
            return;
        }
        StylusManager stylusManager2 = this.mStylusManager;
        if (stylusManager2 != null) {
            stylusManager2.setPencilEvent(false, 0.0f);
        }
        StylusManager stylusManager3 = this.mStylusManager;
        if (stylusManager3 != null) {
            stylusManager3.pauseVibration(true);
        }
    }

    public final void onImageGenerate() {
        DLog("onImageGenerate");
        synchronized (this) {
            try {
                int size = this.mPaintViewListener.size();
                for (int i = 0; i < size; i++) {
                    this.mPaintViewListener.get(i).onGenerateImage();
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onLeaped(float f, float f2, float f3, float f4, float f5) {
        String str = TAG;
        Log.d(str, "onLeaped, scale: " + f5);
        if (this.mScrollListener != null) {
            Log.d(str, "onLeaped call");
            ScrollListener scrollListener = this.mScrollListener;
            Intrinsics.checkNotNull(scrollListener);
            scrollListener.onLeaped(f, f2, f3, f4, f5);
        }
    }

    public void onLoaded() {
        TrackEngineCallback trackEngineCallback = this.mTrackEngine;
        if (trackEngineCallback != null) {
            trackEngineCallback.loadEnd();
        }
        DLog("onLoaded, mLoadListener.size(): " + this.mLoadListener.size());
        Pair<ActionResult, LoadListener> pairTriggerLoadNext = ActionCallbackManager.INSTANCE.triggerLoadNext();
        if (pairTriggerLoadNext != null) {
            ActionResult actionResultC = pairTriggerLoadNext.c();
            LoadListener loadListenerD = pairTriggerLoadNext.d();
            if (loadListenerD != null) {
                loadListenerD.loaded(actionResultC.getImgPath(), actionResultC.getDataPath(), actionResultC.getCode());
            }
            synchronized (this) {
                try {
                    int size = this.mLoadListener.size();
                    for (int i = 0; i < size; i++) {
                        this.mLoadListener.get(i).loaded(actionResultC.getImgPath(), actionResultC.getDataPath(), actionResultC.getCode());
                    }
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        Log.d(TAG, "onLoaded, mPaintViewListener.size(): " + this.mPaintViewListener.size());
        synchronized (this) {
            try {
                int size2 = this.mPaintViewListener.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    this.mPaintViewListener.get(i2).onLoaded();
                }
                Unit unit2 = Unit.INSTANCE;
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final void onMenuChanged(int i, boolean z, int i2, int i3, String[] entityIdArray, String[] entityExtraArray, int[] entityTypeArray, int[] entityColorArray) throws JSONException {
        int iOrdinal;
        String str;
        Extra extraFromJson;
        Intrinsics.checkNotNullParameter(entityIdArray, "entityIdArray");
        Intrinsics.checkNotNullParameter(entityExtraArray, "entityExtraArray");
        Intrinsics.checkNotNullParameter(entityTypeArray, "entityTypeArray");
        Intrinsics.checkNotNullParameter(entityColorArray, "entityColorArray");
        if (this.mInLassoAddOne && MenuType.values()[getMenuType()].ordinal() == MenuType.Paste.ordinal()) {
            this.mInLassoAddOne = false;
            return;
        }
        this.imagePath.clear();
        this.selectedImgExtra = null;
        boolean z2 = false;
        for (String str2 : entityExtraArray) {
            JSONObject jSONObject = new JSONObject(entityExtraArray[0]);
            String string = jSONObject.getString("type");
            if (Intrinsics.areEqual(string, "bitmap")) {
                this.imagePath.add(jSONObject.getString("imagePath"));
            } else if (Intrinsics.areEqual(string, "text")) {
                z2 = true;
            }
        }
        if (i != 0) {
            iOrdinal = i3 == 0 ? SelectType.IS_CURVE_DATA.ordinal() : SelectType.IS_MULTI_DATA.ordinal();
        } else if (z2) {
            this.mEntityManager.setCurrentSelectRecF(getSelectedRecF(), entityExtraArray[0]);
            iOrdinal = SelectType.IS_PLAIN_TEXT.ordinal();
        } else {
            iOrdinal = this.imagePath.size() == 1 ? SelectType.IS_PICTURE.ordinal() : SelectType.IS_MUL_PICTURE.ordinal();
        }
        ToolkitModel.Companion.setSelectType(iOrdinal);
        if (MenuType.values()[getMenuType()].ordinal() == MenuType.Delete_Copy_Cut.ordinal()) {
            int length = entityExtraArray.length;
            int i4 = 0;
            while (true) {
                if (i4 >= length) {
                    str = null;
                    break;
                }
                str = entityExtraArray[i4];
                Extra extraFromJson2 = Extra.Companion.fromJson(str);
                if ((extraFromJson2 != null ? extraFromJson2.getType() : null) == com.oplusos.vfxsdk.doodleengine.span.EntityType.BITMAP) {
                    break;
                } else {
                    i4++;
                }
            }
            if (str != null && (extraFromJson = Extra.Companion.fromJson(str)) != null) {
                this.selectedImgExtra = extraFromJson;
            }
        }
        String str3 = TAG;
        StringBuilder sb = new StringBuilder("onMenuChanged, mPaintViewListener.size(): ");
        sb.append(this.mPaintViewListener.size());
        sb.append(", menu type: ");
        sb.append(MenuType.values()[getMenuType()]);
        sb.append(", strokeCount=");
        sb.append(i);
        sb.append(", strokeColorValid=");
        sb.append(z);
        sb.append(", strokeColor=0x");
        k.d(16);
        sb.append(com.oplus.note.scenecard.utils.b.v1(16, i2 & BodyPartID.bodyIdMax));
        sb.append(", entityCount=");
        androidx.appsearch.app.e.y(sb, i3, str3);
        PaintEditBgView paintEditBgView = this.inkBackgroundView;
        if (paintEditBgView != null) {
            paintEditBgView.postRedraw();
        }
        synchronized (this) {
            try {
                if (z) {
                    ToolkitModel.Companion companion = ToolkitModel.Companion;
                    companion.setSingleColor(true);
                    companion.setSelectColor(i2);
                } else {
                    ToolkitModel.Companion.setSingleColor(false);
                }
                int i5 = 0;
                for (int size = this.mPaintViewListener.size(); i5 < size; size = size) {
                    this.mPaintViewListener.get(i5).onMenuChanged(MenuType.values()[getMenuType()], i, z ? Integer.valueOf(i2) : null, i3, entityIdArray, entityExtraArray, entityTypeArray, entityColorArray);
                    i5++;
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onPreviewDataReady(int i, int i2) {
        DLog("onPreviewDataReady");
        if (this.mContext == null) {
            return;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(width, height, config)");
        NativePaint.Companion.getPreviewBitmap(this.mNativeHandle, bitmapCreateBitmap);
        Object obj = this.mContext;
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type androidx.lifecycle.LifecycleOwner");
        k.n(com.oplus.account.netrequest.utils.e.g0((s) obj), null, null, new C10361(bitmapCreateBitmap, null), 3);
    }

    public final void onPreviewSaved(int i) {
        FileCode value = FileCode.Companion.getValue(i);
        ActionCallbackManager.INSTANCE.triggerNext(value);
        DLog("onPreviewSaved, code: " + i);
        SaveListener saveListener = this.mSaveListener;
        if (saveListener != null) {
            saveListener.onPreviewSaved(value, this.mPreviewImagePath);
        }
    }

    public final void onRedone() {
        Log.d(TAG, "onRedone, mPaintViewListener.size(): " + this.mPaintViewListener.size());
        synchronized (this) {
            try {
                int size = this.mPaintViewListener.size();
                for (int i = 0; i < size; i++) {
                    this.mPaintViewListener.get(i).onRedone();
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onResetEnd() {
        DLog("onResetEnd, mPaintViewListener.size(): " + this.mPaintViewListener.size());
        synchronized (this) {
            try {
                int size = this.mPaintViewListener.size();
                for (int i = 0; i < size; i++) {
                    this.mPaintViewListener.get(i).onResetEnd();
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onSaveImageByHeightFinished(String str, boolean z) {
        DLog("onSaveImageByHeightFinished, paint_file: " + str + ", is_success: " + z);
    }

    public final void onSaveRectToBitmapReady(RectF rect, String savePath, boolean z) {
        Intrinsics.checkNotNullParameter(rect, "rect");
        Intrinsics.checkNotNullParameter(savePath, "savePath");
        DLog("onSave RectToBitmapReady rect " + rect + ", savePath: " + savePath + ", success: " + z);
        String str = this.saveDataPath;
        if (str == null || str.length() == 0) {
            String str2 = this.saveImagePath;
            String str3 = this.saveDataPath;
            Intrinsics.checkNotNull(str3);
            onSaved(str2, str3, z ? FileCode.Success : FileCode.ImageWriteFailed);
            return;
        }
        if (savePath.length() > 0) {
            NativePaint.Companion.resetScrollStatus(this.mNativeHandle);
        }
        TrackEngineCallback trackEngineCallback = this.mTrackEngine;
        if (trackEngineCallback != null) {
            trackEngineCallback.saveBegin();
        }
        FileCode.Companion companion = FileCode.Companion;
        NativePaint.Companion companion2 = NativePaint.Companion;
        long j = this.mNativeHandle;
        String str4 = this.saveDataPath;
        Intrinsics.checkNotNull(str4);
        FileCode value = companion.getValue(companion2.save(j, null, str4, this.saveCacheState.getCacheType(), this.saveCacheState.getCacheMaxSize(), this.saveCacheState.getCacheForce(), this.saveCacheState.getCacheKeepTopBlank()));
        if (value == FileCode.InvalidInstance) {
            TrackEngineCallback trackEngineCallback2 = this.mTrackEngine;
            if (trackEngineCallback2 != null) {
                trackEngineCallback2.saveEnd();
            }
            String str5 = this.saveImagePath;
            String str6 = this.saveDataPath;
            Intrinsics.checkNotNull(str6);
            onSaved(str5, str6, value);
        }
    }

    public final void onSaved() {
        TrackEngineCallback trackEngineCallback = this.mTrackEngine;
        if (trackEngineCallback != null) {
            trackEngineCallback.saveEnd();
        }
        FileCode savedStatus = getSavedStatus();
        DLog("onSaved native call saved: " + savedStatus + ", " + this.saveImagePath);
        String str = this.saveImagePath;
        String str2 = this.saveDataPath;
        Intrinsics.checkNotNull(str2);
        onSaved(str, str2, savedStatus);
    }

    public final void onScrollEnd() {
        DLog("onScrollEnd");
        if (this.inWebviewScroll) {
            Log.d(TAG, "onScrollEnd inWebviewScroll");
        } else {
            ScrollListener scrollListener = this.mScrollListener;
            if (scrollListener != null) {
                scrollListener.onScrollEnd(getRendRect());
            }
        }
        this.inCanvasScroll = false;
    }

    public final void onScrollRestricted() {
        DLog("onScrollRestricted");
        if (this.mScrollListener != null) {
            Log.d(TAG, "onScrollRestricted call");
            ScrollListener scrollListener = this.mScrollListener;
            Intrinsics.checkNotNull(scrollListener);
            scrollListener.onScrollRestricted();
        }
    }

    public final void onScrollScale(float f, float f2, float f3, float f4, float f5) {
        StringBuilder sbQ = com.coui.appcompat.card.c.q("onScrollScale, x: ", f, ", y: ", f2, ", dx: ");
        h.A(sbQ, f3, ", dy: ", f4, ", scale: ");
        sbQ.append(f5);
        DLog(sbQ.toString());
        z zVar = this.mainScope;
        if (zVar != null) {
            k.n(zVar, null, null, new C10371(f5, f, f2, f3, f4, null), 3);
        }
    }

    public final void onScrollStart() {
        this.inCanvasScroll = true;
        DLog("onScrollStart");
        if (this.inWebviewScroll) {
            Log.d(TAG, "onScrollStart inWebviewScroll");
            return;
        }
        ScrollListener scrollListener = this.mScrollListener;
        if (scrollListener != null) {
            scrollListener.onScrollStart(getRendRect());
        }
    }

    public final void onSelectorChanged() {
        DLog("onSelectorChanged,  mPaintViewListener.size(): " + this.mPaintViewListener.size());
        synchronized (this) {
            try {
                int size = this.mPaintViewListener.size();
                for (int i = 0; i < size; i++) {
                    this.mPaintViewListener.get(i).onLassoOperationEnd(LassoOperation.ChangeColor, 0);
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onSelectorCopied() {
        Log.d(TAG, "onSelectorCopied,  mPaintViewListener.size(): " + this.mPaintViewListener.size());
        synchronized (this) {
            try {
                int size = this.mPaintViewListener.size();
                for (int i = 0; i < size; i++) {
                    this.mPaintViewListener.get(i).onLassoOperationEnd(LassoOperation.Copy, 0);
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onSelectorCut() {
        DLog("onSelectorCut,  mPaintViewListener.size(): " + this.mPaintViewListener.size());
        synchronized (this) {
            try {
                int size = this.mPaintViewListener.size();
                for (int i = 0; i < size; i++) {
                    this.mPaintViewListener.get(i).onLassoOperationEnd(LassoOperation.Cut, 0);
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onSelectorDeleted() {
        DLog("onSelectorDeleted,  mPaintViewListener.size(): " + this.mPaintViewListener.size());
        synchronized (this) {
            try {
                int size = this.mPaintViewListener.size();
                for (int i = 0; i < size; i++) {
                    this.mPaintViewListener.get(i).onLassoOperationEnd(LassoOperation.Delete, 0);
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onSelectorPaste() {
        DLog("onSelectorPaste,  mPaintViewListener.size(): " + this.mPaintViewListener.size());
        synchronized (this) {
            try {
                int size = this.mPaintViewListener.size();
                for (int i = 0; i < size; i++) {
                    this.mPaintViewListener.get(i).onLassoOperationEnd(LassoOperation.Paste, 0);
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x004e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void onSelectorSaved(int r6, int r7, int r8) {
        /*
            Method dump skipped, instructions count: 346
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.oplusos.vfxsdk.doodleengine.stylus.OplusStylusImplement.onSelectorSaved(int, int, int):void");
    }

    public final void onShapeRegulared(int i) {
        TrackEngineCallback trackEngineCallback = this.mTrackEngine;
        if (trackEngineCallback != null) {
            trackEngineCallback.trackShapeInfo(i);
        }
    }

    public final void onSplitFinished(String str, boolean z) {
        DLog("onSplitFinished, paint_file: " + str + ", is_success: " + z);
        p<? super String, ? super Boolean, Unit> pVar = this.splitLongCanvasCall;
        if (pVar != null) {
            pVar.invoke(str, Boolean.valueOf(z));
        }
        this.splitLongCanvasCall = null;
    }

    public final void onSurfaceRendered() {
        DLog("onSurfaceRendered");
    }

    public final void onTouchDownOutOfCanvas() {
        DLog("onTouchDownOutOfCanvas");
        StylusManager stylusManager = this.mStylusManager;
        if (stylusManager != null) {
            stylusManager.cancel();
        }
    }

    public final void onUncheckGeometryStroke() {
        StylusManager stylusManager;
        Log.d(TAG, "onUncheckGeometryStroke");
        Operation operation = this.mCurrentStatus;
        if (operation != Operation.PENCIL) {
            if ((operation == Operation.BALLPEN || operation == Operation.PEN) && (stylusManager = this.mStylusManager) != null) {
                stylusManager.pauseVibration(false);
                return;
            }
            return;
        }
        StylusManager stylusManager2 = this.mStylusManager;
        if (stylusManager2 != null) {
            stylusManager2.setPencilEvent(true, this.mPaint.getMSize());
        }
        StylusManager stylusManager3 = this.mStylusManager;
        if (stylusManager3 != null) {
            stylusManager3.startPlaySound();
        }
        StylusManager stylusManager4 = this.mStylusManager;
        if (stylusManager4 != null) {
            stylusManager4.pauseVibration(false);
        }
    }

    public final void onUndid() {
        Log.d(TAG, "onUndid, mPaintViewListener.size(): " + this.mPaintViewListener.size());
        synchronized (this) {
            try {
                int size = this.mPaintViewListener.size();
                for (int i = 0; i < size; i++) {
                    this.mPaintViewListener.get(i).onUndid();
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onZoomEnd() {
        DLog("onZoomEnd");
        if (this.mZoomListener != null) {
            Log.d(TAG, "onZoomEnd call");
            ZoomingListener zoomingListener = this.mZoomListener;
            Intrinsics.checkNotNull(zoomingListener);
            zoomingListener.onZoomEnd();
        }
    }

    public final void onZoomStart() {
        DLog("onZoomStart");
        if (this.mZoomListener != null) {
            Log.d(TAG, "onZoomStart call");
            ZoomingListener zoomingListener = this.mZoomListener;
            Intrinsics.checkNotNull(zoomingListener);
            zoomingListener.onZoomStart();
        }
    }

    public final void onZooming(float f, float f2, float f3, float f4) {
        StringBuilder sbQ = com.coui.appcompat.card.c.q("onZooming: x: ", f, ", y: ", f2, ", bgScale: ");
        sbQ.append(f3);
        sbQ.append(", canvasScale: ");
        sbQ.append(f4);
        DLog(sbQ.toString());
        if (this.mZoomListener != null) {
            Log.d(TAG, "onZooming, call");
            ZoomingListener zoomingListener = this.mZoomListener;
            Intrinsics.checkNotNull(zoomingListener);
            zoomingListener.onZooming(f, f2, f3, (int) (f4 * 100));
        }
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void preLoadSetScale(float f, PointF center, RectF rectF) {
        Intrinsics.checkNotNullParameter(center, "center");
        Intrinsics.checkNotNullParameter(rectF, "rectF");
        DLog("preLoadSetScale: " + f + ", offset: " + rectF + ", " + center);
        updateContent(0.0f, 0.0f, f);
        setRendRect(rectF);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void previewHandwriting(Paint paint, int i, int i2, l<? super Bitmap, Unit> lVar) {
        Intrinsics.checkNotNullParameter(paint, "paint");
        this.mPreviewHandwritingCall = lVar;
        f1 f1Var = this.previewJob;
        if (f1Var != null) {
            f1Var.a(null);
        }
        Object obj = this.mContext;
        s sVar = obj instanceof s ? (s) obj : null;
        if (sVar != null) {
            this.previewJob = k.n(com.oplus.account.netrequest.utils.e.g0(sVar), null, null, new C10381(i, i2, null), 3);
        }
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void readCanvasData() {
        NativePaint.Companion.readCanvasData(this.mNativeHandle, this.mImageWidth, this.mImageHeight);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void redo() {
        if (!this.mTouching) {
            boolean zIsSpannedEditing = this.mEntityManager.isSpannedEditing();
            boolean zCanRedo = this.mEntityManager.canRedo();
            if (zIsSpannedEditing && zCanRedo) {
                this.mEntityManager.redo();
                return;
            } else {
                EntityManager.cancelSpanEdit$default(this.mEntityManager, false, 1, null);
                NativePaint.Companion.redo(this.mNativeHandle);
            }
        }
        Log.d(TAG, "Redo");
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void refreshScreen() {
        NativePaint.Companion.refreshScreen(this.mNativeHandle);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void removeListener(PaintViewListener paintViewListener) {
        if (paintViewListener != null) {
            synchronized (this) {
                this.mPaintViewListener.remove(paintViewListener);
            }
        }
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void removeLoadListener(LoadListener loadListener) {
        if (loadListener != null) {
            synchronized (this) {
                this.mLoadListener.remove(loadListener);
            }
        }
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void replaceSelectedBitmap(String newId, String newPath, UpdateImageCallback updateImageCallback) {
        Intrinsics.checkNotNullParameter(newId, "newId");
        Intrinsics.checkNotNullParameter(newPath, "newPath");
        Extra extra = this.selectedImgExtra;
        if (extra == null) {
            Log.d(TAG, "replaceBitmap: error, no select image ");
            return;
        }
        EntityManager entityManager = this.mEntityManager;
        Intrinsics.checkNotNull(extra);
        entityManager.updateBitmap(newId, newPath, extra, new PointF(this.mPaintWidth / 2.0f, this.mPaintHeight / 2.0f), updateImageCallback);
    }

    public final void requestUpdateBitmap(int i, String[] entityIdArray, String[] entityExtraArray, int[] entityTypeArray, int[] entityColorArray) {
        Intrinsics.checkNotNullParameter(entityIdArray, "entityIdArray");
        Intrinsics.checkNotNullParameter(entityExtraArray, "entityExtraArray");
        Intrinsics.checkNotNullParameter(entityTypeArray, "entityTypeArray");
        Intrinsics.checkNotNullParameter(entityColorArray, "entityColorArray");
        ILog("call requestUpdateBitmap entityCount=" + i + " entityExtraArray=" + entityExtraArray.length);
        EntityListener entityListener = this.mEntityListener;
        if (entityListener != null) {
            entityListener.requestUpdateBitmap(i, entityIdArray, entityExtraArray, entityTypeArray, entityColorArray);
        }
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void reset(boolean z) {
        DLog("reset touch: " + this.mTouching + ", resetCanvas: " + z);
        if (this.mTouching) {
            return;
        }
        if (z) {
            NativePaint.Companion.reset(this.mNativeHandle, z);
        } else {
            resetScale();
        }
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void resetData() {
        reset(true);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void rollEnd() {
        NativePaint.Companion.rollEnd(this.mNativeHandle);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void rollStart(boolean z) {
        NativePaint.Companion.rollStart(this.mNativeHandle, z);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void rolling(float f, boolean z, boolean z2) {
        String str = TAG;
        StringBuilder sb = new StringBuilder("rolling: ");
        sb.append(f);
        sb.append(", extendCanvas: ");
        sb.append(z);
        sb.append(", needSync: ");
        defpackage.d.B(sb, z2, str);
        NativePaint.Companion.rolling(this.mNativeHandle, f, z, z2);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void rollto(float f) {
        rollStart(false);
        rolling(getContentOffsetY() + f, false, false);
        rollEnd();
    }

    public final void runOnUiThread(kotlin.jvm.functions.a<Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        if (Intrinsics.areEqual(Looper.myLooper(), Looper.getMainLooper())) {
            return;
        }
        new Handler(Looper.getMainLooper()).post(new com.nearme.note.activity.edit.c(3, block));
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public FileCode save(String str, String dataPath, String str2, int i, long j, boolean z, boolean z2, String str3, boolean z3, boolean z4) {
        Intrinsics.checkNotNullParameter(dataPath, "dataPath");
        StringBuilder sbX = h.x(str2, "mixturePath", str3, "taskId", "onSave begin, ");
        sbX.append(str);
        sbX.append(", ");
        sbX.append(dataPath);
        DLog(sbX.toString());
        String str4 = TAG;
        Log.i(str4, "BEGIN: Add entity blockly before save.");
        this.mEntityManager.cancelSpanEdit(true);
        Log.i(str4, "END: Add entity blockly before save.");
        this.saveImagePath = str;
        this.saveDataPath = dataPath;
        SaveCacheState saveCacheState = this.saveCacheState;
        saveCacheState.setCacheType(i);
        saveCacheState.setCacheMaxSize(j);
        saveCacheState.setCacheForce(z);
        saveCacheState.setCacheKeepTopBlank(z4);
        RectF rendRect = getRendRect();
        String str5 = this.saveImagePath;
        if (str5 == null || str5.length() == 0) {
            String str6 = this.saveImagePath;
            Intrinsics.checkNotNull(str6);
            onSaveRectToBitmapReady(rendRect, str6, false);
        } else {
            NativePaint.Companion companion = NativePaint.Companion;
            RectF rectFSurfaceToCanvasRect = companion.surfaceToCanvasRect(this.mNativeHandle, rendRect);
            DLog("onSaved Rend: " + rendRect + ", saveRect: " + rectFSurfaceToCanvasRect + ", imagePath: " + this.saveImagePath);
            long j2 = this.mNativeHandle;
            Intrinsics.checkNotNull(rectFSurfaceToCanvasRect);
            String str7 = this.saveImagePath;
            Intrinsics.checkNotNull(str7);
            NativePaint.Companion.saveCanvasRectToBitmap$default(companion, j2, rectFSurfaceToCanvasRect, str7, 0L, 8, null);
        }
        return FileCode.Success;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void saveDrawings(String str, long j, l<? super SaveDrawingResult, Unit> lVar) {
        DLog("saveDrawings");
        this.mDrawingsPath = str;
        NativePaint.Companion companion = NativePaint.Companion;
        long j2 = this.mNativeHandle;
        Intrinsics.checkNotNull(str);
        companion.saveDrawings(j2, str, j);
        ActionCallbackManager.INSTANCE.registerCallback(lVar);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void savePreview(String str, long j, l<? super ActionResult, Unit> lVar) {
        DLog("savePreview");
        this.mPreviewImagePath = str;
        this.mPreviewBackground = j;
        NativePaint.Companion companion = NativePaint.Companion;
        long j2 = this.mNativeHandle;
        Intrinsics.checkNotNull(str);
        companion.savePreview(j2, str, j);
        ActionCallbackManager.INSTANCE.registerCallback(str, ActionCallbackManager.TempPreDataPath, FileCode.Unknown, lVar);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void saveRelativeCanvasInfo(int i, int i2, Rect initRect, long j) {
        Intrinsics.checkNotNullParameter(initRect, "initRect");
        DLog("saveRelativeCanvasBitmap " + i + ", " + i2);
        this.mRelativeCanvasWidth = i;
        this.mRelativeCanvasHeight = i2;
        NativePaint.Companion.saveRelativeCanvasInfo(this.mNativeHandle, i, i2, initRect.left, initRect.bottom, initRect.right, initRect.top, j);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void saveSelectObjToBitmap(l<? super Bitmap, Unit> call) {
        Intrinsics.checkNotNullParameter(call, "call");
        Log.d(TAG, "call saveSelectObjToBitmap");
        this.saveSelect2BitmapCall = call;
        setSelectorOperation(LassoOperation.Save);
    }

    public final void scrollContentByY(float f, float f2) {
        DLog("call scrollContentByY: " + f2);
        NativePaint.Companion.scrollContentByY(this.mNativeHandle, f2, true);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void scrollFromGesture(int i, boolean z) {
        if (this.inCanvasScroll && !this.inWebviewScroll) {
            String str = TAG;
            StringBuilder sb = new StringBuilder("scrollFromGesture 手势滚动中，不支持外部滚动: ");
            sb.append(this.inCanvasScroll);
            sb.append(", ");
            defpackage.d.B(sb, this.inWebviewScroll, str);
            return;
        }
        this.inWebviewScroll = true;
        float f = i;
        this.mRendRect.offset(0.0f, f);
        configureRendManager();
        setNativeRendRect();
        String str2 = TAG;
        StringBuilder sbP = defpackage.d.p("scrollFromGesture: ", i, ", ");
        sbP.append(this.mRendRect);
        Log.d(str2, sbP.toString());
        scrollContentByY(0.0f, -f);
        runDelayGestureEnd();
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void scrollFromGestureEnd() {
        Log.d(TAG, "scrollFromGestureEnd");
        this.inWebviewScroll = false;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setAdsorptionOffset(float f) {
        Log.d(TAG, "setAdsorptionOffset setScalingSnapFactor: " + f);
        NativePaint.Companion.setScalingSnapFactor(this.mNativeHandle, f);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setBackground(Bitmap bitmap) {
        NativePaint.Companion companion = NativePaint.Companion;
        long j = this.mNativeHandle;
        Intrinsics.checkNotNull(bitmap);
        companion.setBackground(j, bitmap);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setBrushParams(int i, float f, float f2) {
        Log.d(TAG, "setBrushParams, pressureEffectSenson: " + f + ", tipEffectLength: " + f2);
        NativePaint.Companion.setBrushParams(this.mNativeHandle, i, f, f2);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setCanvasExtendMax(int i) {
        this.canvasExtendMax = i;
        DLog("setCanvasExtendMax: " + i);
        NativePaint.Companion.setMaxDoodleHeight(this.mNativeHandle, (float) i);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setCanvasRendRectManager(CanvasRendRectManager rendRectManager) {
        Intrinsics.checkNotNullParameter(rendRectManager, "rendRectManager");
        this.canvasRendRectManager = rendRectManager;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setCanvasScaleRange(float f, float f2, float f3) {
        Log.d(TAG, "setCanvasScaleRange: " + f3);
        setMinScale(f);
        setMaxScale(f2);
        setAdsorptionOffset(getScaleInSDK(f2) - getScaleInSDK(f));
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setCaptureSize(int i, int i2) {
        DLog("Capture Size, width: " + i + ", height: " + i2);
        this.mCaptureWidth = i;
        this.mCaptureHeight = i2;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setDarkTheme(boolean z) {
        this.mDarkTheme = z;
        DLog("set dark theme: " + z);
        long j = this.mNativeHandle;
        if (j != 0) {
            NativePaint.Companion.setDarkTheme(j, this.mDarkTheme);
        }
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setDrawablePages(int i, boolean z) {
        if (i < 2 || i > 75) {
            NativePaint.Companion.setDrawablePages(this.mNativeHandle, 8, z);
        } else {
            NativePaint.Companion.setDrawablePages(this.mNativeHandle, i, z);
        }
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setEntityListener(EntityListener entityListener) {
        Log.i(TAG, "setEntityListener");
        this.mEntityListener = entityListener;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setEraser(Paint.EraserType eraserType, float f) {
        float fA1 = j.A1(1.0f, j.z1(f / 50.0f, 0.0f));
        this.mCurrentStatus = Operation.PointErase;
        this.mEraserSize = fA1;
        NativePaint.Companion companion = NativePaint.Companion;
        long j = this.mNativeHandle;
        Intrinsics.checkNotNull(eraserType);
        companion.setEraser(j, eraserType.ordinal(), this.mEraserSize);
        String str = TAG;
        StringBuilder sb = new StringBuilder("setEraser, type: ");
        sb.append(eraserType);
        sb.append(", mEraserSize: ");
        androidx.appsearch.app.e.x(sb, this.mEraserSize, str);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setFingerTouchListener(FingerTouchListener fingerTouchListener) {
        this.fingerTouchListener = fingerTouchListener;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setFingerZoom(boolean z) {
        NativePaint.Companion.setFingerZoomEnabled(this.mNativeHandle, z);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setImageSize(int i, int i2) {
        this.mImageWidth = i;
        this.mImageHeight = i2;
        DLog("setImageSize: (" + i + FileHighlighter.PARAMS_DIVIDER + i2 + ')');
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setInQuickScreen(boolean z) {
        ToolkitModel.Companion.setInQuickScreen(z);
    }

    public final void setKeyboardChanging(boolean z) {
        this.keyboardChanging = z;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setLaserPen(Paint paint) {
        Intrinsics.checkNotNullParameter(paint, "paint");
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setMaxScale(float f) {
        DLog("set scale: mMaxScale = " + this.mMaxScale + " to-> " + f);
        if (this.mMaxScale == f) {
            return;
        }
        this.mMaxScale = f;
        NativePaint.Companion.setMaxScaleFactor(this.mNativeHandle, getScaleInSDK(f));
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setMinScale(float f) {
        DLog("set scale: mMinScale = " + this.mMinScale + " to-> " + f);
        if (this.mMinScale == f) {
            return;
        }
        this.mMinScale = f;
        NativePaint.Companion.setMinScaleFactor(this.mNativeHandle, getScaleInSDK(f));
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setNeedCorrectRect(boolean z) {
        NativePaint.Companion.setNeedCorrectRect(this.mNativeHandle, z);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setPaint(Paint paint) {
        Intrinsics.checkNotNullParameter(paint, "paint");
        this.mCurrentStatus = Operation.Companion.getValue(paint.getMType().ordinal());
        this.mPaint.setValue(paint);
        float realSize = getRealSize(paint);
        int iOrdinal = this.mPaint.getMType().ordinal();
        Paint.Type type = Paint.Type.TEXTBOX;
        int iOrdinal2 = iOrdinal == type.ordinal() ? 12 : this.mPaint.getMType().ordinal();
        NativePaint.Companion.setPaintType(this.mNativeHandle, iOrdinal2, realSize, this.mPaint.getMRed(), this.mPaint.getMGreen(), this.mPaint.getMBlue(), this.mPaint.getMAlpha(), this.mPaint.getMType().ordinal() == type.ordinal() ? 0 : this.mPaint.getMGeometryType().ordinal());
        String str = TAG;
        StringBuilder sbP = defpackage.d.p("setPaint info, type: ", iOrdinal2, ", originType: ");
        sbP.append(this.mPaint.getMType());
        sbP.append(", geometryType: ");
        sbP.append(this.mPaint.getMGeometryType());
        sbP.append(", size: ");
        sbP.append(realSize);
        sbP.append(", red: ");
        sbP.append(this.mPaint.getMRed());
        sbP.append(", green: ");
        sbP.append(this.mPaint.getMGreen());
        sbP.append(", blue: ");
        sbP.append(this.mPaint.getMBlue());
        sbP.append(", alpha: ");
        sbP.append(this.mPaint.getMAlpha());
        sbP.append(", senson: ");
        sbP.append(this.mPaint.getMSenson());
        sbP.append(", length: ");
        sbP.append(this.mPaint.getMTipLength());
        Log.d(str, sbP.toString());
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setPaintDrawLineStatus(boolean z) {
        NativePaint.Companion.setPaintDrawLineStatus(this.mNativeHandle, z);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setPaintViewSize(int i, int i2) {
        DLog("setPaintViewSize, " + this.mNativeHandle + ", " + i + ", " + i2);
        this.mPaintWidth = i;
        this.mPaintHeight = i2;
        NativePaint.Companion.setPaintViewSize(this.mNativeHandle, i, i2);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setPastePoint(float f, float f2) {
        NativePaint.Companion.setPastePosition(this.mNativeHandle, new PointF(f, f2));
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setPlacedAtBottomStatus(boolean z) {
        NativePaint.Companion.setPlacedAtBottomStatus(this.mNativeHandle, z);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setPostFrameCallbackEnabled(boolean z) {
        NativePaint.Companion.setPostFrameCallbackEnabled(this.mNativeHandle, z);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setRendRect(RectF rectF) {
        Intrinsics.checkNotNullParameter(rectF, "rectF");
        this.mRendRect.set(rectF);
        setNativeRendRect();
        configureRendManager();
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setRendRectOnly(RectF rectF) {
        Intrinsics.checkNotNullParameter(rectF, "rectF");
        com.oplus.cloudkit.util.b.F("setRendRectOnly :", rectF, TAG);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setRendRectWitScale(RectF rectF, float f, float f2, float f3, boolean z) {
        Intrinsics.checkNotNullParameter(rectF, "rectF");
        this.mRendRect.set(rectF);
        configureRendManager();
        PaintEditBgView paintEditBgView = this.inkBackgroundView;
        if (paintEditBgView != null) {
            paintEditBgView.postRedraw();
        }
        Log.d(TAG, "setRendRect :" + this.mRendRect + ", WitScale sx: " + f + ", sy: " + f2 + ", scale: " + f3 + ", realUse");
        updateContent(0.0f, 0.0f, f3);
        setNativeRendRect();
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setSaveListener(SaveListener saveListener) {
        this.mSaveListener = saveListener;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setScrollListener(ScrollListener scrollListener) {
        this.mScrollListener = scrollListener;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setSelector() {
        this.mCurrentStatus = Operation.Selection;
        NativePaint.Companion.setSelector(this.mNativeHandle);
        Log.d(TAG, "setSelector, Use selector");
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setSelectorColor(float f, float f2, float f3) {
        float fA1 = j.A1(1.0f, j.z1(f, 0.0f));
        float fA12 = j.A1(1.0f, j.z1(f2, 0.0f));
        float fA13 = j.A1(1.0f, j.z1(f3, 0.0f));
        int iOrdinal = this.mCurrentStatus.ordinal();
        if (iOrdinal == Operation.Selection.ordinal()) {
            this.mPaint.setMRed(fA1);
            this.mPaint.setMGreen(fA12);
            this.mPaint.setMBlue(fA13);
            NativePaint.Companion.setColor(this.mNativeHandle, fA1, fA12, fA13);
        }
        String str = TAG;
        StringBuilder sbQ = com.coui.appcompat.card.c.q("setSelectorColor, red: ", fA1, ", green: ", fA12, ", blue: ");
        sbQ.append(fA13);
        sbQ.append(", index: ");
        sbQ.append(iOrdinal);
        Log.d(str, sbQ.toString());
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setSelectorExtraMenu(MenuItemData op) {
        Intrinsics.checkNotNullParameter(op, "op");
        if (this.imagePath.size() == 1) {
            String str = this.imagePath.get(0);
            Intrinsics.checkNotNullExpressionValue(str, "imagePath[0]");
            op.setImagePath(str);
        }
        RectF selectedRecF = getSelectedRecF();
        if (selectedRecF != null) {
            op.setSelectRectF(selectedRecF);
        }
        StylusInterface.BitmapMenuCallback bitmapMenuCallback = this.bitmapMenuCallback;
        if (bitmapMenuCallback != null) {
            bitmapMenuCallback.onBitmapMenuClick(op);
        }
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setSelectorOperation(LassoOperation lassoOperation) {
        if (this.mCurrentStatus.ordinal() == Operation.Selection.ordinal()) {
            if (lassoOperation != LassoOperation.Save) {
                l<? super Bitmap, Unit> lVar = this.saveSelect2BitmapCall;
                if (lVar != null) {
                    lVar.invoke(null);
                }
                this.saveSelect2BitmapCall = null;
            } else {
                if (this.mCaptureWidth <= 0 || this.mCaptureHeight <= 0) {
                    Log.e(TAG, "Fail to get the display size");
                    l<? super Bitmap, Unit> lVar2 = this.saveSelect2BitmapCall;
                    if (lVar2 != null) {
                        lVar2.invoke(null);
                    }
                    this.saveSelect2BitmapCall = null;
                    return;
                }
                String str = TAG;
                StringBuilder sb = new StringBuilder("start to capture, captureSize: width: ");
                sb.append(this.mCaptureWidth);
                sb.append(", height: ");
                androidx.appsearch.app.e.y(sb, this.mCaptureHeight, str);
                NativePaint.Companion.setCaptureSize(this.mNativeHandle, this.mCaptureWidth, this.mCaptureHeight);
            }
            if (lassoOperation == LassoOperation.CopyAndPaste) {
                NativePaint.Companion companion = NativePaint.Companion;
                companion.setSelectorOperation(this.mNativeHandle, LassoOperation.Copy.ordinal());
                selectLassoAddPosition();
                companion.setSelectorOperation(this.mNativeHandle, LassoOperation.Paste.ordinal());
            } else {
                NativePaint.Companion companion2 = NativePaint.Companion;
                long j = this.mNativeHandle;
                Intrinsics.checkNotNull(lassoOperation);
                companion2.setSelectorOperation(j, lassoOperation.ordinal());
            }
            TrackEngineCallback trackEngineCallback = this.mTrackEngine;
            if (trackEngineCallback != null) {
                trackEngineCallback.trackLassoInfo(lassoOperation);
            }
            synchronized (this) {
                try {
                    int size = this.mPaintViewListener.size();
                    for (int i = 0; i < size; i++) {
                        this.mPaintViewListener.get(i).onLassoOperationBegin(lassoOperation);
                    }
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
        } else {
            l<? super Bitmap, Unit> lVar3 = this.saveSelect2BitmapCall;
            if (lVar3 != null) {
                lVar3.invoke(null);
            }
            this.saveSelect2BitmapCall = null;
        }
        DLog("setSelectorOperation, op: " + lassoOperation + ", mCurrentStatus: " + this.mCurrentStatus);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setSlideSpeedWhenSelecting(float f) {
        NativePaint.Companion.setSlideSpeedWhenSelecting(this.mNativeHandle, f);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    /* renamed from: setSmoothFilterInfo-qim9Vi0, reason: not valid java name */
    public void mo94setSmoothFilterInfoqim9Vi0(int i, float f) {
        long j = this.mNativeHandle;
        if (j == 0) {
            Log.e(TAG, "setSmoothFilterInfo failed, native-pointer is null!");
        } else {
            NativePaint.Companion.setSmoothFilterInfo(j, i, f);
        }
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setSplitHeight(int i, int i2) {
        if (i > 0 && i2 > 0) {
            SPLIT_LONGCANVAS_PROP = (i2 * 1.0f) / i;
        }
        NativePaint.Companion.preSetSplitHeight(this.mNativeHandle, SPLIT_LONGCANVAS_PROP);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setStylus(boolean z) {
        this.mOnlyStylus = z;
        NativePaint.Companion.setOnlyStylusDrawing(this.mNativeHandle, z);
        defpackage.d.B(new StringBuilder("mOnlyStylus: "), this.mOnlyStylus, TAG);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setTopBlank(float f) {
        NativePaint.Companion.setTopBlank(this.mNativeHandle, f);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setUnlimitedScale(boolean z) {
        NativePaint.Companion.setUnlimitedScale(this.mNativeHandle, z);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setVectorDraw(boolean z) {
        this.mCanvasType = z ? 1 : 0;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setWebScrollValues(int i, int i2) {
        DLog("setWebScrollValues: " + i + ", " + i2);
        NativePaint.Companion.setMinAndMaxScrollHeight(this.mNativeHandle, i, i2);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setWriteEffect(Paint.Type penType, EffectType effectType, float f) {
        Intrinsics.checkNotNullParameter(penType, "penType");
        Intrinsics.checkNotNullParameter(effectType, "effectType");
        if (penType != Paint.Type.PEN) {
            return;
        }
        float mSenson = this.mPaint.getMSenson();
        float mTipLength = this.mPaint.getMTipLength();
        if (effectType != EffectType.TIP_UP) {
            if (effectType != EffectType.PRESSURE) {
            }
            setBrushParams(penType.ordinal(), f, mTipLength);
            this.mPaint.setMSenson(f);
            this.mPaint.setMTipLength(mTipLength);
        }
        mTipLength = f;
        f = mSenson;
        setBrushParams(penType.ordinal(), f, mTipLength);
        this.mPaint.setMSenson(f);
        this.mPaint.setMTipLength(mTipLength);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setZoomListener(ZoomingListener zoomingListener) {
        this.mZoomListener = zoomingListener;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void showCanvasBounds(boolean z) {
        NativePaint.Companion.showCanvasBounds(this.mNativeHandle, z);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void showDoodleBlockBounds(boolean z) {
        NativePaint.Companion.showDoodleBlockBounds(this.mNativeHandle, z);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void splitPaintFile(String paintPath, List<String> imgList, List<String> paintList, p<? super String, ? super Boolean, Unit> pVar, long j) {
        Intrinsics.checkNotNullParameter(paintPath, "paintPath");
        Intrinsics.checkNotNullParameter(imgList, "imgList");
        Intrinsics.checkNotNullParameter(paintList, "paintList");
        if (NativePaint.Companion.splitPaintFile(this.mNativeHandle, paintPath, (String[]) imgList.toArray(new String[0]), (String[]) paintList.toArray(new String[0]), 41943040L, j)) {
            this.splitLongCanvasCall = pVar;
        } else if (pVar != null) {
            pVar.invoke(paintPath, Boolean.FALSE);
        }
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public float startExpandShrinkCanvas() {
        this.mContentRect.setEmpty();
        RectF rectFCanvasToSurfaceRect = NativePaint.Companion.canvasToSurfaceRect(this.mNativeHandle, getContentRange());
        if (rectFCanvasToSurfaceRect != null && !rectFCanvasToSurfaceRect.isEmpty()) {
            this.mContentRect.set(rectFCanvasToSurfaceRect);
        }
        Log.d(TAG, "touchBorderExtend down contentRange:" + this.mContentRect);
        return getContentBottomInScreen();
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void startTopExtendCanvas(boolean z, l<? super RectF, Unit> lVar) {
        if (this.mContentRect.isEmpty()) {
            RendRectTopExtendUtil.Companion.touchBorderUp();
            resetTempExtendValue();
        } else {
            this.extendCallback = lVar;
            RendRectTopExtendUtil.Companion.touchBorderExtend(z, new l<Integer, Unit>() { // from class: com.oplusos.vfxsdk.doodleengine.stylus.OplusStylusImplement$startTopExtendCanvas$extendAction$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.l
                public /* bridge */ /* synthetic */ Unit invoke(Integer num) {
                    invoke(num.intValue());
                    return Unit.INSTANCE;
                }

                /* JADX WARN: Removed duplicated region for block: B:35:0x0125  */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public final void invoke(int r9) {
                    /*
                        Method dump skipped, instructions count: 519
                        To view this dump add '--comments-level debug' option
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.oplusos.vfxsdk.doodleengine.stylus.OplusStylusImplement$startTopExtendCanvas$extendAction$1.invoke(int):void");
                }
            });
        }
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void stopExtendShrinkCanvas() {
        this.mContentRect.setEmpty();
        RendRectTopExtendUtil.Companion.touchBorderUp();
        androidx.appsearch.app.e.x(new StringBuilder("touchBorderUp mTempExtendUpValue: "), this.mTempExtendUpValue, TAG);
        resetTempExtendValue();
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void stopTopExtendCanvas() {
        RendRectTopExtendUtil.Companion.touchBorderUp();
        this.extendCallback = null;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public boolean supportBeautify() {
        return false;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void surfaceChanged(Surface surface, int i, int i2, boolean z) {
        Intrinsics.checkNotNullParameter(surface, "surface");
        this.visibleWidth = i;
        this.visibleHeight = i2;
        androidx.appsearch.app.e.y(new StringBuilder("surfaceChanged: visibleHeight"), this.visibleHeight, TAG);
        if (z) {
            NativePaint.Companion.surfaceChanged(this.mNativeHandle, surface);
        } else {
            NativePaint.Companion.surfaceChangedNoWait(this.mNativeHandle, surface);
        }
        DLog("surfaceChanged");
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void surfaceCreated(Surface surface, boolean z) {
        Intrinsics.checkNotNullParameter(surface, "surface");
        if (z) {
            NativePaint.Companion.surfaceCreated(this.mNativeHandle, surface);
        } else {
            NativePaint.Companion.surfaceCreatedNoWait(this.mNativeHandle, surface);
        }
        if (this.mInitialized) {
            this.mInitialized = false;
            Log.d(TAG, "surfaceCreated, mPaintViewListener count: " + this.mPaintViewListener.size());
            synchronized (this) {
                try {
                    int size = this.mPaintViewListener.size();
                    for (int i = 0; i < size; i++) {
                        this.mPaintViewListener.get(i).initialized();
                    }
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void testToast(final String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (this.g_Debug) {
            runOnUiThread(new kotlin.jvm.functions.a<Unit>() { // from class: com.oplusos.vfxsdk.doodleengine.stylus.OplusStylusImplement.testToast.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.a
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    Toast.makeText(OplusStylusImplement.this.mContext, msg, 0).show();
                }
            });
        }
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void touchEvent(MotionEvent event) throws Resources.NotFoundException {
        String str;
        String str2;
        String str3;
        boolean z;
        TrackEngineCallback trackEngineCallback;
        ScrollListener scrollListener;
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.mContext == null) {
            Log.e(TAG, "touchEvent: context is null, do nothing");
            return;
        }
        String str4 = TAG;
        Log.d(str4, "oplus touchEvent, action: " + event.getActionMasked() + ", [" + event.getX() + ", " + event.getY() + ']');
        this.fingerTouchDetector.handleFingerTouchEvent(event);
        this.mInLassoAddOne = false;
        int pointerCount = event.getPointerCount();
        int actionMasked = event.getActionMasked();
        long jNanoTime = System.nanoTime();
        if (5 == actionMasked || actionMasked == 0) {
            this.mDownTime = event.getDownTime();
        }
        if (5 == actionMasked) {
            Timer timer = this.mShapeRegularTimer;
            if (timer != null) {
                timer.cancel();
            }
            Timer timer2 = this.mShapeRegularTimer;
            if (timer2 != null) {
                timer2.purge();
            }
            this.mShapeRegularTimer = null;
            this.mTouchPointerDown = true;
        }
        if (3 == actionMasked) {
            this.mTouchPointerDown = false;
            this.mTouching = false;
            ScrollListener scrollListener2 = this.mScrollListener;
            if (scrollListener2 != null) {
                scrollListener2.touchCanvas(false);
            }
        }
        if (this.isInputTextMode || this.addingExternalText) {
            Log.d(str4, "touchEvent: isInputTextMode, touchEvent to SpannedManager");
            this.mEntityManager.touchEvent(event);
            return;
        }
        this.mEntityManager.touchEvent(event);
        if (pointerCount > 2) {
            NativePaint.Companion.touchEvent(this.mNativeHandle, actionMasked, jNanoTime, 3, 0, this.mPoints);
            this.mTouching = false;
            ScrollListener scrollListener3 = this.mScrollListener;
            if (scrollListener3 != null) {
                scrollListener3.touchCanvas(false);
                return;
            }
            return;
        }
        if (2 == pointerCount) {
            getTouchEvent(event, 1, getTouchEvent(event, 0, 0, true), false);
            NativePaint.Companion.touchEvent(this.mNativeHandle, actionMasked, jNanoTime, 2, 2, this.mPoints);
            this.mTouching = false;
            ScrollListener scrollListener4 = this.mScrollListener;
            if (scrollListener4 != null) {
                scrollListener4.touchCanvas(false);
                return;
            }
            return;
        }
        this.mIsStylus = event.getToolType(0) == 2;
        Context context = this.mContext;
        Intrinsics.checkNotNull(context);
        Display display = context.getDisplay();
        Intrinsics.checkNotNull(display);
        if (display.getRefreshRate() != this.mRefreshRate) {
            float refreshRate = display.getRefreshRate();
            this.mRefreshRate = refreshRate;
            NativePaint.Companion.setRefreshRate(this.mNativeHandle, refreshRate);
        }
        boolean zContainsEvent = containsEvent(event);
        (zContainsEvent ? this.lastValidPoint : this.lastInvalidPoint).set(event.getX(), event.getY());
        if (actionMasked == 0) {
            ScrollListener scrollListener5 = this.mScrollListener;
            if (scrollListener5 != null) {
                scrollListener5.touchCanvas(true);
            }
            this.mPointIdx = 0;
            long j = jNanoTime / IndexProtocol.CONFIG_VAL_MAX_DOC_CNT;
            this.mPreviousTime = j;
            this.mStartTime = j;
            getTouchEvent(event, 0, 0, true);
            NativePaint.Companion companion = NativePaint.Companion;
            companion.setInputType(this.mNativeHandle, this.mIsStylus);
            companion.touchEvent(this.mNativeHandle, actionMasked, jNanoTime, 1, 1, this.mPoints);
            if (this.mEnableShapeRegular) {
                this.mShapeRegularTimer = new Timer();
            }
            Timer timer3 = this.mShapeRegularTimer;
            if (timer3 != null) {
                timer3.schedule(new TimerTask() { // from class: com.oplusos.vfxsdk.doodleengine.stylus.OplusStylusImplement.touchEvent.1
                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        NativePaint.Companion.shapeRegular(OplusStylusImplement.this.mNativeHandle);
                    }
                }, 350L, 100L);
            }
            RendReplacePointUtil.Companion.getLastInValidPointF().set(Float.NaN, Float.NaN);
            this.mTouching = true;
            return;
        }
        if (2 == actionMasked) {
            Pair<MotionEvent, Integer> replaceEventAndAction = getReplaceEventAndAction(zContainsEvent, event, actionMasked);
            if (replaceEventAndAction != null) {
                getTouchEvent(replaceEventAndAction.c(), 0, 0, true);
                str = " -- ";
                str3 = "replace point: [";
                str2 = "] to [";
                z = true;
                NativePaint.Companion.touchEvent(this.mNativeHandle, replaceEventAndAction.d().intValue(), jNanoTime, 1, 1, this.mPoints);
                Log.d(str4, str3 + event.getX() + ", " + event.getY() + str + actionMasked + str2 + replaceEventAndAction.c().getX() + ", " + replaceEventAndAction.c().getY() + str + replaceEventAndAction.d().intValue() + ']');
            } else {
                str = " -- ";
                str2 = "] to [";
                str3 = "replace point: [";
                z = true;
                Log.d(str4, "replace point: fail -- move");
            }
        } else {
            str = " -- ";
            str2 = "] to [";
            str3 = "replace point: [";
            z = true;
        }
        if (z == actionMasked || 3 == actionMasked) {
            Timer timer4 = this.mShapeRegularTimer;
            if (timer4 != null) {
                timer4.cancel();
            }
            Timer timer5 = this.mShapeRegularTimer;
            if (timer5 != null) {
                timer5.purge();
            }
            this.mShapeRegularTimer = null;
            this.mTouchPointerDown = false;
            String str5 = str3;
            Pair<MotionEvent, Integer> replaceEventAndAction2 = getReplaceEventAndAction(zContainsEvent, event, actionMasked);
            if (replaceEventAndAction2 != null) {
                Log.d(str4, str5 + event.getX() + ", " + event.getY() + str + actionMasked + str2 + replaceEventAndAction2.c().getX() + ", " + replaceEventAndAction2.c().getY() + str + replaceEventAndAction2.d().intValue() + ']');
                getTouchEvent(replaceEventAndAction2.c(), 0, 0, z);
                NativePaint.Companion.touchEvent(this.mNativeHandle, replaceEventAndAction2.d().intValue(), jNanoTime, 1, 1, this.mPoints);
            } else {
                Log.d(str4, "replace point: fail -- up");
            }
            if ((jNanoTime / IndexProtocol.CONFIG_VAL_MAX_DOC_CNT) - this.mStartTime > 10000 && (trackEngineCallback = this.mTrackEngine) != null) {
                trackEngineCallback.trackLongTouchInfo();
            }
        }
        boolean z2 = (actionMasked == z || actionMasked == 3 || actionMasked == 6) ? false : z;
        this.mTouching = z2;
        if (z2 || (scrollListener = this.mScrollListener) == null) {
            return;
        }
        scrollListener.touchCanvas(false);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void undo() {
        if (!this.mTouching) {
            boolean zIsSpannedEditing = this.mEntityManager.isSpannedEditing();
            boolean zCanUndo = this.mEntityManager.canUndo();
            if (zIsSpannedEditing && zCanUndo) {
                this.mEntityManager.undo();
                return;
            } else {
                EntityManager.cancelSpanEdit$default(this.mEntityManager, false, 1, null);
                NativePaint.Companion.undo(this.mNativeHandle);
            }
        }
        Log.d(TAG, "Undo");
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void updateContent(float f, float f2, float f3) {
        float scaleInSDK = getScaleInSDK(f3);
        StringBuilder sbQ = com.coui.appcompat.card.c.q("setContentTrans: ", f, ", ", f2, ", scale: ");
        sbQ.append(scaleInSDK);
        DLog(sbQ.toString());
        if (this.mTouching) {
            return;
        }
        NativePaint.Companion.setContentTrans(this.mNativeHandle, f, f2, scaleInSDK);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void updateDisplayRect(float[] values) {
        Intrinsics.checkNotNullParameter(values, "values");
        setImageViewTrans(values);
        NativePaint.Companion.setImageViewTrans(this.mNativeHandle, this.mTx, this.mTy, this.mCurScale);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void updateEntity(String id, String str, Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(id, "id");
        if (!(!Intrinsics.areEqual(id, ""))) {
            throw new IllegalArgumentException("Must: id != \"\"".toString());
        }
        NativePaint.Companion.updateEntity(this.mNativeHandle, id, str, bitmap);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void updateNaiNbi(Context context, FontDataList fontList, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(fontList, "fontList");
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void updateRendRect(RectF rect) {
        Intrinsics.checkNotNullParameter(rect, "rect");
        Log.d(TAG, "updateRendRect :" + rect);
        setRendRect(rect);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void zOrderTopChange(boolean z) {
        com.coui.appcompat.card.c.A("zOrderTopChange: top:", z, TAG);
        PaintSurfaceView paintSurfaceView = this.mSurfaceView;
        if (paintSurfaceView != null) {
            paintSurfaceView.setZOrderOnTop(z);
        }
        PaintSurfaceView paintSurfaceView2 = this.mSurfaceView;
        if (paintSurfaceView2 != null) {
            paintSurfaceView2.setBackgroundColor(0);
            if (z) {
                PaintSurfaceView paintSurfaceView3 = this.mSurfaceView;
                if (paintSurfaceView3 != null) {
                    paintSurfaceView3.activateSurface();
                }
                PaintTextureView paintTextureView = this.mTextureView;
                if (paintTextureView != null) {
                    paintTextureView.deactivateSurface();
                    return;
                }
                return;
            }
            PaintTextureView paintTextureView2 = this.mTextureView;
            if (paintTextureView2 != null) {
                paintTextureView2.enableTextureSurface(true);
            }
            PaintTextureView paintTextureView3 = this.mTextureView;
            if (paintTextureView3 != null) {
                paintTextureView3.activateSurface();
            }
            PaintSurfaceView paintSurfaceView4 = this.mSurfaceView;
            if (paintSurfaceView4 != null) {
                paintSurfaceView4.deactivateSurface();
            }
        }
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void extendCanvas(float f, boolean z, l<? super RectF, Unit> lVar) {
        this.extendCallback = lVar;
        DLog("extendCanvas offsetY: " + f + ", up: " + z);
        if (f > 0.0f && this.mRendRect.height() >= this.canvasExtendMax) {
            DLog("extendCanvas return, height > max: " + this.canvasExtendMax);
            return;
        }
        float f2 = z ? 1.0f : -1.0f;
        if (f > 0.0f) {
            float fHeight = this.mRendRect.height() + f;
            int i = this.canvasExtendMax;
            if (fHeight > i) {
                NativePaint.Companion.extendDrawableRect(this.mNativeHandle, (i - this.mRendRect.height()) * f2, z);
                return;
            }
        }
        NativePaint.Companion.extendDrawableRect(this.mNativeHandle, f * f2, z);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void insertImage(String id, String path, PointF center, InsertImageCallback callback) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(center, "center");
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.mEntityManager.insertImage(center, id, path, callback);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void initCanvas(float f, float f2, float f3) {
        this.mCurScale = f;
        this.mTx = f2;
        this.mTy = f3;
        initImageViewTrans();
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void insertTextToPaintView(String content, PointF point, final TextOperationCallback callback) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(content, "content");
        Intrinsics.checkNotNullParameter(point, "point");
        Intrinsics.checkNotNullParameter(callback, "callback");
        if (content.length() == 0) {
            callback.onFailure(TextOperationError.CONTENT_EMPTY);
            return;
        }
        finishSelect();
        this.addingExternalText = true;
        this.mEntityManager.insertText(content, point, new TextOperationCallback() { // from class: com.oplusos.vfxsdk.doodleengine.stylus.OplusStylusImplement.insertTextToPaintView.2
            @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.TextOperationCallback
            public void onFailure(TextOperationError error) {
                Intrinsics.checkNotNullParameter(error, "error");
                callback.onFailure(error);
                this.addingExternalText = false;
            }

            @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.TextOperationCallback
            public void onSuccess() {
                callback.onSuccess();
                this.addingExternalText = false;
            }
        });
    }

    private final void onSaved(String str, String str2, FileCode fileCode) {
        SaveListener saveListener = this.mSaveListener;
        if (saveListener != null) {
            saveListener.onSaved(new ActionResult(str, str2 == null ? "" : str2, "", "", "", 0, 0, fileCode, null, ModuleType.TYPE_LAUNCHER, null));
        }
        defpackage.d.z("onSave end： ", str2, TAG);
        this.saveImagePath = null;
        this.saveDataPath = null;
    }

    public final void onZooming(float f) {
        DLog("onZooming: scale: " + f);
        this.zoomScale = f;
        if (this.mZoomListener != null) {
            Log.d(TAG, "onZooming call");
            ZoomingListener zoomingListener = this.mZoomListener;
            Intrinsics.checkNotNull(zoomingListener);
            zoomingListener.onZooming(0.0f, 0.0f, 1.0f, (int) (f * 100));
        }
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void changeSelectedColor(int i, float f) {
        changeSelectedColor(Color.red(i) / 255.0f, Color.green(i) / 255.0f, Color.blue(i) / 255.0f, f);
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void clearStep() {
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void enableSelectionBeautify() {
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void finishSpannedEdit() {
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void handWritingToText() {
    }

    public final void onDoodleBlockDataReady() {
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void onStart() {
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void onStop() {
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void resetScale() {
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public float converseScaleByPageScale(float f) {
        return f;
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void enableCalculator(boolean z) {
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void enableWriteBeautify(boolean z) {
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setOnConvertTextReplace(kotlin.jvm.functions.a<Unit> aVar) {
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void triggerDownload(int i) {
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void insertEmptyTextBoxAt(float f, float f2) {
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void onDownloadComplete(int i, boolean z) {
    }

    @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.StylusInterface
    public void setPreviewStatus(boolean z, boolean z2) {
    }
}
