package com.example.scheduler;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activateToolbar(false);

        Button btnListOfEvents = findViewById(R.id.btnListOfEvents);
        btnListOfEvents.setOnClickListener(this);
        Button btnMyListOfEvents = findViewById(R.id.btnMyListOfEvents);
        btnMyListOfEvents.setOnClickListener(this);
        Button btnMap = findViewById(R.id.btnMap);
        btnMap.setOnClickListener(this);
        Button btnAbout = findViewById(R.id.btnAbout);
        btnAbout.setOnClickListener(this);

        createNotificationChannel();
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {
            case R.id.btnListOfEvents:
                intent = new Intent(this, EventItemListActivity.class);
                break;

            case R.id.btnMyListOfEvents:
                intent = new Intent(this, MyEventItemListActivity.class);
                break;

            case R.id.btnMap:
                MapsPins.setPinsToMyEvents(MyEventsContainer.getMyEvents());
                intent = new Intent(this, MapsActivity.class);
                break;

            case R.id.btnAbout:
                intent = new Intent(this, AboutActivity.class);
                break;

            default:
        }

        if (intent != null) {
            startActivity(intent);
        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
