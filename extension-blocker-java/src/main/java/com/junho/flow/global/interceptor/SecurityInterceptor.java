package com.junho.flow.global.interceptor;

import com.junho.flow.extensionblock.domain.UploadHistory;
import com.junho.flow.extensionblock.domain.repository.UploadHistoryRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@Profile("!test")
@RequiredArgsConstructor
public class SecurityInterceptor implements HandlerInterceptor{

    private final UploadHistoryRepository uploadHistoryRepository;

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex
    ) {
        if (request.getAttribute("handledSecurityException") != null) {
            log.warn("SecurityException 발생-------");

            String clientIpAddress = getClientIpAddress(request);
            log.warn("clientIpAddress: {}", clientIpAddress);

            Long userId = getUserId(request);
            log.warn("userId: {}", userId);
            UploadHistory userInfo = new UploadHistory(clientIpAddress, userId);

            uploadHistoryRepository.save(userInfo);
        }

    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        if (xForwardedForHeader == null) {
            return request.getRemoteAddr();
        } else {
            // X-Forwarded-For는 원래 클라이언트 IP/ 이후의 프록시 서버의 IP가 쉼표로 구분된 경우 첫번째가 클라이언트 IP
            return xForwardedForHeader.split(",")[0].trim();
        }
    }

    private Long getUserId(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        int lastIndex = requestURI.lastIndexOf('/');
        if (lastIndex != -1) {
            return Long.parseLong(requestURI.substring(lastIndex + 1));
        }
        return null;
    }

}
