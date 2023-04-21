package org.Camp.Repository;

import org.Camp.Model.Entities.Rating;
import org.Camp.Model.Request.RatingRequest;

import java.util.List;
import java.util.Optional;

public interface RatingRepository {

    List<RatingRequest> findAll();
    List<RatingRequest> findByUserAndCamp(String user, String camp);
    Optional<Rating> findById(Long id);
//    List<Rating> findByUserId(Long id);
    Optional<Rating> findByReservationId(Long reservationId);
//    List<Rating> findByUserIdAndCampId(Long userId, Long campId);
    int save(Rating rating);
    int update(Rating rating);
    int delete(Long id);
}
