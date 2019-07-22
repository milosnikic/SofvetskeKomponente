package com.raf.restdemo.service.impl;

import com.raf.restdemo.domain.Product;
import com.raf.restdemo.dto.CommentDto;
import com.raf.restdemo.mapper.CommentMapper;
import com.raf.restdemo.repository.CommentRepository;
import com.raf.restdemo.repository.ProductRepository;
import com.raf.restdemo.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private ProductRepository productRepository;
    private CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepository, ProductRepository productRepository
            , CommentMapper commentMapper) {

        this.commentRepository = commentRepository;
        this.productRepository = productRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public Page<CommentDto> findAllByProductId(Long productId, Pageable pageable) {
        return commentRepository.findAllByProduct_Id(productId, pageable)
                .map(commentMapper::commentToCommentDto);
    }

    @Override
    public CommentDto addCommentOnProduct(Long productId, CommentDto commentDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        return commentMapper.commentToCommentDto(commentRepository
                .save(commentMapper.commentDtoToComment(commentDto, product)));
    }

}
