package atirek.pothiwala.utility.helper;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionHelper {

    public static boolean checkPermissions(Context context, String[] permissions) {

        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(context, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static void requestPermissions(Context context, String[] permissions, int request) {
        ActivityCompat.requestPermissions((Activity) context, permissions, request);
    }
}
