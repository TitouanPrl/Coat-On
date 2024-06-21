package io.coaton.recommendation;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RecommendationApplication {
	private final String host = "http://localhost";
	@Value("${server.port}")
	private String port;
	private final String suffix = "/swagger-ui/4.15.5/index.html";
	private final String swagger_url = host + port + suffix;

	public static void main(String[] args) {
		SpringApplication.run(RecommendationApplication.class, args);
	}

	@Bean
	public WebClient.Builder getWebClientBuilder() {
		return WebClient.builder();
	}

	@Bean
	public OpenAPI springShopOpenAPI() {
		final String swagger_version = "v0.0.1";
		final String swagger_title = "CoatOn - Activity";
		final String swagger_license_name = "Apache 2.0";
		final String swagger_license_url = "http://springdoc.org";

		return new OpenAPI()
				.info(new Info().title(swagger_title)
						.description(swagger_title)
						.version(swagger_version)
						.license(new License().name(swagger_license_name).url(swagger_license_url)))
				.externalDocs(new ExternalDocumentation()
						.description(swagger_title)
						.url(swagger_url));
	}

}
