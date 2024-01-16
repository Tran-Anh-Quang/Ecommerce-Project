package com.ecommerce.admin;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String dirName = "user-photos";
        String dirName1 = "category-photos";
        String dirName2 = "brand-photo";

        Path userPhotosDir = Paths.get(dirName);
        Path categoryPhotosDir = Paths.get(dirName1);
        Path brandPhotosDir = Paths.get(dirName2);

        String userPhotosPath = userPhotosDir.toFile().getAbsolutePath();
        String categoryPhotosPath = categoryPhotosDir.toFile().getAbsolutePath();
        String brandPhotosPath = brandPhotosDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/" + dirName + "/**")
                .addResourceLocations("file:/" + userPhotosPath + "/")
                .addResourceLocations("file:/" + categoryPhotosPath + "/")
                .addResourceLocations("file:/" + brandPhotosPath + "/");
    }


}
