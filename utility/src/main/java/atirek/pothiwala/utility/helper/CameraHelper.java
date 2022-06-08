package atirek.pothiwala.utility.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.content.FileProvider;


import java.io.File;
import java.util.Date;
import java.util.List;

public class CameraHelper {

    public interface CameraHelperListener {
        void onPhotoCaptured(String filePath);
    }

    private final static String TAG = "file_picker>>";
    private final static int REQUEST_CODE = 2022;
    private final Activity activity;
    private CameraHelperListener cameraHelperListener;
    private String capturedFilePath;
    private Uri capturedFileUri;

    public CameraHelper(Activity activity) {
        this.activity = activity;
    }

    public void setListener(CameraHelperListener cameraHelperListener) {
        this.cameraHelperListener = cameraHelperListener;
    }

    public void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (!storageDir.exists()) {
                Log.d(TAG, "Directory Created");
                storageDir.mkdirs();
            }
            File photo = File.createTempFile(
                    "photo_" + (new Date()).getTime(),
                    ".jpg",
                    storageDir);

            String authority = activity.getPackageName() + ".fileprovider";
            capturedFilePath = photo.getPath();
            capturedFileUri = FileProvider.getUriForFile(activity, authority, photo);
            Log.d(TAG, "Path: " + capturedFilePath);
            Log.d(TAG, "Uri: " + capturedFileUri.toString());

            //We have to explicitly grant the write permission since Intent.setFlag works only on API Level >=20
            grantWritePermission(activity, intent, capturedFileUri);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, capturedFileUri);
            activity.startActivityForResult(intent, REQUEST_CODE);

        } catch (Exception e) {
            Log.d(TAG, "Error: " + e.getMessage());
            e.printStackTrace();
        }

    }

    private static void revokeWritePermission(Context context, Uri uri) {
        context.revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
    }

    @SuppressLint("QueryPermissionsNeeded")
    private static void grantWritePermission(Context context, Intent intent, Uri uri) {
        List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
    }

    public void onActivityResult(int requestCode, int resultCode) {
        Log.d(TAG, "onActivityResult: RequestCode = " + requestCode + ", Result Code = " + resultCode);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            revokeWritePermission(activity, capturedFileUri);
            capturedFileUri = null;

            if (cameraHelperListener != null) {
                cameraHelperListener.onPhotoCaptured(capturedFilePath);
            }
        }
    }

}
