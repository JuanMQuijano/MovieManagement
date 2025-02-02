package org.jquijano.proyects.MovieManagement.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.jquijano.proyects.MovieManagement.dto.request.SaveUser;
import org.jquijano.proyects.MovieManagement.dto.response.GetUser;
import org.jquijano.proyects.MovieManagement.dto.response.GetUserStatistic;
import org.jquijano.proyects.MovieManagement.dto.response.UserSearchCriteria;
import org.jquijano.proyects.MovieManagement.exception.ObjectNotFoundException;
import org.jquijano.proyects.MovieManagement.persistence.entity.User;
import org.jquijano.proyects.MovieManagement.persistence.service.RatingService;
import org.jquijano.proyects.MovieManagement.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    private RatingService ratingService;

    @GetMapping
    public ResponseEntity<Page<GetUser>> findAll(@RequestParam(required = false) String name,
                                                 @RequestParam(required = false) String username,
                                                 Pageable pageable) {
        UserSearchCriteria userSearchCriteria = new UserSearchCriteria(name, username);
        Page<GetUser> users = userService.findAll(userSearchCriteria, pageable);

        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<GetUserStatistic> findOneByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.findOneByUsername(username));
    }

    @GetMapping(value = "/{username}/ratings")
    public ResponseEntity<Page<GetUser.GetRating>> findOneByUsername(@PathVariable String username, Pageable pageable) {
        return ResponseEntity.ok(ratingService.findAllByUsername(username, pageable));
    }

    @PostMapping
    public ResponseEntity<GetUser> createOne(@Valid @RequestBody SaveUser saveDto, HttpServletRequest request) {
        GetUser newUser = userService.createOne(saveDto);

        String baseUrl = request.getRequestURL().toString();
        URI newLocation = URI.create(baseUrl + "/" + newUser.username());
        return ResponseEntity
                .created(newLocation)
                .body(newUser);
    }

    @PutMapping(value = "/{username}")
    public ResponseEntity<GetUser> updateOneByUsername(@PathVariable String username, @Valid @RequestBody SaveUser saveDto) {
        return ResponseEntity.ok(userService.updateOneByUsername(username, saveDto));
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Void> deleteOneByUsername(@PathVariable String username) {
        userService.deleteOneByUsername(username);
        return ResponseEntity.noContent().build();
    }
}
