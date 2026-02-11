package com.calendarproject.schedule.entity;

import com.calendarproject.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private Long userId;

    @Column(length = 100, nullable = false)
    private String title;
    @Lob
    @Column(nullable = false)
    private String details;

    public Schedule(Long userId, String title, String details) {
        this.userId = userId;
        this.title = title;
        this.details = details;
    }

    public void update(String title, String details) {
        this.title = title;
        this.details = details;
    }
}
