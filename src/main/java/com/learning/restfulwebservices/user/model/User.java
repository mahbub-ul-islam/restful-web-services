package com.learning.restfulwebservices.user.model;

import lombok.*;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    private Integer id;

    @Size(min = 2, message = "Name Should Have At Least 2 Character.")
    private String name;

    @Past(message = "Birthdate Should be in past.")
    private Date birthDate;
}
