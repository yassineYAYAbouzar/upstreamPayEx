package com.upstream.py.ex.dto.response;

import com.upstream.py.ex.config.PaymentType;
import com.upstream.py.ex.config.TransactionState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private UUID uuid;
    private BigDecimal total;
    private PaymentType paymentType;
    private TransactionState state;
    private List<OrderResponse> orderList;
}
