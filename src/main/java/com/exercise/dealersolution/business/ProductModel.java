package com.exercise.dealersolution.business;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

public class ProductModel {
    private final Long id;
    private final String description;
    private final ProductStatus status;
    private final BigDecimal price;
    private final BigInteger quantity;
    private final Date deadline;

    public static final ProductModel EMPTY = new ProductModel(0L, "", ProductStatus.INVALID, BigDecimal.ZERO, BigInteger.ZERO, Date.from(Instant.EPOCH));

    public ProductModel(Long id, String description, ProductStatus status, BigDecimal price, BigInteger quantity, Date deadline) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.price = price;
        this.quantity = quantity;
        this.deadline = deadline;
    }

    public ProductModel cloneWithNewId(Long id) {
        return new ProductModel(id, description, status, price, quantity, deadline);
    }

    public ProductModel cloneWithNewPrice(BigDecimal price) {
        return new ProductModel(id, description, status, price, quantity, deadline);
    }

    public ProductModel updateProduct(ProductModel product) {
        return new ProductModel(id, product.getDescription(), product.getStatus(), product.getPrice(), product.getQuantity(), product.getDeadline());
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigInteger getQuantity() {
        return quantity;
    }

    public Date getDeadline() {
        return deadline;
    }

    public Boolean isOutdated() {
        return ProductStatus.OUTDATED.equals(status);
    }

    public Boolean isUnavailable() {
        return BigInteger.ZERO.equals(quantity);
    }

    public Boolean isInTransport() {
        return ProductStatus.IN_TRANSPORT.equals(status);
    }

    public Boolean isAvailable() {
        return ProductStatus.AVAILABLE.equals(status);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductModel that = (ProductModel) o;
        return Objects.equals(description, that.description) && Objects.equals(status, that.status) && Objects.equals(price, that.price) && Objects.equals(quantity, that.quantity) && Objects.equals(deadline, that.deadline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, status, price, quantity, deadline);
    }

}
