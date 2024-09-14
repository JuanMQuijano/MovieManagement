package org.jquijano.proyects.MovieManagement.Controller;

import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<List<User>> findAll(@RequestParam(required = false) String name) {

        List<User> users = null;

        if (StringUtils.hasText(name)) {
            users = userService.findAllByName(name);
        } else {
            users = userService.findAll();
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<User> findOneByUsername(@PathVariable String username) {
        try {
            return ResponseEntity.ok(userService.findOneByUsername(username));
        } catch (ObjectNotFoundException e) {
//            return ResponseEntity.status(404).build();
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<User> createOne(@RequestBody User user, HttpServletRequest request) {
        User newUser = userService.createOne(user);

        String baseUrl = request.getRequestURL().toString();
        URI newLocation = URI.create(baseUrl + "/" + newUser.getUsername());
        return ResponseEntity
                .created(newLocation)
                .body(newUser);
    }

    @PutMapping(value = "/{username}")
    public ResponseEntity<User> updateOneByUsername(@PathVariable String username, @RequestBody User newUser) {
        try {
            return ResponseEntity.ok(userService.updateOneByUsername(username, newUser));
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


    @DeleteMapping
    public ResponseEntity<Void> deleteAll(){
        userService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
