package com.numan.fakestoreapp.common;

import java.util.regex.Pattern;

public class InputValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%\\-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*).{8,64}$"
    );

    private static final Pattern NAME_PATTERN = Pattern.compile(
            "[a-zA-Z][a-zA-Z ]+[a-zA-Z]$"
    );

    private static final Pattern CODE_PATTERN = Pattern.compile(
            "(?<!\\d)\\d{6}(?!\\d)"
    );

    /**
     * Email Validation Function to validate user email during sigmup.
     *
     * @param email user login email
     * @return boolean after validation
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Password Validation Function using ragex which only accept password with minimum length 8 and maximum 64.
     *
     * @param password user login password
     * @return boolean after validation
     */
    public static boolean isValidPassword(String password) {
        return password != null && PASSWORD_PATTERN.matcher(password).matches();
    }

    /**
     * checks if full name is valid or not using pattern which only accept alphabets.
     *
     * @param fname username of user.
     * @return boolean after validation.
     */
    public static boolean isValidFullName(String fname) {
        return fname != null && NAME_PATTERN.matcher(fname).matches();
    }

    /**
     * checks if code is valid or not using pattern which only accepts 6 digits.
     *
     * @param code 6 digit code from user Email.
     * @return boolean after validation
     */
    public static boolean isValidCode(String code) {
        return code != null && CODE_PATTERN.matcher(code).matches();
    }

}