package se.alexjons.junith2jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.alexjons.junith2jpa.dto.ProductResponseDTO;
import se.alexjons.junith2jpa.entity.Product;
import se.alexjons.junith2jpa.exception.NotFoundException;
import se.alexjons.junith2jpa.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public Optional<ProductResponseDTO> getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            throw new NotFoundException("Product not found");
        }

        return product.map(this::toResponseDTO);
    }

    public List<ProductResponseDTO> getProductByName(String name) {
        return productRepository.findByName(name).stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<ProductResponseDTO> getProductByNameContaining(String name) {
        return productRepository.findByNameContaining(name).stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public ProductResponseDTO toResponseDTO(Product product) {
        if (product == null) return null;

        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getPrice()
        );
    }
}
