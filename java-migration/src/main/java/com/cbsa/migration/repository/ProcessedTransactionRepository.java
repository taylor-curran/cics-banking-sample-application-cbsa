package com.cbsa.migration.repository;

import com.cbsa.migration.model.ProcessedTransaction;

public interface ProcessedTransactionRepository {
    
    void save(ProcessedTransaction transaction);
    
    /**
     * Insert a new transaction record
     * 
     * @param transaction the transaction to insert
     */
    void insertTransaction(ProcessedTransaction transaction);
}
