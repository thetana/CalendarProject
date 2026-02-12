package com.calendarproject.admin.repository;

import com.calendarproject.admin.entity.Admin;
import com.calendarproject.admin.enums.AdminRoleEnum;
import com.calendarproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, String> {
    boolean existsByRole(AdminRoleEnum adminRoleEnum);
}
