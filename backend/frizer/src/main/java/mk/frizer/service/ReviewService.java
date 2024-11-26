package mk.frizer.service;

import mk.frizer.model.*;
import mk.frizer.model.dto.ReviewAddDTO;
import mk.frizer.model.dto.ReviewUpdateDTO;
import mk.frizer.model.enums.Role;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ReviewService {
    List<Review> getReviews();

    Optional<Review> getReviewById(Long id);

    Optional<Review> createReviewForEmployee(ReviewAddDTO reviewAddDTO);

    Optional<Review> createReviewForCustomer(ReviewAddDTO reviewAddDTO);

    Optional<Review> updateReview(Long id, ReviewUpdateDTO reviewUpdateDTO);

    Optional<Review> deleteReviewById(Long id);

    Map<Long, ReviewStats> getStatisticsForEmployee(List<Employee> employees);

    ReviewStats getStatisticsForEmployee(Employee employee);

    Map<Long, ReviewStats> getStatisticsForSalon(List<Salon> salons);

    ReviewStats getStatisticsForSalon(Salon salon);

    List<Review> getReviewsForEmployees(List<Employee> employees);
}
