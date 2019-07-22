package com.raf.restdemo.mapper;

import com.raf.restdemo.domain.Comment;
import com.raf.restdemo.domain.Product;
import com.raf.restdemo.dto.CommentDto;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public CommentDto commentToCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());
        commentDto.setUser(comment.getUser());
        commentDto.setId(comment.getId());
        return commentDto;
    }

    public Comment commentDtoToComment(CommentDto commentDto, Product product) {
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setText(commentDto.getText());
        comment.setUser(commentDto.getUser());
        comment.setId(commentDto.getId());
        comment.setProduct(product);
        return comment;
    }

}
