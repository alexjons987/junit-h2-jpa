package se.alexjons.junith2jpa.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister;
import se.alexjons.junith2jpa.dto.ProductResponseDTO;
import se.alexjons.junith2jpa.entity.Product;
import se.alexjons.junith2jpa.exception.NotFoundException;
import se.alexjons.junith2jpa.repository.ProductRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceUnitTest {

    @Mock
    ProductRepository productRepository;

    @Spy
    @InjectMocks
    ProductService productService;

    Product entity;
    ProductResponseDTO dto;

    @BeforeEach
    void setUp() {
        Long id = 1L;
        String name = "Banan";
        double price = 9.99d;

        entity = new Product(id, name, price);

        dto = new ProductResponseDTO(id, name, price);
    }

    @Test
    @DisplayName("getProductById should return ProductResponseDTO")
    void getProductById() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(entity));
        doReturn(dto).when(productService).toResponseDTO(entity);

        // Act
        Optional<ProductResponseDTO> result = productService.getProductById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Banan", result.get().getName());

        verify(productRepository, times(1)).findById(1L);
        verify(productService, times(1)).toResponseDTO(entity);
    }

    @Test
    @DisplayName("getProductById should throw exception on Optional.empty()")
    void getProductByIdEmpty() {
        assertThrows(NotFoundException.class, () -> {
           productService.getProductById(-1L);
        });
    }

    @Test
    @DisplayName("save is never called on delete")
    void verifyDeleteNeverSaves() {
        productRepository.deleteById(1L);

        verify(productRepository, times(1)).deleteById(1L);
        verify(productRepository, never()).save(any());
    }
}
