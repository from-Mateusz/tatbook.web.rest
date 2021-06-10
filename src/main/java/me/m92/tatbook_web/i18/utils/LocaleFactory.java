package me.m92.tatbook_web.i18.utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LocaleFactory {

    private static final Map<String, Locale> locales = new HashMap<>();

    /**
     * Returns appropriate locale based on country code "ISO 693-1 (double chars code)", or if locale is not found, returns default locale
     * @param code
     * @return
     */

    static {
        populateLocales();
    }

    public static Locale create(String code) {
        Locale locale = locales.get(code);
        return null != locale ? locale : locales.get("en");
    }

    public static Locale createDefault() {
        return locales.get("en");
    }

    private static void populateLocales() {
        locales.putAll(
                Map.of("pl", polish(), "us", Locale.US, "en", Locale.ENGLISH)
        );
    }

    private static Locale polish() {
        return new Locale("pl", "PL");
    }
}
