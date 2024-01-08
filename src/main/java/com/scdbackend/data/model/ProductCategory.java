package com.scdbackend.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.List;

@Entity
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               mappedBy = "productCategory")
    private List<Product> products;
    private String imagePath;

    @Formula("(SELECT COUNT(p.id) FROM product p WHERE p.product_category_id = id)")
    private int productsNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getProductsNumber() {
        return productsNumber;
    }

    public void setProductsNumber(int productsNumber) {
        this.productsNumber = productsNumber;
    }
}
