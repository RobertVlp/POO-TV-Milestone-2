package platform.actions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.*;
import platform.Platform;
import platform.PlatformConstants;
import platform.visitor.PlatformVisitor;

import java.io.IOException;
import java.util.ArrayList;

public final class ActionsWrapper {
    private final Platform platform;
    private final ArrayList<Action> actions;
    private final ObjectMapper objectMapper;
    private final Invoker invoker;

    public ActionsWrapper(final Platform platform, final ArrayList<Action> actions) {
        this.platform = platform;
        this.actions = actions;
        objectMapper = PlatformConstants.OBJECT_MAPPER;
        invoker = new Invoker();
    }

    /**
     * Performs the actions received from the input
     * @param output for output
     * @throws JsonProcessingException in case of exceptions when processing the json object
     */
    public void performActions(
            final PlatformVisitor platformVisitor,
            final ArrayNode output
    ) throws IOException {
        for (Action action : actions) {
            ObjectNode jsonObject = objectMapper.createObjectNode();

            switch (action.getType()) {
                case "change page" ->
                    platform.acceptChangePage(
                            platformVisitor,
                            action,
                            jsonObject,
                            objectMapper
                    );

                case "back" ->
                        invoker.runCommand(
                                new BackCommand(platform),
                                jsonObject
                        );

                case "database" -> {
                    switch (action.getFeature()) {
                        case "add" ->
                                invoker.runCommand(
                                        new AddMovieCommand(action.getAddedMovie(), platform),
                                        jsonObject
                                );

                        case "delete" ->
                                invoker.runCommand(
                                        new DeleteMovieCommand(action.getDeletedMovie(), platform),
                                        jsonObject
                                );

                        default -> {

                        }
                    }
                }

                case "on page" -> {
                    switch (action.getFeature()) {
                        case "login" ->
                                platform.acceptLogin(
                                        platformVisitor,
                                        action.getCredentials(),
                                        jsonObject,
                                        objectMapper
                                );
                        case "register" ->
                                platform.acceptRegister(
                                        platformVisitor,
                                        action.getCredentials(),
                                        jsonObject,
                                        objectMapper
                                );

                        case "search" ->
                                platform.acceptSearch(
                                        platformVisitor,
                                        action.getStartsWith(),
                                        jsonObject,
                                        objectMapper
                                );

                        case "filter" ->
                                platform.acceptFilter(
                                        platformVisitor,
                                        action.getFilters(),
                                        jsonObject,
                                        objectMapper
                                );

                        case "buy tokens" ->
                                platform.acceptBuyTokens(
                                        platformVisitor,
                                        action.getCount(),
                                        jsonObject,
                                        objectMapper
                                );

                        case "buy premium account" ->
                                platform.acceptBuyPremiumAccount(
                                        platformVisitor,
                                        jsonObject,
                                        objectMapper
                                );

                        case "purchase" ->
                                platform.acceptPurchaseMovie(
                                        platformVisitor,
                                        jsonObject,
                                        objectMapper
                                );

                        case "watch" ->
                                platform.acceptWatchMovie(
                                        platformVisitor,
                                        jsonObject,
                                        objectMapper
                                );

                        case "like" ->
                                platform.acceptLikeMovie(
                                        platformVisitor,
                                        jsonObject,
                                        objectMapper
                                );

                        case "rate" ->
                                platform.acceptRateMovie(
                                        platformVisitor,
                                        action.getRate(),
                                        jsonObject,
                                        objectMapper
                                );

                        case "subscribe" ->
                                invoker.runCommand(
                                        new SubscribeCommand(action.getSubscribedGenre(), platform),
                                        jsonObject
                                );

                        default -> {
                        }
                    }
                }

                default -> {
                }
            }

            if (!jsonObject.isEmpty()) {
                output.add(jsonObject);
            }
        }
    }
}
