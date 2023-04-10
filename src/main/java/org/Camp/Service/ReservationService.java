package org.Camp.Service;

import org.Camp.Model.Entities.Reservation;

import java.util.List;

public interface ReservationService {
    List<Reservation> findAll();
    Reservation findById(Long id);
    Reservation save(Reservation reservation);
    Reservation update(Reservation reservation);
    Reservation changeCheckoutStatus(Long id, boolean checkedOut);
    boolean delete(Long id);
}
