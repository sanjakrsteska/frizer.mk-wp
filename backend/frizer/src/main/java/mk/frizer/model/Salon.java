package mk.frizer.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Salon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String location;
    @ManyToOne
    private City city;

    private String phoneNumber;
    //cascade = Cascade.ALL
    @OneToMany(mappedBy = "salon", fetch = FetchType.EAGER, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Employee> employees;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "salon")
    private List<Treatment> salonTreatments;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Tag> tags;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @Nullable
    private BusinessOwner owner;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> imagePaths;

    private Double rating;
    private Integer numberOfReviews;

    private Float latitude;
    private Float longitude;

    public Salon(String name, String description, String location, City city, String phoneNumber,
                 BusinessOwner owner ,Float latitude, Float longitude) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.employees = new ArrayList<>();
        this.salonTreatments = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.owner = owner;
        this.imagePaths = List.of(
                "/images/salons/default/default_salon_1.jpg",
                "/images/salons/default/default_salon_2.jpg",
                "/images/salons/default/default_salon_3.jpg",
                "/images/salons/default/default_salon_4.jpg"
        );
        this.rating = 0.0;
        this.numberOfReviews = 0;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
