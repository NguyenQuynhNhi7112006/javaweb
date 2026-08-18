package an_paper.controller;

import an_paper.entity.*;
import an_paper.repository.*;
import an_paper.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired private ReviewRepository reviewRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private FileUploadService fileUploadService;

    @PostMapping("/add")
    public String addReview(@RequestParam Long productId,
                            @RequestParam Integer rating,
                            @RequestParam String comment,
                            @RequestParam(value = "mediaFile", required = false) MultipartFile mediaFile,
                            @AuthenticationPrincipal UserDetails userDetails) throws Exception {

        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

        String mediaUrl = null;
        String mediaType = null;

        if (mediaFile != null && !mediaFile.isEmpty()) {
            mediaUrl = fileUploadService.saveFile(mediaFile);
            String contentType = mediaFile.getContentType();
            if (contentType != null && contentType.startsWith("video")) {
                mediaType = "VIDEO";
            } else {
                mediaType = "IMAGE";
            }
        }

        Review review = Review.builder()
            .user(user)
            .product(product)
            .rating(rating)
            .comment(comment)
            .mediaUrl(mediaUrl)
            .mediaType(mediaType)
            .build();

        reviewRepository.save(review);
        return "redirect:/products/" + productId;
    }
}
