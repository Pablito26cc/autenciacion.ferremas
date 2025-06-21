package cl.integracion.ms_auth_bs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsAuthBsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsAuthBsApplication.class, args);
	}

}
