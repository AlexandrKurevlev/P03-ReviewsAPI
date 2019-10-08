package com.udacity.course3.reviews;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReviewsApplicationTests {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Test
	public void contextLoads() {
	}

	@Test
	public void test() {
		Product p1 = new Product();
		p1.setName("Test Product 1");
		p1.setDescription("Test Product 1 Description");
		p1 = productRepository.save(p1);
		Assert.assertEquals(1, p1.getId());

		Product p2 = new Product();
        p2.setName("Test Product 2");
        p2.setDescription("Test Product 2 Description");
		p2 = productRepository.save(p2);
		Assert.assertEquals(2, p2.getId());

		Product p1Found = productRepository.findById(p1.getId()).get();
		Assert.assertEquals(p1.getId(), p1Found.getId());
		Assert.assertEquals(p1.getName(), p1Found.getName());
		Assert.assertEquals(p1.getDescription(), p1Found.getDescription());

		List<Product> allProducts = productRepository.findAll();
		Assert.assertEquals(2, allProducts.size());

		Review r1 = new Review();
		r1.setProduct(p2);
		r1.setDescription("Test Review 1 Description");
		reviewRepository.save(r1);

		List<Review> allReviewsByProduct = reviewRepository.findAllByProduct(p2);
		Assert.assertEquals(1, allReviewsByProduct.size());
		Assert.assertEquals(r1.getDescription(), allReviewsByProduct.get(0).getDescription());
	}

}