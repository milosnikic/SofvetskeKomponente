package com.raf.restdemo.controller;

import com.raf.restdemo.domain.Product;
import com.raf.restdemo.dto.ProductDto;
import com.raf.restdemo.exception.ErrorCode;
import com.raf.restdemo.exception.ErrorDetails;
import com.raf.restdemo.mapper.ProductMapper;
import com.raf.restdemo.repository.ProductRepository;
import com.raf.restdemo.wrapper.ProductPageWrapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    private static final String PRODUCT_URL = "/product";

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private ProductMapper productMapper;

    @Before
    public void setUp() {
        productRepository.deleteAll();
    }

    @Test
    public void testFindAll() {
        //given
        Product product = createTestProduct("product1", "desc1", new BigDecimal("100.00"));
        productRepository.save(product);
        //when
        ResponseEntity<ProductPageWrapper> response = testRestTemplate
                .exchange(PRODUCT_URL, HttpMethod.GET, null, ProductPageWrapper.class);
        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getContent().size()).isEqualTo(1);
        assertThat(response.getBody().getContent().get(0).getTitle()).isEqualTo(product.getTitle());
        assertThat(response.getBody().getContent().get(0).getDescription()).isEqualTo(product.getDescription());
        assertThat(response.getBody().getContent().get(0).getPrice()).isEqualTo(product.getPrice());
    }

    @Test
    public void testFindAllWithSpecifiedPageable() {
        //given
        Product product1 = createTestProduct("product1", "desc1", new BigDecimal("100.00"));
        Product product2 = createTestProduct("product2", "desc2", new BigDecimal("200.00"));
        productRepository.save(product1);
        productRepository.save(product2);
        String url = PRODUCT_URL + "?page=1&size=1";
        //when
        ResponseEntity<ProductPageWrapper> response = testRestTemplate
                .exchange(url, HttpMethod.GET, null, ProductPageWrapper.class);
        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getContent().size()).isEqualTo(1);
        assertThat(response.getBody().getContent().get(0).getTitle()).isEqualTo(product2.getTitle());
        assertThat(response.getBody().getContent().get(0).getDescription()).isEqualTo(product2.getDescription());
        assertThat(response.getBody().getContent().get(0).getPrice()).isEqualTo(product2.getPrice());
    }

    @Test
    public void testFindById() {
        //given
        Product product = createTestProduct("product", "desc", new BigDecimal("100.00"));
        productRepository.save(product);
        String url = PRODUCT_URL + "/" + product.getId();
        //when
        ResponseEntity<ProductDto> response = testRestTemplate
                .exchange(url, HttpMethod.GET, null, ProductDto.class);
        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getTitle()).isEqualTo(product.getTitle());
        assertThat(response.getBody().getDescription()).isEqualTo(product.getDescription());
        assertThat(response.getBody().getPrice()).isEqualTo(product.getPrice());
    }

    @Test
    public void testFindByIdNotFound() {
        //given
        String url = PRODUCT_URL + "/1";
        //when
        ResponseEntity<ErrorDetails> response = testRestTemplate
                .exchange(url, HttpMethod.GET, null, ErrorDetails.class);
        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().getError()).isEqualTo("Product not found");
        assertThat(response.getBody().getErrorCode()).isEqualTo(ErrorCode.RESOURCE_NOT_FOUND);
    }

    @Test
    public void testAdd() {
        //given
        Product product = createTestProduct("product", "desc", new BigDecimal("100.00"));
        ProductDto productDto = productMapper.productToProductDto(product);
        HttpEntity<ProductDto> request = new HttpEntity<>(productDto);
        //when
        ResponseEntity<ProductDto> response = testRestTemplate
                .exchange(PRODUCT_URL, HttpMethod.POST, request, ProductDto.class);
        //then
        //check response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getTitle()).isEqualTo(productDto.getTitle());
        assertThat(response.getBody().getDescription()).isEqualTo(productDto.getDescription());
        assertThat(response.getBody().getPrice()).isEqualTo(productDto.getPrice());
        //check from database
        Product productFromDatabase = productRepository.findAll().get(0);
        assertThat(productFromDatabase.getTitle()).isEqualTo(productDto.getTitle());
        assertThat(productFromDatabase.getDescription()).isEqualTo(productDto.getDescription());
        assertThat(productFromDatabase.getPrice()).isEqualTo(productDto.getPrice());
    }

    @Test
    public void testUpdate() {
        //given
        Product product = createTestProduct("product", "desc", new BigDecimal("100.00"));
        productRepository.save(product);
        String url = PRODUCT_URL + "/" + product.getId();
        ProductDto productDtoUpdate = productMapper.productToProductDto(createTestProduct("title1", "desc1"
                , new BigDecimal("200.00")));
        HttpEntity<ProductDto> request = new HttpEntity<>(productDtoUpdate);
        //when
        ResponseEntity<ProductDto> response = testRestTemplate
                .exchange(url, HttpMethod.PUT, request, ProductDto.class);
        //then
        //check response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getTitle()).isEqualTo(productDtoUpdate.getTitle());
        assertThat(response.getBody().getDescription()).isEqualTo(productDtoUpdate.getDescription());
        assertThat(response.getBody().getPrice()).isEqualTo(productDtoUpdate.getPrice());
        //check from database
        Product productFromDatabase = productRepository.findAll().get(0);
        assertThat(productFromDatabase.getTitle()).isEqualTo(productDtoUpdate.getTitle());
        assertThat(productFromDatabase.getDescription()).isEqualTo(productDtoUpdate.getDescription());
        assertThat(productFromDatabase.getPrice()).isEqualTo(productDtoUpdate.getPrice());
    }

    @Test
    public void testDelete() {
        //given
        Product product = createTestProduct("product", "desc", new BigDecimal("100.00"));
        productRepository.save(product);
        String url = PRODUCT_URL + "/" + product.getId();
        //when
        ResponseEntity<Void> response = testRestTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(productRepository.count()).isEqualTo(0);
    }

    private Product createTestProduct(String title, String description, BigDecimal price) {
        Product product = new Product();
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        return product;
    }


}
