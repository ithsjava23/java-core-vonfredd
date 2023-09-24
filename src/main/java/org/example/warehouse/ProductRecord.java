package org.example.warehouse;

import org.example.Category;

import java.math.BigDecimal;
import java.util.*;

public record ProductRecord(UUID randomUUID, String productName, Category category, BigDecimal price) {
    public ProductRecord{
        if (productName == null)
            throw new IllegalArgumentException("Product name can't be null or empty.");
        if (productName.equals(""))
            throw new IllegalArgumentException("Product name can't be null or empty.");
        if (category == null)
            throw new IllegalArgumentException("Category can't be null.");
        if(randomUUID == null)
            randomUUID = UUID.randomUUID();
        if (price == null)
            price = BigDecimal.valueOf(0);
    }
    public BigDecimal price() {
        return new BigDecimal(String.valueOf(price));
    }
    public UUID uuid() {
        return this.randomUUID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductRecord that = (ProductRecord) o;
        return Objects.equals(randomUUID, that.randomUUID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(randomUUID);
    }

}
