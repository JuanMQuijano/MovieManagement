package org.jquijano.proyects.MovieManagement.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jquijano.proyects.MovieManagement.dto.response.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            Exception.class,
            ObjectNotFoundException.class,
            InvalidPasswordException.class,
            MethodArgumentTypeMismatchException.class,
            MethodArgumentNotValidException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpMessageNotReadableException.class
    })
    public ResponseEntity<ApiError> handleGenericException(Exception exception, HttpServletRequest request, HttpServletResponse response) {
        LocalDateTime timestamps = LocalDateTime.now();

        if (exception instanceof ObjectNotFoundException objectNotFoundException) {
            return this.handleObjectNotFoundException(objectNotFoundException, request, response, timestamps);
        } else if (exception instanceof InvalidPasswordException invalidPasswordException) {
            return this.handleInvalidPasswordException(invalidPasswordException, request, response, timestamps);
        } else if (exception instanceof MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {
            return this.handleMethodArgumentTypeMismatchException(methodArgumentTypeMismatchException, request, response, timestamps);
        } else if (exception instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
            return this.handleMethodArgumentNotValidException(methodArgumentNotValidException, request, response, timestamps);
        } else if (exception instanceof HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException) {
            return this.handleHttpRequestMethodNotSupportedException(httpRequestMethodNotSupportedException, request, response, timestamps);
        } else if (exception instanceof HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException) {
            return this.handleHttpMediaTypeNotSupportedException(httpMediaTypeNotSupportedException, request, response, timestamps);
        } else if (exception instanceof HttpMessageNotReadableException httpMessageNotReadableException) {
            return this.handleHttpMessageNotReadableException(httpMessageNotReadableException, request, response, timestamps);
        } else if (exception instanceof RatingDuplicatedException ratingDuplicatedException) {
            return this.handleRatingDuplicatedException(ratingDuplicatedException, request, response, timestamps);
        } else {
            return this.handleException(exception, request, response, timestamps);
        }
    }

    private ResponseEntity<ApiError> handleHttpMessageNotReadableException(HttpMessageNotReadableException httpMessageNotReadableException, HttpServletRequest request, HttpServletResponse response, LocalDateTime timestamps) {

        int status = HttpStatus.BAD_REQUEST.value();

        ApiError apiError = new ApiError(
                status,
                request.getRequestURL().toString(),
                request.getMethod(),
                "Oops! Error reading the HTTP message body  ",
                httpMessageNotReadableException.getMessage(),
                timestamps,
                null
        );

        return ResponseEntity.status(status).body(apiError);


    }

    private ResponseEntity<ApiError> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException, HttpServletRequest request, HttpServletResponse response, LocalDateTime timestamps) {

        int status = HttpStatus.UNSUPPORTED_MEDIA_TYPE.value();

        ApiError apiError = new ApiError(
                status,
                request.getRequestURL().toString(),
                request.getMethod(),
                "Unsupported Media Type: The server is unable to process the requested entity in the format provided in the request",
                httpMediaTypeNotSupportedException.getMessage(),
                timestamps,
                null
        );

        return ResponseEntity.status(status).body(apiError);

    }

    private ResponseEntity<ApiError> handleObjectNotFoundException(ObjectNotFoundException objectNotFoundException, HttpServletRequest request, HttpServletResponse response, LocalDateTime timestamps) {

        int httpStatus = HttpStatus.NOT_FOUND.value();

        ApiError apiError = new ApiError(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "Oops!, Object Not Found.",
                objectNotFoundException.getMessage(),
                timestamps,
                null
        );

        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleInvalidPasswordException(InvalidPasswordException invalidPasswordException, HttpServletRequest request, HttpServletResponse response, LocalDateTime timestamps) {

        int httpStatus = HttpStatus.BAD_REQUEST.value();

        ApiError apiError = new ApiError(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "Oops!, Invalid Password, does not meet the required criteria, " + invalidPasswordException.getErrorDescription(),
                invalidPasswordException.getMessage(),
                timestamps,
                null
        );

        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleException(Exception exception, HttpServletRequest request, HttpServletResponse response, LocalDateTime timestamps) {
        int httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();

        ApiError apiError = new ApiError(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "Oops!, Something went wrong on our server. Please try again later.",
                exception.getMessage(),
                timestamps,
                null
        );

        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException, HttpServletRequest request, HttpServletResponse response, LocalDateTime timestamps) {
        int httpStatus = HttpStatus.BAD_REQUEST.value();

        ApiError apiError = new ApiError(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "Invalid Request: The provided value " + methodArgumentTypeMismatchException.getValue() + " does not have the expected data type for the " + methodArgumentTypeMismatchException.getName() + ".",
                methodArgumentTypeMismatchException.getMessage(),
                timestamps,
                null
        );

        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException, HttpServletRequest request, HttpServletResponse response, LocalDateTime timestamps) {

        int status = HttpStatus.BAD_REQUEST.value();

        List<ObjectError> errors = methodArgumentNotValidException.getAllErrors();
        List<String> details = errors.stream().map(error -> {
            if (error instanceof FieldError fieldError) {
                return fieldError.getField().concat(": ").concat(fieldError.getDefaultMessage());
            }

            return error.getDefaultMessage();
        }).toList();

        ApiError apiError = new ApiError(
                status,
                request.getRequestURL().toString(),
                request.getMethod(),
                "The request contains invalid or incomplete parameters. Please verify and provide the required information before trying again",
                methodArgumentNotValidException.getMessage(),
                timestamps,
                details
        );

        return ResponseEntity.status(status).body(apiError);

    }

    private ResponseEntity<ApiError> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException, HttpServletRequest request, HttpServletResponse response, LocalDateTime timestamps) {

        int status = HttpStatus.METHOD_NOT_ALLOWED.value();

        ApiError apiError = new ApiError(
                status,
                request.getRequestURL().toString(),
                request.getMethod(),
                "Oops! Method not allowed, check your http request",
                httpRequestMethodNotSupportedException.getMessage(),
                timestamps,
                null
        );

        return ResponseEntity.status(status).body(apiError);

    }

    private ResponseEntity<ApiError> handleRatingDuplicatedException(RatingDuplicatedException ratingDuplicatedException, HttpServletRequest request, HttpServletResponse response, LocalDateTime timestamps) {
        int status = HttpStatus.CONFLICT.value();

        ApiError apiError = new ApiError(
                status,
                request.getRequestURL().toString(),
                request.getMethod(),
                ratingDuplicatedException.getMessage(),
                ratingDuplicatedException.getMessage(),
                timestamps,
                null
        );

        return ResponseEntity.status(status).body(apiError);

    }
}
