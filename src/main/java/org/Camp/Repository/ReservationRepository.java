package org.Camp.Repository;

import org.Camp.Model.Entities.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    List<Reservation> findAll();
    Optional<Reservation> findById(Long id);
    Optional<Reservation> findByUserIdAndCampId(Long userId, Long campId);
    Optional<Reservation> findByCampId(Long id);
    int save(Reservation reservation);
    int update(Reservation reservation);
    int delete(Long id);
}
