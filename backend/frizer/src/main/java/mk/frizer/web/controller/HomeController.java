package mk.frizer.web.controller;

import mk.frizer.model.Salon;
import mk.frizer.service.BaseUserService;
import mk.frizer.service.CityService;
import mk.frizer.service.ReviewService;
import mk.frizer.service.SalonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping({"/home", "/"})
public class HomeController {
    private final SalonService salonService;
    private final CityService cityService;

    public HomeController(SalonService salonService, CityService cityService) {
        this.salonService = salonService;
        this.cityService = cityService;
    }

    @GetMapping
    public String getHomePage(Model model) {
        List<Salon> salons = salonService.getTop8Salons();
        model.addAttribute("salons", salons);
        model.addAttribute("cities", cityService.getCities());
        model.addAttribute("topCities", cityService.getTop6Cities());
        model.addAttribute("bodyContent", "home");
        return "master-template";
    }
}
