package com.example.scheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyEventsContainer {

    private static MyEventsContainer singleton = new MyEventsContainer();

    private List<Event> myEvents = new ArrayList<>();
    private Map<String, Event> myEventsMap = new HashMap<>();
    private MyEventsDataChanged callback;

    public interface MyEventsDataChanged {
        void myEventsDataChanged(List<Event> newList);
    }

    private MyEventsContainer() {
    }

    public static List<Event> getMyEvents() {
        return singleton.myEvents;
    }

    public static Map<String, Event> getMyEventsMap() {
        return singleton.myEventsMap;
    }

    public static void setDataChangeCallback(MyEventsDataChanged callback) {
        singleton.callback = callback;
    }

    public static boolean addEvent(Event event) {
        if (singleton.myEvents.contains(event)) return false;

        singleton.myEvents.add(event);
        singleton.myEventsMap.put(event.getTitle(), event);

        Collections.sort(singleton.myEvents, new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                String o1Comp = o1.getStart() + " " + o1.getEnd();
                String o2Comp = o2.getStart() + " " + o2.getEnd();
                return o1Comp.compareToIgnoreCase(o2Comp);
            }
        });

        if (singleton.callback != null) {
            singleton.callback.myEventsDataChanged(singleton.myEvents);
        }
        return true;
    }

    public static boolean removeEvent(Event event) {
        boolean result = singleton.myEvents.remove(event);
        singleton.myEventsMap.remove(event.getTitle());

        if (singleton.callback != null) {
            singleton.callback.myEventsDataChanged(singleton.myEvents);
        }
        return result;
    }

}
