package org.Camp.Model.Entities;

import lombok.Data;

@Data
public class Rating {
    private Long id;
    private Long userId;
    private Long campId;
    private int score;
    private String comment;
}
