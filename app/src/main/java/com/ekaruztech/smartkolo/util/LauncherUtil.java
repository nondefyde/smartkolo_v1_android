package com.ekaruztech.smartkolo.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by mac on 7/30/15.
 */
public class LauncherUtil {

    public static void LaunchClass(Activity context, Class<?> cla, Bundle bundle, boolean isNewTask) {
        Intent intent = new Intent(context, cla);
        if (bundle != null)
            intent.putExtras(bundle);
        if (isNewTask)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public static void LaunchActivityForResult(Activity context, Class<?> cla, Bundle bundle, int requestCode) {
        Intent intent = new Intent(context, cla);
        if (bundle != null)
            intent.putExtras(bundle);
        context.startActivityForResult(intent, requestCode);
        context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public static void LaunchClass(Context context, Class<?> cla, Bundle bundle, boolean isNewTask) {
        Intent intent = new Intent(context, cla);
        if (bundle != null)
            intent.putExtras(bundle);
        if (isNewTask)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        //context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
