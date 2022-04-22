package com.exercise.dealersolution.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.stereotype.Component;

@Component
public class Product {
  Map<Integer, String[]> map_produtos = new HashMap<>();

  public Product() {
    //Description, Status, Price, Quantity, Deadline to receive new products
    this.map_produtos.put(1, new String[]{"SUV", "1", "120,000.00", "100", "31/12/2022"});
    this.map_produtos.put(2, new String[]{"Sedan", "1", "100,000.00", "100", "20/11/2022"});
    this.map_produtos.put(3, new String[]{"Hatch1", "0", "40.000,00", "100", "31/12/2099"});
    this.map_produtos.put(4, new String[]{"Hatch2", "1", "50.000,00", "0", "10/05/2022"});
    this.map_produtos.put(5, new String[]{"Sport", "2", "220000,00", "100", "03/04/2023"});
    this.map_produtos.put(6, new String[]{"Truck", "1", "250000,00", "100", "01/02/2024"});
    this.map_produtos.put(7, new String[]{"Eletric", "2", "300.000", "100", "31/06/2025"});
    this.map_produtos.put(8, new String[]{"Autonomos", "0", "520,000.00", "100", "12/12/2022"});
  }

  public Map<Integer, String[]> todos(){ return map_produtos;}


  public Map<Integer, String[]> retrieveOutdated(){
    final HashMap<Integer, String[]> outdatedProductList = new HashMap<>();
    for (Entry<Integer, String[]> product : map_produtos.entrySet()) {
      if (product.getValue()[1].equals("0")) {
        outdatedProductList.put(product.getKey(), product.getValue());
      }
    }
    return outdatedProductList;
  }

  public Map<Integer, String[]> retrieveUnavailable(){
    final HashMap<Integer, String[]> unavailableProductList = new HashMap<>();
    for (Entry<Integer, String[]> product : map_produtos.entrySet()) {
      if ("0".equals(product.getValue()[3])) {
        unavailableProductList.put(product.getKey(), product.getValue());
      }
    }
    return unavailableProductList;
  }

  public Map<Integer, String[]> fetchProductsInTransport(){
    final HashMap<Integer, String[]> list = new HashMap<>();
    for (Entry<Integer, String[]> product : map_produtos.entrySet()) {
      if (product.getValue()[1].equals("1")) {
        list.put(product.getKey(), product.getValue());
      }
    }
    return list;
  }

  public Map<Integer, String[]> getAvailableProducts(){
    final HashMap<Integer, String[]> list = new HashMap<>();
    for (Entry<Integer, String[]> product : map_produtos.entrySet()) {
      if (product.getValue()[1].equals("2")) {
        list.put(product.getKey(), product.getValue());
      }
    }
    return list;
  }

  public void save(String[] novoProduto){
    map_produtos.put(9, novoProduto);
  }

  public void updateProducts(Map<Integer, String[]> updatedProducts) {
    for (Entry<Integer, String[]> productEntry : updatedProducts.entrySet()) {
      this.map_produtos.put(productEntry.getKey(), productEntry.getValue());
    }
  }

  public void remove(String productID) {
    this.map_produtos.remove(productID);
  }

  public String[] getProduct(Integer productModelId) {
    return this.map_produtos.get(productModelId);
  }
}
