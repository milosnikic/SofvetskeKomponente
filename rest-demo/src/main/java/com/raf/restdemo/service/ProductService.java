package com.raf.restdemo.service;

import com.raf.restdemo.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Page<ProductDto> findAll(Pageable pageable);

    ProductDto findById(Long id);

    ProductDto add(ProductDto productDto);

    ProductDto update(Long id, ProductDto productDto);

    void deleteById(Long id);

}
