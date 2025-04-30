package personal.gallery.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class User {
    @Id
    private String id;
    private String username;
    private String password;

    // Getters and setters
}
