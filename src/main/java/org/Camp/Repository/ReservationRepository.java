package org.Camp.Repository;

import org.Camp.Model.Entities.Reservation;
import org.Camp.Model.Request.ReservationRequest;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    List<ReservationRequest> findAll();
    Optional<Reservation> findById(Long id);
    List<ReservationRequest> findByUserNameAndCampName(String userName, String campName, boolean checkedOut);
    Optional<Reservation> findByUserIdAndCampId(Long userId, Long campId);
    Optional<Reservation> findByCampId(Long id);
    int save(Reservation reservation);
    int update(Reservation reservation);
    int delete(Long id);
}
