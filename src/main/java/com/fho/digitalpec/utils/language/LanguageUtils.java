package com.fho.digitalpec.utils.language;

import java.util.Locale;

import org.springframework.context.MessageSource;

public class LanguageUtils {

    public static String getMessage(MessageSource messageSource, String code, Language language) {
        return messageSource.getMessage(code, null, Locale.forLanguageTag(language.name()));
    }

    public static String getMessage(MessageSource messageSource, String code, Language language,
                                    Object... arguments) {
        return messageSource.getMessage(code, arguments, Locale.forLanguageTag(language.name()));
    }
}
