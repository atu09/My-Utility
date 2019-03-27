package atirek.pothiwala.utility.helper;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

public class NotificationHelper {

    private Context context;
    private NotificationManager notificationManager;
    private String channel;
    private int color = Color.WHITE;
    private boolean lights = true;
    private boolean vibrations = true;

    public NotificationHelper(Context context, String channel) {
        this.context = context;
        this.channel = channel;
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void setColor(@ColorRes int color) {
        this.color = getColor(color);
    }

    public void setLights(boolean lights) {
        this.lights = lights;
    }

    public void setVibrations(boolean vibrations) {
        this.vibrations = vibrations;
    }

    public void showNotification(@NonNull Integer notificationId, @NonNull String title, @NonNull String message, @DrawableRes int icon) {

        if (notificationManager != null) {

            NotificationCompat.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = new NotificationChannel(channel, channel, NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.enableLights(lights);
                notificationChannel.enableVibration(vibrations);
                notificationChannel.setLightColor(color);
                builder = new NotificationCompat.Builder(context, notificationChannel.getId());
            } else {
                builder = new NotificationCompat.Builder(context);
            }

            builder.setSmallIcon(icon)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setOnlyAlertOnce(true)
                    .setShowWhen(true);
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

    private int getColor(@ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getColor(color);
        } else {
            return context.getResources().getColor(color);
        }
    }

}
