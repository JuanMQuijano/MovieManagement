package org.jquijano.proyects.MovieManagement.persistence.service.impl;

import org.jquijano.proyects.MovieManagement.dto.response.GetMovieStatistic;
import org.jquijano.proyects.MovieManagement.dto.response.MovieSearchCriteria;
import org.jquijano.proyects.MovieManagement.dto.request.SaveMovie;
import org.jquijano.proyects.MovieManagement.dto.response.GetMovie;
import org.jquijano.proyects.MovieManagement.exception.ObjectNotFoundException;
import org.jquijano.proyects.MovieManagement.mapper.MovieMapper;
import org.jquijano.proyects.MovieManagement.persistence.entity.Movie;
import org.jquijano.proyects.MovieManagement.persistence.repository.MovieCrudRepository;
import org.jquijano.proyects.MovieManagement.persistence.repository.RatingCrudRepository;
import org.jquijano.proyects.MovieManagement.persistence.service.MovieService;
import org.jquijano.proyects.MovieManagement.persistence.specification.FindAllMoviesSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieCrudRepository movieCrudRepository;

    @Autowired
    private RatingCrudRepository ratingCrudRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<GetMovie> findAll(MovieSearchCriteria movieSearchCriteria, Pageable pageable) {
        FindAllMoviesSpecification moviesSpecification = new FindAllMoviesSpecification(movieSearchCriteria);
        Page<Movie> entities = movieCrudRepository.findAll(moviesSpecification, pageable);

        return entities.map(entity -> MovieMapper.toGetDto(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public GetMovieStatistic findOneById(Long id) {
        double avg = ratingCrudRepository.getAvgRatingByMovieId(id);
        int lowest = ratingCrudRepository.getMinRatingByMovieId(id);
        int max = ratingCrudRepository.getMaxRatingByMovieId(id);
        System.out.println(avg);
        System.out.println(lowest);
        System.out.println(max);
        return MovieMapper.toGetMovieStatisticDto(
                this.findOneEntityById(id), avg, lowest, max
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
