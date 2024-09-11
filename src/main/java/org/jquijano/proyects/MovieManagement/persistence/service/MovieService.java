package org.jquijano.proyects.MovieManagement.persistence.service;

import org.jquijano.proyects.MovieManagement.persistence.entity.Movie;
import org.jquijano.proyects.MovieManagement.util.MovieGenre;

import java.util.List;

public interface MovieService {

    List<Movie> findAll();

    List<Movie> findAllByTitle(String title);

    List<Movie> findAllByGenre(MovieGenre genre);

    List<Movie> findAllByGenreAndTitle(MovieGenre genre, String title);

    Movie createOne(Movie movie);

    Movie updateOneById(Long id, Movie movie);

    void deleteOneById(Long id);

    Movie findOneById(Long id);
}
