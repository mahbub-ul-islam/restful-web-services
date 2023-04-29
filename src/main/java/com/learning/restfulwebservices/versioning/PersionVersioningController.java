package com.learning.restfulwebservices.versioning;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersionVersioningController {

    @GetMapping("/v1/person")
    public PersonV1 personV1() {
        return new PersonV1("Bob Charlie");
    }

    @GetMapping("/v2/person")
    public PersonV2 personV2() {
        return new PersonV2(new Name("Bob", "Charlie"));
    }
}
