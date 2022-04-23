package com.exercise.dealersolution.business;

import com.exercise.dealersolution.exception.ProductNotFoundException;
import com.exercise.dealersolution.repository.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    // Integration test

    private ProductService service;

    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    @BeforeEach
    void setUp() throws ParseException {
        Product product = new Product();
        service = new ProductService(product);
    }

    @Test
    void shouldFindAllProducts() throws ParseException {
        Collection<ProductModel> actual = new ArrayList<>(service.findAllProducts());
        assertEquals(Arrays.asList(
                new ProductModel(1L, "SUV", ProductStatus.IN_TRANSPORT, BigDecimal.valueOf(120000L), BigInteger.valueOf(100), formatter.parse("31/12/2022")),
                new ProductModel(2L, "Sedan", ProductStatus.IN_TRANSPORT, BigDecimal.valueOf(100000L), BigInteger.valueOf(100), formatter.parse("20/11/2022")),
                new ProductModel(3L, "Hatch1", ProductStatus.OUTDATED, BigDecimal.valueOf(40000L), BigInteger.valueOf(100), formatter.parse("31/12/2099")),
                new ProductModel(4L, "Hatch2", ProductStatus.IN_TRANSPORT, BigDecimal.valueOf(50000L), BigInteger.ZERO, formatter.parse("10/05/2022")),
                new ProductModel(5L, "Sport", ProductStatus.AVAILABLE, BigDecimal.valueOf(220000L), BigInteger.valueOf(100), formatter.parse("03/04/2023")),
                new ProductModel(6L, "Truck", ProductStatus.IN_TRANSPORT, BigDecimal.valueOf(250000L), BigInteger.valueOf(100), formatter.parse("01/02/2024")),
                new ProductModel(7L, "Eletric", ProductStatus.AVAILABLE, BigDecimal.valueOf(300000L), BigInteger.valueOf(100), formatter.parse("31/06/2025")),
                new ProductModel(8L, "Autonomos", ProductStatus.OUTDATED, BigDecimal.valueOf(520000L), BigInteger.valueOf(100), formatter.parse("12/12/2022"))
        ), actual);
    }

    @Test
    void shouldFindAvailableSorted() throws ParseException {
        Collection<ProductModel> actual = new ArrayList<>(service.findAvailableSorted());
        assertEquals(Arrays.asList(
                new ProductModel(5L, "Sport", ProductStatus.AVAILABLE, BigDecimal.valueOf(220000L), BigInteger.valueOf(100), formatter.parse("03/04/2023")),
                new ProductModel(7L, "Eletric", ProductStatus.AVAILABLE, BigDecimal.valueOf(300000L), BigInteger.valueOf(100), formatter.parse("31/06/2025"))
        ), actual);
    }

    @Test
    void shouldAddProduct() throws ParseException {
        ProductModel newProduct = new ProductModel(0L, "Flying", ProductStatus.AVAILABLE, BigDecimal.valueOf(110000L), BigInteger.valueOf(100), formatter.parse("12/12/2027"));
        service.addProduct(newProduct);
        Collection<ProductModel> actual = new ArrayList<>(service.findAllProducts());
        assertEquals(Arrays.asList(
                new ProductModel(1L, "SUV", ProductStatus.IN_TRANSPORT, BigDecimal.valueOf(120000L), BigInteger.valueOf(100), formatter.parse("31/12/2022")),
                new ProductModel(2L, "Sedan", ProductStatus.IN_TRANSPORT, BigDecimal.valueOf(100000L), BigInteger.valueOf(100), formatter.parse("20/11/2022")),
                new ProductModel(3L, "Hatch1", ProductStatus.OUTDATED, BigDecimal.valueOf(40000L), BigInteger.valueOf(100), formatter.parse("31/12/2099")),
                new ProductModel(4L, "Hatch2", ProductStatus.IN_TRANSPORT, BigDecimal.valueOf(50000L), BigInteger.ZERO, formatter.parse("10/05/2022")),
                new ProductModel(5L, "Sport", ProductStatus.AVAILABLE, BigDecimal.valueOf(220000L), BigInteger.valueOf(100), formatter.parse("03/04/2023")),
                new ProductModel(6L, "Truck", ProductStatus.IN_TRANSPORT, BigDecimal.valueOf(250000L), BigInteger.valueOf(100), formatter.parse("01/02/2024")),
                new ProductModel(7L, "Eletric", ProductStatus.AVAILABLE, BigDecimal.valueOf(300000L), BigInteger.valueOf(100), formatter.parse("31/06/2025")),
                new ProductModel(8L, "Autonomos", ProductStatus.OUTDATED, BigDecimal.valueOf(520000L), BigInteger.valueOf(100), formatter.parse("12/12/2022")),
                new ProductModel(9L, "Flying", ProductStatus.AVAILABLE, BigDecimal.valueOf(110000L), BigInteger.valueOf(100), formatter.parse("12/12/2027"))
        ), actual);
    }

    @Test
    void shouldThrowWhenProductAvailable() {
        assertThrows(ProductNotFoundException.class, () -> service.getProductDeadline(1L));
    }

    @Test
    void shouldGetProductDeadline() {
        LocalDate actual = service.getProductDeadline(4L);
        assertEquals(LocalDate.of(2022, Month.MAY, 10), actual);
    }

    @Test
    void shouldDeleteProduct() throws ParseException {
        service.deleteProduct(1L);
        Collection<ProductModel> actual = new ArrayList<>(service.findAllProducts());
        assertEquals(Arrays.asList(
                new ProductModel(2L, "Sedan", ProductStatus.IN_TRANSPORT, BigDecimal.valueOf(100000L), BigInteger.valueOf(100), formatter.parse("20/11/2022")),
                new ProductModel(3L, "Hatch1", ProductStatus.OUTDATED, BigDecimal.valueOf(40000L), BigInteger.valueOf(100), formatter.parse("31/12/2099")),
                new ProductModel(4L, "Hatch2", ProductStatus.IN_TRANSPORT, BigDecimal.valueOf(50000L), BigInteger.ZERO, formatter.parse("10/05/2022")),
                new ProductModel(5L, "Sport", ProductStatus.AVAILABLE, BigDecimal.valueOf(220000L), BigInteger.valueOf(100), formatter.parse("03/04/2023")),
                new ProductModel(6L, "Truck", ProductStatus.IN_TRANSPORT, BigDecimal.valueOf(250000L), BigInteger.valueOf(100), formatter.parse("01/02/2024")),
                new ProductModel(7L, "Eletric", ProductStatus.AVAILABLE, BigDecimal.valueOf(300000L), BigInteger.valueOf(100), formatter.parse("31/06/2025")),
                new ProductModel(8L, "Autonomos", ProductStatus.OUTDATED, BigDecimal.valueOf(520000L), BigInteger.valueOf(100), formatter.parse("12/12/2022"))
        ), actual);
    }

    @Test
    void shouldDeleteManyProducts() throws ParseException {
        service.deleteProducts(Arrays.asList(1L, 2L));
        Collection<ProductModel> actual = new ArrayList<>(service.findAllProducts());
        assertEquals(Arrays.asList(
                new ProductModel(3L, "Hatch1", ProductStatus.OUTDATED, BigDecimal.valueOf(40000L), BigInteger.valueOf(100), formatter.parse("31/12/2099")),
                new ProductModel(4L, "Hatch2", ProductStatus.IN_TRANSPORT, BigDecimal.valueOf(50000L), BigInteger.ZERO, formatter.parse("10/05/2022")),
                new ProductModel(5L, "Sport", ProductStatus.AVAILABLE, BigDecimal.valueOf(220000L), BigInteger.valueOf(100), formatter.parse("03/04/2023")),
                new ProductModel(6L, "Truck", ProductStatus.IN_TRANSPORT, BigDecimal.valueOf(250000L), BigInteger.valueOf(100), formatter.parse("01/02/2024")),
                new ProductModel(7L, "Eletric", ProductStatus.AVAILABLE, BigDecimal.valueOf(300000L), BigInteger.valueOf(100), formatter.parse("31/06/2025")),
                new ProductModel(8L, "Autonomos", ProductStatus.OUTDATED, BigDecimal.valueOf(520000L), BigInteger.valueOf(100), formatter.parse("12/12/2022"))
        ), actual);
    }

    @Test
    void shouldDeleteAllProducts() {
        service.deleteProducts(emptyList());
        Collection<ProductModel> actual = new ArrayList<>(service.findAllProducts());
        assertEquals(emptyList(), actual);
    }

    @Test
    void shouldRetrieveUnavailable() throws ParseException {
        Collection<ProductModel> actual = service.retrieveUnavailable();
        assertEquals(Collections.singletonList(
                new ProductModel(4L, "Hatch2", ProductStatus.IN_TRANSPORT, BigDecimal.valueOf(50000L), BigInteger.ZERO, formatter.parse("10/05/2022"))
        ), actual);
    }

    @Test
    void retrieveAvailableProducts() throws ParseException {
        Collection<ProductModel> actual = service.retrieveAvailableProducts();
        assertEquals(Arrays.asList(
                new ProductModel(5L, "Sport", ProductStatus.AVAILABLE, BigDecimal.valueOf(220000L), BigInteger.valueOf(100), formatter.parse("03/04/2023")),
                new ProductModel(7L, "Eletric", ProductStatus.AVAILABLE, BigDecimal.valueOf(300000L), BigInteger.valueOf(100), formatter.parse("31/06/2025"))
        ), actual);
    }

    @Test
    void shouldUpdateProduct() throws ParseException {
        ProductModel updatedProduct = new ProductModel(0L, "Sport", ProductStatus.AVAILABLE, BigDecimal.valueOf(220000L), BigInteger.ZERO, formatter.parse("03/04/2025"));
        service.updateProduct(5L, updatedProduct);

        Collection<ProductModel> actual = new ArrayList<>(service.findAllProducts());
        assertEquals(Arrays.asList(
                new ProductModel(1L, "SUV", ProductStatus.IN_TRANSPORT, BigDecimal.valueOf(120000L), BigInteger.valueOf(100), formatter.parse("31/12/2022")),
                new ProductModel(2L, "Sedan", ProductStatus.IN_TRANSPORT, BigDecimal.valueOf(100000L), BigInteger.valueOf(100), formatter.parse("20/11/2022")),
                new ProductModel(3L, "Hatch1", ProductStatus.OUTDATED, BigDecimal.valueOf(40000L), BigInteger.valueOf(100), formatter.parse("31/12/2099")),
                new ProductModel(4L, "Hatch2", ProductStatus.IN_TRANSPORT, BigDecimal.valueOf(50000L), BigInteger.ZERO, formatter.parse("10/05/2022")),
                new ProductModel(5L, "Sport", ProductStatus.AVAILABLE, BigDecimal.valueOf(220000L), BigInteger.ZERO, formatter.parse("03/04/2025")),
                new ProductModel(6L, "Truck", ProductStatus.IN_TRANSPORT, BigDecimal.valueOf(250000L), BigInteger.valueOf(100), formatter.parse("01/02/2024")),
                new ProductModel(7L, "Eletric", ProductStatus.AVAILABLE, BigDecimal.valueOf(300000L), BigInteger.valueOf(100), formatter.parse("31/06/2025")),
                new ProductModel(8L, "Autonomos", ProductStatus.OUTDATED, BigDecimal.valueOf(520000L), BigInteger.valueOf(100), formatter.parse("12/12/2022"))
        ), actual);
    }

    @Test
    void shouldUpdatePrice() throws ParseException {
        service.updatePrice(5L, BigDecimal.valueOf(250000L));

        Collection<ProductModel> actual = new ArrayList<>(service.findAllProducts());
        assertEquals(Arrays.asList(
                new ProductModel(1L, "SUV", ProductStatus.IN_TRANSPORT, BigDecimal.valueOf(120000L), BigInteger.valueOf(100), formatter.parse("31/12/2022")),
                new ProductModel(2L, "Sedan", ProductStatus.IN_TRANSPORT, BigDecimal.valueOf(100000L), BigInteger.valueOf(100), formatter.parse("20/11/2022")),
                new ProductModel(3L, "Hatch1", ProductStatus.OUTDATED, BigDecimal.valueOf(40000L), BigInteger.valueOf(100), formatter.parse("31/12/2099")),
                new ProductModel(4L, "Hatch2", ProductStatus.IN_TRANSPORT, BigDecimal.valueOf(50000L), BigInteger.ZERO, formatter.parse("10/05/2022")),
                new ProductModel(5L, "Sport", ProductStatus.AVAILABLE, BigDecimal.valueOf(250000L), BigInteger.valueOf(100), formatter.parse("03/04/2023")),
                new ProductModel(6L, "Truck", ProductStatus.IN_TRANSPORT, BigDecimal.valueOf(250000L), BigInteger.valueOf(100), formatter.parse("01/02/2024")),
                new ProductModel(7L, "Eletric", ProductStatus.AVAILABLE, BigDecimal.valueOf(300000L), BigInteger.valueOf(100), formatter.parse("31/06/2025")),
                new ProductModel(8L, "Autonomos", ProductStatus.OUTDATED, BigDecimal.valueOf(520000L), BigInteger.valueOf(100), formatter.parse("12/12/2022"))
        ), actual);
    }
}