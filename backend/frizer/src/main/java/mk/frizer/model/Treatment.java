package mk.frizer.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Treatment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer durationMultiplier;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Salon salon;
    private Double price;

    public Treatment(String name, Salon salon, Double price, Integer durationMultiplier) {
        this.name = name;
        this.salon = salon;
        this.price = price;
        this.durationMultiplier = durationMultiplier;
    }
}

