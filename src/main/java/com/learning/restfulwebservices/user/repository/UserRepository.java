package com.learning.restfulwebservices.user.repository;

import com.learning.restfulwebservices.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
