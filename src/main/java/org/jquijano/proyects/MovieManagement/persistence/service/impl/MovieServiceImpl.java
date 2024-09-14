package org.jquijano.proyects.MovieManagement.persistence.service.impl;

import org.jquijano.proyects.MovieManagement.exception.ObjectNotFoundException;
import org.jquijano.proyects.MovieManagement.persistence.entity.Movie;
import org.jquijano.proyects.MovieManagement.persistence.repository.MovieCrudRepository;
import org.jquijano.proyects.MovieManagement.persistence.service.MovieService;
import org.jquijano.proyects.MovieManagement.util.MovieGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieCrudRepository movieCrudRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Movie> findAll() {
        return movieCrudRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Movie> findAllByTitle(String title) {
        return movieCrudRepository.findByTitleContaining(title);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Movie> findAllByGenre(MovieGenre genre) {
        return movieCrudRepository.findByGenre(genre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Movie> findAllByGenreAndTitle(MovieGenre genre, String title) {
        return movieCrudRepository.findByGenreAndTitleContaining(genre, title);
    }

    @Override
    public Movie createOne(Movie movie) {
        return movieCrudRepository.save(movie);
    }

    @Override
    public Movie updateOneById(Long id, Movie newMovie) {
        Movie oldMovie = this.findOneById(id);

        oldMovie.setGenre(newMovie.getGenre());
        oldMovie.setReleaseYear(newMovie.getReleaseYear());
        oldMovie.setTitle(newMovie.getTitle());
        oldMovie.setDirector(newMovie.getDirector());

        return movieCrudRepository.save(oldMovie);
    }

    @Override
    public void deleteOneById(Long id) {
        Movie movie = this.findOneById(id);

        movieCrudRepository.delete(movie);
    }

    @Override
    @Transactional(readOnly = true)
    public Movie findOneById(Long id) {
        return movieCrudRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("[movie:" + Long.toString(id) + "]"));
    }
}
