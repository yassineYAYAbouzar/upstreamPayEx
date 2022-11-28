package com.upstream.py.ex.service;

import com.upstream.py.ex.config.PaymentType;
import com.upstream.py.ex.dto.request.OrderRequest;
import com.upstream.py.ex.dto.request.TransactionRequest;
import com.upstream.py.ex.entity.Transaction;
import com.upstream.py.ex.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
@DataJpaTest
class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;


    private TransactionService underTest;
    private ModelMapper modelMapper;
    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        underTest = new TransactionService(transactionRepository, modelMapper);
    }



    @Test
    void shouldHandelTransaction() {

        OrderRequest orderRequest = OrderRequest
                .builder()
                .productName("paires de gants de ski")
                .quantity(4)
                .price(BigDecimal.valueOf(10))
                .build();
        TransactionRequest transactionRequest =
                TransactionRequest
                .builder()
                .paymentType(PaymentType.CREDIT_CARD)
                .orderList(List.of(orderRequest))
                .build();

        underTest.handelTransaction(transactionRequest);

        ArgumentCaptor<Transaction> transactionArgumentCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionRepository).save(transactionArgumentCaptor.capture());

        assertThat(transactionArgumentCaptor.getValue().getPaymentType()).isEqualTo(transactionRequest.getPaymentType());

        assertThat(transactionArgumentCaptor.getValue().getOrderList().size()).isEqualTo(transactionRequest.getOrderList().size());

    }

}