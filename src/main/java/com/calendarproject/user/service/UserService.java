package com.calendarproject.user.service;



import com.calendarproject.auth.dto.SessionUser;
import com.calendarproject.core.security.PasswordEncoder;
import com.calendarproject.user.dto.DeleteUserRequest;
import com.calendarproject.user.dto.GetUserResponse;
import com.calendarproject.user.dto.UpdateUserRequest;
import com.calendarproject.user.dto.UpdateUserResponse;
import com.calendarproject.user.entity.User;
import com.calendarproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<GetUserResponse> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new GetUserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getCreatedAt(),
                        user.getModifiedAt()
                )).toList();
    }

    @Transactional(readOnly = true)
    public GetUserResponse findOne(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("해당 유저가 없습니다.")
        );
        return new GetUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    @Transactional
    public UpdateUserResponse update(SessionUser sessionUser, UpdateUserRequest request) {
        User user = userRepository.findById(sessionUser.id()).orElseThrow(
                () -> new RuntimeException("해당 유저가 없습니다.")
        );
        boolean matches = passwordEncoder.matches(request.password(), user.getPassword());
        if (!matches) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        user.update(request.name());
        return new UpdateUserResponse(
                user.getId(),
                user.getName(),
                user.getModifiedAt()
        );
    }

    @Transactional
    public void delete(SessionUser sessionUser, DeleteUserRequest request) {
        User user = userRepository.findById(sessionUser.id()).orElseThrow(
                () -> new RuntimeException("해당 유저가 없습니다.")
        );
        boolean matches = passwordEncoder.matches(request.password(), user.getPassword());
        if (!matches) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        userRepository.delete(user);
    }
}