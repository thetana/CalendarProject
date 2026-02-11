package com.calendarproject.schedule.service;

import com.calendarproject.auth.dto.SessionUser;
import com.calendarproject.comment.service.CommentService;
import com.calendarproject.schedule.dto.*;
import com.calendarproject.schedule.entity.Schedule;
import com.calendarproject.schedule.repository.ScheduleRepository;
import com.calendarproject.user.entity.User;
import com.calendarproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final CommentService commentService;

    @Transactional
    public CreateScheduleResponse save(SessionUser sessionUser, CreateScheduleRequest request) {
        User user = userRepository.findById(sessionUser.id()).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 유저입니다.")
        );
        Schedule schedule = new Schedule(user.getId(),
                request.title(),
                request.details()
        );
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new CreateScheduleResponse(savedSchedule.getId(),
                savedSchedule.getUserId(),
                savedSchedule.getTitle(),
                savedSchedule.getDetails(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt());
    }

    @Transactional(readOnly = true)
    public GetPageResponse getAll(Pageable pageable, String title) {
        Page<GetSchedulesResponse> schedules;
//        if (StringUtils.hasText(title)) {
//            schedules = scheduleRepository.findByTitleOrderByModifiedAtDesc(pageable, title).map(GetSchedulesResponse::from);
//        } else {
//            schedules = scheduleRepository.findAllByOrderByModifiedAtDesc(pageable).map(GetSchedulesResponse::from);
//        }
        schedules = scheduleRepository.search(pageable, title);
        return GetPageResponse.from(schedules);
    }

    @Transactional(readOnly = true)
    public GetScheduleResponse getOne(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("존재하지 않는 일정 입니다.")
        );
        return new GetScheduleResponse(
                schedule.getId(),
                schedule.getUserId(),
                schedule.getTitle(),
                schedule.getDetails(),
                commentService.findAll(id),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    @Transactional
    public UpdateScheduleResponse update(SessionUser sessionUser, Long id, UpdateScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("존재하지 않는 일정 입니다.")
        );
        if (!ObjectUtils.nullSafeEquals(sessionUser.id(), schedule.getUserId())) {
            throw new IllegalStateException("작성자가 다릅니다.");
        }
        schedule.update(
                request.title(),
                request.details()
        );
        UpdateScheduleResponse response = new UpdateScheduleResponse(
                schedule.getId(),
                schedule.getUserId(),
                schedule.getTitle(),
                schedule.getDetails(),
                schedule.getModifiedAt()
        );
        return response;
    }

    @Transactional
    public void delete(SessionUser sessionUser, Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("존재하지 않는 일정 입니다.")
        );
        if (!ObjectUtils.nullSafeEquals(sessionUser.id(), schedule.getUserId())) {
            throw new IllegalStateException("작성자가 다릅니다.");
        }
        scheduleRepository.deleteById(id);
    }
}