package br.com.debezium.services;

import br.com.debezium.models.Product;
import br.com.debezium.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    @Transactional
    public Product save(Product product){

        return repository.save(product);
    }
}
