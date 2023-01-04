package commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import platform.Platform;
import platform.PlatformConstants;
import platform.movies.Movie;

import java.io.IOException;

public class AddMovieCommand implements Command {
    private final Platform platform;
    private final ObjectMapper objectMapper;
    private final Movie addedMovie;

    public AddMovieCommand(final Movie addedMovie) throws IOException {
        platform = Platform.getInstance();
        objectMapper = PlatformConstants.OBJECT_MAPPER;
        this.addedMovie = addedMovie;
    }

    @Override
    public void execute(final ObjectNode jsonObject) throws JsonProcessingException {
        if (platform.getMovies().contains(addedMovie)) {
            parseErrorOutput(jsonObject, objectMapper);
            return;
        }

        platform.getMovies().add(addedMovie);
    }
}