package com.example.Portal.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    // Keep in sync with EmployeeProfileService.AVATAR_BASE
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String avatarsPath = "file:" + Paths.get(System.getProperty("user.home"), "portal-data", "uploads", "avatars").toAbsolutePath().toString() + "/";
        registry.addResourceHandler("/files/avatars/**")
                .addResourceLocations(avatarsPath)
                .setCachePeriod(3600); // 0 for dev, larger for prod
    }
}
