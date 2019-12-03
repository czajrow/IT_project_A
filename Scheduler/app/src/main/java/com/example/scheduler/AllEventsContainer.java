package com.example.scheduler;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllEventsContainer implements GetEventsData.OnDataAvailable {
    private static final String TAG = "AllEventsContainer";

    private AllEventsContainer() {
        downloadEvents();
    }

    public interface AllEventsDataChanged {
        void allEventsDataChanged(List<Event> newList);
    }

    private static AllEventsContainer singleton = new AllEventsContainer();

    public static List<Event> getEvents() {
        return singleton.allEvents;
    }

    public static Map<String, Event> getEventsMap() {
        return singleton.allEventsMap;
    }

    public static void setDataChangeCallback(AllEventsDataChanged callback) {
        singleton.callback = callback;
    }

    private List<Event> allEvents = new ArrayList<>();
    private Map<String, Event> allEventsMap = new HashMap<>();
    private AllEventsDataChanged callback;

    private void downloadEvents() {
        GetEventsData getEventsData = new GetEventsData(this);
        getEventsData.execute();
    }

    @Override
    public void onDataAvailable(List<Event> data, DownloadStatus status) {
        if (DownloadStatus.OK.equals(status)) {
            Collections.sort(data, new Comparator<Event>() {
                @Override
                public int compare(Event o1, Event o2) {
                    String o1Comp = o1.getStart() + " " + o1.getEnd();
                    String o2Comp = o2.getStart() + " " + o2.getEnd();
                    return o1Comp.compareToIgnoreCase(o2Comp);
                }
            });
            singleton.allEvents = data;
            for (Event e : data) {
                allEventsMap.put(e.getTitle(), e);
            }
            if (callback != null) {
                callback.allEventsDataChanged(singleton.allEvents);
            }
            Log.d(TAG, "onDataAvailable: data:\n" + data);
        } else {
            Log.e(TAG, "onDataAvailable: EROOR downloading data. Status: " + status);
        }
    }
}
