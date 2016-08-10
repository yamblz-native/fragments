package ru.yandex.yamblz.utils;

import android.content.Context;
import android.content.pm.PackageManager;

import java.util.List;

import timber.log.Timber;


public class Utils {
    public static String convertToString(List list, Character separator) {
        if (list == null) return "";

        StringBuilder resultString = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            resultString.append(list.get(i).toString());
            if (i < list.size() - 1) {
                resultString.append(separator).append(' ');
            }
        }
        return resultString.toString();
    }

    public static String getAppVersion(Context context) {
        String versionCode = "1.0";
        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Timber.e(e, "getAppVersion: Could not get package info");
            e.printStackTrace();
        }

        return versionCode;
    }

    public static boolean checkPackageExists(Context context, String targetPackage) {
        try {
            context.getPackageManager().getPackageInfo(targetPackage, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }


}
