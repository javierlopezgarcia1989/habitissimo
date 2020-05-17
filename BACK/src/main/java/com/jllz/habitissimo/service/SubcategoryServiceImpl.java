package com.jllz.habitissimo.service;

import com.jllz.habitissimo.model.Subcategory;
import com.jllz.habitissimo.repository.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {

    @Autowired
    private SubcategoryRepository repository;

    @Override
    public List<Subcategory> findSubcategories(String categoryId) {
        return repository.getSubcategoriesById(categoryId);
    }

}
