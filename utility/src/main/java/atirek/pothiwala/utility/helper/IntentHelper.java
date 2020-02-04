package atirek.pothiwala.utility.helper;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import atirek.pothiwala.utility.R;

import static atirek.pothiwala.utility.helper.IntentHelper.ErrorText.linkNotAvailable;
import static atirek.pothiwala.utility.helper.IntentHelper.ErrorText.locationNotAvailable;
import static atirek.pothiwala.utility.helper.IntentHelper.ErrorText.sharedVia;

public class IntentHelper {

    interface ErrorText {
        String locationNotAvailable = "Unable to redirect to google maps.";
        String linkNotAvailable = "Unable to share the link.";
        String sharedVia = "%s\n\n- - - - - -\nShared via %s";
        String noWhatsApp = "Your device might not have WhatsApp installed.";
    }

    public static void checkLog(String TAG, Object data) {
        Log.d(TAG + ">>", data.toString());
    }

    public static void popToast(Context context, Object data) {
        try {
            if (data != null && !data.toString().trim().isEmpty()) {
                Toast.makeText(context, data.toString(), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            checkLog("popToast>>", data.toString());
        }
    }

    public static void phoneCall(Context context, String phoneNumber) {
        String uri = "tel:" + phoneNumber.trim();
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        context.startActivity(Intent.createChooser(intent, "Choose Call Engine"));
    }

    public static void openBrowser(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

    public static void openGoogleMaps(Context context, String direction) {
        if (direction == null || direction.isEmpty()) {
            popToast(context, locationNotAvailable);
            return;
        }
        Intent navigationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + direction.trim() + "&mode=d"));
        navigationIntent.setPackage("com.google.android.apps.maps");
        navigationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(navigationIntent);
    }

    public static void shareLink(Context context, String link) {

        Resources resources = context.getResources();
        if (link == null || link.isEmpty()) {
            popToast(context, linkNotAvailable);
            return;
        }

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.app_name));
        String text = String.format(Locale.getDefault(), sharedVia, link, resources.getString(R.string.app_name));
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        context.startActivity(sendIntent);

    }

    public static void openWhatsApp(Context context, String mobile) {

        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo info = packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            if (info != null) {
                IntentHelper.openBrowser(context, String.format(Locale.getDefault(), "https://api.whatsapp.com/send?phone=91%s", mobile));
            } else {
                IntentHelper.popToast(context, ErrorText.noWhatsApp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            IntentHelper.popToast(context, ErrorText.noWhatsApp);

        }
    }

    public static String currencyFormat(String text, String currencyCode) {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.ENGLISH);
        format.setCurrency(Currency.getInstance(currencyCode));
        format.setMaximumFractionDigits(0);
        return format.format(Double.valueOf(text));
    }

    public static void closeKeyboard(Context context, Dialog dialog) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            Window window = dialog != null ? dialog.getWindow() : ((Activity) context).getWindow();
            if (window != null) {
                inputMethodManager.hideSoftInputFromWindow(window.getDecorView().getRootView().getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        }
    }

    public static void restartApp(Context context) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        if (intent != null) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        context.startActivity(intent);
    }

    public static Bundle startAnimate(@NonNull Activity activity, @NonNull View... view) {
        List<Pair<View, String>> pairs = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            for (int i = 0; i < view.length; i++) {
                pairs.add(new Pair<>(view[i], view[i].getTransitionName()));
            }
        }
        Pair<View, String>[] pairArray = pairs.<Pair<View, String>>toArray(new Pair[pairs.size()]);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairArray);
        return options.toBundle();
    }

    public static void finishAnimate(Activity activity) {
        ActivityCompat.finishAfterTransition(activity);
    }

}
