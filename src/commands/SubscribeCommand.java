package commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import platform.Platform;
import platform.PlatformConstants;
import platform.User;
import platform.movies.Movie;

import java.io.IOException;

public final class SubscribeCommand implements Command {
    private final Platform platform;
    private final String subscribedGenre;
    private final ObjectMapper objectMapper;

    public SubscribeCommand(final String subscribedGenre) throws IOException {
        platform = Platform.getInstance();
        objectMapper = PlatformConstants.OBJECT_MAPPER;
        this.subscribedGenre = subscribedGenre;
    }

    @Override
    public void execute(
            final ObjectNode jsonObject
    ) throws JsonProcessingException {
        if (!platform.getCurrentPage().equals("see details")) {
            parseErrorOutput(jsonObject, objectMapper);
            return;
        }

        User currentUser = platform.getCurrentUser();

        if (currentUser.getSubscribedGenres().contains(subscribedGenre)) {
            parseErrorOutput(jsonObject, objectMapper);
            return;
        }

        Movie searchedMovie = platform.getSearchedMovie();

        if (searchedMovie.getGenres().contains(subscribedGenre)) {
            currentUser.getSubscribedGenres().add(subscribedGenre);
        } else {
            parseErrorOutput(jsonObject, objectMapper);
        }
    }
}
