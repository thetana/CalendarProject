package com.calendarproject.validation;

import com.calendarproject.dto.BadRequestDto;
import com.calendarproject.entity.BaseEntity;
import jakarta.persistence.Column;

import java.lang.reflect.Field;

/**
 * 벨리데이션을 안쓰고 정합성 체크를 하기 위해 직접 간이 벨리데이터를 만들었다
 * DTO에 정합성 조건을 추가하면 좀 더 편하게 쓸 수 있을 것 같지만
 * DTO에 정합성 조건을 추가하는 것은 벨리데이터를 쓰는 거랑 크게 다르지 않다고 생각하여 엔티티에 조건으로 체크했다
 * 어느정도 한계가 있는 벨리데이터 이지만 과제 수행에는 무리가 없어 보이고 연습에도 도움이 될 듯 하여 만들었다
 */

public class Validator {
    public static <T extends BaseEntity, U extends Record> BadRequestDto validate(U dto, Class<T> entityClass) {
        // 리퀘스트로 들어온 DTO에 들어있는 필드들을 하나씩 확인한다
        for (Field dtoField : dto.getClass().getDeclaredFields()) {
            try {
                // DTO 필드의 이름과 같은 이름을 갖은 필드를 entity에서 가져와서 오브젝트화 한다
                Field entityField = entityClass.getDeclaredField(dtoField.getName());
                // entity에서 해당필드에 선언한 선언한 Column 어노테이션을 오브젝트화 한다
                Column column = entityField.getAnnotation(Column.class);
                // DTO에서 현재 체크중인 필드의 값을 가져온다
                dtoField.setAccessible(true);
                Object val = dtoField.get(dto);
                // 해당 값이 널이나 공백인지 확인 한다
                if (isNullOrEmpty(val)) {
                    // 만약 entity에서 nullable false이면 필수 값 이기 때문에 메세지와 함께 실패로 리턴한다
                    if (!column.nullable()) {
                        String message = dtoField.getName() + "은 필수 값 입니다";
                        return new BadRequestDto(false, message);
                    }
                } else if (column.length() < val.toString().length()) {
                    // entity에 선언한 length 길이를 초과하면 메세지와 함께 실패로 리턴한다
                    String message = dtoField.getName() + "을 " + column.length() + " 이하로 입력해 주세요";
                    return new BadRequestDto(false, message);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        // 모든 컬럼이 문제 없이 검사를 통과 하면 메세지 없이 성공으로 리턴 한다
        return new BadRequestDto(true, "");
    }

    public static boolean isNullOrEmpty(Object val) {
        return val == null || val.toString().isBlank();
    }
}
