package personal.gallery.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import personal.gallery.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, Long>{
    User findByUsername(String username);
}
