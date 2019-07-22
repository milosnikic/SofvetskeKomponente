package com.raf.restdemo.service.impl;

import com.raf.restdemo.domain.Product;
import com.raf.restdemo.dto.ProductDto;
import com.raf.restdemo.exception.CustomException;
import com.raf.restdemo.exception.ErrorCode;
import com.raf.restdemo.mapper.ProductMapper;
import com.raf.restdemo.repository.ProductRepository;
import com.raf.restdemo.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public Page<ProductDto> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::productToProductDto);
    }

    @Override
    public ProductDto findById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::productToProductDto)
                .orElseThrow(() -> new CustomException("Product not found", ErrorCode.RESOURCE_NOT_FOUND
                        , HttpStatus.NOT_FOUND));
    }

    @Override
    public ProductDto add(ProductDto productDto) {
        return productMapper.productToProductDto(productRepository
                .save(productMapper.productDtoToProduct(productDto)));
    }

    @Override
    public ProductDto update(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());

        return productMapper.productToProductDto(productRepository.save(product));
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
