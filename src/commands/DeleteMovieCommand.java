package commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import platform.Platform;
import platform.PlatformConstants;
import platform.movies.Movie;

import java.io.IOException;

public class DeleteMovieCommand implements Command {
    private final Platform platform;
    private final ObjectMapper objectMapper;
    private final String deletedMovie;

    public DeleteMovieCommand(final String deletedMovie) throws IOException {
        platform = Platform.getInstance();
        objectMapper = PlatformConstants.OBJECT_MAPPER;
        this.deletedMovie = deletedMovie;
    }

    @Override
    public void execute(final ObjectNode jsonObject) throws JsonProcessingException {
        boolean deleted = false;

        for (Movie movie : platform.getMovies()) {
            if (movie.getName().equals(deletedMovie)) {
                platform.getMovies().remove(movie);
                deleted = true;
                break;
            }
        }

        if (!deleted) {
            parseErrorOutput(jsonObject, objectMapper);
        }
    }
}
