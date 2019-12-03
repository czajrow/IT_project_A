package com.example.scheduler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

import android.view.MenuItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * An activity representing a single EventItem detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link EventItemListActivity}.
 */
public class EventItemDetailActivity extends AppCompatActivity {
    private static final String TAG = "EventItemDetailActivity";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventitem_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Event currentEvent = CurrentSelectedEvent.currentEvent;
                boolean result = MyEventsContainer.addEvent(currentEvent);
                if (result) {
                    setUpNotification(currentEvent);
                }
                String message = result ? "Event added to your list." : "Event is already on your list.";
                Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                        .setAction(
                                "See my list",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        goToMyListActivity();
                                    }
                                }
                        ).show();
            }
        });

        FloatingActionButton fabMap = (FloatingActionButton) findViewById(R.id.fabMap);
        fabMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "FabMap...", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                goToMapActivity();

            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(EventItemDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(EventItemDetailFragment.ARG_ITEM_ID));
            EventItemDetailFragment fragment = new EventItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.eventitem_detail_container, fragment)
                    .commit();
        }
    }

    private void setUpNotification(Event event) {
        Log.d(TAG, "setUpNotification: starts");

//        if (MyEventsContainer.getMyEvents().contains(event)) return;

        Intent myIntent = new Intent(this, MyService.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(this, event.hashCode(), myIntent, 0);

        Calendar calendar = Calendar.getInstance();

        if (IsDebug.isDebug) {
        Log.d(TAG, "setUpNotification: time: " + calendar.getTime());
        calendar.add(Calendar.SECOND, 10);

        } else {
            Log.d(TAG, "setUpNotification: startTime: " + event.getStart());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("kk:mm");
            Date startDate = new Date();
            try {
                startDate = simpleDateFormat.parse(event.getStart());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar startHour = Calendar.getInstance();
            startHour.setTime(startDate);

            calendar.set(Calendar.HOUR, startHour.get(Calendar.HOUR));
            calendar.set(Calendar.MINUTE, startHour.get(Calendar.MINUTE));
            calendar.set(Calendar.SECOND, 0);
            calendar.add(Calendar.MINUTE, -30);
        }
        Log.d(TAG, "setUpNotification: time: " + calendar.getTime());


        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private void goToMyListActivity() {
        Intent intent = new Intent(this, MyEventItemListActivity.class);
        startActivity(intent);
    }

    private void goToMapActivity() {
        Map<String, LatLng> pins = new HashMap<>();
        Event currentEvent = CurrentSelectedEvent.currentEvent;
        pins.put(
                currentEvent.getLocation(),
                new LatLng(
                        Double.valueOf(currentEvent.getLat()),
                        Double.valueOf(currentEvent.getLng())
                )
        );
        MapsPins.pins = pins;
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, EventItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
