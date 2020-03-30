package com.example.partia;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AppEngine {
    private HashMap<Integer, Event> events;

    public Event getAnEvent(int pinCode)
    /**
     * this function gets an event pinCode
     * returns the event if exists and null otherwise
     */
    {
        if (! events.containsKey(pinCode))
            return null;
        return events.get(pinCode);
    }

    public boolean createNewEvent(JSONObject eventAnswers)
    /**
     * this function gets a json object with nw event details,
     * and creates new event.
     * return true on successful creation and false in error
     */
    {
        try
        {
            Event newEvent = new Event(eventAnswers);
            events.put(newEvent.getPinCode(), newEvent);
            return  true;
        }
        catch (java.lang.Exception e) {
            return false;
        }
    }
}
