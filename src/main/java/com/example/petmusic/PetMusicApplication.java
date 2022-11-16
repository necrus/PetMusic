package com.example.petmusic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PetMusicApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetMusicApplication.class, args);
    }

//    @Bean
//    MultipartConfigElement multipartConfigElement() {
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        factory.setMaxFileSize(DataSize.ofKilobytes(128));
//        factory.setMaxRequestSize(DataSize.ofKilobytes(128));
//        return factory.createMultipartConfig();
//    }
}
