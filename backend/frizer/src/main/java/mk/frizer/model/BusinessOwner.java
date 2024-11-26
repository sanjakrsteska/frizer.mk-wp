package mk.frizer.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class BusinessOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner")
    private List<Salon> salonList;
    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "base_user_id")
    private BaseUser baseUser;

    public BusinessOwner(BaseUser baseUser) {
        this.baseUser = baseUser;
        this.salonList = new ArrayList<>();
    }
    @Override
    public String toString() {
        return "BusinessOwner{" +
                "id=" + id +
                ", salonList= " + salonList +
                "}";
    }

    public String getFullName(){
        return String.format("%s %s", baseUser.getFirstName(), baseUser.getLastName());
    }
}
