package com.agrihelp.service;

import com.agrihelp.model.Expense;
import com.agrihelp.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    // Add new expense (auto-assign date if not provided)
    public Expense addExpense(Expense expense) {
        if (expense.getDate() == null) {
            expense.setDate(LocalDate.now());
        }
        return expenseRepository.save(expense);
    }

    // Get all expenses
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    // Get expenses by type (case-insensitive)
    public List<Expense> getExpensesByType(String type) {
        return expenseRepository.findByTypeIgnoreCase(type);
    }
}
