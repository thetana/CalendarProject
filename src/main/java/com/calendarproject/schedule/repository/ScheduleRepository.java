package com.calendarproject.schedule.repository;

import com.calendarproject.schedule.dto.GetSchedulesResponse;
import com.calendarproject.schedule.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>,ScheduleRepositoryCustom {
    Page<GetSchedulesResponse> search(Pageable pageable, String title);
}
