package com.jllz.habitissimo.controller;

import com.jllz.habitissimo.model.Category;
import com.jllz.habitissimo.model.Subcategory;
import com.jllz.habitissimo.service.CategoryService;
import com.jllz.habitissimo.service.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    /**
     * name: getCategories
     *
     * El servicio devolverá un listado de categorías
     *
     * @return ResponseEntity
     */
    @GetMapping(value = "/getCategories")
    public ResponseEntity<List<Category>> getCategories(){

        try {
            List<Category> categories = categoryService.findAll();
            if (categories.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
