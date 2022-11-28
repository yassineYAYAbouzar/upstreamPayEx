package com.upstream.py.ex.service;
import com.upstream.py.ex.app.TransactionApp;
import com.upstream.py.ex.config.TransactionState;
import com.upstream.py.ex.dto.request.TransactionRequest;
import com.upstream.py.ex.dto.response.TransactionResponse;
import com.upstream.py.ex.entity.Transaction;
import com.upstream.py.ex.exception.customExeption.TransactionNotFoundException;
import com.upstream.py.ex.exception.customExeption.UnauthorizedException;
import com.upstream.py.ex.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionApp transactionApp;
    private final ModelMapper modelMapper;

    public List<TransactionResponse> fetchAll() {
        return modelMapper.map(transactionRepository.findAll(), new TypeToken<List<TransactionResponse>>(){}.getType());
    }
    public Optional<TransactionResponse> findTransactionByUuid(UUID uuid) {
        return getTransactionByUui(uuid)
                .map(tr -> modelMapper.map(tr, TransactionResponse.class));
    }

    public TransactionResponse saveTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = transactionApp.calTotalTransaction(transactionRequest);
        return modelMapper.map(transactionRepository.save(transaction) , TransactionResponse.class);
    }
    public Optional<TransactionResponse> updateTransaction(UUID uuid, TransactionRequest transactionRequest) {
        Transaction transaction = transactionApp.calTotalTransaction(transactionRequest);
        return getTransactionByUui(uuid)
                .map(tr -> {
                    if (tr.getState() != TransactionState.NEW){
                        throw new UnauthorizedException("The status of your transaction is " + tr.getState() + " ,you cannot update it.");
                    }
                    tr.setPaymentType(transaction.getPaymentType());
                    tr.setOrderList(transaction.getOrderList());
                    tr.setTotal(transaction.getTotal());
                   return transactionRepository.save(Objects.requireNonNull(tr));
                }).map(tr -> modelMapper.map(tr, TransactionResponse.class));
    }

    public Optional<TransactionResponse> updateTransactionState(UUID uuid) {
        return getTransactionByUui(uuid)
                .map(TransactionApp::updateTransactionState)
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

}
