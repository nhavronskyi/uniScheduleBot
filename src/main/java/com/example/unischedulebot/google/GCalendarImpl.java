package com.example.unischedulebot.google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GCalendarImpl implements GCalendar {

    private Calendar service;

    @SneakyThrows
    public GCalendarImpl() {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        List<String> SCOPES =
                Collections.singletonList(CalendarScopes.CALENDAR);


        GAuthorisation authorisation = new GAuthorisation();
        service =
                new Calendar.Builder(HTTP_TRANSPORT, authorisation.getJSON_FACTORY(), authorisation.getCredentials(SCOPES))
                        .setApplicationName("Google GCalendar API Java Quickstart")
                        .build();
    }

    @SneakyThrows
    public String getNextEvent() {
        DateTime now = new DateTime(System.currentTimeMillis());

        // get all calendars
        Events events = service.events().list("nhavronskyi@gmail.com")
                .setMaxResults(1)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();

        Event item = events.getItems().get(0);
        return item.getSummary()
                + "\n" +
                item.getStart()
                        .getDateTime()
                        .toString().replace(":00.000+02:00","")
                        .replace("T", " ")
                + " -> " +
                item.getEnd()
                        .getDateTime()
                        .toString().replace(":00.000+02:00","")
                        .replace("T", " ");
    }


    @SneakyThrows
    private boolean checkIfExist(String summary) {
        Events events = service.events().list("nhavronskyi@gmail.com")
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();

        List<Event> items = events.getItems();
        return items.stream().map(Event::getSummary).anyMatch(e -> e.equals(summary));
    }

    @SneakyThrows
    public void createAnEvent(String title, String teacher, DateTime start, DateTime end) {
        if (!checkIfExist(title)) {
            Event event = new Event()
                    .setSummary(title)
                    .setDescription(teacher)
                    .setStart(new EventDateTime().setDateTime(start))
                    .setEnd(new EventDateTime().setDateTime(end));

            service.events().insert("nhavronskyi@gmail.com", event).execute();
        }
    }
}
