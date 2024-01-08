package com.scdbackend.service;

import com.scdbackend.data.model.Product;
import com.scdbackend.data.model.ProductCategory;
import com.scdbackend.data.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final ProductCategoryService productCategoryService;

    @Autowired
    public ProductService(ProductRepository repository, ProductCategoryService productCategoryService) {
        this.repository = repository;
        this.productCategoryService = productCategoryService;
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public void save(Product product) {
        repository.save(product);
    }

    public void deleteById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }

    public List<Product> findByCategoryId(Long productCategoryId) {
        ProductCategory productCategory = productCategoryService.findById(productCategoryId);
        return repository.findByProductCategory(productCategory);
    }

    public List<Product> findMostPopularProducts(int minPercentage) {
        List<Product> allProducts = findAll();
        return allProducts.stream()
                .filter(product -> product.getPercentage() > minPercentage)
                .sorted(((o1, o2) -> Integer.compare(o2.getCartsNumber(), o1.getCartsNumber())))
                .collect(Collectors.toList());
    }

    public List<Product> findRecommendations(List<Long> productsIds) {
        List<ProductRepository.ProductCartProjection> resultIds =
                repository.findRecommendations(productsIds, productsIds.size()).stream()
                        .filter(pcp -> !productsIds.contains(pcp.getProductId()))
                        .collect(Collectors.toList());

        List<Product> commonProducts = repository.findByIdIn(
                resultIds.stream()
                        .map(ProductRepository.ProductCartProjection::getProductId)
                        .collect(Collectors.toList())
        );

        long distinctCartsCount = resultIds.stream()
                .mapToLong(ProductRepository.ProductCartProjection::getCartId)
                .distinct()
                .count();

        return commonProducts.stream()
                .peek(product -> {

                    long productCount = resultIds.stream()
                            .filter(pcp -> pcp.getProductId().longValue() == product.getId().longValue())
                            .count();
                    product.setCommonCartsNumber((int) productCount);

                    int percentage = (int) (productCount * 100 / distinctCartsCount);
                    product.setCommonPercentage(percentage);

                })
                .sorted((o1, o2) -> Integer.compare(o2.getCommonCartsNumber(), o1.getCommonCartsNumber()))
                .collect(Collectors.toList());
    }

}
