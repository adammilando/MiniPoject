package org.Camp.Service;

import org.Camp.Model.Entities.Rating;
import org.Camp.Model.Request.RatingRequest;

import java.util.List;

public interface RatingService {
    List<RatingRequest> findAll();
    List<RatingRequest> findByUserAndCamp(String user, String camp);
    Rating findById(Long id);
    Rating save(Rating rating);
    Rating update(Rating rating);
    boolean delete(Long id);
}
