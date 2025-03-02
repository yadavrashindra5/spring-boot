package com.orm.services;

import com.orm.dtos.PostDto;

public interface PostService {
    PostDto create(PostDto postDto);

    PostDto get(String postId);

    PostDto assignAuthor(String postId, String userId);
}
