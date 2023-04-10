package org.Camp.Repository;

import org.Camp.Model.Entities.Rating;

import java.util.List;
import java.util.Optional;

public interface RatingRepository {

    List<Rating> findAll();
    Optional<Rating> findById(Long id);
    Optional<Rating> findByUserId(Long id);
    int save(Rating rating);
    int update(Rating rating);
    int delete(Long id);
}
