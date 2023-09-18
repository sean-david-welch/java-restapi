package com.example.demo.product;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
	public List<Product> GetProducts() {
        return productService.GetProducts();
	}

    @PutMapping(path = "{productId}")
    public void updateProduct(@PathVariable("productId") String productId, @RequestBody Product product) {
        productService.PutProduct(productId, product);
    }
    

    @PostMapping
    public void registerProduct(@RequestBody Product product) {
        productService.CreateProduct(product);
    }

    @DeleteMapping(path = "{productId}")
    public void deleteProduct(@PathVariable("productId") String productId) {
        productService.RemoveProduct(productId);
    }
}