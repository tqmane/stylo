package com.oplusos.vfxsdk.doodleengine;

import android.graphics.Bitmap;
import android.graphics.RectF;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.e;

/* compiled from: MenuItemData.kt */
/* loaded from: classes5.dex */
public final class MenuItemData {
    private String imagePath;
    private boolean isShowLine;
    private Bitmap menuIcon;
    private Integer menuId;
    private String menuName;
    private RectF selectRectF;

    public MenuItemData(int i, String menuName, Bitmap menuIcon, boolean z) {
        Intrinsics.checkNotNullParameter(menuName, "menuName");
        Intrinsics.checkNotNullParameter(menuIcon, "menuIcon");
        this.isShowLine = z;
        this.menuId = Integer.valueOf(i);
        this.menuName = menuName;
        this.menuIcon = menuIcon;
        this.imagePath = "";
    }

    public final String getImagePath() {
        return this.imagePath;
    }

    public final boolean getIsShowLine() {
        return this.isShowLine;
    }

    public final Bitmap getMenuIcon() {
        return this.menuIcon;
    }

    public final Integer getMenuId() {
        return this.menuId;
    }

    public final String getMenuName() {
        return this.menuName;
    }

    public final RectF getSelectRectF() {
        return this.selectRectF;
    }

    public final void setImagePath(String imagePath) {
        Intrinsics.checkNotNullParameter(imagePath, "imagePath");
        this.imagePath = imagePath;
    }

    public final void setMenuIcon(Bitmap menuIcon) {
        Intrinsics.checkNotNullParameter(menuIcon, "menuIcon");
        this.menuIcon = menuIcon;
    }

    public final void setMenuName(String menuName) {
        Intrinsics.checkNotNullParameter(menuName, "menuName");
        this.menuName = menuName;
    }

    public final void setSelectRectF(RectF rectF) {
        Intrinsics.checkNotNullParameter(rectF, "rectF");
        this.selectRectF = rectF;
    }

    public /* synthetic */ MenuItemData(int i, String str, Bitmap bitmap, boolean z, int i2, e eVar) {
        this(i, str, bitmap, (i2 & 8) != 0 ? false : z);
    }
}
