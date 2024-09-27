package org.jquijano.proyects.MovieManagement.mapper;

import org.jquijano.proyects.MovieManagement.dto.request.SaveRating;
import org.jquijano.proyects.MovieManagement.dto.response.GetCompleteRating;
import org.jquijano.proyects.MovieManagement.dto.response.GetMovie;
import org.jquijano.proyects.MovieManagement.dto.response.GetUser;
import org.jquijano.proyects.MovieManagement.persistence.entity.Rating;

import java.util.List;

public class RatingMapper {

    public static GetCompleteRating toGetCompleteRatingDto(Rating entity) {
        if (entity == null) return null;

        String movieTitle = entity.getMovie() != null ? entity.getMovie().getTitle() : null;
        String username = entity.getUser() != null ? entity.getUser().getUsername() : null;

        return new GetCompleteRating(entity.getId(), entity.getMovieId(), movieTitle, username, entity.getRating());
    }

    public static List<GetCompleteRating> toGetCompleteRatingDtoList(List<Rating> entities) {
        if (entities == null) return null;

        return entities.stream().map(RatingMapper::toGetCompleteRatingDto).toList();
    }

    public static Rating toEntity(SaveRating entity, Long userId) {
        if (entity == null) return null;

        Rating rating = new Rating();
        rating.setMovieId(entity.movieId());
        rating.setUserId(userId);
        rating.setRating(entity.rating());

        return rating;
    }

    public static GetMovie.GetRating toGetMovieRatingDto(Rating entity) {
        if (entity == null) return null;

        String username = entity.getUser() != null ? entity.getUser().getUsername() : null;

        return new GetMovie.GetRating(entity.getId(), entity.getRating(), username);
    }

    public static GetUser.GetRating toGetUserRatingDto(Rating entity) {
        if (entity == null) return null;

        String movieTitle = entity.getMovie() != null ? entity.getMovie().getTitle() : null;

        return new GetUser.GetRating(entity.getId(), entity.getMovieId(), movieTitle, entity.getRating());
    }

    public static List<GetMovie.GetRating> toGetMovieRatingDtoList(List<Rating> entities) {
        if (entities == null) return null;

        return entities.stream().map(RatingMapper::toGetMovieRatingDto).toList();
    }

    public static List<GetUser.GetRating> toGetUserRatingDtoList(List<Rating> entities) {
        if (entities == null) return null;

        return entities.stream().map(RatingMapper::toGetUserRatingDto).toList();
    }

    public static void updateEntity(Rating oldRating, SaveRating newRating, Long userId) {
        if (oldRating == null || newRating == null) return;

        oldRating.setUserId(userId);
        oldRating.setMovieId(newRating.movieId());
        oldRating.setRating(newRating.rating());
    }
}
