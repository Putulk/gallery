package personal.gallery.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "images")
@Data
public class Image {
    @Id
    private String id;
    
    private String title;
    
    @DBRef
    private User user;
    
    private String contentType;
    
    private byte[] data;
    
    private String fileName;
}
