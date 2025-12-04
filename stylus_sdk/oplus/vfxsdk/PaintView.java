package com.oplusos.vfxsdk.doodleengine;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.h;
import com.heytap.cloudkit.libcommon.utils.CloudDeviceInfoUtil;
import com.oplus.smartenginehelper.ParserTag;
import com.oplusos.vfxsdk.doodleengine.Paint;
import com.oplusos.vfxsdk.doodleengine.PaintView;
import com.oplusos.vfxsdk.doodleengine.base.ActionResult;
import com.oplusos.vfxsdk.doodleengine.base.FontType;
import com.oplusos.vfxsdk.doodleengine.base.ImplPlatform;
import com.oplusos.vfxsdk.doodleengine.base.SaveDrawingResult;
import com.oplusos.vfxsdk.doodleengine.data.ScaleData;
import com.oplusos.vfxsdk.doodleengine.factory.DefaultStylusImplProvider;
import com.oplusos.vfxsdk.doodleengine.font.FontDataList;
import com.oplusos.vfxsdk.doodleengine.font.IDownloadListener;
import com.oplusos.vfxsdk.doodleengine.statics.StatisticsCallSummary;
import com.oplusos.vfxsdk.doodleengine.stylus.StylusManager;
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
import com.oplusos.vfxsdk.doodleengine.stylusapi.TrackEngineCallback;
import com.oplusos.vfxsdk.doodleengine.stylusapi.UpdateImageCallback;
import com.oplusos.vfxsdk.doodleengine.stylusapi.ZoomingListener;
import com.oplusos.vfxsdk.doodleengine.toolkit.Toolkit;
import com.oplusos.vfxsdk.doodleengine.toolkit.ToolkitDataStore;
import com.oplusos.vfxsdk.doodleengine.toolkit.ToolkitModel;
import com.oplusos.vfxsdk.doodleengine.toolkit.ToolkitPopupWindow;
import com.oplusos.vfxsdk.doodleengine.toolkit.internal.IPESettingManager;
import com.oplusos.vfxsdk.doodleengine.util.ActionCallbackManager;
import com.oplusos.vfxsdk.doodleengine.util.CanvasRendRectManager;
import com.oplusos.vfxsdk.doodleengine.util.LatencyReduceUtils;
import com.oplusos.vfxsdk.doodleengine.util.PaintFileUtil;
import com.oplusos.vfxsdk.doodleengine.util.RendRectTopExtendUtil;
import com.oplusos.vfxsdk.doodleengine.view.PaintEditBgView;
import com.oplusos.vfxsdk.doodleengine.view.PaintSurfaceView;
import com.oplusos.vfxsdk.doodleengine.view.PaintTextureView;
import defpackage.d;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.b;
import kotlin.c;
import kotlin.jvm.functions.a;
import kotlin.jvm.functions.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.functions.q;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.e;
import org.spongycastle.asn1.cmc.BodyPartID;

/* compiled from: PaintView.kt */
/* loaded from: classes5.dex */
public class PaintView extends FrameLayout implements IPESettingManager.OnStylusConnectionChangeListener {
    private static final String ACTION_TURN_TO_STYLUS_SETTINGS = "com.oplus.ipemanager.action.pencil_setting_from_notes";
    public static final Companion Companion = new Companion(null);
    public static final String DEFAULT_PROVIDER = "com.oplusos.vfxsdk.doodleengine.factory.DefaultStylusImplProvider";
    private static final int ERROR_CODE = 2;
    private static final String TAG = "PAINT:PaintView";
    private static final String VERSION = "VERSION_6.0.4";
    private static boolean inReplenishPoint;
    private boolean autoPausePostFrameCallback;
    private CanvasRendRectManager canvasRendRectManager;
    private PaintEditBgView.OnPaintEditBgDrawCallback editBgDrawingCallback;
    private ScrollListener externalScrollListener;
    private boolean isDestroy;
    private boolean isSaving;
    private Context mContext;
    private String mDataPath;
    private PaintEditBgView mEditBgView;
    private boolean mEmergency;
    private boolean mEnableColorExtractor;
    private boolean mEnableGestureArea;
    private boolean mEnableOverlay;
    private boolean mEnableVectorDraw;
    private final ArrayList<Rect> mExclusionRects;
    private boolean mFingerTouchEventEnabled;
    private FontType mFontType;
    private final float mGestureShieldOffset;
    private final b mGlobalLayoutListener$delegate;
    private String mImagePath;
    private boolean mInvalid;
    private boolean mIsDarkTheme;
    private boolean mIsEnableSaveData;
    private boolean mIsFloatWindow;
    private boolean mIsHovering;
    private boolean mIsOplusImpl;
    private boolean mIsScreenShot;
    private LatencyReduceUtils mLatencyReduceUtils;
    private String mMixturePath;
    private boolean mPreviewStatus;
    private boolean mScrolling;
    private StylusInterface mStylusImpl;
    private StylusManager mStylusManager;
    private PaintSurfaceView mSurfaceView;
    private PaintTextureView mTextureView;
    private Toolkit mToolkit;
    private ToolkitPopupWindow mToolkitPopupWindow;
    private VibrationType mVibrationType;
    private boolean mVibrationTypeChanged;
    private int mViewType;
    private boolean mZooming;
    private IPESettingManager.StylusConnectionContentObserver stylusConnectionRegister;
    private StylusImplProvider stylusImplProvider;
    private TrackEngineCallback trackEngine;

    /* compiled from: PaintView.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(e eVar) {
            this();
        }

        public final boolean getInReplenishPoint() {
            return PaintView.inReplenishPoint;
        }

        public final void setInReplenishPoint(boolean z) {
            PaintView.inReplenishPoint = z;
        }
    }

    /* compiled from: PaintView.kt */
    public static final class MyGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        private final WeakReference<PaintView> weakRef;

