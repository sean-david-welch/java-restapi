package com.example.demo.product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

	public List<Product> GetProducts() {
        return productRepository.findAll();
    }


    public void CreateProduct(Product product) { 
        Optional<Product> productOptional = productRepository.findProductById(product.getId());

        if (productOptional.isPresent()) {
            throw new IllegalStateException("This product exists");
        }

        product.setId(UUID.randomUUID().toString());

        productRepository.save(product);
    }

    public void RemoveProduct(String productId) {
        boolean exists = productRepository.existsById(productId);

        if (!exists) {
            throw new IllegalStateException("Product does not exist" + productId);
        }
        productRepository.deleteById(productId);
    }
}
