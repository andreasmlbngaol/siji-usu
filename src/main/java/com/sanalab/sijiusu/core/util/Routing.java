package com.sanalab.sijiusu.core.util;

public class Routing {
    public static final String AUTH = "/auth";
    public static final String LOGIN = "/login";
    public static final String REFRESH = "/refresh";
    public static final String LOGOUT = "/logout";

    public static final String STUDENTS = "/students";
    public static final String LECTURERS = "/lecturers";
    public static final String FACULTIES = "/faculties";
    public static final String MAJORS = "/majors";
    public static final String DEPARTMENTS = "/departments";
    public static final String ROOMS = "/rooms";
    public static final String COURSES = "/courses";
    public static final String SECTIONS = "/sections";

    public static final String ADMINS = "/admins";
    public static final String ADMINS_USERS = ADMINS + "/users";
    public static final String ADMINS_ACADEMIC = ADMINS + "/academic";
    public static final String ADMINS_ACADEMIC_FACULTIES = ADMINS_ACADEMIC + FACULTIES;
    public static final String ADMINS_ACADEMIC_DEPARTMENTS = ADMINS_ACADEMIC + DEPARTMENTS;
}
