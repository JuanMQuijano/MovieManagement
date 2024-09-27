package org.jquijano.proyects.MovieManagement.exception;

public class RatingDuplicatedException extends RuntimeException {

    private String username;
    private Long movieId;

    public RatingDuplicatedException(String username, Long movieId) {
        this.username = username;
        this.movieId = movieId;
    }

    public RatingDuplicatedException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Rating already submitted for movie with ID " + movieId + " by user " + username + ". Only one rating per user per movie is allowed";
    }
}
