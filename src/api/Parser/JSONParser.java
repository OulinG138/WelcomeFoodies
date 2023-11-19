package api.Parser;

import entity.Restaurant;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONParser implements Parser {
    @Override
    public ArrayList<Restaurant> parseMultipleRestaurants(String response) {
        return MultipleRestaurantsParser.parseFrom(new JSONObject(response));
    }

    @Override
    public Restaurant parseSingleRestaurant(String response) {
        return SingleRestaurantParser.parseFrom(new JSONObject(response));
    }

//    @Override
//    public ArrayList<Review> parseReviews(String response) {
//        return ReviewsParser.parseFrom(new JSONObject(response));
//    }

    @Override
    public String parseError(String response) {
        return ErrorParser.parseFrom(new JSONObject(response));
    }
}
