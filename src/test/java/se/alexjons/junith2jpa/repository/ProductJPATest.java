package se.alexjons.junith2jpa.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import se.alexjons.junith2jpa.entity.Product;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
class ProductJPATest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("getProductById should return previously saved product")
    void getProductById() {
        // Arrange
        Product banana = new Product("Banan", 9.99d);
        Product apple = new Product("Äpple", 10.99d);
        Product blueberries = new Product("Blåbär", 11.99d);

        Product savedBanana = productRepository.save(banana);
        productRepository.save(apple);
        productRepository.save(blueberries);

        // Act
        Optional<Product> product = productRepository.findById(savedBanana.getId());

        // Assert
        assertTrue(product.isPresent());
        assertEquals(savedBanana.getId(), product.get().getId());
        assertEquals(savedBanana.getName(), product.get().getName());
        assertEquals(savedBanana.getPrice(), product.get().getPrice());
    }
}
