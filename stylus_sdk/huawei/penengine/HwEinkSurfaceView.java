package com.huawei.stylus.penengine.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import com.huawei.penkit.impl.eink.HwEinkStylusImpl;
import com.huawei.stylus.penengine.eink.listener.IHwEinkListener;
import com.huawei.stylus.penengine.eink.model.StrokeInfo;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes9.dex */
public class HwEinkSurfaceView extends FrameLayout {
    public static final String TAG = HwEinkSurfaceView.class.getSimpleName();
    public static final int TOOL_TYPE_BOTH = 3;
    public static final int TOOL_TYPE_FINGER = 1;
    public static final int TOOL_TYPE_STYLUS = 2;
    public Context mContext;
    public HwEinkStylusImpl mHwEinkStylusImpl;
    public SurfaceView mHwSurfaceView;
    public boolean mIsDispatch;

    public HwEinkSurfaceView(Context context) {
        this(context, null);
    }

    public boolean canPaste() {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            return hwEinkStylusImpl.canPaste();
        }
        return false;
    }

    public boolean canRedo() {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            return hwEinkStylusImpl.canRedo();
        }
        return false;
    }

    public boolean canUndo() {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            return hwEinkStylusImpl.canUndo();
        }
        return false;
    }

    public void cancelSelection() {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            hwEinkStylusImpl.cancelSelection();
        }
    }

    public void clear() {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            hwEinkStylusImpl.clear();
        }
    }

    public void copySelection() {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            hwEinkStylusImpl.copySelection();
        }
    }

    public void cutSelection() {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            hwEinkStylusImpl.cutSelection();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return super.dispatchTouchEvent(motionEvent) && !this.mIsDispatch;
    }

    public void eraseArea(Path path) {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            hwEinkStylusImpl.eraseArea(path);
        }
    }

    public void eraseSelection() {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            hwEinkStylusImpl.eraseSelection();
        }
    }

    public int getActionMode() {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            return hwEinkStylusImpl.getActionMode();
        }
        return 0;
    }

    public int getEraserType() {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            return hwEinkStylusImpl.getEraserType();
        }
        return 1;
    }

    public int getEraserWidth() {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            return hwEinkStylusImpl.getEraserWidth();
        }
        return 10;
    }

    public int getPenColor() {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            return hwEinkStylusImpl.getPenColor();
        }
        return -16777216;
    }

    public int getPenType() {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            return hwEinkStylusImpl.getPenType();
        }
        return 1;
    }

    public float getPenWidth() {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            return hwEinkStylusImpl.getPenWidth();
        }
        return 10.0f;
    }

    public Path getSelectionPath() {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        return hwEinkStylusImpl != null ? hwEinkStylusImpl.getSelectionPath() : new Path();
    }

    public List<StrokeInfo> getSelectionStroke() {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        return hwEinkStylusImpl != null ? hwEinkStylusImpl.getSelectionStroke() : new ArrayList(0);
    }

    public List<StrokeInfo> getStrokeInfo() {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        return hwEinkStylusImpl != null ? hwEinkStylusImpl.getStrokeInfo() : new ArrayList(0);
    }

    public Bitmap getThumbnail() {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        return hwEinkStylusImpl != null ? hwEinkStylusImpl.getThumbnail() : Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
    }

    public boolean hasAction() {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            return hwEinkStylusImpl.hasAction();
        }
        return false;
    }

    public void hideArea(Path path) {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            hwEinkStylusImpl.hideArea(path);
        }
    }

    public boolean isEmpty() {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            return hwEinkStylusImpl.isEmpty();
        }
        return true;
    }

    public void pasteSelection(PointF pointF) {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            hwEinkStylusImpl.pasteSelection(pointF);
        }
    }

    public void redo() {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            hwEinkStylusImpl.redo();
        }
    }

    public void setActionMode(int i) {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            hwEinkStylusImpl.setActionMode(i);
        }
    }

    public void setBackground(int i) {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            hwEinkStylusImpl.setBackground(i);
        }
    }

    public boolean setBackgroundBitmap(Bitmap bitmap) {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            return hwEinkStylusImpl.setBackgroundBitmap(bitmap);
        }
        return false;
    }

    public void setClipPath(Path path) {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            hwEinkStylusImpl.setClipPath(path);
        }
    }

    public void setDispatchPoint(boolean z) {
        this.mIsDispatch = z;
    }

    public void setEraserBackgroundBitmap(Bitmap bitmap) {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            hwEinkStylusImpl.setEraserBackgroundBitmap(bitmap);
        }
    }

    public void setEraserInputToolType(int i) {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            hwEinkStylusImpl.setEraserInputToolType(i);
        }
    }

    public void setEraserShowBorder(boolean z) {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            hwEinkStylusImpl.setEraserShowBorder(z);
        }
    }

    public void setEraserType(int i) {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            hwEinkStylusImpl.setEraserType(i);
        }
    }

    public void setEraserWidth(int i) {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            hwEinkStylusImpl.setEraserWidth(i);
        }
    }

    public void setHwEinkListener(IHwEinkListener iHwEinkListener) {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            hwEinkStylusImpl.setHwEinkListener(iHwEinkListener);
        }
    }

    public void setInputToolType(int i) {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            hwEinkStylusImpl.setInputToolType(i);
        }
    }

    public void setPenColor(int i) {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            hwEinkStylusImpl.setPenColor(i);
        }
    }

    public void setPenType(int i) {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            hwEinkStylusImpl.setPenType(i);
        }
    }

    public void setPenWidth(float f) {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            hwEinkStylusImpl.setPenWidth(f);
        }
    }

    public void setSelectInputToolType(int i) {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            hwEinkStylusImpl.setSelectInputToolType(i);
        }
    }

    public void setStrokeInfo(List<StrokeInfo> list) {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            hwEinkStylusImpl.setStrokeInfo(list);
        }
    }

    public void showArea(Path path) {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            hwEinkStylusImpl.showArea(path);
        }
    }

    public void undo() {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            hwEinkStylusImpl.undo();
        }
    }

    public HwEinkSurfaceView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public HwEinkSurfaceView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsDispatch = false;
        this.mContext = context;
        this.mHwEinkStylusImpl = new HwEinkStylusImpl(this.mContext);
        this.mHwSurfaceView = this.mHwEinkStylusImpl.initView(attributeSet, i);
        addView(this.mHwSurfaceView, new FrameLayout.LayoutParams(-1, -1));
    }

    public Bitmap getThumbnail(Bitmap bitmap) {
        HwEinkStylusImpl hwEinkStylusImpl = this.mHwEinkStylusImpl;
        if (hwEinkStylusImpl != null) {
            return hwEinkStylusImpl.getThumbnail(bitmap);
        }
        return Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
    }
}
