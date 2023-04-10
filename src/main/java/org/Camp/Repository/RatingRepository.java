package org.Camp.Repository;

import org.Camp.Model.Entities.Rating;
import org.Camp.Model.Request.RatingRequest;

import java.util.List;
import java.util.Optional;

public interface RatingRepository {

    List<RatingRequest> findAll();
    List<RatingRequest> findByUserAndCamp(String user, String camp);
    Optional<Rating> findById(Long id);
    Optional<Rating> findByUserId(Long id);
    int save(Rating rating);
    int update(Rating rating);
    int delete(Long id);
}
