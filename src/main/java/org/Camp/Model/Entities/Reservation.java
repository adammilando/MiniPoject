package org.Camp.Model.Entities;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Reservation {

    private Long id;
    private Long userId;
    private Long campId;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean checkedOut;
    private int numberOfSpots;
}
