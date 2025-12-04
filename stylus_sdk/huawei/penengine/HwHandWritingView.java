package com.huawei.stylus.penengine.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.huawei.penkit.impl.kitpaint.HwHandWritingImpl;
import com.huawei.stylus.penengine.HwPenEngineManager;
import java.io.File;
import java.io.IOException;

/* loaded from: classes9.dex */
public class HwHandWritingView extends FrameLayout {
    public static final String TAG = HwHandWritingView.class.getSimpleName();
    public IHwHandWritingView mHandWritingView;
    public View mPaintView;
    public View mToolBox;

    public HwHandWritingView(Context context) {
        this(context, null);
    }

    public boolean canRedo() {
        IHwHandWritingView iHwHandWritingView = this.mHandWritingView;
        if (iHwHandWritingView != null) {
            return iHwHandWritingView.canRedo();
        }
        return false;
    }

    public boolean canUndo() {
        IHwHandWritingView iHwHandWritingView = this.mHandWritingView;
        if (iHwHandWritingView != null) {
            return iHwHandWritingView.canUndo();
        }
        return false;
    }

    public void clear() {
        this.mHandWritingView.clear();
    }

    public boolean clearStepChange() {
        IHwHandWritingView iHwHandWritingView;
        if (!HwPenEngineManager.isApiLevelCompatible(2) || (iHwHandWritingView = this.mHandWritingView) == null) {
            return false;
        }
        iHwHandWritingView.clearStepChange();
        return true;
    }

    public boolean enableCanvasScale(boolean z) {
        IHwHandWritingView iHwHandWritingView;
        String str = "enableCanvasScale :" + z;
        if (HwPenEngineManager.isApiLevelCompatible(2) && (iHwHandWritingView = this.mHandWritingView) != null) {
            return iHwHandWritingView.enableCanvasScale(z);
        }
        return false;
    }

    public final boolean enableDrag(boolean z) {
        IHwHandWritingView iHwHandWritingView;
        return HwPenEngineManager.isApiLevelCompatible(2) && this.mToolBox != null && (iHwHandWritingView = this.mHandWritingView) != null && iHwHandWritingView.enableDrag(this, z);
    }

    public boolean enableInstantShape(boolean z) {
        IHwHandWritingView iHwHandWritingView;
        String str = "enableInstantShape :" + z;
        if (HwPenEngineManager.isApiLevelCompatible(2) && (iHwHandWritingView = this.mHandWritingView) != null) {
            return iHwHandWritingView.enableInstantShape(z);
        }
        return false;
    }

    public boolean enableInstantTable(boolean z) {
        IHwHandWritingView iHwHandWritingView;
        String str = "enableInstantTable :" + z;
        if (HwPenEngineManager.isApiLevelCompatible(2) && (iHwHandWritingView = this.mHandWritingView) != null) {
            return iHwHandWritingView.enableInstantTable(z);
        }
        return false;
    }

    public boolean enablePencilTilt(boolean z) {
        IHwHandWritingView iHwHandWritingView;
        String str = "enablePencilTilt :" + z;
        if (HwPenEngineManager.isApiLevelCompatible(2) && (iHwHandWritingView = this.mHandWritingView) != null) {
            return iHwHandWritingView.enablePencilTilt(z);
        }
        return false;
    }

    public Rect getContentRange() {
        IHwHandWritingView iHwHandWritingView = this.mHandWritingView;
        return (iHwHandWritingView == null || iHwHandWritingView.getContentRange() == null) ? new Rect() : this.mHandWritingView.getContentRange();
    }

    public void getThumbnail(Bitmap bitmap, RectF rectF) {
        this.mHandWritingView.getThumbnail(bitmap, rectF);
    }

    public Rect getVisibleRange() {
        IHwHandWritingView iHwHandWritingView = this.mHandWritingView;
        return iHwHandWritingView != null ? iHwHandWritingView.getVisibleRange() : new Rect();
    }

    public boolean isChanged() {
        return this.mHandWritingView.isChanged();
    }

    public boolean isEmpty() {
        return this.mHandWritingView.isEmpty();
    }

    public void load() {
        this.mHandWritingView.load();
    }

    public void redo() {
        IHwHandWritingView iHwHandWritingView = this.mHandWritingView;
        if (iHwHandWritingView != null) {
            iHwHandWritingView.redo();
        }
    }

    public boolean resetChangedState() {
        IHwHandWritingView iHwHandWritingView;
        if (!HwPenEngineManager.isApiLevelCompatible(2) || (iHwHandWritingView = this.mHandWritingView) == null) {
            return false;
        }
        iHwHandWritingView.resetChangedState();
        return true;
    }

    public boolean save(String str) {
        return this.mHandWritingView.save(str);
    }

    public void setMaxPages(int i) {
        this.mHandWritingView.setMaxPages(i);
    }

    public boolean setPaintScrollable(boolean z) {
        IHwHandWritingView iHwHandWritingView;
        if (!HwPenEngineManager.isApiLevelCompatible(2) || (iHwHandWritingView = this.mHandWritingView) == null) {
            return false;
        }
        iHwHandWritingView.setPaintScrollable(z);
        return true;
    }

    public void setPaintViewListener(IPaintViewListener iPaintViewListener) {
        IHwHandWritingView iHwHandWritingView = this.mHandWritingView;
        if (iHwHandWritingView != null) {
            iHwHandWritingView.setPaintViewListener(iPaintViewListener);
        }
    }

    public void setSupportFinger(boolean z) {
        this.mHandWritingView.setSupportFinger(z);
    }

    public void setToolType(int i, int i2, int i3) {
        this.mHandWritingView.setToolType(i, i2, i3);
    }

    public boolean setToolViewVisible(int i, boolean z) {
        IHwHandWritingView iHwHandWritingView;
        return HwPenEngineManager.isApiLevelCompatible(2) && (iHwHandWritingView = this.mHandWritingView) != null && iHwHandWritingView.setToolViewVisible(i, z);
    }

    public void undo() {
        IHwHandWritingView iHwHandWritingView = this.mHandWritingView;
        if (iHwHandWritingView != null) {
            iHwHandWritingView.undo();
        }
    }

    public HwHandWritingView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public boolean load(String str) throws IOException {
        File file = new File(str);
        if (file.exists()) {
            try {
                return this.mHandWritingView.load(file.getCanonicalPath());
            } catch (IOException unused) {
            }
        }
        return false;
    }

    public HwHandWritingView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (this.mHandWritingView == null) {
            this.mHandWritingView = new HwHandWritingImpl(context);
            this.mPaintView = this.mHandWritingView.initView(attributeSet, i);
            this.mToolBox = this.mHandWritingView.initToolBox(attributeSet, i);
            ViewGroup.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-2, -2);
            layoutParams2.gravity = 81;
            addView(this.mPaintView, layoutParams);
            addView(this.mToolBox, layoutParams2);
            this.mHandWritingView.init();
        }
    }
}
