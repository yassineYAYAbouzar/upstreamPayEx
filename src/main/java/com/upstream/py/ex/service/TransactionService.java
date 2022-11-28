package com.upstream.py.ex.service;

import com.upstream.py.ex.config.TransactionState;
import com.upstream.py.ex.dto.request.TransactionRequest;
import com.upstream.py.ex.dto.response.TransactionResponse;
import com.upstream.py.ex.entity.Order;
import com.upstream.py.ex.entity.Transaction;
import com.upstream.py.ex.exception.customExeption.TransactionNotFoundException;
import com.upstream.py.ex.exception.customExeption.UnauthorizedException;
import com.upstream.py.ex.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;

    public List<TransactionResponse> fetchAll() {
        return modelMapper.map(transactionRepository.findAll(), new TypeToken<List<TransactionResponse>>(){}.getType());
    }
    public Optional<TransactionResponse> findTransactionByUuid(UUID uuid) {
        return getTransactionByUui(uuid)
                .map(tr -> modelMapper.map(tr, TransactionResponse.class));
    }

    public TransactionResponse saveTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = handelTransaction(transactionRequest);
        return modelMapper.map(transaction, TransactionResponse.class);
    }

    public Optional<TransactionResponse> updateTransactionState(UUID uuid) {
        return getTransactionByUui(uuid)
                .map(TransactionService::updateTransactionState)
                .map(transaction ->transactionRepository.save(Objects.requireNonNull(transaction)))
                .map(transaction ->modelMapper.map(transaction , TransactionResponse.class));
    }

    private Optional<Transaction> getTransactionByUui(UUID uuid) {
        Optional<Transaction> byUuid =
                Optional.ofNullable(transactionRepository.findByUuid(uuid)
                        .orElseThrow(() -> new TransactionNotFoundException("Transaction with "+ uuid +"  not found")));
        return byUuid;
    }

    public void deleteTransaction(UUID uuid) {
        getTransactionByUui(uuid).ifPresent(transactionRepository::delete);
    }
    public Transaction handelTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = modelMapper.map(transactionRequest, Transaction.class);
        transaction.setUuid(UUID.randomUUID());
        transaction.getOrderList().stream().filter(Objects::nonNull).forEach(order -> order.setUuid(UUID.randomUUID()));
        transaction.setState(TransactionState.NEW);
        transaction.setTotal(
                transaction.getOrderList()
                        .stream()
                        .map( order -> order.getPrice().multiply(new BigDecimal(order.getQuantity())))
                        .reduce(BigDecimal::add).get());

        return transactionRepository.save(transaction);
    }
    public static Transaction updateTransactionState(Transaction tr) {
        switch (tr.getState()) {
            case NEW -> tr.setState(TransactionState.AUTHORIZED);
            case AUTHORIZED -> tr.setState(TransactionState.CAPTURED);
            default ->  throw new UnauthorizedException("The status of your transaction is " + tr.getState() + " ,you cannot update it.");
        }
        return tr;
    }
}
