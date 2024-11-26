package mk.frizer.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.frizer.model.enums.Role;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinColumn(name = "appointment_employee_active_id")
    private List<Appointment> appointmentsActive;
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinColumn(name = "appointment_employee_history_id")
    private List<Appointment> appointmentsHistory;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Salon salon;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "base_user_id")
    private BaseUser baseUser;

    public Employee(BaseUser baseUser, Salon salon) {
        this.baseUser = baseUser;
        this.appointmentsActive = new ArrayList<>();
        this.appointmentsHistory = new ArrayList<>();
        this.salon = salon;
    }

    public String getFullName(){
        return String.format("%s %s", baseUser.getFirstName(), baseUser.getLastName());
    }
}