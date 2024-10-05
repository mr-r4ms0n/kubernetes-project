package mx.com.ktm.msc.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@SpringBootApplication
public class MscAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(MscAuthApplication.class, args);
	}

}
