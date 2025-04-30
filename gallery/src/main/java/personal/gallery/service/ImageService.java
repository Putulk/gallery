package personal.gallery.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import personal.gallery.model.Image;
import personal.gallery.model.User;
import personal.gallery.repository.ImageRepository;
import personal.gallery.repository.UserRepository;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Image> getAllImagesForCurrentUser() {
        User user = getCurrentUser();
        return imageRepository.findByUser(user);
    }

    public Image saveImageForCurrentUser(Image image) {
        User user = getCurrentUser();
        image.setUser(user);
        return imageRepository.save(image);
    }

    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();
        return userRepository.findByUsername(username);
    }

    public Image saveImage(MultipartFile file, String title, String username) throws IOException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Image image = new Image();
        image.setTitle(title);
        image.setUser(user);
        image.setContentType(file.getContentType());
        image.setData(file.getBytes());
        image.setFileName(file.getOriginalFilename());

        return imageRepository.save(image);
    }

    public List<Image> getAllImagesForUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return imageRepository.findByUser(user);
    }

    public Image getImage(String id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"));
    }

    // Additional methods for updating and deleting images
}
