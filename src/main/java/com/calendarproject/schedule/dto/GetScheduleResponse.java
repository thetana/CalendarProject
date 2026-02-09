package com.calendarproject.schedule.dto;

import com.calendarproject.comment.dto.GetCommentsResponse;

import java.time.LocalDateTime;
import java.util.List;

public record GetScheduleResponse (Long id, String title, String details, String writer, List<GetCommentsResponse> comments, LocalDateTime createdAt, LocalDateTime modifiedAt){
}
