package mk.frizer.web.controller;

import mk.frizer.model.Customer;
import mk.frizer.model.dto.ReviewAddDTO;
import mk.frizer.model.exceptions.CustomerNotFoundException;
import mk.frizer.service.CustomerService;
import mk.frizer.service.ReviewService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final CustomerService customerService;

    public ReviewController(ReviewService reviewService, CustomerService customerService) {
        this.reviewService = reviewService;
        this.customerService = customerService;
    }

    @PostMapping("/add")
    public String addReview(@RequestParam Long salonId,
                            @RequestParam Long employeeId,
                            @RequestParam String comment,
                            @RequestParam Double rating,
                            RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            redirectAttributes.addFlashAttribute("message", "You need to be logged in to add a review.");
            return "redirect:/login";
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        Customer loggedInCustomer = customerService.getCustomerByEmail(email)
                .orElse(null);
        Long customerId = null;
        if (loggedInCustomer != null) {
            customerId = loggedInCustomer.getId();
        }
        reviewService.createReviewForEmployee(new ReviewAddDTO(employeeId, customerId, rating, comment));
        return "redirect:/salons/" + salonId;
    }
}
