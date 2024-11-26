package mk.frizer.web.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import mk.frizer.model.BaseUser;
import mk.frizer.model.exceptions.InvalidArgumentsException;
import mk.frizer.model.exceptions.InvalidUsernameOrPasswordException;
import mk.frizer.service.AuthService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    public LoginController(AuthService authService, PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String getLoginPage(Model model) {
        model.addAttribute("bodyContent", "login");
        return "master-template";
    }

    @PostMapping
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        @RequestParam(required = false) String redirect,
                        RedirectAttributes redirectAttributes,
                        Model model, HttpSession session) {
        BaseUser user = null;
        try {
            user = authService.login(username, password);
        } catch (InvalidUsernameOrPasswordException | InvalidArgumentsException exception) {
            model.addAttribute("bodyContent", "login");
            model.addAttribute("hasError", true);
            model.addAttribute("error", exception.getMessage());
            return "master-template";
        }

        session.setAttribute("user", user);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
        try {
            if (redirect != null && !redirect.isEmpty()) {
                return "redirect:" + redirect;
            } else {
                return "redirect:/home";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Login failed. Please try again.");
            return "redirect:/login";
        }
    }
//        try {
//            user = authService.login(username, passwordEncoder.encode(password));
//        } catch (InvalidUsernameOrPasswordException | InvalidArgumentsException exception) {
//            model.addAttribute("bodyContent", "login");
//            model.addAttribute("hasError", true);
//            model.addAttribute("error", exception.getMessage());
//            return "master-template";
//        }
//
//        session.setAttribute("user", user);
//        try {
//            if (redirect != null && !redirect.isEmpty()) {
//                return "redirect:" + redirect;
//            } else {
//                return "redirect:/home";
//            }
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", "Login failed. Please try again.");
//            return "redirect:/login";
//        }


}
