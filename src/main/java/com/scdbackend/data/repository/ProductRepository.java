package com.scdbackend.data.repository;

import com.scdbackend.data.model.Product;
import com.scdbackend.data.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByProductCategory(ProductCategory productCategory);

    @Query(value = "select cp.cart_id cartId, cp.product_id productId from cart_product cp " +
            "right join product p on cp.product_id = p.id " +
            "where cp.cart_id in " +
            "(select cart_id from cart_product where product_id in (?1) " +
            "group by cart_id " +
            "having count(distinct product_id) = ?2)", nativeQuery = true)
    List<ProductCartProjection> findRecommendations(List<Long> ids, int length);

    List<Product> findByIdIn(List<Long> ids);


    interface ProductCartProjection {
        Long getCartId();

        Long getProductId();
    }

}
