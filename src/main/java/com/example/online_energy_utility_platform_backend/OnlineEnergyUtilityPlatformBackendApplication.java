package com.example.online_energy_utility_platform_backend;

import com.example.online_energy_utility_platform_backend.service.MessageOperationsService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication

@EntityScan("com.example.online_energy_utility_platform_backend.model")
public class OnlineEnergyUtilityPlatformBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineEnergyUtilityPlatformBackendApplication.class, args);
//        try {
//            MessageOperationsService.main(new String[]{});
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
        }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedOrigins("*")
                        .allowedHeaders("*");
            }
        };
    }
}
