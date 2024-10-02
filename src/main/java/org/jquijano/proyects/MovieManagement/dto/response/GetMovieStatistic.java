package org.jquijano.proyects.MovieManagement.dto.response;

import org.jquijano.proyects.MovieManagement.util.MovieGenre;

import java.io.Serializable;

public record GetMovieStatistic(
        Long id,
        String title,
        String director,
        MovieGenre genre,
        int totalRatings,
        int releaseYear,
        double averageRatings,
        int lowestRating,
        int highestRating
) implements Serializable {
}
