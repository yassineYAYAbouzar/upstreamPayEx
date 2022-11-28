package com.upstream.py.ex.dto.request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private String productName;
    private int quantity;
    private BigDecimal price;
}
