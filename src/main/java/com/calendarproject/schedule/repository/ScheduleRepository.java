package com.calendarproject.schedule.repository;

import com.calendarproject.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByTitleOrderByModifiedAtDesc(String title);
    List<Schedule> findAllByOrderByModifiedAtDesc();

}
