package com.learning.restfulwebservices.Post.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.learning.restfulwebservices.user.model.User;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;
}
