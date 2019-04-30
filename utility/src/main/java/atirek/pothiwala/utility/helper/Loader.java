package atirek.pothiwala.utility.helper;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class Loader {

    private Context context;
    private Dialog dialog;
    private int color = Color.BLACK;
    private DialogInterface.OnCancelListener onCancelListener;

    public Loader(Context context) {
        this.context = context;
        dialog = new Dialog(context);
    }

    public void setColor(@ColorRes int color) {
        this.color = getColor(color);
    }

    public void setCancelListener(@Nullable DialogInterface.OnCancelListener onCancelListener) {
        this.onCancelListener = onCancelListener;
    }

    public Dialog getDialog() {

        Window window = dialog.getWindow();
        if (window != null) {
            window.requestFeature(Window.FEATURE_NO_TITLE);
            RelativeLayout relativeLayout = new RelativeLayout(context);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            relativeLayout.setLayoutParams(layoutParams);

            ProgressBar progressBar = new ProgressBar(context);
            progressBar.getIndeterminateDrawable().setColorFilter(color, android.graphics.PorterDuff.Mode.MULTIPLY);

            RelativeLayout.LayoutParams progressParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            progressParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            progressBar.setLayoutParams(progressParams);
            relativeLayout.addView(progressBar);

            window.setContentView(relativeLayout, layoutParams);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(onCancelListener != null);
        dialog.setOnCancelListener(onCancelListener);

        return dialog;
    }

    private int getColor(@ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getColor(color);
        } else {
            return context.getResources().getColor(color);
        }
    }
}
