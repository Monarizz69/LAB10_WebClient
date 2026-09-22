package com.example.lab10;

import com.example.lab10.model.Product;
import com.example.lab10.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

/**
 * Lab10ApplicationTests — ทดสอบ Reactive code
 *
 * ✅ test findById() ทำเสร็จแล้วเป็นตัวอย่าง
 * ❌ TODO: เพิ่ม test สำหรับ method ที่นักศึกษาทำเอง
 *
 * StepVerifier — วิธีทดสอบ Mono/Flux:
 *   StepVerifier.create(mono/flux)
 *     .expectNext(value)     ← คาดหวังค่าที่ได้
 *     .expectNextCount(n)    ← คาดหวังจำนวน element
 *     .verifyComplete()      ← ยืนยัน onComplete
 *     .verifyError()         ← ยืนยัน onError
 */
@SpringBootTest
class Lab10ApplicationTests {

    @Autowired
    private ProductRepository repository;

    // ══════════════════════════════════════════════════════
    // ✅ ตัวอย่าง test — ศึกษาแล้วเพิ่ม test เอง
    // ══════════════════════════════════════════════════════

    @Test
    void contextLoads() {
        // Spring Application Context โหลดสำเร็จ
    }

    @Test
    void testFindById_found() {
        // ✅ ตัวอย่าง: ทดสอบ findById ที่พบข้อมูล
        StepVerifier.create(repository.findById("1"))
                .expectNextMatches(p -> p.getName().contains("iPhone"))
                .verifyComplete();
    }

    @Test
    void testFindById_notFound() {
        // ✅ ตัวอย่าง: ทดสอบ findById ที่ไม่พบข้อมูล
        StepVerifier.create(repository.findById("999"))
                .verifyComplete(); // Mono.empty() → onComplete ทันที
    }

    // ══════════════════════════════════════════════════════
    // ❌ TODO: เพิ่ม test ด้านล่างนี้
    // ══════════════════════════════════════════════════════

    @Test
    void testFindAll() {
        // ทดสอบว่า findAll() คืน Flux ที่มีข้อมูลครบ (ค่าเริ่มต้นมี 3 รายการ)
        StepVerifier.create(repository.findAll())
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void testSave() {
        // สร้าง Product จำลองตัวใหม่ขึ้นมาทดสอบ
        Product newProduct = new Product("4", "iPad Pro", "Electronics", "Apple", 15, 32900.0, "NONE");

        // ทดสอบ save() บันทึกแล้วคืน Product ตัวเดิมกลับมา
        StepVerifier.create(repository.save(newProduct))
                .expectNextMatches(p -> p.getId().equals("4") && p.getName().equals("iPad Pro"))
                .verifyComplete();

        // ตรวจสอบยืนยันว่าข้อมูลถูกบันทึกลง store จริงผ่าน findById
        StepVerifier.create(repository.findById("4"))
                .expectNextMatches(p -> p.getName().equals("iPad Pro"))
                .verifyComplete();
    }

    @Test
    void testFindByCategory() {
        // ทดสอบ findByCategory("Electronics") ซึ่งข้อมูลตัวอย่างเริ่มต้นอยู่ในหมวดนี้ทั้งหมด
        StepVerifier.create(repository.findByCategory("Electronics"))
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void testDeleteById() {
        // บันทึกข้อมูลชั่วคราวเพื่อนำมาทดสอบลบ
        Product tempProduct = new Product("99", "Temp Item", "Accessories", "Generic", 5, 500.0, "NONE");
        repository.save(tempProduct).block();

        // ทดสอบ deleteById คืนค่า Mono.empty() (onComplete ทันที)
        StepVerifier.create(repository.deleteById("99"))
                .verifyComplete();

        // ยืนยันว่า id ดังกล่าวถูกลบออกจาก store ไปแล้ว
        StepVerifier.create(repository.findById("99"))
                .verifyComplete();
    }
}

