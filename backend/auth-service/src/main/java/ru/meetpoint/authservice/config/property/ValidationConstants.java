package ru.meetpoint.authservice.config.property;

public class ValidationConstants {

    public static final int FIRST_NAME_MIN_LENGTH = 2;

    public static final int FIRST_NAME_MAX_LENGTH = 50;

    public static final int MIDDLE_NAME_MIN_LENGTH = 2;

    public static final int MIDDLE_NAME_MAX_LENGTH = 50;

    public static final int LAST_NAME_MIN_LENGTH = 2;

    public static final int LAST_NAME_MAX_LENGTH = 50;

    public static final int BIRTHDATE_MIN_YEAR = 1905;

    public static final int EMAIL_MIN_LENGTH = 6;

    public static final int EMAIL_MAX_LENGTH = 60;

    public static final int PHONE_NUMBER_LENGTH = 18;

    public static final String PHONE_PATTERN = "^\\+7 \\(\\d{3}\\) \\d{3}-\\d{2}-\\d{2}$";

    public static final int RAW_PASSWORD_MIN_LENGTH = 8;

    public static final int RAW_PASSWORD_MAX_LENGTH = 30;

    public static final int CITY_NAME_MIN_LENGTH = 3;

    public static final int CITY_NAME_MAX_LENGTH = 30;

}
