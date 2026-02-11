package com.calendarproject.schedule.repository;

import com.calendarproject.schedule.dto.GetSchedulesResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ScheduleRepositoryCustom {
    Page<GetSchedulesResponse> search(Pageable pageable, String title);
}