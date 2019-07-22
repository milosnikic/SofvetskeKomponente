package com.raf.restdemo.service;

import com.raf.restdemo.dto.CommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    Page<CommentDto> findAllByProductId(Long productId, Pageable pageable);

    CommentDto addCommentOnProduct(Long productId, CommentDto commentDto);

}
