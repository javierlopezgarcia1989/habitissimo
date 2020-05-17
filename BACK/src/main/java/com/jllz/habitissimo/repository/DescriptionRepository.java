package com.jllz.habitissimo.repository;

import com.jllz.habitissimo.model.Description;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface DescriptionRepository extends JpaRepository<Description, Long> {

    @Query(value = "SELECT d.subcategory FROM description d WHERE s.category_id = ?1 LIMIT 1", nativeQuery = true)
    String getCategoryDescription(String description);
}
