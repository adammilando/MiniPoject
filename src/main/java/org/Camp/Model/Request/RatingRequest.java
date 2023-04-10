package org.Camp.Model.Request;

import lombok.Data;

@Data
public class RatingRequest {

    private Long id;
    private String userName;
    private String campName;
    private int score;
    private String comment;
}
