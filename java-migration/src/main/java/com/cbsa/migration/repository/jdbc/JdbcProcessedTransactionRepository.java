package com.cbsa.migration.repository.jdbc;

import com.cbsa.migration.model.ProcessedTransaction;
import com.cbsa.migration.repository.ProcessedTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;

@Repository
public class JdbcProcessedTransactionRepository implements ProcessedTransactionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(ProcessedTransaction transaction) {
        String sql = "INSERT INTO PROCTRAN (PROCTRAN_EYECATCHER, PROCTRAN_SORTCODE, PROCTRAN_NUMBER, " +
                    "PROCTRAN_DATE, PROCTRAN_TIME, PROCTRAN_REF, PROCTRAN_TYPE, PROCTRAN_DESC, PROCTRAN_AMOUNT) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        jdbcTemplate.update(sql,
            transaction.getEyecatcher(),
            transaction.getSortCode(),
            transaction.getAccountNumber(),
            Date.valueOf(transaction.getDate()),
            Time.valueOf(transaction.getTime()),
            transaction.getReference(),
            transaction.getType(),
            transaction.getDescription(),
            transaction.getAmount()
        );
    }

    @Override
    public void insertTransaction(ProcessedTransaction transaction) {
        save(transaction);
    }
}
