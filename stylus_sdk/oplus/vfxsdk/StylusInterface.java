package com.oplusos.vfxsdk.doodleengine.stylusapi;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.Surface;
import android.widget.FrameLayout;
import androidx.fragment.app.FragmentManager;
import com.oplusos.vfxsdk.doodleengine.EffectType;
import com.oplusos.vfxsdk.doodleengine.FileCode;
import com.oplusos.vfxsdk.doodleengine.LassoOperation;
import com.oplusos.vfxsdk.doodleengine.MenuItemData;
import com.oplusos.vfxsdk.doodleengine.Paint;
import com.oplusos.vfxsdk.doodleengine.base.ActionResult;
import com.oplusos.vfxsdk.doodleengine.base.FontType;
import com.oplusos.vfxsdk.doodleengine.base.ImplPlatform;
import com.oplusos.vfxsdk.doodleengine.base.SaveDrawingResult;
import com.oplusos.vfxsdk.doodleengine.font.FontDataList;
import com.oplusos.vfxsdk.doodleengine.font.IDownloadListener;
import com.oplusos.vfxsdk.doodleengine.stylus.StylusManager;
import com.oplusos.vfxsdk.doodleengine.util.CanvasRendRectManager;
import com.oplusos.vfxsdk.doodleengine.view.PaintEditBgView;
import com.oplusos.vfxsdk.doodleengine.view.PaintSurfaceView;
import com.oplusos.vfxsdk.doodleengine.view.PaintTextureView;
import java.util.List;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.a;
import kotlin.jvm.functions.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.functions.q;
import org.spongycastle.asn1.cmc.BodyPartID;

/* compiled from: StylusInterface.kt */
/* loaded from: classes5.dex */
public interface StylusInterface {

    /* compiled from: StylusInterface.kt */
    public interface BitmapMenuCallback {
        void onBitmapMenuClick(MenuItemData menuItemData);
    }

    /* compiled from: StylusInterface.kt */
    public static final class DefaultImpls {
        public static /* synthetic */ void initRender$default(StylusInterface stylusInterface, boolean z, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: initRender");
            }
            if ((i & 1) != 0) {
                z = false;
            }
            stylusInterface.initRender(z);
        }

