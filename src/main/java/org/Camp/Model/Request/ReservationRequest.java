package org.Camp.Model.Request;

import lombok.Data;

import java.time.LocalDate;
@Data
public class ReservationRequest {
    private Long userId;
    private String userName;
    private String UserEmail;
    private Long campId;
    private String campName;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean checkOut;
    private int numberOfSpots;
}
