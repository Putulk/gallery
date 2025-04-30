package personal.gallery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class GalleryApplication {

	public static void main(String[] args) {
		SpringApplication.run(GalleryApplication.class, args);
	}

}
