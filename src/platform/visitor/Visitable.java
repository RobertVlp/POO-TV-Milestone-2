package platform.visitor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import platform.OutputParser;
import platform.actions.Action;
import platform.User;

public interface Visitable extends OutputParser {
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
}
