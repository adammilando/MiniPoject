package org.Camp.Controller;

import org.Camp.Model.Entities.Reservation;
import org.Camp.Model.Request.ReservationRequest;
import org.Camp.Model.Response.CommonResponse;
import org.Camp.Model.Response.SuccessResponse;
import org.Camp.Service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/reservations")
public class ReservationApiController {

    private final ReservationService reservationService;

    public ReservationApiController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity getAllReservations() {
        List<ReservationRequest> reservations = reservationService.findAll();
        CommonResponse commonResponse = new SuccessResponse<>("Success Get ALl Reservation",reservations);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping("/find-reservation")
    public ResponseEntity getReservationByUserNameAndCamp(@RequestParam String userName, @RequestParam String campName, @RequestParam boolean checkedOut){
        List<ReservationRequest> reservationRequest= reservationService.findByUserNameAndCampName(userName, campName, checkedOut);
        CommonResponse commonResponse = new SuccessResponse<>("Success Find Reservation", reservationRequest);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity getReservationById(@PathVariable("id") Long id) {
        Reservation reservation = reservationService.findById(id);
        CommonResponse commonResponse = new SuccessResponse<>("Success Find Reservation with id: "+ id, reservation);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PostMapping
    public ResponseEntity createReservation(@RequestBody Reservation reservation) {
       Reservation createReservation = reservationService.save(reservation);
       CommonResponse commonResponse = new SuccessResponse<>("Success Creating New Reservation",createReservation);
       return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateReservation(@PathVariable("id") Long id, @RequestBody Reservation reservation) {
        reservation.setId(id);
        Reservation updatedReservation = reservationService.update(reservation);
        CommonResponse commonResponse = new SuccessResponse<>("Reservation with id: "+id+" Has Been Updated",updatedReservation);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PutMapping("/status-checkout")
    public ResponseEntity updateCheckOutStatus(@RequestParam("id") Long id, @RequestParam boolean checkOut){
        Reservation updateStatus = reservationService.changeCheckoutStatus(id,checkOut);
        CommonResponse commonResponse = new SuccessResponse<>("Success Update Status CheckOut", updateStatus);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@PathVariable("id") Long id) {
        Reservation reservation = reservationService.findById(id);
        reservationService.delete(id);
        CommonResponse commonResponse = new SuccessResponse<>("Success Deleting Reservation",reservation);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
}
