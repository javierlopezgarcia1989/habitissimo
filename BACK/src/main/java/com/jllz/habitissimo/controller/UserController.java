package com.jllz.habitissimo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    /**
     * name: checkEmailUser
     *
     * El servicio devolverá si el email del usuario es correcto
     *
     * @param email
     * @return ResponseEntity
     */
    @GetMapping(value = "/checkEmailUser/{email}")
    public ResponseEntity getSubcategories(@PathVariable("email") String email){

        try {
            if (null != email && !email.contains("hotmail")) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity( HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
