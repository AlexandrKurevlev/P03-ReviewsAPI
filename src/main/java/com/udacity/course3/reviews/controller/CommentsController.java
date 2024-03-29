package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.entity.ReviewWithComments;
import com.udacity.course3.reviews.repository.ReviewRepository;
import com.udacity.course3.reviews.repository.ReviewWithCommentsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with comment entity.
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {

    private final ReviewRepository reviewRepository;
    private final ReviewWithCommentsRepository reviewWithCommentsRepository;

    public CommentsController(ReviewRepository reviewRepository, ReviewWithCommentsRepository reviewWithCommentsRepository) {
        this.reviewRepository = reviewRepository;
        this.reviewWithCommentsRepository = reviewWithCommentsRepository;
    }

    /**
     * Creates a comment for a review.
     *
     * 1. Add argument for comment entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, save comment.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.POST)
    public ResponseEntity<?> createCommentForReview(@PathVariable("reviewId") Integer reviewId, @RequestBody @Valid Comment comment) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        if (review.isPresent()) {
            Optional<ReviewWithComments> reviewWithCommentsOptional = reviewWithCommentsRepository.findByReviewId(reviewId);
            ReviewWithComments reviewWithComments;
            if (!reviewWithCommentsOptional.isPresent()) {
                reviewWithComments = new ReviewWithComments();
                reviewWithComments.setReviewId(reviewId);
            } else {
                reviewWithComments = reviewWithCommentsOptional.get();
            }
            comment.setReview(review.get());
            reviewWithComments.getComments().add(comment);
            reviewWithCommentsRepository.save(reviewWithComments);
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } else {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * List comments for a review.
     *
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, return list of comments.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.GET)
    public List<?> listCommentsForReview(@PathVariable("reviewId") Integer reviewId) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        if (review.isPresent()) {
            return reviewWithCommentsRepository.findByReviewId(reviewId).get().getComments();
        } else {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
        }
    }
}