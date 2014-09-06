package com.ingamedeo.widgetdemo;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

public class Static {

    public static void updateView(Context c, String value, int ID, AppWidgetManager appWidgetManager, int color) {

        SharedPreferences mShared = PreferenceManager.getDefaultSharedPreferences(c);

        RemoteViews remoteViews = new RemoteViews(c.getPackageName(), R.layout.widget);
        remoteViews.setTextViewText(R.id.number, value);

        if (color != -1) {
            SharedPreferences.Editor mEdit = mShared.edit();
            mEdit.putInt("widget_" + ID, color);
            mEdit.apply();
        } else { /* A color is already set */
            color = mShared.getInt("widget_" + ID, -1);
        }
        remoteViews.setInt(R.id.widget_lay, "setBackgroundColor", color);

        Intent updateIntent = new Intent(c, WidgetReceiver.class);
        updateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, ID);

        /* Using ID Below... We should use ID below otherwise Android will try to re-use this intent ;( This way we keep a unique reference */
        PendingIntent pendingIntent = PendingIntent.getBroadcast(c, ID, updateIntent, 0);
        remoteViews.setOnClickPendingIntent(R.id.number, pendingIntent);

        appWidgetManager.updateAppWidget(ID, remoteViews);
    }
}
