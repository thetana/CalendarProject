package com.calendarproject.schedule.entity;

import com.calendarproject.common.entity.BaseEntity;
import com.calendarproject.user.entity.User;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 100, nullable = false)
    private String title;
    @Lob
    @Column(nullable = false)
    private String details;

    public Schedule(User user, String title, String details) {
        this.user = user;
        this.title = title;
        this.details = details;
    }

    public void update(String title, String details) {
        this.title = title;
        this.details = details;
    }
}
