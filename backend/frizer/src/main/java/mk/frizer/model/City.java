package mk.frizer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
public class City{
    @Id
    private String name;
    @OneToMany(mappedBy = "city")
    private List<Salon> salonsInCity;

    public City() {
    }

    public City(String name) {
        this.name = name;
        this.salonsInCity = new ArrayList<>();
    }

    @Override
    public String toString(){
        return name;
    }
}

