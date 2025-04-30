package personal.gallery.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import personal.gallery.model.Image;
import personal.gallery.service.ImageService;

@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = {"http://localhost:8001", "http://localhost:63342"}, allowCredentials = "true")
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            logger.info("Uploading image: {} for user: {}", title, userDetails.getUsername());
            Image savedImage = imageService.saveImage(file, title, userDetails.getUsername());
            return ResponseEntity.ok(savedImage);
        } catch (IOException e) {
            logger.error("Failed to upload image", e);
            return ResponseEntity.badRequest().body("Failed to upload image: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Image>> getAllImages(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            logger.info("Fetching images for user: {}", userDetails.getUsername());
            List<Image> images = imageService.getAllImagesForUser(userDetails.getUsername());
            return ResponseEntity.ok(images);
        } catch (Exception e) {
            logger.error("Failed to fetch images", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getImage(@PathVariable String id) {
        try {
            logger.info("Fetching image with id: {}", id);
            Image image = imageService.getImage(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(image.getContentType()))
                    .body(image.getData());
        } catch (Exception e) {
            logger.error("Failed to fetch image with id: {}", id, e);
            return ResponseEntity.notFound().build();
        }
    }

    // Additional endpoints for updating and deleting images
}
