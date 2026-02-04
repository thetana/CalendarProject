package com.calendarproject.service;

import com.calendarproject.dto.*;
import com.calendarproject.entity.Schedule;
import com.calendarproject.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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

    @Transactional(readOnly = true)
    public GetScheduleResponse getOne(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("존재하지 않는 일정 입니다.")
        );

        return new GetScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getDetails(),
                schedule.getWriter(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    @Transactional
    public UpdateScheduleResponse update(Long id, UpdateScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("존재하지 않는 일정 입니다.")
        );
        if (!schedule.getPassword().equals(request.password())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
        schedule.update(
                request.title(),
                request.writer()
        );
        UpdateScheduleResponse response = new UpdateScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getWriter(),
                schedule.getModifiedAt()
        );
        return response;
    }

    @Transactional
    public void delete(Long id, DeleteScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("존재하지 않는 일정 입니다.")
        );
        if (!schedule.getPassword().equals(request.password())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
        scheduleRepository.deleteById(id);
    }
}