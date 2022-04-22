package com.exercise.dealersolution.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import com.exercise.dealersolution.repository.Product;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

  @Mock
  private Product productRepository;

  @InjectMocks
  private ProductController productController;

  @Test
  void shouldGetAllProducts() {
    final Map<Integer, String[]> products = new HashMap<Integer, String[]>()
    {{
      put(1, new String[]{"SUV", "1", "120,000.00", "100", "31/12/2022"});
      put(2, new String[]{"Sedan", "1", "100,000.00", "100", "20/11/2022"});
      put(3, new String[]{"Hatch1", "0", "40.000,00", "100", "31/12/2099"});
    }};
    given(productRepository.todos()).willReturn(products);

    final Map<Integer, String[]> retrievedObjects = productController.retrieveAll();

    assertEquals(products, retrievedObjects);
  }

  @Test
  void shouldGetAllAvailableProducts() {
    List<List<String>> expected = new ArrayList<>();
    expected.add(Arrays.asList("1","SUV", "1", "120,000.00", "100", "31/12/2022"));
    expected.add(Arrays.asList("2","Sedan", "1", "100,000.00", "100", "20/11/2022"));

    final Map<Integer, String[]> products = new HashMap<Integer, String[]>()
    {{
      put(1, new String[]{"SUV", "1", "120,000.00", "100", "31/12/2022"});
      put(2, new String[]{"Sedan", "1", "100,000.00", "100", "20/11/2022"});
    }};
    given(productRepository.getAvailableProducts()).willReturn(products);

    final List<List<String>> availableProducts = productController.getAll();

    assertTrue(!availableProducts.isEmpty());
  }

  @Test
  void shouldRetrieveDeadline() {
    LocalDate expectedDeadline = LocalDate.of(2022, Month.DECEMBER, 31);
    final Map<Integer, String[]> products = new HashMap<Integer, String[]>()
    {{
      put(1, new String[]{"SUV", "1", "120,000.00", "100", "31/12/2022"});
    }};
    given(productRepository.retrieveUnavailable()).willReturn(products);

    final LocalDate retrieveDeadline = productController.retrieveDeadline("1");

    assertEquals(expectedDeadline, retrieveDeadline);
  }

  @Test
  void shouldAddNewProductModel() {
  }

  @Test
  void shouldDeleteAllProducts() {
  }

  @Test
  void shouldDeleteSpecificProduct() {
  }

  @Test
  void shouldUpdateProduct() {
  }

  @Test
  void shouldUpdatePriceProduct() {
  }
}