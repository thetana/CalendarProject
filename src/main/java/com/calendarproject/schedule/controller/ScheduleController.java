package com.calendarproject.schedule.controller;

import com.calendarproject.auth.dto.SessionUser;
import com.calendarproject.schedule.dto.*;
import com.calendarproject.schedule.service.ScheduleService;
import com.calendarproject.common.constants.Session;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/schedules")
    public ResponseEntity<?> create(
            @SessionAttribute(name = Session.USER, required = false) SessionUser sessionUser,
            @Valid @RequestBody CreateScheduleRequest request) {
        CreateScheduleResponse result = scheduleService.save(sessionUser, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/schedules")
    public ResponseEntity<GetPageResponse> getAll(Pageable pageable, @RequestParam(required = false) String title) {
        GetPageResponse result = scheduleService.getAll(pageable, title);
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
    public ResponseEntity<?> update(
            @SessionAttribute(name = Session.USER, required = false) SessionUser sessionUser,
            @PathVariable Long id,
            @RequestBody UpdateScheduleRequest request) {
        try {
            UpdateScheduleResponse result = scheduleService.update(sessionUser, id, request);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<Void> delete(
            @SessionAttribute(name = Session.USER, required = false) SessionUser sessionUser,
            @PathVariable Long id) {
        try {
            scheduleService.delete(sessionUser, id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
