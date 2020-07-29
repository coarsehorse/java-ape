package com.java.ape.init;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by coarse_horse on 29/07/2020
 */
@Component
@ConfigurationProperties("default.admin")
@Data
@NoArgsConstructor
public class DefaultAdminProperties {
    
    private Boolean createDefault;
    private String nickname;
    private String password;
}
