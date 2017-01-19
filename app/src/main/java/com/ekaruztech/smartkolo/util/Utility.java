package com.ekaruztech.smartkolo.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

    Context context;
    ArrayList<Bitmap> ImageList;

    public Utility(Context context) {
        this.context = context;

    }

    public boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

    public void Message(String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("MyToddlr");
        alertDialog.setMessage(msg);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();

    }

    public void MessageWithFinish(final Activity activity, String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("MyToddlr");
        alertDialog.setMessage(msg);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                activity.finish();
            }
        });
        alertDialog.show();
    }


    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static Bitmap decodeFile(File f) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE = 70;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {

        }

        return null;
    }

    public static Bitmap getLotated(Bitmap bitmap) {

        Bitmap scaledBMP = null;

        // Getting width & height of the given image.
        if (bitmap != null) {
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();
            // Setting post rotate to 90
            Matrix mtx = new Matrix();
            mtx.postRotate(90);
            // Rotating Bitmap
            Bitmap rotatedBMP = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);

            int width = rotatedBMP.getWidth();
            int height = rotatedBMP.getHeight();
            int maxSize = 300;
            float bitmapRatio = (float) width / (float) height;
            if (bitmapRatio > 0) {
                width = maxSize;
                height = (int) (width / bitmapRatio);
            } else {
                height = maxSize;
                width = (int) (height * bitmapRatio);
            }

            scaledBMP = Bitmap.createScaledBitmap(rotatedBMP, width, height, false);
        }

        return scaledBMP;
    }

    public static String loadJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static void saveObjectToFile(Context context, Object object,  String filename) {
        try {
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(object);
            os.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object getObjectFromFile(Context context, String filename) {

        Object simpleClass = null;
        try {
            FileInputStream fis = context.openFileInput(filename);
            ObjectInputStream is = new ObjectInputStream(fis);
            simpleClass = is.readObject();
            is.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return simpleClass;
    }


//    public static <T> void saveObject(Context context, Object object, Class<T> aClass, boolean is_array,  String filename) {
//
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor prefsEditor = preferences.edit();
//
//        Gson gson = new Gson();
//        String json = null;
//        if(is_array){
//            json = gson.toJson(object, new TypeToken<List<T>>(){}.getType());
//        }
//        else{
//            json = gson.toJson(object, aClass);
//        }
//        Logger.write("getObject : "+json);
//        prefsEditor.putString(filename, json);
//        prefsEditor.apply();
//    }
//
//    public static <T> void saveListObject(Context context, List<T> list, Class<T> aClass,String filename) {
//
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor prefsEditor = preferences.edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(list, new TypeToken<List<T>>(){}.getType());
//        Logger.write("getObject : "+json);
//        prefsEditor.putString(filename, json);
//        prefsEditor.apply();
//    }
//
//    public static <T> T getObject(Context context, Class<T> aClass, boolean is_array, String filename) {
//
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//        String json = preferences.getString(filename, null);
//        Logger.write("getObject : "+json);
//        Gson gson = new Gson();
//        return gson.fromJson(json, aClass);
//    }
//
//    public static <T> List<T> getListObject(Context context, Class<T> aClass, String filename) {
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//        String json = preferences.getString(filename, null);
//        Logger.write("getObject : "+json);
//        Gson gson = new Gson();
//        ArrayList<T> arrayList =  gson.fromJson(json,new TypeToken<List<T>>(){}.getType());
//        ArrayList<T> list = new ArrayList<>();
//        for(int i=0; i<arrayList.size(); i++) {
//            LinkedTreeMap linkedTreeMap = (LinkedTreeMap) arrayList.get(i);
//            String json_string = linkedTreeMap.toString();
//            Logger.write("json_string : "+json_string);
//            list.add(gson.fromJson(json_string, aClass));
//        }
//        Logger.write("arrayList : "+arrayList);
//        return arrayList;
//    }
}
