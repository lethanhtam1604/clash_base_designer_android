package com.lavamy.clashbasedesigner.Utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import java.util.ArrayList;

public class Utils {

    public static void openAppOnStore(Context context) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }

    public static void shareLinkStore(Context context) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=" + context.getPackageName());
        context.startActivity(Intent.createChooser(share, "Share"));
    }

    public static ArrayList<String> listFiles(String path, Context context) {

        ArrayList<String> result = new ArrayList<>();
        try {
            Resources res = context.getResources();
            AssetManager am = res.getAssets();
            String fileList[] = am.list(path);
            if (fileList != null) {
                for ( int i = 0;i<fileList.length;i++) {
                    result.add(fileList[i]);
                }
            }
        }
        catch (Exception e) {

        }
        return result;
    }

    public static boolean isConnectNetWork(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
