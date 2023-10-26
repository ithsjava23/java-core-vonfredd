package org.example.warehouse;

import org.example.Category;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


public class Warehouse {

    private static Map<String, Warehouse> instances = new HashMap<>();
    static List<ProductRecord> addedProducts = new ArrayList<>();
    private List<ProductRecord> changedProducts = new ArrayList<>();
    private final String name;

    private Warehouse(String name) {
        this.name = name;
    }

    public static Warehouse getInstance(String name) {
        addedProducts.clear();
        return instances.computeIfAbsent(name, Warehouse::new);
    }

    public static Warehouse getInstance() {
        addedProducts.clear();
        return instances.computeIfAbsent("", Warehouse::new);
    }

    public static Warehouse createWarehouse(String text) {
        if (text == null)
            return getInstance();
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
        return List.copyOf(addedProducts);
    }

    public ProductRecord addProduct(UUID randomUUID, String milk, Category dairy, BigDecimal valueOf) {
        var map = addedProducts.stream().map((e) -> e.randomUUID()).toList();
        if (map.contains(randomUUID))
            throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
        ProductRecord product = new ProductRecord(randomUUID, milk, dairy, valueOf);
        addedProducts.add(product);
        return product;
    }

    public Optional<ProductRecord> getProductById(UUID uuid) {
        var getId = addedProducts.stream().filter((e) -> e.randomUUID().equals(uuid)).toList();
        return getId.isEmpty() ? Optional.empty() : Optional.of(getId.get(0));
    }

    public List<ProductRecord> getChangedProducts() {
        return List.copyOf(changedProducts);
    }

    public ProductRecord updateProductPrice(UUID uuid, BigDecimal valueOf) {
        var map = addedProducts.stream().map((e) -> e.randomUUID()).toList();
        if (!map.contains(uuid))
            throw new IllegalArgumentException("Product with that id doesn't exist.");
        else if (map.contains(uuid)) {
            var i = addedProducts.stream().filter((e) -> e.randomUUID().equals(uuid)).toList();
            var obj = addedProducts.get(addedProducts.indexOf(i.get(0)));
            changedProducts.add(obj);
            addedProducts.set(addedProducts.indexOf(obj), new ProductRecord(obj.randomUUID(), obj.productName(), obj.category(), valueOf));
            return obj;
        }
        return null;
    }

    public Map<Category, List<ProductRecord>> getProductsGroupedByCategories() {
        return addedProducts.stream().collect(Collectors.groupingBy(ProductRecord::category));
    }

    public List<ProductRecord> getProductsBy(Category category) {
        return addedProducts.stream().filter((e) -> e.category().equals(category)).toList();
    }
}
