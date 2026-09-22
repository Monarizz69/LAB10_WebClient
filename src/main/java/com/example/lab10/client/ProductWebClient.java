package com.example.lab10.client;

import com.example.lab10.model.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ProductWebClient {

    private final WebClient webClient;

    public ProductWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    // มีให้แล้วเป็นตัวอย่าง
    public Mono<Product> getProductById(Long id) {
        return webClient.get()
                .uri("/products/{id}", id)
                .retrieve()
                .bodyToMono(Product.class);
    }

    // TODO: 1. ดึงข้อมูลสินค้าทั้งหมด
    public Flux<Product> getAllProducts() {
        return webClient.get()
                .uri("/products")
                .retrieve()
                .bodyToFlux(Product.class);
    }

    // TODO: 2. สร้างสินค้าใหม่
    public Mono<Product> createProduct(Product product) {
        return webClient.post()
                .uri("/products")
                .bodyValue(product)
                .retrieve()
                .bodyToMono(Product.class);
    }

    // TODO: 3. ลบสินค้าตาม id
    public Mono<Void> deleteProduct(Long id) {
        return webClient.delete()
                .uri("/products/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }

    // TODO: 4. ดึงข้อมูลสินค้าตามหมวดหมู่
    public Flux<Product> getProductsByCategory(String category) {
        return webClient.get()
                .uri("/products/category/{cat}", category)
                .retrieve()
                .bodyToFlux(Product.class);
    }

    // TODO: 5. ดึงเฉพาะราคาสินค้าตาม id
    public Mono<Double> getProductPrice(Long id) {
        return webClient.get()
                .uri("/products/{id}/price", id)
                .retrieve()
                .bodyToMono(Double.class);
    }
}