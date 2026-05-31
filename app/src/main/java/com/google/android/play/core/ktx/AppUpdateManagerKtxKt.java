package com.google.android.play.core.ktx;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateOptions;
import com.google.android.play.core.common.IntentSenderForResultStarter;
import com.google.android.play.core.install.InstallException;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.ktx.AppUpdateResult;
import com.tekartik.sqflite.Constant;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.channels.ChannelResult;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* JADX INFO: compiled from: com.google.android.play:app-update-ktx@@2.1.0 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000j\n\u0000\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a5\u0010 \u001a\u0002H!\"\u0004\b\u0000\u0010!2\f\u0010\"\u001a\b\u0012\u0004\u0012\u0002H!0#2\u000e\b\u0002\u0010$\u001a\b\u0012\u0004\u0012\u00020&0%H\u0080@ø\u0001\u0000¢\u0006\u0002\u0010'\u001a\u0015\u0010(\u001a\u00020\u0002*\u00020)H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010*\u001a\u0015\u0010+\u001a\u00020&*\u00020)H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010*\u001a\u0010\u0010,\u001a\b\u0012\u0004\u0012\u00020.0-*\u00020)\u001a*\u0010/\u001a\u00020\f*\u00020)2\u0006\u00100\u001a\u00020\u00022\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u00020\b\u001a%\u00106\u001a\u00020\f\"\u0004\b\u0000\u00107*\b\u0012\u0004\u0012\u0002H7082\u0006\u00109\u001a\u0002H7H\u0000¢\u0006\u0002\u0010:\"\u0016\u0010\u0000\u001a\u00020\u0001*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0016\u0010\u0000\u001a\u00020\u0001*\u00020\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0006\"\u0018\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\t\u0010\n\"\u0016\u0010\u000b\u001a\u00020\f*\u00020\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e\"\u0016\u0010\u000f\u001a\u00020\b*\u00020\u00058Ç\u0002¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011\"\u0016\u0010\u0012\u001a\u00020\b*\u00020\u00028Ç\u0002¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014\"\u0016\u0010\u0012\u001a\u00020\b*\u00020\u00058Ç\u0002¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0011\"\u0016\u0010\u0015\u001a\u00020\f*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016\"\u0016\u0010\u0017\u001a\u00020\f*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0016\"\u0016\u0010\u0018\u001a\u00020\u0019*\u00020\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001b\"\u0016\u0010\u001c\u001a\u00020\u0001*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u0004\"\u0016\u0010\u001c\u001a\u00020\u0001*\u00020\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u0006\"\u0016\u0010\u001e\u001a\u00020\b*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u0014\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006;"}, d2 = {"bytesDownloaded", "", "Lcom/google/android/play/core/appupdate/AppUpdateInfo;", "getBytesDownloaded", "(Lcom/google/android/play/core/appupdate/AppUpdateInfo;)J", "Lcom/google/android/play/core/install/InstallState;", "(Lcom/google/android/play/core/install/InstallState;)J", "clientVersionStalenessDays", "", "getClientVersionStalenessDays", "(Lcom/google/android/play/core/appupdate/AppUpdateInfo;)Ljava/lang/Integer;", "hasTerminalStatus", "", "getHasTerminalStatus", "(Lcom/google/android/play/core/install/InstallState;)Z", "installErrorCode", "getInstallErrorCode", "(Lcom/google/android/play/core/install/InstallState;)I", "installStatus", "getInstallStatus", "(Lcom/google/android/play/core/appupdate/AppUpdateInfo;)I", "isFlexibleUpdateAllowed", "(Lcom/google/android/play/core/appupdate/AppUpdateInfo;)Z", "isImmediateUpdateAllowed", "packageName", "", "getPackageName", "(Lcom/google/android/play/core/install/InstallState;)Ljava/lang/String;", "totalBytesToDownload", "getTotalBytesToDownload", "updatePriority", "getUpdatePriority", "runTask", ExifInterface.GPS_DIRECTION_TRUE, "task", "Lcom/google/android/gms/tasks/Task;", "onCanceled", "Lkotlin/Function0;", "", "(Lcom/google/android/gms/tasks/Task;Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "requestAppUpdateInfo", "Lcom/google/android/play/core/appupdate/AppUpdateManager;", "(Lcom/google/android/play/core/appupdate/AppUpdateManager;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "requestCompleteUpdate", "requestUpdateFlow", "Lkotlinx/coroutines/flow/Flow;", "Lcom/google/android/play/core/ktx/AppUpdateResult;", "startUpdateFlowForResult", "appUpdateInfo", "fragment", "Landroidx/fragment/app/Fragment;", Constant.METHOD_OPTIONS, "Lcom/google/android/play/core/appupdate/AppUpdateOptions;", "requestCode", "tryOffer", ExifInterface.LONGITUDE_EAST, "Lkotlinx/coroutines/channels/SendChannel;", "element", "(Lkotlinx/coroutines/channels/SendChannel;Ljava/lang/Object;)Z", "java.com.google.android.apps.play.store.sdk.playcore.ktx_playcore_app_update_ktx"}, k = 2, mv = {1, 8, 0}, xi = 48)
public final class AppUpdateManagerKtxKt {

