package com.project.code.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.*;

import com.project.code.Model.Product;

public interface ProductRepository extends JpaRepository<Product,Long>{
// 1. Add the repository interface:
//    - Extend JpaRepository<Product, Long> to inherit basic CRUD functionality.
//    - This allows the repository to perform operations like save, delete, update, and find without having to implement these methods manually.

// Example: public interface ProductRepository extends JpaRepository<Product, Long> {}

// 2. Add custom query methods:
//    - **findAll**:
//      - This method will retrieve all products.
//      - Return type: List<Product>
    public List<Product> findAll();

// Example: public List<Product> findAll();

//    - **findByCategory**:
//      - This method will retrieve products by their category.
//      - Return type: List<Product>
//      - Parameter: String category

// Example: public List<Product> findByCategory(String category);
    public List<Product> findByCategory(String category);

//    - **findByPriceBetween**:
//      - This method will retrieve products within a price range.
//      - Return type: List<Product>
//      - Parameters: Double minPrice, Double maxPrice

// Example: public List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
    public List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

//    - **findBySku**:
//      - This method will retrieve a product by its SKU.
//      - Return type: Product
//      - Parameter: String sku

// Example: public Product findBySku(String sku);
    public Product findBySku(String sku);

//    - **findByName**:
//      - This method will retrieve a product by its name.
//      - Return type: Product
//      - Parameter: String name

// Example: public Product findByName(String name);
    public Product findByName(String name);

//    - **findByNameLike**:
//      - This method will retrieve products by a name pattern for a specific store.
//      - Return type: List<Product>
//      - Parameters: Long storeId, String pname
//      - Use @Query annotation to write a custom query.
    @Query("SELECT i.product FROM Inventory Where i.store.id = :storeId AND LOWER(i.product.name) LIKE LOWER(CONCAT('%', :pname, '%')")
    public List<Product> findByNameLike(Long storeId, String pname);

    @Query("SELECT i.product FROM Inventory Where i.store.id = :storeId AND LOWER(i.product.name) LIKE LOWER(CONCAT('%', :pname, '%') AND i.product.catrogry = :category")
    public List<Product> findByNameAndCategory(Long storeId, String pname, String category);

    @Query("SELECT i.product FROM Inventory WHERE i.product.category = :category AND i.store.id = :storeId")
    public List<Product> findByProductIdandStoreId(String category, Long storeId);

    @Query("SELECT i.product FROM INVENTORY WHERE LOWER(i.name) LIKE LOWER(CONCAT('%', :pname, '%'))")
    public List<Product> findProductBySubName(String pname);

    @Query("SELECT i.product FROM Inventory where i.store.id = :storeId")
    public List<Product> findProductsByStoreId(Long storeId);

    @Query("SELECT i.product from Inventory where i.product.cateogry = :category AND i.store.id = :storeId")
    public List<Product> findProductByCategory(String category, Long storeId);

    @Query("SELECT i FROM Product i WHERE LOWER(i.name) LIKE LOWER(CONCAT('%', :pname, '%')) AND i.category = :category")
    public List<Product> findProductBySubNameAndCategory(String pname, String category);


}   
