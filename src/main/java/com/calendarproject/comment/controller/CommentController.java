package com.calendarproject.comment.controller;

import com.calendarproject.auth.dto.SessionUser;
import com.calendarproject.comment.dto.CreateCommentRequest;
import com.calendarproject.comment.dto.CreateCommentResponse;
import com.calendarproject.comment.dto.GetCommentsResponse;
import com.calendarproject.comment.service.CommentService;
import com.calendarproject.core.constants.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<CreateCommentResponse> create(
            @SessionAttribute(name = Session.USER, required = false) SessionUser sessionUser,
            @PathVariable Long scheduleId,
            @RequestBody CreateCommentRequest request
    ) {
        try {
            CreateCommentResponse result = commentService.save(sessionUser, scheduleId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<List<GetCommentsResponse>> getAll(@PathVariable Long scheduleId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.findAll(scheduleId));
    }
}
