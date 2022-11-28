package com.upstream.py.ex.controller;

import com.upstream.py.ex.dto.request.TransactionRequest;
import com.upstream.py.ex.dto.response.TransactionResponse;
import com.upstream.py.ex.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@RequiredArgsConstructor
@RestController
@RequestMapping("api/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    /**
     * return all transactions
     * @return
     */
    @GetMapping
    public List<TransactionResponse> transactions(){
        return transactionService.fetchAll();
    }

    /**
     * return transaction by uuid
     * @param uuid
     * @return
     */
    @GetMapping("/{uuid}")
    public ResponseEntity<?> getTransaction(@PathVariable UUID uuid){
        return ResponseEntity.ok().body(transactionService.findTransactionByUuid(uuid));
    }

    /**
     * Add new transaction
     * @param transactionRequest
     * @return
     */


    @PostMapping("/save")
    public ResponseEntity<TransactionResponse> addNewTransaction(@RequestBody  TransactionRequest transactionRequest){
        TransactionResponse transactionResponse = transactionService.saveTransaction(transactionRequest);
        return ResponseEntity.accepted().body(transactionResponse);
    }
    /**
     * Update State of transaction
     * @param uuid
     * @return
     */
    @PutMapping("/edit/state/{uuid}")
    public ResponseEntity<TransactionResponse> editTransactionState(@PathVariable UUID uuid){
        TransactionResponse transactionResponse = transactionService.updateTransactionState(uuid).get();
        return  ResponseEntity.accepted().body(transactionResponse);
    }
    /**
     * Delete a transaction by uuid
     * @param uuid
     * @return
     */
    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<?> deleteTransaction(@PathVariable UUID uuid){
         transactionService.deleteTransaction(uuid);
        return ResponseEntity.noContent().build();
    }
}
