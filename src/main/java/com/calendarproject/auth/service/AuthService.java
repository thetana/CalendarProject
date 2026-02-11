package com.calendarproject.auth.service;


import com.calendarproject.admin.entity.Admin;
import com.calendarproject.admin.repository.AdminRepository;
import com.calendarproject.auth.dto.*;
import com.calendarproject.auth.dto.admin.AuthAdminSigninRequest;
import com.calendarproject.auth.dto.admin.AuthAdminSignupRequest;
import com.calendarproject.auth.dto.admin.AuthAdminSignupResponse;
import com.calendarproject.common.security.JwtProvider;
import com.calendarproject.common.security.PasswordEncoder;
import com.calendarproject.user.entity.User;
import com.calendarproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;


    @Transactional
    public AuthSignupResponse signup(AuthSignupRequest request) {
        String password = request.password();
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(
                request.email(),
                encodedPassword,
                request.name()
        );
        User savedUser = userRepository.save(user);
        return new AuthSignupResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getName(),
                savedUser.getCreatedAt(),
                savedUser.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public SessionUser signin(AuthSigninRequest request) {
        User user = userRepository.findByEmail(request.email()).orElseThrow(
                () -> new RuntimeException("해당 유저가 없습니다.")
        );
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        return new SessionUser(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }




    @Transactional
    public AuthAdminSignupResponse adminSignup(AuthAdminSignupRequest request) {
        String password = request.password();
        String encodedPassword = passwordEncoder.encode(password);
        Admin admin = new Admin(
                request.id(),
                encodedPassword,
                ""
        );
        Admin savedAdmin = adminRepository.save(admin);
        return new AuthAdminSignupResponse(
                savedAdmin.getId()
        );
    }

    @Transactional
    public String adminSignin(AuthAdminSigninRequest request) {
        Admin admin = adminRepository.findById(request.id())
                .orElseThrow(() -> new RuntimeException("유저 없음"));

        if (!passwordEncoder.matches(request.password(), admin.getPassword())) {
            throw new RuntimeException("비밀번호 불일치");
        }

        return jwtProvider.createToken(admin.getId(), admin.getRole());
    }
}
