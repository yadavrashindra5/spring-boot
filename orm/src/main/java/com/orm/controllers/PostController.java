package com.orm.controllers;

import com.orm.dtos.PostDto;
import com.orm.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<PostDto> create(@RequestBody PostDto postDto) {
        PostDto postDto1 = postService.create(postDto);
        ResponseEntity<PostDto> response = new ResponseEntity<>(postDto1, HttpStatus.CREATED);
        return response;
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> get(@PathVariable String postId) {
        PostDto postDto = postService.get(postId);
        ResponseEntity<PostDto> response = new ResponseEntity<>(postDto, HttpStatus.CREATED);
        return response;
    }

    @PutMapping("/{postId}/{userId}")
    public ResponseEntity<PostDto> get(@PathVariable String postId, @PathVariable String userId) {
        PostDto postDto = postService.assignAuthor(postId, userId);
        ResponseEntity<PostDto> response = new ResponseEntity<>(postDto, HttpStatus.CREATED);
        return response;
    }
}
