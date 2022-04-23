package com.exercise.dealersolution.business;

import com.exercise.dealersolution.exception.ProductNotFoundException;
import com.exercise.dealersolution.repository.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class ProductService {

    private final Product productRepository;

    public ProductService(Product productRepository) {
        this.productRepository = productRepository;
    }

    public Collection<ProductModel> findAllProducts() {
        return productRepository.allProducts();
    }

    public Collection<ProductModel> findAvailableSorted() {
        return retrieveAvailableProducts().stream()
                .sorted(Comparator.comparing(ProductModel::getDeadline)
                        .thenComparing(ProductModel::getQuantity)
                        .thenComparing(Comparator.comparing(ProductModel::getPrice).reversed()))
                .collect(Collectors.toList());
    }

    public void addProduct(ProductModel product) {
        Long lastId = this.findLastId();
        ProductModel productToAdd = product.cloneWithNewId(lastId + 1);
        productRepository.save(productToAdd);
    }

    public LocalDate getProductDeadline(Long id) {
        Map<Long, ProductModel> unavailableProducts = this.retrieveUnavailable().stream()
                .collect(Collectors.toMap(ProductModel::getId, Function.identity()));
        ProductModel productModel = unavailableProducts.getOrDefault(id, ProductModel.EMPTY);

        if (ProductModel.EMPTY.equals(productModel)) {
            throw new ProductNotFoundException();
        }

        return productModel.getDeadline()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public void deleteProduct(Long id) {
        productRepository.remove(id);
    }

    public void deleteProducts(List<Long> ids) {
        List<Long> idsToDelete = ids.isEmpty() ? allProductIds() : ids;
        productRepository.removeAll(idsToDelete);
    }

    private List<Long> allProductIds() {
        return productRepository.allProducts().stream()
                .map(ProductModel::getId)
                .collect(Collectors.toList());
    }

    public Collection<ProductModel> retrieveUnavailable() {
        return fetchAndFilterProducts(ProductModel::isUnavailable);
    }

    public Collection<ProductModel> retrieveAvailableProducts() {
        return fetchAndFilterProducts(ProductModel::isAvailable);
    }

    private Collection<ProductModel> fetchAndFilterProducts(Predicate<ProductModel> productFilter) {
        return productRepository.allProducts().stream()
                .filter(productFilter)
                .collect(Collectors.toList());
    }

    private Long findLastId() {
        return productRepository.allProducts().stream()
                .map(ProductModel::getId)
                .max(Long::compareTo)
                .orElse(0L);
    }

    public void updateProduct(Long id, ProductModel productModel) {
        ProductModel toUpdate = productRepository.getProduct(id);
        ProductModel updatedModel = toUpdate.updateProduct(productModel);
        productRepository.save(updatedModel);
    }

    public void updatePrice(Long id, BigDecimal price) {
        ProductModel toUpdate = productRepository.getProduct(id);
        ProductModel updatedModel = toUpdate.cloneWithNewPrice(price);
        productRepository.save(updatedModel);
    }
}
