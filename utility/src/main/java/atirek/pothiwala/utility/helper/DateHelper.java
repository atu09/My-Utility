package atirek.pothiwala.utility.helper;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.Window;
import android.widget.LinearLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateHelper {

    public static String getFormatDateText(@NonNull String date, @NonNull String fromFormat, @NonNull String toFormat) {

        try {
            SimpleDateFormat format = new SimpleDateFormat(fromFormat, Locale.getDefault());
            Date dateInstance = format.parse(date);
            format.applyPattern(toFormat);
            return format.format(dateInstance);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getFormatDateText(@NonNull String date, @NonNull String fromFormat) {

        try {
            SimpleDateFormat format = new SimpleDateFormat(fromFormat, Locale.getDefault());
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static DatePickerDialog getDatePicker(@NonNull Context context, @Nullable Date minDate, @NonNull DatePickerDialog.OnDateSetListener listener) {

        Calendar calendar = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(context, listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.CENTER);
            if (minDate != null) {
                dialog.getDatePicker().setMinDate(minDate.getTime());
            }
            dialog.setTitle(null);
        }
        return dialog;
    }

    public static String getDateText(@NonNull String dateText, @NonNull String fromFormat) {

        Date targetDate = getFormatDateText(dateText, fromFormat);
        if (targetDate != null){
            return getDateText(targetDate);
        }
        return null;
    }

    public static String getDateText(@NonNull Date targetDate) {

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM, yyyy", Locale.getDefault());

        Calendar targetCalendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        targetCalendar.setTime(targetDate);

        Calendar currentCalendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        currentCalendar.setTime(new Date());

        int currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH);
        int currentMonth = currentCalendar.get(Calendar.MONTH) - 1;
        int currentYear = currentCalendar.get(Calendar.YEAR);

        int targetDay = targetCalendar.get(Calendar.DAY_OF_MONTH);
        int targetMonth = targetCalendar.get(Calendar.MONTH) - 1;
        int targetYear = targetCalendar.get(Calendar.YEAR);

        String returnDate;

        if (currentYear > targetYear) {
            if (currentYear - targetYear == 1) {
                returnDate = dateFormatter.format(targetDate);
            } else {
                returnDate = dateFormatter.format(targetDate);
            }
        } else if (currentYear < targetYear) {
            if (currentYear - targetYear == -1) {
                returnDate = dateFormatter.format(targetDate);
            } else {
                returnDate = dateFormatter.format(targetDate);
            }
        } else {
            if (currentMonth > targetMonth) {
                if (currentMonth - targetMonth == 1) {
                    returnDate = dateFormatter.format(targetDate);
                } else {
                    returnDate = dateFormatter.format(targetDate);
                }
            } else if (currentMonth < targetMonth) {
                if (currentMonth - targetMonth == -1) {
                    returnDate = dateFormatter.format(targetDate);
                } else {
                    returnDate = dateFormatter.format(targetDate);
                }
            } else {
                if (currentDay > targetDay) {
                    if (currentDay - targetDay == 1) {
                        returnDate = "Yesterday";
                    } else {
                        returnDate = dateFormatter.format(targetDate);
                    }
                } else if (currentDay < targetDay) {
                    if (currentDay - targetDay == -1) {
                        returnDate = "Tomorrow";
                    } else {
                        returnDate = dateFormatter.format(targetDate);
                    }
                } else {
                    returnDate = "Today";
                }
            }
        }

        return returnDate;

    }
}
