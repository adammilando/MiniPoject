package org.Camp.Service.Implement;

import org.Camp.Exception.NotFoundException;
import org.Camp.Exception.RatingException;
import org.Camp.Exception.ReservationException;
import org.Camp.Model.Entities.Rating;
import org.Camp.Model.Entities.Reservation;
import org.Camp.Model.Request.RatingRequest;
import org.Camp.Repository.RatingRepository;
import org.Camp.Repository.ReservationRepository;
import org.Camp.Service.RatingService;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final ReservationRepository reservationRepository;

    public RatingServiceImpl(RatingRepository ratingRepository, ReservationRepository reservationRepository) {
        this.ratingRepository = ratingRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<RatingRequest> findAll() {
        try {
            List<RatingRequest> rating = ratingRepository.findAll();
            if (rating.isEmpty()){
                throw new NotFoundException("Database Empty");
            }
            return rating;
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<RatingRequest> findByUserAndCamp(String user, String camp) {
        try {
            List<RatingRequest> ratingRequests = ratingRepository.findByUserAndCamp(user, camp);
            if (ratingRequests.isEmpty()){
                throw new NotFoundException("Cannot Find rating with given user and camp name");
            }
            return ratingRequests;
        }catch (NotFoundException e){
            throw e;
        }catch (Exception e){
            throw new RuntimeException("Failed to find rating: " + e.getMessage());
        }
    }

    @Override
    public Rating findById(Long id) {
        try {
            return ratingRepository.findById(id).orElseThrow(() -> new NotFoundException("Rating With id:" + id + " does  Not Exists"));
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Rating save(Rating rating) {
        try {
            validateRating(rating);

            // Validasi apakah user sudah melakukan reservasi di tempat perkemahan yang sesuai
            Optional<Reservation> reservationOpt = reservationRepository.findById(rating.getReservationId());
            if (!reservationOpt.isPresent()) {
                throw new ReservationException("Invalid reservation ID");
            }

            Reservation reservation = reservationOpt.get();
            if (!reservation.isCheckedOut()) {
                throw new ReservationException("User has not checked out from the camp");
            }

            // Cek apakah rating sudah ada untuk reservasi ini
            Optional<Rating> existingRating = ratingRepository.findByReservationId(reservation.getId());
            if (existingRating.isPresent()) {
                throw new ReservationException("User has already given a rating for this reservation");
            }

            return ratingRepository.save(rating) == 1 ? rating : null;
        } catch (ReservationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save rating: " + e.getMessage(), e);
        }
    }




    @Override
    public Rating update(Rating rating) {
        try {
            Rating existingRating = findById(rating.getId());
            validateRating(rating);

            rating.setReservationId(existingRating.getReservationId());

            existingRating.setScore(rating.getScore());
            existingRating.setComment(rating.getComment());

            return ratingRepository.update(existingRating) == 1 ? existingRating : null;
        } catch (ReservationException e){
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update rating: " + e.getMessage(), e);
        }
    }


    @Override
    public boolean delete(Long id) {
        try {
            findById(id);
            return ratingRepository.delete(id) == 1;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete rating: " + e.getMessage(), e);
        }
    }

    private void validateRating(Rating rating) {
        try {
            Assert.notNull(rating, "Rating must not be null");
            Assert.isTrue(rating.getScore() >= 1 && rating.getScore() <= 5, "Score must be between 1 and 5");
            Assert.isTrue(rating.getComment() != null && !rating.getComment().isEmpty(), "Comment must not be null or empty");
        } catch (IllegalArgumentException e) {
            throw new RatingException(e.getMessage());
        }
    }
}
