package com.calendarproject.comment.service;

import com.calendarproject.auth.dto.SessionUser;
import com.calendarproject.comment.dto.CreateCommentRequest;
import com.calendarproject.comment.dto.CreateCommentResponse;
import com.calendarproject.comment.dto.GetCommentsResponse;
import com.calendarproject.comment.entity.Comment;
import com.calendarproject.comment.repository.CommentRepository;
import com.calendarproject.schedule.entity.Schedule;
import com.calendarproject.schedule.repository.ScheduleRepository;
import com.calendarproject.user.entity.User;
import com.calendarproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CreateCommentResponse save(SessionUser sessionUser, Long scheduleId, CreateCommentRequest request) {
        User user = userRepository.findById(sessionUser.id()).orElseThrow(
                () -> new IllegalStateException("해당 유저가 없습니다.")
        );
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("해당 유저가 없습니다.")
        );
        if (commentRepository.countByScheduleId(scheduleId) >= 10) {
            throw new IllegalStateException("댓글의 제한 수를 초과 했습니다");
        }
        Comment comment = new Comment(user,
                schedule,
                request.content());
        Comment savedComment = commentRepository.save(comment);
        return new CreateCommentResponse(savedComment.getId(),
                savedComment.getUser().getId(),
                savedComment.getSchedule().getId(),
                savedComment.getContent(),
                savedComment.getCreatedAt(),
                savedComment.getModifiedAt());
    }

    @Transactional(readOnly = true)
    public List<GetCommentsResponse> findAll(Long scheduleId) {
        List<Comment> comments = commentRepository.findAllByScheduleId(scheduleId);
        List<GetCommentsResponse> dtos = new ArrayList<>();
        for (Comment comment : comments) {
            GetCommentsResponse dto = new GetCommentsResponse(comment.getId(),
                    comment.getUser().getId(),
                    comment.getSchedule().getId(),
                    comment.getContent(),
                    comment.getCreatedAt(),
                    comment.getModifiedAt());
            dtos.add(dto);
        }
        return dtos;
    }
}