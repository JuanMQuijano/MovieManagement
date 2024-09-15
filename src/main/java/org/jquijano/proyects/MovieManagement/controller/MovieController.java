package org.jquijano.proyects.MovieManagement.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.jquijano.proyects.MovieManagement.dto.request.SaveMovie;
import org.jquijano.proyects.MovieManagement.dto.response.GetMovie;
import org.jquijano.proyects.MovieManagement.exception.ObjectNotFoundException;
import org.jquijano.proyects.MovieManagement.persistence.entity.Movie;
import org.jquijano.proyects.MovieManagement.persistence.service.MovieService;
import org.jquijano.proyects.MovieManagement.util.MovieGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<List<GetMovie>> findAll(@RequestParam(required = false) String title, @RequestParam(required = false) MovieGenre genre) {

        List<GetMovie> movies = null;

        if (StringUtils.hasText(title) && genre != null) {
            movies = movieService.findAllByGenreAndTitle(genre, title);
        } else if (StringUtils.hasText(title)) {
            movies = movieService.findAllByTitle(title);
        } else if (genre != null) {
            movies = movieService.findAllByGenre(genre);
        } else {
            movies = movieService.findAll();
        }

        /*
            Opción 1
            return new ResponseEntity(movies, HttpStatus.OK);

            Opción 2
            return ResponseEntity.status(HttpStatus.OK).body(movies);
        */

        return ResponseEntity.ok(movies);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GetMovie> findOneById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(movieService.findOneById(id));
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<GetMovie> createOne(@RequestBody SaveMovie saveDto,
                                              HttpServletRequest httpServletRequest) {

        GetMovie movieCreated = movieService.createOne(saveDto);

        String baseUrl = httpServletRequest.getRequestURL().toString();
        URI newLocation = URI.create(baseUrl + "/" + movieCreated.id());
        return ResponseEntity
                .created(newLocation)
                .body(movieCreated);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<GetMovie> updateOneById(@PathVariable Long id,
                                                  @RequestBody SaveMovie saveDto) {
        try {
            GetMovie movieUpdated = movieService.updateOneById(id, saveDto);

            return ResponseEntity.ok(movieUpdated);
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteOneById(@PathVariable Long id) {
        try {
            movieService.deleteOneById(id);
            return ResponseEntity.noContent().build();
        } catch (ObjectNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}