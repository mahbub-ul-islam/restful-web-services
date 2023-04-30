package com.learning.restfulwebservices.user.controller;

import com.learning.restfulwebservices.Post.model.Post;
import com.learning.restfulwebservices.Post.repository.PostRepository;
import com.learning.restfulwebservices.exceptions.UserNotFoundException;
import com.learning.restfulwebservices.user.dao.UserDaoService;
import com.learning.restfulwebservices.user.model.User;
import com.learning.restfulwebservices.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class UserJPAController {

    @Autowired
    private UserRepository repository;
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/users")
    public List<User> retrieveAllJpa() {
        return repository.findAll();
    }

    @GetMapping("/users/{userId}")
    public EntityModel<Optional<User>> retrieveUserJpa(@PathVariable Integer userId) {
        Optional<User> user = repository.findById(userId);
        if (!user.isPresent()) {
            throw new UserNotFoundException("Id: " + userId + " not found.");
        }

//        create a entityModel and add user
        EntityModel<Optional<User>> entityModel = EntityModel.of(user);
//        get the link from the class
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllJpa());
//        add the link to the entity model
        entityModel.add(link.withRel("all-users"));

        return entityModel;
    }


    @PostMapping("/users")
    public ResponseEntity<Object> createUserJpa(@Valid @RequestBody User user) {
        User saveduser = repository.save(user);

        URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(saveduser.getId())
                    .toUri();

        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUserJpa(@PathVariable int userId) {
        repository.deleteById(userId);
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> retrieveAllPostJpa(@PathVariable int id) {
        Optional<User> user = repository.findById(id);

        if (user.isEmpty()) {
            throw new UserNotFoundException("Id: " + id + " not found.");
        }

        return user.get().getPosts();
    }

    @PostMapping("/users/{userId}/post")
    public ResponseEntity<Object> createPostJpa(@PathVariable int userId, @Valid @RequestBody Post post) {
        Optional<User> userOptional = repository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("Id: " + userId + " not found.");
        }

        User user = userOptional.get();
        post.setUser(user);
        postRepository.save(post);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }
}
