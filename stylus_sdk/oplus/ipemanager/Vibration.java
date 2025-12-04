package com.oplus.ipemanager.sdk;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes3.dex */
public final class Vibration {

    /* renamed from: a, reason: collision with root package name */
    public static final Vibration f3913a;
    public static final Vibration b;
    public static final Vibration c;
    public static final Vibration d;
    public static final Vibration e;
    public static final /* synthetic */ Vibration[] f;

    static {
        Vibration vibration = new Vibration("PENCIL", 0);
        f3913a = vibration;
        Vibration vibration2 = new Vibration("ERASER", 1);
        b = vibration2;
        Vibration vibration3 = new Vibration("BALLPEN", 2);
        c = vibration3;
        Vibration vibration4 = new Vibration("PEN", 3);
        d = vibration4;
        Vibration vibration5 = new Vibration("FUNCTION_VIBRATION", 4);
        e = vibration5;
        f = new Vibration[]{vibration, vibration2, vibration3, vibration4, vibration5};
    }

    public Vibration() {
        throw null;
    }

    public static Vibration valueOf(String str) {
        return (Vibration) Enum.valueOf(Vibration.class, str);
    }

    public static Vibration[] values() {
        return (Vibration[]) f.clone();
    }
}
