package com.orm.dtos;

import com.orm.entities.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {
    private String id;
    private String title;
    private String content;
    private User author;
}
