package mk.frizer.service;

import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import mk.frizer.model.Appointment;
import mk.frizer.model.Customer;
import mk.frizer.model.Employee;
import mk.frizer.model.Review;
import mk.frizer.model.dto.EmployeeAddDTO;
import mk.frizer.model.enums.Role;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EmployeeService {
    List<Employee> getEmployees();

    Optional<Employee> getEmployeeById(Long id);
    Optional<Employee> getEmployeeByBaseUserId(Long id);


    List<Employee> getEmployeesForSalon(Long id);

    Optional<Employee> createEmployee(EmployeeAddDTO employeeAddDTO);

    Optional<Employee> deleteEmployeeById(Long id);
    Optional<Employee> deleteEmployeeByIdFromSalon(Long id, Long salonId);

    Optional<Employee> addActiveAppointmentForEmployee(Appointment appointment);

    Optional<Employee> addHistoryAppointmentForEmployee(Appointment appointment);
}
