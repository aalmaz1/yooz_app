package io.flutter.embedding.engine.systemchannels;

import android.os.Bundle;
import com.tekartik.sqflite.Constant;
import io.flutter.Log;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.JSONMethodCodec;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.editing.TextEditingDelta;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class TextInputChannel {
    private static final String TAG = "TextInputChannel";
    public final MethodChannel channel;
    final MethodChannel.MethodCallHandler parsingMethodHandler;
    private TextInputMethodHandler textInputMethodHandler;

    public interface TextInputMethodHandler {
        void clearClient();

        void finishAutofillContext(boolean z);

        void hide();

        void requestAutofill();

        void sendAppPrivateCommand(String str, Bundle bundle);

        void setClient(int i, Configuration configuration);

        void setEditableSizeAndTransform(double d, double d2, double[] dArr);

        void setEditingState(TextEditState textEditState);

        void setPlatformViewClient(int i, boolean z);

        void show();
    }

    public TextInputChannel(DartExecutor dartExecutor) {
        MethodChannel.MethodCallHandler methodCallHandler = new MethodChannel.MethodCallHandler() { // from class: io.flutter.embedding.engine.systemchannels.TextInputChannel.1
            @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
            public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
                Object obj;
                int i;
                Bundle bundle;
                if (TextInputChannel.this.textInputMethodHandler == null) {
                }
                String str = methodCall.method;
                obj = methodCall.arguments;
                Log.v(TextInputChannel.TAG, "Received '" + str + "' message.");
                str.hashCode();
                switch (str) {
                    case "TextInput.setPlatformViewClient":
                        try {
                            JSONObject jSONObject = (JSONObject) obj;
                            TextInputChannel.this.textInputMethodHandler.setPlatformViewClient(jSONObject.getInt("platformViewId"), jSONObject.optBoolean("usesVirtualDisplay", false));
                            result.success(null);
                            break;
                        } catch (JSONException e) {
                            result.error(Constant.PARAM_ERROR, e.getMessage(), null);
                            return;
                        }
                        break;
                    case "TextInput.setEditingState":
                        try {
                            TextInputChannel.this.textInputMethodHandler.setEditingState(TextEditState.fromJson((JSONObject) obj));
                            result.success(null);
                            break;
                        } catch (JSONException e2) {
                            result.error(Constant.PARAM_ERROR, e2.getMessage(), null);
                            return;
                        }
                        break;
                    case "TextInput.setClient":
                        try {
                            JSONArray jSONArray = (JSONArray) obj;
                            TextInputChannel.this.textInputMethodHandler.setClient(jSONArray.getInt(0), Configuration.fromJson(jSONArray.getJSONObject(1)));
                            result.success(null);
                            break;
                        } catch (NoSuchFieldException | JSONException e3) {
                            result.error(Constant.PARAM_ERROR, e3.getMessage(), null);
                            return;
                        }
                        break;
                    case "TextInput.hide":
                        TextInputChannel.this.textInputMethodHandler.hide();
                        result.success(null);
                        break;
                    case "TextInput.show":
                        TextInputChannel.this.textInputMethodHandler.show();
                        result.success(null);
                        break;
                    case "TextInput.sendAppPrivateCommand":
                        try {
                            JSONObject jSONObject2 = (JSONObject) obj;
                            String string = jSONObject2.getString("action");
                            String string2 = jSONObject2.getString("data");
                            if (string2 == null || string2.isEmpty()) {
                                bundle = null;
                            } else {
                                bundle = new Bundle();
                                bundle.putString("data", string2);
                            }
                            TextInputChannel.this.textInputMethodHandler.sendAppPrivateCommand(string, bundle);
                            result.success(null);
                            break;
                        } catch (JSONException e4) {
                            result.error(Constant.PARAM_ERROR, e4.getMessage(), null);
                            return;
                        }
                        break;
                    case "TextInput.setEditableSizeAndTransform":
                        try {
                            JSONObject jSONObject3 = (JSONObject) obj;
                            double d = jSONObject3.getDouble("width");
                            double d2 = jSONObject3.getDouble("height");
                            JSONArray jSONArray2 = jSONObject3.getJSONArray("transform");
                            double[] dArr = new double[16];
                            for (i = 0; i < 16; i++) {
                                dArr[i] = jSONArray2.getDouble(i);
                            }
                            TextInputChannel.this.textInputMethodHandler.setEditableSizeAndTransform(d, d2, dArr);
                            result.success(null);
                            break;
                        } catch (JSONException e5) {
                            result.error(Constant.PARAM_ERROR, e5.getMessage(), null);
                            return;
                        }
                        break;
                    case "TextInput.finishAutofillContext":
                        TextInputChannel.this.textInputMethodHandler.finishAutofillContext(((Boolean) obj).booleanValue());
                        result.success(null);
                        break;
                    case "TextInput.clearClient":
                        TextInputChannel.this.textInputMethodHandler.clearClient();
                        result.success(null);
                        break;
                    case "TextInput.requestAutofill":
                        TextInputChannel.this.textInputMethodHandler.requestAutofill();
                        result.success(null);
                        break;
                    default:
                        result.notImplemented();
                        break;
                }
            }
        };
        this.parsingMethodHandler = methodCallHandler;
        MethodChannel methodChannel = new MethodChannel(dartExecutor, "flutter/textinput", JSONMethodCodec.INSTANCE);
        this.channel = methodChannel;
        methodChannel.setMethodCallHandler(methodCallHandler);
    }

    public void requestExistingInputState() {
        this.channel.invokeMethod("TextInputClient.requestExistingInputState", null);
    }

    private static HashMap<Object, Object> createEditingStateJSON(String str, int i, int i2, int i3, int i4) {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("text", str);
        map.put("selectionBase", Integer.valueOf(i));
        map.put("selectionExtent", Integer.valueOf(i2));
        map.put("composingBase", Integer.valueOf(i3));
        map.put("composingExtent", Integer.valueOf(i4));
        return map;
    }

    private static HashMap<Object, Object> createEditingDeltaJSON(ArrayList<TextEditingDelta> arrayList) {
        HashMap<Object, Object> map = new HashMap<>();
        JSONArray jSONArray = new JSONArray();
        Iterator<TextEditingDelta> it = arrayList.iterator();
        while (it.hasNext()) {
            jSONArray.put(it.next().toJSON());
        }
        map.put("deltas", jSONArray);
        return map;
    }

    public void updateEditingState(int i, String str, int i2, int i3, int i4, int i5) {
        Log.v(TAG, "Sending message to update editing state: \nText: " + str + "\nSelection start: " + i2 + "\nSelection end: " + i3 + "\nComposing start: " + i4 + "\nComposing end: " + i5);
        this.channel.invokeMethod("TextInputClient.updateEditingState", Arrays.asList(Integer.valueOf(i), createEditingStateJSON(str, i2, i3, i4, i5)));
    }

    public void updateEditingStateWithDeltas(int i, ArrayList<TextEditingDelta> arrayList) {
        Log.v(TAG, "Sending message to update editing state with deltas: \nNumber of deltas: " + arrayList.size());
        this.channel.invokeMethod("TextInputClient.updateEditingStateWithDeltas", Arrays.asList(Integer.valueOf(i), createEditingDeltaJSON(arrayList)));
    }

    public void updateEditingStateWithTag(int i, HashMap<String, TextEditState> map) {
        Log.v(TAG, "Sending message to update editing state for " + String.valueOf(map.size()) + " field(s).");
        HashMap map2 = new HashMap();
        for (Map.Entry<String, TextEditState> entry : map.entrySet()) {
            TextEditState value = entry.getValue();
            map2.put(entry.getKey(), createEditingStateJSON(value.text, value.selectionStart, value.selectionEnd, -1, -1));
        }
        this.channel.invokeMethod("TextInputClient.updateEditingStateWithTag", Arrays.asList(Integer.valueOf(i), map2));
    }

    public void newline(int i) {
        Log.v(TAG, "Sending 'newline' message.");
        this.channel.invokeMethod("TextInputClient.performAction", Arrays.asList(Integer.valueOf(i), "TextInputAction.newline"));
    }

    public void go(int i) {
        Log.v(TAG, "Sending 'go' message.");
        this.channel.invokeMethod("TextInputClient.performAction", Arrays.asList(Integer.valueOf(i), "TextInputAction.go"));
    }

    public void search(int i) {
        Log.v(TAG, "Sending 'search' message.");
        this.channel.invokeMethod("TextInputClient.performAction", Arrays.asList(Integer.valueOf(i), "TextInputAction.search"));
    }

    public void send(int i) {
        Log.v(TAG, "Sending 'send' message.");
        this.channel.invokeMethod("TextInputClient.performAction", Arrays.asList(Integer.valueOf(i), "TextInputAction.send"));
    }

    public void done(int i) {
        Log.v(TAG, "Sending 'done' message.");
        this.channel.invokeMethod("TextInputClient.performAction", Arrays.asList(Integer.valueOf(i), "TextInputAction.done"));
    }

    public void next(int i) {
        Log.v(TAG, "Sending 'next' message.");
        this.channel.invokeMethod("TextInputClient.performAction", Arrays.asList(Integer.valueOf(i), "TextInputAction.next"));
    }

    public void previous(int i) {
        Log.v(TAG, "Sending 'previous' message.");
        this.channel.invokeMethod("TextInputClient.performAction", Arrays.asList(Integer.valueOf(i), "TextInputAction.previous"));
    }

    public void unspecifiedAction(int i) {
        Log.v(TAG, "Sending 'unspecified' message.");
        this.channel.invokeMethod("TextInputClient.performAction", Arrays.asList(Integer.valueOf(i), "TextInputAction.unspecified"));
    }

    public void commitContent(int i, Map<String, Object> map) {
        Log.v(TAG, "Sending 'commitContent' message.");
        this.channel.invokeMethod("TextInputClient.performAction", Arrays.asList(Integer.valueOf(i), "TextInputAction.commitContent", map));
    }

    public void performPrivateCommand(int i, String str, Bundle bundle) {
        HashMap map = new HashMap();
        map.put("action", str);
        if (bundle != null) {
            HashMap map2 = new HashMap();
            for (String str2 : bundle.keySet()) {
                Object obj = bundle.get(str2);
                if (obj instanceof byte[]) {
                    map2.put(str2, bundle.getByteArray(str2));
                } else if (obj instanceof Byte) {
                    map2.put(str2, Byte.valueOf(bundle.getByte(str2)));
                } else if (obj instanceof char[]) {
                    map2.put(str2, bundle.getCharArray(str2));
                } else if (obj instanceof Character) {
                    map2.put(str2, Character.valueOf(bundle.getChar(str2)));
                } else if (obj instanceof CharSequence[]) {
                    map2.put(str2, bundle.getCharSequenceArray(str2));
                } else if (obj instanceof CharSequence) {
                    map2.put(str2, bundle.getCharSequence(str2));
                } else if (obj instanceof float[]) {
                    map2.put(str2, bundle.getFloatArray(str2));
                } else if (obj instanceof Float) {
                    map2.put(str2, Float.valueOf(bundle.getFloat(str2)));
                }
            }
            map.put("data", map2);
        }
        this.channel.invokeMethod("TextInputClient.performPrivateCommand", Arrays.asList(Integer.valueOf(i), map));
    }

    public void onConnectionClosed(int i) {
        Log.v(TAG, "Sending 'onConnectionClosed' message.");
        this.channel.invokeMethod("TextInputClient.onConnectionClosed", Arrays.asList(Integer.valueOf(i), "TextInputClient.onConnectionClosed"));
    }

    public void setTextInputMethodHandler(TextInputMethodHandler textInputMethodHandler) {
        this.textInputMethodHandler = textInputMethodHandler;
    }

    public static class Configuration {
        public final String actionLabel;
        public final boolean autocorrect;
        public final Autofill autofill;
        public final String[] contentCommitMimeTypes;
        public final boolean enableDeltaModel;
        public final boolean enableIMEPersonalizedLearning;
        public final boolean enableSuggestions;
        public final Configuration[] fields;
        public final Integer inputAction;
        public final InputType inputType;
        public final boolean obscureText;
        public final TextCapitalization textCapitalization;

        public static Configuration fromJson(JSONObject jSONObject) throws JSONException, NoSuchFieldException {
            Configuration[] configurationArr;
            String string = jSONObject.getString("inputAction");
            if (string == null) {
                throw new JSONException("Configuration JSON missing 'inputAction' property.");
            }
            if (jSONObject.isNull("fields")) {
                configurationArr = null;
            } else {
                JSONArray jSONArray = jSONObject.getJSONArray("fields");
                int length = jSONArray.length();
                Configuration[] configurationArr2 = new Configuration[length];
                for (int i = 0; i < length; i++) {
                    configurationArr2[i] = fromJson(jSONArray.getJSONObject(i));
                }
                configurationArr = configurationArr2;
            }
            Integer numInputActionFromTextInputAction = inputActionFromTextInputAction(string);
            ArrayList arrayList = new ArrayList();
            JSONArray jSONArray2 = jSONObject.isNull("contentCommitMimeTypes") ? null : jSONObject.getJSONArray("contentCommitMimeTypes");
            if (jSONArray2 != null) {
                for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                    arrayList.add(jSONArray2.optString(i2));
                }
            }
            return new Configuration(jSONObject.optBoolean("obscureText"), jSONObject.optBoolean("autocorrect", true), jSONObject.optBoolean("enableSuggestions"), jSONObject.optBoolean("enableIMEPersonalizedLearning"), jSONObject.optBoolean("enableDeltaModel"), TextCapitalization.fromValue(jSONObject.getString("textCapitalization")), InputType.fromJson(jSONObject.getJSONObject("inputType")), numInputActionFromTextInputAction, jSONObject.isNull("actionLabel") ? null : jSONObject.getString("actionLabel"), jSONObject.isNull("autofill") ? null : Autofill.fromJson(jSONObject.getJSONObject("autofill")), (String[]) arrayList.toArray(new String[arrayList.size()]), configurationArr);
        }

        private static Integer inputActionFromTextInputAction(String str) {
            str.hashCode();
            switch (str) {
                case "TextInputAction.unspecified":
                    return 0;
                case "TextInputAction.done":
                    return 6;
                case "TextInputAction.next":
                    return 5;
                case "TextInputAction.none":
                    return 1;
                case "TextInputAction.send":
                    return 4;
                case "TextInputAction.search":
                    return 3;
                case "TextInputAction.go":
                    return 2;
                case "TextInputAction.newline":
                    return 1;
                case "TextInputAction.previous":
                    return 7;
                default:
                    return 0;
            }
        }

        public static class Autofill {
            public final TextEditState editState;
            public final String hintText;
            public final String[] hints;
            public final String uniqueIdentifier;

            public static Autofill fromJson(JSONObject jSONObject) throws JSONException, NoSuchFieldException {
                String string = jSONObject.getString("uniqueIdentifier");
                JSONArray jSONArray = jSONObject.getJSONArray("hints");
                String string2 = jSONObject.isNull("hintText") ? null : jSONObject.getString("hintText");
                JSONObject jSONObject2 = jSONObject.getJSONObject("editingValue");
                String[] strArr = new String[jSONArray.length()];
                for (int i = 0; i < jSONArray.length(); i++) {
                    strArr[i] = translateAutofillHint(jSONArray.getString(i));
                }
                return new Autofill(string, strArr, string2, TextEditState.fromJson(jSONObject2));
            }

            private static String translateAutofillHint(String str) {
                str.hashCode();
                switch (str) {
                    case "birthdayYear":
                        return "birthDateYear";
                    case "oneTimeCode":
                        return "smsOTPCode";
                    case "creditCardExpirationMonth":
                        return "creditCardExpirationMonth";
                    case "telephoneNumberNational":
                        return "phoneNational";
                    case "newPassword":
                        return "newPassword";
                    case "creditCardSecurityCode":
                        return "creditCardSecurityCode";
                    case "creditCardExpirationDay":
                        return "creditCardExpirationDay";
                    case "fullStreetAddress":
                        return "streetAddress";
                    case "telephoneNumberDevice":
                        return "phoneNumberDevice";
                    case "countryName":
                        return "addressCountry";
                    case "middleInitial":
                        return "personMiddleInitial";
                    case "addressCity":
                        return "addressLocality";
                    case "gender":
                        return "gender";
                    case "postalAddressExtendedPostalCode":
                        return "extendedPostalCode";
                    case "creditCardNumber":
                        return "creditCardNumber";
                    case "namePrefix":
                        return "personNamePrefix";
                    case "middleName":
                        return "personMiddleName";
                    case "nameSuffix":
                        return "personNameSuffix";
                    case "creditCardExpirationDate":
                        return "creditCardExpirationDate";
                    case "creditCardExpirationYear":
                        return "creditCardExpirationYear";
                    case "telephoneNumberCountryCode":
                        return "phoneCountryCode";
                    case "username":
                        return "username";
                    case "name":
                        return "personName";
                    case "email":
                        return "emailAddress";
                    case "addressState":
                        return "addressRegion";
                    case "birthdayMonth":
                        return "birthDateMonth";
                    case "familyName":
                        return "personFamilyName";
                    case "telephoneNumber":
                        return "phoneNumber";
                    case "newUsername":
                        return "newUsername";
                    case "birthday":
                        return "birthDateFull";
                    case "password":
                        return "password";
                    case "givenName":
                        return "personGivenName";
                    case "postalAddress":
                        return "postalAddress";
                    case "postalAddressExtended":
                        return "extendedAddress";
                    case "postalCode":
                        return "postalCode";
                    case "birthdayDay":
                        return "birthDateDay";
                    default:
                        return str;
                }
            }

            public Autofill(String str, String[] strArr, String str2, TextEditState textEditState) {
                this.uniqueIdentifier = str;
                this.hints = strArr;
                this.hintText = str2;
                this.editState = textEditState;
            }
        }

        public Configuration(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, TextCapitalization textCapitalization, InputType inputType, Integer num, String str, Autofill autofill, String[] strArr, Configuration[] configurationArr) {
            this.obscureText = z;
            this.autocorrect = z2;
            this.enableSuggestions = z3;
            this.enableIMEPersonalizedLearning = z4;
            this.enableDeltaModel = z5;
            this.textCapitalization = textCapitalization;
            this.inputType = inputType;
            this.inputAction = num;
            this.actionLabel = str;
            this.autofill = autofill;
            this.contentCommitMimeTypes = strArr;
            this.fields = configurationArr;
        }
    }

    public static class InputType {
        public final boolean isDecimal;
        public final boolean isSigned;
        public final TextInputType type;

        public static InputType fromJson(JSONObject jSONObject) throws JSONException, NoSuchFieldException {
            return new InputType(TextInputType.fromValue(jSONObject.getString("name")), jSONObject.optBoolean("signed", false), jSONObject.optBoolean("decimal", false));
        }

        public InputType(TextInputType textInputType, boolean z, boolean z2) {
            this.type = textInputType;
            this.isSigned = z;
            this.isDecimal = z2;
        }
    }

    public enum TextInputType {
        TEXT("TextInputType.text"),
        DATETIME("TextInputType.datetime"),
        NAME("TextInputType.name"),
        POSTAL_ADDRESS("TextInputType.address"),
        NUMBER("TextInputType.number"),
        PHONE("TextInputType.phone"),
        MULTILINE("TextInputType.multiline"),
        EMAIL_ADDRESS("TextInputType.emailAddress"),
        URL("TextInputType.url"),
        VISIBLE_PASSWORD("TextInputType.visiblePassword"),
        NONE("TextInputType.none");

        private final String encodedName;

        static TextInputType fromValue(String str) throws NoSuchFieldException {
            for (TextInputType textInputType : values()) {
                if (textInputType.encodedName.equals(str)) {
                    return textInputType;
                }
            }
            throw new NoSuchFieldException("No such TextInputType: " + str);
        }

        TextInputType(String str) {
            this.encodedName = str;
        }
    }

    public enum TextCapitalization {
        CHARACTERS("TextCapitalization.characters"),
        WORDS("TextCapitalization.words"),
        SENTENCES("TextCapitalization.sentences"),
        NONE("TextCapitalization.none");

        private final String encodedName;

        static TextCapitalization fromValue(String str) throws NoSuchFieldException {
            for (TextCapitalization textCapitalization : values()) {
                if (textCapitalization.encodedName.equals(str)) {
                    return textCapitalization;
                }
            }
            throw new NoSuchFieldException("No such TextCapitalization: " + str);
        }

        TextCapitalization(String str) {
            this.encodedName = str;
        }
    }

    public static class TextEditState {
        public final int composingEnd;
        public final int composingStart;
        public final int selectionEnd;
        public final int selectionStart;
        public final String text;

        public static TextEditState fromJson(JSONObject jSONObject) throws JSONException {
            return new TextEditState(jSONObject.getString("text"), jSONObject.getInt("selectionBase"), jSONObject.getInt("selectionExtent"), jSONObject.getInt("composingBase"), jSONObject.getInt("composingExtent"));
        }

        public TextEditState(String str, int i, int i2, int i3, int i4) throws IndexOutOfBoundsException {
            if (!(i == -1 && i2 == -1) && (i < 0 || i2 < 0)) {
                throw new IndexOutOfBoundsException("invalid selection: (" + String.valueOf(i) + ", " + String.valueOf(i2) + ")");
            }
            if ((i3 != -1 || i4 != -1) && (i3 < 0 || i3 > i4)) {
                throw new IndexOutOfBoundsException("invalid composing range: (" + String.valueOf(i3) + ", " + String.valueOf(i4) + ")");
            }
            if (i4 > str.length()) {
                throw new IndexOutOfBoundsException("invalid composing start: " + String.valueOf(i3));
            }
            if (i > str.length()) {
                throw new IndexOutOfBoundsException("invalid selection start: " + String.valueOf(i));
            }
            if (i2 > str.length()) {
                throw new IndexOutOfBoundsException("invalid selection end: " + String.valueOf(i2));
            }
            this.text = str;
            this.selectionStart = i;
            this.selectionEnd = i2;
            this.composingStart = i3;
            this.composingEnd = i4;
        }

        public boolean hasSelection() {
            return this.selectionStart >= 0;
        }

        public boolean hasComposing() {
            int i = this.composingStart;
            return i >= 0 && this.composingEnd > i;
        }
    }
}
