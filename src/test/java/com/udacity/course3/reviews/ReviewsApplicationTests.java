package com.udacity.course3.reviews;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReviewsApplicationTests {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Test
	public void contextLoads() {
	}

	@Test
	public void test() {
		Product p1 = new Product();
		p1 = productRepository.save(p1);
		Assert.assertEquals(1, p1.getId());

		Product p2 = new Product();
		p2 = productRepository.save(p2);
		Assert.assertEquals(2, p2.getId());

		Product p1Found = productRepository.findById(p1.getId()).get();
		Assert.assertEquals(p1.getId(), p1Found.getId());

		List<Product> allProducts = productRepository.findAll();
		Assert.assertEquals(2, allProducts.size());

		Review r1 = new Review();
		r1.setProduct(p2);
		reviewRepository.save(r1);

		List<Review> allReviewsByProduct = reviewRepository.findAllByProduct(p2);
		Assert.assertEquals(1, allReviewsByProduct.size());

		Comment c1 = new Comment();
		c1.setReview(r1);
		commentRepository.save(c1);

		List<Comment> allCommentsByReview = commentRepository.findAllByReview(r1);
		Assert.assertEquals(1, allCommentsByReview.size());
	}

}