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
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import atirek.pothiwala.utility.R;

import static atirek.pothiwala.utility.helper.IntentHelper.ErrorText.browserNotAvailable;
import static atirek.pothiwala.utility.helper.IntentHelper.ErrorText.linkNotAvailable;
import static atirek.pothiwala.utility.helper.IntentHelper.ErrorText.locationNotAvailable;
import static atirek.pothiwala.utility.helper.IntentHelper.ErrorText.noWhatsApp;
import static atirek.pothiwala.utility.helper.IntentHelper.ErrorText.phoneNumberNotAvailable;
import static atirek.pothiwala.utility.helper.IntentHelper.ErrorText.sharedVia;

public class IntentHelper {

    interface ErrorText {
        String locationNotAvailable = "Invalid location, cannot redirect to the google maps.";
        String linkNotAvailable = "Invalid link, cannot share it.";
        String browserNotAvailable = "Invalid link, cannot redirect to the browser.";
        String phoneNumberNotAvailable = "Invalid phone number, cannot make a call.";
        String sharedVia = "%s\n\n- - - - - -\nShared via %s";
        String noWhatsApp = "Your device might not have WhatsApp installed.";
    }

    public static void phoneCall(@NonNull Context context, @Nullable String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber)){
            Toast.makeText(context, phoneNumberNotAvailable, Toast.LENGTH_SHORT).show();
            return;
        }
        String uri = "tel:" + phoneNumber.trim();
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        context.startActivity(Intent.createChooser(intent, "Choose Call Engine"));
    }

    public static void browser(@NonNull Context context, @Nullable String url) {
        if (TextUtils.isEmpty(url)) {
            Toast.makeText(context, browserNotAvailable, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

    public static void googleMaps(@NonNull Context context, @Nullable String direction) {
        if (TextUtils.isEmpty(direction)) {
            Toast.makeText(context, locationNotAvailable, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent navigationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + direction.trim() + "&mode=d"));
        navigationIntent.setPackage("com.google.android.apps.maps");
        navigationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(navigationIntent);
    }

    public static void shareLink(@NonNull Context context, @Nullable String link) {

        Resources resources = context.getResources();
        if (TextUtils.isEmpty(link)) {
            Toast.makeText(context, linkNotAvailable, Toast.LENGTH_SHORT).show();
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

    public static void whatsApp(Context context, String mobile) {

        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo info = packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            if (info != null) {
                IntentHelper.browser(context, String.format(Locale.getDefault(), "https://api.whatsapp.com/send?phone=91%s", mobile));
            } else {
                Toast.makeText(context, noWhatsApp, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, noWhatsApp, Toast.LENGTH_SHORT).show();
        }
    }

    public static void closeKeyboard(@NonNull Context context, @Nullable Dialog dialog) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            Window window = dialog != null ? dialog.getWindow() : ((Activity) context).getWindow();
            if (window != null) {
                inputMethodManager.hideSoftInputFromWindow(window.getDecorView().getRootView().getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        }
    }

    public static void restart(@NonNull Context context) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        if (intent != null) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        context.startActivity(intent);
    }

    public static Bundle startAnimate(@NonNull Activity activity, @NonNull View... view) {
        List<Pair<View, String>> pairs = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            for (View value : view) {
                pairs.add(new Pair<>(value, value.getTransitionName()));
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
