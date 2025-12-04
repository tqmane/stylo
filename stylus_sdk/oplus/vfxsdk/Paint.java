package com.oplusos.vfxsdk.doodleengine;

import android.graphics.Color;
import com.oplus.note.repo.note.entity.PaintTypeEntity;
import com.oplus.note.scenecard.utils.b;
import com.oplusos.vfxsdk.doodleengine.data.ColorPickerContractKt;
import com.oplusos.vfxsdk.doodleengine.data.ThicknessContractKt;
import com.oplusos.vfxsdk.doodleengine.sunia.utils.Constant;
import java.util.List;
import kotlin.Triple;
import kotlin.collections.k;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.e;
import kotlin.ranges.j;

/* compiled from: Paint.kt */
/* loaded from: classes5.dex */
public class Paint {
    public static final Companion Companion = new Companion(null);
    public static final String M_ALPHA = "alpha";
    public static final String M_BLUE = "blue";
    public static final String M_ERASER_TYPE = "eraser_type";
    public static final String M_GEOMETRY_TYPE = "geometry_type";
    public static final String M_GREEN = "green";
    public static final String M_LENGTH = "length";
    public static final String M_RED = "red";
    public static final String M_SENSON = "senson";
    public static final String M_SIZE = "size";
    public static final String M_TYPE = "type";
    private float mAlpha;
    private float mBlue;
    private EraserType mEraserType;
    private GeometryType mGeometryType;
    private float mGreen;
    private float mRed;
    private float mSenson;
    private float mSize;
    private float mTipLength;
    private Type mType;

    /* compiled from: Paint.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(e eVar) {
            this();
        }
    }

    /* compiled from: Paint.kt */
    public enum EraserType {
        POINT,
        LINE;

        public static final Companion Companion = new Companion(null);

        /* compiled from: Paint.kt */
        public static final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(e eVar) {
                this();
            }

