package com.calendarproject.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String content;
    @Column(length = 30, nullable = false)
    private String writer;
    @Column(nullable = false)
    private Long scheduleId;
    @Column(nullable = false)
    private String password;

    public Comment(String content, String writer, Long scheduleId, String password) {
        this.content = content;
        this.writer = writer;
        this.scheduleId = scheduleId;
        this.password = password;
    }
}
