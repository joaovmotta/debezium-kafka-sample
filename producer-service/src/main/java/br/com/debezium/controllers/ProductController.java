package br.com.debezium.controllers;

import br.com.debezium.models.Product;
import br.com.debezium.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping("/products")
    public ResponseEntity<?> save(@RequestBody Product product){

        Product dbProduct = service.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(dbProduct);
    }
}
