package org.Camp.Service;

import org.Camp.Model.Entities.Reservation;
import org.Camp.Model.Request.ReservationRequest;

import java.util.List;

public interface ReservationService {
    List<ReservationRequest> findAll();
    List<ReservationRequest> findByUserNameAndCampName(String userName, String campName, boolean checkedOut);
    Reservation findById(Long id);
    Reservation save(Reservation reservation);
    Reservation update(Reservation reservation);
    Reservation changeCheckoutStatus(Long id, boolean checkedOut);
    boolean delete(Long id);
}
