package mk.frizer.web.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
public class UserPositionController {

    @PostMapping("/save-user-position")
    public ResponseEntity<String> saveUserPosition(@RequestBody String userPosition, HttpSession session) {
        session.setAttribute("userGeolocation", userPosition);
        return new ResponseEntity<>("User position saved successfully", HttpStatus.OK);
    }
}
