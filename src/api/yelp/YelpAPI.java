package api.yelp;

import api.Search.SearchCriteria;
import api.response.ExceptionResponse;
import api.response.MultipleRestaurantResponse;
import api.response.SingleRestaurantResponse;
import entity.Restaurant;
import okhttp3.Response;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class YelpAPI implements YelpApiServices {
    private static final YelpAPIClient yelpAPIClient = new YelpAPIClient(new YelpURLs());

    public ArrayList<Restaurant> getLocalRestaurants(SearchCriteria criteria) {
        yelpAPIClient.allRestaurantsMatching(criteria);
        MultipleRestaurantResponse multipleRestaurantResponse = new MultipleRestaurantResponse(yelpAPIClient.getResponseBody());
        return multipleRestaurantResponse.getRestaurants();
    }

    public Restaurant getRestaurantByID(String id) {
        yelpAPIClient.RestaurantIDMatching(id);
        SingleRestaurantResponse singleRestaurantResponse = new SingleRestaurantResponse(yelpAPIClient.getResponseBody());
        return singleRestaurantResponse.getRestaurant();
    }

//    public ArrayList<Review> getReviews(String id) {}
}