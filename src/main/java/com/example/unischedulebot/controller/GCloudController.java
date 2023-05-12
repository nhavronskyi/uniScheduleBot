package com.example.unischedulebot.controller;

import com.example.unischedulebot.service.GService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping
public class GCloudController {

    private GService gService;

    @GetMapping("/sheets")
    public Map<String, List<String>> getSchedule() {
        return gService.getSchedule();
    }

    @GetMapping("/calendar")
    public String getNextEvent() {
        return gService.getNextEvent();
    }

    @PostMapping("/integration")
    public void createEventsForSchedule() {
        gService.createEventsForSchedule();
    }
}
