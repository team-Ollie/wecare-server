package ollie.wecare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WecareApplication {

	public static void main(String[] args) {
		SpringApplication.run(WecareApplication.class, args);
	}

}
