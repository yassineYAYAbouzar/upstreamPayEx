package com.upstream.py.ex.app;

import com.upstream.py.ex.config.TransactionState;
import com.upstream.py.ex.dto.request.TransactionRequest;
import com.upstream.py.ex.entity.Transaction;
import com.upstream.py.ex.exception.customExeption.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
@RequiredArgsConstructor
@Component
public class TransactionApp {
    private final ModelMapper modelMapper;

    public Transaction calTotalTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = modelMapper.map(transactionRequest, Transaction.class);
        transaction.setUuid(UUID.randomUUID());
        transaction.getOrderList().stream().filter(Objects::nonNull).forEach(order -> order.setUuid(UUID.randomUUID()));
        transaction.setState(TransactionState.NEW);
        transaction.setTotal(
                transaction.getOrderList()
                        .stream()
                        .map( order -> order.getPrice().multiply(new BigDecimal(order.getQuantity())))
                        .reduce(BigDecimal::add).get());
        return transaction;
    }
    public static Transaction updateTransactionState(Transaction tr) {
        switch (tr.getState()) {
            case NEW -> tr.setState(TransactionState.AUTHORIZED);
            case AUTHORIZED -> tr.setState(TransactionState.CAPTURED);
            default ->  throw new UnauthorizedException("your transaction status in " + tr.getState() + " state you can't update");
        }
        return tr;
    }
}
