package com.project.code.Service;

import org.springframework.stereotype.Service;

import com.project.code.Model.*;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.ProductRepository;

@Service
public class ServiceClass {
    
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    public ServiceClass(InventoryRepository inventoryRepository, ProductRepository productRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }
 
// 1. **validateInventory Method**:
//    - Checks if an inventory record exists for a given product and store combination.
//    - Parameters: `Inventory inventory`
//    - Return Type: `boolean` (Returns `false` if inventory exists, otherwise `true`)
    public boolean validateInventory(Inventory inventory) {
        Long productId = inventory.getProduct().getId();
        Long storeId = inventory.getStore().getId();
        Inventory foundInventory = inventoryRepository.findByProductIdandStoreId(productId, storeId);
        return foundInventory != null;
    }    
// 2. **validateProduct Method**:
//    - Checks if a product exists by its name.
//    - Parameters: `Product product`
//    - Return Type: `boolean` (Returns `false` if a product with the same name exists, otherwise `true`)

    public boolean validateProduct(Product product) {
        Product targeProduct = productRepository.findByName(product.getName());
        return targeProduct != null;
    }

// 3. **ValidateProductId Method**:
//    - Checks if a product exists by its ID.
//    - Parameters: `long id`
//    - Return Type: `boolean` (Returns `false` if the product does not exist with the given ID, otherwise `true`)
    public boolean ValidateProductId(Long id) {
        Product targeProduct = productRepository.findById(id).get();
        return targeProduct != null;
    }
// 4. **getInventoryId Method**:
//    - Fetches the inventory record for a given product and store combination.
//    - Parameters: `Inventory inventory`
//    - Return Type: `Inventory` (Returns the inventory record for the product-store combination)
    public Inventory getInventoryId(Inventory inventory) {
        Long productId = inventory.getProduct().getId();
        Long storeId = inventory.getStore().getId();
        Inventory foundInventory = inventoryRepository.findByProductIdandStoreId(productId, storeId);
        return foundInventory;    
    }
}
