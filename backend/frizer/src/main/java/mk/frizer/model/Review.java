package mk.frizer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private BaseUser userFrom;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BaseUser userTo;
    private Double rating;
    private String comment;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-dd-MM HH:mm")
    private LocalDateTime date;

    public Review(BaseUser userFrom, BaseUser userTo, Double rating, String comment) {
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.rating = rating;
        this.comment = comment;
        this.date = LocalDateTime.now();
    }
}
