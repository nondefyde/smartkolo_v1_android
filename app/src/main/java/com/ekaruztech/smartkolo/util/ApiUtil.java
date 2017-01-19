package com.ekaruztech.smartkolo.util;


/**
 * Created by Emmanuel on 11/29/2016.
 */

public class ApiUtil {

    public static final String TAG = ApiUtil.class
            .getSimpleName();

    private static void closeLoader(boolean show_loader){
        if (show_loader) {
            DialogUtils.closeProgress();
        }
    }
}
