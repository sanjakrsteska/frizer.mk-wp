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
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "tags")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Salon> salonsWithTag;
    public Tag(String name) {
        this.name = name;
        salonsWithTag = new ArrayList<>();
    }
}
