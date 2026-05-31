package com.amazonaws.amplify.amplify_auth_cognito;

import androidx.exifinterface.media.ExifInterface;
import com.tekartik.sqflite.Constant;
import io.flutter.Log;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;

/* JADX INFO: compiled from: AtomicResult.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \r*\u0004\b\u0000\u0010\u00012\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u0003\u0012\u0004\u0012\u00020\u00040\u0002:\u0001\rB'\u0012\u0018\u0010\u0005\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0003\u0012\u0004\u0012\u00020\u00040\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u001c\u0010\u000b\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0096\u0002¢\u0006\u0002\u0010\fR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0005\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0003\u0012\u0004\u0012\u00020\u00040\u0002X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/amazonaws/amplify/amplify_auth_cognito/AtomicResult;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/Function1;", "Lkotlin/Result;", "", Constant.PARAM_RESULT, "operation", "", "(Lkotlin/jvm/functions/Function1;Ljava/lang/String;)V", "isSent", "Ljava/util/concurrent/atomic/AtomicBoolean;", "invoke", "(Ljava/lang/Object;)V", "Companion", "amplify_auth_cognito_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AtomicResult<T> implements Function1<Result<? extends T>, Unit> {
    private static final Companion Companion = new Companion(null);
    private static final CoroutineScope scope = CoroutineScopeKt.MainScope();
    private final AtomicBoolean isSent;
    private final String operation;
    private final Function1<Result<? extends T>, Unit> result;

    /* JADX WARN: Multi-variable type inference failed */
    public AtomicResult(Function1<? super Result<? extends T>, Unit> result, String operation) {
        Intrinsics.checkNotNullParameter(result, "result");
        Intrinsics.checkNotNullParameter(operation, "operation");
        this.result = result;
        this.operation = operation;
        this.isSent = new AtomicBoolean(false);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Object obj) {
        invoke2(((Result) obj).getValue());
        return Unit.INSTANCE;
    }

    /* JADX INFO: compiled from: AtomicResult.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/amazonaws/amplify/amplify_auth_cognito/AtomicResult$Companion;", "", "()V", "scope", "Lkotlinx/coroutines/CoroutineScope;", "getScope", "()Lkotlinx/coroutines/CoroutineScope;", "amplify_auth_cognito_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final CoroutineScope getScope() {
            return AtomicResult.scope;
        }
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public void invoke2(Object result) {
        if (!this.isSent.getAndSet(true)) {
            BuildersKt__Builders_commonKt.launch$default(scope, null, null, new AnonymousClass1(this, result, null), 3, null);
            return;
        }
        Log.w("AmplifyHostedUiPlugin(" + this.operation + ")", StringsKt.trimIndent("\n                    Attempted to send result after initial reply:\n                    | " + Result.m692toStringimpl(result) + "\n                "));
    }

    /* JADX INFO: renamed from: com.amazonaws.amplify.amplify_auth_cognito.AtomicResult$invoke$1, reason: invalid class name */
    /* JADX INFO: compiled from: AtomicResult.kt */
    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "com.amazonaws.amplify.amplify_auth_cognito.AtomicResult$invoke$1", f = "AtomicResult.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Object $result;
        int label;
        final /* synthetic */ AtomicResult<T> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(AtomicResult<T> atomicResult, Object obj, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.this$0 = atomicResult;
            this.$result = obj;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.this$0, this.$result, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                ((AtomicResult) this.this$0).result.invoke(Result.m683boximpl(this.$result));
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
