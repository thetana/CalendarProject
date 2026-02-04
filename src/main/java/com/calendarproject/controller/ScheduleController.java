package com.calendarproject.controller;

import com.calendarproject.dto.*;
import com.calendarproject.entity.Schedule;
import com.calendarproject.service.ScheduleService;
import com.calendarproject.validation.Validator;
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
        BadRequestDto dto = Validator.validate(request, Schedule.class);
        if(!dto.isOk()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
        }
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
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UpdateScheduleRequest request) {
        try {
            BadRequestDto dto = Validator.validate(request, Schedule.class);
            if(!dto.isOk()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
            }
            UpdateScheduleResponse result = scheduleService.update(id, request);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestBody DeleteScheduleRequest request) {
        try {
            scheduleService.delete(id, request);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
