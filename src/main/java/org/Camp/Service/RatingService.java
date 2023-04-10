package org.Camp.Service;

import org.Camp.Model.Entities.Rating;

import java.util.List;

public interface RatingService {
    List<Rating> findAll();
    Rating findById(Long id);
    Rating save(Rating rating);
    Rating update(Rating rating);
    boolean delete(Long id);
}
