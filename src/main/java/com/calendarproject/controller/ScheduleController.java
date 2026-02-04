package com.calendarproject.controller;

import com.calendarproject.dto.*;
import com.calendarproject.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/schedules")
    public ResponseEntity<?> create(@RequestBody CreateScheduleRequest request) {
        CreateScheduleResponse result = scheduleService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<GetSchedulesResponse>> getAll(@RequestParam(required = false) String title) {
        List<GetSchedulesResponse> result = scheduleService.getAll(title);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/schedules/{id}")
    public ResponseEntity<GetScheduleResponse> getOne(@PathVariable Long id) {
        try {
            GetScheduleResponse result = scheduleService.getOne(id);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping("/schedules/{id}")
    public ResponseEntity<UpdateScheduleResponse> update(@PathVariable Long id, @RequestBody UpdateScheduleRequest request) {
        try {
            UpdateScheduleResponse result = scheduleService.update(id, request);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
