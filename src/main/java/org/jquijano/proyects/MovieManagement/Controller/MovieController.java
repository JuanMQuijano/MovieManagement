package org.jquijano.proyects.MovieManagement.Controller;

import org.jquijano.proyects.MovieManagement.persistence.entity.Movie;
import org.jquijano.proyects.MovieManagement.persistence.service.MovieService;
import org.jquijano.proyects.MovieManagement.util.MovieGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Movie> findAll(@RequestParam(required = false) String title, @RequestParam(required = false) MovieGenre genre) {

        List<Movie> movies = null;

        if (StringUtils.hasText(title) && genre != null) {
            movies = movieService.findAllByGenreAndTitle(genre, title);
        } else if (StringUtils.hasText(title)) {
            movies = movieService.findAllByTitle(title);
        } else if (genre != null) {
            movies = movieService.findAllByGenre(genre);
        } else {
            movies = movieService.findAll();
        }

        return movies;
    }

//    @RequestMapping(method = RequestMethod.GET, params = {"genre", "title"})
//    public List<Movie> findAllByGenreAndTitle(@RequestParam String title, @RequestParam MovieGenre genre) {
//        return movieService.findAllByGenreAndTitle(genre, title);
//    }
//
//    @RequestMapping(method = RequestMethod.GET, params = {"title"})
//    public List<Movie> findAllByTitle(@RequestParam String title) {
//        return movieService.findAllByTitle(title);
//    }
//
//    @RequestMapping(method = RequestMethod.GET, params = {"genre"})
//    public List<Movie> findAllByGenre(@RequestParam MovieGenre genre) {
//        return movieService.findAllByGenre(genre);
//    }
//
//    @RequestMapping(method = RequestMethod.GET, params = {"!genre", "!title"})
//    public List<Movie> findAll() {
//        return movieService.findAll();
//    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Movie findOneById(@PathVariable Long id) {
        return movieService.findOneById(id);
    }

}