package mk.frizer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Data
@Entity
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime dateFrom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime dateTo;
    @ManyToOne
    private Treatment treatment;

    @ManyToOne
    @JsonIgnore
    private Salon salon;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private boolean attended;

    public Appointment(LocalDateTime dateFrom, LocalDateTime dateTo, Treatment treatment, Salon salon, Employee employee, Customer customer) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.treatment = treatment;
        this.salon = salon;
        this.employee = employee;
        this.customer = customer;
        this.attended = false;
    }

    public String getDateFromFormatted(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return dateFrom.format(formatter);
    }

    public String getDateToFormatted(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return dateTo.format(formatter);
    }

    public boolean isDateNDaysFromNow(int days) {
//        LocalDateTime targetDate = dateFrom.plusDays(days);
//        return dateFrom.truncatedTo(ChronoUnit.DAYS).equals(targetDate.truncatedTo(ChronoUnit.DAYS));
        LocalDateTime now = LocalDateTime.now();
        return !dateFrom.isAfter(now.plusDays(days));
    }

    public boolean isDateOneDayFromNow(){
        return isDateNDaysFromNow(1);
    }
}