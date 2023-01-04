package visitor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import platform.actions.Action;
import platform.User;
import platform.movies.Movie;

import java.util.ArrayList;
import java.util.List;

public interface Visitable {
    void acceptChangePage(
            Visitor visitor,
            Action action,
            ObjectNode jsonObject,
            ObjectMapper objectMapper
    ) throws JsonProcessingException;

    void acceptLogin(
            Visitor visitor,
            User.Credentials credentials,
            ObjectNode jsonObject,
            ObjectMapper objectMapper
    ) throws JsonProcessingException;

    void acceptRegister(
            Visitor visitor,
            User.Credentials credentials,
            ObjectNode jsonObject,
            ObjectMapper objectMapper
    ) throws JsonProcessingException;

    void acceptSearch(
            Visitor visitor,
            String startsWith,
            ObjectNode jsonObject,
            ObjectMapper objectMapper
    ) throws JsonProcessingException;

    void acceptFilter(
            Visitor visitor,
            Action.Filters filters,
            ObjectNode jsonObject,
            ObjectMapper objectMapper
    ) throws JsonProcessingException;

    void acceptBuyTokens(
            Visitor visitor,
            Integer count,
            ObjectNode jsonObject,
            ObjectMapper objectMapper
    ) throws JsonProcessingException;

    void acceptBuyPremiumAccount(
            Visitor visitor,
            ObjectNode jsonObject,
            ObjectMapper objectMapper
    ) throws JsonProcessingException;

    void acceptPurchaseMovie(
            Visitor visitor,
            ObjectNode jsonObject,
            ObjectMapper objectMapper
    ) throws JsonProcessingException;

    void acceptWatchMovie(
            Visitor visitor,
            ObjectNode jsonObject,
            ObjectMapper objectMapper
    ) throws JsonProcessingException;

    void acceptLikeMovie(
            Visitor visitor,
            ObjectNode jsonObject,
            ObjectMapper objectMapper
    ) throws JsonProcessingException;

    void acceptRateMovie(
            Visitor visitor,
            Integer rate,
            ObjectNode jsonObject,
            ObjectMapper objectMapper
    ) throws JsonProcessingException;

    /**
     * Sets the json object with the "field name" : "value" pair
     * @param jsonObject for json object to be set
     * @param objectMapper for the object mapper
     * @param fieldName for the field name
     * @param value for the value
     * @throws JsonProcessingException in case of exceptions when processing the json object
     */
    default void parseOutput(
            ObjectNode jsonObject,
            ObjectMapper objectMapper,
            String fieldName,
            Object value
    ) throws JsonProcessingException {
        jsonObject.set(
                fieldName,
                objectMapper.readTree(
                        objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value)
                )
        );
    }

    /**
     * Sets the default error output
     * @param jsonObject for json object
     * @param objectMapper for object mapper
     * @throws JsonProcessingException in case of exceptions when processing the json object
     */
    default void parseErrorOutput(
            ObjectNode jsonObject,
            ObjectMapper objectMapper
    ) throws JsonProcessingException {
        parseOutput(jsonObject, objectMapper, "error", "Error");
        parseOutput(
                jsonObject,
                objectMapper,
                "currentMoviesList",
                objectMapper.createArrayNode()
        );
        parseOutput(jsonObject, objectMapper, "currentUser", null);
    }

    /**
     * Sets the json object with the default success output
     * @param jsonObject for json object
     * @param objectMapper for object mapper
     * @param currentUser for the current user
     * @throws JsonProcessingException in case of exceptions when processing the json object
     */
    default void parseSuccessOutput(
            ObjectNode jsonObject,
            ObjectMapper objectMapper,
            User currentUser
    ) throws JsonProcessingException {
        parseOutput(jsonObject, objectMapper, "error", null);
        parseOutput(
                jsonObject, objectMapper,
                "currentMoviesList",
                currentUser.getAvailableMovies()
        );
        parseOutput(jsonObject, objectMapper, "currentUser", currentUser);
    }

    /**
     * Sets the json object with the details of the searched movie by the current user
     * @param jsonObject for json object
     * @param objectMapper for object mapper
     * @param searchedMovie for the searched movie
     * @param currentUser for the current user
     * @throws JsonProcessingException in case of exceptions when processing the json object
     */
    default void parseMovieOutput(
            ObjectNode jsonObject,
            ObjectMapper objectMapper,
            Movie searchedMovie,
            User currentUser
    ) throws JsonProcessingException {
        parseOutput(jsonObject, objectMapper, "error", null);
        parseOutput(jsonObject, objectMapper, "currentMoviesList", new ArrayList<>(List.of(searchedMovie)));
        parseOutput(jsonObject, objectMapper, "currentUser", currentUser);
    }
}
