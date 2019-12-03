package com.example.scheduler;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsPins {
    public static Map<String, LatLng> pins = new HashMap<>();

    public static void setPinsToMyEvents(List<Event> events) {
        pins = new HashMap<>();

        for (Event e : events) {
            String location = e.getLocation();
            String[] locations = location.split(",");
            pins.put(
                    locations[0],
                    new LatLng(Double.valueOf(
                            e.getLat()),
                            Double.valueOf(e.getLng())
                    )
            );
        }
    }
}
