package com.raf.restdemo.repository;

import com.raf.restdemo.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByProduct_Id(Long productId, Pageable pageable);

}
