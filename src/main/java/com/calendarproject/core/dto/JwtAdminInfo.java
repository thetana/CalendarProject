package com.calendarproject.core.dto;

import com.calendarproject.admin.enums.AdminRoleEnum;

public record JwtAdminInfo(
        String userId,
        AdminRoleEnum role) {
}
