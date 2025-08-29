package com.project.code.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.code.Model.*;

import com.project.code.Repo.CustomerRepository;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.OrderDetailsRepository;
import com.project.code.Repo.OrderItemRepository;
import com.project.code.Repo.ProductRepository;
import com.project.code.Repo.StoreRepository;

@Service
public class OrderService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public OrderService(ProductRepository productRepository, InventoryRepository inventoryRepository, CustomerRepository customerRepository, StoreRepository storeRepository, OrderDetailsRepository orderDetailsRepository, OrderItemRepository orderItemRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.orderItemRepository = orderItemRepository;
        this.storeRepository = storeRepository;
    }
// 1. **saveOrder Method**:
//    - Processes a customer's order, including saving the order details and associated items.
//    - Parameters: `PlaceOrderRequestDTO placeOrderRequest` (Request data for placing an order)
//    - Return Type: `void` (This method doesn't return anything, it just processes the order)
    public void saveOrder(PlaceOrderRequestDTO placeOrderRequest) {
        String email = placeOrderRequest.getCustomerEmail();
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            throw new IllegalArgumentException("Invalid Customer");
        }
        Long storeId = placeOrderRequest.getStoreId();
        Store store = storeRepository.findById(storeId).get();
        if (store == null) throw new IllegalArgumentException("Invalid Store");
        
        double totalPrice = placeOrderRequest.getTotalPrice();
        OrderDetails newOrder = new OrderDetails(); 
        newOrder.setCustomer(customer);
        newOrder.setStore(store);
        newOrder.setTotalPrice(totalPrice);
        newOrder.setDate(LocalDateTime.now());

        orderDetailsRepository.save(newOrder);

        //Create and Save OrderItems
        List<PurchaseProductDTO> listProductDTO = placeOrderRequest.getPurchaseProduct();
        for (PurchaseProductDTO productDTO : listProductDTO) {
            Product product = productRepository.findById(productDTO.getId()).get();
            
            Inventory inventory = inventoryRepository.findByProductIdandStoreId(product.getId(), storeId);
            inventory.setStockLevel(inventory.getStockLevel() - productDTO.getQuantity());

            OrderItem item = new OrderItem(newOrder, product, productDTO.getQuantity(), productDTO.getTotal());
            orderItemRepository.save(item);
        }


    }
    



// 2. **Retrieve or Create the Customer**:
//    - Check if the customer exists by their email using `findByEmail`.
//    - If the customer exists, use the existing customer; otherwise, create and save a new customer using `customerRepository.save()`.

// 3. **Retrieve the Store**:
//    - Fetch the store by ID from `storeRepository`.
//    - If the store doesn't exist, throw an exception. Use `storeRepository.findById()`.

// 4. **Create OrderDetails**:
//    - Create a new `OrderDetails` object and set customer, store, total price, and the current timestamp.
//    - Set the order date using `java.time.LocalDateTime.now()` and save the order with `orderDetailsRepository.save()`.

// 5. **Create and Save OrderItems**:
//    - For each product purchased, find the corresponding inventory, update stock levels, and save the changes using `inventoryRepository.save()`.
//    - Create and save `OrderItem` for each product and associate it with the `OrderDetails` using `orderItemRepository.save()`.

   
}
