package com.happyaging.fallprevention.security.filter;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.happyaging.fallprevention.token.service.JwtTokenService;

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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class AccessTokenAuthorizationFilter extends GenericFilterBean {

    private final JwtTokenService jwtTokenService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;

        // Pre-flight 요청, 혹은 특정 URL은 필터 통과 시키고 바로 진행
        if (isPreflightRequest(servletRequest) || isRefreshOrLogoutRequest(servletRequest)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            String accessToken = jwtTokenService.resolveAccessToken(servletRequest);
            if (accessToken != null) {
                Jws<Claims> claims = jwtTokenService.extractClaims(accessToken);
                String email = jwtTokenService.extractEmail(claims);
                Authentication authentication = jwtTokenService.getAuthentication(email);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            chain.doFilter(request, response);

        } catch (ExpiredJwtException e) {
            log.warn("Access token has expired: {}", e.getMessage());
            writeErrorResponse(servletResponse, HttpServletResponse.SC_UNAUTHORIZED, "ACCESS_TOKEN_EXPIRED");

        } catch (JwtException e) {
            log.warn("Invalid access token: {}", e.getMessage());
            writeErrorResponse(servletResponse, HttpServletResponse.SC_UNAUTHORIZED, "ACCESS_TOKEN_INVALID");

        } catch (Exception e) {
            log.error("Error in AccessTokenAuthorizationFilter: {}", e.getMessage(), e);
            writeErrorResponse(servletResponse, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR");
        }
    }

    private boolean isPreflightRequest(HttpServletRequest request) {
        return "OPTIONS".equalsIgnoreCase(request.getMethod());
    }

    private boolean isRefreshOrLogoutRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return
            uri.contains("/auth/refresh") ||
            uri.contains("/auth/logout") ||
            uri.contains("/auth/register") ||
            uri.contains("/auth/login");
    }

    private void writeErrorResponse(HttpServletResponse response, int statusCode, String errorMsg) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(String.format("""
            {
              "errorMsg": "%s"
            }
            """, errorMsg));
        response.getWriter().flush();
    }
}
