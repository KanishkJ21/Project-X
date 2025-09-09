package com.agrihelp.repository;

import com.agrihelp.model.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends MongoRepository<Expense, String> {
    // Custom query method to find expenses by type (case-insensitive)
    List<Expense> findByTypeIgnoreCase(String type);
}
