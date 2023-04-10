package org.Camp.Model.Request;

import lombok.Data;

import java.time.LocalDate;
@Data
public class ReservationRequest {
    private Long id;
    private String userName;
    private String campName;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean checkedOut;
    private int numberOfSpots;
}
