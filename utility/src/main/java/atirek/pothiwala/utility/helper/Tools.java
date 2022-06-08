package atirek.pothiwala.utility.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Atirek Pothiwala on 11/27/2017.
 */

public class Tools {

    public static void requestFullScreen(@NonNull Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static DisplayMetrics getScreenSize(@NonNull Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static double getRatio(double principal, double percent) {
        return principal * percent / 100;
    }

    public static float dpToPixels(@NonNull Context context, int dp) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    public static int getColor(Context context, @ColorRes int color) {
        return ContextCompat.getColor(context, color);
    }

    public static Drawable getDrawable(Context context, @DrawableRes int drawable) {
        return ContextCompat.getDrawable(context, drawable);
    }

}
