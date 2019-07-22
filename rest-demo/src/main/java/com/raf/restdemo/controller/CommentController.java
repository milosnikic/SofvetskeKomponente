package com.raf.restdemo.controller;

import com.raf.restdemo.dto.CommentDto;
import com.raf.restdemo.service.CommentService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/product/{id}/comment")
public class CommentController {

    private CommentService commentService;

    public CommentController (CommentService commentService) {
        this.commentService = commentService;
    }

    @ApiOperation(value = "Get all comments")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "What page number you want", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Number of items to return", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})
    @GetMapping
    public ResponseEntity<Page<CommentDto>> findAll(@PathVariable("id") Long id, Pageable pageable) {
        return new ResponseEntity<>(commentService.findAllByProductId(id, pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CommentDto> add(@PathVariable("id") Long id, @RequestBody @Valid CommentDto commentDto) {
        return new ResponseEntity<>(commentService.addCommentOnProduct(id, commentDto), HttpStatus.OK);
    }

}
