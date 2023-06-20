package com.example.sneaker_shop.Utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.*;

@Configuration
public class ImageConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(@NotNull ResourceHandlerRegistry registry) {
        exposeDirectory("photos", registry);
    }

    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get(dirName);
        String uploadPath = uploadDir.toFile().getAbsolutePath();
        if (dirName.startsWith("../")) {
            dirName = dirName.replace("../", "");
        }
        registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:///" + uploadPath + "/").setCachePeriod(0);
    }
}
