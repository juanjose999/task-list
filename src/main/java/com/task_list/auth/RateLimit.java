package com.task_list.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.*;

@Order(1)
@Component
public class RateLimit extends OncePerRequestFilter {

    private static final Map<String, List<Long>> requestLog = new HashMap<>();
    private static final int MAX_REQUESTS = 10;
    private static final long TIME_WINDOW_MS = 60 * 1000;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String uri = request.getRequestURI();
        long currentTime = System.currentTimeMillis();

        synchronized (requestLog) {
            requestLog.putIfAbsent(uri, new ArrayList<>());
            List<Long> timestamps = requestLog.get(uri);

            timestamps.removeIf(time -> (currentTime - time) > TIME_WINDOW_MS);

            if (timestamps.size() >= MAX_REQUESTS) {
                response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                response.getWriter().write("Too many requests. Please wait.");
                return;
            }

            timestamps.add(currentTime);
        }

        filterChain.doFilter(request, response);
    }

}
