package com.upstream.py.ex.service;

import com.upstream.py.ex.app.TransactionApp;
import com.upstream.py.ex.config.PaymentType;
import com.upstream.py.ex.dto.request.OrderRequest;
import com.upstream.py.ex.dto.request.TransactionRequest;
import com.upstream.py.ex.entity.Transaction;
import com.upstream.py.ex.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
@DataJpaTest
@RunWith(SpringRunner.class)

class TransactionServiceTest {
    @Mock
    @Autowired
    private TransactionRepository transactionRepository;


    private TransactionService underTest;
    private ModelMapper modelMapper;
    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        underTest = new TransactionService(transactionRepository, modelMapper);
    }



    @Test
    void saveTransaction() {

        OrderRequest orderRequest = OrderRequest
                .builder()
                .productName("test prod")
                .quantity(3)
                .price(BigDecimal.valueOf(5))
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


        TransactionRequest map = modelMapper.map(transactionArgumentCaptor.getValue(), TransactionRequest.class);
        assertThat(map.getOrderList()).isEqualTo(transactionRequest.getOrderList());
        assertThat(map.getPaymentType()).isEqualTo(transactionRequest.getPaymentType());
    }

}