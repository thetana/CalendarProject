package com.calendarproject.service;

import com.calendarproject.dto.*;
import com.calendarproject.entity.Schedule;
import com.calendarproject.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CreateScheduleResponse save(CreateScheduleRequest request) {
        Schedule schedule = new Schedule(request.title(),
                request.details(),
                request.writer(),
                request.password());
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new CreateScheduleResponse(savedSchedule.getId(),
                savedSchedule.getTitle(),
                savedSchedule.getDetails(),
                savedSchedule.getWriter(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt());
    }

    @Transactional(readOnly = true)
    public List<GetSchedulesResponse> getAll(String title) {
        List<Schedule> schedules;
        if (title == null || title.isBlank()) {
            schedules = scheduleRepository.findAllByOrderByModifiedAtDesc();
        } else {
            schedules = scheduleRepository.findByTitleOrderByModifiedAtDesc(title);
        }
        List<GetSchedulesResponse> dtos = new ArrayList<>();
        for (Schedule schedule : schedules) {
            GetSchedulesResponse dto = new GetSchedulesResponse(schedule.getId(),
                    schedule.getTitle(),
                    schedule.getDetails(),
                    schedule.getWriter(),
                    schedule.getCreatedAt(),
                    schedule.getModifiedAt());
            dtos.add(dto);
        }
        return dtos;
    }
}