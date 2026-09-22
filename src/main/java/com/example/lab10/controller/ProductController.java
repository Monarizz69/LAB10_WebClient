package com.example.lab10.controller;

import com.example.lab10.model.Product;
import com.example.lab10.service.ProductService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // ทำแล้ว (ตัวอย่าง)
    @GetMapping("/{id}")
    public Mono<Product> getById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    @GetMapping
    public Flux<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    public Mono<Product> createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteProduct(@PathVariable String id) {
        return productService.deleteProduct(id);
    }

    @GetMapping("/category/{cat}")
    public Flux<Product> getProductsByCategory(@PathVariable("cat") String cat) {
        return productService.getProductsByCategory(cat);
    }

    @GetMapping("/{id}/price")
    public Mono<Double> getProductPrice(@PathVariable String id) {
        return productService.getProductPrice(id);
    }
}