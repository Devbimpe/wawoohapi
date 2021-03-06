package com.longbridge.services;

import com.longbridge.models.ProductRating;
import com.longbridge.models.User;

import java.util.List;

/**
 * Created by Longbridge on 25/04/2018.
 */
public interface ProductRatingService {
    Boolean RateProduct (User user, Long id, ProductRating productRating);
    void updateRating(ProductRating productRating);
    ProductRating getUserRating(User user ,Long id);
    void verifyRating (User user, Long id);
    List<ProductRating> getVerifiedRatings();
    List<ProductRating> getAllRatings();
}
