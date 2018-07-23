package ru.popov.bodya.core.resources;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

/**
 * @author popovbodya
 */

public class ResourceManager {

    private final Context mContext;

    public ResourceManager(Context context) {
        mContext = context;
    }

    public PackageManager getPackageManager() {
        return mContext.getPackageManager();
    }

    public String getPackageName() {
        return mContext.getPackageName();
    }

    public String getString(@StringRes int resId) {
        return mContext.getString(resId);
    }

    public String getString(@StringRes int resId, Object... formatArgs) {
        return mContext.getResources().getString(resId, formatArgs);
    }

    public int getColorResource(@ColorRes int resId) {
        return ContextCompat.getColor(mContext, resId);
    }
}