        public static /* synthetic */ void reset$default(StylusInterface stylusInterface, boolean z, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: reset");
            }
            if ((i & 1) != 0) {
                z = false;
            }
            stylusInterface.reset(z);
        }

        public static /* synthetic */ void rollStart$default(StylusInterface stylusInterface, boolean z, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: rollStart");
            }
            if ((i & 1) != 0) {
                z = false;
            }
            stylusInterface.rollStart(z);
        }

        public static /* synthetic */ void rolling$default(StylusInterface stylusInterface, float f, boolean z, boolean z2, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: rolling");
            }
            if ((i & 4) != 0) {
                z2 = false;
            }
            stylusInterface.rolling(f, z, z2);
        }

        public static /* synthetic */ FileCode save$default(StylusInterface stylusInterface, String str, String str2, String str3, int i, long j, boolean z, boolean z2, String str4, boolean z3, boolean z4, int i2, Object obj) {
            if (obj == null) {
                return stylusInterface.save(str, str2, str3, i, j, (i2 & 32) != 0 ? false : z, z2, (i2 & 128) != 0 ? "" : str4, (i2 & 256) != 0 ? false : z3, (i2 & 512) != 0 ? false : z4);
            }
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: save");
        }

        public static /* synthetic */ void savePreview$default(StylusInterface stylusInterface, String str, long j, l lVar, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: savePreview");
            }
            if ((i & 2) != 0) {
                j = BodyPartID.bodyIdMax;
            }
            stylusInterface.savePreview(str, j, lVar);
        }

        public static /* synthetic */ void splitPaintFile$default(StylusInterface stylusInterface, String str, List list, List list2, p pVar, long j, int i, Object obj) {
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
            stylusInterface.splitPaintFile(str, list, list2, pVar2, j);
        }

        public static /* synthetic */ void surfaceChanged$default(StylusInterface stylusInterface, Surface surface, int i, int i2, boolean z, int i3, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: surfaceChanged");
            }
            if ((i3 & 8) != 0) {
                z = true;
            }
            stylusInterface.surfaceChanged(surface, i, i2, z);
        }

        public static /* synthetic */ void surfaceCreated$default(StylusInterface stylusInterface, Surface surface, boolean z, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: surfaceCreated");
            }
            if ((i & 2) != 0) {
                z = true;
            }
            stylusInterface.surfaceCreated(surface, z);
        }
    }

    void addAdapterFontDownLoadListener(IDownloadListener iDownloadListener);

    void addCustomBitmapMenu(List<MenuItemData> list, BitmapMenuCallback bitmapMenuCallback);

    void addDownloadListener(IDownloadListener iDownloadListener);

    void addEntity(String str, String str2, float[] fArr, float f, float[] fArr2, EntityType entityType, Color color, Bitmap bitmap);

    void addListener(PaintViewListener paintViewListener);

    void addLoadListener(LoadListener loadListener);

    void bindRecognizeFile(String str);

    boolean calculateCompressBottom(int i);

    boolean calculateCompressTop(int i);

    boolean canAutoSave();

    boolean canShowPastePopup();

    void cancelLastDrawnContent(float f, float f2);

    void changeBeautifyFont(FontType fontType);

    void changeSelectedColor(float f, float f2, float f3, float f4);

    void changeSelectedColor(int i, float f);

    void clear();

    void clearStep();

    float converseScaleByPageScale(float f);

    void destroySurface();

    void destroySurfaceWithSurface(Surface surface);

    void doPointSelect(float f, float f2);

    void drawSizePoint(float f);

    void enableCalculator(boolean z);

    void enableInputTextMode(boolean z);

    void enableOverlay();

    void enablePreviewMode(boolean z);

    void enableRulerMode(boolean z);

    void enableSelectionBeautify();

    void enableShapeRegular(boolean z);

    void enableWriteBeautify(boolean z);

    void exit();

    void exportBitmapFromPaint(String str, String str2, int i, int i2, l<? super Bitmap, Unit> lVar);

    void extendCanvas(float f);

    void extendCanvas(float f, boolean z, l<? super RectF, Unit> lVar);

    void finishSelect();

    void finishSpannedEdit();

    void forceScroll(float f);

    int getCanvaHeight();

    Bitmap getCanvasBitmap();

    float getContentBottomInScreen();

    float getContentOffsetX();

    float getContentOffsetY();

    float getContentScale();

    Paint getCurrentPaint();

    int getDisplayHeight();

    float[][][] getDisplayStrokes();

    IDownloadListener getFontDownLoadListener();

    RectF getLassoRect();

    FileCode getLoadedStatus();

    int getMaxPaintHeight();

    float getMaxScale();

    int getMenuHeightValue();

    float getMenuPositionX();

    float getMenuPositionY();

    int getMenuRotateValue();

    int getMenuType();

    int getMenuWidthValue();

    boolean getOnlyStylus();

    long getPaintFileSize();

    int getPaintHeight();

    ImplPlatform getPlatform();

    boolean getRedoStatus();

    Bitmap getRelativeCanvasBitmap();

    RectF getRendRect();

    float getRotateIconPositionX();

    float getRotateIconPositionY();

    FileCode getSavedStatus();

    RectF getSelectedRecF();

    RectF getShowRectf();

    Pair<Integer, Boolean> getSplitCount(String str);

    long getStepChangeTime();

    float getTopBlank();

    boolean getUndoStatus();

    void handWritingToText();

    void initAuth(a<Unit> aVar);

    void initCanvas(float f, float f2, float f3);

    void initCanvas(float[] fArr);

    void initContent(float f, float f2, float f3);

    void initFragmentManager(FragmentManager fragmentManager, boolean z);

    void initRender(boolean z);

    void initStylusManager(StylusManager stylusManager);

    void initSurface(int i, FrameLayout frameLayout, boolean z, boolean z2, q<? super PaintSurfaceView, ? super PaintTextureView, ? super PaintEditBgView, Unit> qVar);

    void initTrack(TrackEngineCallback trackEngineCallback);

    void insertEmptyTextBoxAt(float f, float f2);

    void insertImage(String str, String str2, PointF pointF, InsertImageCallback insertImageCallback);

    void insertImage(String str, String str2, InsertImageCallback insertImageCallback);

    void insertTextToPaintView(String str, PointF pointF, TextOperationCallback textOperationCallback);

    void insertTextToPaintView(String str, RectF rectF, TextOperationCallback textOperationCallback);

    void invalidate();

    boolean isChanged();

    boolean isContentEmpty();

    boolean isFingerTouchValidElement(float f, float f2);

    boolean isSpanEditing();

    boolean isSpanEditingEmpty();

    boolean isTouching();

    FileCode load(String str, String str2, String str3, LoadListener loadListener);

    void lockScale(boolean z);

    void moveBy(float f, float f2);

    void onConfigurationChanged(Configuration configuration);

    void onDownloadComplete(int i, boolean z);

    void onStart();

    void onStop();

    void preLoadSetScale(float f, PointF pointF, RectF rectF);

    void previewHandwriting(Paint paint, int i, int i2, l<? super Bitmap, Unit> lVar);

    void readCanvasData();

    void redo();

    void refreshScreen();

    void removeListener(PaintViewListener paintViewListener);

    void removeLoadListener(LoadListener loadListener);

    void replaceSelectedBitmap(String str, String str2, UpdateImageCallback updateImageCallback);

    void reset(boolean z);

    void resetData();

    void resetScale();

    void rollEnd();

    void rollStart(boolean z);

    void rolling(float f, boolean z, boolean z2);

    void rollto(float f);

    FileCode save(String str, String str2, String str3, int i, long j, boolean z, boolean z2, String str4, boolean z3, boolean z4);

    void saveDrawings(String str, long j, l<? super SaveDrawingResult, Unit> lVar);

    void savePreview(String str, long j, l<? super ActionResult, Unit> lVar);

    void saveRelativeCanvasInfo(int i, int i2, Rect rect, long j);

    void saveSelectObjToBitmap(l<? super Bitmap, Unit> lVar);

    void scrollFromGesture(int i, boolean z);

    void scrollFromGestureEnd();

    void setAdsorptionOffset(float f);

    void setBackground(Bitmap bitmap);

    void setBrushParams(int i, float f, float f2);

    void setCanvasExtendMax(int i);

    void setCanvasRendRectManager(CanvasRendRectManager canvasRendRectManager);

    void setCanvasScaleRange(float f, float f2, float f3);

    void setCaptureSize(int i, int i2);

    void setDarkTheme(boolean z);

    void setDrawablePages(int i, boolean z);

    void setEntityListener(EntityListener entityListener);

    void setEraser(Paint.EraserType eraserType, float f);

    void setFingerTouchListener(FingerTouchListener fingerTouchListener);

    void setFingerZoom(boolean z);

    void setImageSize(int i, int i2);

    void setInQuickScreen(boolean z);

    void setLaserPen(Paint paint);

    void setMaxScale(float f);

    void setMinScale(float f);

    void setNeedCorrectRect(boolean z);

    void setOnConvertTextReplace(a<Unit> aVar);

    void setPaint(Paint paint);

    void setPaintDrawLineStatus(boolean z);

    void setPaintViewSize(int i, int i2);

    void setPastePoint(float f, float f2);

    void setPlacedAtBottomStatus(boolean z);

    void setPostFrameCallbackEnabled(boolean z);

    void setPreviewStatus(boolean z, boolean z2);

    void setRendRect(RectF rectF);

    void setRendRectOnly(RectF rectF);

    void setRendRectWitScale(RectF rectF, float f, float f2, float f3, boolean z);

    void setSaveListener(SaveListener saveListener);

    void setScrollListener(ScrollListener scrollListener);

    void setSelector();

    void setSelectorColor(float f, float f2, float f3);

    void setSelectorExtraMenu(MenuItemData menuItemData);

    void setSelectorOperation(LassoOperation lassoOperation);

    void setSlideSpeedWhenSelecting(float f);

    /* renamed from: setSmoothFilterInfo-qim9Vi0 */
    void mo94setSmoothFilterInfoqim9Vi0(int i, float f);

    void setSplitHeight(int i, int i2);

    void setStylus(boolean z);

    void setTopBlank(float f);

    void setUnlimitedScale(boolean z);

    void setVectorDraw(boolean z);

    void setWebScrollValues(int i, int i2);

    void setWriteEffect(Paint.Type type, EffectType effectType, float f);

    void setZoomListener(ZoomingListener zoomingListener);

    void showCanvasBounds(boolean z);

    void showDoodleBlockBounds(boolean z);

    void splitPaintFile(String str, List<String> list, List<String> list2, p<? super String, ? super Boolean, Unit> pVar, long j);

    float startExpandShrinkCanvas();

    void startTopExtendCanvas(boolean z, l<? super RectF, Unit> lVar);

    void stopExtendShrinkCanvas();

    void stopTopExtendCanvas();

    boolean supportBeautify();

    void surfaceChanged(Surface surface, int i, int i2, boolean z);

    void surfaceCreated(Surface surface, boolean z);

    void touchEvent(MotionEvent motionEvent);

    void triggerDownload(int i);

    void undo();

    void updateContent(float f, float f2, float f3);

    void updateDisplayRect(float[] fArr);

    void updateEntity(String str, String str2, Bitmap bitmap);

    void updateNaiNbi(Context context, FontDataList fontDataList, boolean z);

    void updateRendRect(RectF rectF);

    void zOrderTopChange(boolean z);
}
