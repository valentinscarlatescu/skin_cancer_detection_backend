package com.scdbackend.data.model;

import com.scdbackend.enums.QuantityType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_category_id", referencedColumnName = "id")
    private ProductCategory productCategory;
    private String name;
    private Integer averagePrice;
    @JsonIgnore
    @ManyToMany(mappedBy = "cartProducts")
    private List<Cart> carts;
    @Enumerated(EnumType.STRING)
    private QuantityType quantityType;
    private String imagePath;

    @Formula("(SELECT COUNT(cp.product_id) FROM cart_product cp WHERE cp.product_id = id)")
    private int cartsNumber;

    @Formula("(SELECT (COUNT(cp.product_id) * 100 / (SELECT COUNT(*) FROM cart)) FROM cart_product cp WHERE cp.product_id = id)")
    private float percentage;

    @Transient
    private int commonCartsNumber;

    @Transient
    private int commonPercentage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> cartSet) {
        this.carts = cartSet;
    }

    public Integer getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Integer averagePrice) {
        this.averagePrice = averagePrice;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public QuantityType getQuantityType() {
        return quantityType;
    }

    public void setQuantityType(QuantityType quantityType) {
        this.quantityType = quantityType;
    }

    public int getCartsNumber() {
        return cartsNumber;
    }

    public void setCartsNumber(int cartsNumber) {
        this.cartsNumber = cartsNumber;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public int getCommonCartsNumber() {
        return commonCartsNumber;
    }

    public void setCommonCartsNumber(int commonCartsNumber) {
        this.commonCartsNumber = commonCartsNumber;
    }

    public int getCommonPercentage() {
        return commonPercentage;
    }

    public void setCommonPercentage(int commonPercentage) {
        this.commonPercentage = commonPercentage;
    }
}
