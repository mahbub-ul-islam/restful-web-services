package com.learning.restfulwebservices.user.controller;

import com.learning.restfulwebservices.user.dao.UserDaoService;
import com.learning.restfulwebservices.exceptions.UserNotFoundException;
import com.learning.restfulwebservices.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {

    @Autowired
     private UserDaoService service;

    @GetMapping("/users")
    public List<User> retrieveAll() {
        return service.findAll();
    }

    @GetMapping("/users/{userId}")
    public EntityModel<User> retrieveUser(@PathVariable int userId) {
        User user = service.findOne(userId);
        if (user == null) {
            throw new UserNotFoundException("Id: " + userId + " not found.");
        }

//        create a entityModel and add user
        EntityModel<User> entityModel = EntityModel.of(user);
//        get the link from the class
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAll());
//        add the link to the entity model
        entityModel.add(link.withRel("all-users"));

        return entityModel;
    }


    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User saveduser = service.save(user);

        URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(saveduser.getId())
                    .toUri();

        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable int userId) {
        User user = service.deleteById(userId);
        if (user == null) {
            throw new UserNotFoundException("Id: " + userId + " not found.");
        }
    }
}
