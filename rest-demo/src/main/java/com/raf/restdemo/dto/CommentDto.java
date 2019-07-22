package com.raf.restdemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class CommentDto {

    private Long id;
    @NotEmpty(message = "Text cant be empty")
    private String text;
    @NotEmpty(message = "user cant be empty")
    private String user;
    @Min(value = 1)
    @Max(value = 5)
    @JsonProperty("product_rating")
    private Integer productRating;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getProductRating() {
        return productRating;
    }

    public void setProductRating(Integer productRating) {
        this.productRating = productRating;
    }
}
