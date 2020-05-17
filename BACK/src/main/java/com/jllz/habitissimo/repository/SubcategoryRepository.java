package com.jllz.habitissimo.repository;

import com.jllz.habitissimo.model.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

    @Query(value = "SELECT * FROM subcategory s WHERE s.category_id = ?1", nativeQuery = true)
    List<Subcategory> getSubcategoriesById(String category_id);
}
