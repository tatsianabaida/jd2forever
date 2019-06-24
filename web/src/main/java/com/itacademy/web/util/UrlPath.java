package com.itacademy.web.util;

import lombok.experimental.UtilityClass;

import javax.servlet.http.HttpServletRequest;

@UtilityClass
public class UrlPath {

    public static final String PROFESSOR_FILTER = "/professor-filter";
    public static final String STUDENTS_LIST = "/students-list";

    public static String getReferer(HttpServletRequest request) {
        return request.getHeader("referer");
    }

}
