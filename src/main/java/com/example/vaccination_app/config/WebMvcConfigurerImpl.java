package com.example.vaccination_app.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;

public class WebMvcConfigurerImpl implements WebMvcConfigurer {
	public static final String FILE_UPLOAD_PATH = "src/main/resources/static";
	public static final String FILE_SERVE_PATH = "target/classes/static";

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		var absoluteUploadPath = Path.of(FILE_UPLOAD_PATH).toAbsolutePath();
		registry
				.addResourceHandler("/" + FILE_UPLOAD_PATH + "/**")
				.addResourceLocations("file:/" + absoluteUploadPath)
				.setCachePeriod(0);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/logout").setViewName("logout");
		registry.addViewController("/403").setViewName("error/403");
	}
}
