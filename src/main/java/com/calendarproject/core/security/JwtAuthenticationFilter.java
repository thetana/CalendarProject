package com.calendarproject.core.security;

import com.calendarproject.core.dto.JwtAdminInfo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String BEARER = "Bearer ";

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Authorization 헤더에서 토큰 추출
        String token = resolveToken(request);

        // 토큰이 존재하고 아직 인증되지 않은 경우에만 처리
        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                // 토큰에서 관리자 정보 추출 (검증 포함)
                JwtAdminInfo adminInfo = jwtProvider.extractAdminInfo(token);

                // Spring Security 인증 객체 생성
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                adminInfo,         // principal
                                null,                       // credentials (JWT에서는 사용 안 함)
                                List.of(new SimpleGrantedAuthority(
                                        adminInfo.role().getAuthority() // ROLE_XXX
                                ))
                        );

                // SecurityContext에 인증 정보 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (RuntimeException e) {
                // 토큰이 유효하지 않으면 인증 세팅 없이 다음 필터로 진행
            }
        }

        // 반드시 항상 호출되어야 함
        filterChain.doFilter(request, response);
    }

    /**
     * Authorization 헤더에서 Bearer 접두어 제거 후 토큰 반환
     */
    private String resolveToken(HttpServletRequest request) {

        String bearer = request.getHeader("Authorization");

        if (bearer != null && bearer.startsWith(BEARER)) {
            return bearer.substring(BEARER.length());
        }

        return null;
    }
}
