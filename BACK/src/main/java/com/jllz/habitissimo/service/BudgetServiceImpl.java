package com.jllz.habitissimo.service;

import com.jllz.habitissimo.model.Budget;
import com.jllz.habitissimo.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BudgetServiceImpl implements BudgetService{

    @Autowired
    private BudgetRepository repository;

    public List<Budget> findAll(){
        return repository.findAll();
    }

    @Override
    public Optional<Budget> findById(Long id) {
        return repository.findById(id);
    }
    @Override
    public Budget create(Budget budget) {
        return repository.save(budget);
    }

    @Override
    public Budget update(Budget budget) {
        return repository.save(budget);
    }

    @Override
    public Budget save(Budget budget) {
        return repository.save(budget);
    }

}
