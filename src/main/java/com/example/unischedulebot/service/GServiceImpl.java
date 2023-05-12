package com.example.unischedulebot.service;

import com.example.unischedulebot.google.GCalendar;
import com.example.unischedulebot.google.GSheets;
import com.google.api.client.util.DateTime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Service
@AllArgsConstructor
public class GServiceImpl implements GService {

    private GSheets sheets;

    private GCalendar calendar;

    public Map<String, List<String>> getSchedule() {
        return sheets.getSchedule();
    }

    public String getNextEvent() {
        return calendar.getNextEvent();
    }

    public void createEventsForSchedule() {
        LocalDate localDate = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        for (Map.Entry<String, List<String>> stringListEntry : sheets.getSchedule().entrySet()) {

            LocalDate lDate = localDate.with(TemporalAdjusters.next(DayOfWeek.valueOf(stringListEntry.getKey())));

            if (stringListEntry.getValue().size() != 1) {
                for (String lessonLine : stringListEntry.getValue()) {
                    String[] strings = lessonLine
                            .replaceAll("\\[", "")
                            .split("]");

                    for (String lesson : List.of(strings)) {
                        Object[] data = Arrays.stream(lesson.split(",")).map(String::trim).toArray();

                        String title = data[0] + " " + data[2];
                        String teacher = data[3].toString();


                        Object[] time = Arrays.stream(data[1].toString().split("-")).map(String::trim).toArray();
                        calendar.createAnEvent(title
                                , teacher
                                , new DateTime(lDate + "T" + time[0].toString() + ":00" + "+02:00")
                                , new DateTime(lDate + "T" + time[1].toString() + ":00" + "+02:00")
                        );
                    }
                }
            }
        }
    }
}
