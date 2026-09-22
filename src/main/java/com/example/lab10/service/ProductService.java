package com.example.lab10.service;

import com.example.lab10.model.Product;
import com.example.lab10.repository.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Mono<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    public Flux<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Mono<Product> createProduct(Product product) {
        return productRepository.save(product);
    }

    public Mono<Void> deleteProduct(String id) {
        return productRepository.deleteById(id);
    }

    public Flux<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    // ดึงราคาสินค้าผ่านการดึง object Product แล้ว map เอาเฉพาะฟิลด์ price
    public Mono<Double> getProductPrice(String id) {
        return productRepository.findById(id)
                .map(Product::getPrice);
    }
}