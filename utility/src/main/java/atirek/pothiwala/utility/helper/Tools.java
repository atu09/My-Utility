package atirek.pothiwala.utility.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getColor(color);
        } else {
            return context.getResources().getColor(color);
        }
    }

    public static Drawable getDrawable(Context context, @DrawableRes int drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getDrawable(drawable);
        } else {
            return context.getResources().getDrawable(drawable);
        }
    }

    public static void loadImage(@NonNull ImageView imageView, @Nullable String imageUrl, @DrawableRes int placeHolder, boolean isCache) {

        Picasso.get().setIndicatorsEnabled(false);
        if (imageUrl == null || imageUrl.isEmpty()) {
            imageView.setImageResource(placeHolder);
            return;
        }
        RequestCreator requestCreator = Picasso.get().load(imageUrl);
        if (placeHolder == 0) {
            requestCreator = requestCreator.noPlaceholder();
        } else {
            requestCreator = requestCreator.error(placeHolder).placeholder(placeHolder);
        }
        if (!isCache) {
            requestCreator = requestCreator.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE);
        }
        requestCreator.into(imageView);
    }

}
