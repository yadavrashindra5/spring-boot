package com.orm.services.impl;

import com.orm.dtos.PostDto;
import com.orm.entities.Post;
import com.orm.entities.User;
import com.orm.repositories.PostRepository;
import com.orm.repositories.UserRepository;
import com.orm.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    public ModelMapper modelMapper;

    @Autowired
    public PostRepository postRepository;

    @Autowired
    public UserRepository userRepository;

    @Override
    public PostDto create(PostDto postDto) {
        postDto.setId(UUID.randomUUID().toString());
        Post post = modelMapper.map(postDto, Post.class);
        Post savedPost = postRepository.save(post);
        return modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public PostDto get(String postid) {
        Post post = postRepository.findById(postid).orElseThrow(() -> new RuntimeException("post not found with the given id"));
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostDto assignAuthor(String postId, String userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("post not found with the given id"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
        user.getPosts().add(post);
        post.setAuthor(user);
        userRepository.save(user);
        Post savedDto = postRepository.save(post);
        return modelMapper.map(savedDto, PostDto.class);
    }
}
