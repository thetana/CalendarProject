package com.calendarproject.common.security;

import com.calendarproject.admin.entity.Admin;
import com.calendarproject.admin.enums.AdminRoleEnum;
import com.calendarproject.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!adminRepository.existsByRole(AdminRoleEnum.SUPER_ADMIN)) {
            Admin superAdmin = new Admin(
                    "root",
                    passwordEncoder.encode("12345678"),
                    AdminRoleEnum.SUPER_ADMIN
            );

            adminRepository.save(superAdmin);
        }
    }
}