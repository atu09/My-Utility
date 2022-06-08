package atirek.pothiwala.utility.helper;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;

public class PhotoHelper {

    private RequestCreator requestCreator;

    public PhotoHelper(@Nullable String path) {
        Picasso.get().setIndicatorsEnabled(false);
        requestCreator = Picasso.get().load(path).onlyScaleDown();
    }

    public PhotoHelper(@Nullable Uri uri) {
        Picasso.get().setIndicatorsEnabled(false);
        requestCreator = Picasso.get().load(uri).onlyScaleDown();
    }

    public PhotoHelper(@NonNull File file) {
        Picasso.get().setIndicatorsEnabled(false);
        requestCreator = Picasso.get().load(file).onlyScaleDown();
    }

    public PhotoHelper(@DrawableRes int resourceId) {
        Picasso.get().setIndicatorsEnabled(false);
        requestCreator = Picasso.get().load(resourceId).onlyScaleDown();
    }

    public PhotoHelper placeholder(@DrawableRes int resourceId) {
        if (resourceId == 0) {
            requestCreator = requestCreator.noPlaceholder();
        } else {
            requestCreator = requestCreator.placeholder(resourceId);
        }
        return this;
    }

    public PhotoHelper placeholder(@Nullable Drawable placeholderDrawable) {
        if (placeholderDrawable != null) {
            requestCreator = requestCreator.placeholder(placeholderDrawable);
        } else {
            requestCreator = requestCreator.noPlaceholder();
        }
        return this;
    }

    public PhotoHelper error(@DrawableRes int errorResId) {
        if (errorResId == 0) {
            requestCreator = requestCreator.noPlaceholder();
        } else {
            requestCreator = requestCreator.error(errorResId);
        }
        return this;
    }

    public PhotoHelper error(@Nullable Drawable errorDrawable) {
        if (errorDrawable != null) {
            requestCreator = requestCreator.error(errorDrawable);
        } else {
            requestCreator = requestCreator.noPlaceholder();
        }
        return this;
    }

    public PhotoHelper clearCache() {
        requestCreator = requestCreator.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE);
        return this;
    }

    public PhotoHelper skipCheckingInMemory() {
        requestCreator = requestCreator.memoryPolicy(MemoryPolicy.NO_CACHE);
        return this;
    }

    public PhotoHelper skipStoringInMemory() {
        requestCreator = requestCreator.memoryPolicy(MemoryPolicy.NO_STORE);
        return this;
    }

    public PhotoHelper skipCheckingInDisk() {
        requestCreator = requestCreator.networkPolicy(NetworkPolicy.NO_CACHE);
        return this;
    }

    public PhotoHelper skipStoringInDisk() {
        requestCreator = requestCreator.networkPolicy(NetworkPolicy.NO_STORE);
        return this;
    }

    public PhotoHelper useOfflineDisk() {
        requestCreator = requestCreator.networkPolicy(NetworkPolicy.OFFLINE);
        return this;
    }

    public void load(ImageView imageView) {
        requestCreator.into(imageView);
    }

    public void load(ImageView imageView, Callback callback) {
        requestCreator.into(imageView, callback);
    }

    public void fetch() {
        requestCreator.fetch();
    }

    public void fetch(Callback callback) {
        requestCreator.fetch(callback);
    }
}
