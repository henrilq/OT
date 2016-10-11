package com.openteam.ot.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by zoz on 25/05/2016.
 */
public class PermissionUtils {

    public static final String TAG = "PermissionUtils";

    public static boolean checkPermission(Context context, String permission){
        int res = ActivityCompat.checkSelfPermission(context, permission);
        return res == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission(Activity activity, String permission, boolean forcePermission){
        if (!checkPermission(activity, permission)) {
            if (forcePermission || ! ActivityCompat.shouldShowRequestPermissionRationale(activity,permission)) {
                ActivityCompat.requestPermissions(activity, new String[] { permission },1);
            }
        }
    }

    public static void requestPermission(Activity activity, String permission){
        requestPermission(activity, permission, false);
    }

}
