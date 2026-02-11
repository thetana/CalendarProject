package com.calendarproject.schedule.dto;

import com.calendarproject.comment.dto.GetCommentsResponse;

import java.time.LocalDateTime;
import java.util.List;

public record GetScheduleResponse (Long id, Long userId, String title, String details, List<GetCommentsResponse> comments, LocalDateTime createdAt, LocalDateTime modifiedAt){
        }
