package edu.msu.pastyrn1.project2;

import android.graphics.Color;

public enum PasswordStrength {

    //set Colors to emphasize password strength
    WEAK(R.string.weak, Color.parseColor("#FF0000")),
    MEDIUM(R.string.medium, Color.parseColor("#FF8000")),
    STRONG(R.string.strong, Color.parseColor("#61ad85")),
    VERY_STRONG(R.string.very_strong, Color.parseColor("#3a674f"));


    public int msg;
    public int color;
    private static int MIN_LENGTH = 5;
    private static int MAX_LENGTH = 10;

    PasswordStrength(int msg, int color) {
        this.msg = msg;
        this.color = color;
    }

    public static PasswordStrength calculate(String password) {
        int score = 0;
        boolean upper = false; // if password has an upper case
        boolean lower = false; // if password has a lower case
        boolean digit = false; // if password has at least one digit
        boolean specChar = false; // if password has a least one special char

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);

            if (!specChar  &&  !Character.isLetterOrDigit(c)) {
                score++;
                specChar = true;
            } else {
                if (!digit  &&  Character.isDigit(c)) {
                    score++;
                    digit = true;
                } else {
                    if (!upper || !lower) {
                        if (Character.isUpperCase(c)) {
                            upper = true;
                        } else {
                            lower = true;
                        }

                        if (upper && lower) {
                            score++;
                        }
                    }
                }
            }
        }

        int length = password.length();

        if (length > MAX_LENGTH) {
            score++;
        } else if (length < MIN_LENGTH) {
            score = 0;
        }

        // return enum following the score
        switch(score) {
            case 0 : return WEAK;
            case 1 : return MEDIUM;
            case 2 : return STRONG;
            case 3 : return VERY_STRONG;
            default:
        }

        return VERY_STRONG;
    }
}
