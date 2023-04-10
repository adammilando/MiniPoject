package org.Camp.Service.Implement;

import org.Camp.Exception.NotFoundException;
import org.Camp.Exception.ReservationException;
import org.Camp.Model.Entities.Camp;
import org.Camp.Model.Entities.Reservation;
import org.Camp.Repository.CampRepository;
import org.Camp.Repository.ReservationRepository;
import org.Camp.Service.ReservationService;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final CampRepository campRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, CampRepository campRepository) {
        this.reservationRepository = reservationRepository;
        this.campRepository = campRepository;
    }

    @Override
    public List<Reservation> findAll() {
        try {
            List<Reservation> reservations = reservationRepository.findAll();
            if (reservations.isEmpty()){
                throw new NotFoundException("Database Empty");
            }
            return reservations;
        } catch (NotFoundException e){
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to find all reservations: " + e.getMessage(), e);
        }
    }

    @Override
    public Reservation findById(Long id) {
        try {
            return reservationRepository.findById(id).orElseThrow(() -> new NotFoundException("Id "+ id + " Does Not Exists"));
        }catch (NotFoundException e){
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to find reservation by id: " + e.getMessage(), e);
        }
    }

    @Override
    public Reservation save(Reservation reservation) {
        try {
            validateReservation(reservation);
            Optional<Camp> camp = campRepository.findById(reservation.getCampId());
            if (camp.isEmpty()) {
                throw new NotFoundException("Failed to find camp with id " + reservation.getCampId());
            }
            int reservedRooms = reservation.getNumberOfSpots();
            if (camp.get().getStock() < reservedRooms) {
                throw new ReservationException("Not enough rooms available for reservation");
            }
            camp.get().setStock(camp.get().getStock() - reservedRooms);
            campRepository.update(camp.get());
            return reservationRepository.save(reservation) == 1 ? reservation : null;
        }catch (ReservationException e){
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save reservation: " + e.getMessage(), e);
        }
    }

    @Override
    public Reservation update(Reservation reservation) {
        try {
            findById(reservation.getId());
            validateReservation(reservation);
            Optional<Reservation> existingReservation = reservationRepository.findById(reservation.getId());
            if (existingReservation.isEmpty()) {
                throw new NotFoundException("Failed to find reservation with id " + reservation.getId());
            }
            Optional<Camp> camp = campRepository.findById(reservation.getCampId());
            if (camp.isEmpty()) {
                throw new NotFoundException("Failed to find camp with id " + reservation.getCampId());
            }

            // Menambah atau mengurangi stok berdasarkan perubahan pada jumlah spot yang dipesan
            int newReservedRooms = reservation.getNumberOfSpots();
            int oldReservedRooms = existingReservation.get().getNumberOfSpots();
            int roomDiff = newReservedRooms - oldReservedRooms;
            if (roomDiff > 0) {

                if (camp.get().getStock() < roomDiff) {
                    throw new ReservationException("Not enough rooms available for reservation");
                }
                camp.get().setStock(camp.get().getStock() - roomDiff);
            } else if (roomDiff < 0) {

                camp.get().setStock(camp.get().getStock() - roomDiff);
            }

            campRepository.update(camp.get());
            return reservationRepository.update(reservation) == 1 ? reservation : null;
        } catch (ReservationException e){
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update reservation: " + e.getMessage(), e);
        }
    }

    @Override
    public Reservation changeCheckoutStatus(Long id, boolean checkedOut) {
        try {
            Reservation reservation = findById(id);
            if (reservation == null) {
                throw new NotFoundException("Failed to find reservation with id " + id);
            }

            // Menambah atau mengurangi stok berdasarkan perubahan pada status checkout
            if (checkedOut != reservation.isCheckedOut()) {
                Optional<Camp> camp = campRepository.findById(reservation.getCampId());
                if (camp.isEmpty()) {
                    throw new NotFoundException("Failed to find camp with id " + reservation.getCampId());
                }

                if (checkedOut) {
                    camp.get().setStock(camp.get().getStock() + reservation.getNumberOfSpots());
                } else {

                    int newStock = camp.get().getStock() - reservation.getNumberOfSpots();
                    if (newStock < 0) {
                        throw new ReservationException("Not enough rooms available for reservation");
                    }
                    camp.get().setStock(newStock);
                }
                campRepository.update(camp.get());
            }

            reservation.setCheckedOut(checkedOut);
            return reservationRepository.update(reservation) == 1 ? reservation : null;
        } catch (ReservationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to change reservation checkout status: " + e.getMessage(), e);
        }
    }


    @Override
    public boolean delete(Long id) {
        try {
            Reservation reservation = findById(id);
            if (reservation == null) {
                throw new NotFoundException("Failed to find reservation with id " + id);
            }
            Optional<Camp> camp = campRepository.findById(reservation.getCampId());
            if (camp.isEmpty()) {
                throw new NotFoundException("Failed to find camp with id " + reservation.getCampId());
            }
            int reservedRooms = reservation.getNumberOfSpots();
            camp.get().setStock(camp.get().getStock() + reservedRooms);
            campRepository.update(camp.get());
            return reservationRepository.delete(id) == 1;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete reservation: " + e.getMessage(), e);
        }
    }

    private void validateReservation(Reservation reservation) {
        Assert.notNull(reservation, "Reservation must not be null");
        Assert.notNull(reservation.getUserId(), "User ID must not be null");
        Assert.notNull(reservation.getCampId(), "Camp ID must not be null");
        Assert.notNull(reservation.getStartDate(), "Start date must not be null");
        Assert.notNull(reservation.getEndDate(), "End date must not be null");
        Assert.isTrue(reservation.getStartDate().isBefore(reservation.getEndDate()), "Start date must be before end date");
        Assert.isTrue(reservation.getStartDate().isAfter(LocalDate.now().minusDays(1)), "Start date must be in the future");
    }
}

