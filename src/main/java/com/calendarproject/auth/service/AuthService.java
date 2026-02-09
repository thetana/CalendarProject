package com.calendarproject.auth.service;


import com.calendarproject.auth.dto.AuthSigninRequest;
import com.calendarproject.auth.dto.AuthSignupRequest;
import com.calendarproject.auth.dto.AuthSignupResponse;
import com.calendarproject.auth.dto.SessionUser;
import com.calendarproject.support.security.PasswordEncoder;
import com.calendarproject.user.entity.User;
import com.calendarproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public AuthSignupResponse signup(AuthSignupRequest request) {
        String password = request.password();
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(
                request.email(),
                request.name(),
                encodedPassword
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

}
