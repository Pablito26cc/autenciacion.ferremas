package cl.integracion.ms_auth_bff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsAuthBffApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsAuthBffApplication.class, args);
	}

}