            public final EraserType getType(int i) {
                return EraserType.values()[i];
            }
        }
    }

    /* compiled from: Paint.kt */
    public enum GeometryType {
        LINE,
        RECTANGLE,
        ROUND,
        ARROWEND,
        ARROWSTART,
        ARROWBOTH,
        ARROWFULL,
        POLYLINE,
        PARALLELOGRAM,
        QUADRILATERAL,
        TRIANGLE,
        RIGHT_ANGLE_TRIANGLE,
        RHOMBUS,
        EQUILATERALTRIANGLE,
        ISOSCELES_TRIANGLE,
        PENTAGRAM,
        PENTAGON,
        PARABOLA,
        PARABOLASTART,
        PARABOLAEND,
        HEART;

        public static final Companion Companion = new Companion(null);

        /* compiled from: Paint.kt */
        public static final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(e eVar) {
                this();
            }

            public final List<GeometryType> getSuniaTypes() {
                return b.q0(GeometryType.LINE, GeometryType.ARROWEND, GeometryType.ARROWBOTH, GeometryType.RIGHT_ANGLE_TRIANGLE, GeometryType.ISOSCELES_TRIANGLE, GeometryType.ROUND, GeometryType.RECTANGLE, GeometryType.PARALLELOGRAM, GeometryType.PENTAGON, GeometryType.PENTAGRAM);
            }

            public final GeometryType getType(int i) {
                GeometryType[] geometryTypeArrValues = GeometryType.values();
                return (i < 0 || i > k.R1(geometryTypeArrValues)) ? GeometryType.LINE : geometryTypeArrValues[i];
            }
        }
    }

    /* compiled from: Paint.kt */
    public enum Stroke {
        TYPE1,
        TYPE2,
        TYPE3,
        TYPE4,
        TYPE5
    }

    /* compiled from: Paint.kt */
    public enum Type {
        PENCIL("pencil"),
        MARK("mark"),
        BALLPEN("ballpen"),
        PEN(Constant.PEN),
        CRAYON("crayon"),
        BRUSH("brush"),
        GEOMETRY("geometry"),
        TEXTBOX(PaintTypeEntity.TYPE_TEXTBOX),
        LASERPOINTER("laser_pointer"),
        LASSO(Constant.LASSO),
        ERASER("eraser"),
        AILASSO("ai_lasso"),
        RULER(Constant.RULER),
        SCREENSHOT("screen_shot"),
        SETTING("setting"),
        NONE("none");

        public static final Companion Companion = new Companion(null);
        private final String typeName;

        /* compiled from: Paint.kt */
        public static final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(e eVar) {
                this();
            }

            public final Type getValue(int i) {
                if (i == Type.LASERPOINTER.ordinal() || i == Type.RULER.ordinal()) {
                    return Type.values()[i];
                }
                int iOrdinal = Type.ERASER.ordinal();
                if (i < 0) {
                    i = 0;
                }
                if (iOrdinal > i) {
                    iOrdinal = i;
                }
                return Type.values()[iOrdinal];
            }
        }

        Type(String str) {
            this.typeName = str;
        }

        public final String getTypeName() {
            return this.typeName;
        }
    }

    public Paint() {
        this.mType = Type.PENCIL;
        this.mGeometryType = GeometryType.LINE;
        this.mEraserType = EraserType.LINE;
        this.mSenson = 0.5f;
        this.mTipLength = 1.0f;
    }

    public final int argbColor() {
        return Color.argb(this.mAlpha, this.mRed, this.mGreen, this.mBlue);
    }

    public final Paint copy() {
        Paint paint = new Paint(this.mType);
        paint.mGeometryType = this.mGeometryType;
        paint.mEraserType = this.mEraserType;
        paint.mSize = this.mSize;
        paint.mRed = this.mRed;
        paint.mGreen = this.mGreen;
        paint.mBlue = this.mBlue;
        paint.mAlpha = this.mAlpha;
        paint.mSenson = this.mSenson;
        paint.mTipLength = this.mTipLength;
        return paint;
    }

    public final float getMAlpha() {
        return this.mAlpha;
    }

    public final float getMBlue() {
        return this.mBlue;
    }

    public final EraserType getMEraserType() {
        return this.mEraserType;
    }

    public final GeometryType getMGeometryType() {
        return this.mGeometryType;
    }

    public final float getMGreen() {
        return this.mGreen;
    }

    public final float getMRed() {
        return this.mRed;
    }

    public final float getMSenson() {
        return this.mSenson;
    }

    public final float getMSize() {
        return this.mSize;
    }

    public final float getMTipLength() {
        return this.mTipLength;
    }

    public final Type getMType() {
        return this.mType;
    }

    public final boolean isSame(Paint paint) {
        Intrinsics.checkNotNullParameter(paint, "paint");
        return this.mType == paint.mType && this.mGeometryType == paint.mGeometryType && this.mEraserType == paint.mEraserType && this.mSize == paint.mSize && this.mRed == paint.mRed && this.mGreen == paint.mGreen && this.mBlue == paint.mBlue && this.mAlpha == paint.mAlpha && this.mSenson == paint.mSenson && this.mTipLength == paint.mTipLength;
    }

    public final int rgbColor() {
        return Color.rgb(this.mRed, this.mGreen, this.mBlue);
    }

    public final void setAlpha(float f) {
        this.mAlpha = f;
    }

    public final void setBlue(float f) {
        this.mBlue = f;
    }

    public final void setColor(int i) {
        Color colorValueOf = Color.valueOf(i);
        Intrinsics.checkNotNullExpressionValue(colorValueOf, "valueOf(color)");
        this.mAlpha = colorValueOf.alpha();
        this.mRed = colorValueOf.red();
        this.mGreen = colorValueOf.green();
        this.mBlue = colorValueOf.blue();
    }

    public final void setEraserType(int i) {
        this.mEraserType = EraserType.Companion.getType(i);
    }

    public final void setGeometryType(int i) {
        this.mGeometryType = GeometryType.Companion.getType(i);
    }

    public final void setGreen(float f) {
        this.mGreen = f;
    }

    public final void setMAlpha(float f) {
        this.mAlpha = f;
    }

    public final void setMBlue(float f) {
        this.mBlue = f;
    }

    public final void setMEraserType(EraserType eraserType) {
        Intrinsics.checkNotNullParameter(eraserType, "<set-?>");
        this.mEraserType = eraserType;
    }

    public final void setMGeometryType(GeometryType geometryType) {
        Intrinsics.checkNotNullParameter(geometryType, "<set-?>");
        this.mGeometryType = geometryType;
    }

    public final void setMGreen(float f) {
        this.mGreen = f;
    }

    public final void setMRed(float f) {
        this.mRed = f;
    }

    public final void setMSenson(float f) {
        this.mSenson = f;
    }

    public final void setMSize(float f) {
        this.mSize = f;
    }

    public final void setMTipLength(float f) {
        this.mTipLength = f;
    }

    public final void setMType(Type type) {
        Intrinsics.checkNotNullParameter(type, "<set-?>");
        this.mType = type;
    }

    public final void setRed(float f) {
        this.mRed = f;
    }

    public final void setSenson(float f) {
        this.mSenson = f;
    }

    public final void setSize(float f) {
        this.mSize = f;
    }

    public final void setTipLength(float f) {
        this.mTipLength = f;
    }

    public final void setType(int i) {
        this.mType = Type.Companion.getValue(i);
    }

    public final void setValue(Paint paint) {
        Intrinsics.checkNotNullParameter(paint, "paint");
        this.mType = paint.mType;
        this.mEraserType = paint.mEraserType;
        this.mGeometryType = paint.mGeometryType;
        this.mSenson = paint.mSenson;
        this.mTipLength = paint.mTipLength;
        setValue(paint.mSize, paint.mRed, paint.mGreen, paint.mBlue, paint.mAlpha);
    }

    public String toString() {
        return "type: " + this.mType + ", geometry type: " + this.mGeometryType + ", eraser type: " + this.mEraserType + ", size: " + this.mSize + ", color: " + this.mRed + ' ' + this.mGreen + ' ' + this.mBlue + ' ' + this.mAlpha + ", senson: " + this.mSenson + ", tipLength: " + this.mTipLength;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public Paint(Type type, boolean z) {
        this();
        Intrinsics.checkNotNullParameter(type, "type");
        this.mType = type;
        this.mSize = ThicknessContractKt.getDefaultSizeFromType(type);
        Triple<Integer, Integer, Integer> defaultColor = ColorPickerContractKt.getDefaultColor(type, z);
        Integer numD = defaultColor != null ? defaultColor.d() : null;
        if (numD != null) {
            setColor(numD.intValue());
        } else {
            setColor(-16777216);
        }
        if (type == Type.PEN) {
            this.mTipLength = 0.75f;
        }
    }

    public final void setValue(float f, float f2, float f3, float f4, float f5) {
        this.mSize = ThicknessContractKt.coerceSizeWithType(this.mType, f);
        this.mRed = j.A1(1.0f, j.z1(f2, 0.0f));
        this.mGreen = j.A1(1.0f, j.z1(f3, 0.0f));
        this.mBlue = j.A1(1.0f, j.z1(f4, 0.0f));
        this.mAlpha = j.A1(1.0f, j.z1(f5, 0.0f));
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public Paint(Type type, int i, float f, float f2, float f3, float f4) {
        this();
        Intrinsics.checkNotNullParameter(type, "type");
        this.mType = type;
        this.mSize = ThicknessContractKt.getDefaultSizeFromType(type);
        this.mRed = f;
        this.mGreen = f2;
        this.mBlue = f3;
        this.mAlpha = f4;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public Paint(Type type, int i, float f, float f2, float f3, float f4, GeometryType geometryType) {
        this();
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(geometryType, "geometryType");
        this.mType = type;
        this.mGeometryType = geometryType;
        this.mSize = ThicknessContractKt.getDefaultSizeFromType(type);
        this.mRed = f;
        this.mGreen = f2;
        this.mBlue = f3;
        this.mAlpha = f4;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public Paint(Type type, EraserType eraserType, int i) {
        this();
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(eraserType, "eraserType");
        this.mType = type;
        this.mSize = ThicknessContractKt.getDefaultSizeFromType(type);
        this.mEraserType = eraserType;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public Paint(Type type) {
        this();
        Intrinsics.checkNotNullParameter(type, "type");
        this.mType = type;
        this.mSize = ThicknessContractKt.getDefaultSizeFromType(type);
    }
}
