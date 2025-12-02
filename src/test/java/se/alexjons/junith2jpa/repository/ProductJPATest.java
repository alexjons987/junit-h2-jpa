package se.alexjons.junith2jpa.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import se.alexjons.junith2jpa.entity.Product;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    @DisplayName("findByName should return previously saved product")
    void findByName() {
        // Arrange
        Product banana = new Product("Banan", 9.99d);
        Product apple = new Product("Äpple", 10.99d);
        Product blueberries = new Product("Blåbär", 11.99d);

        Product savedBanana = productRepository.save(banana);
        productRepository.save(apple);
        productRepository.save(blueberries);

        // Act
        List<Product> result = productRepository.findByName(savedBanana.getName());

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactly(savedBanana);
        assertThat(result)
                .extracting(Product::getName)
                .doesNotContain("Äpple", "Blåbär");
    }
}
