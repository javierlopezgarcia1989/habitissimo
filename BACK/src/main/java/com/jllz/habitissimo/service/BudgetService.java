package com.jllz.habitissimo.service;

import com.jllz.habitissimo.model.Budget;
import java.util.List;
import java.util.Optional;

public interface BudgetService {
    List<Budget> findAll();
    Optional<Budget> findById(Long id);
    Budget create(Budget budget);
    Budget update(Budget budget);
    Budget save(Budget budget);
}
