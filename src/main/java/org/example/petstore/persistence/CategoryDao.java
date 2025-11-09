package org.example.petstore.persistence;

import org.example.petstore.domain.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> getCategoryList();

    Category getCategory(String categoryId);
}
