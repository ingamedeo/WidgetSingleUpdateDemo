package com.ingamedeo.widgetdemo;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.widget.RemoteViews;

public class Static {

    public static void updateView(Context c, String value, int ID, AppWidgetManager appWidgetManager, int color) {

        SharedPreferences mShared = PreferenceManager.getDefaultSharedPreferences(c);

        RemoteViews remoteViews = new RemoteViews(c.getPackageName(), R.layout.widget);
        //remoteViews.setTextViewText(R.id.number, value);

        if (color != -1) {
            SharedPreferences.Editor mEdit = mShared.edit();
            mEdit.putInt("widget_" + ID, color);
            mEdit.apply();
        } else { /* A color is already set */
            color = mShared.getInt("widget_" + ID, -1);
        }
        remoteViews.setInt(R.id.widget_lay, "setBackgroundColor", color);

        remoteViews.setImageViewBitmap(R.id.number, getFontBitmap(c,"Number: " + value, Color.WHITE, 16));

        Intent updateIntent = new Intent(c, WidgetReceiver.class);
        updateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, ID);

        /* Using ID Below... We should use ID below otherwise Android will try to re-use this intent ;( This way we keep a unique reference */
        PendingIntent pendingIntent = PendingIntent.getBroadcast(c, ID, updateIntent, 0);
        remoteViews.setOnClickPendingIntent(R.id.widget_lay, pendingIntent);

        appWidgetManager.updateAppWidget(ID, remoteViews);
    }

    public static Bitmap getFontBitmap(Context context, String text, int color, float fontSizeSP) {
        int fontSizePX = convertDiptoPix(context, fontSizeSP);
        int pad = (fontSizePX / 9);
        Paint paint = new Paint();
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/kartika.ttf");
        paint.setAntiAlias(true);
        paint.setTypeface(typeface);
        paint.setColor(color);
        paint.setTextSize(fontSizePX);

        int textWidth = (int) (paint.measureText(text) + pad * 2);
        int height = (int) (fontSizePX / 0.75);
        Bitmap bitmap = Bitmap.createBitmap(textWidth, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        float xOriginal = pad;
        canvas.drawText(text, xOriginal, fontSizePX, paint);
        return bitmap;
    }

    public static int convertDiptoPix(Context context, float dip) {
        int value = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
        return value;
    }
}
