package io.coaton.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;


@EnableDiscoveryClient
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class WeatherApplication {


  @Bean
	public WebClient.Builder getWebClientBuilder() {
		return  WebClient.builder();
	}

  public static void main(String[] args) {
    SpringApplication.run(WeatherApplication.class, args);
  }
}
