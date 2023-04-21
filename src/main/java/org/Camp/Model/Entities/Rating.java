package org.Camp.Model.Entities;

import lombok.Data;

@Data
public class Rating {
    private Long id;
    private Long reservationId;
    private int score;
    private String comment;
}
