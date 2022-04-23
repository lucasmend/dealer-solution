package com.exercise.dealersolution.business;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;

import static java.util.function.Predicate.not;

@RestController
@RequestMapping("/api/dealer")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/models")
    public Collection<ProductModel> retrieveAll() {
        return service.findAllProducts();
    }

    @GetMapping("/models/available")
    public Collection<ProductModel> retrieveAvailable() {
        return service.retrieveAvailableProducts();
    }

    @GetMapping("/models/deadline/{id}")
    public LocalDate getProductDeadline(@PathVariable Long id) {
        return service.getProductDeadline(id);
    }

    @PostMapping("/models/new")
    public void addNewProductModel(@RequestBody ProductModel newProduct) {
        service.addProduct(newProduct);
    }

    @DeleteMapping("/models")
    public void deleteMany(@RequestBody String ids) {
        List<Long> productIds = Arrays.stream(ids.split(","))
                .filter(not(String::isEmpty))
                .map(Long::parseLong).collect(Collectors.toList());
        service.deleteProducts(productIds);
    }

    @DeleteMapping("/models/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteProduct(id);
    }

    @PutMapping("/model/{id}")
    public void updateProduct(@PathVariable Long id, @RequestBody ProductModel product) {
        service.updateProduct(id, product);
    }

    @PatchMapping("/model/{id}/{price}")
    public void updateProductPrice(@PathVariable Long id, @PathVariable BigDecimal price) {
        service.updatePrice(id, price);
    }

    @GetMapping("/models/sorted")
    public Collection<ProductModel> retrieveAvailableSorted() {
        return service.findAvailableSorted();
    }
}
