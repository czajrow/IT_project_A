package com.example.scheduler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.widget.Toolbar;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

import android.view.MenuItem;

import java.util.HashMap;
import java.util.Map;

/**
 * An activity representing a single MyEventItem detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link MyEventItemListActivity}.
 */
public class MyEventItemDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myeventitem_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Event currentEvent = CurrentSelectedEvent.currentEvent;
                boolean result = MyEventsContainer.removeEvent(currentEvent);

                String message;
                if (result) {
                    message = "Event deleted.";
                } else {
                    message = "Event is already deleted.";
                }
                Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                cancelNotification(currentEvent);
                goBackToParentActivity();
            }
        });

        FloatingActionButton fabMap = (FloatingActionButton) findViewById(R.id.fabMap);
        fabMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            arguments.putString(MyEventItemDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(MyEventItemDetailFragment.ARG_ITEM_ID));
            MyEventItemDetailFragment fragment = new MyEventItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.myeventitem_detail_container, fragment)
                    .commit();
        }
    }

    private void cancelNotification(Event event) {

        Intent myIntent = new Intent(this, MyService.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(this, event.hashCode(), myIntent, 0);

        alarmManager.cancel(pendingIntent);
    }

    private void goBackToParentActivity(){
        navigateUpTo(new Intent(this, MyEventItemListActivity.class));
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
            navigateUpTo(new Intent(this, MyEventItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
