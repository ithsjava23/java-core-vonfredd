package org.example;

import org.example.warehouse.Warehouse;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Category {
    private static Map<String, Category> instances = new HashMap<>();
    private final String name;

    private Category(String name) {
        if(name == null)
            throw new IllegalArgumentException("Category name can't be null");
        StringBuilder b = new StringBuilder(name.substring(0,1).toUpperCase() + name.substring(1,name.length()));
        this.name = String.valueOf(b);
    }
    public static Category of(String test) {
        Category newCategory = new Category(test);
        if(newCategory.getName() == null)
            throw new IllegalArgumentException("Category name can't be null");
        return instances.computeIfAbsent(test, Category::new);
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }
}
