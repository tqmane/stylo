package com.oplusos.vfxsdk.doodleengine.stylusapi;

import android.graphics.RectF;
import com.oplusos.vfxsdk.doodleengine.data.ScaleData;
import kotlin.Unit;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ScrollListener.kt */
/* loaded from: classes5.dex */
public interface ScrollListener {
    void onLeaped(float f, float f2, float f3, float f4, float f5);

    void onScaleChanged(float f);

    void onScrollEnd(RectF rectF);

    void onScrollRestricted();

    void onScrollScale(RectF rectF, RectF rectF2, ScaleData scaleData, p<? super Integer, ? super Integer, Unit> pVar);

    void onScrollStart(RectF rectF);

    void touchCanvas(boolean z);

    /* compiled from: ScrollListener.kt */
    public static final class DefaultImpls {
        public static void onScrollScale(ScrollListener scrollListener, RectF rendRectF, RectF rectF, ScaleData scale, p<? super Integer, ? super Integer, Unit> maxMoveBlock) {
            Intrinsics.checkNotNullParameter(rendRectF, "rendRectF");
            Intrinsics.checkNotNullParameter(scale, "scale");
            Intrinsics.checkNotNullParameter(maxMoveBlock, "maxMoveBlock");
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ void onScrollScale$default(ScrollListener scrollListener, RectF rectF, RectF rectF2, ScaleData scaleData, p pVar, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: onScrollScale");
            }
            if ((i & 8) != 0) {
                pVar = new p<Integer, Integer, Unit>() { // from class: com.oplusos.vfxsdk.doodleengine.stylusapi.ScrollListener.onScrollScale.1
                    public final void invoke(int i2, int i3) {
                    }

                    @Override // kotlin.jvm.functions.p
                    public /* bridge */ /* synthetic */ Unit invoke(Integer num, Integer num2) {
                        invoke(num.intValue(), num2.intValue());
                        return Unit.INSTANCE;
                    }
                };
            }
            scrollListener.onScrollScale(rectF, rectF2, scaleData, pVar);
        }

        public static void onScaleChanged(ScrollListener scrollListener, float f) {
        }

        public static void touchCanvas(ScrollListener scrollListener, boolean z) {
        }

        public static void onLeaped(ScrollListener scrollListener, float f, float f2, float f3, float f4, float f5) {
        }
    }
}
