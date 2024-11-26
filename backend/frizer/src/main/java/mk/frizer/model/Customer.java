package mk.frizer.model;

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
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinColumn(name = "appointment_customer_active_id")
    private List<Appointment> appointmentsActive;
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinColumn(name = "appointment_customer_history_id")
    private List<Appointment> appointmentsHistory;
    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "base_user_id")
    private BaseUser baseUser;

    public Customer(BaseUser baseUser) {
        this.baseUser = baseUser;
        this.appointmentsActive = new ArrayList<>();
        this.appointmentsHistory = new ArrayList<>();
    }

    public String getFullName(){
        return String.format("%s %s", baseUser.getFirstName(), baseUser.getLastName());
    }
}