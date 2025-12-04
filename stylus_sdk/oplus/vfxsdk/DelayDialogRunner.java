package com.oplusos.vfxsdk.doodleengine;

import android.util.Log;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.s;
import com.coui.appcompat.panel.COUIBottomSheetDialogFragment;
import com.oplusos.vfxsdk.doodleengine.view.TextConversionPanel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.d;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.coroutines.jvm.internal.c;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.e;
import kotlin.sequences.k;
import kotlinx.coroutines.f1;
import kotlinx.coroutines.h0;
import kotlinx.coroutines.n0;
import kotlinx.coroutines.o1;
import kotlinx.coroutines.scheduling.b;
import kotlinx.coroutines.z;

/* compiled from: DelayDialogRunner.kt */
/* loaded from: classes5.dex */
public final class DelayDialogRunner {
    public static final Companion Companion = new Companion(null);
    public static final long DEFAULT_AT_LEAST = 1000;
    public static final long DEFAULT_DELAY = 1000;
    private static final String DIALOG_FRAGMENT_TAG = "bottom sheet";
    private static final String TAG = "DelayDialogRunner";
    private boolean alreadyDismiss;
    private final long atLeast;
    private final long delay;
    private final FragmentManager fragmentManager;
    private boolean isShowing;
    private f1 jobOfDelayShow;
    private COUIBottomSheetDialogFragment mBottomSheetDialogFragment;
    private final s owner;
    private TextConversionPanel panelFragment;
    private long startTimeOfRealShow;

