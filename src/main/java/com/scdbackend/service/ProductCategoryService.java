package com.scdbackend.service;

import com.scdbackend.data.model.ProductCategory;
import com.scdbackend.data.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService {

    private final ProductCategoryRepository repository;

    @Autowired
    public ProductCategoryService(ProductCategoryRepository repository) {
        this.repository = repository;
    }

    public ProductCategory findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NullPointerException("Product category not found"));
    }

    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    public void save(ProductCategory category) {
        repository.save(category);
    }

    public void deleteById(Long id) {
        if( repository.existsById(id)){
            repository.deleteById(id);
        }
    }
}
