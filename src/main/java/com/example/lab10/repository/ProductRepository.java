package com.example.lab10.repository;

import com.example.lab10.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

/**
 * ProductRepository — In-memory Reactive Repository
 *
 * ✅ โครงสร้างและ annotation ครบแล้ว
 * ❌ TODO: เติม method body ให้ครบทุก method
 *
 * ใช้ ConcurrentHashMap เป็น in-memory storage
 * (ไม่ต่อ Database — เน้นฝึก Mono/Flux)
 *
 * Hint:
 *   - Mono.just(value)          คืนค่าเดียว
 *   - Mono.empty()              คืนเปล่า
 *   - Flux.fromIterable(list)   คืนหลายค่าจาก collection
 */
@Repository
public class ProductRepository {
    // ── In-memory storage ────────────────────────────────
    private final Map<String, Product> store = new ConcurrentHashMap<>();

    // ── Constructor: ใส่ข้อมูลตัวอย่าง ──────────────────
    public ProductRepository() {
        store.put("1", new Product("1", "iPhone 15 Pro (673380059-1 SEC 1)",
                "Electronics", "Apple", 50, 39900.0, "MEMBER"));
        store.put("2", new Product("2", "MacBook Air M3",
                "Electronics", "Apple", 20, 49900.0, "NONE"));
        store.put("3", new Product("3", "Samsung Galaxy S24",
                "Electronics", "Samsung", 30, 29900.0, "SEASONAL"));
    }

    public Mono<Product> findById(String id) {
        Product product = store.get(id);
        return product != null ? Mono.just(product) : Mono.empty();
    }

    public Flux<Product> findAll() {
        return Flux.fromIterable(store.values());
    }

    public Mono<Product> save(Product product) {
        store.put(product.getId(), product);
        return Mono.just(product);
    }

    public Mono<Void> deleteById(String id) {
        store.remove(id);
        return Mono.empty();
    }

    public Flux<Product> findByCategory(String category) {
        return findAll()
                .filter(p -> p.getCategory() != null && p.getCategory().equalsIgnoreCase(category));
    }
}