    /* JADX INFO: renamed from: com.google.android.play.core.ktx.AppUpdateManagerKtxKt$requestAppUpdateInfo$1, reason: invalid class name */
    /* JADX INFO: compiled from: com.google.android.play:app-update-ktx@@2.1.0 */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.google.android.play.core.ktx.AppUpdateManagerKtxKt", f = "AppUpdateManagerKtx.kt", i = {}, l = {226}, m = "requestAppUpdateInfo", n = {}, s = {})
    static final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AppUpdateManagerKtxKt.requestAppUpdateInfo(null, this);
        }
    }

    /* JADX INFO: renamed from: com.google.android.play.core.ktx.AppUpdateManagerKtxKt$requestUpdateFlow$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: com.google.android.play:app-update-ktx@@2.1.0 */
    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/channels/ProducerScope;", "Lcom/google/android/play/core/ktx/AppUpdateResult;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.google.android.play.core.ktx.AppUpdateManagerKtxKt$requestUpdateFlow$1", f = "AppUpdateManagerKtx.kt", i = {}, l = {90}, m = "invokeSuspend", n = {}, s = {})
    static final class C00471 extends SuspendLambda implements Function2<ProducerScope<? super AppUpdateResult>, Continuation<? super Unit>, Object> {
        final /* synthetic */ AppUpdateManager $this_requestUpdateFlow;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C00471(AppUpdateManager appUpdateManager, Continuation<? super C00471> continuation) {
            super(2, continuation);
            this.$this_requestUpdateFlow = appUpdateManager;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C00471 c00471 = new C00471(this.$this_requestUpdateFlow, continuation);
            c00471.L$0 = obj;
            return c00471;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope<? super AppUpdateResult> producerScope, Continuation<? super Unit> continuation) {
            return ((C00471) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ProducerScope producerScope = (ProducerScope) this.L$0;
                final AppUpdateManager appUpdateManager = this.$this_requestUpdateFlow;
                final AppUpdatePassthroughListener appUpdatePassthroughListener = new AppUpdatePassthroughListener(new InstallStateUpdatedListener() { // from class: com.google.android.play.core.ktx.AppUpdateManagerKtxKt$requestUpdateFlow$1$globalUpdateListener$1
                    @Override // com.google.android.play.core.listener.StateUpdatedListener
                    public final void onStateUpdate(InstallState installState) {
                        Intrinsics.checkNotNullParameter(installState, "installState");
                        if (installState.installStatus() == 11) {
                            AppUpdateManagerKtxKt.tryOffer(producerScope, new AppUpdateResult.Downloaded(appUpdateManager));
                        } else {
                            AppUpdateManagerKtxKt.tryOffer(producerScope, new AppUpdateResult.InProgress(installState));
                        }
                    }
                }, new Function1<AppUpdatePassthroughListener, Unit>() { // from class: com.google.android.play.core.ktx.AppUpdateManagerKtxKt$requestUpdateFlow$1$globalUpdateListener$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final /* bridge */ /* synthetic */ Unit invoke(AppUpdatePassthroughListener appUpdatePassthroughListener2) {
                        invoke2(appUpdatePassthroughListener2);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(AppUpdatePassthroughListener $receiver) {
                        Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                        SendChannel.DefaultImpls.close$default(producerScope, null, 1, null);
                    }
                });
                Task<AppUpdateInfo> appUpdateInfo = this.$this_requestUpdateFlow.getAppUpdateInfo();
                final AppUpdateManager appUpdateManager2 = this.$this_requestUpdateFlow;
                appUpdateInfo.addOnSuccessListener(new OnSuccessListener() { // from class: com.google.android.play.core.ktx.AppUpdateManagerKtxKt.requestUpdateFlow.1.1
                    @Override // com.google.android.gms.tasks.OnSuccessListener
                    public final void onSuccess(AppUpdateInfo updateInfo) {
                        int iUpdateAvailability = updateInfo.updateAvailability();
                        if (iUpdateAvailability == 0) {
                            producerScope.close(new InstallException(-2));
                            return;
                        }
                        if (iUpdateAvailability == 1) {
                            AppUpdateManagerKtxKt.tryOffer(producerScope, AppUpdateResult.NotAvailable.INSTANCE);
                            SendChannel.DefaultImpls.close$default(producerScope, null, 1, null);
                        } else if (iUpdateAvailability == 2 || iUpdateAvailability == 3) {
                            Intrinsics.checkNotNullExpressionValue(updateInfo, "updateInfo");
                            if (updateInfo.installStatus() == 11) {
                                AppUpdateManagerKtxKt.tryOffer(producerScope, new AppUpdateResult.Downloaded(appUpdateManager2));
                                SendChannel.DefaultImpls.close$default(producerScope, null, 1, null);
                            } else {
                                appUpdateManager2.registerListener(appUpdatePassthroughListener);
                                AppUpdateManagerKtxKt.tryOffer(producerScope, new AppUpdateResult.Available(appUpdateManager2, updateInfo));
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() { // from class: com.google.android.play.core.ktx.AppUpdateManagerKtxKt.requestUpdateFlow.1.2
                    @Override // com.google.android.gms.tasks.OnFailureListener
                    public final void onFailure(Exception exception) {
                        Intrinsics.checkNotNullParameter(exception, "exception");
                        producerScope.close(exception);
                    }
                });
                final AppUpdateManager appUpdateManager3 = this.$this_requestUpdateFlow;
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, new Function0<Unit>() { // from class: com.google.android.play.core.ktx.AppUpdateManagerKtxKt.requestUpdateFlow.1.3
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        appUpdateManager3.unregisterListener(appUpdatePassthroughListener);
                    }
                }, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    public static final int getInstallErrorCode(InstallState installState) {
        Intrinsics.checkNotNullParameter(installState, "<this>");
        return installState.installErrorCode();
    }

    public static final int getInstallStatus(AppUpdateInfo appUpdateInfo) {
        Intrinsics.checkNotNullParameter(appUpdateInfo, "<this>");
        return appUpdateInfo.installStatus();
    }

    public static final int getInstallStatus(InstallState installState) {
        Intrinsics.checkNotNullParameter(installState, "<this>");
        return installState.installStatus();
    }

    public static final int getUpdatePriority(AppUpdateInfo appUpdateInfo) {
        Intrinsics.checkNotNullParameter(appUpdateInfo, "<this>");
        return appUpdateInfo.updatePriority();
    }

    public static final long getBytesDownloaded(AppUpdateInfo appUpdateInfo) {
        Intrinsics.checkNotNullParameter(appUpdateInfo, "<this>");
        return appUpdateInfo.bytesDownloaded();
    }

    public static final long getBytesDownloaded(InstallState installState) {
        Intrinsics.checkNotNullParameter(installState, "<this>");
        return installState.bytesDownloaded();
    }

    public static final long getTotalBytesToDownload(AppUpdateInfo appUpdateInfo) {
        Intrinsics.checkNotNullParameter(appUpdateInfo, "<this>");
        return appUpdateInfo.totalBytesToDownload();
    }

    public static final long getTotalBytesToDownload(InstallState installState) {
        Intrinsics.checkNotNullParameter(installState, "<this>");
        return installState.totalBytesToDownload();
    }

    public static final Integer getClientVersionStalenessDays(AppUpdateInfo appUpdateInfo) {
        Intrinsics.checkNotNullParameter(appUpdateInfo, "<this>");
        return appUpdateInfo.clientVersionStalenessDays();
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object requestAppUpdateInfo(com.google.android.play.core.appupdate.AppUpdateManager r4, kotlin.coroutines.Continuation<? super com.google.android.play.core.appupdate.AppUpdateInfo> r5) throws java.lang.Throwable {
        /*
            boolean r0 = r5 instanceof com.google.android.play.core.ktx.AppUpdateManagerKtxKt.AnonymousClass1
            if (r0 == 0) goto L14
            r0 = r5
            com.google.android.play.core.ktx.AppUpdateManagerKtxKt$requestAppUpdateInfo$1 r0 = (com.google.android.play.core.ktx.AppUpdateManagerKtxKt.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r5 = r0.label
            int r5 = r5 - r2
            r0.label = r5
            goto L19
        L14:
            com.google.android.play.core.ktx.AppUpdateManagerKtxKt$requestAppUpdateInfo$1 r0 = new com.google.android.play.core.ktx.AppUpdateManagerKtxKt$requestAppUpdateInfo$1
            r0.<init>(r5)
        L19:
            java.lang.Object r5 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L32
            if (r2 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r5)
            goto L49
        L2a:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L32:
            kotlin.ResultKt.throwOnFailure(r5)
            com.google.android.gms.tasks.Task r4 = r4.getAppUpdateInfo()
            java.lang.String r5 = "appUpdateInfo"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r4, r5)
            r0.label = r3
            r5 = 0
            r2 = 2
            java.lang.Object r5 = runTask$default(r4, r5, r0, r2, r5)
            if (r5 != r1) goto L49
            return r1
        L49:
            java.lang.String r4 = "runTask(appUpdateInfo)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r5, r4)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.play.core.ktx.AppUpdateManagerKtxKt.requestAppUpdateInfo(com.google.android.play.core.appupdate.AppUpdateManager, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static final Object requestCompleteUpdate(AppUpdateManager appUpdateManager, Continuation<? super Unit> continuation) {
        Task<Void> taskCompleteUpdate = appUpdateManager.completeUpdate();
        Intrinsics.checkNotNullExpressionValue(taskCompleteUpdate, "completeUpdate()");
        Object objRunTask$default = runTask$default(taskCompleteUpdate, null, continuation, 2, null);
        return objRunTask$default == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objRunTask$default : Unit.INSTANCE;
    }

    public static final <T> Object runTask(Task<T> task, final Function0<Unit> function0, Continuation<? super T> continuation) {
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        final CancellableContinuationImpl cancellableContinuationImpl2 = cancellableContinuationImpl;
        cancellableContinuationImpl2.invokeOnCancellation(new Function1<Throwable, Unit>() { // from class: com.google.android.play.core.ktx.AppUpdateManagerKtxKt$runTask$3$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable th) {
                function0.invoke();
            }
        });
        if (task.isComplete()) {
            if (task.isSuccessful()) {
                cancellableContinuationImpl2.resumeWith(Result.m684constructorimpl(task.getResult()));
            } else {
                Exception exception = task.getException();
                Intrinsics.checkNotNull(exception);
                cancellableContinuationImpl2.resumeWith(Result.m684constructorimpl(ResultKt.createFailure(exception)));
            }
        } else {
            task.addOnSuccessListener(new OnSuccessListener() { // from class: com.google.android.play.core.ktx.AppUpdateManagerKtxKt$runTask$3$2
                @Override // com.google.android.gms.tasks.OnSuccessListener
                public final void onSuccess(T t) {
                    cancellableContinuationImpl2.resumeWith(Result.m684constructorimpl(t));
                }
            });
            task.addOnFailureListener(new OnFailureListener() { // from class: com.google.android.play.core.ktx.AppUpdateManagerKtxKt$runTask$3$3
                @Override // com.google.android.gms.tasks.OnFailureListener
                public final void onFailure(Exception exception2) {
                    Intrinsics.checkNotNullParameter(exception2, "exception");
                    cancellableContinuationImpl2.resumeWith(Result.m684constructorimpl(ResultKt.createFailure(exception2)));
                }
            });
        }
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result;
    }

    public static /* synthetic */ Object runTask$default(Task task, Function0 function0, Continuation continuation, int i, Object obj) {
        if ((i & 2) != 0) {
            function0 = new Function0<Unit>() { // from class: com.google.android.play.core.ktx.AppUpdateManagerKtxKt.runTask.2
                @Override // kotlin.jvm.functions.Function0
                public final /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                }
            };
        }
        return runTask(task, function0, continuation);
    }

    public static final String getPackageName(InstallState installState) {
        Intrinsics.checkNotNullParameter(installState, "<this>");
        String strPackageName = installState.packageName();
        Intrinsics.checkNotNullExpressionValue(strPackageName, "packageName()");
        return strPackageName;
    }

    public static final Flow<AppUpdateResult> requestUpdateFlow(AppUpdateManager appUpdateManager) throws InstallException {
        Intrinsics.checkNotNullParameter(appUpdateManager, "<this>");
        return FlowKt.conflate(FlowKt.callbackFlow(new C00471(appUpdateManager, null)));
    }

    public static final boolean getHasTerminalStatus(InstallState installState) {
        Intrinsics.checkNotNullParameter(installState, "<this>");
        int iInstallStatus = installState.installStatus();
        return iInstallStatus == 0 || iInstallStatus == 11 || iInstallStatus == 5 || iInstallStatus == 6;
    }

    public static final boolean isFlexibleUpdateAllowed(AppUpdateInfo appUpdateInfo) {
        Intrinsics.checkNotNullParameter(appUpdateInfo, "<this>");
        return appUpdateInfo.isUpdateTypeAllowed(0);
    }

    public static final boolean isImmediateUpdateAllowed(AppUpdateInfo appUpdateInfo) {
        Intrinsics.checkNotNullParameter(appUpdateInfo, "<this>");
        return appUpdateInfo.isUpdateTypeAllowed(1);
    }

    public static final boolean startUpdateFlowForResult(AppUpdateManager appUpdateManager, AppUpdateInfo appUpdateInfo, final Fragment fragment, AppUpdateOptions options, int i) throws IntentSender.SendIntentException {
        Intrinsics.checkNotNullParameter(appUpdateManager, "<this>");
        Intrinsics.checkNotNullParameter(appUpdateInfo, "appUpdateInfo");
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        Intrinsics.checkNotNullParameter(options, "options");
        return appUpdateManager.startUpdateFlowForResult(appUpdateInfo, new IntentSenderForResultStarter() { // from class: com.google.android.play.core.ktx.AppUpdateManagerKtxKt.startUpdateFlowForResult.1
            @Override // com.google.android.play.core.common.IntentSenderForResultStarter
            public final void startIntentSenderForResult(IntentSender p0, int i2, Intent intent, int i3, int i4, int i5, Bundle bundle) throws IntentSender.SendIntentException {
                Intrinsics.checkNotNullParameter(p0, "p0");
                fragment.startIntentSenderForResult(p0, i2, intent, i3, i4, i5, bundle);
            }
        }, options, i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <E> boolean tryOffer(SendChannel<? super E> sendChannel, E e) {
        Intrinsics.checkNotNullParameter(sendChannel, "<this>");
        return ChannelResult.m2208isSuccessimpl(sendChannel.mo2188trySendJP2dKIU(e));
    }
}
