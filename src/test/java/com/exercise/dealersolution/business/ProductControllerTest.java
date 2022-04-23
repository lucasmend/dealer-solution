package com.exercise.dealersolution.business;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Test
    void shouldGetAllProducts() throws ParseException {
        final Collection<ProductModel> expected = Arrays.asList(
                new ProductModel(1L, "SUV", ProductStatus.IN_TRANSPORT, BigDecimal.valueOf(120000L), BigInteger.valueOf(100), formatter.parse("31/12/2022")),
                new ProductModel(2L, "Sedan", ProductStatus.IN_TRANSPORT, BigDecimal.valueOf(100000L), BigInteger.valueOf(100), formatter.parse("20/11/2022")),
                new ProductModel(3L, "Hatch1", ProductStatus.OUTDATED, BigDecimal.valueOf(40000L), BigInteger.valueOf(100), formatter.parse("31/12/2099"))
        );
        given(productService.findAllProducts()).willReturn(expected);

        final Collection<ProductModel> actual = productController.retrieveAll();
        assertEquals(expected, actual);
    }

    @Test
    void shouldGetAllAvailableProducts() throws ParseException {
        final Collection<ProductModel> expected = Arrays.asList(
                new ProductModel(1L, "SUV", ProductStatus.AVAILABLE, BigDecimal.valueOf(120000L), BigInteger.valueOf(100), formatter.parse("31/12/2022")),
                new ProductModel(2L, "Sedan", ProductStatus.AVAILABLE, BigDecimal.valueOf(100000L), BigInteger.valueOf(100), formatter.parse("20/11/2022"))
        );
        given(productService.retrieveAvailableProducts()).willReturn(expected);

        final Collection<ProductModel> availableProducts = productController.retrieveAvailable();

        assertEquals(expected, availableProducts);
    }

    @Test
    void shouldRetrieveDeadline() throws ParseException {
        LocalDate expectedDeadline = LocalDate.of(2022, Month.DECEMBER, 31);
        given(productService.getProductDeadline(any())).willReturn(expectedDeadline);
        final LocalDate retrieveDeadline = productController.getProductDeadline(1L);
        assertEquals(expectedDeadline, retrieveDeadline);
    }

    @Test
    void shouldAddNewProductModel() throws ParseException {
        ProductModel newProduct = new ProductModel(2L, "SUV", ProductStatus.IN_TRANSPORT, BigDecimal.valueOf(120000L), BigInteger.valueOf(100), formatter.parse("31/12/2022"));
        productController.addNewProductModel(newProduct);
        verify(productService).addProduct(newProduct);
    }

    @Test
    void shouldDeleteAllProducts() {
        productController.deleteMany("");
        verify(productService).deleteProducts(emptyList());
    }

    @Test
    void shouldDeleteManyProducts() {
        productController.deleteMany("1,2,3");
        verify(productService).deleteProducts(Arrays.asList(1L, 2L, 3L));
    }

    @Test
    void shouldDeleteSpecificProduct() {
        productController.delete(1L);
        verify(productService).deleteProduct(1L);
    }

    @Test
    void shouldUpdateProduct() throws ParseException {
        ProductModel product = new ProductModel(1L, "SUV", ProductStatus.AVAILABLE, BigDecimal.valueOf(140000L), BigInteger.valueOf(100), formatter.parse("31/12/2022"));
        productController.updateProduct(1L, product);
        verify(productService).updateProduct(1L, product);
    }

    @Test
    void shouldUpdatePriceProduct() {
        productController.updateProductPrice(1L, BigDecimal.valueOf(140000L));
        verify(productService).updatePrice(1L, BigDecimal.valueOf(140000L));
    }

    @Test
    void shouldRetrieveSorted() {
        productController.retrieveAvailableSorted();
        verify(productService).findAvailableSorted();
    }
}