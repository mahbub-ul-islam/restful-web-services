package com.learning.restfulwebservices.Post.repository;

import com.learning.restfulwebservices.Post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
