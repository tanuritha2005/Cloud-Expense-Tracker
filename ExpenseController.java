package com.example.expense.controller;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.example.expense.model.Expense;
import com.example.expense.service.ExpenseService;
@RestController
@RequestMapping("/api/expenses")
@CrossOrigin("*")
public class ExpenseController{
 private final ExpenseService service;
 public ExpenseController(ExpenseService service){this.service=service;}
 @GetMapping
 public List<Expense> all(){return service.getAll();}
 @PostMapping
 public Expense save(@RequestBody Expense e){return service.save(e);}
 @DeleteMapping("/{id}")
 public void delete(@PathVariable Long id){service.delete(id);}
}