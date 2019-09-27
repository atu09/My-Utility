package atirek.pothiwala.utility.helper;

import android.widget.EditText;

import java.util.Locale;
import java.util.regex.Pattern;


import static atirek.pothiwala.utility.helper.ValidationHelper.ErrorText.cannotBeEmpty;
import static atirek.pothiwala.utility.helper.ValidationHelper.ErrorText.noSpaces;
import static atirek.pothiwala.utility.helper.ValidationHelper.ErrorText.invalidEmail;
import static atirek.pothiwala.utility.helper.ValidationHelper.ErrorText.requiresDigits;
import static atirek.pothiwala.utility.helper.ValidationHelper.ErrorText.invalidPhone;
import static atirek.pothiwala.utility.helper.ValidationHelper.ErrorText.requiresLetters;

public class ValidationHelper {

    public interface ErrorText {
        String cannotBeEmpty = "This field cannot be empty.";
        String noSpaces = "This field cannot not contain spaces.";
        String invalidEmail = "Email address is invalid.";
        String invalidPhone = "Phone number is invalid.";
        String requiresDigits = "Requires exactly %d digits.";
        String requiresLetters = "Requires at least %d letters.";
    }

    public static boolean isNonEmpty(EditText editText) {
        String string = editText.getText().toString().trim();
        if (string.isEmpty()) {
            editText.setError(cannotBeEmpty);
            editText.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean isValidEmail(EditText editText) {
        String string = editText.getText().toString().trim();

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);

        if (string.isEmpty()) {
            editText.setError(cannotBeEmpty);
            editText.requestFocus();
            return false;
        } else if (string.contains(" ")) {
            editText.setError(noSpaces);
            editText.requestFocus();
            return false;
        } else if (!pattern.matcher(string).matches()) {
            editText.setError(invalidEmail);
            editText.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean isValidPhoneNumber(EditText editText, int limit) {
        String string = editText.getText().toString().trim();
        String expression = "[0-9]+";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);

        if (string.isEmpty()) {
            editText.setError(cannotBeEmpty);
            editText.requestFocus();
            return false;
        } else if (string.length() < limit) {
            editText.setError(String.format(Locale.getDefault(), requiresDigits, limit));
            editText.requestFocus();
            return false;
        } else if (string.contains(" ")) {
            editText.setError(noSpaces);
            editText.requestFocus();
            return false;
        } else if (!pattern.matcher(string).matches()) {
            editText.setError(invalidPhone);
            editText.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean isValidPhoneNumber(EditText editText) {
        return isValidPhoneNumber(editText, 10);
    }

    public static boolean isValidString(EditText editText, int length) {
        String string = editText.getText().toString().trim();
        if (string.isEmpty()) {
            editText.setError(cannotBeEmpty);
            editText.requestFocus();
            return false;
        } else if (string.length() < length) {
            editText.setError(String.format(Locale.getDefault(), requiresLetters, length));
            editText.requestFocus();
            return false;
        } else if (string.contains(" ")) {
            editText.setError(noSpaces);
            editText.requestFocus();
            return false;
        }
        return true;
    }

}
