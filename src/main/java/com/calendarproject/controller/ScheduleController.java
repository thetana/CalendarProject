package com.calendarproject.controller;

import com.calendarproject.dto.*;
import com.calendarproject.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/schedules")
    public ResponseEntity<?> create(@RequestBody CreateScheduleRequest request) {
        CreateScheduleResponse result = scheduleService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
