package com.example.unischedulebot.google;

import java.util.LinkedHashMap;
import java.util.List;

public interface GSheets {
    LinkedHashMap<String, List<String>> getSchedule();
}
