package com.calendarproject.admin.entity;

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
    private String role;

    public Admin(String id, String password, String role) {
        this.id = id;
        this.password = password;
        this.role = role;
    }
}