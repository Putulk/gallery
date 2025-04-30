package personal.gallery.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import personal.gallery.model.Image;
import personal.gallery.model.User;

public interface ImageRepository extends MongoRepository<Image, String> {
    List<Image> findByUser(User user);
}
