package mk.frizer.web.rest;

import mk.frizer.model.BaseUser;
import mk.frizer.model.Review;
import mk.frizer.model.Salon;
import mk.frizer.model.dto.ReviewAddDTO;
import mk.frizer.model.dto.ReviewUpdateDTO;
import mk.frizer.model.dto.SalonAddDTO;
import mk.frizer.model.dto.SalonUpdateDTO;
import mk.frizer.model.exceptions.UserNotFoundException;
import mk.frizer.service.ReviewService;
import mk.frizer.service.SalonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/api/reviews", "/api/review"})
@CrossOrigin(origins = {"localhost:3000","localhost:3001"})
public class ReviewRestController {
    private final ReviewService reviewService;

    public ReviewRestController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping()
    public List<Review> getAllReviews(){
        return reviewService.getReviews();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReview(@PathVariable Long id){
        return this.reviewService.getReviewById(id)
                .map(review -> ResponseEntity.ok().body(review))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add-for-employee")
    public ResponseEntity<Review> createReviewForEmployee(@RequestBody ReviewAddDTO reviewAddDto) {
        System.out.println(reviewAddDto);
        return this.reviewService.createReviewForEmployee(reviewAddDto)
                .map(review -> ResponseEntity.ok().body(review))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/add-for-customer")
    public ResponseEntity<Review> createReviewForCustomer(@RequestBody ReviewAddDTO reviewAddDto) {
        System.out.println(reviewAddDto);
        return this.reviewService.createReviewForCustomer(reviewAddDto)
                .map(review -> ResponseEntity.ok().body(review))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody ReviewUpdateDTO reviewUpdateDTO) {
        return this.reviewService.updateReview(id, reviewUpdateDTO)
                .map(review -> ResponseEntity.ok().body(review))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Review> deleteReviewById(@PathVariable Long id) {
        Optional<Review> review = this.reviewService.deleteReviewById(id);
        try{
            this.reviewService.getReviewById(id);
            return ResponseEntity.badRequest().build();
        }
        catch(UserNotFoundException exception){
            return ResponseEntity.ok().body(review.get());
        }
    }
}
