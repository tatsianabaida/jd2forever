package com.itacademy.database.util;

import lombok.experimental.UtilityClass;

import static java.util.Objects.isNull;

@UtilityClass
public class StringUtils {

    private static final String EMPTY = "";

    public boolean isEmpty(String string) {
        return isNull(string) || EMPTY.equals(string.trim());
    }

    public boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }
}
