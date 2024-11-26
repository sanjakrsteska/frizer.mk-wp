package mk.frizer.web.controller;

import mk.frizer.model.BaseUser;
import mk.frizer.model.BusinessOwner;
import mk.frizer.model.dto.BaseUserUpdateDTO;
import mk.frizer.model.exceptions.InvalidArgumentsException;
import mk.frizer.model.exceptions.InvalidUsernameOrPasswordException;
import mk.frizer.model.exceptions.UserNotFoundException;
import mk.frizer.service.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Optional;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final BaseUserService baseUserService;
    private final BusinessOwnerService businessOwnerService;
    private final CityService cityService;
    private final ReviewService reviewService;
    private final AuthService authService;

    public ProfileController(BaseUserService baseUserService, BusinessOwnerService businessOwnerService, CityService cityService, ReviewService reviewService, AuthService authService) {
        this.baseUserService = baseUserService;
        this.businessOwnerService = businessOwnerService;
        this.cityService = cityService;
        this.reviewService = reviewService;
        this.authService = authService;
    }

    @GetMapping
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        BaseUser user = (BaseUser) userDetails;

        Optional<BusinessOwner> businessOwner = businessOwnerService
                .getBusinessOwnerByBaseUserId(user.getId());

        businessOwner.ifPresent(owner -> {
            model.addAttribute("businessOwner", businessOwner.get());
            model.addAttribute("salonRatings", reviewService.getStatisticsForSalon(businessOwner.get().getSalonList()));
        });

        model.addAttribute("cities", cityService.getCities().stream().skip(1));
        model.addAttribute("user", user);
        model.addAttribute("bodyContent", "profile");
        return "master-template";
    }

    @GetMapping("/edit")
    public String editProfile(Model model,@RequestParam(required = false) String error) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        BaseUser user = (BaseUser) userDetails;
        model.addAttribute("user", user);
        model.addAttribute("bodyContent", "profile-edit");
        if(error != null) {
            model.addAttribute("hasError",true);

        }
        return "master-template";
    }

    @PostMapping("/edit")
    public String editProfile(@RequestParam String firstName,
                              @RequestParam String lastName,
                              @RequestParam String phoneNumber,
                              Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        BaseUser user = (BaseUser) userDetails;

        baseUserService.updateBaseUser(user.getId(), new BaseUserUpdateDTO(firstName, lastName, phoneNumber));
        // Retrieve the updated user information

        // Create a new Authentication token with the updated user details
        UserDetails updatedUserDetails = baseUserService.getBaseUserById(user.getId())
                .orElseThrow(UserNotFoundException::new);

        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                updatedUserDetails,
                authentication.getCredentials(),
                authentication.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(newAuth);

        return "redirect:/profile";
    }

    @PostMapping("/password/edit")
    public String changeUserPassword(Locale locale,
                                     @RequestParam String oldPassword,
                                     @RequestParam String newPassword) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        BaseUser user = null;
        try {
            user = authService.login(email, oldPassword);
        } catch (InvalidUsernameOrPasswordException | InvalidArgumentsException exception) {

            return "redirect:/profile/edit?error="+exception.getMessage();
        }
        baseUserService.changeBaseUserPassword(user.getId(), newPassword);
        return "redirect:/profile";
    }
}

