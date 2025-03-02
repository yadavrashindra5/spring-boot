package com.orm.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.orm.entities.Post;
import com.orm.entities.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String id;
    private String name;
    private List<Post> posts;
}
