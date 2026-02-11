package com.calendarproject.schedule.repository;

import com.calendarproject.comment.entity.QComment;
import com.calendarproject.schedule.dto.GetSchedulesResponse;
import com.calendarproject.schedule.dto.QGetSchedulesResponse;
import com.calendarproject.schedule.entity.QSchedule;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<GetSchedulesResponse> search(Pageable pageable, String title) {

        QSchedule schedule = QSchedule.schedule;
        QComment comment = QComment.comment;

        List<GetSchedulesResponse> content = queryFactory
                .select(new QGetSchedulesResponse(
                        schedule.id,
                        schedule.userId,
                        schedule.title,
                        schedule.details,
                        comment.id.count(),
                        schedule.createdAt,
                        schedule.modifiedAt
                ))
                .from(schedule)
                .leftJoin(comment).on(comment.schedule.id.eq(schedule.id))
                .where(titleContains(title))
                .groupBy(schedule.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        Long total = queryFactory
                .select(schedule.count())
                .from(schedule)
                .where(titleContains(title))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression titleContains(String title) {
        return title != null ? QSchedule.schedule.title.contains(title) : null;
    }
}