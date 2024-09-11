package org.jquijano.proyects.MovieManagement.persistence.service.impl;

import org.jquijano.proyects.MovieManagement.exception.ObjectNotFoundException;
import org.jquijano.proyects.MovieManagement.persistence.entity.Rating;
import org.jquijano.proyects.MovieManagement.persistence.repository.RatingCrudRepository;
import org.jquijano.proyects.MovieManagement.persistence.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingCrudRepository ratingCrudRepository;

    @Override
    public List<Rating> findAll() {
        return ratingCrudRepository.findAll();
    }

    @Override
    public List<Rating> findAllByMovieId(Long id) {
        return ratingCrudRepository.findByMovieId(id);
    }

    @Override
    public List<Rating> findAllByUsername(String username) {
        return ratingCrudRepository.findByUsername(username);
    }

    @Override
    public Rating findOneById(Long id) {
        return ratingCrudRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("[rating:" + Long.toString(id) + "]"));
    }

    @Override
    public Rating createOne(Rating rating) {
        return ratingCrudRepository.save(rating);
    }

    @Override
    public Rating updateOneById(Long id, Rating newRating) {
        Rating oldRating = this.findOneById(id);

        oldRating.setUserId(newRating.getUserId());
        oldRating.setMovieId(newRating.getMovieId());

        return ratingCrudRepository.save(oldRating);
    }

    @Override
    public void deleteOneById(Long id) {
        Rating rating = this.findOneById(id);
        ratingCrudRepository.delete(rating);

        /*
           if(ratingCrudRepository.existsById(id)){
                ratingCrudRepository.deleteById(id);
                return;
           }

           throw new ObjectNotFoundException("[rating:" + Long.toString(id) + "]"));
        */
    }
}
