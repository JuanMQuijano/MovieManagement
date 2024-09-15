package org.jquijano.proyects.MovieManagement.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public record GetUser(
        String username,
        String name,
        List<GetRating> ratings
) implements Serializable {

    public static record GetRating(
            Long id,
            @JsonProperty(value = "movie_id")
            Long movieId,
            @JsonProperty(value = "movie_title")
            String movieTitle,
            int rating
    ) implements Serializable {
    }

}
