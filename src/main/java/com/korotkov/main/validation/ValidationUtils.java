package com.korotkov.main.validation;

import com.korotkov.main.config.PortalConstants;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.regex.Pattern;

public final class ValidationUtils implements PortalConstants {
    private static Pattern PATTERN_TEXTFIELD_CHARS = Pattern.compile("^[^@#\\$%^*}{\\]\\[\":;\\/?!><_)(]*$");
    private static Pattern PATTERN_CYRILLIC_CHARS = Pattern.compile("^[А-ЯЁа-яё -]*$");
    private static Pattern PATTERN_LATIN_CHARS = Pattern.compile("^[A-Z a-z -]*$");
    private static Pattern PATTERN_NUMBERS = Pattern.compile("^[0-9]{5,}$");
    private static Pattern LOGIN_PATTERN = Pattern.compile("[a-zA-Z\\d@#\\$%^*}{\\]\\[\":;\\/?!><,._&')(]{1,32}");
    private static Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,100}$");
    //for password ald pattern "^[a-zA-Z\\d!\"#$%&'()*+,-./:;<=>?@\\[\\]\\\\^_`{|}~]{8,100}$"
    private static Pattern PHONE_PATTERN = Pattern.compile("^380\\d{9}$");
    private static Pattern EMAIL_PATTERN =
            Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    public static String PATTERN_DATEPICKER_FORMAT = "dd.MM.yyyy";
    public static String PATTERN_DATE_FORMAT = PATTERN_DATEPICKER_FORMAT;
//    public static Pattern PATTERN_NAME = Pattern.compile("^[а-яА-ЯёЁa-zA-Z -]{1,100}$");

    private ValidationUtils(){
    }

    public static boolean isValidTextField(final String text){
        return notBlankAndMatches(text,PATTERN_TEXTFIELD_CHARS);
    }

    public static boolean isValidDateOfBirth(final LocalDate birthDate){
        try {
            if(birthDate.isAfter(LocalDate.now(ZoneId.of(TIME_ZONE)).minusYears(18))){
                return false;
            }
            return true;
        } catch (NullPointerException nullPointerException) {
            return false;
        }
    }

    public static boolean isCyrillicOrLatin(final String text){
        return isCyrillic(text) || isLatin(text);
    }

    public static boolean isCyrillic(final String text){
        return notBlankAndMatches(text, PATTERN_CYRILLIC_CHARS);
    }
    public static boolean isLatin(final String text){
        return notBlankAndMatches(text, PATTERN_LATIN_CHARS);
    }
    public static boolean isValidLogin(final String login){
        return notBlankAndMatches(login,LOGIN_PATTERN);
    }
    public static boolean isValidPassword(final String password){
        return notBlankAndMatches(password,PASSWORD_PATTERN);
    }
    public static boolean isValidEmail(final String email){
        return notBlankAndMatches(email,EMAIL_PATTERN);
    }
    public static boolean isValidNumber(final String number){
        return notBlankAndMatches(number,PATTERN_NUMBERS);
    }
    public static boolean isValidPhone(final String phone){
        return notBlankAndMatches(phone,PHONE_PATTERN);
    }

    private static boolean notBlankAndMatches(String target, Pattern pattern){
        return StringUtils.isNotBlank(target) && pattern.matcher(target).matches();
    }
}
