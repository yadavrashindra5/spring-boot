package com.orm.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
@JsonIgnoreProperties({"posts"})
public class User {
    @Id
    private String id;
    private String name;
    @OneToMany(mappedBy = "author")
    @JsonManagedReference
    private List<Post> posts;
}
