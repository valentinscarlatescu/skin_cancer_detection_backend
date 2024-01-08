package com.scdbackend.controller;

import com.scdbackend.data.model.Product;
import com.scdbackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/products")
    public List<Product> findAll() {
        return service.findAll();
    }

    @PostMapping("/products")
    public void save(@RequestBody Product product) {
        service.save(product);
    }

    @DeleteMapping("/products/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

    @GetMapping("/products/byCategory")
    public List<Product> findByCategoryId(@RequestParam("categoryId") Long productCategoryId) {
        return service.findByCategoryId(productCategoryId);
    }

    @GetMapping("/products/mostPopular")
    public List<Product> getMostPopularProducts(@RequestParam("minPercentage") Integer minPercentage) {
        return service.findMostPopularProducts(minPercentage);
    }

    @GetMapping("/products/recommendations")
    public List<Product> getRecommendations(@RequestParam("productsIds") List<Long> productsIds) {
        return service.findRecommendations(productsIds);
    }

}
