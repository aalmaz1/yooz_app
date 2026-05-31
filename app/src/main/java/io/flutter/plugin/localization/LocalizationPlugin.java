package io.flutter.plugin.localization;

import android.content.Context;
import android.content.res.Configuration;
import android.os.LocaleList;
import io.flutter.embedding.engine.systemchannels.LocalizationChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: classes2.dex */
public class LocalizationPlugin {
    private final Context context;
    private final LocalizationChannel localizationChannel;
    final LocalizationChannel.LocalizationMessageHandler localizationMessageHandler;

    public LocalizationPlugin(Context context, LocalizationChannel localizationChannel) {
        LocalizationChannel.LocalizationMessageHandler localizationMessageHandler = new LocalizationChannel.LocalizationMessageHandler() { // from class: io.flutter.plugin.localization.LocalizationPlugin.1
            @Override // io.flutter.embedding.engine.systemchannels.LocalizationChannel.LocalizationMessageHandler
            public String getStringResource(String str, String str2) {
                Context contextCreateConfigurationContext = LocalizationPlugin.this.context;
                if (str2 != null) {
                    Locale localeLocaleFromString = LocalizationPlugin.localeFromString(str2);
                    Configuration configuration = new Configuration(LocalizationPlugin.this.context.getResources().getConfiguration());
                    configuration.setLocale(localeLocaleFromString);
                    contextCreateConfigurationContext = LocalizationPlugin.this.context.createConfigurationContext(configuration);
                }
                int identifier = contextCreateConfigurationContext.getResources().getIdentifier(str, "string", LocalizationPlugin.this.context.getPackageName());
                if (identifier != 0) {
                    return contextCreateConfigurationContext.getResources().getString(identifier);
                }
                return null;
            }
        };
        this.localizationMessageHandler = localizationMessageHandler;
        this.context = context;
        this.localizationChannel = localizationChannel;
        localizationChannel.setLocalizationMessageHandler(localizationMessageHandler);
    }

    public Locale resolveNativeLocale(List<Locale> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        LocaleList locales = this.context.getResources().getConfiguration().getLocales();
        int size = locales.size();
        for (int i = 0; i < size; i++) {
            Locale locale = locales.get(i);
            String language = locale.getLanguage();
            if (!locale.getScript().isEmpty()) {
                language = language + "-" + locale.getScript();
            }
            if (!locale.getCountry().isEmpty()) {
                language = language + "-" + locale.getCountry();
            }
            arrayList.add(new Locale.LanguageRange(language));
            arrayList.add(new Locale.LanguageRange(locale.getLanguage()));
            arrayList.add(new Locale.LanguageRange(locale.getLanguage() + "-*"));
        }
        Locale localeLookup = Locale.lookup(arrayList, list);
        return localeLookup != null ? localeLookup : list.get(0);
    }

    public void sendLocalesToFlutter(Configuration configuration) {
        ArrayList arrayList = new ArrayList();
        LocaleList locales = configuration.getLocales();
        int size = locales.size();
        for (int i = 0; i < size; i++) {
            arrayList.add(locales.get(i));
        }
        this.localizationChannel.sendLocales(arrayList);
    }

    public static Locale localeFromString(String str) {
        String str2;
        String[] strArrSplit = str.replace('_', '-').split("-", -1);
        String str3 = strArrSplit[0];
        String str4 = "";
        int i = 1;
        if (strArrSplit.length <= 1 || strArrSplit[1].length() != 4) {
            str2 = "";
        } else {
            str2 = strArrSplit[1];
            i = 2;
        }
        if (strArrSplit.length > i && strArrSplit[i].length() >= 2 && strArrSplit[i].length() <= 3) {
            str4 = strArrSplit[i];
        }
        return new Locale(str3, str4, str2);
    }
}
