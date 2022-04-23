package com.exercise.dealersolution.repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.exercise.dealersolution.business.ProductModel;
import com.exercise.dealersolution.business.ProductStatus;
import org.springframework.stereotype.Component;

@Component
public class Product {
    private final Map<Long, ProductModel> database;

    public Product() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Collection<ProductModel> products = new ArrayList<>();
        products.add(new ProductModel(1L, "SUV", ProductStatus.IN_TRANSPORT, BigDecimal.valueOf(120000L), BigInteger.valueOf(100), formatter.parse("31/12/2022")));
        products.add(new ProductModel(2L, "Sedan", ProductStatus.IN_TRANSPORT, BigDecimal.valueOf(100000L), BigInteger.valueOf(100), formatter.parse("20/11/2022")));
        products.add(new ProductModel(3L, "Hatch1", ProductStatus.OUTDATED, BigDecimal.valueOf(40000L), BigInteger.valueOf(100), formatter.parse("31/12/2099")));
        products.add(new ProductModel(4L, "Hatch2", ProductStatus.IN_TRANSPORT, BigDecimal.valueOf(50000L), BigInteger.ZERO, formatter.parse("10/05/2022")));
        products.add(new ProductModel(5L, "Sport", ProductStatus.AVAILABLE, BigDecimal.valueOf(220000L), BigInteger.valueOf(100), formatter.parse("03/04/2023")));
        products.add(new ProductModel(6L, "Truck", ProductStatus.IN_TRANSPORT, BigDecimal.valueOf(250000L), BigInteger.valueOf(100), formatter.parse("01/02/2024")));
        products.add(new ProductModel(7L, "Eletric", ProductStatus.AVAILABLE, BigDecimal.valueOf(300000L), BigInteger.valueOf(100), formatter.parse("31/06/2025")));
        products.add(new ProductModel(8L, "Autonomos", ProductStatus.OUTDATED, BigDecimal.valueOf(520000L), BigInteger.valueOf(100), formatter.parse("12/12/2022")));

        database = products.stream()
                .collect(Collectors.toMap(ProductModel::getId, Function.identity()));
    }

    public Collection<ProductModel> allProducts() {
        return database.values();
    }

    public void save(ProductModel product) {
        database.put(product.getId(), product);
    }

    public void remove(Long id) {
        database.remove(id);
    }

    public void removeAll(List<Long> ids) {
        ids.forEach(database::remove);
    }

    public ProductModel getProduct(Long id) {
        return database.get(id);
    }
}
