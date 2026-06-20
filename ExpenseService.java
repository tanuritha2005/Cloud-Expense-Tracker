package com.example.expense.service;
import java.util.List;
import org.springframework.stereotype.Service;
import com.example.expense.model.Expense;
import com.example.expense.repository.ExpenseRepository;
@Service
public class ExpenseService{
 private final ExpenseRepository repo;
 public ExpenseService(ExpenseRepository repo){this.repo=repo;}
 public List<Expense> getAll(){return repo.findAll();}
 public Expense save(Expense e){return repo.save(e);}
 public void delete(Long id){repo.deleteById(id);}
}