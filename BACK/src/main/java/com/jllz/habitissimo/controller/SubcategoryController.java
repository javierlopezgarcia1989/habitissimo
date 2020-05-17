package com.jllz.habitissimo.controller;

import com.jllz.habitissimo.model.Subcategory;
import com.jllz.habitissimo.service.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/api/subcategory")
public class SubcategoryController {

    @Autowired
    private SubcategoryService subcategoryService;


    /**
     * name: getSubcategories
     *
     * El servicio devolverá un listado de subcategorías asociadas a la categoría recibida por parametro.
     *
     * @param categoryId
     * @return ResponseEntity
     */
    @GetMapping(value = "/getSubcategories/{categoryId}")
    public ResponseEntity<List<Subcategory>> getSubcategories(@PathVariable("categoryId") String categoryId){

        try {
            List<Subcategory> subcategories = subcategoryService.findSubcategories(categoryId);
            if (subcategories.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(subcategories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
