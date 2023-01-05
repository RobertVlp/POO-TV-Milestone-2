package commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import platform.Platform;
import platform.PlatformConstants;
import platform.User;
import platform.movies.Movie;
import java.util.HashMap;
import java.util.Map;

public final class RecommendCommand implements Command {
    private final Platform platform;
    private final ObjectMapper objectMapper;

    public RecommendCommand(final Platform platform) {
        this.platform = platform;
        objectMapper = PlatformConstants.OBJECT_MAPPER;
    }

    @Override
    public void execute(ObjectNode jsonObject) throws JsonProcessingException {
        User currentUser = platform.getCurrentUser();

        if (currentUser.getLikedMovies().isEmpty()) {
            currentUser.getNotifications().add(
                    new User.Notifications("No recommendation", "Recommendation")
            );
            parseRecommendOutput(jsonObject, objectMapper, currentUser);
            return;
        }

        HashMap<String, Integer> preferredGenres = new HashMap<>();

        for (Movie likedMovie : currentUser.getLikedMovies()) {
            for (String genre : likedMovie.getGenres()) {
                if (preferredGenres.containsKey(genre)) {
                    int numLikes = preferredGenres.get(genre);
                    preferredGenres.replace(genre, numLikes, numLikes + 1);
                } else {
                    preferredGenres.put(genre, 1);
                }
            }
        }

        platform.getMovies().sort((o1, o2) -> Integer.compare(o2.getNumLikes(), o1.getNumLikes()));

        while (!preferredGenres.isEmpty()) {
            String mostLikedGenre = null;
            int maxLikes = 0;

            for (Map.Entry<String, Integer> entry : preferredGenres.entrySet()) {
                if (entry.getValue() > maxLikes) {
                    maxLikes = entry.getValue();
                    mostLikedGenre = entry.getKey();
                }
            }

            for (Movie movie : platform.getMovies()) {
                if (!movie.getCountriesBanned().contains(currentUser.getCredentials().getCountry())) {
                    if (movie.getGenres().contains(mostLikedGenre)
                            && !currentUser.getWatchedMovies().contains(movie)) {
                        currentUser.getNotifications().add(
                                new User.Notifications(movie.getName(), "Recommendation")
                        );
                        parseRecommendOutput(jsonObject, objectMapper, currentUser);
                        return;
                    }
                }
            }

            preferredGenres.remove(mostLikedGenre);
        }

        currentUser.getNotifications().add(
                new User.Notifications("No recommendation", "Recommendation")
        );
        parseRecommendOutput(jsonObject, objectMapper, currentUser);
    }
}
