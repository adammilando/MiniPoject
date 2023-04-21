package org.Camp.Model.Entities;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Camp {
    private Long id;
    private String name;
    private String location;
    private BigDecimal price;
    private int stock;
}
