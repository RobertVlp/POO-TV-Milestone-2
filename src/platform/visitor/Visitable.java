package platform.visitor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import platform.OutputParser;
import platform.actions.Action;
import platform.User;

public interface Visitable extends OutputParser {
    /**
     * Processes a visitor's request to change the current page.
     *
     * @param visitor the visitor that executes the "change page" action
     * @param action the action representing the page change request
     * @param jsonObject the JSON object to update with the response
     * @param objectMapper the object mapper to use for parsing
     * @throws JsonProcessingException if an error occurs while parsing the JSON object
     */
    void acceptChangePage(
            Visitor visitor,
            Action action,
            ObjectNode jsonObject,
            ObjectMapper objectMapper
    ) throws JsonProcessingException;

    /**
     * Processes a visitor's login of a registered user.
     *
     * @param visitor the visitor that executes the "login" action
     * @param credentials the credentials provided by the user
     * @param jsonObject the JSON object to update with the response
     * @param objectMapper the object mapper to use for parsing
     * @throws JsonProcessingException if an error occurs while parsing the JSON object
     */
    void acceptLogin(
            Visitor visitor,
            User.Credentials credentials,
            ObjectNode jsonObject,
            ObjectMapper objectMapper
    ) throws JsonProcessingException;

    /**
     * Processes a visitor's registration of a new user.
     *
     * @param visitor the visitor that executes the "register" action
     * @param credentials the username and password chosen by the new user
     * @param jsonObject the JSON object to update with the response
     * @param objectMapper the object mapper to use for parsing
     * @throws JsonProcessingException if an error occurs while parsing the JSON object
     */
    void acceptRegister(
            Visitor visitor,
            User.Credentials credentials,
            ObjectNode jsonObject,
            ObjectMapper objectMapper
    ) throws JsonProcessingException;

    /**
     * Processes a visitor's search for a movie by name.
     *
     * @param visitor the visitor that executes the "search" action
     * @param startsWith the search query, a string that the movie's name should start with
     * @param jsonObject the JSON object to update with the response
     * @param objectMapper the object mapper to use for parsing
     * @throws JsonProcessingException if an error occurs while parsing the JSON object
     */
    void acceptSearch(
            Visitor visitor,
            String startsWith,
            ObjectNode jsonObject,
            ObjectMapper objectMapper
    ) throws JsonProcessingException;

    /**
     * Processes a visitor's application of filters to the list of available movies
     *
     * @param visitor the visitor that executes the "filter" action
     * @param jsonObject the JSON object to update with the response
     * @param objectMapper the object mapper to use for parsing
     * @throws JsonProcessingException if an error occurs while parsing the JSON object
     */
    void acceptFilter(
            Visitor visitor,
            Action.Filters filters,
            ObjectNode jsonObject,
            ObjectMapper objectMapper
    ) throws JsonProcessingException;

    /**
     * Processes a visitor's purchase of tokens.
     *
     * @param visitor the visitor that executes the "buy tokens" action
     * @param jsonObject the JSON object to update with the response
     * @param objectMapper the object mapper to use for parsing
     * @throws JsonProcessingException if an error occurs while parsing the JSON object
     */
    void acceptBuyTokens(
            Visitor visitor,
            Integer count,
            ObjectNode jsonObject,
            ObjectMapper objectMapper
    ) throws JsonProcessingException;

    /**
     * Processes a visitor's purchase of a premium account.
     *
     * @param visitor the visitor that executes the "buy premium account" action
     * @param jsonObject the JSON object to update with the response
     * @param objectMapper the object mapper to use for parsing
     * @throws JsonProcessingException if an error occurs while parsing the JSON object
     */
    void acceptBuyPremiumAccount(
            Visitor visitor,
            ObjectNode jsonObject,
            ObjectMapper objectMapper
    ) throws JsonProcessingException;

    /**
     * Processes a visitor's purchase of a movie.
     *
     * @param visitor the visitor that executes the "purchase" action
     * @param jsonObject the JSON object to update with the response
     * @param objectMapper the object mapper to use for parsing
     * @throws JsonProcessingException if an error occurs while parsing the JSON object
     */
    void acceptPurchaseMovie(
            Visitor visitor,
            ObjectNode jsonObject,
            ObjectMapper objectMapper
    ) throws JsonProcessingException;


    /**
     * Processes a visitor's watch of a movie.
     *
     * @param visitor the visitor that executes the "watch" action
     * @param jsonObject the JSON object to update with the response
     * @param objectMapper the object mapper to use for parsing
     * @throws JsonProcessingException if an error occurs while parsing the JSON object
     */
    void acceptWatchMovie(
            Visitor visitor,
            ObjectNode jsonObject,
            ObjectMapper objectMapper
    ) throws JsonProcessingException;

    /**
     * Processes a visitor's like for a movie.
     *
     * @param visitor the visitor that executes the "like" action
     * @param jsonObject the JSON object to update with the response
     * @param objectMapper the object mapper to use for parsing
     * @throws JsonProcessingException if an error occurs while parsing the JSON object
     */
    void acceptLikeMovie(
            Visitor visitor,
            ObjectNode jsonObject,
            ObjectMapper objectMapper
    ) throws JsonProcessingException;

    /**
     * Processes a visitor's rating for a movie.
     *
     * @param visitor the visitor that executes the "rate" action
     * @param jsonObject the JSON object to update with the response
     * @param objectMapper the object mapper to use for parsing
     * @throws JsonProcessingException if an error occurs while parsing the JSON object
     */
    void acceptRateMovie(
            Visitor visitor,
            Integer rate,
            ObjectNode jsonObject,
            ObjectMapper objectMapper
    ) throws JsonProcessingException;
}
