package io.flutter.plugins.sharedpreferences;

import android.util.Log;
import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugins.sharedpreferences.Messages;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public class Messages {

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

    public interface SharedPreferencesApi {
        Boolean clear(String str, List<String> list);

        Map<String, Object> getAll(String str, List<String> list);

        Boolean remove(String str);

        Boolean setBool(String str, Boolean bool);

        Boolean setDouble(String str, Double d);

        Boolean setInt(String str, Long l);

        Boolean setString(String str, String str2);

        Boolean setStringList(String str, List<String> list);

        static MessageCodec<Object> getCodec() {
            return new StandardMessageCodec();
        }

        static void setup(BinaryMessenger binaryMessenger, final SharedPreferencesApi sharedPreferencesApi) {
            BasicMessageChannel basicMessageChannel = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.SharedPreferencesApi.remove", getCodec(), binaryMessenger.makeBackgroundTaskQueue());
            if (sharedPreferencesApi != null) {
                basicMessageChannel.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.sharedpreferences.Messages$SharedPreferencesApi$$ExternalSyntheticLambda0
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        Messages.SharedPreferencesApi.lambda$setup$0(this.f$0, obj, reply);
                    }
                });
            } else {
                basicMessageChannel.setMessageHandler(null);
            }
            BasicMessageChannel basicMessageChannel2 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.SharedPreferencesApi.setBool", getCodec(), binaryMessenger.makeBackgroundTaskQueue());
            if (sharedPreferencesApi != null) {
                basicMessageChannel2.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.sharedpreferences.Messages$SharedPreferencesApi$$ExternalSyntheticLambda1
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        Messages.SharedPreferencesApi.lambda$setup$1(this.f$0, obj, reply);
                    }
                });
            } else {
                basicMessageChannel2.setMessageHandler(null);
            }
            BasicMessageChannel basicMessageChannel3 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.SharedPreferencesApi.setString", getCodec(), binaryMessenger.makeBackgroundTaskQueue());
            if (sharedPreferencesApi != null) {
                basicMessageChannel3.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.sharedpreferences.Messages$SharedPreferencesApi$$ExternalSyntheticLambda2
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        Messages.SharedPreferencesApi.lambda$setup$2(this.f$0, obj, reply);
                    }
                });
            } else {
                basicMessageChannel3.setMessageHandler(null);
            }
            BasicMessageChannel basicMessageChannel4 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.SharedPreferencesApi.setInt", getCodec(), binaryMessenger.makeBackgroundTaskQueue());
            if (sharedPreferencesApi != null) {
                basicMessageChannel4.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.sharedpreferences.Messages$SharedPreferencesApi$$ExternalSyntheticLambda3
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        Messages.SharedPreferencesApi.lambda$setup$3(this.f$0, obj, reply);
                    }
                });
            } else {
                basicMessageChannel4.setMessageHandler(null);
            }
            BasicMessageChannel basicMessageChannel5 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.SharedPreferencesApi.setDouble", getCodec(), binaryMessenger.makeBackgroundTaskQueue());
            if (sharedPreferencesApi != null) {
                basicMessageChannel5.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.sharedpreferences.Messages$SharedPreferencesApi$$ExternalSyntheticLambda4
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        Messages.SharedPreferencesApi.lambda$setup$4(this.f$0, obj, reply);
                    }
                });
            } else {
                basicMessageChannel5.setMessageHandler(null);
            }
            BasicMessageChannel basicMessageChannel6 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.SharedPreferencesApi.setStringList", getCodec(), binaryMessenger.makeBackgroundTaskQueue());
            if (sharedPreferencesApi != null) {
                basicMessageChannel6.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.sharedpreferences.Messages$SharedPreferencesApi$$ExternalSyntheticLambda5
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        Messages.SharedPreferencesApi.lambda$setup$5(this.f$0, obj, reply);
                    }
                });
            } else {
                basicMessageChannel6.setMessageHandler(null);
            }
            BasicMessageChannel basicMessageChannel7 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.SharedPreferencesApi.clear", getCodec(), binaryMessenger.makeBackgroundTaskQueue());
            if (sharedPreferencesApi != null) {
                basicMessageChannel7.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.sharedpreferences.Messages$SharedPreferencesApi$$ExternalSyntheticLambda6
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        Messages.SharedPreferencesApi.lambda$setup$6(this.f$0, obj, reply);
                    }
                });
            } else {
                basicMessageChannel7.setMessageHandler(null);
            }
            BasicMessageChannel basicMessageChannel8 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.SharedPreferencesApi.getAll", getCodec(), binaryMessenger.makeBackgroundTaskQueue());
            if (sharedPreferencesApi != null) {
                basicMessageChannel8.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.sharedpreferences.Messages$SharedPreferencesApi$$ExternalSyntheticLambda7
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        Messages.SharedPreferencesApi.lambda$setup$7(this.f$0, obj, reply);
                    }
                });
            } else {
                basicMessageChannel8.setMessageHandler(null);
            }
        }

        static /* synthetic */ void lambda$setup$0(SharedPreferencesApi sharedPreferencesApi, Object obj, BasicMessageChannel.Reply reply) {
            ArrayList<Object> arrayList = new ArrayList<>();
            try {
                arrayList.add(0, sharedPreferencesApi.remove((String) ((ArrayList) obj).get(0)));
            } catch (Throwable th) {
                arrayList = Messages.wrapError(th);
            }
            reply.reply(arrayList);
        }

        static /* synthetic */ void lambda$setup$1(SharedPreferencesApi sharedPreferencesApi, Object obj, BasicMessageChannel.Reply reply) {
            ArrayList<Object> arrayList = new ArrayList<>();
            ArrayList arrayList2 = (ArrayList) obj;
            try {
                arrayList.add(0, sharedPreferencesApi.setBool((String) arrayList2.get(0), (Boolean) arrayList2.get(1)));
            } catch (Throwable th) {
                arrayList = Messages.wrapError(th);
            }
            reply.reply(arrayList);
        }

        static /* synthetic */ void lambda$setup$2(SharedPreferencesApi sharedPreferencesApi, Object obj, BasicMessageChannel.Reply reply) {
            ArrayList<Object> arrayList = new ArrayList<>();
            ArrayList arrayList2 = (ArrayList) obj;
            try {
                arrayList.add(0, sharedPreferencesApi.setString((String) arrayList2.get(0), (String) arrayList2.get(1)));
            } catch (Throwable th) {
                arrayList = Messages.wrapError(th);
            }
            reply.reply(arrayList);
        }

        static /* synthetic */ void lambda$setup$3(SharedPreferencesApi sharedPreferencesApi, Object obj, BasicMessageChannel.Reply reply) {
            Long lValueOf;
            ArrayList<Object> arrayList = new ArrayList<>();
            ArrayList arrayList2 = (ArrayList) obj;
            String str = (String) arrayList2.get(0);
            Number number = (Number) arrayList2.get(1);
            if (number == null) {
                lValueOf = null;
            } else {
                try {
                    lValueOf = Long.valueOf(number.longValue());
                } catch (Throwable th) {
                    arrayList = Messages.wrapError(th);
                }
            }
            arrayList.add(0, sharedPreferencesApi.setInt(str, lValueOf));
            reply.reply(arrayList);
        }

        static /* synthetic */ void lambda$setup$4(SharedPreferencesApi sharedPreferencesApi, Object obj, BasicMessageChannel.Reply reply) {
            ArrayList<Object> arrayList = new ArrayList<>();
            ArrayList arrayList2 = (ArrayList) obj;
            try {
                arrayList.add(0, sharedPreferencesApi.setDouble((String) arrayList2.get(0), (Double) arrayList2.get(1)));
            } catch (Throwable th) {
                arrayList = Messages.wrapError(th);
            }
            reply.reply(arrayList);
        }

        static /* synthetic */ void lambda$setup$5(SharedPreferencesApi sharedPreferencesApi, Object obj, BasicMessageChannel.Reply reply) {
            ArrayList<Object> arrayList = new ArrayList<>();
            ArrayList arrayList2 = (ArrayList) obj;
            try {
                arrayList.add(0, sharedPreferencesApi.setStringList((String) arrayList2.get(0), (List) arrayList2.get(1)));
            } catch (Throwable th) {
                arrayList = Messages.wrapError(th);
            }
            reply.reply(arrayList);
        }

        static /* synthetic */ void lambda$setup$6(SharedPreferencesApi sharedPreferencesApi, Object obj, BasicMessageChannel.Reply reply) {
            ArrayList<Object> arrayList = new ArrayList<>();
            ArrayList arrayList2 = (ArrayList) obj;
            try {
                arrayList.add(0, sharedPreferencesApi.clear((String) arrayList2.get(0), (List) arrayList2.get(1)));
            } catch (Throwable th) {
                arrayList = Messages.wrapError(th);
            }
            reply.reply(arrayList);
        }

        static /* synthetic */ void lambda$setup$7(SharedPreferencesApi sharedPreferencesApi, Object obj, BasicMessageChannel.Reply reply) {
            ArrayList<Object> arrayList = new ArrayList<>();
            ArrayList arrayList2 = (ArrayList) obj;
            try {
                arrayList.add(0, sharedPreferencesApi.getAll((String) arrayList2.get(0), (List) arrayList2.get(1)));
            } catch (Throwable th) {
                arrayList = Messages.wrapError(th);
            }
            reply.reply(arrayList);
        }
    }
}
