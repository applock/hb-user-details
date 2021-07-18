package hb.details.hbuserdetails;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class HbUserDetailsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HbUserDetailsApplication.class, args);
	}

}
