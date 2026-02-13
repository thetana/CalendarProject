package com.calendarproject.admin.controller;

import com.calendarproject.admin.enums.AdminRoleEnum;
import com.calendarproject.common.dto.JwtAdminInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @GetMapping("/admin/info")
    public ResponseEntity<String> getAdminInfo(Authentication authentication) {

        JwtAdminInfo info = (JwtAdminInfo) authentication.getPrincipal();
        String userId = info.userId();
        AdminRoleEnum role = info.role();

        return ResponseEntity.ok("현재 사용자: " + userId + "현재 role: " + role);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/admin/super/info")
    public ResponseEntity<String> getSuperAdminInfo(Authentication authentication) {

        JwtAdminInfo info = (JwtAdminInfo) authentication.getPrincipal();
        String userId = info.userId();
        AdminRoleEnum role = info.role();

        return ResponseEntity.ok("현재 사용자: " + userId + "현재 role: " + role);
    }
}
