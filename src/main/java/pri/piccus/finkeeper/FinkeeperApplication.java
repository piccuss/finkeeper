package pri.piccus.finkeeper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinkeeperApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(FinkeeperApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(FinkeeperApplication.class, args);
	}

}
