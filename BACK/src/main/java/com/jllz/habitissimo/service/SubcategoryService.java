package com.jllz.habitissimo.service;

import com.jllz.habitissimo.model.Subcategory;
import java.util.List;

public interface SubcategoryService {
    List<Subcategory> findSubcategories(String category_id);
}
