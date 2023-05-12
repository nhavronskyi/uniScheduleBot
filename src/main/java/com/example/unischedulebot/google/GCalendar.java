package com.example.unischedulebot.google;

import com.google.api.client.util.DateTime;

public interface GCalendar {
    String getNextEvent();

    void createAnEvent(String title, String teacher, DateTime start, DateTime end);
}
