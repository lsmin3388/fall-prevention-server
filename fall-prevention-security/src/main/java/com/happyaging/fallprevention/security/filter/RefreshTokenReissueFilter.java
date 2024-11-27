package com.happyaging.fallprevention.security.filter;

import java.io.IOException;
import java.util.Objects;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.happyaging.fallprevention.exception.RefreshTokenExpiredException;
import com.happyaging.fallprevention.exception.RefreshTokenInvalidException;
import com.happyaging.fallprevention.token.service.JwtTokenService;
import com.happyaging.fallprevention.token.service.RefreshTokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor
@Component
public class RefreshTokenReissueFilter extends GenericFilterBean {
    private final JwtTokenService jwtTokenService;
    private final RefreshTokenService refreshTokenService;

	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;

        if ("OPTIONS".equalsIgnoreCase(servletRequest.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            chain.doFilter(request, response);
            return;
        }

        String refreshToken = jwtTokenService.resolveRefreshToken(servletRequest);

        if (refreshToken != null) {
            try {
                Jws<Claims> refreshClaims = jwtTokenService.extractClaims(refreshToken);
                String email = jwtTokenService.extractEmail(refreshClaims);
                String storedRefreshToken = refreshTokenService.getRefreshToken(email);

                if (Objects.equals(storedRefreshToken, refreshToken)) {
                    Authentication authentication = jwtTokenService.getAuthentication(email);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (ExpiredJwtException e) {
                log.warn("Refresh token has expired: {}", e.getMessage());
                throw new RefreshTokenExpiredException();
            } catch (JwtException e) {
                log.warn("Invalid refresh token: {}", e.getMessage());
                throw new RefreshTokenInvalidException();
            } catch (Exception e) {
                log.error("Error in RefreshTokenFilter: {}", e.getMessage(), e);
            }
        }
        chain.doFilter(request, response);

    }
}
