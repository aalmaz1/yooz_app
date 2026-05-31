package com.amazonaws.amplify.amplify_analytics_pinpoint;

import android.util.Log;
import com.amazonaws.amplify.amplify_analytics_pinpoint.Messages;
import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.common.StandardMessageCodec;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class Messages {

    public interface Result<T> {
        void error(Throwable th);

        void success(T t);
    }

    public static class FlutterError extends RuntimeException {
        public final String code;
        public final Object details;

        public FlutterError(String str, String str2, Object obj) {
            super(str2);
            this.code = str;
            this.details = obj;
        }
    }

    protected static ArrayList<Object> wrapError(Throwable th) {
        ArrayList<Object> arrayList = new ArrayList<>(3);
        if (th instanceof FlutterError) {
            FlutterError flutterError = (FlutterError) th;
            arrayList.add(flutterError.code);
            arrayList.add(flutterError.getMessage());
            arrayList.add(flutterError.details);
        } else {
            arrayList.add(th.toString());
            arrayList.add(th.getClass().getSimpleName());
            arrayList.add("Cause: " + th.getCause() + ", Stacktrace: " + Log.getStackTraceString(th));
        }
        return arrayList;
    }

    public interface PigeonLegacyDataProvider {
        void getEndpointId(String str, Result<String> result);

        static MessageCodec<Object> getCodec() {
            return new StandardMessageCodec();
        }

        static void setup(BinaryMessenger binaryMessenger, final PigeonLegacyDataProvider pigeonLegacyDataProvider) {
            BasicMessageChannel basicMessageChannel = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.amplify_analytics_pinpoint.PigeonLegacyDataProvider.getEndpointId", getCodec());
            if (pigeonLegacyDataProvider != null) {
                basicMessageChannel.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: com.amazonaws.amplify.amplify_analytics_pinpoint.Messages$PigeonLegacyDataProvider$$ExternalSyntheticLambda0
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        Messages.PigeonLegacyDataProvider.lambda$setup$0(this.f$0, obj, reply);
                    }
                });
            } else {
                basicMessageChannel.setMessageHandler(null);
            }
        }

        static /* synthetic */ void lambda$setup$0(PigeonLegacyDataProvider pigeonLegacyDataProvider, Object obj, final BasicMessageChannel.Reply reply) {
            final ArrayList arrayList = new ArrayList();
            pigeonLegacyDataProvider.getEndpointId((String) ((ArrayList) obj).get(0), new Result<String>() { // from class: com.amazonaws.amplify.amplify_analytics_pinpoint.Messages.PigeonLegacyDataProvider.1
                @Override // com.amazonaws.amplify.amplify_analytics_pinpoint.Messages.Result
                public void success(String str) {
                    arrayList.add(0, str);
                    reply.reply(arrayList);
                }

                @Override // com.amazonaws.amplify.amplify_analytics_pinpoint.Messages.Result
                public void error(Throwable th) {
                    reply.reply(Messages.wrapError(th));
                }
            });
        }
    }
}
