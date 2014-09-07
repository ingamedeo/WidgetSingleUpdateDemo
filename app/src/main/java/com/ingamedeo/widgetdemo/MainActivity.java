package com.ingamedeo.widgetdemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class MainActivity extends ActionBarActivity {

    /*
    *
    * Ref: http://www.bogdanirimia.ro/android-widget-click-event-multiple-instances/269
    * Ref: http://developer.android.com/reference/android/widget/RemoteViews.html#setInt%28int,%20java.lang.String,%20int%29
    * Ref: http://www.vogella.com/tutorials/AndroidWidgets/article.html
    *
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
