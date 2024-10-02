package org.jquijano.proyects.MovieManagement.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.jquijano.proyects.MovieManagement.dto.response.GetMovieStatistic;
import org.jquijano.proyects.MovieManagement.dto.response.MovieSearchCriteria;
import org.jquijano.proyects.MovieManagement.dto.request.SaveMovie;
import org.jquijano.proyects.MovieManagement.dto.response.GetMovie;
import org.jquijano.proyects.MovieManagement.persistence.service.MovieService;
import org.jquijano.proyects.MovieManagement.persistence.service.RatingService;
import org.jquijano.proyects.MovieManagement.util.MovieGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private RatingService ratingService;

    @GetMapping
    public ResponseEntity<Page<GetMovie>> findAll(@RequestParam(required = false) String title,
                                                  @RequestParam(required = false) MovieGenre genre,
                                                  @RequestParam(required = false, name = "min_release_year") Integer minReleaseYear,
                                                  @RequestParam(required = false, name = "max_release_year") Integer maxReleaseYear,
                                                  @RequestParam(required = false, name = "min_average_rating") Integer minAverageRating,
                                                  Pageable moviePageable) {

        MovieSearchCriteria movieSearchCriteria = new MovieSearchCriteria(title, genre, minReleaseYear, maxReleaseYear, minAverageRating);
        Page<GetMovie> movies = movieService.findAll(movieSearchCriteria, moviePageable);

        return ResponseEntity.ok(movies);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GetMovieStatistic> findOneById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.findOneById(id));
    }

    @GetMapping(value = "/{id}/ratings")
    public ResponseEntity<Page<GetMovie.GetRating>> findAllRatingsForMovieId(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(ratingService.findAllByMovieId(id, pageable));
    }

    @PostMapping
    public ResponseEntity<GetMovie> createOne(@Valid @RequestBody SaveMovie saveDto,
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
                                                  @Valid @RequestBody SaveMovie saveDto) {
        GetMovie movieUpdated = movieService.updateOneById(id, saveDto);

        return ResponseEntity.ok(movieUpdated);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteOneById(@PathVariable Long id) {
        movieService.deleteOneById(id);
        return ResponseEntity.noContent().build();
    }

}