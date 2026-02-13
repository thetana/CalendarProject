package com.calendarproject.schedule.entity;

import com.calendarproject.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@Table(name = "schedules",
        indexes = {
                @Index(name = "idx_schedules_deleted_at", columnList = "deleted_at")
        })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE schedules SET deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
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
