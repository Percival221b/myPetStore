package org.example.petstore.service;

import org.example.petstore.domain.Category;
import org.example.petstore.domain.Item;
import org.example.petstore.domain.Product;
import org.example.petstore.persistence.CategoryDao;
import org.example.petstore.persistence.ItemDao;
import org.example.petstore.persistence.ProductDao;
import org.example.petstore.persistence.impl.CategoryDaoImpl;
import org.example.petstore.persistence.impl.ItemDaoImpl;
import org.example.petstore.persistence.impl.ProductDaoImpl;

import java.util.List;

public class CatalogService {
    private CategoryDao categoryDao;
    private ProductDao productDao;
    private ItemDao itemDao;

    public CatalogService() {
        this.categoryDao = new CategoryDaoImpl();
        this.productDao = new ProductDaoImpl();
        this.itemDao = new ItemDaoImpl();
    }

    public List<Category> getCategoryList() {return categoryDao.getCategoryList();}

    public Category getCategory(String categoryId) {
        return categoryDao.getCategory(categoryId);
    }

    public Product getProduct(String productId) {
        return productDao.getProduct(productId);
    }

    public List<Product> getProductListByCategory(String categoryId) {
        return productDao.getProductListByCategory(categoryId);
    }

    // TODO enable using more than one keyword
    public List<Product> searchProductList(String keyword) {
        return productDao.searchProductList("%" + keyword.toLowerCase() + "%");
    }

    public List<Item> getItemListByProduct(String productId) {
        return itemDao.getItemListByProduct(productId);
    }

    public Item getItem(String itemId) {
        return itemDao.getItem(itemId);
    }

    public boolean isItemInStock(String itemId) {
        return itemDao.getInventoryQuantity(itemId) > 0;
    }

}
