package com.github.monchenkoid.quotationapp.utils;

import java.util.Locale;

public final class StringUtil {

    private StringUtil() {
    }

    public static String format(String valueToFormat, Object... args) {
        return String.format(Locale.ENGLISH, valueToFormat, args);
    }
}
