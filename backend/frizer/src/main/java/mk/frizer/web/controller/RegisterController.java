package mk.frizer.web.controller;

import mk.frizer.model.enums.Role;
import mk.frizer.model.exceptions.InvalidArgumentsException;
import mk.frizer.model.exceptions.PasswordsDoNotMatchException;
import mk.frizer.model.exceptions.UsernameAlreadyExistsException;
import mk.frizer.service.AuthService;
import mk.frizer.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final AuthService authService;
    private final UserService userService;

    public RegisterController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model) {
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        model.addAttribute("bodyContent", "register");
        return "master-template";
    }

    @PostMapping
    public String register(@RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String repeatedPassword,
                           @RequestParam String firstName,
                           @RequestParam String lastName,
                           @RequestParam String phoneNumber) {
        try{
            this.userService.register(email, password, repeatedPassword, firstName, lastName, phoneNumber);
            return "redirect:/login";
        } catch (UsernameAlreadyExistsException exception) {
            return "redirect:/register?error=" + exception.getMessage();
        }
    }
}

