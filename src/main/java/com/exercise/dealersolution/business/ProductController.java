package com.exercise.dealersolution.business;

import com.exercise.dealersolution.exception.ProdutoNaoEncontradoException;
import com.exercise.dealersolution.repository.Product;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dealer")
public class ProductController {

  private Product product;

  public ProductController(Product product) {
    this.product = product;
  }

  @GetMapping("/models")
  public Map<Integer, String[]> retrieveAll() {
    return product.todos();
  }

  @GetMapping("/models/available")
  public List<List<String>> getAll() {
    ArrayList<List<String>> disponiveis = new ArrayList<>();
    ArrayList<String> item = new ArrayList<>();
    final Map<Integer, String[]> availableProducts = product.getAvailableProducts();

    availableProducts.forEach((integer, strings) -> {
      item.add(integer.toString());
      for (String string : strings) {
        item.add(string);
      }
      disponiveis.add(item);
    });

    return disponiveis;
  }

  @GetMapping("/models/{id}")
  public LocalDate retrieveDeadline(String id) {
    final Map<Integer, String[]> integerMap = product.retrieveUnavailable();

    final String[] strings = integerMap.get(new Integer(id));

    if (strings.length == 0) {
      throw new ProdutoNaoEncontradoException();
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    return LocalDate.parse(strings[4], formatter);
  }

  @PostMapping("/models/new/{id}")
  public void addNewProductModel(@PathVariable Integer productModelId, @RequestBody String s) {
    final Map<Integer, String[]> map = product.todos();
    final String[] strings = s.split(",");
    map.put(productModelId, strings);
    product.save(strings);
  }

  @PostMapping("/models")
  public void apagarItens(@RequestBody String s) {
    for (String str : s.split(",")) {
      product.remove(str);
    }
  }

  @PostMapping("/models/{produto}")
  public void delete(@PathVariable String produto) {
    product.remove(produto);
  }

  @PatchMapping("/model/{id}")
  public void updateProduct(@PathVariable Integer productModelId, @RequestBody String product) {
    final Map<Integer, String[]> map = this.product.todos();
    final String[] strings = product.split(",");
    map.put(productModelId, strings);
    this.product.updateProducts(map);
  }

  @PutMapping("/model/{id}/{price}")
  public void updateProductPrice(@PathVariable Integer productModelId, @PathVariable Double price) {
    String[] p = this.product.getProduct(productModelId);
    p[3] = price.toString();
    Map<Integer, String[]> newValues = new HashMap<>();
    newValues.put(productModelId, p);
    this.product.updateProducts(newValues);
  }
}
