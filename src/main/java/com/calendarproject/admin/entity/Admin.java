package com.calendarproject.admin.entity;

import com.calendarproject.admin.enums.AdminRoleEnum;
import com.calendarproject.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "admins")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends BaseEntity {
    @Id
    private String id;
    private String password;
    @Enumerated(EnumType.STRING)
    private AdminRoleEnum role;

    public Admin(String id, String password, AdminRoleEnum role) {
        this.id = id;
        this.password = password;
        this.role = role;
    }
}