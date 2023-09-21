package org.example.warehouse;

import org.example.Category;

import java.math.BigDecimal;
import java.util.*;

public class Warehouse {
    private static Map<String, Warehouse> instances = new HashMap<>();
    private List<ProductRecord> addedProducts = new ArrayList<>();
    private List<ProductRecord> changedProducts = new ArrayList<>();
    private final String name;
    private Warehouse(String name) {
        this.name = name;
    }

    public static Warehouse getInstance(String myStore) {
        return instances.computeIfAbsent(myStore, Warehouse::new);
    }

    public static Warehouse createWarehouse(String text){
        if (text == null)
            return getInstance("");
        return getInstance(text);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Warehouse warehouse = (Warehouse) o;
        return Objects.equals(name, warehouse.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Warehouse{" +
                "name='" + name + '\'' +
                '}';
    }

    public boolean isEmpty() {
        return addedProducts.isEmpty();
    }

    public List<ProductRecord> getProducts() {
        return Collections.unmodifiableList(this.addedProducts);
    }

    public ProductRecord addProduct(UUID randomUUID, String milk, Category dairy, BigDecimal valueOf) {
        for (ProductRecord p : addedProducts)
            if(p.uuid().equals(randomUUID))
                throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
        addedProducts.add(new ProductRecord(randomUUID,milk,dairy,valueOf));
        return new ProductRecord(randomUUID,milk,dairy,valueOf);
    }

    public Optional<ProductRecord> getProductById(UUID uuid) {
        for(ProductRecord p : addedProducts)
            if (p.randomUUID().equals(uuid))
                return Optional.of(p);
        return Optional.empty();
    }

    public List<ProductRecord> getChangedProducts() {
        return List.copyOf(changedProducts);
    }

    public ProductRecord updateProductPrice(UUID uuid, BigDecimal valueOf) {
        boolean found = false;
        int index;
        for(ProductRecord p : addedProducts)
            if (p.uuid().equals(uuid)) {
                index = addedProducts.indexOf(p);
                changedProducts.add(p);
                addedProducts.set(index, new ProductRecord(p.uuid(),p.productName(),p.category(),valueOf));
                return p;
            }
        found = true;
        if(found == true)
            throw new IllegalArgumentException("Product with that id doesn't exist.");
        return null;
    }

    public Map<Category,List<ProductRecord>> getProductsGroupedByCategories() {
        List<ProductRecord> tempList = (addedProducts);
        Map<Category, List<ProductRecord>> productsOfCategories = new HashMap<>();
        tempList.sort((o1, o2) -> o1.productName().compareTo(o2.productName()));
        for (ProductRecord p : tempList)
            productsOfCategories.computeIfAbsent(p.category(), k -> new ArrayList<>()).add(p);
        return productsOfCategories;
    }

    public List<ProductRecord> getProductsBy(Category meat) {
        List<ProductRecord> temp = new ArrayList<>();
        for (ProductRecord p : addedProducts)
            if(p.category().equals(meat))
                temp.add(p);
        return temp;
    }
}
