package platform.actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.*;
import platform.*;
import strategies.*;
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
     * @throws IOException in case of exceptions when processing the json object
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
                        new Context(
                                new Back(),
                                invoker,
                                new BackCommand(platform),
                                jsonObject
                        ).executeStrategy();

                case "database" -> {
                    switch (action.getFeature()) {
                        case "add" ->
                                new Context(
                                        new AddMovie(),
                                        invoker,
                                        new AddMovieCommand(action.getAddedMovie(), platform),
                                        jsonObject
                                ).executeStrategy();

                        case "delete" ->
                                new Context(
                                        new DeleteMovie(),
                                        invoker,
                                        new DeleteMovieCommand(action.getDeletedMovie(), platform),
                                        jsonObject
                                ).executeStrategy();

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
                                new Context(
                                        new Subscribe(),
                                        invoker,
                                        new SubscribeCommand(action.getSubscribedGenre(), platform),
                                        jsonObject
                                ).executeStrategy();

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

        User currentUser = platform.getCurrentUser();

        if (currentUser != null) {
            if (currentUser.getCredentials().getAccountType().equals("premium")) {
                ObjectNode jsonObject = PlatformConstants.OBJECT_MAPPER.createObjectNode();

                new Context(
                        new Recommend(),
                        invoker,
                        new RecommendCommand(platform),
                        jsonObject
                ).executeStrategy();

                output.add(jsonObject);
            }
        }
    }
}
