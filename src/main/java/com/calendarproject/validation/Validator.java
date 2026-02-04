package com.calendarproject.validation;

import com.calendarproject.dto.BadRequestDto;
import com.calendarproject.entity.BaseEntity;
import jakarta.persistence.Column;

import java.lang.reflect.Field;

public class Validator {
    public static <T extends BaseEntity, U extends Record> BadRequestDto validate(U dto, Class<T> entityClass) {
        BadRequestDto result = new BadRequestDto();
        result.isOk = true;
        for (Field dtoField : dto.getClass().getDeclaredFields()) {
            try {
                Field entityField = entityClass.getDeclaredField(dtoField.getName());
                Column column = entityField.getAnnotation(Column.class);
                dtoField.setAccessible(true);
                Object val = dtoField.get(dto);
                if (isNullOrEmpty(val)) {
                    if (!column.nullable()) {
                        result.message = dtoField.getName() + "은 필수 값 입니다";
                        result.isOk = false;
                        return result;
                    }
                } else if (column.length() < val.toString().length()) {
                    result.message = dtoField.getName() + "을 " + column.length() + " 이하로 입력해 주세요";
                    result.isOk = false;
                    return result;
                }
            } catch (NoSuchFieldException e) {
                continue;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    public static boolean isNullOrEmpty(Object val) {
        return val == null || val.toString().isBlank();
    }
}
