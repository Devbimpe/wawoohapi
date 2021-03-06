package com.longbridge.services.implementations;

import com.longbridge.exception.WawoohException;
import com.longbridge.models.ProductRating;
import com.longbridge.models.Products;
import com.longbridge.models.User;
import com.longbridge.repository.OrderRepository;
import com.longbridge.repository.ProductRatingRepository;
import com.longbridge.repository.ProductRepository;
import com.longbridge.services.ProductRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Longbridge on 25/04/2018.
 */
@Service
public class ProductRatingServiceImpl implements ProductRatingService {
    @Autowired
    private ProductRatingRepository productRatingRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Boolean RateProduct(User user,Long productId, ProductRating productRating) {
        try {
//            Products products = productRepository.findOne(productId);
//            productRating.setUser(user);
//            productRating.setProducts(products);
//            productRatingRepository.save(productRating);

            if(orderRepository.noOfTimesOrdered(productId, user.id) > 0){
                Products products = productRepository.findOne(productId);
                productRating.setUser(user);
                productRating.setProducts(products);
                productRatingRepository.save(productRating);
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new WawoohException();
        }
    }

    @Override
    public void updateRating(ProductRating productRating) {
        try {
            ProductRating prodRat = productRatingRepository.findOne(productRating.id);

            if(prodRat != null){
                prodRat.setDeliveryTimeRating(productRating.getDeliveryTimeRating());
                prodRat.setProductQualityRating(productRating.getProductQualityRating());
                prodRat.setServiceRating(productRating.getServiceRating());
                prodRat.setSubject(productRating.getSubject());
                prodRat.setReview(productRating.getReview());

                productRatingRepository.save(prodRat);
            }
            else {
                throw new WawoohException();
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new WawoohException();
        }

    }

    @Override
    public ProductRating getUserRating(User user, Long id) {

        Products products = productRepository.findOne(id);

        if(products != null){
            ProductRating productRating = productRatingRepository.findByUserAndProducts(user, products);
            if(productRating == null){
                System.out.println("null");
            }else{
                productRating.setUser(null);
                return productRating;
            }
        }
        return null;
    }

    @Override
    public void verifyRating(User user, Long id) {
        try {
            ProductRating productRating = productRatingRepository.findOne(id);
            productRating.setUser(user);
            productRating.setVerifiedFlag("Y");
            productRatingRepository.save(productRating);
        }catch (Exception e){
            e.printStackTrace();
            throw new WawoohException();
        }
    }

    @Override
    public List<ProductRating> getVerifiedRatings() {
        try {
            return productRatingRepository.findByVerifiedFlag("Y");
        }catch (Exception e){
            e.printStackTrace();
            throw new WawoohException();
        }
    }

    @Override
    public List<ProductRating> getAllRatings() {
        try {
            return productRatingRepository.findAll();
        }catch (Exception e){
            e.printStackTrace();
            throw new WawoohException();
        }
    }
}