        public MyGlobalLayoutListener(PaintView view) {
            Intrinsics.checkNotNullParameter(view, "view");
            this.weakRef = new WeakReference<>(view);
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            PaintView paintView = this.weakRef.get();
            if (paintView != null) {
                paintView.onGlobalLayout();
            }
        }
    }

    /* compiled from: PaintView.kt */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Paint.Type.values().length];
            try {
                iArr[Paint.Type.PENCIL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Paint.Type.BALLPEN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Paint.Type.PEN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PaintView(Context context, AttributeSet attributeSet, int i) throws IllegalAccessException, InstantiationException {
        StylusInterface stylusInterface;
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.mViewType = 1;
        this.mVibrationType = VibrationType.NONE;
        this.mIsOplusImpl = true;
        this.mExclusionRects = new ArrayList<>();
        this.mEnableGestureArea = true;
        this.mEnableVectorDraw = true;
        this.mEnableColorExtractor = true;
        this.mIsEnableSaveData = true;
        this.mFingerTouchEventEnabled = true;
        this.mGlobalLayoutListener$delegate = c.b(new a<MyGlobalLayoutListener>() { // from class: com.oplusos.vfxsdk.doodleengine.PaintView$mGlobalLayoutListener$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.a
            public final PaintView.MyGlobalLayoutListener invoke() {
                return new PaintView.MyGlobalLayoutListener(this.this$0);
            }
        });
        this.canvasRendRectManager = new CanvasRendRectManager();
        setAttrsValues(attributeSet);
        this.mContext = context;
        StylusImplProvider stylusImplProvider = this.stylusImplProvider;
        if (stylusImplProvider == null || (stylusInterface = stylusImplProvider.getStylusInterface(context)) == null) {
            stylusInterface = null;
        } else {
            stylusInterface.setScrollListener(new ScrollListener() { // from class: com.oplusos.vfxsdk.doodleengine.PaintView$1$1
                @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.ScrollListener
                public void onLeaped(float f, float f2, float f3, float f4, float f5) {
                    ScrollListener scrollListener = this.this$0.externalScrollListener;
                    if (scrollListener != null) {
                        scrollListener.onLeaped(f, f2, f3, f4, f5);
                    }
                }

                @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.ScrollListener
                public void onScaleChanged(float f) {
                    StylusInterface stylusInterface2 = this.this$0.mStylusImpl;
                    if (stylusInterface2 != null) {
                        f = stylusInterface2.converseScaleByPageScale(f);
                    }
                    PaintEditBgView paintEditBgView = this.this$0.mEditBgView;
                    if (paintEditBgView != null) {
                        paintEditBgView.doScale(f, 0.0f, 0.0f);
                    }
                    ScrollListener scrollListener = this.this$0.externalScrollListener;
                    if (scrollListener != null) {
                        scrollListener.onScaleChanged(f);
                    }
                }

                @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.ScrollListener
                public void onScrollEnd(RectF rendRectF) {
                    Intrinsics.checkNotNullParameter(rendRectF, "rendRectF");
                    ScrollListener scrollListener = this.this$0.externalScrollListener;
                    if (scrollListener != null) {
                        scrollListener.onScrollEnd(rendRectF);
                    }
                }

                @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.ScrollListener
                public void onScrollRestricted() {
                    ScrollListener scrollListener = this.this$0.externalScrollListener;
                    if (scrollListener != null) {
                        scrollListener.onScrollRestricted();
                    }
                }

                @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.ScrollListener
                public void onScrollScale(RectF rendRectF, RectF rectF, ScaleData scale, p<? super Integer, ? super Integer, Unit> maxMoveBlock) {
                    Intrinsics.checkNotNullParameter(rendRectF, "rendRectF");
                    Intrinsics.checkNotNullParameter(scale, "scale");
                    Intrinsics.checkNotNullParameter(maxMoveBlock, "maxMoveBlock");
                    PaintEditBgView paintEditBgView = this.this$0.mEditBgView;
                    if (paintEditBgView != null) {
                        paintEditBgView.doScale(scale.getScale(), 0.0f, 0.0f);
                    }
                    ScrollListener scrollListener = this.this$0.externalScrollListener;
                    if (scrollListener != null) {
                        scrollListener.onScrollScale(rendRectF, rectF, scale, maxMoveBlock);
                    }
                }

                @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.ScrollListener
                public void onScrollStart(RectF rendRectF) {
                    Intrinsics.checkNotNullParameter(rendRectF, "rendRectF");
                    ScrollListener scrollListener = this.this$0.externalScrollListener;
                    if (scrollListener != null) {
                        scrollListener.onScrollStart(rendRectF);
                    }
                }

                @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.ScrollListener
                public void touchCanvas(boolean z) {
                    ScrollListener scrollListener = this.this$0.externalScrollListener;
                    if (scrollListener != null) {
                        scrollListener.touchCanvas(z);
                    }
                }
            });
        }
        this.mStylusImpl = stylusInterface;
        boolean z = (stylusInterface != null ? stylusInterface.getPlatform() : null) == ImplPlatform.PlatForm_O;
        this.mIsOplusImpl = z;
        ToolkitModel.Companion.setRunOplus(z);
        StylusInterface stylusInterface2 = this.mStylusImpl;
        if (stylusInterface2 != null) {
            stylusInterface2.setCanvasRendRectManager(this.canvasRendRectManager);
        }
        initAuth(new a<Unit>() { // from class: com.oplusos.vfxsdk.doodleengine.PaintView.2
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
                StylusInterface stylusInterface3 = PaintView.this.mStylusImpl;
                if (stylusInterface3 != null) {
                    final PaintView paintView = PaintView.this;
                    stylusInterface3.initSurface(paintView.mViewType, paintView, paintView.mIsFloatWindow, paintView.mEnableOverlay, new q<PaintSurfaceView, PaintTextureView, PaintEditBgView, Unit>() { // from class: com.oplusos.vfxsdk.doodleengine.PaintView$2$1$1
                        {
                            super(3);
                        }

                        @Override // kotlin.jvm.functions.q
                        public /* bridge */ /* synthetic */ Unit invoke(PaintSurfaceView paintSurfaceView, PaintTextureView paintTextureView, PaintEditBgView paintEditBgView) {
                            invoke2(paintSurfaceView, paintTextureView, paintEditBgView);
                            return Unit.INSTANCE;
                        }

                        /* renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(PaintSurfaceView paintSurfaceView, PaintTextureView paintTextureView, PaintEditBgView paintEditBgView) {
                            paintView.mSurfaceView = paintSurfaceView;
                            paintView.mTextureView = paintTextureView;
                            PaintView paintView2 = paintView;
                            if (paintEditBgView != null) {
                                paintEditBgView.setOnPaintEditBgDrawCallback(paintView2.editBgDrawingCallback);
                            } else {
                                paintEditBgView = null;
                            }
                            paintView2.mEditBgView = paintEditBgView;
                        }
                    });
                }
            }
        });
        StylusImplProvider stylusImplProvider2 = this.stylusImplProvider;
        TrackEngineCallback trackEngineCallback = stylusImplProvider2 != null ? stylusImplProvider2.getTrackEngineCallback(VERSION) : null;
        this.trackEngine = trackEngineCallback;
        StylusInterface stylusInterface3 = this.mStylusImpl;
        if (stylusInterface3 != null) {
            stylusInterface3.initTrack(trackEngineCallback);
        }
        StringBuilder sb = new StringBuilder("create impl: ");
        sb.append(this.mStylusImpl != null);
        sb.append(", trackEngine: ");
        sb.append(this.trackEngine != null);
        sb.append(", runO: ");
        sb.append(this.mStylusImpl);
        Log.i(TAG, sb.toString());
        StatisticsCallSummary.INSTANCE.init(context);
        initStylusManager();
        this.mLatencyReduceUtils = new LatencyReduceUtils(this.mContext);
        Context context2 = this.mContext;
        Intrinsics.checkNotNull(context2);
        this.mGestureShieldOffset = context2.getResources().getDimension(R.dimen.de_toolkit_window_gesture_shield);
        setFocusable(false);
        getViewTreeObserver().addOnGlobalLayoutListener(getMGlobalLayoutListener());
        initSoundStatus();
        getSysProp();
        StringBuilder sb2 = new StringBuilder("PaintView constructor, ");
        sb2.append(hashCode());
        sb2.append(", mViewType: ");
        androidx.appsearch.app.e.y(sb2, this.mViewType, TAG);
        registerStylusConnectState();
    }

    private final MyGlobalLayoutListener getMGlobalLayoutListener() {
        return (MyGlobalLayoutListener) this.mGlobalLayoutListener$delegate.getValue();
    }

    private final boolean getSoundStatusFromSystemFeature() {
        PackageManager packageManager;
        Context context = this.mContext;
        if (context == null || (packageManager = context.getPackageManager()) == null) {
            return false;
        }
        return packageManager.hasSystemFeature("paint.pencil.sound.default.status");
    }

    @SuppressLint({"PrivateApi"})
    private final boolean getSoundSysProp() {
        try {
            Class<?> cls = Class.forName(CloudDeviceInfoUtil.PHONE_SYSTEM_PROPERTIES);
            Object objInvoke = cls.getMethod(ParserTag.TAG_GET, String.class).invoke(cls, "paint.pencil.sound.default.status");
            Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type kotlin.String");
            String str = (String) objInvoke;
            Log.d(TAG, "paint.pencil.sound.default.status = " + str);
            return Intrinsics.areEqual(str, "1");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    @SuppressLint({"PrivateApi"})
    private final void getSysProp() {
        try {
            Class<?> cls = Class.forName(CloudDeviceInfoUtil.PHONE_SYSTEM_PROPERTIES);
            Method method = cls.getMethod(ParserTag.TAG_GET, String.class);
            Object objInvoke = method.invoke(cls, "paint.colorextractor.disable");
            String str = objInvoke instanceof String ? (String) objInvoke : null;
            Object objInvoke2 = method.invoke(cls, "paint.zoominclear.disable");
            String str2 = objInvoke2 instanceof String ? (String) objInvoke2 : null;
            Log.d(TAG, "colorextractor.disable = " + str + "  zoominclear.disable = " + str2);
            if (Intrinsics.areEqual(str, "1") || Intrinsics.areEqual(str2, "1")) {
                this.mEnableColorExtractor = false;
                this.mEnableVectorDraw = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable th) {
            throw th;
        }
    }

    private final void initSoundStatus() {
        ToolkitDataStore.Companion companion = ToolkitDataStore.Companion;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        soundSwitch(companion.getInstant(context).getSoundStatus());
    }

    private final void initStylusManager() {
        try {
            if (new Intent(ACTION_TURN_TO_STYLUS_SETTINGS).resolveActivity(getContext().getApplicationContext().getPackageManager()) != null) {
                StylusManager stylusManager = new StylusManager(getContext().getApplicationContext());
                stylusManager.start();
                StylusInterface stylusInterface = this.mStylusImpl;
                if (stylusInterface != null) {
                    stylusInterface.initStylusManager(stylusManager);
                }
                this.mStylusManager = stylusManager;
                return;
            }
        } catch (Exception e) {
            h.z(e, new StringBuilder("checkActionIsSupport failed: "), Toolkit.TAG);
        }
        Log.d(Toolkit.TAG, "checkActionIsSupport failed: not support");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initUseFinger() {
        ToolkitModel.Companion companion = ToolkitModel.Companion;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        if (!companion.needShowExtraMenu(context)) {
            setStylus(false);
            soundSwitch(false);
        } else {
            Context context2 = getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "context");
            setStylus(!companion.getUseFingerStatus(context2));
        }
    }

    public static /* synthetic */ FileCode load$default(PaintView paintView, String str, String str2, String str3, LoadListener loadListener, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: load");
        }
        if ((i & 8) != 0) {
            loadListener = null;
        }
        return paintView.load(str, str2, str3, loadListener);
    }

    public static /* synthetic */ void onCreate$default(PaintView paintView, int i, boolean z, FragmentManager fragmentManager, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: onCreate");
        }
        if ((i2 & 1) != 0) {
            i = 8;
        }
        if ((i2 & 2) != 0) {
            z = false;
        }
        if ((i2 & 4) != 0) {
            fragmentManager = null;
        }
        paintView.onCreate(i, z, fragmentManager);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onGlobalLayout() {
        Log.d(TAG, "onGlobalLayout, " + hashCode() + ", width: " + getWidth() + ", height: " + getHeight() + ", top: " + getTop() + ", bottom: " + getBottom() + ", left: " + getLeft() + ", right: " + getRight());
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setPaintViewSize(getWidth(), getHeight());
        }
        SPHelper companion = SPHelper.Companion.getInstance();
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        companion.checkEmpty(context);
    }

    private final void registerStylusConnectState() {
        if (!ToolkitModel.Companion.checkDevices() && this.stylusConnectionRegister == null) {
            this.stylusConnectionRegister = new IPESettingManager.StylusConnectionContentObserver(this);
            Log.d(TAG, " registerStylusConnectState");
            ContentResolver contentResolver = getContext().getContentResolver();
            if (contentResolver != null) {
                Uri uriFor = Settings.Global.getUriFor("ipe_pencil_connect_state");
                IPESettingManager.StylusConnectionContentObserver stylusConnectionContentObserver = this.stylusConnectionRegister;
                Intrinsics.checkNotNull(stylusConnectionContentObserver);
                contentResolver.registerContentObserver(uriFor, false, stylusConnectionContentObserver);
            }
        }
    }

    public static /* synthetic */ void reset$default(PaintView paintView, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: reset");
        }
        if ((i & 1) != 0) {
            z = false;
        }
        paintView.reset(z);
    }

    public static /* synthetic */ FileCode save$default(PaintView paintView, String str, String str2, int i, String str3, boolean z, boolean z2, int i2, Object obj) {
        if (obj == null) {
            return paintView.save(str, str2, i, str3, (i2 & 16) != 0 ? false : z, (i2 & 32) != 0 ? false : z2);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: save");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void saveDrawings$default(PaintView paintView, String str, l lVar, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: saveDrawings");
        }
        if ((i & 2) != 0) {
            lVar = null;
        }
        paintView.saveDrawings(str, lVar);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void savePreview$default(PaintView paintView, String str, l lVar, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: savePreview");
        }
        if ((i & 2) != 0) {
            lVar = null;
        }
        paintView.savePreview(str, lVar);
    }

    private final void setAttrsValues(AttributeSet attributeSet) throws IllegalAccessException, InstantiationException {
        StylusImplProvider stylusImplProvider;
        if (attributeSet == null) {
            Log.d(TAG, "Don't set custom attribute");
            return;
        }
        int attributeCount = attributeSet.getAttributeCount();
        int i = 0;
        while (true) {
            stylusImplProvider = null;
            if (i >= attributeCount) {
                break;
            }
            int attributeNameResource = attributeSet.getAttributeNameResource(i);
            if (attributeNameResource == R.attr.enableOverlay) {
                this.mEnableOverlay = attributeSet.getAttributeBooleanValue(i, false);
            } else if (attributeNameResource == R.attr.viewType) {
                this.mViewType = attributeSet.getAttributeIntValue(i, 1);
            } else if (attributeNameResource == R.attr.isFloatWindow) {
                this.mIsFloatWindow = attributeSet.getAttributeBooleanValue(i, false);
            } else if (attributeNameResource == R.attr.stylusImpl) {
                try {
                    Object objNewInstance = Class.forName(attributeSet.getAttributeValue(i)).newInstance();
                    if (objNewInstance instanceof StylusImplProvider) {
                        stylusImplProvider = (StylusImplProvider) objNewInstance;
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Failed to instantiate stylusImplProvider", e);
                }
                this.stylusImplProvider = stylusImplProvider;
            }
            i++;
        }
        if (this.stylusImplProvider == null) {
            try {
                DefaultStylusImplProvider.Companion companion = DefaultStylusImplProvider.Companion;
                Object objNewInstance2 = DefaultStylusImplProvider.class.newInstance();
                if (objNewInstance2 instanceof StylusImplProvider) {
                    stylusImplProvider = (StylusImplProvider) objNewInstance2;
                }
            } catch (Exception e2) {
                Log.e(TAG, "Failed to instantiate default stylusImplProvider", e2);
            }
            this.stylusImplProvider = stylusImplProvider;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.stylusImplProvider);
        sb.append(" config: viewType: ");
        sb.append(this.mViewType);
        sb.append(", enableOverlay: ");
        sb.append(this.mEnableOverlay);
        sb.append(", isFloatWindow: ");
        d.B(sb, this.mIsFloatWindow, TAG);
    }

    public static /* synthetic */ void setRendRectWitScale$default(PaintView paintView, RectF rectF, float f, float f2, float f3, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setRendRectWitScale");
        }
        if ((i & 16) != 0) {
            z = true;
        }
        paintView.setRendRectWitScale(rectF, f, f2, f3, z);
    }

    public static /* synthetic */ void setTouchBorderValues$default(PaintView paintView, long j, int i, int i2, int i3, int i4, int i5, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setTouchBorderValues");
        }
        if ((i5 & 1) != 0) {
            j = 50;
        }
        paintView.setTouchBorderValues(j, (i5 & 2) != 0 ? -1 : i, (i5 & 4) != 0 ? -1 : i2, (i5 & 8) != 0 ? -1 : i3, (i5 & 16) != 0 ? -1 : i4);
    }

    public static /* synthetic */ void splitPaintFile$default(PaintView paintView, String str, List list, List list2, p pVar, long j, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: splitPaintFile");
        }
        if ((i & 8) != 0) {
            pVar = null;
        }
        p pVar2 = pVar;
        if ((i & 16) != 0) {
            j = 0;
        }
        paintView.splitPaintFile(str, list, list2, pVar2, j);
    }

    private final void surfaceActive(boolean z) {
        if (z) {
            PaintTextureView paintTextureView = this.mTextureView;
            if (paintTextureView != null) {
                paintTextureView.activateSurface();
            }
            PaintSurfaceView paintSurfaceView = this.mSurfaceView;
            if (paintSurfaceView != null) {
                paintSurfaceView.deactivateSurface();
                return;
            }
            return;
        }
        PaintSurfaceView paintSurfaceView2 = this.mSurfaceView;
        if (paintSurfaceView2 != null) {
            paintSurfaceView2.activateSurface();
        }
        PaintTextureView paintTextureView2 = this.mTextureView;
        if (paintTextureView2 != null) {
            paintTextureView2.deactivateSurface();
        }
    }

    private final void unregisterStylusConnectState() {
        IPESettingManager.StylusConnectionContentObserver stylusConnectionContentObserver = this.stylusConnectionRegister;
        if (stylusConnectionContentObserver != null) {
            Log.d(TAG, " unregisterStylusConnectState");
            ContentResolver contentResolver = getContext().getContentResolver();
            if (contentResolver != null) {
                contentResolver.unregisterContentObserver(stylusConnectionContentObserver);
            }
            this.stylusConnectionRegister = null;
        }
    }

    private final boolean updateGestureExclusionArea(MotionEvent motionEvent) {
        if (this.mEnableGestureArea) {
            return false;
        }
        if (motionEvent.getActionMasked() == 7 || motionEvent.getActionMasked() == 9) {
            this.mIsHovering = true;
            float y = motionEvent.getY(0);
            this.mExclusionRects.clear();
            this.mExclusionRects.add(new Rect(getLeft(), (int) (y - this.mGestureShieldOffset), getRight(), (int) (y + this.mGestureShieldOffset)));
            setSystemGestureExclusionRects(this.mExclusionRects);
        } else if (motionEvent.getActionMasked() == 10) {
            this.mIsHovering = false;
            postDelayed(new com.oplus.richtext.editor.view.toolbar.content.e(this, 11), 50L);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateGestureExclusionArea$lambda$3(PaintView this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.mIsHovering) {
            return;
        }
        this$0.mExclusionRects.clear();
        this$0.mExclusionRects.add(new Rect(this$0.getLeft(), 0, this$0.getRight(), 0));
        this$0.setSystemGestureExclusionRects(this$0.mExclusionRects);
        Log.d(TAG, "cancelGestureExclusionArea");
    }

    public final void addAdapterFontDownLoadListener(IDownloadListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.addAdapterFontDownLoadListener(listener);
        }
    }

    public void addCustomBitmapMenu(List<MenuItemData> menuList, StylusInterface.BitmapMenuCallback bitmapMenuCallback) {
        Intrinsics.checkNotNullParameter(menuList, "menuList");
        Intrinsics.checkNotNullParameter(bitmapMenuCallback, "bitmapMenuCallback");
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.addCustomBitmapMenu(menuList, bitmapMenuCallback);
        }
    }

    public final void addDownloadListener(IDownloadListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.addDownloadListener(listener);
        }
    }

    public void addEntity(String id, String extra, float[] position, float f, float[] scaling, EntityType type, Color color, Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(extra, "extra");
        Intrinsics.checkNotNullParameter(position, "position");
        Intrinsics.checkNotNullParameter(scaling, "scaling");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(color, "color");
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.addEntity(id, extra, position, f, scaling, type, color, bitmap);
        }
    }

    public void addListener(PaintViewListener paintViewListener) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.addListener(paintViewListener);
        }
    }

    public void addLoadListener(LoadListener loadListener) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.addLoadListener(loadListener);
        }
    }

    public void bindRecognizeFile(String entDir) {
        Intrinsics.checkNotNullParameter(entDir, "entDir");
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.bindRecognizeFile(entDir);
        }
    }

    public boolean calculateCompressBottom(int i) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return stylusInterface.calculateCompressBottom(i);
        }
        return false;
    }

    public boolean calculateCompressTop(int i) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return stylusInterface.calculateCompressTop(i);
        }
        return false;
    }

    public final boolean canAutoSave() {
        StylusInterface stylusInterface = this.mStylusImpl;
        return stylusInterface != null && stylusInterface.canAutoSave();
    }

    public boolean canShowPastePopup() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return stylusInterface.canShowPastePopup();
        }
        return false;
    }

    public final void changeBeautifyFont(FontType font) {
        Intrinsics.checkNotNullParameter(font, "font");
        this.mFontType = font;
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.changeBeautifyFont(font);
        }
        StatisticsCallSummary.INSTANCE.onFontBeautyUse(true, String.valueOf(this.mFontType));
    }

    public void changeSelectedColor(float f, float f2, float f3, float f4) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.changeSelectedColor(f, f2, f3, f4);
        }
    }

    public void clear() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.clear();
        }
    }

    public void clearStep() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.clearStep();
        }
    }

    public final void disableBackGestureArea() {
        this.mEnableGestureArea = false;
    }

    public void disableEmergencySave() {
        this.mEmergency = false;
        this.mImagePath = null;
        this.mDataPath = null;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        Toolkit toolkit;
        if (motionEvent != null && motionEvent.getAction() == 0 && (toolkit = this.mToolkit) != null) {
            toolkit.foldToolkit();
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public void drawSizePoint(float f) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.drawSizePoint(f);
        }
    }

    public void enableEmergencySave(String str, String dataPath, String mixturePath) {
        Intrinsics.checkNotNullParameter(dataPath, "dataPath");
        Intrinsics.checkNotNullParameter(mixturePath, "mixturePath");
        this.mEmergency = true;
        this.mImagePath = str;
        this.mDataPath = dataPath;
        this.mMixturePath = mixturePath;
    }

    public final void enableFingerTouchEvent(boolean z) {
        this.mFingerTouchEventEnabled = z;
    }

    public void enableRulerMode(boolean z) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.enableRulerMode(z);
        }
    }

    public final void enableSaveData(boolean z) {
        this.mIsEnableSaveData = z;
        com.coui.appcompat.card.c.A("enableSaveData: ", z, TAG);
    }

    public void enableShapeRegular(boolean z) {
        com.coui.appcompat.card.c.A("enableShapeRegular: ", z, TAG);
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.enableShapeRegular(z);
        }
        SPHelper.Companion.getInstance().putBoolean(SPHelper.SP_SHAPE_REGULAR, z);
    }

    public final void enableVibration() {
        StylusManager stylusManager = this.mStylusManager;
        if (stylusManager != null) {
            Context context = this.mContext;
            Intrinsics.checkNotNull(context);
            stylusManager.enableVibration(context);
        }
    }

    public final void enterColorExtractorMode() {
        Context context = this.mContext;
        if (context == null) {
            Log.e(TAG, "Context is null, cannot enter color extractor mode");
            return;
        }
        if (Settings.canDrawOverlays(context)) {
            Toolkit toolkit = this.mToolkit;
            if (toolkit != null) {
                toolkit.showColorExtractorView();
            }
            Toolkit toolkit2 = this.mToolkit;
            if (toolkit2 != null) {
                toolkit2.popupWindowDismiss();
                return;
            }
            return;
        }
        Intent intent = new Intent();
        intent.setAction("android.settings.action.MANAGE_OVERLAY_PERMISSION");
        StringBuilder sb = new StringBuilder("package:");
        Context context2 = this.mContext;
        sb.append(context2 != null ? context2.getPackageName() : null);
        intent.setData(Uri.parse(sb.toString()));
        Context context3 = this.mContext;
        if (context3 != null) {
            context3.startActivity(intent);
        }
    }

    public final void exportBitmapFromPaint(String paintPath, String str, int i, int i2, l<? super Bitmap, Unit> lVar) {
        Intrinsics.checkNotNullParameter(paintPath, "paintPath");
        if ((str == null || str.length() == 0) && lVar == null) {
            return;
        }
        if (paintPath.length() == 0 || i <= 0 || i2 <= 0) {
            if (lVar != null) {
                lVar.invoke(null);
            }
        } else {
            StylusInterface stylusInterface = this.mStylusImpl;
            if (stylusInterface != null) {
                stylusInterface.exportBitmapFromPaint(paintPath, str, i, i2, lVar);
            }
        }
    }

    public void extendCanvas(float f) {
        if (f < 0.0f) {
            Log.d(TAG, "extendCanvas dy < 0");
            return;
        }
        Log.d(TAG, "extendCanvas dy: " + f);
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.extendCanvas(f);
        }
    }

    public void finishSelect() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.finishSelect();
        }
    }

    public void finishSpannedEdit() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.finishSpannedEdit();
        }
    }

    public void forceScroll(float f) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.forceScroll(f);
        }
    }

    public boolean getAutoPausePostFrameCallback() {
        return this.autoPausePostFrameCallback;
    }

    public Bitmap getCanvasBitmap() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return stylusInterface.getCanvasBitmap();
        }
        return null;
    }

    public int getCanvasHeight() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return stylusInterface.getCanvaHeight();
        }
        return 0;
    }

    public final float getContentBottomInScreen() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return stylusInterface.getContentBottomInScreen();
        }
        return 0.0f;
    }

    public float getContentOffsetX() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return stylusInterface.getContentOffsetX();
        }
        return 0.0f;
    }

    public float getContentOffsetY() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return stylusInterface.getContentOffsetY();
        }
        return 0.0f;
    }

    public float getContentScale() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return stylusInterface.getContentScale();
        }
        return 1.0f;
    }

    public Paint getCurrentPaint() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return stylusInterface.getCurrentPaint();
        }
        return null;
    }

    public int getDisplayHeight() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return stylusInterface.getDisplayHeight();
        }
        return 0;
    }

    public final float[][][] getDisplayStrokes() {
        float[][][] displayStrokes;
        StylusInterface stylusInterface = this.mStylusImpl;
        return (stylusInterface == null || (displayStrokes = stylusInterface.getDisplayStrokes()) == null) ? new float[0][][] : displayStrokes;
    }

    public final IDownloadListener getFontDownLoadListener() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return stylusInterface.getFontDownLoadListener();
        }
        return null;
    }

    public final RectF getLassoRect() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return stylusInterface.getLassoRect();
        }
        return null;
    }

    public FileCode getLoadedStatus() {
        FileCode loadedStatus;
        StylusInterface stylusInterface = this.mStylusImpl;
        return (stylusInterface == null || (loadedStatus = stylusInterface.getLoadedStatus()) == null) ? FileCode.Empty : loadedStatus;
    }

    public final FontType getMFontType() {
        return this.mFontType;
    }

    public int getMaxPaintHeight() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return stylusInterface.getMaxPaintHeight();
        }
        return 0;
    }

    public float getMaxScale() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return stylusInterface.getMaxScale();
        }
        return 0.0f;
    }

    public int getMenuHeightValue() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return stylusInterface.getMenuHeightValue();
        }
        return 0;
    }

    public float getMenuPositionX() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return stylusInterface.getMenuPositionX();
        }
        return 0.0f;
    }

    public float getMenuPositionY() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return stylusInterface.getMenuPositionY();
        }
        return 0.0f;
    }

    public int getMenuRotateValue() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return stylusInterface.getMenuRotateValue();
        }
        return 0;
    }

    public MenuType getMenuType() {
        MenuType[] menuTypeArrValues = MenuType.values();
        StylusInterface stylusInterface = this.mStylusImpl;
        return menuTypeArrValues[stylusInterface != null ? stylusInterface.getMenuType() : MenuType.Cancel.ordinal()];
    }

    public int getMenuWidthValue() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return stylusInterface.getMenuWidthValue();
        }
        return 0;
    }

    public boolean getOnlyStylus() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return stylusInterface.getOnlyStylus();
        }
        return false;
    }

    public final PaintEditBgView getPaintEditBgView() {
        return this.mEditBgView;
    }

    public long getPaintFileSize() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return stylusInterface.getPaintFileSize();
        }
        return 0L;
    }

    public int getPaintHeight() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return stylusInterface.getPaintHeight();
        }
        return 0;
    }

    public boolean getPreviewStatus() {
        return this.mPreviewStatus;
    }

    public boolean getRedoStatus() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return stylusInterface.getRedoStatus();
        }
        return false;
    }

    public Bitmap getRelativeCanvasBitmap() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return stylusInterface.getRelativeCanvasBitmap();
        }
        return null;
    }

    public RectF getRendRect() {
        RectF rendRect;
        StylusInterface stylusInterface = this.mStylusImpl;
        return (stylusInterface == null || (rendRect = stylusInterface.getRendRect()) == null) ? new RectF() : rendRect;
    }

    public float getRotateIconPositionX() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return stylusInterface.getRotateIconPositionX();
        }
        return 0.0f;
    }

    public float getRotateIconPositionY() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return stylusInterface.getRotateIconPositionY();
        }
        return 0.0f;
    }

    public FileCode getSavedStatus() {
        FileCode savedStatus;
        StylusInterface stylusInterface = this.mStylusImpl;
        return (stylusInterface == null || (savedStatus = stylusInterface.getSavedStatus()) == null) ? FileCode.Empty : savedStatus;
    }

    public RectF getSelectedRect() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return stylusInterface.getSelectedRecF();
        }
        return null;
    }

    public final Pair<Integer, Boolean> getSplitCount(String str) {
        Pair<Integer, Boolean> splitCount;
        if (str == null || str.length() == 0) {
            return new Pair<>(1, Boolean.FALSE);
        }
        StylusInterface stylusInterface = this.mStylusImpl;
        return (stylusInterface == null || (splitCount = stylusInterface.getSplitCount(str)) == null) ? new Pair<>(1, Boolean.FALSE) : splitCount;
    }

    public long getStepChangeTime() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return stylusInterface.getStepChangeTime();
        }
        return -1L;
    }

    public final Toolkit getToolkit$paint_intermediate_release() {
        return this.mToolkit;
    }

    public float getTopBlank() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return stylusInterface.getTopBlank();
        }
        return 0.0f;
    }

    public boolean getUndoStatus() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return stylusInterface.getUndoStatus();
        }
        return false;
    }

    public void initAuth(final a<Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.initAuth(new a<Unit>() { // from class: com.oplusos.vfxsdk.doodleengine.PaintView.initAuth.1
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
                    Log.d(PaintView.TAG, "initAuth: finished");
                    block.invoke();
                    this.initUseFinger();
                }
            });
        }
    }

    public void initCanvas(float[] fArr) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            Intrinsics.checkNotNull(fArr);
            stylusInterface.initCanvas(fArr);
        }
    }

    public void initContent(float f, float f2, float f3) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.initContent(f, f2, f3);
        }
    }

    public final void initFragmentManager(FragmentManager fragmentManager) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.initFragmentManager(fragmentManager, true);
        }
    }

    public final void insertImage(String id, String path, InsertImageCallback callback) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(callback, "callback");
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.insertImage(id, path, callback);
        }
        Toolkit toolkit = this.mToolkit;
        if (toolkit != null) {
            toolkit.changeLassoView();
        }
    }

    public void insertText(String content, PointF pointF, TextOperationCallback callback) {
        Intrinsics.checkNotNullParameter(content, "content");
        Intrinsics.checkNotNullParameter(pointF, "pointF");
        Intrinsics.checkNotNullParameter(callback, "callback");
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.insertTextToPaintView(content, pointF, callback);
        }
    }

    public boolean isChanged() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return stylusInterface.isChanged();
        }
        return false;
    }

    public boolean isContentEmpty() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return stylusInterface.isContentEmpty();
        }
        return false;
    }

    public final boolean isSpanEditing() {
        StylusInterface stylusInterface = this.mStylusImpl;
        return stylusInterface != null && stylusInterface.isSpanEditing();
    }

    public final boolean isSpanEditingEmpty() {
        StylusInterface stylusInterface = this.mStylusImpl;
        return stylusInterface != null && stylusInterface.isSpanEditingEmpty();
    }

    public final int isThereMore(int i, int i2, String str) {
        if (i == 0 || str == null) {
            Log.e(TAG, "isThereMore width = 0 or dataPath = null");
            return 2;
        }
        try {
            StylusImplProvider stylusImplProvider = this.stylusImplProvider;
            if (stylusImplProvider != null) {
                return stylusImplProvider.isThereMore(i, i2, str);
            }
            return 2;
        } catch (Exception unused) {
            return 2;
        }
    }

    public FileCode load(String str, String str2, String str3, LoadListener loadListener) {
        StringBuilder sbX = h.x(str2, "dataPath", str3, "mixturePath", "Load imagePath: ");
        d.A(sbX, str, ", dataPath: ", str2, ", mixturePath = ");
        androidx.appsearch.app.e.z(sbX, str3, TAG);
        TrackEngineCallback trackEngineCallback = this.trackEngine;
        if (trackEngineCallback != null) {
            trackEngineCallback.loadBegin();
        }
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return stylusInterface.load(str, str2, str3, loadListener);
        }
        return null;
    }

    public void lockScale(boolean z) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.lockScale(z);
        }
    }

    public boolean menuListIsEmpty() {
        return ToolkitModel.Companion.getExtraMenuList().isEmpty();
    }

    public void moveBy(float f, float f2) {
        StringBuilder sbQ = com.coui.appcompat.card.c.q("PaintView moveBy， dx: ", f, ", dy: ", f2, ", displayHeight: ");
        sbQ.append(getDisplayHeight());
        sbQ.append(", canvasHeight: ");
        sbQ.append(getCanvasHeight());
        Log.d(TAG, sbQ.toString());
        if (getDisplayHeight() - f2 > getCanvasHeight()) {
            Log.e(TAG, "error cannot move by");
        }
        if (f == 0.0f && f2 == 0.0f) {
            Log.d(TAG, "no need to move");
            return;
        }
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.moveBy(f, f2);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG, "onAttachedToWindow");
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.onConfigurationChanged(configuration);
        }
    }

    public void onCreate(int i, boolean z, boolean z2, FragmentManager fragmentManager) {
        androidx.appsearch.app.e.v("Life Cycle, PaintView onCreate page: ", i, TAG);
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.initFragmentManager(fragmentManager, false);
            stylusInterface.setVectorDraw(z && this.mEnableVectorDraw);
            StylusInterface.DefaultImpls.initRender$default(stylusInterface, false, 1, null);
            stylusInterface.setDrawablePages(i, z2);
            if (this.mViewType == 0 && this.mEnableOverlay) {
                Log.d(TAG, "enable overlay");
                stylusInterface.enableOverlay();
            }
        }
    }

    public void onDestroy() {
        this.isDestroy = true;
        if (this.isSaving) {
            Log.d(TAG, "onDestroy saving cannot destroy");
            return;
        }
        Log.d(TAG, "Life Cycle, PaintView onDestroy, " + hashCode());
        if (this.mEmergency && this.mDataPath != null && this.mIsEnableSaveData) {
            Log.d(TAG, "trigger emergency save");
            StylusInterface stylusInterface = this.mStylusImpl;
            if (stylusInterface != null) {
                String str = this.mImagePath;
                String str2 = this.mDataPath;
                Intrinsics.checkNotNull(str2);
                String str3 = this.mMixturePath;
                Intrinsics.checkNotNull(str3);
                StylusInterface.DefaultImpls.save$default(stylusInterface, str, str2, str3, 1, Long.MAX_VALUE, false, false, null, false, false, 928, null);
            }
        }
        Toolkit toolkit = this.mToolkit;
        if (toolkit != null) {
            toolkit.setPaintView(null);
        }
        StylusInterface stylusInterface2 = this.mStylusImpl;
        if (stylusInterface2 != null) {
            stylusInterface2.exit();
        }
        PaintFileUtil.Companion.destroy();
        ActionCallbackManager.INSTANCE.clear();
        this.canvasRendRectManager.reset();
        this.trackEngine = null;
        this.mStylusManager = null;
        this.mLatencyReduceUtils = null;
        getViewTreeObserver().removeOnGlobalLayoutListener(getMGlobalLayoutListener());
        unregisterStylusConnectState();
        this.mContext = null;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.editBgDrawingCallback = null;
    }

    public final void onDownloadComplete(int i, boolean z) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.onDownloadComplete(i, z);
        }
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent motionEvent) {
        Intrinsics.checkNotNull(motionEvent);
        return updateGestureExclusionArea(motionEvent);
    }

    public void onPause() {
        StylusInterface stylusInterface;
        this.mInvalid = true;
        Toolkit toolkit = this.mToolkit;
        if (toolkit != null) {
            toolkit.onPause$paint_intermediate_release();
        }
        if (getAutoPausePostFrameCallback() && (stylusInterface = this.mStylusImpl) != null) {
            stylusInterface.setPostFrameCallbackEnabled(false);
        }
        Log.d(TAG, "Life Cycle, PaintView onPause");
    }

    public void onResume() {
        StylusInterface stylusInterface;
        StylusInterface stylusInterface2;
        Log.d(TAG, "Life Cycle, PaintView onResume");
        if (getAutoPausePostFrameCallback() && (stylusInterface2 = this.mStylusImpl) != null) {
            stylusInterface2.setPostFrameCallbackEnabled(true);
        }
        Log.d(TAG, "Life Cycle, PaintView onResume  去掉 mToolkitPopupWindow?.show()");
        if (this.mViewType == 1 && this.mInvalid && (stylusInterface = this.mStylusImpl) != null) {
            stylusInterface.invalidate();
        }
        Toolkit toolkit = this.mToolkit;
        if (toolkit != null) {
            toolkit.onResume$paint_intermediate_release();
        }
    }

    public void onStart() {
        PaintTextureView paintTextureView;
        androidx.appsearch.app.e.y(new StringBuilder("Life Cycle, PaintView onStart, mViewType: "), this.mViewType, TAG);
        int i = this.mViewType;
        if (i == 0) {
            PaintSurfaceView paintSurfaceView = this.mSurfaceView;
            if (paintSurfaceView != null) {
                paintSurfaceView.activateSurface();
            }
        } else if (i == 1 && (paintTextureView = this.mTextureView) != null) {
            paintTextureView.activateSurface();
        }
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.onStart();
        }
        this.mInvalid = false;
        if (this.mToolkit == null) {
            ToolkitPopupWindow toolkitPopupWindow = this.mToolkitPopupWindow;
            this.mToolkit = toolkitPopupWindow != null ? toolkitPopupWindow.getToolkit() : null;
            initUseFinger();
        }
    }

    public void onStop() {
        Log.d(TAG, "Life Cycle, PaintView onStop");
        Toolkit toolkit = this.mToolkit;
        if (toolkit != null) {
            toolkit.popupWindowDismiss();
        }
        PaintSurfaceView paintSurfaceView = this.mSurfaceView;
        if (paintSurfaceView != null) {
            paintSurfaceView.destroySurface();
        }
        PaintTextureView paintTextureView = this.mTextureView;
        if (paintTextureView != null) {
            paintTextureView.destroySurface();
        }
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.onStop();
        }
    }

    @Override // com.oplusos.vfxsdk.doodleengine.toolkit.internal.IPESettingManager.OnStylusConnectionChangeListener
    public void onStylusConnectionChange() {
        Toolkit toolkit = this.mToolkit;
        if (toolkit != null) {
            if (toolkit != null) {
                toolkit.penListUpdate$paint_intermediate_release();
                return;
            }
            return;
        }
        ToolkitModel.Companion companion = ToolkitModel.Companion;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        if (!companion.needShowExtraMenu(context)) {
            setStylus(false);
            soundSwitch(false);
        } else {
            Context context2 = getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "context");
            setStylus(!companion.getUseFingerStatus(context2));
        }
    }

    @Override // android.view.View
    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(MotionEvent event) {
        LatencyReduceUtils latencyReduceUtils;
        Intrinsics.checkNotNullParameter(event, "event");
        boolean z = event.getToolType(0) == 1;
        if (this.mPreviewStatus || (!this.mFingerTouchEventEnabled && z)) {
            return false;
        }
        if (event.getActionMasked() == 0) {
            requestUnbufferedDispatch(event);
        }
        if (this.mIsFloatWindow && !this.mFingerTouchEventEnabled && this.mScrolling) {
            rollEnd();
        }
        int actionMasked = event.getActionMasked();
        int pointerCount = event.getPointerCount();
        LatencyReduceUtils latencyReduceUtils2 = this.mLatencyReduceUtils;
        if (latencyReduceUtils2 != null) {
            latencyReduceUtils2.start(actionMasked);
        }
        if (pointerCount < 2 && actionMasked == 0 && (latencyReduceUtils = this.mLatencyReduceUtils) != null) {
            latencyReduceUtils.requestVrrVsyncOffset();
        }
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.touchEvent(event);
        }
        if (this.mVibrationTypeChanged && pointerCount < 2 && (actionMasked == 0 || actionMasked == 3)) {
            StylusManager stylusManager = this.mStylusManager;
            if (stylusManager != null) {
                stylusManager.setVibrationType(this.mVibrationType);
            }
            this.mVibrationTypeChanged = false;
        }
        StylusManager stylusManager2 = this.mStylusManager;
        if (stylusManager2 != null) {
            stylusManager2.addTouchEventEvent(event);
        }
        return true;
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        StylusManager stylusManager = this.mStylusManager;
        if (stylusManager != null) {
            stylusManager.windowFocusChange(z);
        }
    }

    public void pasteInLocation(float f, float f2) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setPastePoint(f, f2);
        }
        Toolkit toolkit = this.mToolkit;
        if (toolkit != null) {
            toolkit.changeLassoView();
        }
        setLassoOperation(LassoOperation.Paste);
    }

    public void preLoadSetScale(float f, PointF center, RectF rectF) {
        Intrinsics.checkNotNullParameter(center, "center");
        Intrinsics.checkNotNullParameter(rectF, "rectF");
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.preLoadSetScale(f, center, rectF);
        }
    }

    public void previewHandwriting(Paint paint, int i, int i2, l<? super Bitmap, Unit> lVar) {
        Intrinsics.checkNotNullParameter(paint, "paint");
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.previewHandwriting(paint, i, i2, lVar);
        }
    }

    public void readCanvasData() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.readCanvasData();
        }
    }

    public void redo() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.redo();
        }
    }

    public void removeListener(PaintViewListener paintViewListener) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.removeListener(paintViewListener);
        }
    }

    public void removeLoadListener(LoadListener loadListener) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.removeLoadListener(loadListener);
        }
    }

    public void removeSaveListener() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setSaveListener(null);
        }
    }

    public void removeScrollListener() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setScrollListener(null);
        }
    }

    public void removeZoomListener() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setZoomListener(null);
        }
    }

    public void replaceSelectBitmap(String newId, String newPath, UpdateImageCallback updateImageCallback) {
        Intrinsics.checkNotNullParameter(newId, "newId");
        Intrinsics.checkNotNullParameter(newPath, "newPath");
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.replaceSelectedBitmap(newId, newPath, updateImageCallback);
        }
    }

    public void reset(boolean z) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.reset(z);
        }
    }

    public void resetData() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.resetData();
        }
    }

    public final void resetScale() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.resetScale();
        }
    }

    public void rollEnd() {
        Log.d(TAG, "PaintView rollEnd");
        if (this.mScrolling) {
            StylusInterface stylusInterface = this.mStylusImpl;
            if (stylusInterface != null) {
                stylusInterface.rollEnd();
            }
            if (this.mViewType == 0 && this.mEnableOverlay && !this.mIsFloatWindow) {
                surfaceActive(false);
            }
            this.mScrolling = false;
        }
    }

    public void rollStart() {
        rollStart(false);
    }

    public void rolling(float f, boolean z) {
        PaintTextureView paintTextureView;
        Log.d(TAG, "PaintView rolling, dy:" + f + ", extendCanvas: " + z + ", dispalyHeight: " + getDisplayHeight() + ", canvasHeight: " + getCanvasHeight());
        if (!this.mScrolling) {
            Log.e(TAG, "error cannot rolling, not start");
            return;
        }
        if (getDisplayHeight() + f > getCanvasHeight()) {
            Log.e(TAG, "error cannot rolling, need extend");
        }
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            StylusInterface.DefaultImpls.rolling$default(stylusInterface, f, z, false, 4, null);
        }
        if (!this.mEnableOverlay || (paintTextureView = this.mTextureView) == null) {
            return;
        }
        paintTextureView.overlaySync();
    }

    public void rollto(float f) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.rollto(f);
        }
    }

    public FileCode save(String str, String dataPath, int i, String mixturePath, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(dataPath, "dataPath");
        Intrinsics.checkNotNullParameter(mixturePath, "mixturePath");
        if (!this.mIsEnableSaveData) {
            Log.d(TAG, "data is loading busy,can not save at now");
            return FileCode.Busy;
        }
        this.isSaving = true;
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return StylusInterface.DefaultImpls.save$default(stylusInterface, str, dataPath, mixturePath, i, Long.MAX_VALUE, false, false, null, z, z2, 160, null);
        }
        return null;
    }

    public void saveDrawings(String str, l<? super SaveDrawingResult, Unit> lVar) {
        saveDrawings(str, 0L, lVar);
    }

    public void savePreview(String str, l<? super ActionResult, Unit> lVar) {
        savePreview(str, BodyPartID.bodyIdMax, lVar);
    }

    public void saveRelativeCanvasInfo(int i, int i2, Rect initRect, long j) {
        Intrinsics.checkNotNullParameter(initRect, "initRect");
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.saveRelativeCanvasInfo(i, i2, initRect, j);
        }
    }

    public final void saveSelectObjToBitmap(final l<? super Bitmap, Unit> call) {
        Intrinsics.checkNotNullParameter(call, "call");
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setCaptureSize(getMeasuredWidth(), getMeasuredHeight());
        }
        Toolkit.Companion.setSaveSelectState(true);
        StylusInterface stylusInterface2 = this.mStylusImpl;
        if (stylusInterface2 != null) {
            stylusInterface2.saveSelectObjToBitmap(new l<Bitmap, Unit>() { // from class: com.oplusos.vfxsdk.doodleengine.PaintView.saveSelectObjToBitmap.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.l
                public /* bridge */ /* synthetic */ Unit invoke(Bitmap bitmap) {
                    invoke2(bitmap);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Bitmap bitmap) {
                    call.invoke(bitmap);
                    Toolkit.Companion.setSaveSelectState(false);
                }
            });
        }
    }

    public void scrollFromGesture(int i) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.scrollFromGesture(i, true);
        }
    }

    public void scrollFromGestureEnd() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.scrollFromGestureEnd();
        }
    }

    public final void setAdsorptionOffset(float f) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setAdsorptionOffset(f);
        }
    }

    public void setAutoPausePostFrameCallback(boolean z) {
        this.autoPausePostFrameCallback = z;
    }

    public void setBackground(Bitmap bitmap) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setBackground(bitmap);
        }
    }

    public void setBrushParams(int i, float f, float f2) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setBrushParams(i, f, f2);
        }
    }

    public final void setCanvasExtendMax(int i) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setCanvasExtendMax(i);
        }
    }

    public void setCanvasScaleRange(float f, float f2, float f3) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setCanvasScaleRange(f, f2, f3);
        }
    }

    public void setDarkTheme(boolean z) {
        this.mIsDarkTheme = z;
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setDarkTheme(z);
        }
        Toolkit toolkit = this.mToolkit;
        if (toolkit != null) {
            toolkit.setDarkTheme(z);
        }
    }

    public void setEntityListener(EntityListener entityListener) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setEntityListener(entityListener);
        }
    }

    public void setEraser(Paint.EraserType eraserType, float f) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setEraser(eraserType, f);
        }
        StylusManager stylusManager = this.mStylusManager;
        if (stylusManager != null) {
            stylusManager.setPencilEvent(false, 0.0f);
        }
        this.mVibrationType = VibrationType.ERASE;
        StylusInterface stylusInterface2 = this.mStylusImpl;
        if (stylusInterface2 == null || !stylusInterface2.isTouching()) {
            StylusManager stylusManager2 = this.mStylusManager;
            if (stylusManager2 != null) {
                stylusManager2.setVibrationType(this.mVibrationType);
            }
        } else {
            this.mVibrationTypeChanged = true;
        }
        if (eraserType == Paint.EraserType.POINT) {
            TrackEngineCallback trackEngineCallback = this.trackEngine;
            if (trackEngineCallback != null) {
                trackEngineCallback.trackEraserInfo(com.oplusos.vfxsdk.doodleengine.toolkit.internal.EraserType.PointEraser.ordinal());
                return;
            }
            return;
        }
        TrackEngineCallback trackEngineCallback2 = this.trackEngine;
        if (trackEngineCallback2 != null) {
            trackEngineCallback2.trackEraserInfo(com.oplusos.vfxsdk.doodleengine.toolkit.internal.EraserType.LineEraser.ordinal());
        }
    }

    public void setExtraMenuOperation(MenuItemData op) {
        Intrinsics.checkNotNullParameter(op, "op");
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setSelectorExtraMenu(op);
        }
    }

    public void setFingerTouchListener(final FingerTouchListener fingerTouchListener) {
        if (fingerTouchListener == null) {
            StylusInterface stylusInterface = this.mStylusImpl;
            if (stylusInterface != null) {
                stylusInterface.setFingerTouchListener(null);
                return;
            }
            return;
        }
        FingerTouchListener fingerTouchListener2 = new FingerTouchListener() { // from class: com.oplusos.vfxsdk.doodleengine.PaintView$setFingerTouchListener$finalListener$1
            @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.FingerTouchListener
            public void onMultiFingerTap(int i, List<? extends PointF> touchPoints) {
                Intrinsics.checkNotNullParameter(touchPoints, "touchPoints");
                Log.d("PAINT:PaintView", "onMultiFingerTap: " + i + " finger");
                if (i == 1) {
                    PointF pointF = (PointF) kotlin.collections.q.c2(0, touchPoints);
                    if (pointF != null) {
                        PaintView paintView = this.this$0;
                        StylusInterface stylusInterface2 = paintView.mStylusImpl;
                        if (stylusInterface2 == null || !stylusInterface2.isFingerTouchValidElement(pointF.x, pointF.y)) {
                            StylusInterface stylusInterface3 = paintView.mStylusImpl;
                            if (stylusInterface3 != null) {
                                stylusInterface3.insertEmptyTextBoxAt(pointF.x, pointF.y);
                            }
                            Log.d("PAINT:PaintView", "onMultiFingerTap: " + i + " finger, insertEmptyTextBoxAt");
                        } else {
                            Toolkit toolkit = paintView.mToolkit;
                            if (toolkit != null) {
                                toolkit.changeLassoView();
                            }
                            StylusInterface stylusInterface4 = paintView.mStylusImpl;
                            if (stylusInterface4 != null) {
                                stylusInterface4.cancelLastDrawnContent(pointF.x, pointF.y);
                            }
                            StylusInterface stylusInterface5 = paintView.mStylusImpl;
                            if (stylusInterface5 != null) {
                                stylusInterface5.doPointSelect(pointF.x, pointF.y);
                            }
                            Log.d("PAINT:PaintView", "onMultiFingerTap: " + i + " finger, doPointSelect");
                        }
                    }
                } else if (i != 2) {
                    if (i == 3 && this.this$0.getRedoStatus()) {
                        Log.d("PAINT:PaintView", "onMultiFingerTap: three finger: do redo");
                        this.this$0.redo();
                    }
                } else if (this.this$0.getUndoStatus()) {
                    Log.d("PAINT:PaintView", "onMultiFingerTap: two finger: do undo");
                    this.this$0.undo();
                }
                FingerTouchListener fingerTouchListener3 = fingerTouchListener;
                if (fingerTouchListener3 != null) {
                    fingerTouchListener3.onMultiFingerTap(i, touchPoints);
                }
            }

            @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.FingerTouchListener
            public void onSingleFingerLongPress(float f, float f2) {
                Log.d("PAINT:PaintView", "onSingleFingerLongPress");
                StylusInterface stylusInterface2 = this.this$0.mStylusImpl;
                if (stylusInterface2 != null) {
                    stylusInterface2.cancelLastDrawnContent(f, f2);
                }
                FingerTouchListener fingerTouchListener3 = fingerTouchListener;
                if (fingerTouchListener3 != null) {
                    fingerTouchListener3.onSingleFingerLongPress(f, f2);
                }
            }
        };
        StylusInterface stylusInterface2 = this.mStylusImpl;
        if (stylusInterface2 != null) {
            stylusInterface2.setFingerTouchListener(fingerTouchListener2);
        }
    }

    public void setFingerZoom(boolean z) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setFingerZoom(z);
        }
    }

    public final void setInQuickScreen(boolean z) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setInQuickScreen(z);
        }
    }

    public void setLaserPen(Paint paint) {
        Intrinsics.checkNotNullParameter(paint, "paint");
        StylusManager stylusManager = this.mStylusManager;
        if (stylusManager != null) {
            stylusManager.setPencilEvent(false, 0.0f);
        }
        this.mVibrationType = VibrationType.NONE;
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface == null || !stylusInterface.isTouching()) {
            StylusManager stylusManager2 = this.mStylusManager;
            if (stylusManager2 != null) {
                stylusManager2.setVibrationType(this.mVibrationType);
            }
        } else {
            this.mVibrationTypeChanged = true;
        }
        StylusInterface stylusInterface2 = this.mStylusImpl;
        if (stylusInterface2 != null) {
            stylusInterface2.setLaserPen(paint);
        }
    }

    public void setLassoOperation(LassoOperation lassoOperation) {
        StylusInterface stylusInterface;
        if (lassoOperation == LassoOperation.Save && (stylusInterface = this.mStylusImpl) != null) {
            stylusInterface.setCaptureSize(getMeasuredWidth(), getMeasuredHeight());
        }
        StylusInterface stylusInterface2 = this.mStylusImpl;
        if (stylusInterface2 != null) {
            stylusInterface2.setSelectorOperation(lassoOperation);
        }
    }

    public final void setMFontType(FontType fontType) {
        this.mFontType = fontType;
    }

    public void setMaxScale(float f) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setMaxScale(f);
        }
    }

    public void setMinScale(float f) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setMinScale(f);
        }
    }

    public final void setOnPaintEditBgDrawCallback(PaintEditBgView.OnPaintEditBgDrawCallback onPaintEditBgDrawCallback) {
        this.editBgDrawingCallback = onPaintEditBgDrawCallback;
        PaintEditBgView paintEditBgView = this.mEditBgView;
        if (paintEditBgView != null) {
            paintEditBgView.setOnPaintEditBgDrawCallback(onPaintEditBgDrawCallback);
        }
    }

    public final void setOnPaperClickListener(a<Unit> aVar) {
        Toolkit toolkit$paint_intermediate_release = getToolkit$paint_intermediate_release();
        if (toolkit$paint_intermediate_release != null) {
            toolkit$paint_intermediate_release.setOnPaperClickListener(aVar);
        }
    }

    public void setPaint(Paint paint) {
        Intrinsics.checkNotNullParameter(paint, "paint");
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setPaint(paint);
        }
        TrackEngineCallback trackEngineCallback = this.trackEngine;
        if (trackEngineCallback != null) {
            trackEngineCallback.trackPaintInfo(paint.getMType());
        }
        StylusManager stylusManager = this.mStylusManager;
        if (stylusManager != null) {
            stylusManager.setPencilEvent(paint.getMType() == Paint.Type.PENCIL, paint.getMSize());
        }
        int i = WhenMappings.$EnumSwitchMapping$0[paint.getMType().ordinal()];
        this.mVibrationType = i != 1 ? i != 2 ? i != 3 ? VibrationType.NONE : VibrationType.PEN : VibrationType.BALL : VibrationType.PENCIL;
        StylusInterface stylusInterface2 = this.mStylusImpl;
        if (stylusInterface2 != null && stylusInterface2.isTouching()) {
            this.mVibrationTypeChanged = true;
            Log.d(TAG, "setPaint during touch action");
        } else {
            StylusManager stylusManager2 = this.mStylusManager;
            if (stylusManager2 != null) {
                stylusManager2.setVibrationType(this.mVibrationType);
            }
        }
    }

    public void setPaintDrawLineStatus(boolean z) {
        com.coui.appcompat.card.c.A("setPaintDrawLineStatus: ", z, TAG);
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setPaintDrawLineStatus(z);
        }
    }

    public void setPlacedAtBottomStatus(boolean z) {
        com.coui.appcompat.card.c.A("setPlacedAtBottomStatus: ", z, TAG);
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setPlacedAtBottomStatus(z);
        }
    }

    public void setPreviewStatus(boolean z) {
        Log.d(TAG, "Preview Status: old: " + this.mPreviewStatus + ", new: " + z);
        this.mPreviewStatus = z;
        if (this.mEnableOverlay) {
            if (z) {
                this.mViewType = 1;
                PaintTextureView paintTextureView = this.mTextureView;
                if (paintTextureView != null) {
                    paintTextureView.enableTextureSurface(true);
                }
                PaintTextureView paintTextureView2 = this.mTextureView;
                if (paintTextureView2 != null) {
                    paintTextureView2.activateSurface();
                }
                PaintSurfaceView paintSurfaceView = this.mSurfaceView;
                if (paintSurfaceView != null) {
                    paintSurfaceView.deactivateSurface();
                }
                PaintSurfaceView paintSurfaceView2 = this.mSurfaceView;
                if (paintSurfaceView2 != null) {
                    paintSurfaceView2.enableSurface(false);
                }
            } else {
                this.mViewType = 0;
                if (!this.mIsFloatWindow) {
                    PaintTextureView paintTextureView3 = this.mTextureView;
                    if (paintTextureView3 != null) {
                        paintTextureView3.deactivateSurface();
                    }
                    PaintTextureView paintTextureView4 = this.mTextureView;
                    if (paintTextureView4 != null) {
                        paintTextureView4.enableTextureSurface(false);
                    }
                }
                PaintSurfaceView paintSurfaceView3 = this.mSurfaceView;
                if (paintSurfaceView3 != null) {
                    paintSurfaceView3.enableSurface(true);
                }
                PaintSurfaceView paintSurfaceView4 = this.mSurfaceView;
                if (paintSurfaceView4 != null) {
                    paintSurfaceView4.activateSurface();
                }
            }
            PaintTextureView paintTextureView5 = this.mTextureView;
            if (paintTextureView5 != null) {
                paintTextureView5.invalidate();
            }
            PaintSurfaceView paintSurfaceView5 = this.mSurfaceView;
            if (paintSurfaceView5 != null) {
                paintSurfaceView5.invalidate();
            }
        }
    }

    public void setRendRect(RectF rectF) {
        Intrinsics.checkNotNullParameter(rectF, "rectF");
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setRendRect(rectF);
        }
        this.canvasRendRectManager.configure(rectF);
    }

    public void setRendRectOnly(RectF rectF) {
        Intrinsics.checkNotNullParameter(rectF, "rectF");
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setRendRectOnly(rectF);
        }
        this.canvasRendRectManager.configure(rectF);
    }

    public void setRendRectWitScale(RectF rectF, float f, float f2, float f3, boolean z) {
        Intrinsics.checkNotNullParameter(rectF, "rectF");
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setRendRectWitScale(rectF, f, f2, f3, z);
        }
        this.canvasRendRectManager.configure(rectF);
        PaintEditBgView paintEditBgView = this.mEditBgView;
        if (paintEditBgView != null) {
            paintEditBgView.doChangeRendRect(rectF);
            paintEditBgView.invalidate();
        }
    }

    public void setSaveListener(final SaveListener saveListener) {
        if (saveListener == null) {
            StylusInterface stylusInterface = this.mStylusImpl;
            if (stylusInterface != null) {
                stylusInterface.setSaveListener(null);
            }
            Log.e(TAG, "setSaveListener null");
            return;
        }
        StylusInterface stylusInterface2 = this.mStylusImpl;
        if (stylusInterface2 != null) {
            stylusInterface2.setSaveListener(new SaveListener() { // from class: com.oplusos.vfxsdk.doodleengine.PaintView.setSaveListener.1
                @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.SaveListener
                public void onDataTruncation() {
                    PaintView.this.isSaving = false;
                    if (PaintView.this.isDestroy) {
                        PaintView.this.onDestroy();
                    }
                    SaveListener saveListener2 = saveListener;
                    if (saveListener2 != null) {
                        saveListener2.onDataTruncation();
                    }
                }

                @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.SaveListener
                public void onDrawingsSaved(FileCode fileCode, ArrayList<String> arrayList) {
                    SaveListener saveListener2 = saveListener;
                    if (saveListener2 != null) {
                        saveListener2.onDrawingsSaved(fileCode, arrayList);
                    }
                    if (PaintView.this.isDestroy) {
                        PaintView.this.onDestroy();
                    }
                    PaintView.this.isSaving = false;
                }

                @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.SaveListener
                public void onPreviewSaved(FileCode fileCode, String str) {
                    PaintView.this.isSaving = false;
                    if (PaintView.this.isDestroy) {
                        PaintView.this.onDestroy();
                    }
                    SaveListener saveListener2 = saveListener;
                    if (saveListener2 != null) {
                        saveListener2.onPreviewSaved(fileCode, str);
                    }
                }

                @Override // com.oplusos.vfxsdk.doodleengine.stylusapi.SaveListener
                public void onSaved(ActionResult result) {
                    Intrinsics.checkNotNullParameter(result, "result");
                    PaintView.this.isSaving = false;
                    if (PaintView.this.isDestroy) {
                        PaintView.this.onDestroy();
                    }
                    SaveListener saveListener2 = saveListener;
                    if (saveListener2 != null) {
                        saveListener2.onSaved(result);
                    }
                }
            });
        }
    }

    public void setScrollListener(ScrollListener scrollListener) {
        this.externalScrollListener = scrollListener;
    }

    public void setSelector() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setSelector();
        }
        StylusManager stylusManager = this.mStylusManager;
        if (stylusManager != null) {
            stylusManager.setPencilEvent(false, 0.0f);
        }
        this.mVibrationType = VibrationType.NONE;
        StylusInterface stylusInterface2 = this.mStylusImpl;
        if (stylusInterface2 != null && stylusInterface2.isTouching()) {
            this.mVibrationTypeChanged = true;
            return;
        }
        StylusManager stylusManager2 = this.mStylusManager;
        if (stylusManager2 != null) {
            stylusManager2.setVibrationType(this.mVibrationType);
        }
    }

    public void setSelectorColor(int i) {
        float f = ((16711680 & i) >> 16) / 255.0f;
        float f2 = ((65280 & i) >> 8) / 255.0f;
        float f3 = (i & 255) / 255.0f;
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setSelectorColor(f, f2, f3);
        }
    }

    public void setSlideSpeedWhenSelecting(float f) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setSlideSpeedWhenSelecting(f);
        }
    }

    /* renamed from: setSmoothFilterInfo-qim9Vi0, reason: not valid java name */
    public void m87setSmoothFilterInfoqim9Vi0(int i, float f) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.mo94setSmoothFilterInfoqim9Vi0(i, f);
        }
    }

    public final void setSplitHeight(int i, int i2) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setSplitHeight(i, i2);
        }
    }

    public void setStylus(boolean z) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setStylus(z);
        }
    }

    public void setTextInputMode(boolean z) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.enableInputTextMode(z);
        }
    }

    public void setTextureViewRotate(float f) {
        PaintTextureView paintTextureView = this.mTextureView;
        if (paintTextureView == null) {
            return;
        }
        paintTextureView.setRotation(f);
    }

    public final void setToolkit$paint_intermediate_release(Toolkit toolkit) {
        this.mToolkit = toolkit;
        if (toolkit != null) {
            toolkit.setDarkTheme(this.mIsDarkTheme);
        }
        Toolkit toolkit2 = this.mToolkit;
        if (toolkit2 != null) {
            toolkit2.enableColorExtraction$paint_intermediate_release(this.mEnableColorExtractor);
        }
        Toolkit toolkit3 = this.mToolkit;
        if (toolkit3 != null) {
            toolkit3.enableToolkitAutoFoldMenuItem$paint_intermediate_release(this.mIsFloatWindow);
        }
        Toolkit toolkit4 = this.mToolkit;
        if (toolkit4 != null) {
            toolkit4.enableStylusMenuItem$paint_intermediate_release(!this.mIsFloatWindow);
        }
        Toolkit toolkit5 = this.mToolkit;
        if (toolkit5 != null) {
            toolkit5.setGlobalMarkMode$paint_intermediate_release(this.mIsFloatWindow);
        }
        if (toolkit != null) {
            Log.d(TAG, "unregisterStylusConnectState with toolkit attached");
            unregisterStylusConnectState();
        } else {
            Log.d(TAG, "registerStylusConnectState with toolkit detached");
            registerStylusConnectState();
        }
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setOnConvertTextReplace(new a<Unit>() { // from class: com.oplusos.vfxsdk.doodleengine.PaintView$setToolkit$1
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
                    Log.d("PAINT:PaintView", "setToolkit changeTextBoxView");
                    Toolkit toolkit6 = this.this$0.mToolkit;
                    if (toolkit6 != null) {
                        toolkit6.changeTextBoxView();
                    }
                }
            });
        }
    }

    public final void setToolkitPopupWindow$paint_intermediate_release(ToolkitPopupWindow popupWindow) {
        Intrinsics.checkNotNullParameter(popupWindow, "popupWindow");
        this.mToolkitPopupWindow = popupWindow;
    }

    public void setTopBlank(float f) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setTopBlank(f);
        }
    }

    public void setTouchBorderValues(long j, int i, int i2, int i3, int i4) {
        if (j > 0) {
            RendRectTopExtendUtil.Companion.setTime_Delay(j);
        }
        if (i > 0) {
            RendRectTopExtendUtil.Companion.setExtendUnit(i);
        }
        if (i2 > 0) {
            RendRectTopExtendUtil.Companion.setShrinkSpace(i2);
        }
        if (i3 > 0) {
            RendRectTopExtendUtil.Companion.setMinHeight(i3);
        }
        if (i4 <= 0 || i4 <= i3) {
            return;
        }
        RendRectTopExtendUtil.Companion.setMaxHeight(i4);
    }

    public void setUnlimitedScale(boolean z) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setUnlimitedScale(z);
        }
    }

    public final void setWebScrollValues(int i, int i2) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setWebScrollValues(i, i2);
        }
    }

    public final void setWriteBeautify(boolean z) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.enableWriteBeautify(z);
        }
    }

    public final void setWriteCalculator(boolean z) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.enableCalculator(z);
        }
        StatisticsCallSummary.INSTANCE.onHandwritingCalculator(z, false);
    }

    public final void setWriteEffect(Paint.Type penType, EffectType effectType, float f) {
        Intrinsics.checkNotNullParameter(penType, "penType");
        Intrinsics.checkNotNullParameter(effectType, "effectType");
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setWriteEffect(penType, effectType, f);
        }
    }

    public void setZoomListener(ZoomingListener zoomingListener) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setZoomListener(zoomingListener);
        }
    }

    public void showCanvasBounds(boolean z) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.showCanvasBounds(z);
        }
    }

    public void showDoodleBlockBounds(boolean z) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.showDoodleBlockBounds(z);
        }
    }

    public void soundSwitch(boolean z) {
        StylusManager stylusManager = this.mStylusManager;
        if (stylusManager != null) {
            stylusManager.soundSwitchEvent$paint_intermediate_release(z);
        }
        TrackEngineCallback trackEngineCallback = this.trackEngine;
        if (trackEngineCallback != null) {
            trackEngineCallback.soundSwitch(z);
        }
    }

    public final void splitPaintFile(String str, List<String> imgList, List<String> paintList, p<? super String, ? super Boolean, Unit> pVar, long j) {
        Intrinsics.checkNotNullParameter(imgList, "imgList");
        Intrinsics.checkNotNullParameter(paintList, "paintList");
        if (str == null || str.length() == 0 || imgList.isEmpty() || imgList.size() != paintList.size()) {
            if (pVar != null) {
                pVar.invoke(str, Boolean.FALSE);
                return;
            }
            return;
        }
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            if (stylusInterface != null) {
                stylusInterface.splitPaintFile(str, imgList, paintList, pVar, j);
            }
        } else {
            Log.d(TAG, "splitPaintFile impl == null");
            if (pVar != null) {
                pVar.invoke(str, Boolean.FALSE);
            }
        }
    }

    public float startExpandShrinkCanvas() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return stylusInterface.startExpandShrinkCanvas();
        }
        return 0.0f;
    }

    public final void startFunctionVibration() {
        StylusManager stylusManager = this.mStylusManager;
        if (stylusManager != null) {
            stylusManager.startFunctionVibration();
        }
    }

    public void startTopExtendCanvas(boolean z, final a<Unit> aVar) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.startTopExtendCanvas(z, new l<RectF, Unit>() { // from class: com.oplusos.vfxsdk.doodleengine.PaintView.startTopExtendCanvas.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.l
                public /* bridge */ /* synthetic */ Unit invoke(RectF rectF) {
                    invoke2(rectF);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(RectF it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    a<Unit> aVar2 = aVar;
                    if (aVar2 != null) {
                        aVar2.invoke();
                    }
                    PaintEditBgView paintEditBgView = this.mEditBgView;
                    if (paintEditBgView != null) {
                        paintEditBgView.doChangeRendRect(it);
                        paintEditBgView.invalidate();
                    }
                }
            });
        }
    }

    public void stopExtendShrinkCanvas() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.stopExtendShrinkCanvas();
        }
    }

    public void stopTopExtendCanvas() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.stopTopExtendCanvas();
        }
    }

    public final void trackColorPickerUseTime(int i) {
        TrackEngineCallback trackEngineCallback = this.trackEngine;
        if (trackEngineCallback != null) {
            trackEngineCallback.trackColorPickerInfo(i);
        }
    }

    public final void triggerDownload(int i) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.triggerDownload(i);
        }
    }

    public final void triggerSaveLast() {
        this.canvasRendRectManager.setSaveLastClip(true);
    }

    public void undo() {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.undo();
        }
    }

    public final void upLoadDataInRealTime() {
        TrackEngineCallback trackEngineCallback = this.trackEngine;
        if (trackEngineCallback != null) {
            trackEngineCallback.uploadDataInRealTime();
        }
    }

    public void updateCanvas(float[] fArr) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            Intrinsics.checkNotNull(fArr);
            stylusInterface.updateDisplayRect(fArr);
        }
    }

    public void updateContent(float f, float f2, float f3) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.updateContent(f, f2, f3);
        }
    }

    public void updateEntity(String id, String str, Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(id, "id");
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.updateEntity(id, str, bitmap);
        }
    }

    public final void updateNaiNbi(Context context, FontDataList fontList, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(fontList, "fontList");
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.updateNaiNbi(context, fontList, z);
        }
    }

    public void updateRendRect(RectF rectF) {
        Intrinsics.checkNotNullParameter(rectF, "rectF");
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.updateRendRect(rectF);
        }
        this.canvasRendRectManager.configure(rectF);
        PaintEditBgView paintEditBgView = this.mEditBgView;
        if (paintEditBgView != null) {
            paintEditBgView.doChangeRendRect(rectF);
            paintEditBgView.invalidate();
        }
    }

    public void zOrderTopChange(boolean z) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.zOrderTopChange(z);
        }
    }

    public void zoomEnd() {
        if (this.mZooming) {
            this.mZooming = false;
            StylusInterface stylusInterface = this.mStylusImpl;
            if (stylusInterface != null) {
                stylusInterface.refreshScreen();
            }
        }
    }

    public void zoomStart() {
        if (this.mZooming) {
            return;
        }
        this.mZooming = true;
    }

    public void zooming() {
        if (this.mZooming) {
            StylusInterface stylusInterface = this.mStylusImpl;
            if (stylusInterface != null) {
                stylusInterface.refreshScreen();
            }
            if (this.mIsScreenShot) {
                PaintTextureView paintTextureView = this.mTextureView;
                if (paintTextureView != null) {
                    Intrinsics.checkNotNull(paintTextureView);
                    paintTextureView.invalidate();
                    return;
                }
                PaintSurfaceView paintSurfaceView = this.mSurfaceView;
                if (paintSurfaceView != null) {
                    Intrinsics.checkNotNull(paintSurfaceView);
                    paintSurfaceView.invalidate();
                }
            }
        }
    }

    public static /* synthetic */ void onCreate$default(PaintView paintView, int i, boolean z, boolean z2, FragmentManager fragmentManager, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: onCreate");
        }
        if ((i2 & 8) != 0) {
            fragmentManager = null;
        }
        paintView.onCreate(i, z, z2, fragmentManager);
    }

    public static /* synthetic */ FileCode save$default(PaintView paintView, String str, String str2, String str3, int i, long j, boolean z, boolean z2, String str4, boolean z3, int i2, Object obj) {
        if (obj == null) {
            return paintView.save((i2 & 1) != 0 ? null : str, str2, str3, i, (i2 & 16) != 0 ? Long.MAX_VALUE : j, (i2 & 32) != 0 ? false : z, z2, (i2 & 128) != 0 ? "" : str4, (i2 & 256) != 0 ? false : z3);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: save");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void saveDrawings$default(PaintView paintView, String str, long j, l lVar, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: saveDrawings");
        }
        if ((i & 4) != 0) {
            lVar = null;
        }
        paintView.saveDrawings(str, j, lVar);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void savePreview$default(PaintView paintView, String str, long j, l lVar, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: savePreview");
        }
        if ((i & 4) != 0) {
            lVar = null;
        }
        paintView.savePreview(str, j, lVar);
    }

    public void changeSelectedColor(int i, float f) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.changeSelectedColor(i, f);
        }
    }

    public void insertText(String content, RectF rectF, TextOperationCallback callback) {
        Intrinsics.checkNotNullParameter(content, "content");
        Intrinsics.checkNotNullParameter(rectF, "rectF");
        Intrinsics.checkNotNullParameter(callback, "callback");
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.insertTextToPaintView(content, rectF, callback);
        }
    }

    public void rollStart(boolean z) {
        Log.d(TAG, "PaintView rollStart, mViewType: " + this.mViewType + ", width: " + getWidth() + ", height: " + getHeight());
        if (this.mScrolling) {
            return;
        }
        this.mScrolling = true;
        if (this.mViewType == 0 && this.mEnableOverlay && !this.mIsFloatWindow) {
            surfaceActive(true);
        }
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.rollStart(z);
        }
    }

    public void saveDrawings(String str, long j, l<? super SaveDrawingResult, Unit> lVar) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.saveDrawings(str, j, lVar);
        }
    }

    public void savePreview(String str, long j, l<? super ActionResult, Unit> lVar) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.savePreview(str, j, lVar);
        }
    }

    public void setSelectorColor(float f, float f2, float f3) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setSelectorColor(f, f3, f2);
        }
    }

    public static /* synthetic */ void onCreate$default(PaintView paintView, int i, int i2, boolean z, int i3, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: onCreate");
        }
        if ((i3 & 4) != 0) {
            z = false;
        }
        paintView.onCreate(i, i2, z);
    }

    public final void insertImage(String id, String path, PointF center, InsertImageCallback callback) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(center, "center");
        Intrinsics.checkNotNullParameter(callback, "callback");
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.insertImage(id, path, center, callback);
        }
        Toolkit toolkit = this.mToolkit;
        if (toolkit != null) {
            toolkit.changeLassoView();
        }
    }

    public void extendCanvas(float f, boolean z) {
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.extendCanvas(f, z, new l<RectF, Unit>() { // from class: com.oplusos.vfxsdk.doodleengine.PaintView.extendCanvas.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.l
                public /* bridge */ /* synthetic */ Unit invoke(RectF rectF) {
                    invoke2(rectF);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(RectF it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    PaintEditBgView paintEditBgView = PaintView.this.mEditBgView;
                    if (paintEditBgView != null) {
                        paintEditBgView.doChangeRendRect(it);
                        paintEditBgView.invalidate();
                    }
                }
            });
        }
    }

    public FileCode save(String str, String dataPath, String mixturePath, int i, long j, boolean z, boolean z2, String taskId, boolean z3) {
        Intrinsics.checkNotNullParameter(dataPath, "dataPath");
        Intrinsics.checkNotNullParameter(mixturePath, "mixturePath");
        Intrinsics.checkNotNullParameter(taskId, "taskId");
        if (!this.mIsEnableSaveData) {
            Log.d(TAG, "data is loading busy,can not save at now");
            return FileCode.Busy;
        }
        this.isSaving = true;
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            return StylusInterface.DefaultImpls.save$default(stylusInterface, str, dataPath, mixturePath, i, j, z, z2, taskId, z3, false, 512, null);
        }
        return null;
    }

    public void rolling(float f, boolean z, boolean z2) {
        PaintTextureView paintTextureView;
        Log.d(TAG, "PaintView rolling, dy:" + f + ", extendCanvas: " + z + ", dispalyHeight: " + getDisplayHeight() + ", canvasHeight: " + getCanvasHeight() + ", needSync: " + z2);
        if (!this.mScrolling) {
            Log.e(TAG, "error cannot rolling, not start");
            return;
        }
        if (getDisplayHeight() + f > getCanvasHeight()) {
            Log.e(TAG, "error cannot rolling, need extend");
        }
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.rolling(f, z, z2);
        }
        if (!this.mEnableOverlay || (paintTextureView = this.mTextureView) == null) {
            return;
        }
        paintTextureView.overlaySync();
    }

    public void onCreate(int i, boolean z, FragmentManager fragmentManager) {
        onCreate(i, z, false, fragmentManager);
    }

    public void onCreate(int i, int i2, boolean z) {
        Log.d(TAG, "Life Cycle, PaintView onCreate");
        this.mIsScreenShot = true;
        StylusInterface stylusInterface = this.mStylusImpl;
        if (stylusInterface != null) {
            stylusInterface.setImageSize(i, i2);
            stylusInterface.initRender(z);
        }
        TrackEngineCallback trackEngineCallback = this.trackEngine;
        if (trackEngineCallback != null) {
            trackEngineCallback.trackOverlayInfo();
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PaintView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(attrs, "attrs");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PaintView(Context context) {
        this(context, (AttributeSet) null, 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PaintView(Context context, boolean z, int i) {
        this(context, (AttributeSet) null, 0);
        Intrinsics.checkNotNullParameter(context, "context");
        this.mEnableOverlay = z;
        this.mViewType = i;
    }
}
