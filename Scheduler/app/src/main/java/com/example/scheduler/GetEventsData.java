package com.example.scheduler;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class GetEventsData extends AsyncTask<String, Void, List<Event>> implements GetRawData.OnDownloadComplete, EventXMLReadeable {
    private static final String TAG = "GetEventsData";

    private List<Event> eventList = new ArrayList<>();
    private String url;

    private final OnDataAvailable callback;

    interface OnDataAvailable {
        void onDataAvailable(List<Event> data, DownloadStatus status);
    }

    public GetEventsData(OnDataAvailable callback) {
        this.callback = callback;
        url = "http://czajrow.000webhostapp.com/events.xml";
    }

    @Override
    protected void onPostExecute(List<Event> events) {
        Log.d(TAG, "onPostExecute: starts");

        if (callback != null) {
            callback.onDataAvailable(eventList, DownloadStatus.OK);
        }
        Log.d(TAG, "onPostExecute: ends");
    }

    @Override
    protected List<Event> doInBackground(String... strings) {
        Log.d(TAG, "doInBackground: starts");

        GetRawData getRawData = new GetRawData(this);
        getRawData.runInSameThread(url);

        Log.d(TAG, "doInBackground: ends");
        return eventList;
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        boolean inEntry = false;
        Event currentEvent = null;
        String textValue = "";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(data));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = xpp.getName();

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (eventTag.equalsIgnoreCase(tagName)) {
                            inEntry = true;
                            currentEvent = new Event();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (inEntry) {
                            if (eventTag.equalsIgnoreCase(tagName)) {
                                eventList.add(currentEvent);
                                inEntry = false;
                            } else if (titleTag.equalsIgnoreCase(tagName)) {
                                currentEvent.setTitle(textValue);
                            } else if (descriptionTag.equalsIgnoreCase(tagName)) {
                                currentEvent.setDescription(textValue);
                            } else if (presenterTag.equalsIgnoreCase(tagName)) {
                                currentEvent.setPresenter(textValue);
                            } else if (emailTag.equalsIgnoreCase(tagName)) {
                                currentEvent.setEmail(textValue);
                            } else if (locationTag.equalsIgnoreCase(tagName)) {
                                currentEvent.setLocation(textValue);
                            } else if (latTag.equalsIgnoreCase(tagName)) {
                                currentEvent.setLat(textValue);
                            } else if (lngTag.equalsIgnoreCase(tagName)) {
                                currentEvent.setLng(textValue);
                            } else if (startTag.equalsIgnoreCase(tagName)) {
                                currentEvent.setStart(textValue);
                            } else if (endTag.equalsIgnoreCase(tagName)) {
                                currentEvent.setEnd(textValue);
                            } else if (durationTag.equalsIgnoreCase(tagName)) {
                                currentEvent.setDuration(textValue);
                            }
                        }
                        break;
                    default:
                        // Nothing else to do
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}






















