package com.upstream.py.ex.dto.request;

import com.upstream.py.ex.config.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class TransactionRequest {
    private PaymentType paymentType;
    private List<OrderRequest> orderList;
}
