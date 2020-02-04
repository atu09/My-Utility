package atirek.pothiwala.utility.helper;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import androidx.annotation.FontRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import static android.text.Html.FROM_HTML_MODE_LEGACY;

public class TextHelper {

    public static String capitalizeWord(@Nullable String text) {

        if (text == null) {
            return "";
        } else {
            text = text.trim().toLowerCase();
        }
        StringBuilder stringBuilder = new StringBuilder();

        // Declare a character of space
        // To identify that the next character is the starting
        // of a new word
        char ch = ' ';
        for (int i = 0; i < text.length(); i++) {

            // If previous character is space and current
            // character is not space then it shows that
            // current letter is the starting of the word
            if (ch == ' ' && text.charAt(i) != ' ')
                stringBuilder.append(Character.toUpperCase(text.charAt(i)));
            else
                stringBuilder.append(text.charAt(i));
            ch = text.charAt(i);
        }

        // Return the string with trimming
        return stringBuilder.toString().trim();
    }

    public static String capitalizeSentence(@Nullable String text) {

        if (text == null) {
            return "";
        } else {
            text = text.trim().toLowerCase();
        }

        // Create a char array of given String
        char charArray[] = text.toCharArray();
        for (int i = 0; i < text.length(); i++) {

            // If first character of a word is found
            if (i == 0 && charArray[i] != ' ' ||
                    charArray[i] != ' ' && charArray[i - 1] == ' ') {

                // If it is in lower-case
                if (charArray[i] >= 'a' && charArray[i] <= 'z') {

                    // Convert into Upper-case
                    charArray[i] = (char) (charArray[i] - 'a' + 'A');
                }
            }

            // If apart from first character
            // Any one is in Upper-case
            else if (charArray[i] >= 'A' && charArray[i] <= 'Z')

                // Convert into Lower-Case
                charArray[i] = (char) (charArray[i] + 'a' - 'A');
        }
        // Convert the char array to equivalent String
        return new String(charArray);
    }

    public static String getCurrencyFormat(@NonNull String currencyFormat, double value) {
        Currency usd = Currency.getInstance(currencyFormat);
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.ENGLISH);
        format.setCurrency(usd);
        return format.format(value);
    }

    public static SpannableString getStyle(@NonNull Context context, @NonNull String currentString, @FontRes int font) {

        SpannableString updatedString = new SpannableString(currentString);
        final Typeface custom_font = ResourcesCompat.getFont(context, font);
        MetricAffectingSpan span = new MetricAffectingSpan() {
            @Override
            public void updateMeasureState(TextPaint paint) {
                paint.setTypeface(custom_font);
            }

            @Override
            public void updateDrawState(TextPaint paint) {
                paint.setTypeface(custom_font);
            }
        };

        updatedString.setSpan(span, 0, updatedString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return updatedString;
    }

    public static Spanned getHtml(@NonNull String html) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(html, FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(html);
        }
    }

    public static void strikeTextView(@NonNull TextView view, boolean strike) {
        if (strike) {
            view.setPaintFlags(view.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            view.setPaintFlags(view.getPaintFlags());
        }
    }
}
