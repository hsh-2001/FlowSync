package FlowSync.FlowSync;

import org.jspecify.annotations.NonNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class FlowSyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowSyncApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {

		return new WebMvcConfigurer() {

			@Override
			public void addCorsMappings(
                    @NonNull CorsRegistry registry
			) {

				registry.addMapping("/**")
						.allowedOrigins("http://localhost:3000")
						.allowedMethods(
								"GET",
								"POST"
						)
						.allowedHeaders("*");
			}
		};
	}
}