    /* compiled from: DelayDialogRunner.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(e eVar) {
            this();
        }
    }

    /* compiled from: DelayDialogRunner.kt */
    @c(c = "com.oplusos.vfxsdk.doodleengine.DelayDialogRunner$dismiss$1", f = "DelayDialogRunner.kt", l = {70, 78, 80, 85}, m = "invokeSuspend")
    /* renamed from: com.oplusos.vfxsdk.doodleengine.DelayDialogRunner$dismiss$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements p<z, d<? super Unit>, Object> {
        final /* synthetic */ String $content;
        final /* synthetic */ boolean $immediately;
        int label;
        final /* synthetic */ DelayDialogRunner this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(boolean z, DelayDialogRunner delayDialogRunner, String str, d<? super AnonymousClass1> dVar) {
            super(2, dVar);
            this.$immediately = z;
            this.this$0 = delayDialogRunner;
            this.$content = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final d<Unit> create(Object obj, d<?> dVar) {
            return new AnonymousClass1(this.$immediately, this.this$0, this.$content, dVar);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.f6153a;
            int i = this.label;
            if (i != 0) {
                if (i == 1) {
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                if (i != 2 && i != 3 && i != 4) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
            if (this.$immediately) {
                Log.d(DelayDialogRunner.TAG, "dismiss immediately.");
                f1 f1Var = this.this$0.jobOfDelayShow;
                if (f1Var != null) {
                    f1Var.a(null);
                }
                if (!this.this$0.alreadyDismiss) {
                    DelayDialogRunner delayDialogRunner = this.this$0;
                    String str = this.$content;
                    this.label = 1;
                    if (delayDialogRunner.dismissLoading(str, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
                return Unit.INSTANCE;
            }
            if (this.this$0.atLeast - (System.currentTimeMillis() - this.this$0.startTimeOfRealShow) <= 0) {
                f1 f1Var2 = this.this$0.jobOfDelayShow;
                if (f1Var2 != null) {
                    f1Var2.a(null);
                }
                if (this.this$0.isShowing) {
                    DelayDialogRunner delayDialogRunner2 = this.this$0;
                    String str2 = this.$content;
                    this.label = 3;
                    if (delayDialogRunner2.dismissLoading(str2, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    DelayDialogRunner delayDialogRunner3 = this.this$0;
                    String str3 = this.$content;
                    this.label = 2;
                    if (delayDialogRunner3.showPanel(str3, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
            } else {
                f1 f1Var3 = this.this$0.jobOfDelayShow;
                if (f1Var3 != null) {
                    f1Var3.a(null);
                }
                if (!this.this$0.alreadyDismiss) {
                    DelayDialogRunner delayDialogRunner4 = this.this$0;
                    String str4 = this.$content;
                    this.label = 4;
                    if (delayDialogRunner4.dismissLoading(str4, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
            }
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.p
        public final Object invoke(z zVar, d<? super Unit> dVar) {
            return ((AnonymousClass1) create(zVar, dVar)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* compiled from: DelayDialogRunner.kt */
    @c(c = "com.oplusos.vfxsdk.doodleengine.DelayDialogRunner$dismissLoading$2", f = "DelayDialogRunner.kt", l = {}, m = "invokeSuspend")
    /* renamed from: com.oplusos.vfxsdk.doodleengine.DelayDialogRunner$dismissLoading$2, reason: invalid class name */
    public static final class AnonymousClass2 extends SuspendLambda implements p<z, d<? super Unit>, Object> {
        final /* synthetic */ String $content;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(String str, d<? super AnonymousClass2> dVar) {
            super(2, dVar);
            this.$content = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final d<Unit> create(Object obj, d<?> dVar) {
            return DelayDialogRunner.this.new AnonymousClass2(this.$content, dVar);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.f6153a;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Log.d(DelayDialogRunner.TAG, "dismissLoading");
            TextConversionPanel textConversionPanel = DelayDialogRunner.this.panelFragment;
            if (textConversionPanel == null) {
                return null;
            }
            textConversionPanel.setLoading(false, this.$content);
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.p
        public final Object invoke(z zVar, d<? super Unit> dVar) {
            return ((AnonymousClass2) create(zVar, dVar)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* compiled from: DelayDialogRunner.kt */
    @c(c = "com.oplusos.vfxsdk.doodleengine.DelayDialogRunner$show$1", f = "DelayDialogRunner.kt", l = {50, 51}, m = "invokeSuspend")
    /* renamed from: com.oplusos.vfxsdk.doodleengine.DelayDialogRunner$show$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10201 extends SuspendLambda implements p<z, d<? super Unit>, Object> {
        int label;

        /* compiled from: DelayDialogRunner.kt */
        @c(c = "com.oplusos.vfxsdk.doodleengine.DelayDialogRunner$show$1$1", f = "DelayDialogRunner.kt", l = {}, m = "invokeSuspend")
        /* renamed from: com.oplusos.vfxsdk.doodleengine.DelayDialogRunner$show$1$1, reason: invalid class name and collision with other inner class name */
        public static final class C03421 extends SuspendLambda implements p<z, d<? super Unit>, Object> {
            int label;
            final /* synthetic */ DelayDialogRunner this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03421(DelayDialogRunner delayDialogRunner, d<? super C03421> dVar) {
                super(2, dVar);
                this.this$0 = delayDialogRunner;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final d<Unit> create(Object obj, d<?> dVar) {
                return new C03421(this.this$0, dVar);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                COUIBottomSheetDialogFragment cOUIBottomSheetDialogFragment;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.f6153a;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                Log.d(DelayDialogRunner.TAG, "show");
                this.this$0.startTimeOfRealShow = System.currentTimeMillis();
                FragmentManager fragmentManager = this.this$0.fragmentManager;
                if (fragmentManager != null && (cOUIBottomSheetDialogFragment = this.this$0.mBottomSheetDialogFragment) != null) {
                    cOUIBottomSheetDialogFragment.show(fragmentManager, DelayDialogRunner.DIALOG_FRAGMENT_TAG);
                }
                TextConversionPanel textConversionPanel = this.this$0.panelFragment;
                if (textConversionPanel != null) {
                    TextConversionPanel.setLoading$default(textConversionPanel, true, null, 2, null);
                }
                this.this$0.isShowing = true;
                return Unit.INSTANCE;
            }

            @Override // kotlin.jvm.functions.p
            public final Object invoke(z zVar, d<? super Unit> dVar) {
                return ((C03421) create(zVar, dVar)).invokeSuspend(Unit.INSTANCE);
            }
        }

        public C10201(d<? super C10201> dVar) {
            super(2, dVar);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final d<Unit> create(Object obj, d<?> dVar) {
            return DelayDialogRunner.this.new C10201(dVar);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.f6153a;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Log.d(DelayDialogRunner.TAG, "show: delay = " + DelayDialogRunner.this.delay + ", atLeast = " + DelayDialogRunner.this.atLeast);
                long j = DelayDialogRunner.this.delay;
                this.label = 1;
                if (h0.a(j, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    if (i != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                ResultKt.throwOnFailure(obj);
            }
            b bVar = n0.f6298a;
            o1 o1Var = kotlinx.coroutines.internal.p.f6288a;
            C03421 c03421 = new C03421(DelayDialogRunner.this, null);
            this.label = 2;
            if (k.y(o1Var, c03421, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.p
        public final Object invoke(z zVar, d<? super Unit> dVar) {
            return ((C10201) create(zVar, dVar)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* compiled from: DelayDialogRunner.kt */
    @c(c = "com.oplusos.vfxsdk.doodleengine.DelayDialogRunner$showPanel$2", f = "DelayDialogRunner.kt", l = {}, m = "invokeSuspend")
    /* renamed from: com.oplusos.vfxsdk.doodleengine.DelayDialogRunner$showPanel$2, reason: invalid class name and case insensitive filesystem */
    public static final class C10212 extends SuspendLambda implements p<z, d<? super Unit>, Object> {
        final /* synthetic */ String $content;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10212(String str, d<? super C10212> dVar) {
            super(2, dVar);
            this.$content = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final d<Unit> create(Object obj, d<?> dVar) {
            return DelayDialogRunner.this.new C10212(this.$content, dVar);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            COUIBottomSheetDialogFragment cOUIBottomSheetDialogFragment;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.f6153a;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            FragmentManager fragmentManager = DelayDialogRunner.this.fragmentManager;
            if (fragmentManager != null && (cOUIBottomSheetDialogFragment = DelayDialogRunner.this.mBottomSheetDialogFragment) != null) {
                cOUIBottomSheetDialogFragment.show(fragmentManager, DelayDialogRunner.DIALOG_FRAGMENT_TAG);
            }
            TextConversionPanel textConversionPanel = DelayDialogRunner.this.panelFragment;
            if (textConversionPanel != null) {
                textConversionPanel.setLoading(false, this.$content);
            }
            DelayDialogRunner.this.isShowing = true;
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.p
        public final Object invoke(z zVar, d<? super Unit> dVar) {
            return ((C10212) create(zVar, dVar)).invokeSuspend(Unit.INSTANCE);
        }
    }

    public DelayDialogRunner(TextConversionPanel textConversionPanel, FragmentManager fragmentManager, COUIBottomSheetDialogFragment cOUIBottomSheetDialogFragment, long j, long j2, s owner) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        this.panelFragment = textConversionPanel;
        this.fragmentManager = fragmentManager;
        this.mBottomSheetDialogFragment = cOUIBottomSheetDialogFragment;
        this.delay = j;
        this.atLeast = j2;
        this.owner = owner;
    }

    public static /* synthetic */ void dismiss$default(DelayDialogRunner delayDialogRunner, boolean z, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        if ((i & 2) != 0) {
            str = "";
        }
        delayDialogRunner.dismiss(z, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object dismissLoading(String str, d<? super Unit> dVar) {
        b bVar = n0.f6298a;
        return k.y(kotlinx.coroutines.internal.p.f6288a, new AnonymousClass2(str, null), dVar);
    }

    public static /* synthetic */ Object dismissLoading$default(DelayDialogRunner delayDialogRunner, String str, d dVar, int i, Object obj) {
        if ((i & 1) != 0) {
            str = "";
        }
        return delayDialogRunner.dismissLoading(str, dVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object showPanel(String str, d<? super Unit> dVar) {
        b bVar = n0.f6298a;
        Object objY = k.y(kotlinx.coroutines.internal.p.f6288a, new C10212(str, null), dVar);
        return objY == CoroutineSingletons.f6153a ? objY : Unit.INSTANCE;
    }

    public static /* synthetic */ Object showPanel$default(DelayDialogRunner delayDialogRunner, String str, d dVar, int i, Object obj) {
        if ((i & 1) != 0) {
            str = "";
        }
        return delayDialogRunner.showPanel(str, dVar);
    }

    public final void dismiss(boolean z, String content) {
        Intrinsics.checkNotNullParameter(content, "content");
        k.n(com.oplus.account.netrequest.utils.e.g0(this.owner), null, null, new AnonymousClass1(z, this, content, null), 3);
    }

    public final void show() {
        this.alreadyDismiss = false;
        this.jobOfDelayShow = k.n(com.oplus.account.netrequest.utils.e.g0(this.owner), null, null, new C10201(null), 3);
    }

    public /* synthetic */ DelayDialogRunner(TextConversionPanel textConversionPanel, FragmentManager fragmentManager, COUIBottomSheetDialogFragment cOUIBottomSheetDialogFragment, long j, long j2, s sVar, int i, e eVar) {
        this(textConversionPanel, fragmentManager, cOUIBottomSheetDialogFragment, (i & 8) != 0 ? 1000L : j, (i & 16) != 0 ? 1000L : j2, sVar);
    }
}
