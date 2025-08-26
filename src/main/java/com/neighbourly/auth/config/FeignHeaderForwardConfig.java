package com.neighbourly.auth.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;
import java.util.Set;

@Configuration
public class FeignHeaderForwardConfig {

    // Forward only these (adjust as needed)
    private static final Set<String> ALLOWED = Set.of(
            "authorization",          // Bearer <token>
            "user_id",
            "uuid"
    );

    // Never forward these (defensive)
    private static final Set<String> DISALLOWED = Set.of(
            "content-length",
            "transfer-encoding",
            "host",
            "connection",
            "expect",
            "upgrade",
            "via"
    );


    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            // Forward the Authorization header
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            Enumeration<String> headerNames = requestAttributes.getRequest().getHeaderNames();
            if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                    String headerName = headerNames.nextElement();
                    headerName.toLowerCase();
                    if (DISALLOWED.contains(headerName)) {
                        continue; // Never forward these
                    }

                    String headerValue = requestAttributes.getRequest().getHeader(headerName);
                    requestTemplate.header(headerName, headerValue);
                }
            }

        };


    }

}
