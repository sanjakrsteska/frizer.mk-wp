package mk.frizer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewStats {
    private Double rating;
    private Integer numberOfReviews;
}
