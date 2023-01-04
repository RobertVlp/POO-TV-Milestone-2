package commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import platform.Platform;
import platform.PlatformConstants;
import platform.movies.Movie;

public final class DeleteMovieCommand implements Command {
    private final Platform platform;
    private final ObjectMapper objectMapper;
    private final String deletedMovie;

    public DeleteMovieCommand(final String deletedMovie, final Platform platform) {
        this.platform = platform;
        objectMapper = PlatformConstants.OBJECT_MAPPER;
        this.deletedMovie = deletedMovie;
    }

    @Override
    public void execute(final ObjectNode jsonObject) throws JsonProcessingException {
        for (Movie movie : platform.getMovies()) {
            if (movie.getName().equals(deletedMovie)) {
                platform.getMovies().remove(movie);
                platform.modifyState("DELETE", null, deletedMovie);
                return;
            }
        }

        parseErrorOutput(jsonObject, objectMapper);
    }
}
