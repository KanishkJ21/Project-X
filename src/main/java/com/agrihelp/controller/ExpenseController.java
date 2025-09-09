package com.agrihelp.controller;

import com.agrihelp.model.Expense;
import com.agrihelp.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    // Add expense with JSON body
    @PostMapping("/add")
    public Expense addExpense(@RequestBody Expense expense) {
        return expenseService.addExpense(expense);
    }

    // Get all expenses
    @GetMapping("/all")
    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    // Get expenses by type
    @GetMapping("/type/{type}")
    public List<Expense> getExpensesByType(@PathVariable String type) {
        return expenseService.getExpensesByType(type);
    }
}
