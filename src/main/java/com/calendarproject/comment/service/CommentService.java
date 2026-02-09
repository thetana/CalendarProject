package com.calendarproject.comment.service;

import com.calendarproject.comment.dto.CreateCommentRequest;
import com.calendarproject.comment.dto.CreateCommentResponse;
import com.calendarproject.comment.entity.Comment;
import com.calendarproject.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public CreateCommentResponse save(CreateCommentRequest request) {
        if (commentRepository.countByScheduleId(request.scheduleId()) >= 10) {
            throw new IllegalStateException("댓글의 제한 수를 초과 했습니다");
        }
        Comment comment = new Comment(request.scheduleId(),
                request.content(),
                request.writer(),
                request.password());
        Comment savedComment = commentRepository.save(comment);
        return new CreateCommentResponse(savedComment.getId(),
                savedComment.getScheduleId(),
                savedComment.getContent(),
                savedComment.getWriter(),
                savedComment.getCreatedAt(),
                savedComment.getModifiedAt());
    }

}