package com.scdbackend.controller;

import com.scdbackend.data.model.ProductCategory;
import com.scdbackend.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductCategoryController {

    private final ProductCategoryService service;

    @Autowired
    public ProductCategoryController(ProductCategoryService service){
        this.service = service;
    }

    @GetMapping("/productCategories")
    public List<ProductCategory> findAll() {
        return service.findAll();
    }

    @PostMapping("/productCategories")
    public void save(@RequestBody ProductCategory category){
        service.save(category);
    }

    @DeleteMapping("/productCategories/{id}")
    public void deleteById(@PathVariable("id") Long id){
        service.deleteById(id);
    }

}
