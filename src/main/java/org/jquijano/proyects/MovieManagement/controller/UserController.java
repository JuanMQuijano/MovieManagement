package org.jquijano.proyects.MovieManagement.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.jquijano.proyects.MovieManagement.dto.request.SaveUser;
import org.jquijano.proyects.MovieManagement.dto.response.GetUser;
import org.jquijano.proyects.MovieManagement.exception.ObjectNotFoundException;
import org.jquijano.proyects.MovieManagement.persistence.entity.User;
import org.jquijano.proyects.MovieManagement.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<GetUser>> findAll(@RequestParam(required = false) String name) {

        List<GetUser> users = null;

        if (StringUtils.hasText(name)) {
            users = userService.findAllByName(name);
        } else {
            users = userService.findAll();
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<GetUser> findOneByUsername(@PathVariable String username) {
        try {
            return ResponseEntity.ok(userService.findOneByUsername(username));
        } catch (ObjectNotFoundException e) {
//            return ResponseEntity.status(404).build();
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<GetUser> createOne(@RequestBody SaveUser saveDto, HttpServletRequest request) {
        GetUser newUser = userService.createOne(saveDto);

        String baseUrl = request.getRequestURL().toString();
        URI newLocation = URI.create(baseUrl + "/" + newUser.username());
        return ResponseEntity
                .created(newLocation)
                .body(newUser);
    }

    @PutMapping(value = "/{username}")
    public ResponseEntity<GetUser> updateOneByUsername(@PathVariable String username, @RequestBody SaveUser saveDto) {
        try {
            return ResponseEntity.ok(userService.updateOneByUsername(username, saveDto));
        } catch (ObjectNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Void> deleteOneByUsername(@PathVariable String username) {
        try {
            userService.deleteOneByUsername(username);
            return ResponseEntity.noContent().build();
        } catch (ObjectNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
