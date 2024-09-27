package org.jquijano.proyects.MovieManagement.persistence.service.impl;

import jakarta.persistence.EntityManager;
import org.jquijano.proyects.MovieManagement.dto.request.SaveRating;
import org.jquijano.proyects.MovieManagement.dto.response.GetCompleteRating;
import org.jquijano.proyects.MovieManagement.exception.ObjectNotFoundException;
import org.jquijano.proyects.MovieManagement.exception.RatingDuplicatedException;
import org.jquijano.proyects.MovieManagement.mapper.RatingMapper;
import org.jquijano.proyects.MovieManagement.persistence.entity.Rating;
import org.jquijano.proyects.MovieManagement.persistence.entity.User;
import org.jquijano.proyects.MovieManagement.persistence.repository.RatingCrudRepository;
import org.jquijano.proyects.MovieManagement.persistence.service.RatingService;
import org.jquijano.proyects.MovieManagement.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingCrudRepository ratingCrudRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public Page<GetCompleteRating> findAll(Pageable pageable) {
        return ratingCrudRepository.findAll(pageable).map(RatingMapper::toGetCompleteRatingDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GetCompleteRating> findAllByMovieId(Long id, Pageable pageable) {
        return ratingCrudRepository.findByMovieId(id, pageable).map(RatingMapper::toGetCompleteRatingDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GetCompleteRating> findAllByUsername(String username, Pageable pageable) {
        return ratingCrudRepository.findByUsername(username, pageable).map(RatingMapper::toGetCompleteRatingDto);
    }

    @Override
    @Transactional(readOnly = true)
    public GetCompleteRating findOneById(Long id) {
        return RatingMapper.toGetCompleteRatingDto(this.findOneEntityById(id));
    }

    @Override
    public GetCompleteRating createOne(SaveRating rating) {

        boolean ratingExists = ratingCrudRepository.existsByMovieIdAndUserUsername(rating.movieId(), rating.username());

        if(ratingExists) throw new RatingDuplicatedException(rating.username(), rating.movieId());
//        Long ratingId = ratingCrudRepository.getRatingIdByMovieIdAndUserUsername(rating.movieId(), rating.username());
//        if (ratingId != null && ratingId.longValue() > 0) {
//            return this.updateOneById(ratingId, rating);
//        }

        User userEntity = userService.findOneEntityByUsername(rating.username());

        Rating entity = RatingMapper.toEntity(rating, userEntity.getId());
        ratingCrudRepository.save(entity);

        entityManager.detach(entity);

        return ratingCrudRepository.findById(entity.getId()).map(RatingMapper::toGetCompleteRatingDto).get();
    }

    private Rating findOneEntityById(Long id) {
        return ratingCrudRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("[rating:" + Long.toString(id) + "]"));
    }

    @Override
    public GetCompleteRating updateOneById(Long id, SaveRating newRating) {
        Rating oldRating = this.findOneEntityById(id);
        User userEntity = userService.findOneEntityByUsername(newRating.username());
        RatingMapper.updateEntity(oldRating, newRating, userEntity.getId());

        return RatingMapper.toGetCompleteRatingDto(ratingCrudRepository.save(oldRating));
    }

    @Override
    public void deleteOneById(Long id) {
        Rating rating = this.findOneEntityById(id);
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
