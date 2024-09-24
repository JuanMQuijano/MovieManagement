package org.jquijano.proyects.MovieManagement.persistence.service.impl;

import org.jquijano.proyects.MovieManagement.dto.response.MovieSearchCriteria;
import org.jquijano.proyects.MovieManagement.dto.request.SaveMovie;
import org.jquijano.proyects.MovieManagement.dto.response.GetMovie;
import org.jquijano.proyects.MovieManagement.exception.ObjectNotFoundException;
import org.jquijano.proyects.MovieManagement.mapper.MovieMapper;
import org.jquijano.proyects.MovieManagement.persistence.entity.Movie;
import org.jquijano.proyects.MovieManagement.persistence.repository.MovieCrudRepository;
import org.jquijano.proyects.MovieManagement.persistence.service.MovieService;
import org.jquijano.proyects.MovieManagement.persistence.specification.FindAllMoviesSpecification;
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
    public List<GetMovie> findAll(MovieSearchCriteria movieSearchCriteria) {
        FindAllMoviesSpecification moviesSpecification = new FindAllMoviesSpecification(movieSearchCriteria);
        List<Movie> entities = movieCrudRepository.findAll(moviesSpecification);
        return MovieMapper.toGetDtoList(entities);
    }

//    @Override
//    @Transactional(readOnly = true)
//    public List<GetMovie> findAllByTitle(String title) {
//        List<Movie> entities = movieCrudRepository.findByTitleContaining(title);
//        return MovieMapper.toGetDtoList(entities);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<GetMovie> findAllByGenre(MovieGenre genre) {
//        List<Movie> entities = movieCrudRepository.findByGenre(genre);
//        return MovieMapper.toGetDtoList(entities);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<GetMovie> findAllByGenreAndTitle(MovieGenre genre, String title) {
//        List<Movie> entities = movieCrudRepository.findByGenreAndTitleContaining(genre, title);
//        return MovieMapper.toGetDtoList(entities);
//    }

    @Override
    @Transactional(readOnly = true)
    public GetMovie findOneById(Long id) {
        return MovieMapper.toGetDto(
                this.findOneEntityById(id)
        );
    }

    @Transactional(readOnly = true)
    private Movie findOneEntityById(Long id) {
        return movieCrudRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("[movie:" + Long.toString(id) + "]"));
    }

    @Override
    public GetMovie createOne(SaveMovie saveDto) {
        Movie newMovie = MovieMapper.toEntity(saveDto);

        return MovieMapper.toGetDto(movieCrudRepository.save(newMovie));
    }

    @Override
    public GetMovie updateOneById(Long id, SaveMovie saveDto) {
        Movie oldMovie = this.findOneEntityById(id);

        MovieMapper.updateEntity(oldMovie, saveDto);

        return MovieMapper.toGetDto(movieCrudRepository.save(oldMovie));
    }

    @Override
    public void deleteOneById(Long id) {
        Movie movie = this.findOneEntityById(id);

        movieCrudRepository.delete(movie);
    }


}
