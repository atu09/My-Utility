package atirek.pothiwala.utility.helper;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Locale;

public class NotificationHelper {

    private final Context context;
    private final NotificationManagerCompat notificationManager;
    private final String channelId;
    private final String channelName;
    private int color = Color.WHITE;
    private boolean lights = true;
    private boolean vibrations = true;
    private @DrawableRes
    int icon;
    private Uri sound = Settings.System.DEFAULT_NOTIFICATION_URI;
    public static final int requestCode = 2121;

    private Bitmap bitmapImage;
    private Intent intent;

    public NotificationHelper(Context context) {
        this.context = context;
        this.channelId = String.format(Locale.getDefault(), "%s.channel.id", context.getPackageName());
        this.channelName = String.format(Locale.getDefault(), "%s.channel", context.getPackageName());
        this.notificationManager = NotificationManagerCompat.from(context);
    }

    public void setColor(@ColorRes int color) {
        this.color = Tools.getColor(context, color);
    }

    public void setLights(boolean lights) {
        this.lights = lights;
    }

    public void setVibrations(boolean vibrations) {
        this.vibrations = vibrations;
    }

    public void setIcon(@DrawableRes int icon) {
        this.icon = icon;
    }

    public void setSound(@NonNull Uri sound) {
        this.sound = sound;
    }

    private void setChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(lights);
            notificationChannel.enableVibration(vibrations);
            notificationChannel.setLightColor(color);

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();

            notificationChannel.setSound(sound, audioAttributes);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public void setBitmapImage(@Nullable Bitmap bitmapImage) {
        this.bitmapImage = bitmapImage;
    }

    public void setIntent(@Nullable Intent intent) {
        this.intent = intent;
    }

    public void showNotification(@NonNull Integer notificationId, @NonNull String title, @NonNull String message) {
        if (notificationManager != null) {
            setChannel();

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId);
            builder.setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(icon)
                    .setSound(sound)
                    .setAutoCancel(true)
                    .setOnlyAlertOnce(true)
                    .setShowWhen(true);

            if (bitmapImage != null) {
                NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle()
                        .setBigContentTitle(title)
                        .bigPicture(bitmapImage);
                builder.setStyle(style);
            } else {
                NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle()
                        .setBigContentTitle(title)
                        .bigText(message);
                builder.setStyle(style);
            }
            if (intent != null) {
                PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
            }
            notificationManager.notify(notificationId, builder.build());
        }
    }

    public void clearAllNotifications() {
        if (notificationManager != null) {
            notificationManager.cancelAll();
        }
    }

    public void removeNotification(@NonNull Integer notificationId) {
        if (notificationManager != null) {
            notificationManager.cancel(notificationId);
        }
    }
}
