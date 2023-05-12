package com.example.unischedulebot.service;

import java.util.List;
import java.util.Map;

public interface GService {
    Map<String, List<String>> getSchedule();

    String getNextEvent();

    void createEventsForSchedule();
}
