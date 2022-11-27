package com.upstream.py.ex.dto.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private UUID uuid;
    private String productName;
    private int quantity;
    private BigDecimal price;
}
