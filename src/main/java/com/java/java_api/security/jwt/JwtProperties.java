package com.java.java_api.security.jwt;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by coarse_horse on 27/07/2020
 */
@Component
@ConfigurationProperties("jwt")
@Data
@NoArgsConstructor
public class JwtProperties {
    
    private String secretKey;
    private String header;
    private String tokenPrefix;
    private Integer tokenExpirationAfterDays;
}
