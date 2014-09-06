package com.ingamedeo.widgetdemo;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.larswerkman.holocolorpicker.ColorPicker;

import java.util.Random;

public class ConfigActivity extends ActionBarActivity {

    int currentID;
    Button updateButton;
    ColorPicker picker;

    Context c = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        /* Fallback Result value, if sth goes wrong this is passed over */
        setResult(RESULT_CANCELED);

        picker = (ColorPicker) findViewById(R.id.picker);
        updateButton = (Button) findViewById(R.id.update);

        picker.setShowOldCenterColor(false);

        Intent configIntent = getIntent();
        Bundle extras = configIntent.getExtras();

        if (extras != null) {
            /* Using Invalid ID as Fallback Value */
            currentID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppWidgetManager awp = AppWidgetManager.getInstance(getBaseContext());

                int number = new Random().nextInt(100);

                Log.i("log_tag", "CurrentID: " + currentID);

                Static.updateView(c, String.valueOf(number), currentID, awp, picker.getColor());

                /* Return the response */
                Intent result = new Intent();
                result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, currentID);
                setResult(RESULT_OK, result);
                finish();
            }
        });
    }
}