package com.ingamedeo.widgetdemo;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Random;

public class WidgetReceiver extends AppWidgetProvider {

    @Override
    public void onDeleted(Context c, int[] appWidgetIds) {

        Log.i("log_tag", "onDelete Called!");

        SharedPreferences mShared = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor mEditor = mShared.edit();

        for (int ID : appWidgetIds) {
            mEditor.remove("widget_" + ID);
            Log.i("log_tag", "widget_" + ID + " has been removed from Shared!");
        }

        mEditor.apply();

    }

    @Override
    public void onReceive(@NonNull Context c, @NonNull Intent intent) {

        Log.i("log_tag", "onUpdate Called!");

        int ID;
        int[] IDs;

        AppWidgetManager awp = AppWidgetManager.getInstance(c);

        Bundle extras = intent.getExtras();
        if (extras != null) {
            ID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

            /* So if there is no ID, then we are loading everything */
            if (ID == AppWidgetManager.INVALID_APPWIDGET_ID) {

                /* Declare Component Definition */
                ComponentName mWidget = new ComponentName(c, WidgetReceiver.class);

                /* Loop through all Widget instances we have */
                IDs = awp.getAppWidgetIds(mWidget);

                for (int currentID : IDs) {
                    int mNumber = (new Random().nextInt(100));
                    Static.updateView(c, String.valueOf(mNumber), currentID, awp, -1);
                }

            } else {
                int number = (new Random().nextInt(100));
                Static.updateView(c, String.valueOf(number), ID, awp, -1);
            }
        }


    }
}
