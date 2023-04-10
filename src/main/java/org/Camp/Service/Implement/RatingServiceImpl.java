package org.Camp.Service.Implement;

import org.Camp.Exception.NotFoundException;
import org.Camp.Exception.RatingException;
import org.Camp.Exception.ReservationException;
import org.Camp.Model.Entities.Rating;
import org.Camp.Model.Entities.Reservation;
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
    public List<Rating> findAll() {
        try {
            List<Rating> rating = ratingRepository.findAll();
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
            Optional<Reservation> reservation = reservationRepository.findByUserIdAndCampId(rating.getUserId(), rating.getCampId());
            if (reservation.isEmpty()) {
                throw new ReservationException("User has not made a reservation at this camp");
            }

            // Mengambil semua rating yang diberikan oleh pengguna
            Optional<Rating> userRating = ratingRepository.findByUserId(rating.getUserId());

            // Mengambil semua reservasi yang dibuat oleh pengguna untuk camp tertentu
            List<Reservation> userReservations = reservationRepository.findByCampId(rating.getCampId()).stream()
                    .filter(res -> res.getUserId().equals(rating.getUserId()))
                    .toList();

            // Mengecek apakah pengguna telah memberikan rating untuk camp yang sama sebelumnya
            boolean hasPreviousRating = userRating.isPresent() && userRating.get().getCampId().equals(rating.getCampId());

            // Menghitung jumlah reservasi yang telah selesai sebelum tanggal penilaian
            long allowedRatingsCount = userReservations.stream()
                    .filter(res -> res.getEndDate().isBefore(LocalDate.now()))
                    .count();

            if (hasPreviousRating && allowedRatingsCount <= 1) {
                throw new RuntimeException("User has already given a rating for this reservation");
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
            findById(rating.getId());
            validateRating(rating);

            // Validasi apakah user sudah melakukan reservasi di tempat perkemahan yang sesuai
            Optional<Reservation> reservation = reservationRepository.findByUserIdAndCampId(rating.getUserId(), rating.getCampId());
            if (reservation.isEmpty()) {
                throw new ReservationException("User has not made a reservation at this camp");
            }
            return ratingRepository.update(rating) == 1 ? rating : null;
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
            Assert.notNull(rating.getUserId(), "User ID must not be null");
            Assert.notNull(rating.getCampId(), "Camp ID must not be null");
            Assert.isTrue(rating.getScore() >= 1 && rating.getScore() <= 5, "Score must be between 1 and 5");
            Assert.isTrue(rating.getComment() != null && !rating.getComment().isEmpty(), "Comment must not be null or empty");
        } catch (IllegalArgumentException e) {
            throw new RatingException(e.getMessage());
        }
    }
}
