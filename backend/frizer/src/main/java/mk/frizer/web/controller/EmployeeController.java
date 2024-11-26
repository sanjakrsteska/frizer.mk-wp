package mk.frizer.web.controller;

import mk.frizer.model.Employee;
import mk.frizer.model.dto.EmployeeAddDTO;
import mk.frizer.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/add")
    public String addEmployee(@RequestParam Long userId, @RequestParam Long salonId) {
        Optional<Employee> employee = employeeService.createEmployee(new EmployeeAddDTO(userId, salonId));
        return "redirect:/salons/" + salonId;
    }

    @PostMapping("/remove")
    public String removeEmployee(@RequestParam Long employeeId, @RequestParam Long salonId) {
        Optional<Employee> employee = employeeService
                .deleteEmployeeByIdFromSalon(employeeId, salonId);
        return "redirect:/salons/" + salonId;
    }
}
