package kotlinx.coroutines.channels;

import androidx.concurrent.futures.AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0;
import androidx.exifinterface.media.ExifInterface;
import androidx.webkit.ProxyConfig;
import com.tekartik.sqflite.Constant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.Volatile;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.time.DurationKt;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CancellableContinuationKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.Waiter;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ChannelIterator;
import kotlinx.coroutines.internal.ConcurrentLinkedListKt;
import kotlinx.coroutines.internal.ConcurrentLinkedListNode;
import kotlinx.coroutines.internal.InlineList;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.Segment;
import kotlinx.coroutines.internal.SegmentOrClosed;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.internal.UndeliveredElementException;
import kotlinx.coroutines.selects.SelectClause1;
import kotlinx.coroutines.selects.SelectClause1Impl;
import kotlinx.coroutines.selects.SelectClause2;
import kotlinx.coroutines.selects.SelectClause2Impl;
import kotlinx.coroutines.selects.SelectImplementation;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.TrySelectDetailedResult;

/* JADX INFO: compiled from: BufferedChannel.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000À\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b#\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\"\b\u0010\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002:\u0004Þ\u0001ß\u0001B1\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\"\b\u0002\u0010\u0005\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\b¢\u0006\u0002\u0010\tJ\u0010\u0010P\u001a\u00020\u001c2\u0006\u0010Q\u001a\u00020\u0010H\u0002J\u0006\u0010R\u001a\u00020\u0007J\u0010\u0010R\u001a\u00020\u001c2\b\u0010S\u001a\u0004\u0018\u00010\u0016J\u0016\u0010R\u001a\u00020\u00072\u000e\u0010S\u001a\n\u0018\u00010Tj\u0004\u0018\u0001`UJ\u0017\u0010V\u001a\u00020\u001c2\b\u0010S\u001a\u0004\u0018\u00010\u0016H\u0010¢\u0006\u0002\bWJ\u001e\u0010X\u001a\u00020\u00072\f\u0010Y\u001a\b\u0012\u0004\u0012\u00028\u00000\u00142\u0006\u0010K\u001a\u00020\u0010H\u0002J\u0006\u0010Z\u001a\u00020\u0007J\u0012\u0010[\u001a\u00020\u001c2\b\u0010S\u001a\u0004\u0018\u00010\u0016H\u0016J\u000e\u0010\\\u001a\b\u0012\u0004\u0012\u00028\u00000\u0014H\u0002J\u001a\u0010]\u001a\u00020\u001c2\b\u0010S\u001a\u0004\u0018\u00010\u00162\u0006\u0010R\u001a\u00020\u001cH\u0014J\u0010\u0010^\u001a\u00020\u00072\u0006\u0010_\u001a\u00020\u0010H\u0002J\u0016\u0010`\u001a\b\u0012\u0004\u0012\u00028\u00000\u00142\u0006\u0010_\u001a\u00020\u0010H\u0002J\b\u0010a\u001a\u00020\u0007H\u0002J\u0010\u0010b\u001a\u00020\u00072\u0006\u0010c\u001a\u00020\u0010H\u0004J\b\u0010d\u001a\u00020\u0007H\u0002J.\u0010e\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00142\u0006\u0010f\u001a\u00020\u00102\f\u0010g\u001a\b\u0012\u0004\u0012\u00028\u00000\u00142\u0006\u0010h\u001a\u00020\u0010H\u0002J&\u0010i\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00142\u0006\u0010f\u001a\u00020\u00102\f\u0010g\u001a\b\u0012\u0004\u0012\u00028\u00000\u0014H\u0002J&\u0010j\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00142\u0006\u0010f\u001a\u00020\u00102\f\u0010g\u001a\b\u0012\u0004\u0012\u00028\u00000\u0014H\u0002J\r\u0010k\u001a\u00020\u001cH\u0000¢\u0006\u0002\blJ\u0012\u0010m\u001a\u00020\u00072\b\b\u0002\u0010n\u001a\u00020\u0010H\u0002J\b\u0010o\u001a\u00020\u0007H\u0002J-\u0010p\u001a\u00020\u00072#\u0010q\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\u0016¢\u0006\f\b:\u0012\b\b;\u0012\u0004\b\b(S\u0012\u0004\u0012\u00020\u00070\u0006H\u0016J&\u0010r\u001a\u00020\u001c2\f\u0010s\u001a\b\u0012\u0004\u0012\u00028\u00000\u00142\u0006\u0010t\u001a\u00020\u00042\u0006\u0010u\u001a\u00020\u0010H\u0002J\u0018\u0010v\u001a\u00020\u001c2\u0006\u0010w\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u000f\u0010x\u001a\b\u0012\u0004\u0012\u00028\u00000yH\u0096\u0002J\u0016\u0010z\u001a\u00020\u00102\f\u0010Y\u001a\b\u0012\u0004\u0012\u00028\u00000\u0014H\u0002J\b\u0010{\u001a\u00020\u0007H\u0002J\b\u0010|\u001a\u00020\u0007H\u0002J\b\u0010}\u001a\u00020\u0007H\u0002J\u001e\u0010~\u001a\u00020\u00072\u0006\u0010f\u001a\u00020\u00102\f\u0010g\u001a\b\u0012\u0004\u0012\u00028\u00000\u0014H\u0002J\b\u0010\u007f\u001a\u00020\u0007H\u0014J\"\u0010\u0080\u0001\u001a\u00020\u00072\u0014\u0010\u0081\u0001\u001a\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000,0\u0082\u0001H\u0002ø\u0001\u0000J\u0019\u0010\u0083\u0001\u001a\u00020\u00072\u000e\u0010\u0081\u0001\u001a\t\u0012\u0004\u0012\u00028\u00000\u0082\u0001H\u0002J\u0015\u0010\u0084\u0001\u001a\u00020\u00072\n\u0010<\u001a\u0006\u0012\u0002\b\u000309H\u0002J$\u0010\u0085\u0001\u001a\u00020\u00072\u0007\u0010\u0086\u0001\u001a\u00028\u00002\n\u0010<\u001a\u0006\u0012\u0002\b\u000309H\u0002¢\u0006\u0003\u0010\u0087\u0001J\u001c\u0010\u0088\u0001\u001a\u00020\u00072\u0007\u0010\u0086\u0001\u001a\u00028\u0000H\u0082@ø\u0001\u0000¢\u0006\u0003\u0010\u0089\u0001J(\u0010\u008a\u0001\u001a\u00020\u00072\u0007\u0010\u0086\u0001\u001a\u00028\u00002\u000e\u0010\u0081\u0001\u001a\t\u0012\u0004\u0012\u00020\u00070\u0082\u0001H\u0002¢\u0006\u0003\u0010\u008b\u0001J\t\u0010\u008c\u0001\u001a\u00020\u0007H\u0014J\t\u0010\u008d\u0001\u001a\u00020\u0007H\u0014J!\u0010\u008e\u0001\u001a\u0004\u0018\u00010\f2\t\u0010\u008f\u0001\u001a\u0004\u0018\u00010\f2\t\u0010\u0090\u0001\u001a\u0004\u0018\u00010\fH\u0002J!\u0010\u0091\u0001\u001a\u0004\u0018\u00010\f2\t\u0010\u008f\u0001\u001a\u0004\u0018\u00010\f2\t\u0010\u0090\u0001\u001a\u0004\u0018\u00010\fH\u0002J!\u0010\u0092\u0001\u001a\u0004\u0018\u00010\f2\t\u0010\u008f\u0001\u001a\u0004\u0018\u00010\f2\t\u0010\u0090\u0001\u001a\u0004\u0018\u00010\fH\u0002J!\u0010\u0093\u0001\u001a\u0004\u0018\u00010\f2\t\u0010\u008f\u0001\u001a\u0004\u0018\u00010\f2\t\u0010\u0090\u0001\u001a\u0004\u0018\u00010\fH\u0002J\u0013\u0010\u0094\u0001\u001a\u00028\u0000H\u0096@ø\u0001\u0000¢\u0006\u0003\u0010\u0095\u0001J%\u0010\u0096\u0001\u001a\b\u0012\u0004\u0012\u00028\u00000,H\u0096@ø\u0001\u0001ø\u0001\u0002ø\u0001\u0000ø\u0001\u0000¢\u0006\u0006\b\u0097\u0001\u0010\u0095\u0001JD\u0010\u0098\u0001\u001a\b\u0012\u0004\u0012\u00028\u00000,2\f\u0010s\u001a\b\u0012\u0004\u0012\u00028\u00000\u00142\u0006\u0010t\u001a\u00020\u00042\u0007\u0010\u0099\u0001\u001a\u00020\u0010H\u0082@ø\u0001\u0001ø\u0001\u0002ø\u0001\u0000ø\u0001\u0000¢\u0006\u0006\b\u009a\u0001\u0010\u009b\u0001J\u008c\u0002\u0010\u009c\u0001\u001a\u0003H\u009d\u0001\"\u0005\b\u0001\u0010\u009d\u00012\t\u0010\u009e\u0001\u001a\u0004\u0018\u00010\f2$\u0010\u009f\u0001\u001a\u001f\u0012\u0014\u0012\u00128\u0000¢\u0006\r\b:\u0012\t\b;\u0012\u0005\b\b(\u0086\u0001\u0012\u0005\u0012\u0003H\u009d\u00010\u00062V\u0010 \u0001\u001aQ\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00028\u00000\u0014¢\u0006\r\b:\u0012\t\b;\u0012\u0005\b\b(¡\u0001\u0012\u0014\u0012\u00120\u0004¢\u0006\r\b:\u0012\t\b;\u0012\u0005\b\b(¢\u0001\u0012\u0014\u0012\u00120\u0010¢\u0006\r\b:\u0012\t\b;\u0012\u0005\b\b(\u0099\u0001\u0012\u0005\u0012\u0003H\u009d\u0001082\u000f\u0010£\u0001\u001a\n\u0012\u0005\u0012\u0003H\u009d\u00010¤\u00012X\b\u0002\u0010¥\u0001\u001aQ\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00028\u00000\u0014¢\u0006\r\b:\u0012\t\b;\u0012\u0005\b\b(¡\u0001\u0012\u0014\u0012\u00120\u0004¢\u0006\r\b:\u0012\t\b;\u0012\u0005\b\b(¢\u0001\u0012\u0014\u0012\u00120\u0010¢\u0006\r\b:\u0012\t\b;\u0012\u0005\b\b(\u0099\u0001\u0012\u0005\u0012\u0003H\u009d\u000108H\u0082\b¢\u0006\u0003\u0010¦\u0001Jh\u0010§\u0001\u001a\u00020\u00072\f\u0010s\u001a\b\u0012\u0004\u0012\u00028\u00000\u00142\u0006\u0010t\u001a\u00020\u00042\u0007\u0010\u0099\u0001\u001a\u00020\u00102\b\u0010\u009e\u0001\u001a\u00030¨\u00012#\u0010\u009f\u0001\u001a\u001e\u0012\u0014\u0012\u00128\u0000¢\u0006\r\b:\u0012\t\b;\u0012\u0005\b\b(\u0086\u0001\u0012\u0004\u0012\u00020\u00070\u00062\u000e\u0010£\u0001\u001a\t\u0012\u0004\u0012\u00020\u00070¤\u0001H\u0082\bJ2\u0010©\u0001\u001a\u00028\u00002\f\u0010s\u001a\b\u0012\u0004\u0012\u00028\u00000\u00142\u0006\u0010t\u001a\u00020\u00042\u0007\u0010\u0099\u0001\u001a\u00020\u0010H\u0082@ø\u0001\u0000¢\u0006\u0003\u0010\u009b\u0001J \u0010ª\u0001\u001a\u00020\u00072\n\u0010<\u001a\u0006\u0012\u0002\b\u0003092\t\u0010\u008f\u0001\u001a\u0004\u0018\u00010\fH\u0002J \u0010«\u0001\u001a\u00020\u00072\n\u0010<\u001a\u0006\u0012\u0002\b\u0003092\t\u0010\u0086\u0001\u001a\u0004\u0018\u00010\fH\u0014J\u0017\u0010¬\u0001\u001a\u00020\u00072\f\u0010Y\u001a\b\u0012\u0004\u0012\u00028\u00000\u0014H\u0002J\u001c\u0010\u00ad\u0001\u001a\u00020\u00072\u0007\u0010\u0086\u0001\u001a\u00028\u0000H\u0096@ø\u0001\u0000¢\u0006\u0003\u0010\u0089\u0001J\u001f\u0010®\u0001\u001a\u00020\u001c2\u0007\u0010\u0086\u0001\u001a\u00028\u0000H\u0090@ø\u0001\u0000¢\u0006\u0006\b¯\u0001\u0010\u0089\u0001J\u0082\u0002\u0010°\u0001\u001a\u0003H\u009d\u0001\"\u0005\b\u0001\u0010\u009d\u00012\u0007\u0010\u0086\u0001\u001a\u00028\u00002\t\u0010\u009e\u0001\u001a\u0004\u0018\u00010\f2\u000f\u0010±\u0001\u001a\n\u0012\u0005\u0012\u0003H\u009d\u00010¤\u00012A\u0010 \u0001\u001a<\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00028\u00000\u0014¢\u0006\r\b:\u0012\t\b;\u0012\u0005\b\b(¡\u0001\u0012\u0014\u0012\u00120\u0004¢\u0006\r\b:\u0012\t\b;\u0012\u0005\b\b(¢\u0001\u0012\u0005\u0012\u0003H\u009d\u00010²\u00012\u000f\u0010£\u0001\u001a\n\u0012\u0005\u0012\u0003H\u009d\u00010¤\u00012o\b\u0002\u0010¥\u0001\u001ah\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00028\u00000\u0014¢\u0006\r\b:\u0012\t\b;\u0012\u0005\b\b(¡\u0001\u0012\u0014\u0012\u00120\u0004¢\u0006\r\b:\u0012\t\b;\u0012\u0005\b\b(¢\u0001\u0012\u0014\u0012\u00128\u0000¢\u0006\r\b:\u0012\t\b;\u0012\u0005\b\b(\u0086\u0001\u0012\u0014\u0012\u00120\u0010¢\u0006\r\b:\u0012\t\b;\u0012\u0005\b\b(´\u0001\u0012\u0005\u0012\u0003H\u009d\u00010³\u0001H\u0084\b¢\u0006\u0003\u0010µ\u0001Jb\u0010¶\u0001\u001a\u00020\u00072\f\u0010s\u001a\b\u0012\u0004\u0012\u00028\u00000\u00142\u0006\u0010t\u001a\u00020\u00042\u0007\u0010\u0086\u0001\u001a\u00028\u00002\u0007\u0010´\u0001\u001a\u00020\u00102\b\u0010\u009e\u0001\u001a\u00030¨\u00012\u000e\u0010±\u0001\u001a\t\u0012\u0004\u0012\u00020\u00070¤\u00012\u000e\u0010£\u0001\u001a\t\u0012\u0004\u0012\u00020\u00070¤\u0001H\u0082\b¢\u0006\u0003\u0010·\u0001J;\u0010¸\u0001\u001a\u00020\u00072\f\u0010s\u001a\b\u0012\u0004\u0012\u00028\u00000\u00142\u0006\u0010t\u001a\u00020\u00042\u0007\u0010\u0086\u0001\u001a\u00028\u00002\u0007\u0010´\u0001\u001a\u00020\u0010H\u0082@ø\u0001\u0000¢\u0006\u0003\u0010¹\u0001J\u000f\u0010º\u0001\u001a\u00020\u001cH\u0010¢\u0006\u0003\b»\u0001J\u0012\u0010º\u0001\u001a\u00020\u001c2\u0007\u0010¼\u0001\u001a\u00020\u0010H\u0003J\n\u0010½\u0001\u001a\u00030¾\u0001H\u0016J\u0010\u0010¿\u0001\u001a\u00030¾\u0001H\u0000¢\u0006\u0003\bÀ\u0001J!\u0010Á\u0001\u001a\b\u0012\u0004\u0012\u00028\u00000,H\u0016ø\u0001\u0001ø\u0001\u0002ø\u0001\u0000¢\u0006\u0006\bÂ\u0001\u0010Ã\u0001J*\u0010Ä\u0001\u001a\b\u0012\u0004\u0012\u00020\u00070,2\u0007\u0010\u0086\u0001\u001a\u00028\u0000H\u0016ø\u0001\u0001ø\u0001\u0002ø\u0001\u0000¢\u0006\u0006\bÅ\u0001\u0010Æ\u0001J(\u0010Ç\u0001\u001a\u00020\u001c2\f\u0010s\u001a\b\u0012\u0004\u0012\u00028\u00000\u00142\u0006\u0010t\u001a\u00020\u00042\u0007\u0010È\u0001\u001a\u00020\u0010H\u0002J(\u0010É\u0001\u001a\u00020\u001c2\f\u0010s\u001a\b\u0012\u0004\u0012\u00028\u00000\u00142\u0006\u0010t\u001a\u00020\u00042\u0007\u0010È\u0001\u001a\u00020\u0010H\u0002J5\u0010Ê\u0001\u001a\u0004\u0018\u00010\f2\f\u0010s\u001a\b\u0012\u0004\u0012\u00028\u00000\u00142\u0006\u0010t\u001a\u00020\u00042\u0007\u0010\u0099\u0001\u001a\u00020\u00102\t\u0010\u009e\u0001\u001a\u0004\u0018\u00010\fH\u0002J5\u0010Ë\u0001\u001a\u0004\u0018\u00010\f2\f\u0010s\u001a\b\u0012\u0004\u0012\u00028\u00000\u00142\u0006\u0010t\u001a\u00020\u00042\u0007\u0010\u0099\u0001\u001a\u00020\u00102\t\u0010\u009e\u0001\u001a\u0004\u0018\u00010\fH\u0002JK\u0010Ì\u0001\u001a\u00020\u00042\f\u0010s\u001a\b\u0012\u0004\u0012\u00028\u00000\u00142\u0006\u0010t\u001a\u00020\u00042\u0007\u0010\u0086\u0001\u001a\u00028\u00002\u0007\u0010´\u0001\u001a\u00020\u00102\t\u0010\u009e\u0001\u001a\u0004\u0018\u00010\f2\u0007\u0010Í\u0001\u001a\u00020\u001cH\u0002¢\u0006\u0003\u0010Î\u0001JK\u0010Ï\u0001\u001a\u00020\u00042\f\u0010s\u001a\b\u0012\u0004\u0012\u00028\u00000\u00142\u0006\u0010t\u001a\u00020\u00042\u0007\u0010\u0086\u0001\u001a\u00028\u00002\u0007\u0010´\u0001\u001a\u00020\u00102\t\u0010\u009e\u0001\u001a\u0004\u0018\u00010\f2\u0007\u0010Í\u0001\u001a\u00020\u001cH\u0002¢\u0006\u0003\u0010Î\u0001J\u0012\u0010Ð\u0001\u001a\u00020\u00072\u0007\u0010Ñ\u0001\u001a\u00020\u0010H\u0002J\u0012\u0010Ò\u0001\u001a\u00020\u00072\u0007\u0010Ñ\u0001\u001a\u00020\u0010H\u0002J\u0017\u0010Ó\u0001\u001a\u00020\u00072\u0006\u0010u\u001a\u00020\u0010H\u0000¢\u0006\u0003\bÔ\u0001J$\u0010Õ\u0001\u001a\u00020\u0007*\u00030¨\u00012\f\u0010s\u001a\b\u0012\u0004\u0012\u00028\u00000\u00142\u0006\u0010t\u001a\u00020\u0004H\u0002J$\u0010Ö\u0001\u001a\u00020\u0007*\u00030¨\u00012\f\u0010s\u001a\b\u0012\u0004\u0012\u00028\u00000\u00142\u0006\u0010t\u001a\u00020\u0004H\u0002J\u000e\u0010×\u0001\u001a\u00020\u0007*\u00030¨\u0001H\u0002J\u000e\u0010Ø\u0001\u001a\u00020\u0007*\u00030¨\u0001H\u0002J\u0017\u0010Ù\u0001\u001a\u00020\u0007*\u00030¨\u00012\u0007\u0010Ú\u0001\u001a\u00020\u001cH\u0002J\u001c\u0010Û\u0001\u001a\u00020\u001c*\u00020\f2\u0007\u0010\u0086\u0001\u001a\u00028\u0000H\u0002¢\u0006\u0003\u0010Ü\u0001J#\u0010Ý\u0001\u001a\u00020\u001c*\u00020\f2\f\u0010s\u001a\b\u0012\u0004\u0012\u00028\u00000\u00142\u0006\u0010t\u001a\u00020\u0004H\u0002R\u0011\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000bX\u0082\u0004R\t\u0010\r\u001a\u00020\u000eX\u0082\u0004R\u0014\u0010\u000f\u001a\u00020\u00108BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0015\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00140\u000bX\u0082\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0015\u001a\u0004\u0018\u00010\u00168DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0019\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000bX\u0082\u0004R\t\u0010\u001a\u001a\u00020\u000eX\u0082\u0004R\u001a\u0010\u001b\u001a\u00020\u001c8VX\u0097\u0004¢\u0006\f\u0012\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001b\u0010\u001fR\u001a\u0010 \u001a\u00020\u001c8VX\u0097\u0004¢\u0006\f\u0012\u0004\b!\u0010\u001e\u001a\u0004\b \u0010\u001fR\u0014\u0010\"\u001a\u00020\u001c8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010\u001fR\u001a\u0010#\u001a\u00020\u001c8VX\u0097\u0004¢\u0006\f\u0012\u0004\b$\u0010\u001e\u001a\u0004\b#\u0010\u001fR\u0014\u0010%\u001a\u00020\u001c8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b%\u0010\u001fR \u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000'8VX\u0096\u0004¢\u0006\f\u0012\u0004\b(\u0010\u001e\u001a\u0004\b)\u0010*R)\u0010+\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000,0'8VX\u0096\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b-\u0010\u001e\u001a\u0004\b.\u0010*R\"\u0010/\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000'8VX\u0096\u0004¢\u0006\f\u0012\u0004\b0\u0010\u001e\u001a\u0004\b1\u0010*R,\u00102\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0000038VX\u0096\u0004¢\u0006\f\u0012\u0004\b4\u0010\u001e\u001a\u0004\b5\u00106R*\u0010\u0005\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\b8\u0000X\u0081\u0004¢\u0006\u0002\n\u0000Ru\u00107\u001ac\u0012\u0017\u0012\u0015\u0012\u0002\b\u000309¢\u0006\f\b:\u0012\b\b;\u0012\u0004\b\b(<\u0012\u0015\u0012\u0013\u0018\u00010\f¢\u0006\f\b:\u0012\b\b;\u0012\u0004\b\b(=\u0012\u0015\u0012\u0013\u0018\u00010\f¢\u0006\f\b:\u0012\b\b;\u0012\u0004\b\b(>\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00070\u0006\u0018\u000108j\u0004\u0018\u0001`?X\u0082\u0004¢\u0006\b\n\u0000\u0012\u0004\b@\u0010\u001eR\u0014\u0010A\u001a\u00020\u00168BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bB\u0010\u0018R\u0015\u0010C\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00140\u000bX\u0082\u0004R\t\u0010D\u001a\u00020\u000eX\u0082\u0004R\u0014\u0010E\u001a\u00020\u00108@X\u0080\u0004¢\u0006\u0006\u001a\u0004\bF\u0010\u0012R\u0014\u0010G\u001a\u00020\u00168DX\u0084\u0004¢\u0006\u0006\u001a\u0004\bH\u0010\u0018R\u0015\u0010I\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00140\u000bX\u0082\u0004R\t\u0010J\u001a\u00020\u000eX\u0082\u0004R\u0014\u0010K\u001a\u00020\u00108@X\u0080\u0004¢\u0006\u0006\u001a\u0004\bL\u0010\u0012R\u0018\u0010M\u001a\u00020\u001c*\u00020\u00108BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bM\u0010NR\u0018\u0010O\u001a\u00020\u001c*\u00020\u00108BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bO\u0010N\u0082\u0002\u000f\n\u0002\b\u0019\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006à\u0001"}, d2 = {"Lkotlinx/coroutines/channels/BufferedChannel;", ExifInterface.LONGITUDE_EAST, "Lkotlinx/coroutines/channels/Channel;", "capacity", "", "onUndeliveredElement", "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "(ILkotlin/jvm/functions/Function1;)V", "_closeCause", "Lkotlinx/atomicfu/AtomicRef;", "", "bufferEnd", "Lkotlinx/atomicfu/AtomicLong;", "bufferEndCounter", "", "getBufferEndCounter", "()J", "bufferEndSegment", "Lkotlinx/coroutines/channels/ChannelSegment;", "closeCause", "", "getCloseCause", "()Ljava/lang/Throwable;", "closeHandler", "completedExpandBuffersAndPauseFlag", "isClosedForReceive", "", "isClosedForReceive$annotations", "()V", "()Z", "isClosedForSend", "isClosedForSend$annotations", "isConflatedDropOldest", "isEmpty", "isEmpty$annotations", "isRendezvousOrUnlimited", "onReceive", "Lkotlinx/coroutines/selects/SelectClause1;", "getOnReceive$annotations", "getOnReceive", "()Lkotlinx/coroutines/selects/SelectClause1;", "onReceiveCatching", "Lkotlinx/coroutines/channels/ChannelResult;", "getOnReceiveCatching$annotations", "getOnReceiveCatching", "onReceiveOrNull", "getOnReceiveOrNull$annotations", "getOnReceiveOrNull", "onSend", "Lkotlinx/coroutines/selects/SelectClause2;", "getOnSend$annotations", "getOnSend", "()Lkotlinx/coroutines/selects/SelectClause2;", "onUndeliveredElementReceiveCancellationConstructor", "Lkotlin/Function3;", "Lkotlinx/coroutines/selects/SelectInstance;", "Lkotlin/ParameterName;", "name", "select", "param", "internalResult", "Lkotlinx/coroutines/selects/OnCancellationConstructor;", "getOnUndeliveredElementReceiveCancellationConstructor$annotations", "receiveException", "getReceiveException", "receiveSegment", "receivers", "receiversCounter", "getReceiversCounter$kotlinx_coroutines_core", "sendException", "getSendException", "sendSegment", "sendersAndCloseStatus", "sendersCounter", "getSendersCounter$kotlinx_coroutines_core", "isClosedForReceive0", "(J)Z", "isClosedForSend0", "bufferOrRendezvousSend", "curSenders", Constant.PARAM_CANCEL, "cause", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "cancelImpl", "cancelImpl$kotlinx_coroutines_core", "cancelSuspendedReceiveRequests", "lastSegment", "checkSegmentStructureInvariants", "close", "closeLinkedList", "closeOrCancelImpl", "completeCancel", "sendersCur", "completeClose", "completeCloseOrCancel", "dropFirstElementUntilTheSpecifiedCellIsInTheBuffer", "globalCellIndex", "expandBuffer", "findSegmentBufferEnd", "id", "startFrom", "currentBufferEndCounter", "findSegmentReceive", "findSegmentSend", "hasElements", "hasElements$kotlinx_coroutines_core", "incCompletedExpandBufferAttempts", "nAttempts", "invokeCloseHandler", "invokeOnClose", "handler", "isCellNonEmpty", "segment", "index", "globalIndex", "isClosed", "sendersAndCloseStatusCur", "iterator", "Lkotlinx/coroutines/channels/ChannelIterator;", "markAllEmptyCellsAsClosed", "markCancellationStarted", "markCancelled", "markClosed", "moveSegmentBufferEndToSpecifiedOrLast", "onClosedIdempotent", "onClosedReceiveCatchingOnNoWaiterSuspend", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "onClosedReceiveOnNoWaiterSuspend", "onClosedSelectOnReceive", "onClosedSelectOnSend", "element", "(Ljava/lang/Object;Lkotlinx/coroutines/selects/SelectInstance;)V", "onClosedSend", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onClosedSendOnNoWaiterSuspend", "(Ljava/lang/Object;Lkotlinx/coroutines/CancellableContinuation;)V", "onReceiveDequeued", "onReceiveEnqueued", "processResultSelectReceive", "ignoredParam", "selectResult", "processResultSelectReceiveCatching", "processResultSelectReceiveOrNull", "processResultSelectSend", "receive", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "receiveCatching", "receiveCatching-JP2dKIU", "receiveCatchingOnNoWaiterSuspend", "r", "receiveCatchingOnNoWaiterSuspend-GKJJFZk", "(Lkotlinx/coroutines/channels/ChannelSegment;IJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "receiveImpl", "R", "waiter", "onElementRetrieved", "onSuspend", "segm", "i", "onClosed", "Lkotlin/Function0;", "onNoWaiterSuspend", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function3;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function3;)Ljava/lang/Object;", "receiveImplOnNoWaiter", "Lkotlinx/coroutines/Waiter;", "receiveOnNoWaiterSuspend", "registerSelectForReceive", "registerSelectForSend", "removeUnprocessedElements", "send", "sendBroadcast", "sendBroadcast$kotlinx_coroutines_core", "sendImpl", "onRendezvousOrBuffered", "Lkotlin/Function2;", "Lkotlin/Function4;", "s", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function4;)Ljava/lang/Object;", "sendImplOnNoWaiter", "(Lkotlinx/coroutines/channels/ChannelSegment;ILjava/lang/Object;JLkotlinx/coroutines/Waiter;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;)V", "sendOnNoWaiterSuspend", "(Lkotlinx/coroutines/channels/ChannelSegment;ILjava/lang/Object;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "shouldSendSuspend", "shouldSendSuspend$kotlinx_coroutines_core", "curSendersAndCloseStatus", "toString", "", "toStringDebug", "toStringDebug$kotlinx_coroutines_core", "tryReceive", "tryReceive-PtdJZtk", "()Ljava/lang/Object;", "trySend", "trySend-JP2dKIU", "(Ljava/lang/Object;)Ljava/lang/Object;", "updateCellExpandBuffer", "b", "updateCellExpandBufferSlow", "updateCellReceive", "updateCellReceiveSlow", "updateCellSend", "closed", "(Lkotlinx/coroutines/channels/ChannelSegment;ILjava/lang/Object;JLjava/lang/Object;Z)I", "updateCellSendSlow", "updateReceiversCounterIfLower", "value", "updateSendersCounterIfLower", "waitExpandBufferCompletion", "waitExpandBufferCompletion$kotlinx_coroutines_core", "prepareReceiverForSuspension", "prepareSenderForSuspension", "resumeReceiverOnClosedChannel", "resumeSenderOnCancelledChannel", "resumeWaiterOnClosedChannel", "receiver", "tryResumeReceiver", "(Ljava/lang/Object;Ljava/lang/Object;)Z", "tryResumeSender", "BufferedChannelIterator", "SendBroadcast", "kotlinx-coroutines-core"}, k = 1, mv = {1, 8, 0}, xi = 48)
public class BufferedChannel<E> implements Channel<E> {

    @Volatile
    private volatile Object _closeCause;

    @Volatile
    private volatile long bufferEnd;

    @Volatile
    private volatile Object bufferEndSegment;
    private final int capacity;

    @Volatile
    private volatile Object closeHandler;

    @Volatile
    private volatile long completedExpandBuffersAndPauseFlag;
    public final Function1<E, Unit> onUndeliveredElement;
    private final Function3<SelectInstance<?>, Object, Object, Function1<Throwable, Unit>> onUndeliveredElementReceiveCancellationConstructor;

    @Volatile
    private volatile Object receiveSegment;

    @Volatile
    private volatile long receivers;

    @Volatile
    private volatile Object sendSegment;

    @Volatile
    private volatile long sendersAndCloseStatus;
    private static final AtomicLongFieldUpdater sendersAndCloseStatus$FU = AtomicLongFieldUpdater.newUpdater(BufferedChannel.class, "sendersAndCloseStatus");
    private static final AtomicLongFieldUpdater receivers$FU = AtomicLongFieldUpdater.newUpdater(BufferedChannel.class, "receivers");
    private static final AtomicLongFieldUpdater bufferEnd$FU = AtomicLongFieldUpdater.newUpdater(BufferedChannel.class, "bufferEnd");
    private static final AtomicLongFieldUpdater completedExpandBuffersAndPauseFlag$FU = AtomicLongFieldUpdater.newUpdater(BufferedChannel.class, "completedExpandBuffersAndPauseFlag");
    private static final AtomicReferenceFieldUpdater sendSegment$FU = AtomicReferenceFieldUpdater.newUpdater(BufferedChannel.class, Object.class, "sendSegment");
    private static final AtomicReferenceFieldUpdater receiveSegment$FU = AtomicReferenceFieldUpdater.newUpdater(BufferedChannel.class, Object.class, "receiveSegment");
    private static final AtomicReferenceFieldUpdater bufferEndSegment$FU = AtomicReferenceFieldUpdater.newUpdater(BufferedChannel.class, Object.class, "bufferEndSegment");
    private static final AtomicReferenceFieldUpdater _closeCause$FU = AtomicReferenceFieldUpdater.newUpdater(BufferedChannel.class, Object.class, "_closeCause");
    private static final AtomicReferenceFieldUpdater closeHandler$FU = AtomicReferenceFieldUpdater.newUpdater(BufferedChannel.class, Object.class, "closeHandler");

    private final Object getAndUpdate$atomicfu(AtomicReferenceFieldUpdater atomicReferenceFieldUpdater, Function1<Object, ? extends Object> function1, Object obj) {
        Object obj2;
        do {
            obj2 = atomicReferenceFieldUpdater.get(obj);
        } while (!AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(atomicReferenceFieldUpdater, obj, obj2, function1.invoke(obj2)));
        return obj2;
    }

    public static /* synthetic */ void getOnReceive$annotations() {
    }

    public static /* synthetic */ void getOnReceiveCatching$annotations() {
    }

    public static /* synthetic */ void getOnReceiveOrNull$annotations() {
    }

    public static /* synthetic */ void getOnSend$annotations() {
    }

    private static /* synthetic */ void getOnUndeliveredElementReceiveCancellationConstructor$annotations() {
    }

    public static /* synthetic */ void isClosedForReceive$annotations() {
    }

    public static /* synthetic */ void isClosedForSend$annotations() {
    }

    public static /* synthetic */ void isEmpty$annotations() {
    }

    private final void loop$atomicfu(AtomicLongFieldUpdater atomicLongFieldUpdater, Function1<? super Long, Unit> function1, Object obj) {
        while (true) {
            function1.invoke(Long.valueOf(atomicLongFieldUpdater.get(obj)));
        }
    }

    private final void loop$atomicfu(AtomicReferenceFieldUpdater atomicReferenceFieldUpdater, Function1<Object, Unit> function1, Object obj) {
        while (true) {
            function1.invoke(atomicReferenceFieldUpdater.get(obj));
        }
    }

    private final void update$atomicfu(AtomicLongFieldUpdater atomicLongFieldUpdater, Function1<? super Long, Long> function1, Object obj) {
        long j;
        do {
            j = atomicLongFieldUpdater.get(obj);
        } while (!atomicLongFieldUpdater.compareAndSet(obj, j, function1.invoke(Long.valueOf(j)).longValue()));
    }

    protected boolean isConflatedDropOldest() {
        return false;
    }

    protected void onClosedIdempotent() {
    }

    protected void onReceiveDequeued() {
    }

    protected void onReceiveEnqueued() {
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public Object receive(Continuation<? super E> continuation) {
        return receive$suspendImpl(this, continuation);
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    /* JADX INFO: renamed from: receiveCatching-JP2dKIU, reason: not valid java name */
    public Object mo2192receiveCatchingJP2dKIU(Continuation<? super ChannelResult<? extends E>> continuation) {
        return m2190receiveCatchingJP2dKIU$suspendImpl(this, continuation);
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public Object send(E e, Continuation<? super Unit> continuation) {
        return send$suspendImpl(this, e, continuation);
    }

    public Object sendBroadcast$kotlinx_coroutines_core(E e, Continuation<? super Boolean> continuation) {
        return sendBroadcast$suspendImpl(this, e, continuation);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public BufferedChannel(int i, Function1<? super E, Unit> function1) {
        this.capacity = i;
        this.onUndeliveredElement = function1;
        if (i >= 0) {
            this.bufferEnd = BufferedChannelKt.initialBufferEnd(i);
            this.completedExpandBuffersAndPauseFlag = getBufferEndCounter();
            ChannelSegment channelSegment = new ChannelSegment(0L, null, this, 3);
            this.sendSegment = channelSegment;
            this.receiveSegment = channelSegment;
            if (isRendezvousOrUnlimited()) {
                channelSegment = BufferedChannelKt.NULL_SEGMENT;
                Intrinsics.checkNotNull(channelSegment, "null cannot be cast to non-null type kotlinx.coroutines.channels.ChannelSegment<E of kotlinx.coroutines.channels.BufferedChannel>");
            }
            this.bufferEndSegment = channelSegment;
            this.onUndeliveredElementReceiveCancellationConstructor = function1 != 0 ? (Function3) new Function3<SelectInstance<?>, Object, Object, Function1<? super Throwable, ? extends Unit>>(this) { // from class: kotlinx.coroutines.channels.BufferedChannel$onUndeliveredElementReceiveCancellationConstructor$1$1
                final /* synthetic */ BufferedChannel<E> this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                    this.this$0 = this;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Function1<Throwable, Unit> invoke(final SelectInstance<?> selectInstance, Object obj, final Object obj2) {
                    final BufferedChannel<E> bufferedChannel = this.this$0;
                    return new Function1<Throwable, Unit>() { // from class: kotlinx.coroutines.channels.BufferedChannel$onUndeliveredElementReceiveCancellationConstructor$1$1.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                            invoke2(th);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(Throwable th) {
                            if (obj2 != BufferedChannelKt.getCHANNEL_CLOSED()) {
                                OnUndeliveredElementKt.callUndeliveredElement(bufferedChannel.onUndeliveredElement, obj2, selectInstance.getContext());
                            }
                        }
                    };
                }
            } : null;
            this._closeCause = BufferedChannelKt.NO_CLOSE_CAUSE;
            return;
        }
        throw new IllegalArgumentException(("Invalid channel capacity: " + i + ", should be >=0").toString());
    }

    public /* synthetic */ BufferedChannel(int i, Function1 function1, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i2 & 2) != 0 ? null : function1);
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'trySend' method", replaceWith = @ReplaceWith(expression = "trySend(element).isSuccess", imports = {}))
    public boolean offer(E e) {
        return Channel.DefaultImpls.offer(this, e);
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'tryReceive'. Please note that the provided replacement does not rethrow channel's close cause as 'poll' did, for the precise replacement please refer to the 'poll' documentation", replaceWith = @ReplaceWith(expression = "tryReceive().getOrNull()", imports = {}))
    public E poll() {
        return (E) Channel.DefaultImpls.poll(this);
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in favor of 'receiveCatching'. Please note that the provided replacement does not rethrow channel's close cause as 'receiveOrNull' did, for the detailed replacement please refer to the 'receiveOrNull' documentation", replaceWith = @ReplaceWith(expression = "receiveCatching().getOrNull()", imports = {}))
    public Object receiveOrNull(Continuation<? super E> continuation) {
        return Channel.DefaultImpls.receiveOrNull(this, continuation);
    }

    public final long getSendersCounter$kotlinx_coroutines_core() {
        return sendersAndCloseStatus$FU.get(this) & 1152921504606846975L;
    }

    public final long getReceiversCounter$kotlinx_coroutines_core() {
        return receivers$FU.get(this);
    }

    private final long getBufferEndCounter() {
        return bufferEnd$FU.get(this);
    }

    private final boolean isRendezvousOrUnlimited() {
        long bufferEndCounter = getBufferEndCounter();
        return bufferEndCounter == 0 || bufferEndCounter == Long.MAX_VALUE;
    }

    static /* synthetic */ <E> Object send$suspendImpl(BufferedChannel<E> bufferedChannel, E e, Continuation<? super Unit> continuation) {
        ChannelSegment<E> channelSegment;
        ChannelSegment<E> channelSegment2 = (ChannelSegment) sendSegment$FU.get(bufferedChannel);
        while (true) {
            long andIncrement = sendersAndCloseStatus$FU.getAndIncrement(bufferedChannel);
            long j = andIncrement & 1152921504606846975L;
            boolean zIsClosedForSend0 = bufferedChannel.isClosedForSend0(andIncrement);
            long j2 = j / ((long) BufferedChannelKt.SEGMENT_SIZE);
            int i = (int) (j % ((long) BufferedChannelKt.SEGMENT_SIZE));
            if (channelSegment2.id != j2) {
                ChannelSegment<E> channelSegmentFindSegmentSend = bufferedChannel.findSegmentSend(j2, channelSegment2);
                if (channelSegmentFindSegmentSend != null) {
                    channelSegment = channelSegmentFindSegmentSend;
                } else if (zIsClosedForSend0) {
                    Object objOnClosedSend = bufferedChannel.onClosedSend(e, continuation);
                    if (objOnClosedSend == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                        return objOnClosedSend;
                    }
                }
            } else {
                channelSegment = channelSegment2;
            }
            int iUpdateCellSend = bufferedChannel.updateCellSend(channelSegment, i, e, j, null, zIsClosedForSend0);
            if (iUpdateCellSend == 0) {
                channelSegment.cleanPrev();
                break;
            }
            if (iUpdateCellSend == 1) {
                break;
            }
            if (iUpdateCellSend != 2) {
                if (iUpdateCellSend == 3) {
                    Object objSendOnNoWaiterSuspend = bufferedChannel.sendOnNoWaiterSuspend(channelSegment, i, e, j, continuation);
                    if (objSendOnNoWaiterSuspend == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                        return objSendOnNoWaiterSuspend;
                    }
                } else if (iUpdateCellSend != 4) {
                    if (iUpdateCellSend == 5) {
                        channelSegment.cleanPrev();
                    }
                    channelSegment2 = channelSegment;
                } else {
                    if (j < bufferedChannel.getReceiversCounter$kotlinx_coroutines_core()) {
                        channelSegment.cleanPrev();
                    }
                    Object objOnClosedSend2 = bufferedChannel.onClosedSend(e, continuation);
                    if (objOnClosedSend2 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                        return objOnClosedSend2;
                    }
                }
            } else if (!zIsClosedForSend0) {
                if (DebugKt.getASSERTIONS_ENABLED()) {
                    throw new AssertionError();
                }
            } else {
                channelSegment.onSlotCleaned();
                Object objOnClosedSend3 = bufferedChannel.onClosedSend(e, continuation);
                if (objOnClosedSend3 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    return objOnClosedSend3;
                }
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void prepareSenderForSuspension(Waiter waiter, ChannelSegment<E> channelSegment, int i) {
        waiter.invokeOnCancellation(channelSegment, i + BufferedChannelKt.SEGMENT_SIZE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onClosedSendOnNoWaiterSuspend(E element, CancellableContinuation<? super Unit> cont) {
        Function1<E, Unit> function1 = this.onUndeliveredElement;
        if (function1 != null) {
            OnUndeliveredElementKt.callUndeliveredElement(function1, element, cont.getContext());
        }
        CancellableContinuation<? super Unit> cancellableContinuation = cont;
        Throwable sendException = getSendException();
        if (DebugKt.getRECOVER_STACK_TRACES() && (cancellableContinuation instanceof CoroutineStackFrame)) {
            sendException = StackTraceRecoveryKt.recoverFromStackFrame(sendException, (CoroutineStackFrame) cancellableContinuation);
        }
        Result.Companion companion = Result.INSTANCE;
        cancellableContinuation.resumeWith(Result.m684constructorimpl(ResultKt.createFailure(sendException)));
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x00bd, code lost:
    
        return kotlinx.coroutines.channels.ChannelResult.INSTANCE.m2213successJP2dKIU(kotlin.Unit.INSTANCE);
     */
    @Override // kotlinx.coroutines.channels.SendChannel
    /* JADX INFO: renamed from: trySend-JP2dKIU */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object mo2188trySendJP2dKIU(E r15) {
        /*
            r14 = this;
            java.util.concurrent.atomic.AtomicLongFieldUpdater r0 = kotlinx.coroutines.channels.BufferedChannel.sendersAndCloseStatus$FU
            long r0 = r0.get(r14)
            boolean r0 = r14.shouldSendSuspend(r0)
            if (r0 == 0) goto L13
            kotlinx.coroutines.channels.ChannelResult$Companion r15 = kotlinx.coroutines.channels.ChannelResult.INSTANCE
            java.lang.Object r15 = r15.m2212failurePtdJZtk()
            return r15
        L13:
            kotlinx.coroutines.internal.Symbol r8 = kotlinx.coroutines.channels.BufferedChannelKt.access$getINTERRUPTED_SEND$p()
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r0 = access$getSendSegment$FU$p()
            java.lang.Object r0 = r0.get(r14)
            kotlinx.coroutines.channels.ChannelSegment r0 = (kotlinx.coroutines.channels.ChannelSegment) r0
        L21:
            java.util.concurrent.atomic.AtomicLongFieldUpdater r1 = access$getSendersAndCloseStatus$FU$p()
            long r1 = r1.getAndIncrement(r14)
            r3 = 1152921504606846975(0xfffffffffffffff, double:1.2882297539194265E-231)
            long r9 = r1 & r3
            boolean r11 = access$isClosedForSend0(r14, r1)
            int r1 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            long r1 = (long) r1
            long r1 = r9 / r1
            int r3 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            long r3 = (long) r3
            long r3 = r9 % r3
            int r12 = (int) r3
            long r3 = r0.id
            int r3 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r3 == 0) goto L50
            kotlinx.coroutines.channels.ChannelSegment r1 = access$findSegmentSend(r14, r1, r0)
            if (r1 != 0) goto L4e
            if (r11 == 0) goto L21
            goto L90
        L4e:
            r13 = r1
            goto L51
        L50:
            r13 = r0
        L51:
            r0 = r14
            r1 = r13
            r2 = r12
            r3 = r15
            r4 = r9
            r6 = r8
            r7 = r11
            int r0 = access$updateCellSend(r0, r1, r2, r3, r4, r6, r7)
            if (r0 == 0) goto Lb2
            r1 = 1
            if (r0 == r1) goto Lb5
            r1 = 2
            if (r0 == r1) goto L8b
            r1 = 3
            if (r0 == r1) goto L7f
            r1 = 4
            if (r0 == r1) goto L73
            r1 = 5
            if (r0 == r1) goto L6e
            goto L71
        L6e:
            r13.cleanPrev()
        L71:
            r0 = r13
            goto L21
        L73:
            long r0 = r14.getReceiversCounter$kotlinx_coroutines_core()
            int r15 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1))
            if (r15 >= 0) goto L90
            r13.cleanPrev()
            goto L90
        L7f:
            java.lang.IllegalStateException r15 = new java.lang.IllegalStateException
            java.lang.String r0 = "unexpected"
            java.lang.String r0 = r0.toString()
            r15.<init>(r0)
            throw r15
        L8b:
            if (r11 == 0) goto L9b
            r13.onSlotCleaned()
        L90:
            kotlinx.coroutines.channels.ChannelResult$Companion r15 = kotlinx.coroutines.channels.ChannelResult.INSTANCE
            java.lang.Throwable r0 = r14.getSendException()
            java.lang.Object r15 = r15.m2211closedJP2dKIU(r0)
            goto Lbd
        L9b:
            boolean r15 = r8 instanceof kotlinx.coroutines.Waiter
            if (r15 == 0) goto La2
            kotlinx.coroutines.Waiter r8 = (kotlinx.coroutines.Waiter) r8
            goto La3
        La2:
            r8 = 0
        La3:
            if (r8 == 0) goto La8
            access$prepareSenderForSuspension(r14, r8, r13, r12)
        La8:
            r13.onSlotCleaned()
            kotlinx.coroutines.channels.ChannelResult$Companion r15 = kotlinx.coroutines.channels.ChannelResult.INSTANCE
            java.lang.Object r15 = r15.m2212failurePtdJZtk()
            goto Lbd
        Lb2:
            r13.cleanPrev()
        Lb5:
            kotlinx.coroutines.channels.ChannelResult$Companion r15 = kotlinx.coroutines.channels.ChannelResult.INSTANCE
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            java.lang.Object r15 = r15.m2213successJP2dKIU(r0)
        Lbd:
            return r15
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.mo2188trySendJP2dKIU(java.lang.Object):java.lang.Object");
    }

    /* JADX INFO: compiled from: BufferedChannel.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u001d\u0010\b\u001a\u00020\t2\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000b2\u0006\u0010\f\u001a\u00020\rH\u0096\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000e"}, d2 = {"Lkotlinx/coroutines/channels/BufferedChannel$SendBroadcast;", "Lkotlinx/coroutines/Waiter;", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "(Lkotlinx/coroutines/CancellableContinuation;)V", "getCont", "()Lkotlinx/coroutines/CancellableContinuation;", "invokeOnCancellation", "", "segment", "Lkotlinx/coroutines/internal/Segment;", "index", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 8, 0}, xi = 48)
    private static final class SendBroadcast implements Waiter {
        private final /* synthetic */ CancellableContinuationImpl<Boolean> $$delegate_0;
        private final CancellableContinuation<Boolean> cont;

        @Override // kotlinx.coroutines.Waiter
        public void invokeOnCancellation(Segment<?> segment, int index) {
            this.$$delegate_0.invokeOnCancellation(segment, index);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public SendBroadcast(CancellableContinuation<? super Boolean> cancellableContinuation) {
            this.cont = cancellableContinuation;
            Intrinsics.checkNotNull(cancellableContinuation, "null cannot be cast to non-null type kotlinx.coroutines.CancellableContinuationImpl<kotlin.Boolean>");
            this.$$delegate_0 = (CancellableContinuationImpl) cancellableContinuation;
        }

        public final CancellableContinuation<Boolean> getCont() {
            return this.cont;
        }
    }

    public static /* synthetic */ Object sendImpl$default(BufferedChannel bufferedChannel, Object obj, Object obj2, Function0 function0, Function2 function2, Function0 function02, Function4 function4, int i, Object obj3) {
        ChannelSegment channelSegment;
        if (obj3 != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: sendImpl");
        }
        Function4 function42 = (i & 32) != 0 ? new Function4() { // from class: kotlinx.coroutines.channels.BufferedChannel.sendImpl.1
            @Override // kotlin.jvm.functions.Function4
            public /* bridge */ /* synthetic */ Object invoke(Object obj4, Object obj5, Object obj6, Object obj7) {
                return invoke((ChannelSegment<Object>) obj4, ((Number) obj5).intValue(), obj6, ((Number) obj7).longValue());
            }

            public final Void invoke(ChannelSegment<E> channelSegment2, int i2, E e, long j) {
                throw new IllegalStateException("unexpected".toString());
            }
        } : function4;
        ChannelSegment channelSegment2 = (ChannelSegment) sendSegment$FU.get(bufferedChannel);
        while (true) {
            long andIncrement = sendersAndCloseStatus$FU.getAndIncrement(bufferedChannel);
            long j = andIncrement & 1152921504606846975L;
            boolean zIsClosedForSend0 = bufferedChannel.isClosedForSend0(andIncrement);
            long j2 = j / ((long) BufferedChannelKt.SEGMENT_SIZE);
            int i2 = (int) (j % ((long) BufferedChannelKt.SEGMENT_SIZE));
            if (channelSegment2.id != j2) {
                ChannelSegment channelSegmentFindSegmentSend = bufferedChannel.findSegmentSend(j2, channelSegment2);
                if (channelSegmentFindSegmentSend != null) {
                    channelSegment = channelSegmentFindSegmentSend;
                } else if (zIsClosedForSend0) {
                    return function02.invoke();
                }
            } else {
                channelSegment = channelSegment2;
            }
            int iUpdateCellSend = bufferedChannel.updateCellSend(channelSegment, i2, obj, j, obj2, zIsClosedForSend0);
            if (iUpdateCellSend == 0) {
                channelSegment.cleanPrev();
                return function0.invoke();
            }
            if (iUpdateCellSend == 1) {
                return function0.invoke();
            }
            if (iUpdateCellSend == 2) {
                if (zIsClosedForSend0) {
                    channelSegment.onSlotCleaned();
                    return function02.invoke();
                }
                Waiter waiter = obj2 instanceof Waiter ? (Waiter) obj2 : null;
                if (waiter != null) {
                    bufferedChannel.prepareSenderForSuspension(waiter, channelSegment, i2);
                }
                return function2.invoke(channelSegment, Integer.valueOf(i2));
            }
            if (iUpdateCellSend == 3) {
                return function42.invoke(channelSegment, Integer.valueOf(i2), obj, Long.valueOf(j));
            }
            if (iUpdateCellSend == 4) {
                if (j < bufferedChannel.getReceiversCounter$kotlinx_coroutines_core()) {
                    channelSegment.cleanPrev();
                }
                return function02.invoke();
            }
            if (iUpdateCellSend == 5) {
                channelSegment.cleanPrev();
            }
            channelSegment2 = channelSegment;
        }
    }

    protected final <R> R sendImpl(E element, Object waiter, Function0<? extends R> onRendezvousOrBuffered, Function2<? super ChannelSegment<E>, ? super Integer, ? extends R> onSuspend, Function0<? extends R> onClosed, Function4<? super ChannelSegment<E>, ? super Integer, ? super E, ? super Long, ? extends R> onNoWaiterSuspend) {
        ChannelSegment channelSegment;
        ChannelSegment channelSegment2 = (ChannelSegment) sendSegment$FU.get(this);
        while (true) {
            long andIncrement = sendersAndCloseStatus$FU.getAndIncrement(this);
            long j = andIncrement & 1152921504606846975L;
            boolean zIsClosedForSend0 = isClosedForSend0(andIncrement);
            long j2 = j / ((long) BufferedChannelKt.SEGMENT_SIZE);
            int i = (int) (j % ((long) BufferedChannelKt.SEGMENT_SIZE));
            if (channelSegment2.id != j2) {
                ChannelSegment channelSegmentFindSegmentSend = findSegmentSend(j2, channelSegment2);
                if (channelSegmentFindSegmentSend != null) {
                    channelSegment = channelSegmentFindSegmentSend;
                } else if (zIsClosedForSend0) {
                    return onClosed.invoke();
                }
            } else {
                channelSegment = channelSegment2;
            }
            int iUpdateCellSend = updateCellSend(channelSegment, i, element, j, waiter, zIsClosedForSend0);
            if (iUpdateCellSend == 0) {
                channelSegment.cleanPrev();
                return onRendezvousOrBuffered.invoke();
            }
            if (iUpdateCellSend == 1) {
                return onRendezvousOrBuffered.invoke();
            }
            if (iUpdateCellSend == 2) {
                if (zIsClosedForSend0) {
                    channelSegment.onSlotCleaned();
                    return onClosed.invoke();
                }
                Waiter waiter2 = waiter instanceof Waiter ? (Waiter) waiter : null;
                if (waiter2 != null) {
                    prepareSenderForSuspension(waiter2, channelSegment, i);
                }
                return onSuspend.invoke(channelSegment, Integer.valueOf(i));
            }
            if (iUpdateCellSend == 3) {
                return onNoWaiterSuspend.invoke(channelSegment, Integer.valueOf(i), element, Long.valueOf(j));
            }
            if (iUpdateCellSend == 4) {
                if (j < getReceiversCounter$kotlinx_coroutines_core()) {
                    channelSegment.cleanPrev();
                }
                return onClosed.invoke();
            }
            if (iUpdateCellSend == 5) {
                channelSegment.cleanPrev();
            }
            channelSegment2 = channelSegment;
        }
    }

    private final void sendImplOnNoWaiter(ChannelSegment<E> segment, int index, E element, long s, Waiter waiter, Function0<Unit> onRendezvousOrBuffered, Function0<Unit> onClosed) {
        ChannelSegment channelSegment;
        Waiter waiter2 = waiter;
        int iUpdateCellSend = updateCellSend(segment, index, element, s, waiter, false);
        if (iUpdateCellSend == 0) {
            segment.cleanPrev();
            onRendezvousOrBuffered.invoke();
            return;
        }
        if (iUpdateCellSend == 1) {
            onRendezvousOrBuffered.invoke();
            return;
        }
        if (iUpdateCellSend == 2) {
            prepareSenderForSuspension(waiter2, segment, index);
            return;
        }
        if (iUpdateCellSend == 4) {
            if (s < getReceiversCounter$kotlinx_coroutines_core()) {
                segment.cleanPrev();
            }
            onClosed.invoke();
            return;
        }
        if (iUpdateCellSend == 5) {
            segment.cleanPrev();
            ChannelSegment channelSegment2 = (ChannelSegment) sendSegment$FU.get(this);
            while (true) {
                long andIncrement = sendersAndCloseStatus$FU.getAndIncrement(this);
                long j = andIncrement & 1152921504606846975L;
                boolean zIsClosedForSend0 = isClosedForSend0(andIncrement);
                long j2 = j / ((long) BufferedChannelKt.SEGMENT_SIZE);
                int i = (int) (j % ((long) BufferedChannelKt.SEGMENT_SIZE));
                if (channelSegment2.id != j2) {
                    ChannelSegment channelSegmentFindSegmentSend = findSegmentSend(j2, channelSegment2);
                    if (channelSegmentFindSegmentSend != null) {
                        channelSegment = channelSegmentFindSegmentSend;
                    } else if (zIsClosedForSend0) {
                        onClosed.invoke();
                        return;
                    }
                } else {
                    channelSegment = channelSegment2;
                }
                ChannelSegment channelSegment3 = channelSegment;
                int iUpdateCellSend2 = updateCellSend(channelSegment, i, element, j, waiter, zIsClosedForSend0);
                if (iUpdateCellSend2 == 0) {
                    channelSegment3.cleanPrev();
                    onRendezvousOrBuffered.invoke();
                    return;
                }
                if (iUpdateCellSend2 == 1) {
                    onRendezvousOrBuffered.invoke();
                    return;
                }
                if (iUpdateCellSend2 == 2) {
                    if (zIsClosedForSend0) {
                        channelSegment3.onSlotCleaned();
                        onClosed.invoke();
                        return;
                    }
                    if (!(waiter2 instanceof Waiter)) {
                        waiter2 = null;
                    }
                    if (waiter2 != null) {
                        prepareSenderForSuspension(waiter2, channelSegment3, i);
                    }
                    Unit unit = Unit.INSTANCE;
                    return;
                }
                if (iUpdateCellSend2 == 3) {
                    throw new IllegalStateException("unexpected".toString());
                }
                if (iUpdateCellSend2 == 4) {
                    if (j < getReceiversCounter$kotlinx_coroutines_core()) {
                        channelSegment3.cleanPrev();
                    }
                    onClosed.invoke();
                    return;
                } else {
                    if (iUpdateCellSend2 == 5) {
                        channelSegment3.cleanPrev();
                    }
                    channelSegment2 = channelSegment3;
                }
            }
        } else {
            throw new IllegalStateException("unexpected".toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int updateCellSend(ChannelSegment<E> segment, int index, E element, long s, Object waiter, boolean closed) {
        segment.storeElement$kotlinx_coroutines_core(index, element);
        if (closed) {
            return updateCellSendSlow(segment, index, element, s, waiter, closed);
        }
        Object state$kotlinx_coroutines_core = segment.getState$kotlinx_coroutines_core(index);
        if (state$kotlinx_coroutines_core == null) {
            if (bufferOrRendezvousSend(s)) {
                if (segment.casState$kotlinx_coroutines_core(index, null, BufferedChannelKt.BUFFERED)) {
                    return 1;
                }
            } else {
                if (waiter == null) {
                    return 3;
                }
                if (segment.casState$kotlinx_coroutines_core(index, null, waiter)) {
                    return 2;
                }
            }
        } else if (state$kotlinx_coroutines_core instanceof Waiter) {
            segment.cleanElement$kotlinx_coroutines_core(index);
            if (tryResumeReceiver(state$kotlinx_coroutines_core, element)) {
                segment.setState$kotlinx_coroutines_core(index, BufferedChannelKt.DONE_RCV);
                onReceiveDequeued();
                return 0;
            }
            if (segment.getAndSetState$kotlinx_coroutines_core(index, BufferedChannelKt.INTERRUPTED_RCV) != BufferedChannelKt.INTERRUPTED_RCV) {
                segment.onCancelledRequest(index, true);
            }
            return 5;
        }
        return updateCellSendSlow(segment, index, element, s, waiter, closed);
    }

    private final int updateCellSendSlow(ChannelSegment<E> segment, int index, E element, long s, Object waiter, boolean closed) {
        while (true) {
            Object state$kotlinx_coroutines_core = segment.getState$kotlinx_coroutines_core(index);
            if (state$kotlinx_coroutines_core != null) {
                if (state$kotlinx_coroutines_core != BufferedChannelKt.IN_BUFFER) {
                    if (state$kotlinx_coroutines_core != BufferedChannelKt.INTERRUPTED_RCV) {
                        if (state$kotlinx_coroutines_core == BufferedChannelKt.POISONED) {
                            segment.cleanElement$kotlinx_coroutines_core(index);
                            return 5;
                        }
                        if (state$kotlinx_coroutines_core == BufferedChannelKt.getCHANNEL_CLOSED()) {
                            segment.cleanElement$kotlinx_coroutines_core(index);
                            completeCloseOrCancel();
                            return 4;
                        }
                        if (DebugKt.getASSERTIONS_ENABLED()) {
                            if (!((state$kotlinx_coroutines_core instanceof Waiter) || (state$kotlinx_coroutines_core instanceof WaiterEB))) {
                                throw new AssertionError();
                            }
                        }
                        segment.cleanElement$kotlinx_coroutines_core(index);
                        if (state$kotlinx_coroutines_core instanceof WaiterEB) {
                            state$kotlinx_coroutines_core = ((WaiterEB) state$kotlinx_coroutines_core).waiter;
                        }
                        if (tryResumeReceiver(state$kotlinx_coroutines_core, element)) {
                            segment.setState$kotlinx_coroutines_core(index, BufferedChannelKt.DONE_RCV);
                            onReceiveDequeued();
                            return 0;
                        }
                        if (segment.getAndSetState$kotlinx_coroutines_core(index, BufferedChannelKt.INTERRUPTED_RCV) != BufferedChannelKt.INTERRUPTED_RCV) {
                            segment.onCancelledRequest(index, true);
                        }
                        return 5;
                    }
                    segment.cleanElement$kotlinx_coroutines_core(index);
                    return 5;
                }
                if (segment.casState$kotlinx_coroutines_core(index, state$kotlinx_coroutines_core, BufferedChannelKt.BUFFERED)) {
                    return 1;
                }
            } else if (!bufferOrRendezvousSend(s) || closed) {
                if (closed) {
                    if (segment.casState$kotlinx_coroutines_core(index, null, BufferedChannelKt.INTERRUPTED_SEND)) {
                        segment.onCancelledRequest(index, false);
                        return 4;
                    }
                } else {
                    if (waiter == null) {
                        return 3;
                    }
                    if (segment.casState$kotlinx_coroutines_core(index, null, waiter)) {
                        return 2;
                    }
                }
            } else if (segment.casState$kotlinx_coroutines_core(index, null, BufferedChannelKt.BUFFERED)) {
                return 1;
            }
        }
    }

    private final boolean shouldSendSuspend(long curSendersAndCloseStatus) {
        if (isClosedForSend0(curSendersAndCloseStatus)) {
            return false;
        }
        return !bufferOrRendezvousSend(curSendersAndCloseStatus & 1152921504606846975L);
    }

    private final boolean bufferOrRendezvousSend(long curSenders) {
        return curSenders < getBufferEndCounter() || curSenders < getReceiversCounter$kotlinx_coroutines_core() + ((long) this.capacity);
    }

    public boolean shouldSendSuspend$kotlinx_coroutines_core() {
        return shouldSendSuspend(sendersAndCloseStatus$FU.get(this));
    }

    private final boolean tryResumeReceiver(Object obj, E e) {
        if (obj instanceof SelectInstance) {
            return ((SelectInstance) obj).trySelect(this, e);
        }
        if (obj instanceof ReceiveCatching) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlinx.coroutines.channels.ReceiveCatching<E of kotlinx.coroutines.channels.BufferedChannel>");
            ReceiveCatching receiveCatching = (ReceiveCatching) obj;
            CancellableContinuationImpl<ChannelResult<? extends E>> cancellableContinuationImpl = receiveCatching.cont;
            ChannelResult channelResultM2198boximpl = ChannelResult.m2198boximpl(ChannelResult.INSTANCE.m2213successJP2dKIU(e));
            Function1<E, Unit> function1 = this.onUndeliveredElement;
            return BufferedChannelKt.tryResume0(cancellableContinuationImpl, channelResultM2198boximpl, function1 != null ? OnUndeliveredElementKt.bindCancellationFun(function1, e, receiveCatching.cont.getContext()) : null);
        }
        if (obj instanceof BufferedChannelIterator) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlinx.coroutines.channels.BufferedChannel.BufferedChannelIterator<E of kotlinx.coroutines.channels.BufferedChannel>");
            return ((BufferedChannelIterator) obj).tryResumeHasNext(e);
        }
        if (obj instanceof CancellableContinuation) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlinx.coroutines.CancellableContinuation<E of kotlinx.coroutines.channels.BufferedChannel>");
            CancellableContinuation cancellableContinuation = (CancellableContinuation) obj;
            Function1<E, Unit> function12 = this.onUndeliveredElement;
            return BufferedChannelKt.tryResume0(cancellableContinuation, e, function12 != null ? OnUndeliveredElementKt.bindCancellationFun(function12, e, cancellableContinuation.getContext()) : null);
        }
        throw new IllegalStateException(("Unexpected receiver type: " + obj).toString());
    }

    static /* synthetic */ <E> Object receive$suspendImpl(BufferedChannel<E> bufferedChannel, Continuation<? super E> continuation) throws Throwable {
        ChannelSegment<E> channelSegment;
        ChannelSegment<E> channelSegment2 = (ChannelSegment) receiveSegment$FU.get(bufferedChannel);
        while (!bufferedChannel.isClosedForReceive()) {
            long andIncrement = receivers$FU.getAndIncrement(bufferedChannel);
            long j = andIncrement / ((long) BufferedChannelKt.SEGMENT_SIZE);
            int i = (int) (andIncrement % ((long) BufferedChannelKt.SEGMENT_SIZE));
            if (channelSegment2.id != j) {
                ChannelSegment<E> channelSegmentFindSegmentReceive = bufferedChannel.findSegmentReceive(j, channelSegment2);
                if (channelSegmentFindSegmentReceive == null) {
                    continue;
                } else {
                    channelSegment = channelSegmentFindSegmentReceive;
                }
            } else {
                channelSegment = channelSegment2;
            }
            Object objUpdateCellReceive = bufferedChannel.updateCellReceive(channelSegment, i, andIncrement, null);
            if (objUpdateCellReceive != BufferedChannelKt.SUSPEND) {
                if (objUpdateCellReceive != BufferedChannelKt.FAILED) {
                    if (objUpdateCellReceive == BufferedChannelKt.SUSPEND_NO_WAITER) {
                        return bufferedChannel.receiveOnNoWaiterSuspend(channelSegment, i, andIncrement, continuation);
                    }
                    channelSegment.cleanPrev();
                    return objUpdateCellReceive;
                }
                if (andIncrement < bufferedChannel.getSendersCounter$kotlinx_coroutines_core()) {
                    channelSegment.cleanPrev();
                }
                channelSegment2 = channelSegment;
            } else {
                throw new IllegalStateException("unexpected".toString());
            }
        }
        throw StackTraceRecoveryKt.recoverStackTrace(bufferedChannel.getReceiveException());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void prepareReceiverForSuspension(Waiter waiter, ChannelSegment<E> channelSegment, int i) {
        onReceiveEnqueued();
        waiter.invokeOnCancellation(channelSegment, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onClosedReceiveOnNoWaiterSuspend(CancellableContinuation<? super E> cont) {
        Result.Companion companion = Result.INSTANCE;
        cont.resumeWith(Result.m684constructorimpl(ResultKt.createFailure(getReceiveException())));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /* JADX INFO: renamed from: receiveCatching-JP2dKIU$suspendImpl, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static /* synthetic */ <E> java.lang.Object m2190receiveCatchingJP2dKIU$suspendImpl(kotlinx.coroutines.channels.BufferedChannel<E> r14, kotlin.coroutines.Continuation<? super kotlinx.coroutines.channels.ChannelResult<? extends E>> r15) throws java.lang.Throwable {
        /*
            boolean r0 = r15 instanceof kotlinx.coroutines.channels.BufferedChannel$receiveCatching$1
            if (r0 == 0) goto L14
            r0 = r15
            kotlinx.coroutines.channels.BufferedChannel$receiveCatching$1 r0 = (kotlinx.coroutines.channels.BufferedChannel$receiveCatching$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r15 = r0.label
            int r15 = r15 - r2
            r0.label = r15
            goto L19
        L14:
            kotlinx.coroutines.channels.BufferedChannel$receiveCatching$1 r0 = new kotlinx.coroutines.channels.BufferedChannel$receiveCatching$1
            r0.<init>(r14, r15)
        L19:
            r6 = r0
            java.lang.Object r15 = r6.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r6.label
            r2 = 1
            if (r1 == 0) goto L3a
            if (r1 != r2) goto L32
            kotlin.ResultKt.throwOnFailure(r15)
            kotlinx.coroutines.channels.ChannelResult r15 = (kotlinx.coroutines.channels.ChannelResult) r15
            java.lang.Object r14 = r15.getHolder()
            goto Lb9
        L32:
            java.lang.IllegalStateException r14 = new java.lang.IllegalStateException
            java.lang.String r15 = "call to 'resume' before 'invoke' with coroutine"
            r14.<init>(r15)
            throw r14
        L3a:
            kotlin.ResultKt.throwOnFailure(r15)
            r15 = 0
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r1 = access$getReceiveSegment$FU$p()
            java.lang.Object r1 = r1.get(r14)
            kotlinx.coroutines.channels.ChannelSegment r1 = (kotlinx.coroutines.channels.ChannelSegment) r1
        L48:
            boolean r3 = r14.isClosedForReceive()
            if (r3 == 0) goto L5a
            kotlinx.coroutines.channels.ChannelResult$Companion r15 = kotlinx.coroutines.channels.ChannelResult.INSTANCE
            java.lang.Throwable r14 = r14.getCloseCause()
            java.lang.Object r14 = r15.m2211closedJP2dKIU(r14)
            goto Lb9
        L5a:
            java.util.concurrent.atomic.AtomicLongFieldUpdater r3 = access$getReceivers$FU$p()
            long r4 = r3.getAndIncrement(r14)
            int r3 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            long r7 = (long) r3
            long r7 = r4 / r7
            int r3 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            long r9 = (long) r3
            long r9 = r4 % r9
            int r3 = (int) r9
            long r9 = r1.id
            int r9 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
            if (r9 == 0) goto L7c
            kotlinx.coroutines.channels.ChannelSegment r7 = access$findSegmentReceive(r14, r7, r1)
            if (r7 != 0) goto L7a
            goto L48
        L7a:
            r13 = r7
            goto L7d
        L7c:
            r13 = r1
        L7d:
            r7 = r14
            r8 = r13
            r9 = r3
            r10 = r4
            r12 = r15
            java.lang.Object r1 = access$updateCellReceive(r7, r8, r9, r10, r12)
            kotlinx.coroutines.internal.Symbol r7 = kotlinx.coroutines.channels.BufferedChannelKt.access$getSUSPEND$p()
            if (r1 == r7) goto Lba
            kotlinx.coroutines.internal.Symbol r7 = kotlinx.coroutines.channels.BufferedChannelKt.access$getFAILED$p()
            if (r1 != r7) goto L9f
            long r7 = r14.getSendersCounter$kotlinx_coroutines_core()
            int r1 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
            if (r1 >= 0) goto L9d
            r13.cleanPrev()
        L9d:
            r1 = r13
            goto L48
        L9f:
            kotlinx.coroutines.internal.Symbol r15 = kotlinx.coroutines.channels.BufferedChannelKt.access$getSUSPEND_NO_WAITER$p()
            if (r1 != r15) goto Lb0
            r6.label = r2
            r1 = r14
            r2 = r13
            java.lang.Object r14 = r1.m2191receiveCatchingOnNoWaiterSuspendGKJJFZk(r2, r3, r4, r6)
            if (r14 != r0) goto Lb9
            return r0
        Lb0:
            r13.cleanPrev()
            kotlinx.coroutines.channels.ChannelResult$Companion r14 = kotlinx.coroutines.channels.ChannelResult.INSTANCE
            java.lang.Object r14 = r14.m2213successJP2dKIU(r1)
        Lb9:
            return r14
        Lba:
            java.lang.IllegalStateException r14 = new java.lang.IllegalStateException
            java.lang.String r15 = "unexpected"
            java.lang.String r15 = r15.toString()
            r14.<init>(r15)
            throw r14
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.m2190receiveCatchingJP2dKIU$suspendImpl(kotlinx.coroutines.channels.BufferedChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001e  */
    /* JADX INFO: renamed from: receiveCatchingOnNoWaiterSuspend-GKJJFZk, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object m2191receiveCatchingOnNoWaiterSuspendGKJJFZk(kotlinx.coroutines.channels.ChannelSegment<E> r17, int r18, long r19, kotlin.coroutines.Continuation<? super kotlinx.coroutines.channels.ChannelResult<? extends E>> r21) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 363
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.m2191receiveCatchingOnNoWaiterSuspendGKJJFZk(kotlinx.coroutines.channels.ChannelSegment, int, long, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onClosedReceiveCatchingOnNoWaiterSuspend(CancellableContinuation<? super ChannelResult<? extends E>> cont) {
        Result.Companion companion = Result.INSTANCE;
        cont.resumeWith(Result.m684constructorimpl(ChannelResult.m2198boximpl(ChannelResult.INSTANCE.m2211closedJP2dKIU(getCloseCause()))));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlinx.coroutines.channels.ReceiveChannel
    /* JADX INFO: renamed from: tryReceive-PtdJZtk, reason: not valid java name */
    public Object mo2193tryReceivePtdJZtk() {
        ChannelSegment channelSegment;
        long j = receivers$FU.get(this);
        long j2 = sendersAndCloseStatus$FU.get(this);
        if (isClosedForReceive0(j2)) {
            return ChannelResult.INSTANCE.m2211closedJP2dKIU(getCloseCause());
        }
        if (j < (j2 & 1152921504606846975L)) {
            Object obj = BufferedChannelKt.INTERRUPTED_RCV;
            ChannelSegment channelSegment2 = (ChannelSegment) receiveSegment$FU.get(this);
            while (!isClosedForReceive()) {
                long andIncrement = receivers$FU.getAndIncrement(this);
                long j3 = andIncrement / ((long) BufferedChannelKt.SEGMENT_SIZE);
                int i = (int) (andIncrement % ((long) BufferedChannelKt.SEGMENT_SIZE));
                if (channelSegment2.id != j3) {
                    ChannelSegment channelSegmentFindSegmentReceive = findSegmentReceive(j3, channelSegment2);
                    if (channelSegmentFindSegmentReceive == null) {
                        continue;
                    } else {
                        channelSegment = channelSegmentFindSegmentReceive;
                    }
                } else {
                    channelSegment = channelSegment2;
                }
                Object objUpdateCellReceive = updateCellReceive(channelSegment, i, andIncrement, obj);
                if (objUpdateCellReceive != BufferedChannelKt.SUSPEND) {
                    if (objUpdateCellReceive != BufferedChannelKt.FAILED) {
                        if (objUpdateCellReceive == BufferedChannelKt.SUSPEND_NO_WAITER) {
                            throw new IllegalStateException("unexpected".toString());
                        }
                        channelSegment.cleanPrev();
                        return ChannelResult.INSTANCE.m2213successJP2dKIU(objUpdateCellReceive);
                    }
                    if (andIncrement < getSendersCounter$kotlinx_coroutines_core()) {
                        channelSegment.cleanPrev();
                    }
                    channelSegment2 = channelSegment;
                } else {
                    Waiter waiter = obj instanceof Waiter ? (Waiter) obj : null;
                    if (waiter != null) {
                        prepareReceiverForSuspension(waiter, channelSegment, i);
                    }
                    waitExpandBufferCompletion$kotlinx_coroutines_core(andIncrement);
                    channelSegment.onSlotCleaned();
                    return ChannelResult.INSTANCE.m2212failurePtdJZtk();
                }
            }
            return ChannelResult.INSTANCE.m2211closedJP2dKIU(getCloseCause());
        }
        return ChannelResult.INSTANCE.m2212failurePtdJZtk();
    }

    protected final void dropFirstElementUntilTheSpecifiedCellIsInTheBuffer(long globalCellIndex) {
        UndeliveredElementException undeliveredElementExceptionCallUndeliveredElementCatchingException$default;
        if (DebugKt.getASSERTIONS_ENABLED() && !isConflatedDropOldest()) {
            throw new AssertionError();
        }
        ChannelSegment<E> channelSegment = (ChannelSegment) receiveSegment$FU.get(this);
        while (true) {
            AtomicLongFieldUpdater atomicLongFieldUpdater = receivers$FU;
            long j = atomicLongFieldUpdater.get(this);
            if (globalCellIndex < Math.max(((long) this.capacity) + j, getBufferEndCounter())) {
                return;
            }
            if (atomicLongFieldUpdater.compareAndSet(this, j, j + 1)) {
                long j2 = j / ((long) BufferedChannelKt.SEGMENT_SIZE);
                int i = (int) (j % ((long) BufferedChannelKt.SEGMENT_SIZE));
                if (channelSegment.id != j2) {
                    ChannelSegment<E> channelSegmentFindSegmentReceive = findSegmentReceive(j2, channelSegment);
                    if (channelSegmentFindSegmentReceive == null) {
                        continue;
                    } else {
                        channelSegment = channelSegmentFindSegmentReceive;
                    }
                }
                Object objUpdateCellReceive = updateCellReceive(channelSegment, i, j, null);
                if (objUpdateCellReceive != BufferedChannelKt.FAILED) {
                    channelSegment.cleanPrev();
                    Function1<E, Unit> function1 = this.onUndeliveredElement;
                    if (function1 != null && (undeliveredElementExceptionCallUndeliveredElementCatchingException$default = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default(function1, objUpdateCellReceive, null, 2, null)) != null) {
                        throw undeliveredElementExceptionCallUndeliveredElementCatchingException$default;
                    }
                } else if (j < getSendersCounter$kotlinx_coroutines_core()) {
                    channelSegment.cleanPrev();
                }
            }
        }
    }

    static /* synthetic */ Object receiveImpl$default(BufferedChannel bufferedChannel, Object obj, Function1 function1, Function3 function3, Function0 function0, Function3 function32, int i, Object obj2) {
        if (obj2 != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: receiveImpl");
        }
        if ((i & 16) != 0) {
            function32 = new Function3() { // from class: kotlinx.coroutines.channels.BufferedChannel.receiveImpl.1
                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ Object invoke(Object obj3, Object obj4, Object obj5) {
                    return invoke((ChannelSegment) obj3, ((Number) obj4).intValue(), ((Number) obj5).longValue());
                }

                public final Void invoke(ChannelSegment<E> channelSegment, int i2, long j) {
                    throw new IllegalStateException("unexpected".toString());
                }
            };
        }
        ChannelSegment channelSegment = (ChannelSegment) receiveSegment$FU.get(bufferedChannel);
        while (!bufferedChannel.isClosedForReceive()) {
            long andIncrement = receivers$FU.getAndIncrement(bufferedChannel);
            long j = andIncrement / ((long) BufferedChannelKt.SEGMENT_SIZE);
            int i2 = (int) (andIncrement % ((long) BufferedChannelKt.SEGMENT_SIZE));
            if (channelSegment.id != j) {
                ChannelSegment channelSegmentFindSegmentReceive = bufferedChannel.findSegmentReceive(j, channelSegment);
                if (channelSegmentFindSegmentReceive == null) {
                    continue;
                } else {
                    channelSegment = channelSegmentFindSegmentReceive;
                }
            }
            Object objUpdateCellReceive = bufferedChannel.updateCellReceive(channelSegment, i2, andIncrement, obj);
            if (objUpdateCellReceive != BufferedChannelKt.SUSPEND) {
                if (objUpdateCellReceive != BufferedChannelKt.FAILED) {
                    if (objUpdateCellReceive == BufferedChannelKt.SUSPEND_NO_WAITER) {
                        return function32.invoke(channelSegment, Integer.valueOf(i2), Long.valueOf(andIncrement));
                    }
                    channelSegment.cleanPrev();
                    return function1.invoke(objUpdateCellReceive);
                }
                if (andIncrement < bufferedChannel.getSendersCounter$kotlinx_coroutines_core()) {
                    channelSegment.cleanPrev();
                }
            } else {
                Waiter waiter = obj instanceof Waiter ? (Waiter) obj : null;
                if (waiter != null) {
                    bufferedChannel.prepareReceiverForSuspension(waiter, channelSegment, i2);
                }
                return function3.invoke(channelSegment, Integer.valueOf(i2), Long.valueOf(andIncrement));
            }
        }
        return function0.invoke();
    }

    private final <R> R receiveImpl(Object waiter, Function1<? super E, ? extends R> onElementRetrieved, Function3<? super ChannelSegment<E>, ? super Integer, ? super Long, ? extends R> onSuspend, Function0<? extends R> onClosed, Function3<? super ChannelSegment<E>, ? super Integer, ? super Long, ? extends R> onNoWaiterSuspend) {
        ChannelSegment channelSegment;
        ChannelSegment channelSegment2 = (ChannelSegment) receiveSegment$FU.get(this);
        while (!isClosedForReceive()) {
            long andIncrement = receivers$FU.getAndIncrement(this);
            long j = andIncrement / ((long) BufferedChannelKt.SEGMENT_SIZE);
            int i = (int) (andIncrement % ((long) BufferedChannelKt.SEGMENT_SIZE));
            if (channelSegment2.id != j) {
                ChannelSegment channelSegmentFindSegmentReceive = findSegmentReceive(j, channelSegment2);
                if (channelSegmentFindSegmentReceive == null) {
                    continue;
                } else {
                    channelSegment = channelSegmentFindSegmentReceive;
                }
            } else {
                channelSegment = channelSegment2;
            }
            Symbol symbol = (Object) updateCellReceive(channelSegment, i, andIncrement, waiter);
            if (symbol != BufferedChannelKt.SUSPEND) {
                if (symbol != BufferedChannelKt.FAILED) {
                    if (symbol == BufferedChannelKt.SUSPEND_NO_WAITER) {
                        return onNoWaiterSuspend.invoke(channelSegment, Integer.valueOf(i), Long.valueOf(andIncrement));
                    }
                    channelSegment.cleanPrev();
                    return onElementRetrieved.invoke(symbol);
                }
                if (andIncrement < getSendersCounter$kotlinx_coroutines_core()) {
                    channelSegment.cleanPrev();
                }
                channelSegment2 = channelSegment;
            } else {
                Waiter waiter2 = waiter instanceof Waiter ? (Waiter) waiter : null;
                if (waiter2 != null) {
                    prepareReceiverForSuspension(waiter2, channelSegment, i);
                }
                return onSuspend.invoke(channelSegment, Integer.valueOf(i), Long.valueOf(andIncrement));
            }
        }
        return onClosed.invoke();
    }

    private final void receiveImplOnNoWaiter(ChannelSegment<E> segment, int index, long r, Waiter waiter, Function1<? super E, Unit> onElementRetrieved, Function0<Unit> onClosed) {
        Symbol symbol = (Object) updateCellReceive(segment, index, r, waiter);
        if (symbol != BufferedChannelKt.SUSPEND) {
            if (symbol != BufferedChannelKt.FAILED) {
                segment.cleanPrev();
                onElementRetrieved.invoke(symbol);
                return;
            }
            if (r < getSendersCounter$kotlinx_coroutines_core()) {
                segment.cleanPrev();
            }
            ChannelSegment channelSegment = (ChannelSegment) receiveSegment$FU.get(this);
            while (!isClosedForReceive()) {
                long andIncrement = receivers$FU.getAndIncrement(this);
                long j = andIncrement / ((long) BufferedChannelKt.SEGMENT_SIZE);
                int i = (int) (andIncrement % ((long) BufferedChannelKt.SEGMENT_SIZE));
                if (channelSegment.id != j) {
                    ChannelSegment channelSegmentFindSegmentReceive = findSegmentReceive(j, channelSegment);
                    if (channelSegmentFindSegmentReceive == null) {
                        continue;
                    } else {
                        channelSegment = channelSegmentFindSegmentReceive;
                    }
                }
                Symbol symbol2 = (Object) updateCellReceive(channelSegment, i, andIncrement, waiter);
                if (symbol2 != BufferedChannelKt.SUSPEND) {
                    if (symbol2 != BufferedChannelKt.FAILED) {
                        if (symbol2 == BufferedChannelKt.SUSPEND_NO_WAITER) {
                            throw new IllegalStateException("unexpected".toString());
                        }
                        channelSegment.cleanPrev();
                        onElementRetrieved.invoke(symbol2);
                        return;
                    }
                    if (andIncrement < getSendersCounter$kotlinx_coroutines_core()) {
                        channelSegment.cleanPrev();
                    }
                } else {
                    if (!(waiter instanceof Waiter)) {
                        waiter = null;
                    }
                    if (waiter != null) {
                        prepareReceiverForSuspension(waiter, channelSegment, i);
                    }
                    Unit unit = Unit.INSTANCE;
                    return;
                }
            }
            onClosed.invoke();
            return;
        }
        prepareReceiverForSuspension(waiter, segment, index);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object updateCellReceive(ChannelSegment<E> segment, int index, long r, Object waiter) {
        Object state$kotlinx_coroutines_core = segment.getState$kotlinx_coroutines_core(index);
        if (state$kotlinx_coroutines_core == null) {
            if (r >= (sendersAndCloseStatus$FU.get(this) & 1152921504606846975L)) {
                if (waiter == null) {
                    return BufferedChannelKt.SUSPEND_NO_WAITER;
                }
                if (segment.casState$kotlinx_coroutines_core(index, state$kotlinx_coroutines_core, waiter)) {
                    expandBuffer();
                    return BufferedChannelKt.SUSPEND;
                }
            }
        } else if (state$kotlinx_coroutines_core == BufferedChannelKt.BUFFERED && segment.casState$kotlinx_coroutines_core(index, state$kotlinx_coroutines_core, BufferedChannelKt.DONE_RCV)) {
            expandBuffer();
            return segment.retrieveElement$kotlinx_coroutines_core(index);
        }
        return updateCellReceiveSlow(segment, index, r, waiter);
    }

    private final Object updateCellReceiveSlow(ChannelSegment<E> segment, int index, long r, Object waiter) {
        while (true) {
            Object state$kotlinx_coroutines_core = segment.getState$kotlinx_coroutines_core(index);
            if (state$kotlinx_coroutines_core == null || state$kotlinx_coroutines_core == BufferedChannelKt.IN_BUFFER) {
                if (r < (sendersAndCloseStatus$FU.get(this) & 1152921504606846975L)) {
                    if (segment.casState$kotlinx_coroutines_core(index, state$kotlinx_coroutines_core, BufferedChannelKt.POISONED)) {
                        expandBuffer();
                        return BufferedChannelKt.FAILED;
                    }
                } else {
                    if (waiter == null) {
                        return BufferedChannelKt.SUSPEND_NO_WAITER;
                    }
                    if (segment.casState$kotlinx_coroutines_core(index, state$kotlinx_coroutines_core, waiter)) {
                        expandBuffer();
                        return BufferedChannelKt.SUSPEND;
                    }
                }
            } else if (state$kotlinx_coroutines_core == BufferedChannelKt.BUFFERED) {
                if (segment.casState$kotlinx_coroutines_core(index, state$kotlinx_coroutines_core, BufferedChannelKt.DONE_RCV)) {
                    expandBuffer();
                    return segment.retrieveElement$kotlinx_coroutines_core(index);
                }
            } else {
                if (state$kotlinx_coroutines_core != BufferedChannelKt.INTERRUPTED_SEND && state$kotlinx_coroutines_core != BufferedChannelKt.POISONED) {
                    if (state$kotlinx_coroutines_core != BufferedChannelKt.getCHANNEL_CLOSED()) {
                        if (state$kotlinx_coroutines_core != BufferedChannelKt.RESUMING_BY_EB && segment.casState$kotlinx_coroutines_core(index, state$kotlinx_coroutines_core, BufferedChannelKt.RESUMING_BY_RCV)) {
                            boolean z = state$kotlinx_coroutines_core instanceof WaiterEB;
                            if (z) {
                                state$kotlinx_coroutines_core = ((WaiterEB) state$kotlinx_coroutines_core).waiter;
                            }
                            if (tryResumeSender(state$kotlinx_coroutines_core, segment, index)) {
                                segment.setState$kotlinx_coroutines_core(index, BufferedChannelKt.DONE_RCV);
                                expandBuffer();
                                return segment.retrieveElement$kotlinx_coroutines_core(index);
                            }
                            segment.setState$kotlinx_coroutines_core(index, BufferedChannelKt.INTERRUPTED_SEND);
                            segment.onCancelledRequest(index, false);
                            if (z) {
                                expandBuffer();
                            }
                            return BufferedChannelKt.FAILED;
                        }
                    } else {
                        expandBuffer();
                        return BufferedChannelKt.FAILED;
                    }
                }
                return BufferedChannelKt.FAILED;
            }
        }
    }

    private final boolean tryResumeSender(Object obj, ChannelSegment<E> channelSegment, int i) {
        if (obj instanceof CancellableContinuation) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlinx.coroutines.CancellableContinuation<kotlin.Unit>");
            return BufferedChannelKt.tryResume0$default((CancellableContinuation) obj, Unit.INSTANCE, null, 2, null);
        }
        if (obj instanceof SelectInstance) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlinx.coroutines.selects.SelectImplementation<*>");
            TrySelectDetailedResult trySelectDetailedResultTrySelectDetailed = ((SelectImplementation) obj).trySelectDetailed(this, Unit.INSTANCE);
            if (trySelectDetailedResultTrySelectDetailed == TrySelectDetailedResult.REREGISTER) {
                channelSegment.cleanElement$kotlinx_coroutines_core(i);
            }
            return trySelectDetailedResultTrySelectDetailed == TrySelectDetailedResult.SUCCESSFUL;
        }
        if (obj instanceof SendBroadcast) {
            return BufferedChannelKt.tryResume0$default(((SendBroadcast) obj).getCont(), true, null, 2, null);
        }
        throw new IllegalStateException(("Unexpected waiter: " + obj).toString());
    }

    private final void expandBuffer() {
        if (isRendezvousOrUnlimited()) {
            return;
        }
        ChannelSegment<E> channelSegment = (ChannelSegment) bufferEndSegment$FU.get(this);
        while (true) {
            long andIncrement = bufferEnd$FU.getAndIncrement(this);
            long j = andIncrement / ((long) BufferedChannelKt.SEGMENT_SIZE);
            if (getSendersCounter$kotlinx_coroutines_core() <= andIncrement) {
                if (channelSegment.id < j && channelSegment.getNext() != 0) {
                    moveSegmentBufferEndToSpecifiedOrLast(j, channelSegment);
                }
                incCompletedExpandBufferAttempts$default(this, 0L, 1, null);
                return;
            }
            if (channelSegment.id != j) {
                ChannelSegment<E> channelSegmentFindSegmentBufferEnd = findSegmentBufferEnd(j, channelSegment, andIncrement);
                if (channelSegmentFindSegmentBufferEnd == null) {
                    continue;
                } else {
                    channelSegment = channelSegmentFindSegmentBufferEnd;
                }
            }
            if (updateCellExpandBuffer(channelSegment, (int) (andIncrement % ((long) BufferedChannelKt.SEGMENT_SIZE)), andIncrement)) {
                incCompletedExpandBufferAttempts$default(this, 0L, 1, null);
                return;
            }
            incCompletedExpandBufferAttempts$default(this, 0L, 1, null);
        }
    }

    private final boolean updateCellExpandBuffer(ChannelSegment<E> segment, int index, long b) {
        Object state$kotlinx_coroutines_core = segment.getState$kotlinx_coroutines_core(index);
        if ((state$kotlinx_coroutines_core instanceof Waiter) && b >= receivers$FU.get(this) && segment.casState$kotlinx_coroutines_core(index, state$kotlinx_coroutines_core, BufferedChannelKt.RESUMING_BY_EB)) {
            if (!tryResumeSender(state$kotlinx_coroutines_core, segment, index)) {
                segment.setState$kotlinx_coroutines_core(index, BufferedChannelKt.INTERRUPTED_SEND);
                segment.onCancelledRequest(index, false);
                return false;
            }
            segment.setState$kotlinx_coroutines_core(index, BufferedChannelKt.BUFFERED);
            return true;
        }
        return updateCellExpandBufferSlow(segment, index, b);
    }

    private final boolean updateCellExpandBufferSlow(ChannelSegment<E> segment, int index, long b) {
        while (true) {
            Object state$kotlinx_coroutines_core = segment.getState$kotlinx_coroutines_core(index);
            if (state$kotlinx_coroutines_core instanceof Waiter) {
                if (b >= receivers$FU.get(this)) {
                    if (segment.casState$kotlinx_coroutines_core(index, state$kotlinx_coroutines_core, BufferedChannelKt.RESUMING_BY_EB)) {
                        if (!tryResumeSender(state$kotlinx_coroutines_core, segment, index)) {
                            segment.setState$kotlinx_coroutines_core(index, BufferedChannelKt.INTERRUPTED_SEND);
                            segment.onCancelledRequest(index, false);
                            return false;
                        }
                        segment.setState$kotlinx_coroutines_core(index, BufferedChannelKt.BUFFERED);
                        return true;
                    }
                } else if (segment.casState$kotlinx_coroutines_core(index, state$kotlinx_coroutines_core, new WaiterEB((Waiter) state$kotlinx_coroutines_core))) {
                    return true;
                }
            } else {
                if (state$kotlinx_coroutines_core == BufferedChannelKt.INTERRUPTED_SEND) {
                    return false;
                }
                if (state$kotlinx_coroutines_core == null) {
                    if (segment.casState$kotlinx_coroutines_core(index, state$kotlinx_coroutines_core, BufferedChannelKt.IN_BUFFER)) {
                        return true;
                    }
                } else {
                    if (state$kotlinx_coroutines_core == BufferedChannelKt.BUFFERED || state$kotlinx_coroutines_core == BufferedChannelKt.POISONED || state$kotlinx_coroutines_core == BufferedChannelKt.DONE_RCV || state$kotlinx_coroutines_core == BufferedChannelKt.INTERRUPTED_RCV || state$kotlinx_coroutines_core == BufferedChannelKt.getCHANNEL_CLOSED()) {
                        return true;
                    }
                    if (state$kotlinx_coroutines_core != BufferedChannelKt.RESUMING_BY_RCV) {
                        throw new IllegalStateException(("Unexpected cell state: " + state$kotlinx_coroutines_core).toString());
                    }
                }
            }
        }
    }

    static /* synthetic */ void incCompletedExpandBufferAttempts$default(BufferedChannel bufferedChannel, long j, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: incCompletedExpandBufferAttempts");
        }
        if ((i & 1) != 0) {
            j = 1;
        }
        bufferedChannel.incCompletedExpandBufferAttempts(j);
    }

    private final void incCompletedExpandBufferAttempts(long nAttempts) {
        if ((completedExpandBuffersAndPauseFlag$FU.addAndGet(this, nAttempts) & 4611686018427387904L) != 0) {
            do {
            } while ((completedExpandBuffersAndPauseFlag$FU.get(this) & 4611686018427387904L) != 0);
        }
    }

    public final void waitExpandBufferCompletion$kotlinx_coroutines_core(long globalIndex) {
        long j;
        AtomicLongFieldUpdater atomicLongFieldUpdater;
        long j2;
        if (isRendezvousOrUnlimited()) {
            return;
        }
        while (getBufferEndCounter() <= globalIndex) {
        }
        int i = BufferedChannelKt.EXPAND_BUFFER_COMPLETION_WAIT_ITERATIONS;
        for (int i2 = 0; i2 < i; i2++) {
            long bufferEndCounter = getBufferEndCounter();
            if (bufferEndCounter == (DurationKt.MAX_MILLIS & completedExpandBuffersAndPauseFlag$FU.get(this)) && bufferEndCounter == getBufferEndCounter()) {
                return;
            }
        }
        AtomicLongFieldUpdater atomicLongFieldUpdater2 = completedExpandBuffersAndPauseFlag$FU;
        do {
            j = atomicLongFieldUpdater2.get(this);
        } while (!atomicLongFieldUpdater2.compareAndSet(this, j, BufferedChannelKt.constructEBCompletedAndPauseFlag(j & DurationKt.MAX_MILLIS, true)));
        while (true) {
            long bufferEndCounter2 = getBufferEndCounter();
            atomicLongFieldUpdater = completedExpandBuffersAndPauseFlag$FU;
            long j3 = atomicLongFieldUpdater.get(this);
            long j4 = j3 & DurationKt.MAX_MILLIS;
            boolean z = (4611686018427387904L & j3) != 0;
            if (bufferEndCounter2 == j4 && bufferEndCounter2 == getBufferEndCounter()) {
                break;
            } else if (!z) {
                atomicLongFieldUpdater.compareAndSet(this, j3, BufferedChannelKt.constructEBCompletedAndPauseFlag(j4, true));
            }
        }
        do {
            j2 = atomicLongFieldUpdater.get(this);
        } while (!atomicLongFieldUpdater.compareAndSet(this, j2, BufferedChannelKt.constructEBCompletedAndPauseFlag(j2 & DurationKt.MAX_MILLIS, false)));
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public SelectClause2<E, BufferedChannel<E>> getOnSend() {
        BufferedChannel$onSend$1 bufferedChannel$onSend$1 = BufferedChannel$onSend$1.INSTANCE;
        Intrinsics.checkNotNull(bufferedChannel$onSend$1, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = 'clauseObject')] kotlin.Any, @[ParameterName(name = 'select')] kotlinx.coroutines.selects.SelectInstance<*>, @[ParameterName(name = 'param')] kotlin.Any?, kotlin.Unit>{ kotlinx.coroutines.selects.SelectKt.RegistrationFunction }");
        Function3 function3 = (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(bufferedChannel$onSend$1, 3);
        BufferedChannel$onSend$2 bufferedChannel$onSend$2 = BufferedChannel$onSend$2.INSTANCE;
        Intrinsics.checkNotNull(bufferedChannel$onSend$2, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = 'clauseObject')] kotlin.Any, @[ParameterName(name = 'param')] kotlin.Any?, @[ParameterName(name = 'clauseResult')] kotlin.Any?, kotlin.Any?>{ kotlinx.coroutines.selects.SelectKt.ProcessResultFunction }");
        return new SelectClause2Impl(this, function3, (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(bufferedChannel$onSend$2, 3), null, 8, null);
    }

    private final void onClosedSelectOnSend(E element, SelectInstance<?> select) {
        Function1<E, Unit> function1 = this.onUndeliveredElement;
        if (function1 != null) {
            OnUndeliveredElementKt.callUndeliveredElement(function1, element, select.getContext());
        }
        select.selectInRegistrationPhase(BufferedChannelKt.getCHANNEL_CLOSED());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object processResultSelectSend(Object ignoredParam, Object selectResult) throws Throwable {
        if (selectResult != BufferedChannelKt.getCHANNEL_CLOSED()) {
            return this;
        }
        throw getSendException();
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public SelectClause1<E> getOnReceive() {
        BufferedChannel$onReceive$1 bufferedChannel$onReceive$1 = BufferedChannel$onReceive$1.INSTANCE;
        Intrinsics.checkNotNull(bufferedChannel$onReceive$1, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = 'clauseObject')] kotlin.Any, @[ParameterName(name = 'select')] kotlinx.coroutines.selects.SelectInstance<*>, @[ParameterName(name = 'param')] kotlin.Any?, kotlin.Unit>{ kotlinx.coroutines.selects.SelectKt.RegistrationFunction }");
        Function3 function3 = (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(bufferedChannel$onReceive$1, 3);
        BufferedChannel$onReceive$2 bufferedChannel$onReceive$2 = BufferedChannel$onReceive$2.INSTANCE;
        Intrinsics.checkNotNull(bufferedChannel$onReceive$2, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = 'clauseObject')] kotlin.Any, @[ParameterName(name = 'param')] kotlin.Any?, @[ParameterName(name = 'clauseResult')] kotlin.Any?, kotlin.Any?>{ kotlinx.coroutines.selects.SelectKt.ProcessResultFunction }");
        return new SelectClause1Impl(this, function3, (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(bufferedChannel$onReceive$2, 3), this.onUndeliveredElementReceiveCancellationConstructor);
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public SelectClause1<ChannelResult<E>> getOnReceiveCatching() {
        BufferedChannel$onReceiveCatching$1 bufferedChannel$onReceiveCatching$1 = BufferedChannel$onReceiveCatching$1.INSTANCE;
        Intrinsics.checkNotNull(bufferedChannel$onReceiveCatching$1, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = 'clauseObject')] kotlin.Any, @[ParameterName(name = 'select')] kotlinx.coroutines.selects.SelectInstance<*>, @[ParameterName(name = 'param')] kotlin.Any?, kotlin.Unit>{ kotlinx.coroutines.selects.SelectKt.RegistrationFunction }");
        Function3 function3 = (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(bufferedChannel$onReceiveCatching$1, 3);
        BufferedChannel$onReceiveCatching$2 bufferedChannel$onReceiveCatching$2 = BufferedChannel$onReceiveCatching$2.INSTANCE;
        Intrinsics.checkNotNull(bufferedChannel$onReceiveCatching$2, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = 'clauseObject')] kotlin.Any, @[ParameterName(name = 'param')] kotlin.Any?, @[ParameterName(name = 'clauseResult')] kotlin.Any?, kotlin.Any?>{ kotlinx.coroutines.selects.SelectKt.ProcessResultFunction }");
        return new SelectClause1Impl(this, function3, (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(bufferedChannel$onReceiveCatching$2, 3), this.onUndeliveredElementReceiveCancellationConstructor);
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public SelectClause1<E> getOnReceiveOrNull() {
        BufferedChannel$onReceiveOrNull$1 bufferedChannel$onReceiveOrNull$1 = BufferedChannel$onReceiveOrNull$1.INSTANCE;
        Intrinsics.checkNotNull(bufferedChannel$onReceiveOrNull$1, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = 'clauseObject')] kotlin.Any, @[ParameterName(name = 'select')] kotlinx.coroutines.selects.SelectInstance<*>, @[ParameterName(name = 'param')] kotlin.Any?, kotlin.Unit>{ kotlinx.coroutines.selects.SelectKt.RegistrationFunction }");
        Function3 function3 = (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(bufferedChannel$onReceiveOrNull$1, 3);
        BufferedChannel$onReceiveOrNull$2 bufferedChannel$onReceiveOrNull$2 = BufferedChannel$onReceiveOrNull$2.INSTANCE;
        Intrinsics.checkNotNull(bufferedChannel$onReceiveOrNull$2, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = 'clauseObject')] kotlin.Any, @[ParameterName(name = 'param')] kotlin.Any?, @[ParameterName(name = 'clauseResult')] kotlin.Any?, kotlin.Any?>{ kotlinx.coroutines.selects.SelectKt.ProcessResultFunction }");
        return new SelectClause1Impl(this, function3, (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(bufferedChannel$onReceiveOrNull$2, 3), this.onUndeliveredElementReceiveCancellationConstructor);
    }

    private final void onClosedSelectOnReceive(SelectInstance<?> select) {
        select.selectInRegistrationPhase(BufferedChannelKt.getCHANNEL_CLOSED());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object processResultSelectReceive(Object ignoredParam, Object selectResult) throws Throwable {
        if (selectResult != BufferedChannelKt.getCHANNEL_CLOSED()) {
            return selectResult;
        }
        throw getReceiveException();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object processResultSelectReceiveOrNull(Object ignoredParam, Object selectResult) throws Throwable {
        if (selectResult != BufferedChannelKt.getCHANNEL_CLOSED()) {
            return selectResult;
        }
        if (getCloseCause() == null) {
            return null;
        }
        throw getReceiveException();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object processResultSelectReceiveCatching(Object ignoredParam, Object selectResult) {
        return ChannelResult.m2198boximpl(selectResult == BufferedChannelKt.getCHANNEL_CLOSED() ? ChannelResult.INSTANCE.m2211closedJP2dKIU(getCloseCause()) : ChannelResult.INSTANCE.m2213successJP2dKIU(selectResult));
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public ChannelIterator<E> iterator() {
        return new BufferedChannelIterator();
    }

    /* JADX INFO: compiled from: BufferedChannel.kt */
    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b\u0082\u0004\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0011\u0010\t\u001a\u00020\u0006H\u0096Bø\u0001\u0000¢\u0006\u0002\u0010\nJ/\u0010\u000b\u001a\u00020\u00062\f\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0082@ø\u0001\u0000¢\u0006\u0002\u0010\u0012J\u001c\u0010\u0013\u001a\u00020\u00142\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\u00152\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u000e\u0010\u0016\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u0017J\b\u0010\u0018\u001a\u00020\u0006H\u0002J\b\u0010\u0019\u001a\u00020\u0014H\u0002J\u0013\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00028\u0000¢\u0006\u0002\u0010\u001cJ\u0006\u0010\u001d\u001a\u00020\u0014R\u0016\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u001e"}, d2 = {"Lkotlinx/coroutines/channels/BufferedChannel$BufferedChannelIterator;", "Lkotlinx/coroutines/channels/ChannelIterator;", "Lkotlinx/coroutines/Waiter;", "(Lkotlinx/coroutines/channels/BufferedChannel;)V", "continuation", "Lkotlinx/coroutines/CancellableContinuationImpl;", "", "receiveResult", "", "hasNext", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "hasNextOnNoWaiterSuspend", "segment", "Lkotlinx/coroutines/channels/ChannelSegment;", "index", "", "r", "", "(Lkotlinx/coroutines/channels/ChannelSegment;IJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "invokeOnCancellation", "", "Lkotlinx/coroutines/internal/Segment;", "next", "()Ljava/lang/Object;", "onClosedHasNext", "onClosedHasNextNoWaiterSuspend", "tryResumeHasNext", "element", "(Ljava/lang/Object;)Z", "tryResumeHasNextOnClosedChannel", "kotlinx-coroutines-core"}, k = 1, mv = {1, 8, 0}, xi = 48)
    private final class BufferedChannelIterator implements ChannelIterator<E>, Waiter {
        private CancellableContinuationImpl<? super Boolean> continuation;
        private Object receiveResult = BufferedChannelKt.NO_RECEIVE_RESULT;

        public BufferedChannelIterator() {
        }

        @Override // kotlinx.coroutines.channels.ChannelIterator
        @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.3.0, binary compatibility with versions <= 1.2.x")
        public /* synthetic */ Object next(Continuation continuation) {
            return ChannelIterator.DefaultImpls.next(this, continuation);
        }

        @Override // kotlinx.coroutines.channels.ChannelIterator
        public Object hasNext(Continuation<? super Boolean> continuation) {
            ChannelSegment<E> channelSegment;
            BufferedChannel<E> bufferedChannel = BufferedChannel.this;
            ChannelSegment<E> channelSegment2 = (ChannelSegment) BufferedChannel.receiveSegment$FU.get(bufferedChannel);
            while (!bufferedChannel.isClosedForReceive()) {
                long andIncrement = BufferedChannel.receivers$FU.getAndIncrement(bufferedChannel);
                long j = andIncrement / ((long) BufferedChannelKt.SEGMENT_SIZE);
                int i = (int) (andIncrement % ((long) BufferedChannelKt.SEGMENT_SIZE));
                if (channelSegment2.id != j) {
                    ChannelSegment<E> channelSegmentFindSegmentReceive = bufferedChannel.findSegmentReceive(j, channelSegment2);
                    if (channelSegmentFindSegmentReceive == null) {
                        continue;
                    } else {
                        channelSegment = channelSegmentFindSegmentReceive;
                    }
                } else {
                    channelSegment = channelSegment2;
                }
                Object objUpdateCellReceive = bufferedChannel.updateCellReceive(channelSegment, i, andIncrement, null);
                if (objUpdateCellReceive != BufferedChannelKt.SUSPEND) {
                    if (objUpdateCellReceive != BufferedChannelKt.FAILED) {
                        if (objUpdateCellReceive == BufferedChannelKt.SUSPEND_NO_WAITER) {
                            return hasNextOnNoWaiterSuspend(channelSegment, i, andIncrement, continuation);
                        }
                        channelSegment.cleanPrev();
                        this.receiveResult = objUpdateCellReceive;
                        return Boxing.boxBoolean(true);
                    }
                    if (andIncrement < bufferedChannel.getSendersCounter$kotlinx_coroutines_core()) {
                        channelSegment.cleanPrev();
                    }
                    channelSegment2 = channelSegment;
                } else {
                    throw new IllegalStateException("unreachable".toString());
                }
            }
            return Boxing.boxBoolean(onClosedHasNext());
        }

        private final boolean onClosedHasNext() throws Throwable {
            this.receiveResult = BufferedChannelKt.getCHANNEL_CLOSED();
            Throwable closeCause = BufferedChannel.this.getCloseCause();
            if (closeCause == null) {
                return false;
            }
            throw StackTraceRecoveryKt.recoverStackTrace(closeCause);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Object hasNextOnNoWaiterSuspend(ChannelSegment<E> channelSegment, int i, long j, Continuation<? super Boolean> continuation) {
            Boolean boolBoxBoolean;
            BufferedChannel<E> bufferedChannel = BufferedChannel.this;
            CancellableContinuationImpl orCreateCancellableContinuation = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt.intercepted(continuation));
            try {
                this.continuation = orCreateCancellableContinuation;
                Object objUpdateCellReceive = bufferedChannel.updateCellReceive(channelSegment, i, j, this);
                if (objUpdateCellReceive != BufferedChannelKt.SUSPEND) {
                    Function1<Throwable, Unit> function1BindCancellationFun = null;
                    function1BindCancellationFun = null;
                    if (objUpdateCellReceive == BufferedChannelKt.FAILED) {
                        if (j < bufferedChannel.getSendersCounter$kotlinx_coroutines_core()) {
                            channelSegment.cleanPrev();
                        }
                        ChannelSegment channelSegment2 = (ChannelSegment) BufferedChannel.receiveSegment$FU.get(bufferedChannel);
                        while (true) {
                            if (bufferedChannel.isClosedForReceive()) {
                                onClosedHasNextNoWaiterSuspend();
                                break;
                            }
                            long andIncrement = BufferedChannel.receivers$FU.getAndIncrement(bufferedChannel);
                            long j2 = andIncrement / ((long) BufferedChannelKt.SEGMENT_SIZE);
                            int i2 = (int) (andIncrement % ((long) BufferedChannelKt.SEGMENT_SIZE));
                            if (channelSegment2.id != j2) {
                                ChannelSegment channelSegmentFindSegmentReceive = bufferedChannel.findSegmentReceive(j2, channelSegment2);
                                if (channelSegmentFindSegmentReceive != null) {
                                    channelSegment2 = channelSegmentFindSegmentReceive;
                                }
                            }
                            Object objUpdateCellReceive2 = bufferedChannel.updateCellReceive(channelSegment2, i2, andIncrement, this);
                            if (objUpdateCellReceive2 != BufferedChannelKt.SUSPEND) {
                                if (objUpdateCellReceive2 == BufferedChannelKt.FAILED) {
                                    if (andIncrement < bufferedChannel.getSendersCounter$kotlinx_coroutines_core()) {
                                        channelSegment2.cleanPrev();
                                    }
                                } else {
                                    if (objUpdateCellReceive2 == BufferedChannelKt.SUSPEND_NO_WAITER) {
                                        throw new IllegalStateException("unexpected".toString());
                                    }
                                    channelSegment2.cleanPrev();
                                    this.receiveResult = objUpdateCellReceive2;
                                    this.continuation = null;
                                    boolBoxBoolean = Boxing.boxBoolean(true);
                                    Function1<E, Unit> function1 = bufferedChannel.onUndeliveredElement;
                                    if (function1 != null) {
                                        function1BindCancellationFun = OnUndeliveredElementKt.bindCancellationFun(function1, objUpdateCellReceive2, orCreateCancellableContinuation.getContext());
                                    }
                                }
                            } else {
                                BufferedChannelIterator bufferedChannelIterator = this instanceof Waiter ? this : null;
                                if (bufferedChannelIterator != null) {
                                    bufferedChannel.prepareReceiverForSuspension(bufferedChannelIterator, channelSegment2, i2);
                                }
                            }
                        }
                    } else {
                        channelSegment.cleanPrev();
                        this.receiveResult = objUpdateCellReceive;
                        this.continuation = null;
                        boolBoxBoolean = Boxing.boxBoolean(true);
                        Function1<E, Unit> function12 = bufferedChannel.onUndeliveredElement;
                        if (function12 != null) {
                            function1BindCancellationFun = OnUndeliveredElementKt.bindCancellationFun(function12, objUpdateCellReceive, orCreateCancellableContinuation.getContext());
                        }
                    }
                    orCreateCancellableContinuation.resume(boolBoxBoolean, function1BindCancellationFun);
                } else {
                    bufferedChannel.prepareReceiverForSuspension(this, channelSegment, i);
                }
                Object result = orCreateCancellableContinuation.getResult();
                if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    DebugProbesKt.probeCoroutineSuspended(continuation);
                }
                return result;
            } catch (Throwable th) {
                orCreateCancellableContinuation.releaseClaimedReusableContinuation$kotlinx_coroutines_core();
                throw th;
            }
        }

        @Override // kotlinx.coroutines.Waiter
        public void invokeOnCancellation(Segment<?> segment, int index) {
            CancellableContinuationImpl<? super Boolean> cancellableContinuationImpl = this.continuation;
            if (cancellableContinuationImpl != null) {
                cancellableContinuationImpl.invokeOnCancellation(segment, index);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void onClosedHasNextNoWaiterSuspend() {
            CancellableContinuationImpl<? super Boolean> cancellableContinuationImpl = this.continuation;
            Intrinsics.checkNotNull(cancellableContinuationImpl);
            this.continuation = null;
            this.receiveResult = BufferedChannelKt.getCHANNEL_CLOSED();
            Throwable closeCause = BufferedChannel.this.getCloseCause();
            if (closeCause == null) {
                Result.Companion companion = Result.INSTANCE;
                cancellableContinuationImpl.resumeWith(Result.m684constructorimpl(false));
                return;
            }
            CancellableContinuationImpl<? super Boolean> cancellableContinuationImpl2 = cancellableContinuationImpl;
            if (DebugKt.getRECOVER_STACK_TRACES() && (cancellableContinuationImpl2 instanceof CoroutineStackFrame)) {
                closeCause = StackTraceRecoveryKt.recoverFromStackFrame(closeCause, cancellableContinuationImpl2);
            }
            Result.Companion companion2 = Result.INSTANCE;
            cancellableContinuationImpl2.resumeWith(Result.m684constructorimpl(ResultKt.createFailure(closeCause)));
        }

        @Override // kotlinx.coroutines.channels.ChannelIterator
        public E next() throws Throwable {
            E e = (E) this.receiveResult;
            if (e != BufferedChannelKt.NO_RECEIVE_RESULT) {
                this.receiveResult = BufferedChannelKt.NO_RECEIVE_RESULT;
                if (e != BufferedChannelKt.getCHANNEL_CLOSED()) {
                    return e;
                }
                throw StackTraceRecoveryKt.recoverStackTrace(BufferedChannel.this.getReceiveException());
            }
            throw new IllegalStateException("`hasNext()` has not been invoked".toString());
        }

        public final boolean tryResumeHasNext(E element) {
            CancellableContinuationImpl<? super Boolean> cancellableContinuationImpl = this.continuation;
            Intrinsics.checkNotNull(cancellableContinuationImpl);
            this.continuation = null;
            this.receiveResult = element;
            CancellableContinuationImpl<? super Boolean> cancellableContinuationImpl2 = cancellableContinuationImpl;
            Function1<E, Unit> function1 = BufferedChannel.this.onUndeliveredElement;
            return BufferedChannelKt.tryResume0(cancellableContinuationImpl2, true, function1 != null ? OnUndeliveredElementKt.bindCancellationFun(function1, element, cancellableContinuationImpl.getContext()) : null);
        }

        public final void tryResumeHasNextOnClosedChannel() {
            CancellableContinuationImpl<? super Boolean> cancellableContinuationImpl = this.continuation;
            Intrinsics.checkNotNull(cancellableContinuationImpl);
            this.continuation = null;
            this.receiveResult = BufferedChannelKt.getCHANNEL_CLOSED();
            Throwable closeCause = BufferedChannel.this.getCloseCause();
            if (closeCause == null) {
                Result.Companion companion = Result.INSTANCE;
                cancellableContinuationImpl.resumeWith(Result.m684constructorimpl(false));
                return;
            }
            CancellableContinuationImpl<? super Boolean> cancellableContinuationImpl2 = cancellableContinuationImpl;
            if (DebugKt.getRECOVER_STACK_TRACES() && (cancellableContinuationImpl2 instanceof CoroutineStackFrame)) {
                closeCause = StackTraceRecoveryKt.recoverFromStackFrame(closeCause, cancellableContinuationImpl2);
            }
            Result.Companion companion2 = Result.INSTANCE;
            cancellableContinuationImpl2.resumeWith(Result.m684constructorimpl(ResultKt.createFailure(closeCause)));
        }
    }

    protected final Throwable getCloseCause() {
        return (Throwable) _closeCause$FU.get(this);
    }

    protected final Throwable getSendException() {
        Throwable closeCause = getCloseCause();
        return closeCause == null ? new ClosedSendChannelException(ChannelsKt.DEFAULT_CLOSE_MESSAGE) : closeCause;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Throwable getReceiveException() {
        Throwable closeCause = getCloseCause();
        return closeCause == null ? new ClosedReceiveChannelException(ChannelsKt.DEFAULT_CLOSE_MESSAGE) : closeCause;
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public boolean close(Throwable cause) {
        return closeOrCancelImpl(cause, false);
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public final boolean cancel(Throwable cause) {
        return cancelImpl$kotlinx_coroutines_core(cause);
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public final void cancel() {
        cancelImpl$kotlinx_coroutines_core(null);
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public final void cancel(CancellationException cause) {
        cancelImpl$kotlinx_coroutines_core(cause);
    }

    public boolean cancelImpl$kotlinx_coroutines_core(Throwable cause) {
        if (cause == null) {
            cause = new CancellationException("Channel was cancelled");
        }
        return closeOrCancelImpl(cause, true);
    }

    protected boolean closeOrCancelImpl(Throwable cause, boolean cancel) {
        if (cancel) {
            markCancellationStarted();
        }
        boolean zM = AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_closeCause$FU, this, BufferedChannelKt.NO_CLOSE_CAUSE, cause);
        if (cancel) {
            markCancelled();
        } else {
            markClosed();
        }
        completeCloseOrCancel();
        onClosedIdempotent();
        if (zM) {
            invokeCloseHandler();
        }
        return zM;
    }

    private final void invokeCloseHandler() {
        Object obj;
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = closeHandler$FU;
        do {
            obj = atomicReferenceFieldUpdater.get(this);
        } while (!AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(atomicReferenceFieldUpdater, this, obj, obj == null ? BufferedChannelKt.CLOSE_HANDLER_CLOSED : BufferedChannelKt.CLOSE_HANDLER_INVOKED));
        if (obj == null) {
            return;
        }
        ((Function1) obj).invoke(getCloseCause());
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public void invokeOnClose(Function1<? super Throwable, Unit> handler) {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = closeHandler$FU;
        if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(atomicReferenceFieldUpdater, this, null, handler)) {
            return;
        }
        do {
            Object obj = atomicReferenceFieldUpdater.get(this);
            if (obj != BufferedChannelKt.CLOSE_HANDLER_CLOSED) {
                if (obj != BufferedChannelKt.CLOSE_HANDLER_INVOKED) {
                    throw new IllegalStateException(("Another handler is already registered: " + obj).toString());
                }
                throw new IllegalStateException("Another handler was already registered and successfully invoked".toString());
            }
        } while (!AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(closeHandler$FU, this, BufferedChannelKt.CLOSE_HANDLER_CLOSED, BufferedChannelKt.CLOSE_HANDLER_INVOKED));
        handler.invoke(getCloseCause());
    }

    private final void markClosed() {
        long j;
        long jConstructSendersAndCloseStatus;
        AtomicLongFieldUpdater atomicLongFieldUpdater = sendersAndCloseStatus$FU;
        do {
            j = atomicLongFieldUpdater.get(this);
            int i = (int) (j >> 60);
            if (i == 0) {
                jConstructSendersAndCloseStatus = BufferedChannelKt.constructSendersAndCloseStatus(j & 1152921504606846975L, 2);
            } else if (i != 1) {
                return;
            } else {
                jConstructSendersAndCloseStatus = BufferedChannelKt.constructSendersAndCloseStatus(j & 1152921504606846975L, 3);
            }
        } while (!atomicLongFieldUpdater.compareAndSet(this, j, jConstructSendersAndCloseStatus));
    }

    private final void markCancelled() {
        long j;
        AtomicLongFieldUpdater atomicLongFieldUpdater = sendersAndCloseStatus$FU;
        do {
            j = atomicLongFieldUpdater.get(this);
        } while (!atomicLongFieldUpdater.compareAndSet(this, j, BufferedChannelKt.constructSendersAndCloseStatus(1152921504606846975L & j, 3)));
    }

    private final void markCancellationStarted() {
        long j;
        AtomicLongFieldUpdater atomicLongFieldUpdater = sendersAndCloseStatus$FU;
        do {
            j = atomicLongFieldUpdater.get(this);
            if (((int) (j >> 60)) != 0) {
                return;
            }
        } while (!atomicLongFieldUpdater.compareAndSet(this, j, BufferedChannelKt.constructSendersAndCloseStatus(1152921504606846975L & j, 1)));
    }

    private final void completeCloseOrCancel() {
        isClosedForSend();
    }

    private final ChannelSegment<E> completeClose(long sendersCur) {
        ChannelSegment<E> channelSegmentCloseLinkedList = closeLinkedList();
        if (isConflatedDropOldest()) {
            long jMarkAllEmptyCellsAsClosed = markAllEmptyCellsAsClosed(channelSegmentCloseLinkedList);
            if (jMarkAllEmptyCellsAsClosed != -1) {
                dropFirstElementUntilTheSpecifiedCellIsInTheBuffer(jMarkAllEmptyCellsAsClosed);
            }
        }
        cancelSuspendedReceiveRequests(channelSegmentCloseLinkedList, sendersCur);
        return channelSegmentCloseLinkedList;
    }

    private final void completeCancel(long sendersCur) {
        removeUnprocessedElements(completeClose(sendersCur));
    }

    private final ChannelSegment<E> closeLinkedList() {
        Object obj = bufferEndSegment$FU.get(this);
        ChannelSegment channelSegment = (ChannelSegment) sendSegment$FU.get(this);
        if (channelSegment.id > ((ChannelSegment) obj).id) {
            obj = channelSegment;
        }
        ChannelSegment channelSegment2 = (ChannelSegment) receiveSegment$FU.get(this);
        if (channelSegment2.id > ((ChannelSegment) obj).id) {
            obj = channelSegment2;
        }
        return (ChannelSegment) ConcurrentLinkedListKt.close((ConcurrentLinkedListNode) obj);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x003c, code lost:
    
        r8 = (kotlinx.coroutines.channels.ChannelSegment) r8.getPrev();
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final long markAllEmptyCellsAsClosed(kotlinx.coroutines.channels.ChannelSegment<E> r8) {
        /*
            r7 = this;
        L0:
            int r0 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            int r0 = r0 + (-1)
        L4:
            r1 = -1
            r3 = -1
            if (r3 >= r0) goto L3c
            long r3 = r8.id
            int r5 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            long r5 = (long) r5
            long r3 = r3 * r5
            long r5 = (long) r0
            long r3 = r3 + r5
            long r5 = r7.getReceiversCounter$kotlinx_coroutines_core()
            int r5 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r5 >= 0) goto L1a
            return r1
        L1a:
            java.lang.Object r1 = r8.getState$kotlinx_coroutines_core(r0)
            if (r1 == 0) goto L2c
            kotlinx.coroutines.internal.Symbol r2 = kotlinx.coroutines.channels.BufferedChannelKt.access$getIN_BUFFER$p()
            if (r1 != r2) goto L27
            goto L2c
        L27:
            kotlinx.coroutines.internal.Symbol r2 = kotlinx.coroutines.channels.BufferedChannelKt.BUFFERED
            if (r1 != r2) goto L39
            return r3
        L2c:
            kotlinx.coroutines.internal.Symbol r2 = kotlinx.coroutines.channels.BufferedChannelKt.getCHANNEL_CLOSED()
            boolean r1 = r8.casState$kotlinx_coroutines_core(r0, r1, r2)
            if (r1 == 0) goto L1a
            r8.onSlotCleaned()
        L39:
            int r0 = r0 + (-1)
            goto L4
        L3c:
            kotlinx.coroutines.internal.ConcurrentLinkedListNode r8 = r8.getPrev()
            kotlinx.coroutines.channels.ChannelSegment r8 = (kotlinx.coroutines.channels.ChannelSegment) r8
            if (r8 != 0) goto L0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.markAllEmptyCellsAsClosed(kotlinx.coroutines.channels.ChannelSegment):long");
    }

    /* JADX WARN: Code restructure failed: missing block: B:50:0x00b3, code lost:
    
        r12 = (kotlinx.coroutines.channels.ChannelSegment) r12.getPrev();
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void removeUnprocessedElements(kotlinx.coroutines.channels.ChannelSegment<E> r12) {
        /*
            Method dump skipped, instruction units count: 229
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.removeUnprocessedElements(kotlinx.coroutines.channels.ChannelSegment):void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void cancelSuspendedReceiveRequests(ChannelSegment<E> lastSegment, long sendersCounter) {
        Object objM2234constructorimpl$default = InlineList.m2234constructorimpl$default(null, 1, null);
        loop0: while (lastSegment != null) {
            for (int i = BufferedChannelKt.SEGMENT_SIZE - 1; -1 < i; i--) {
                if ((lastSegment.id * ((long) BufferedChannelKt.SEGMENT_SIZE)) + ((long) i) < sendersCounter) {
                    break loop0;
                }
                while (true) {
                    Object state$kotlinx_coroutines_core = lastSegment.getState$kotlinx_coroutines_core(i);
                    if (state$kotlinx_coroutines_core == null || state$kotlinx_coroutines_core == BufferedChannelKt.IN_BUFFER) {
                        if (lastSegment.casState$kotlinx_coroutines_core(i, state$kotlinx_coroutines_core, BufferedChannelKt.getCHANNEL_CLOSED())) {
                            lastSegment.onSlotCleaned();
                            break;
                        }
                    } else if (state$kotlinx_coroutines_core instanceof WaiterEB) {
                        if (lastSegment.casState$kotlinx_coroutines_core(i, state$kotlinx_coroutines_core, BufferedChannelKt.getCHANNEL_CLOSED())) {
                            objM2234constructorimpl$default = InlineList.m2239plusFjFbRPM(objM2234constructorimpl$default, ((WaiterEB) state$kotlinx_coroutines_core).waiter);
                            lastSegment.onCancelledRequest(i, true);
                            break;
                        }
                    } else {
                        if (!(state$kotlinx_coroutines_core instanceof Waiter)) {
                            break;
                        }
                        if (lastSegment.casState$kotlinx_coroutines_core(i, state$kotlinx_coroutines_core, BufferedChannelKt.getCHANNEL_CLOSED())) {
                            objM2234constructorimpl$default = InlineList.m2239plusFjFbRPM(objM2234constructorimpl$default, state$kotlinx_coroutines_core);
                            lastSegment.onCancelledRequest(i, true);
                            break;
                        }
                    }
                }
            }
            lastSegment = (ChannelSegment) lastSegment.getPrev();
        }
        if (objM2234constructorimpl$default != null) {
            if (!(objM2234constructorimpl$default instanceof ArrayList)) {
                resumeReceiverOnClosedChannel((Waiter) objM2234constructorimpl$default);
                return;
            }
            Intrinsics.checkNotNull(objM2234constructorimpl$default, "null cannot be cast to non-null type java.util.ArrayList<E of kotlinx.coroutines.internal.InlineList>{ kotlin.collections.TypeAliasesKt.ArrayList<E of kotlinx.coroutines.internal.InlineList> }");
            ArrayList arrayList = (ArrayList) objM2234constructorimpl$default;
            for (int size = arrayList.size() - 1; -1 < size; size--) {
                resumeReceiverOnClosedChannel((Waiter) arrayList.get(size));
            }
        }
    }

    private final void resumeReceiverOnClosedChannel(Waiter waiter) {
        resumeWaiterOnClosedChannel(waiter, true);
    }

    private final void resumeSenderOnCancelledChannel(Waiter waiter) {
        resumeWaiterOnClosedChannel(waiter, false);
    }

    private final void resumeWaiterOnClosedChannel(Waiter waiter, boolean z) {
        if (waiter instanceof SendBroadcast) {
            CancellableContinuation<Boolean> cont = ((SendBroadcast) waiter).getCont();
            Result.Companion companion = Result.INSTANCE;
            cont.resumeWith(Result.m684constructorimpl(false));
            return;
        }
        if (waiter instanceof CancellableContinuation) {
            Continuation continuation = (Continuation) waiter;
            Result.Companion companion2 = Result.INSTANCE;
            continuation.resumeWith(Result.m684constructorimpl(ResultKt.createFailure(z ? getReceiveException() : getSendException())));
        } else if (waiter instanceof ReceiveCatching) {
            CancellableContinuationImpl<ChannelResult<? extends E>> cancellableContinuationImpl = ((ReceiveCatching) waiter).cont;
            Result.Companion companion3 = Result.INSTANCE;
            cancellableContinuationImpl.resumeWith(Result.m684constructorimpl(ChannelResult.m2198boximpl(ChannelResult.INSTANCE.m2211closedJP2dKIU(getCloseCause()))));
        } else if (waiter instanceof BufferedChannelIterator) {
            ((BufferedChannelIterator) waiter).tryResumeHasNextOnClosedChannel();
        } else {
            if (!(waiter instanceof SelectInstance)) {
                throw new IllegalStateException(("Unexpected waiter: " + waiter).toString());
            }
            ((SelectInstance) waiter).trySelect(this, BufferedChannelKt.getCHANNEL_CLOSED());
        }
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public boolean isClosedForSend() {
        return isClosedForSend0(sendersAndCloseStatus$FU.get(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isClosedForSend0(long j) {
        return isClosed(j, false);
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public boolean isClosedForReceive() {
        return isClosedForReceive0(sendersAndCloseStatus$FU.get(this));
    }

    private final boolean isClosedForReceive0(long j) {
        return isClosed(j, true);
    }

    private final boolean isClosed(long sendersAndCloseStatusCur, boolean isClosedForReceive) {
        int i = (int) (sendersAndCloseStatusCur >> 60);
        if (i == 0 || i == 1) {
            return false;
        }
        if (i == 2) {
            completeClose(sendersAndCloseStatusCur & 1152921504606846975L);
            if (isClosedForReceive && hasElements$kotlinx_coroutines_core()) {
                return false;
            }
        } else if (i == 3) {
            completeCancel(sendersAndCloseStatusCur & 1152921504606846975L);
        } else {
            throw new IllegalStateException(("unexpected close status: " + i).toString());
        }
        return true;
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public boolean isEmpty() {
        if (isClosedForReceive() || hasElements$kotlinx_coroutines_core()) {
            return false;
        }
        return !isClosedForReceive();
    }

    public final boolean hasElements$kotlinx_coroutines_core() {
        while (true) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = receiveSegment$FU;
            ChannelSegment<E> channelSegmentFindSegmentReceive = (ChannelSegment) atomicReferenceFieldUpdater.get(this);
            long receiversCounter$kotlinx_coroutines_core = getReceiversCounter$kotlinx_coroutines_core();
            if (getSendersCounter$kotlinx_coroutines_core() <= receiversCounter$kotlinx_coroutines_core) {
                return false;
            }
            long j = receiversCounter$kotlinx_coroutines_core / ((long) BufferedChannelKt.SEGMENT_SIZE);
            if (channelSegmentFindSegmentReceive.id == j || (channelSegmentFindSegmentReceive = findSegmentReceive(j, channelSegmentFindSegmentReceive)) != null) {
                channelSegmentFindSegmentReceive.cleanPrev();
                if (isCellNonEmpty(channelSegmentFindSegmentReceive, (int) (receiversCounter$kotlinx_coroutines_core % ((long) BufferedChannelKt.SEGMENT_SIZE)), receiversCounter$kotlinx_coroutines_core)) {
                    return true;
                }
                receivers$FU.compareAndSet(this, receiversCounter$kotlinx_coroutines_core, receiversCounter$kotlinx_coroutines_core + 1);
            } else if (((ChannelSegment) atomicReferenceFieldUpdater.get(this)).id < j) {
                return false;
            }
        }
    }

    private final boolean isCellNonEmpty(ChannelSegment<E> segment, int index, long globalIndex) {
        Object state$kotlinx_coroutines_core;
        do {
            state$kotlinx_coroutines_core = segment.getState$kotlinx_coroutines_core(index);
            if (state$kotlinx_coroutines_core != null && state$kotlinx_coroutines_core != BufferedChannelKt.IN_BUFFER) {
                if (state$kotlinx_coroutines_core == BufferedChannelKt.BUFFERED) {
                    return true;
                }
                if (state$kotlinx_coroutines_core == BufferedChannelKt.INTERRUPTED_SEND || state$kotlinx_coroutines_core == BufferedChannelKt.getCHANNEL_CLOSED() || state$kotlinx_coroutines_core == BufferedChannelKt.DONE_RCV || state$kotlinx_coroutines_core == BufferedChannelKt.POISONED) {
                    return false;
                }
                if (state$kotlinx_coroutines_core == BufferedChannelKt.RESUMING_BY_EB) {
                    return true;
                }
                return state$kotlinx_coroutines_core != BufferedChannelKt.RESUMING_BY_RCV && globalIndex == getReceiversCounter$kotlinx_coroutines_core();
            }
        } while (!segment.casState$kotlinx_coroutines_core(index, state$kotlinx_coroutines_core, BufferedChannelKt.POISONED));
        expandBuffer();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ChannelSegment<E> findSegmentSend(long id, ChannelSegment<E> startFrom) {
        Object objFindSegmentInternal;
        boolean z;
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = sendSegment$FU;
        Function2 function2 = (Function2) BufferedChannelKt.createSegmentFunction();
        do {
            objFindSegmentInternal = ConcurrentLinkedListKt.findSegmentInternal(startFrom, id, function2);
            if (SegmentOrClosed.m2251isClosedimpl(objFindSegmentInternal)) {
                break;
            }
            Segment segmentM2249getSegmentimpl = SegmentOrClosed.m2249getSegmentimpl(objFindSegmentInternal);
            while (true) {
                Segment segment = (Segment) atomicReferenceFieldUpdater.get(this);
                if (segment.id >= segmentM2249getSegmentimpl.id) {
                    break;
                }
                if (!segmentM2249getSegmentimpl.tryIncPointers$kotlinx_coroutines_core()) {
                    z = false;
                    break;
                }
                if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(atomicReferenceFieldUpdater, this, segment, segmentM2249getSegmentimpl)) {
                    if (segment.decPointers$kotlinx_coroutines_core()) {
                        segment.remove();
                    }
                } else if (segmentM2249getSegmentimpl.decPointers$kotlinx_coroutines_core()) {
                    segmentM2249getSegmentimpl.remove();
                }
            }
            z = true;
        } while (!z);
        if (SegmentOrClosed.m2251isClosedimpl(objFindSegmentInternal)) {
            completeCloseOrCancel();
            if (startFrom.id * ((long) BufferedChannelKt.SEGMENT_SIZE) >= getReceiversCounter$kotlinx_coroutines_core()) {
                return null;
            }
            startFrom.cleanPrev();
            return null;
        }
        ChannelSegment<E> channelSegment = (ChannelSegment) SegmentOrClosed.m2249getSegmentimpl(objFindSegmentInternal);
        if (channelSegment.id > id) {
            updateSendersCounterIfLower(channelSegment.id * ((long) BufferedChannelKt.SEGMENT_SIZE));
            if (channelSegment.id * ((long) BufferedChannelKt.SEGMENT_SIZE) >= getReceiversCounter$kotlinx_coroutines_core()) {
                return null;
            }
            channelSegment.cleanPrev();
            return null;
        }
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(channelSegment.id == id)) {
                throw new AssertionError();
            }
        }
        return channelSegment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ChannelSegment<E> findSegmentReceive(long id, ChannelSegment<E> startFrom) {
        Object objFindSegmentInternal;
        boolean z;
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = receiveSegment$FU;
        Function2 function2 = (Function2) BufferedChannelKt.createSegmentFunction();
        do {
            objFindSegmentInternal = ConcurrentLinkedListKt.findSegmentInternal(startFrom, id, function2);
            if (SegmentOrClosed.m2251isClosedimpl(objFindSegmentInternal)) {
                break;
            }
            Segment segmentM2249getSegmentimpl = SegmentOrClosed.m2249getSegmentimpl(objFindSegmentInternal);
            while (true) {
                Segment segment = (Segment) atomicReferenceFieldUpdater.get(this);
                if (segment.id >= segmentM2249getSegmentimpl.id) {
                    break;
                }
                if (!segmentM2249getSegmentimpl.tryIncPointers$kotlinx_coroutines_core()) {
                    z = false;
                    break;
                }
                if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(atomicReferenceFieldUpdater, this, segment, segmentM2249getSegmentimpl)) {
                    if (segment.decPointers$kotlinx_coroutines_core()) {
                        segment.remove();
                    }
                } else if (segmentM2249getSegmentimpl.decPointers$kotlinx_coroutines_core()) {
                    segmentM2249getSegmentimpl.remove();
                }
            }
            z = true;
        } while (!z);
        if (SegmentOrClosed.m2251isClosedimpl(objFindSegmentInternal)) {
            completeCloseOrCancel();
            if (startFrom.id * ((long) BufferedChannelKt.SEGMENT_SIZE) >= getSendersCounter$kotlinx_coroutines_core()) {
                return null;
            }
            startFrom.cleanPrev();
            return null;
        }
        ChannelSegment<E> channelSegment = (ChannelSegment) SegmentOrClosed.m2249getSegmentimpl(objFindSegmentInternal);
        if (!isRendezvousOrUnlimited() && id <= getBufferEndCounter() / ((long) BufferedChannelKt.SEGMENT_SIZE)) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = bufferEndSegment$FU;
            while (true) {
                Segment segment2 = (Segment) atomicReferenceFieldUpdater2.get(this);
                ChannelSegment<E> channelSegment2 = channelSegment;
                if (segment2.id >= channelSegment2.id || !channelSegment2.tryIncPointers$kotlinx_coroutines_core()) {
                    break;
                }
                if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(atomicReferenceFieldUpdater2, this, segment2, channelSegment2)) {
                    if (segment2.decPointers$kotlinx_coroutines_core()) {
                        segment2.remove();
                    }
                } else if (channelSegment2.decPointers$kotlinx_coroutines_core()) {
                    channelSegment2.remove();
                }
            }
        }
        if (channelSegment.id > id) {
            updateReceiversCounterIfLower(channelSegment.id * ((long) BufferedChannelKt.SEGMENT_SIZE));
            if (channelSegment.id * ((long) BufferedChannelKt.SEGMENT_SIZE) >= getSendersCounter$kotlinx_coroutines_core()) {
                return null;
            }
            channelSegment.cleanPrev();
            return null;
        }
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(channelSegment.id == id)) {
                throw new AssertionError();
            }
        }
        return channelSegment;
    }

    private final ChannelSegment<E> findSegmentBufferEnd(long id, ChannelSegment<E> startFrom, long currentBufferEndCounter) {
        Object objFindSegmentInternal;
        boolean z;
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = bufferEndSegment$FU;
        Function2 function2 = (Function2) BufferedChannelKt.createSegmentFunction();
        do {
            objFindSegmentInternal = ConcurrentLinkedListKt.findSegmentInternal(startFrom, id, function2);
            if (SegmentOrClosed.m2251isClosedimpl(objFindSegmentInternal)) {
                break;
            }
            Segment segmentM2249getSegmentimpl = SegmentOrClosed.m2249getSegmentimpl(objFindSegmentInternal);
            while (true) {
                Segment segment = (Segment) atomicReferenceFieldUpdater.get(this);
                if (segment.id >= segmentM2249getSegmentimpl.id) {
                    break;
                }
                if (!segmentM2249getSegmentimpl.tryIncPointers$kotlinx_coroutines_core()) {
                    z = false;
                    break;
                }
                if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(atomicReferenceFieldUpdater, this, segment, segmentM2249getSegmentimpl)) {
                    if (segment.decPointers$kotlinx_coroutines_core()) {
                        segment.remove();
                    }
                } else if (segmentM2249getSegmentimpl.decPointers$kotlinx_coroutines_core()) {
                    segmentM2249getSegmentimpl.remove();
                }
            }
            z = true;
        } while (!z);
        if (SegmentOrClosed.m2251isClosedimpl(objFindSegmentInternal)) {
            completeCloseOrCancel();
            moveSegmentBufferEndToSpecifiedOrLast(id, startFrom);
            incCompletedExpandBufferAttempts$default(this, 0L, 1, null);
            return null;
        }
        ChannelSegment<E> channelSegment = (ChannelSegment) SegmentOrClosed.m2249getSegmentimpl(objFindSegmentInternal);
        if (channelSegment.id > id) {
            if (bufferEnd$FU.compareAndSet(this, currentBufferEndCounter + 1, channelSegment.id * ((long) BufferedChannelKt.SEGMENT_SIZE))) {
                incCompletedExpandBufferAttempts((channelSegment.id * ((long) BufferedChannelKt.SEGMENT_SIZE)) - currentBufferEndCounter);
                return null;
            }
            incCompletedExpandBufferAttempts$default(this, 0L, 1, null);
            return null;
        }
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(channelSegment.id == id)) {
                throw new AssertionError();
            }
        }
        return channelSegment;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void moveSegmentBufferEndToSpecifiedOrLast(long id, ChannelSegment<E> startFrom) {
        boolean z;
        ChannelSegment<E> channelSegment;
        ChannelSegment<E> channelSegment2;
        while (startFrom.id < id && (channelSegment2 = (ChannelSegment) startFrom.getNext()) != null) {
            startFrom = channelSegment2;
        }
        while (true) {
            if (!startFrom.isRemoved() || (channelSegment = (ChannelSegment) startFrom.getNext()) == null) {
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = bufferEndSegment$FU;
                while (true) {
                    Segment segment = (Segment) atomicReferenceFieldUpdater.get(this);
                    ChannelSegment<E> channelSegment3 = startFrom;
                    z = true;
                    if (segment.id >= channelSegment3.id) {
                        break;
                    }
                    if (!channelSegment3.tryIncPointers$kotlinx_coroutines_core()) {
                        z = false;
                        break;
                    } else if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(atomicReferenceFieldUpdater, this, segment, channelSegment3)) {
                        if (segment.decPointers$kotlinx_coroutines_core()) {
                            segment.remove();
                        }
                    } else if (channelSegment3.decPointers$kotlinx_coroutines_core()) {
                        channelSegment3.remove();
                    }
                }
                if (z) {
                    return;
                }
            } else {
                startFrom = channelSegment;
            }
        }
    }

    private final void updateSendersCounterIfLower(long value) {
        long j;
        long j2;
        AtomicLongFieldUpdater atomicLongFieldUpdater = sendersAndCloseStatus$FU;
        do {
            j = atomicLongFieldUpdater.get(this);
            j2 = 1152921504606846975L & j;
            if (j2 >= value) {
                return;
            }
        } while (!sendersAndCloseStatus$FU.compareAndSet(this, j, BufferedChannelKt.constructSendersAndCloseStatus(j2, (int) (j >> 60))));
    }

    private final void updateReceiversCounterIfLower(long value) {
        long j;
        AtomicLongFieldUpdater atomicLongFieldUpdater = receivers$FU;
        do {
            j = atomicLongFieldUpdater.get(this);
            if (j >= value) {
                return;
            }
        } while (!receivers$FU.compareAndSet(this, j, value));
    }

    /* JADX WARN: Code restructure failed: missing block: B:94:0x01e8, code lost:
    
        r3 = (kotlinx.coroutines.channels.ChannelSegment) r3.getNext();
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x01ef, code lost:
    
        if (r3 != null) goto L101;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String toString() {
        /*
            Method dump skipped, instruction units count: 541
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.toString():java.lang.String");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final String toStringDebug$kotlinx_coroutines_core() {
        String strValueOf;
        StringBuilder sb = new StringBuilder();
        StringBuilder sbAppend = new StringBuilder("S=").append(getSendersCounter$kotlinx_coroutines_core()).append(",R=").append(getReceiversCounter$kotlinx_coroutines_core()).append(",B=").append(getBufferEndCounter()).append(",B'=").append(completedExpandBuffersAndPauseFlag$FU.get(this)).append(",C=");
        AtomicLongFieldUpdater atomicLongFieldUpdater = sendersAndCloseStatus$FU;
        sb.append(sbAppend.append((int) (atomicLongFieldUpdater.get(this) >> 60)).append(',').toString());
        int i = (int) (atomicLongFieldUpdater.get(this) >> 60);
        if (i == 1) {
            sb.append("CANCELLATION_STARTED,");
        } else if (i == 2) {
            sb.append("CLOSED,");
        } else if (i == 3) {
            sb.append("CANCELLED,");
        }
        StringBuilder sb2 = new StringBuilder("SEND_SEGM=");
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = sendSegment$FU;
        StringBuilder sbAppend2 = sb2.append(DebugStringsKt.getHexAddress(atomicReferenceFieldUpdater.get(this))).append(",RCV_SEGM=");
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = receiveSegment$FU;
        sb.append(sbAppend2.append(DebugStringsKt.getHexAddress(atomicReferenceFieldUpdater2.get(this))).toString());
        if (!isRendezvousOrUnlimited()) {
            sb.append(",EB_SEGM=" + DebugStringsKt.getHexAddress(bufferEndSegment$FU.get(this)));
        }
        sb.append("  ");
        List listListOf = CollectionsKt.listOf((Object[]) new ChannelSegment[]{atomicReferenceFieldUpdater2.get(this), atomicReferenceFieldUpdater.get(this), bufferEndSegment$FU.get(this)});
        ArrayList arrayList = new ArrayList();
        for (Object obj : listListOf) {
            if (((ChannelSegment) obj) != BufferedChannelKt.NULL_SEGMENT) {
                arrayList.add(obj);
            }
        }
        Iterator it = arrayList.iterator();
        if (!it.hasNext()) {
            throw new NoSuchElementException();
        }
        Object next = it.next();
        if (it.hasNext()) {
            long j = ((ChannelSegment) next).id;
            do {
                Object next2 = it.next();
                long j2 = ((ChannelSegment) next2).id;
                if (j > j2) {
                    next = next2;
                    j = j2;
                }
            } while (it.hasNext());
        }
        ChannelSegment channelSegment = (ChannelSegment) next;
        do {
            StringBuilder sbAppend3 = new StringBuilder().append(DebugStringsKt.getHexAddress(channelSegment)).append("=[").append(channelSegment.isRemoved() ? ProxyConfig.MATCH_ALL_SCHEMES : "").append(channelSegment.id).append(",prev=");
            ChannelSegment channelSegment2 = (ChannelSegment) channelSegment.getPrev();
            sb.append(sbAppend3.append(channelSegment2 != null ? DebugStringsKt.getHexAddress(channelSegment2) : null).append(',').toString());
            int i2 = BufferedChannelKt.SEGMENT_SIZE;
            for (int i3 = 0; i3 < i2; i3++) {
                Object state$kotlinx_coroutines_core = channelSegment.getState$kotlinx_coroutines_core(i3);
                Object element$kotlinx_coroutines_core = channelSegment.getElement$kotlinx_coroutines_core(i3);
                if (state$kotlinx_coroutines_core instanceof CancellableContinuation) {
                    strValueOf = "cont";
                } else if (state$kotlinx_coroutines_core instanceof SelectInstance) {
                    strValueOf = "select";
                } else if (state$kotlinx_coroutines_core instanceof ReceiveCatching) {
                    strValueOf = "receiveCatching";
                } else if (state$kotlinx_coroutines_core instanceof SendBroadcast) {
                    strValueOf = "send(broadcast)";
                } else {
                    strValueOf = state$kotlinx_coroutines_core instanceof WaiterEB ? "EB(" + state$kotlinx_coroutines_core + ')' : String.valueOf(state$kotlinx_coroutines_core);
                }
                sb.append("[" + i3 + "]=(" + strValueOf + ',' + element$kotlinx_coroutines_core + "),");
            }
            StringBuilder sb3 = new StringBuilder("next=");
            ChannelSegment channelSegment3 = (ChannelSegment) channelSegment.getNext();
            sb.append(sb3.append(channelSegment3 != null ? DebugStringsKt.getHexAddress(channelSegment3) : null).append("]  ").toString());
            channelSegment = (ChannelSegment) channelSegment.getNext();
        } while (channelSegment != null);
        return sb.toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0100  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void checkSegmentStructureInvariants() {
        /*
            Method dump skipped, instruction units count: 578
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.checkSegmentStructureInvariants():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object onClosedSend(E e, Continuation<? super Unit> continuation) {
        UndeliveredElementException undeliveredElementExceptionCallUndeliveredElementCatchingException$default;
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        CancellableContinuationImpl cancellableContinuationImpl2 = cancellableContinuationImpl;
        Function1<E, Unit> function1 = this.onUndeliveredElement;
        if (function1 != null && (undeliveredElementExceptionCallUndeliveredElementCatchingException$default = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default(function1, e, null, 2, null)) != null) {
            UndeliveredElementException undeliveredElementExceptionRecoverFromStackFrame = undeliveredElementExceptionCallUndeliveredElementCatchingException$default;
            ExceptionsKt.addSuppressed(undeliveredElementExceptionRecoverFromStackFrame, getSendException());
            CancellableContinuationImpl cancellableContinuationImpl3 = cancellableContinuationImpl2;
            Result.Companion companion = Result.INSTANCE;
            if (DebugKt.getRECOVER_STACK_TRACES() && (cancellableContinuationImpl3 instanceof CoroutineStackFrame)) {
                undeliveredElementExceptionRecoverFromStackFrame = StackTraceRecoveryKt.recoverFromStackFrame(undeliveredElementExceptionRecoverFromStackFrame, cancellableContinuationImpl3);
            }
            cancellableContinuationImpl3.resumeWith(Result.m684constructorimpl(ResultKt.createFailure(undeliveredElementExceptionRecoverFromStackFrame)));
        } else {
            CancellableContinuationImpl cancellableContinuationImpl4 = cancellableContinuationImpl2;
            Throwable sendException = getSendException();
            Result.Companion companion2 = Result.INSTANCE;
            if (DebugKt.getRECOVER_STACK_TRACES() && (cancellableContinuationImpl4 instanceof CoroutineStackFrame)) {
                sendException = StackTraceRecoveryKt.recoverFromStackFrame(sendException, cancellableContinuationImpl4);
            }
            cancellableContinuationImpl4.resumeWith(Result.m684constructorimpl(ResultKt.createFailure(sendException)));
        }
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0142  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x014b A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x014c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object sendOnNoWaiterSuspend(kotlinx.coroutines.channels.ChannelSegment<E> r22, int r23, E r24, long r25, kotlin.coroutines.Continuation<? super kotlin.Unit> r27) {
        /*
            Method dump skipped, instruction units count: 340
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.sendOnNoWaiterSuspend(kotlinx.coroutines.channels.ChannelSegment, int, java.lang.Object, long, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:45:0x00c8, code lost:
    
        r0 = kotlin.Result.INSTANCE;
        r11.resumeWith(kotlin.Result.m684constructorimpl(kotlin.coroutines.jvm.internal.Boxing.boxBoolean(true)));
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static /* synthetic */ <E> java.lang.Object sendBroadcast$suspendImpl(kotlinx.coroutines.channels.BufferedChannel<E> r19, E r20, kotlin.coroutines.Continuation<? super java.lang.Boolean> r21) {
        /*
            Method dump skipped, instruction units count: 241
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.sendBroadcast$suspendImpl(kotlinx.coroutines.channels.BufferedChannel, java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object receiveOnNoWaiterSuspend(ChannelSegment<E> channelSegment, int i, long j, Continuation<? super E> continuation) {
        CancellableContinuationImpl orCreateCancellableContinuation = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt.intercepted(continuation));
        try {
            Object objUpdateCellReceive = updateCellReceive(channelSegment, i, j, orCreateCancellableContinuation);
            if (objUpdateCellReceive != BufferedChannelKt.SUSPEND) {
                Function1<Throwable, Unit> function1BindCancellationFun = null;
                function1BindCancellationFun = null;
                if (objUpdateCellReceive == BufferedChannelKt.FAILED) {
                    if (j < getSendersCounter$kotlinx_coroutines_core()) {
                        channelSegment.cleanPrev();
                    }
                    ChannelSegment channelSegment2 = (ChannelSegment) receiveSegment$FU.get(this);
                    while (true) {
                        if (isClosedForReceive()) {
                            onClosedReceiveOnNoWaiterSuspend(orCreateCancellableContinuation);
                            break;
                        }
                        long andIncrement = receivers$FU.getAndIncrement(this);
                        long j2 = andIncrement / ((long) BufferedChannelKt.SEGMENT_SIZE);
                        int i2 = (int) (andIncrement % ((long) BufferedChannelKt.SEGMENT_SIZE));
                        if (channelSegment2.id != j2) {
                            ChannelSegment channelSegmentFindSegmentReceive = findSegmentReceive(j2, channelSegment2);
                            if (channelSegmentFindSegmentReceive != null) {
                                channelSegment2 = channelSegmentFindSegmentReceive;
                            }
                        }
                        objUpdateCellReceive = updateCellReceive(channelSegment2, i2, andIncrement, orCreateCancellableContinuation);
                        if (objUpdateCellReceive != BufferedChannelKt.SUSPEND) {
                            if (objUpdateCellReceive == BufferedChannelKt.FAILED) {
                                if (andIncrement < getSendersCounter$kotlinx_coroutines_core()) {
                                    channelSegment2.cleanPrev();
                                }
                            } else {
                                if (objUpdateCellReceive == BufferedChannelKt.SUSPEND_NO_WAITER) {
                                    throw new IllegalStateException("unexpected".toString());
                                }
                                channelSegment2.cleanPrev();
                                Function1<E, Unit> function1 = this.onUndeliveredElement;
                                if (function1 != null) {
                                    function1BindCancellationFun = OnUndeliveredElementKt.bindCancellationFun(function1, objUpdateCellReceive, orCreateCancellableContinuation.getContext());
                                }
                            }
                        } else {
                            CancellableContinuationImpl cancellableContinuationImpl = orCreateCancellableContinuation instanceof Waiter ? orCreateCancellableContinuation : null;
                            if (cancellableContinuationImpl != null) {
                                prepareReceiverForSuspension(cancellableContinuationImpl, channelSegment2, i2);
                            }
                        }
                    }
                } else {
                    channelSegment.cleanPrev();
                    Function1<E, Unit> function12 = this.onUndeliveredElement;
                    if (function12 != null) {
                        function1BindCancellationFun = OnUndeliveredElementKt.bindCancellationFun(function12, objUpdateCellReceive, orCreateCancellableContinuation.getContext());
                    }
                }
                orCreateCancellableContinuation.resume(objUpdateCellReceive, function1BindCancellationFun);
            } else {
                prepareReceiverForSuspension(orCreateCancellableContinuation, channelSegment, i);
            }
            Object result = orCreateCancellableContinuation.getResult();
            if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended(continuation);
            }
            return result;
        } catch (Throwable th) {
            orCreateCancellableContinuation.releaseClaimedReusableContinuation$kotlinx_coroutines_core();
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x008a, code lost:
    
        r14.selectInRegistrationPhase(kotlin.Unit.INSTANCE);
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x008f, code lost:
    
        return;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void registerSelectForSend(kotlinx.coroutines.selects.SelectInstance<?> r14, java.lang.Object r15) {
        /*
            r13 = this;
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r0 = access$getSendSegment$FU$p()
            java.lang.Object r0 = r0.get(r13)
            kotlinx.coroutines.channels.ChannelSegment r0 = (kotlinx.coroutines.channels.ChannelSegment) r0
        La:
            java.util.concurrent.atomic.AtomicLongFieldUpdater r1 = access$getSendersAndCloseStatus$FU$p()
            long r1 = r1.getAndIncrement(r13)
            r3 = 1152921504606846975(0xfffffffffffffff, double:1.2882297539194265E-231)
            long r3 = r3 & r1
            boolean r1 = access$isClosedForSend0(r13, r1)
            int r2 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            long r5 = (long) r2
            long r5 = r3 / r5
            int r2 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            long r7 = (long) r2
            long r7 = r3 % r7
            int r2 = (int) r7
            long r7 = r0.id
            int r7 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r7 == 0) goto L37
            kotlinx.coroutines.channels.ChannelSegment r5 = access$findSegmentSend(r13, r5, r0)
            if (r5 != 0) goto L36
            if (r1 == 0) goto La
            goto L75
        L36:
            r0 = r5
        L37:
            r5 = r13
            r6 = r0
            r7 = r2
            r8 = r15
            r9 = r3
            r11 = r14
            r12 = r1
            int r5 = access$updateCellSend(r5, r6, r7, r8, r9, r11, r12)
            if (r5 == 0) goto L87
            r6 = 1
            if (r5 == r6) goto L8a
            r6 = 2
            if (r5 == r6) goto L70
            r1 = 3
            if (r5 == r1) goto L64
            r1 = 4
            if (r5 == r1) goto L58
            r1 = 5
            if (r5 == r1) goto L54
            goto La
        L54:
            r0.cleanPrev()
            goto La
        L58:
            long r1 = r13.getReceiversCounter$kotlinx_coroutines_core()
            int r1 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r1 >= 0) goto L75
            r0.cleanPrev()
            goto L75
        L64:
            java.lang.IllegalStateException r14 = new java.lang.IllegalStateException
            java.lang.String r15 = "unexpected"
            java.lang.String r15 = r15.toString()
            r14.<init>(r15)
            throw r14
        L70:
            if (r1 == 0) goto L79
            r0.onSlotCleaned()
        L75:
            r13.onClosedSelectOnSend(r15, r14)
            goto L8f
        L79:
            boolean r15 = r14 instanceof kotlinx.coroutines.Waiter
            if (r15 == 0) goto L80
            kotlinx.coroutines.Waiter r14 = (kotlinx.coroutines.Waiter) r14
            goto L81
        L80:
            r14 = 0
        L81:
            if (r14 == 0) goto L8f
            access$prepareSenderForSuspension(r13, r14, r0, r2)
            goto L8f
        L87:
            r0.cleanPrev()
        L8a:
            kotlin.Unit r15 = kotlin.Unit.INSTANCE
            r14.selectInRegistrationPhase(r15)
        L8f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.registerSelectForSend(kotlinx.coroutines.selects.SelectInstance, java.lang.Object):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void registerSelectForReceive(SelectInstance<?> select, Object ignoredParam) {
        ChannelSegment channelSegment = (ChannelSegment) receiveSegment$FU.get(this);
        while (!isClosedForReceive()) {
            long andIncrement = receivers$FU.getAndIncrement(this);
            long j = andIncrement / ((long) BufferedChannelKt.SEGMENT_SIZE);
            int i = (int) (andIncrement % ((long) BufferedChannelKt.SEGMENT_SIZE));
            if (channelSegment.id != j) {
                ChannelSegment channelSegmentFindSegmentReceive = findSegmentReceive(j, channelSegment);
                if (channelSegmentFindSegmentReceive == null) {
                    continue;
                } else {
                    channelSegment = channelSegmentFindSegmentReceive;
                }
            }
            Object objUpdateCellReceive = updateCellReceive(channelSegment, i, andIncrement, select);
            if (objUpdateCellReceive != BufferedChannelKt.SUSPEND) {
                if (objUpdateCellReceive != BufferedChannelKt.FAILED) {
                    if (objUpdateCellReceive == BufferedChannelKt.SUSPEND_NO_WAITER) {
                        throw new IllegalStateException("unexpected".toString());
                    }
                    channelSegment.cleanPrev();
                    select.selectInRegistrationPhase(objUpdateCellReceive);
                    return;
                }
                if (andIncrement < getSendersCounter$kotlinx_coroutines_core()) {
                    channelSegment.cleanPrev();
                }
            } else {
                Waiter waiter = select instanceof Waiter ? (Waiter) select : null;
                if (waiter != null) {
                    prepareReceiverForSuspension(waiter, channelSegment, i);
                    return;
                }
                return;
            }
        }
        onClosedSelectOnReceive(select);
    }
}
