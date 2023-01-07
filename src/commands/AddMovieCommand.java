package commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import platform.Platform;
import platform.PlatformConstants;
import platform.movies.Movie;

public final class AddMovieCommand implements Command {
    private final Platform platform;
    private final ObjectMapper objectMapper;
    private final Movie addedMovie;

    public AddMovieCommand(final Movie addedMovie, final Platform platform) {
        this.platform = platform;
        objectMapper = PlatformConstants.OBJECT_MAPPER;
        this.addedMovie = addedMovie;
    }

    @Override
    public void execute(final ObjectNode jsonObject) throws JsonProcessingException {
        for (Movie movie : platform.getMovies()) {
            if (movie.getName().equals(addedMovie.getName())) {
                parseErrorOutput(jsonObject, objectMapper);
                return;
            }
        }

        platform.getMovies().add(addedMovie);
        platform.modifyState("ADD", addedMovie, null);
    }
}
