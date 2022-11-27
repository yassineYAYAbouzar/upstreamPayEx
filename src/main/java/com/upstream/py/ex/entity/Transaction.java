package comupstream.py.entity;

import comupstream.py.config.PaymentMode;
import comupstream.py.config.TransactionState;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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
    private PaymentMode paymentType;
    private TransactionState state;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Order> orderList;

}
