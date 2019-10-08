package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.ReviewWithComments;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewWithCommentsRepository extends MongoRepository<ReviewWithComments, String> {

    Optional<ReviewWithComments> findByReviewId(int reviewId);

}