package com.raf.restdemo.wrapper;

import com.raf.restdemo.domain.Product;
import com.raf.restdemo.dto.ProductDto;

import java.util.List;

public class ProductPageWrapper {

    private List<ProductDto> content;

    public List<ProductDto> getContent() {
        return content;
    }

    public void setContent(List<ProductDto> content) {
        this.content = content;
    }
}
