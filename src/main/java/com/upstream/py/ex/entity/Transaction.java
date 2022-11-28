package com.upstream.py.ex.entity;

import com.upstream.py.ex.config.PaymentType;
import com.upstream.py.ex.config.TransactionState;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID uuid;
    private BigDecimal total;
    private PaymentType paymentType;
    private TransactionState state;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Order> orderList;

}
