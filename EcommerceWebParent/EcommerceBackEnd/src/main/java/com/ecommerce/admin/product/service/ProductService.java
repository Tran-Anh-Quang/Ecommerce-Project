package com.ecommerce.admin.product.service;

import com.ecommerce.admin.product.exception.ProductNotFoundException;
import com.ecommerce.admin.product.repository.ProductRepository;
import com.ecommerce.common.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    public static final int PRODUCTS_PER_PAGE = 5;
    @Autowired
    private ProductRepository productRepository;

    public List<Product> listAll(){
        return productRepository.findAll();
    }

    public Page<Product> listByPage(int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);
        return productRepository.findAll(pageable);
    }

    public void delete(Integer id) throws ProductNotFoundException {
        Long countById = productRepository.countById(id);
        if (countById == null || countById == 0) {
            throw new ProductNotFoundException("Could not find any product with ID: " + id);
        }

        productRepository.deleteById(id);
    }
}
