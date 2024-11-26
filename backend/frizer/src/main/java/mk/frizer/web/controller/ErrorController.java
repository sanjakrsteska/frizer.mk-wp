package mk.frizer.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/app-error")
public class ErrorController {

    @GetMapping("")
    public String error(@RequestParam String message, Model model) {
        model.addAttribute("message", message);
        model.addAttribute("bodyContent", "error");
        return "master-template";
    }
}
